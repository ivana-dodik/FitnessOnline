package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.AddParticipationDto;
import org.unibl.etf.ip.fitnessonline.models.entities.ParticipationEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.ParticipationsService;

@RestController
@AllArgsConstructor
@RequestMapping("/participation")
@CrossOrigin(origins = "*")
public class ParticipationsController {
    private final ParticipationsService participationsService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ParticipationEntity> addParticipation(@RequestBody @Valid AddParticipationDto participationDetails,
                                                                @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(participationsService.participate(participationDetails, userEntity));
    }

}

