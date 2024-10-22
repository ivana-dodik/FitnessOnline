package org.unibl.etf.ip.fitnessonline.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.exception.ResourceNotFoundException;
import org.unibl.etf.ip.fitnessonline.exception.UnauthenticatedException;
import org.unibl.etf.ip.fitnessonline.mapper.ProgramMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.*;
import org.unibl.etf.ip.fitnessonline.models.entities.*;
import org.unibl.etf.ip.fitnessonline.repositories.ProgramsRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramsService {
    private final ProgramsRepository programsRepository;
    private final CommentsService commentsService;
    private final ProgramAttributesService programAttributesService;
    private final AttributesService attributesService;
    private final ParticipationsService participationsService;
    private final ImagesService imagesService;

    public ProgramsService(ProgramsRepository programsRepository,
                           CommentsService commentsService,
                           ProgramAttributesService programAttributesService,
                           AttributesService attributesService,
                           @Lazy ParticipationsService participationsService,
                           ImagesService imagesService) {
        this.programsRepository = programsRepository;
        this.commentsService = commentsService;
        this.programAttributesService = programAttributesService;
        this.attributesService = attributesService;
        this.participationsService = participationsService;
        this.imagesService = imagesService;
    }

    public List<ProgramEntity> getAll() {
        return programsRepository.findAll();
    }

    public List<ProgramEntity> getAllActiveByUserId(Integer id) {
        return programsRepository.findAllByAvailableTrueAndDeletedFalseAndInstructorId(id);
    }

    public List<ProgramEntity> getAllByInstructorId(Integer id) {
        return programsRepository.getAllByInstructorIdAndDeletedFalse(id);
    }

    public List<ProgramEntity> getAllParticipatedByUserId(Integer id) {
        List<Integer> participatedProgramsIds = participationsService
                .getAllParticipationsForUser(id)
                .stream()
                .map(p -> p.getProgram().getId())
                .toList();
        return programsRepository.getAllByIdIn(participatedProgramsIds);
    }

    public List<ProgramEntity> getAllCompletedByUserId(Integer id) {
        return programsRepository.getAllByInstructorIdAndAvailableFalseAndDeletedFalse(id);
    }

    public List<String> getImagesByProgramId(Integer id) {
        List<String> imageUrls = imagesService.getImagesByProgramId(id)
                .stream()
                .map(ImageEntity::getImageUrl)
                .collect(Collectors.toList());

        if (imageUrls.size() == 0) {
            imageUrls.add(0, "/images/no-images.png");
        }

        return imageUrls;
    }

    public ProgramEntity getById(Integer id) {
        return programsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program with ID: " + id + " does not exist"));
    }

    public void deleteById(UserEntity userEntity, Integer id) {
        ProgramEntity programEntity = getById(id);
        if (userEntity.getId() != programEntity.getInstructorId()) {
            throw new UnauthenticatedException("User does not have permission to delete the program with ID: " + id);
        }
        programEntity.setDeleted(true);
        programsRepository.saveAndFlush(programEntity);
    }

    public ProgramDetailsDto getProgramByIdWithAttributes(Integer id) {
        ProgramEntity programEntity = getById(id);
        ModelMapper modelMapper = ProgramMapper.configureProgramEntityToProgramDetailsDtoMapper();
        ProgramDetailsDto programDetailsDto = modelMapper.map(programEntity, ProgramDetailsDto.class);
        List<ProgramAttributeEntity> programAttributeEntities = programAttributesService.findByProgramId(id);

        List<AttributeNameValueDto> attributeNameValueDtos = new ArrayList<>();
        for (ProgramAttributeEntity programAttributeEntity : programAttributeEntities) {
            AttributeEntity attributeEntity = attributesService.getById(programAttributeEntity.getAttributeId());
            AttributeNameValueDto attributeNameValueDto = new AttributeNameValueDto();
            attributeNameValueDto.setName(attributeEntity.getName());
            attributeNameValueDto.setValue(programAttributeEntity.getValue());
            attributeNameValueDtos.add(attributeNameValueDto);
        }

        programDetailsDto.setAttributes(attributeNameValueDtos);
        return programDetailsDto;
    }

    public List<ProgramEntity> getAllAvailable() {
        return programsRepository.findAllByAvailableTrueAndDeletedFalse();
    }

    public ProgramEntity getByIdAndAvailable(Integer id) {
        return programsRepository
                .findByIdAndAvailableTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program with ID: " + id + " does not exist or is not available"));
    }

    public List<CommentDto> getCommentsByProgramId(Integer id) {
        ProgramEntity programEntity = getById(id);
        return commentsService.getAllByProgramId(programEntity.getId());
    }


    public List<CommentDto> addCommentByProgramId(Integer id, AddCommentDto addCommentDto, UserEntity userEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(null);
        commentEntity.setContent(addCommentDto.getContent());

        ProgramEntity programEntity = getById(id);
        commentEntity.setProgram(programEntity);
        commentEntity.setUser(userEntity);
        commentEntity.setDateTime(new Timestamp(System.currentTimeMillis()));
        commentsService.add(commentEntity);

        return commentsService.getAllByProgramId(programEntity.getId());
    }

    public ProgramEntity update(ProgramEntity programEntity) {
        return programsRepository.saveAndFlush(programEntity);
    }

    @Transactional
    public ProgramEntity create(UserEntity userEntity, AddProgramDto addProgramDto) {
        ModelMapper modelMapper = ProgramMapper.configureAddProgramDtoToProgramEntityMapper();
        ProgramEntity programEntity = modelMapper.map(addProgramDto, ProgramEntity.class);
        System.out.println("BLAAAAAAA");
        System.out.println(programEntity);
        programEntity.setId(null);
        programEntity.setInstructorId(userEntity.getId());
        programEntity.setCategoryId(addProgramDto.getCategoryId());

        ProgramEntity insertedProgramEntity = programsRepository.saveAndFlush(programEntity);
        Integer programId = insertedProgramEntity.getId();

        for (AddAttributeToProgramDto addAttributeToProgramDto : addProgramDto.getAttributes()) {
            Integer attributeId = addAttributeToProgramDto.getAttributeId();
            String attributeValue = addAttributeToProgramDto.getValue();
            ProgramAttributeEntity programAttributeEntity = new ProgramAttributeEntity();
            programAttributeEntity.setId(null);
            programAttributeEntity.setProgramId(programId);
            programAttributeEntity.setAttributeId(attributeId);
            programAttributeEntity.setValue(attributeValue);
            programAttributesService.create(programAttributeEntity);
        }

        return insertedProgramEntity;
    }

    public void closeProgram(Integer id) {
        ProgramEntity program = programsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found"));

        program.setAvailable(false);

        programsRepository.saveAndFlush(program);
    }
}

