package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.chat.MessageRequest;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.entity.Car;
import org.example.turboaz.entity.Conversation;
import org.example.turboaz.entity.Message;
import org.example.turboaz.entity.Users;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.repository.CarRepository;
import org.example.turboaz.repository.ConversationRepository;
import org.example.turboaz.repository.MessageRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UsersRepository usersRepository;
    private final CarRepository carRepository;

    public void sendMessage(MessageRequest request) {

        Users sender = usersRepository.findById(request.getSenderId())
                .orElseThrow(() -> new NotFoundException("SENDER_NOT_FOUND"));

        Users receiver = usersRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundException("RECEİVER_NOT_FOUND"));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new NotFoundException("CAR_NOT_FOUND"));

        Conversation conversation = conversationRepository
                .findByBuyerIdAndSellerIdAndCarId(request.getSenderId(), request.getReceiverId(), request.getCarId())
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setBuyer(sender);
                    newConversation.setSeller(receiver);
                    newConversation.setCar(car);
                    return conversationRepository.save(newConversation);
                });

        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(sender);
        message.setContent(request.getContent());
        messageRepository.save(message);

        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setConversationId(conversation.getId());
        response.setSenderId(sender.getId());
        response.setContent(message.getContent());
        response.setSentAt(message.getSentAt());
        response.setRead(false);

        messagingTemplate.convertAndSend(
                "/topic/conversation." + conversation.getId(),
                response);
    }

    public List<MessageResponse> getMessages(Long conversationId) {
        return messageRepository
                .findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .map(msg -> {
                    MessageResponse response = new MessageResponse();
                    response.setId(msg.getId());
                    response.setConversationId(conversationId);
                    response.setSenderId(msg.getSender().getId());
                    response.setContent(msg.getContent());
                    response.setSentAt(msg.getSentAt());
                    response.setRead(msg.isRead());
                    return response;
                })
                .toList();
    }
}