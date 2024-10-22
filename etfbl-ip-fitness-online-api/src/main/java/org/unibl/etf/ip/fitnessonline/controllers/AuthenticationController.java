package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.LoginUserDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.UsersService;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
    UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@RequestBody @Valid LoginUserDto loginUserDto) {
        try {
            usersService.login(loginUserDto);
            //System.out.println("USPJEH");
            return ResponseEntity.ok(loginUserDto);
        } catch (Exception e) {
            //System.out.println("NEUSPJEH");
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/user-details")
    public ResponseEntity<UserEntity> getUserDetails(@RequestBody(required = false) @Valid LoginUserDto loginUserDto) {
        /*try {
            return ResponseEntity.ok(usersService.login(loginUserDto));
        } catch (Exception e){
            e.getMessage();
        }
        return null;*/
        try {
            if (loginUserDto == null) {
                return null;
            }

            return ResponseEntity.ok(usersService.login(loginUserDto));
        } catch (Exception e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}