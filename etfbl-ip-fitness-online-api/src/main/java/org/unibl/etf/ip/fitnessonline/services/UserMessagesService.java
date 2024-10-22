package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.exception.ResourceNotFoundException;
import org.unibl.etf.ip.fitnessonline.exception.UnauthenticatedException;
import org.unibl.etf.ip.fitnessonline.mapper.UserMessagesMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.AddUserMessageDto;
import org.unibl.etf.ip.fitnessonline.models.dto.InboxDto;
import org.unibl.etf.ip.fitnessonline.models.dto.InboxMessageDto;
import org.unibl.etf.ip.fitnessonline.models.dto.UserMessageDetailsDto;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserMessageEntity;
import org.unibl.etf.ip.fitnessonline.repositories.UserMessageRepository;
import org.unibl.etf.ip.fitnessonline.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMessagesService {
    private final UserMessageRepository userMessageRepository;
    private final UsersRepository usersRepository;

    public UserMessageEntity addUserMessage(AddUserMessageDto addUserMessageDto, UserEntity sender) {
        ModelMapper mapper = UserMessagesMapper.configureAddUserMessageDtoToUserMessageEntityMapper();
        UserMessageEntity userMessageEntity = mapper.map(addUserMessageDto, UserMessageEntity.class);
        userMessageEntity.setId(null);
        userMessageEntity.setSenderId(sender.getId());
        userMessageEntity.setIsRead(false);
        return userMessageRepository.saveAndFlush(userMessageEntity);
    }

    public List<UserMessageDetailsDto> getAllReceivedMessages(UserEntity receiver) {
        ModelMapper mapper = UserMessagesMapper.configureUserMessageEntityToUserMessageDetailsDtoMapper();
        List<UserMessageEntity> receivedMessages = userMessageRepository.findAllByReceiverId(receiver.getId());
        return receivedMessages.stream()
                .map(message -> mapper.map(message, UserMessageDetailsDto.class))
                .collect(Collectors.toList());
    }

    public UserMessageEntity getById(Integer id) {
        return userMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User message with ID: " + id + " does not exist"));
    }

    public UserMessageDetailsDto getUserMessageById(Integer id) {
        ModelMapper modelMapper = UserMessagesMapper.configureAddUserMessageDtoToUserMessageEntityMapper();
        UserMessageEntity receivedMessage = userMessageRepository.getById(id);
        UserMessageDetailsDto messageDetails = modelMapper.map(receivedMessage, UserMessageDetailsDto.class);
        messageDetails.setIsRead(true);
        return messageDetails;
    }

    public UserMessageDetailsDto getUserMessageById(Integer messageId, UserEntity user) {
        ModelMapper modelMapper = UserMessagesMapper.configureUserMessageEntityToUserMessageDetailsDtoMapper();
        UserMessageEntity messageEntity = getById(messageId);
        if (!messageEntity.getReceiverId().equals(user.getId())) {
            throw new UnauthenticatedException();
        }
        UserMessageDetailsDto messageDetails = modelMapper.map(messageEntity, UserMessageDetailsDto.class);
        messageDetails.setIsRead(true);
        return messageDetails;
    }

    public List<InboxDto> getInbox(UserEntity receiver) {
        var latestMessages = userMessageRepository.findLatestMessages(receiver.getId());
        return latestMessages.stream()
                .map(msg -> new InboxDto(msg.getSenderId(), usersRepository.findById(msg.getSenderId()).get().getUsername(), msg.getContent(), msg.getIsRead()))
                .toList();
    }

    public List<InboxMessageDto> getInboxMessages(UserEntity receiver, Integer senderId) {
        // dohvatamo poruke koje smo mi poslali drugom korisniku
        var sent = userMessageRepository.findAllBySenderIdAndReceiverId(receiver.getId(), senderId);

        //  kao i poruke koje smo primili od njega
        var received = userMessageRepository.findAllBySenderIdAndReceiverId(senderId, receiver.getId());

        List<InboxMessageDto> allMessages = new ArrayList<>();

        allMessages.addAll(
                sent.stream().map(msg -> new InboxMessageDto(true, msg.getContent(), msg.getDateTime())).toList()
        );

        allMessages.addAll(
                received.stream().map(msg -> new InboxMessageDto(false, msg.getContent(), msg.getDateTime())).toList()
        );

        return allMessages.stream().sorted(Comparator.comparing(InboxMessageDto::dateTime)).toList();
    }
}

