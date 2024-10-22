package org.unibl.etf.ip.fitnessonline.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.fitnessonline.mapper.MessagesMapper;
import org.unibl.etf.ip.fitnessonline.models.dto.AddMessageDto;
import org.unibl.etf.ip.fitnessonline.models.entities.MessageEntity;
import org.unibl.etf.ip.fitnessonline.models.entities.UserEntity;
import org.unibl.etf.ip.fitnessonline.repositories.MessagesRepository;

@Service
@AllArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public MessageEntity addNewMessage(AddMessageDto newMessageDto, UserEntity userEntity) {
        ModelMapper mapper = MessagesMapper.configureAddMessageDtoToMessageEntityMapper();
        MessageEntity messageEntity = mapper.map(newMessageDto, MessageEntity.class);
        messageEntity.setId(null);
        messageEntity.setUserId(userEntity.getId());
        return messagesRepository.saveAndFlush(messageEntity);
    }
}
