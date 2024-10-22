package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.CommentDto;
import org.unibl.etf.ip.fitnessonline.models.entities.CommentEntity;

public abstract class CommentsMapper {

    public static ModelMapper configureCommentEntityToCommentDtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CommentEntity, CommentDto>() {
            @Override
            protected void configure() {
                map().setUsername(source.getUser().getUsername());
            }
        });

        return modelMapper;
    }
}
