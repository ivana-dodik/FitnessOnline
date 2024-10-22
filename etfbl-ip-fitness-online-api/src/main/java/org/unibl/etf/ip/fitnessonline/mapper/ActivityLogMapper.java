package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.ActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.dto.AddActivityLogDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ActivityLogEntity;
import org.unibl.etf.ip.fitnessonline.repositories.UsersRepository;

import java.sql.Timestamp;

public abstract class ActivityLogMapper {
    private static UsersRepository usersRepository;

    public static ModelMapper configureAddActivityLogDtoToActivityLogEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddActivityLogDto, ActivityLogEntity>() {

            @Override
            protected void configure() {
                map().setLogDate(new Timestamp(System.currentTimeMillis()));
            }
        });
        return modelMapper;
    }

    public static ModelMapper configureActivityLogEntityToActivityLogDtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ActivityLogEntity, ActivityLogDto>() {
            @Override
            protected void configure() {
                map().setUsername("ivana");
                map().setDateTime(source.getLogDate().toString());
            }
        });

        return modelMapper;
    }
}