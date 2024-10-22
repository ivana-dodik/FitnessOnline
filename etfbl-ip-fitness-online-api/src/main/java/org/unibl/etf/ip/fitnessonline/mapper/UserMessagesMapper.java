package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.AddUserMessageDto;
import org.unibl.etf.ip.fitnessonline.models.dto.UserMessageDetailsDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserMessageEntity;

import java.sql.Timestamp;

public abstract class UserMessagesMapper {
    public static ModelMapper configureAddUserMessageDtoToUserMessageEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddUserMessageDto, UserMessageEntity>() {
            @Override
            protected void configure() {
                map().setDateTime(new Timestamp(System.currentTimeMillis()));
                map().setIsRead(false);
                skip().setId(null); // Preskačemo postavljanje ID-a jer će se automatski generisati u bazi podataka
            }
        });

        return modelMapper;
    }

    public static ModelMapper configureUserMessageEntityToUserMessageDetailsDtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<UserMessageEntity, UserMessageDetailsDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setContent(source.getContent());
                map().setIsRead(source.getIsRead());
                map().setDateTime(source.getDateTime());
                map().setSenderId(source.getSenderId());
            }
        });
        return modelMapper;
    }
}
