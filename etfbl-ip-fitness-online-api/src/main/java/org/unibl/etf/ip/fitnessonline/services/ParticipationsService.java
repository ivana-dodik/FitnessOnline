package org.unibl.etf.ip.fitnessonline.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.mapper.ParticipationsMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.AddParticipationDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ParticipationEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.repositories.ParticipationsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipationsService {
    private final ParticipationsRepository participationsRepository;
    private final ProgramsService programsService;
    private final UsersService usersService;

    @Transactional
    public ParticipationEntity participate(AddParticipationDto addParticipationDto, UserEntity userEntity) {
        Integer programId = addParticipationDto.getProgramId();
        ProgramEntity programEntity = programsService.getById(programId);
        //programsService.update(programEntity);

        ModelMapper modelMapper = ParticipationsMapper.configureParticipationDtoToParticipationEntityMapper();
        ParticipationEntity participationEntity = modelMapper.map(addParticipationDto, ParticipationEntity.class);
        participationEntity.setId(null);
        participationEntity.setProgram(programEntity);
        participationEntity.setUser(userEntity);

        return participationsRepository.saveAndFlush(participationEntity);
    }

    public List<ParticipationEntity> getAllParticipationsForUser(Integer id) {
        return participationsRepository.getAllByUserId(id);
    }

    public boolean hasUserParticipatedInProgram(int userId, int programId) {
        return participationsRepository.existsByUserIdAndProgramId(userId, programId);
    }

}
