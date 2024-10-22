package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.AddMessageDto;
import org.unibl.etf.ip.fitnessonline.models.entities.MessageEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.MessagesService;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessagesController {

    private final MessagesService messagesService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<MessageEntity> addNewMessage(@RequestBody @Valid AddMessageDto addMessageDto,
                                                       @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                       @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity userEntity = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(messagesService.addNewMessage(addMessageDto, userEntity));
    }

}
