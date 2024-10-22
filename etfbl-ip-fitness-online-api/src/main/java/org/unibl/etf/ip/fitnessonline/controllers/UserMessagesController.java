package org.unibl.etf.ip.fitnessonline.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.fitnessonline.models.dto.AddUserMessageDto;
import org.unibl.etf.ip.fitnessonline.models.dto.InboxDto;
import org.unibl.etf.ip.fitnessonline.models.dto.InboxMessageDto;
import org.unibl.etf.ip.fitnessonline.models.dto.UserMessageDetailsDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserMessageEntity;
import org.unibl.etf.ip.fitnessonline.services.AuthenticationService;
import org.unibl.etf.ip.fitnessonline.services.UserMessagesService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user-messages")
@CrossOrigin(origins = "*")
public class UserMessagesController {

    private final UserMessagesService userMessageService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<UserMessageEntity> addUserMessage(@RequestBody @Valid AddUserMessageDto addUserMessageDto,
                                                            @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                            @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity sender = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        return ResponseEntity.ok(userMessageService.addUserMessage(addUserMessageDto, sender));
    }

    @GetMapping("/received")
    public ResponseEntity<List<UserMessageDetailsDto>> getAllReceivedMessages(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                              @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity receiver = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        List<UserMessageDetailsDto> receivedMessages = userMessageService.getAllReceivedMessages(receiver);
        return ResponseEntity.ok(receivedMessages);
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<InboxDto>> inbox(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                              @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        // izlistava posljednje poruke od svih korisnika koji su pisali datom korisniku
        UserEntity receiver = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        List<InboxDto> inbox = userMessageService.getInbox(receiver);
        return ResponseEntity.ok(inbox);
    }

    @GetMapping("/inbox/{senderId}")
    public ResponseEntity<List<InboxMessageDto>> allMessagesBetweenTwoUsers(@RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                     @RequestHeader(value = "X-Auth-Password") String xAuthPassword, @PathVariable Integer senderId) {
        // izlistava citavu prepisku izmedju dva korisnika
        UserEntity receiver = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        List<InboxMessageDto> allMessages = userMessageService.getInboxMessages(receiver, senderId);
        return ResponseEntity.ok(allMessages);
    }

    @GetMapping("/received/{id}")
    public ResponseEntity<UserMessageDetailsDto> getUserMessageById(@PathVariable Integer id,
                                                                    @RequestHeader(value = "X-Auth-Username") String xAuthUsername,
                                                                    @RequestHeader(value = "X-Auth-Password") String xAuthPassword) {
        UserEntity user = authenticationService.authenticateAndGetUserEntity(xAuthUsername, xAuthPassword);
        UserMessageDetailsDto userMessage = userMessageService.getUserMessageById(id, user);
        return ResponseEntity.ok(userMessage);
    }


}
