package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.AddMessageDto;
import org.unibl.etf.ip.fitnessonline.models.entities.MessageEntity;

import java.sql.Timestamp;

public abstract class MessagesMapper {

    public static ModelMapper configureAddMessageDtoToMessageEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddMessageDto, MessageEntity>() {
            @Override
            protected void configure() {
                map().setDateTime(new Timestamp(System.currentTimeMillis()));
                map().setIsRead(false);
            }
        });

        return modelMapper;
    }
}
