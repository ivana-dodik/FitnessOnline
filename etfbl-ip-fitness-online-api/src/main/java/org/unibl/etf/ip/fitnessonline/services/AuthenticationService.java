package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.models.dto.LoginUserDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;

@Service
@AllArgsConstructor
public class AuthenticationService {
    UsersService usersService;
    LogsService logsService;

    public UserEntity authenticateAndGetUserEntity(String xAuthUsername, String xAuthPassword) {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername(xAuthUsername);
        loginUserDto.setPassword(xAuthPassword);
        logsService.addLog("Authenticating user with username: " + xAuthUsername);
        return usersService.login(loginUserDto);
    }

    public UserEntity getUserInfoForValidation(String xAuthUsername, String xAuthPassword) {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername(xAuthUsername);
        loginUserDto.setPassword(xAuthPassword);
        return usersService.getUserInfoForValidation(loginUserDto);
    }
}