package org.example.turboaz.mapper;

import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.dto.chat.WebSocketMessageResponse;
import org.example.turboaz.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "conversationId", source = "message.conversation.id")
    @Mapping(target = "senderId", source = "message.sender.id")
    @Mapping(target = "senderName", expression = "java(message.getSender().getName())")
    WebSocketMessageResponse toWebSocketDto(Message message);

    @Mapping(target = "senderName", expression = "java(message.getSender().getName())")
    MessageResponse toDto(Message message);
}
