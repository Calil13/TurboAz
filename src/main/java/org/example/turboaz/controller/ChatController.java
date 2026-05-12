package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.chat.ConversationResponse;
import org.example.turboaz.dto.chat.MessageRequest;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.dto.chat.WebSocketMessageResponse;
import org.example.turboaz.service.ChatService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/sendMessage")
    @SecurityRequirement(name = "bearerAuth")
    public void sendMessage(@RequestBody MessageRequest request) {
        chatService.sendMessage(request);
    }

    @GetMapping("/messages/{conversationId}")
    @SecurityRequirement(name = "bearerAuth")
    public List<MessageResponse> getMessages(@PathVariable Long conversationId) {
        return chatService.getMessages(conversationId);
    }

    @GetMapping("/my-conversations")
    @SecurityRequirement(name = "bearerAuth")
    public Page<ConversationResponse> getMyConversations(@ParameterObject Pageable pageable) {
        return chatService.getMyConversations(pageable);
    }

    @GetMapping("/conversation")
    @SecurityRequirement(name = "bearerAuth")
    public WebSocketMessageResponse initConversation(@RequestParam Long buyerId, @RequestParam Long sellerId, @RequestParam Long carId) {
        return chatService.initConversation(buyerId, sellerId, carId);
    }
}