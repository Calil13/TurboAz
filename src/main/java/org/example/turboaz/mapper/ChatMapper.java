package org.example.turboaz.mapper;

import org.example.turboaz.dto.chat.ConversationResponse;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.dto.chat.WebSocketMessageResponse;
import org.example.turboaz.entity.Conversation;
import org.example.turboaz.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "conversationId", source = "message.conversation.id")
    @Mapping(target = "senderId", source = "message.sender.id")
    @Mapping(target = "senderName", expression = "java(message.getSender().getName())")
    WebSocketMessageResponse toWebSocketDto(Message message);

    @Mapping(target = "senderName", expression = "java(message.getSender().getName())")
    MessageResponse toDto(Message message);

    @Mapping(target = "conversationId", source = "id")
    @Mapping(target = "otherPersonName", source = "id")
    ConversationResponse toDto(Conversation conversation);
}
