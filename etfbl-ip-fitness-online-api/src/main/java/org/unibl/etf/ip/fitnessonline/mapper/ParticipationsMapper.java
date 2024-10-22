package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.AddParticipationDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ParticipationEntity;

public abstract class ParticipationsMapper {

    public static ModelMapper configureParticipationDtoToParticipationEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddParticipationDto, ParticipationEntity>() {
            @Override
            protected void configure() {
                map().setId(null);
            }
        });

        return modelMapper;
    }
}