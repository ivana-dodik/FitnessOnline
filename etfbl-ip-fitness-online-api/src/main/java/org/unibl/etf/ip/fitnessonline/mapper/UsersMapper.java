package org.unibl.etf.ip.fitnessonline.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.unibl.etf.ip.fitnessonline.models.dto.AddUserDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;

public abstract class UsersMapper {

    public static ModelMapper configureAddUserDtoToUserEntityMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AddUserDto, UserEntity>() {
            @Override
            protected void configure() {
                map().setActivated(false);
                map().setDeleted(false);
                map().setAvatarUrl("/images/no-avatar.png");
            }
        });

        return modelMapper;
    }

    public static ModelMapper configureUserEntityToLoginUserDtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
