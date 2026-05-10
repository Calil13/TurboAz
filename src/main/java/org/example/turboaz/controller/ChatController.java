package org.example.turboaz.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.chat.MessageRequest;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
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
}