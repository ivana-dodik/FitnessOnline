package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.mapper.CommentsMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.CommentDto;
import org.unibl.etf.ip.fitnessonline.models.entities.CommentEntity;
import org.unibl.etf.ip.fitnessonline.repositories.CommentsRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;

    public List<CommentDto> getAllByProgramId(Integer programId) {
        ModelMapper modelMapper = CommentsMapper.configureCommentEntityToCommentDtoMapper();
        List<CommentEntity> commentEntities = commentsRepository.getAllByProgramId(programId);
        return commentEntities.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).toList();
    }


    public CommentEntity add(CommentEntity commentEntity) {

        return commentsRepository.saveAndFlush(commentEntity);
    }
}
