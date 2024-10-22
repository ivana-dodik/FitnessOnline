package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.AddProgramDto;
import org.unibl.etf.ip.fitnessonline.models.dto.ProgramDetailsDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ProgramEntity;

import java.sql.Timestamp;

public abstract class ProgramMapper {
    public static ModelMapper configureAddProgramDtoToProgramEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddProgramDto, ProgramEntity>() {
            @Override
            protected void configure() {

                map().setDeleted(false);
                map().setAvailable(true);
                map().setTimeCreated(new Timestamp(System.currentTimeMillis()));
            }
        });
        return modelMapper;
    }

    public static ModelMapper configureProgramEntityToProgramDetailsDtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<ProgramEntity, ProgramDetailsDto>() {
            @Override
            protected void configure() {

            }
        });
        return modelMapper;
    }
}