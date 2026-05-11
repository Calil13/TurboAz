package org.example.turboaz.service;

import lombok.RequiredArgsConstructor;
import org.example.turboaz.dto.chat.MessageRequest;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.dto.chat.WebSocketMessageResponse;
import org.example.turboaz.entity.Conversation;
import org.example.turboaz.entity.Message;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.mapper.ChatMapper;
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
    private final ChatMapper chatMapper;

    public void sendMessage(MessageRequest request) {

        var sender = usersRepository.findById(request.getSenderId())
                .orElseThrow(() -> new NotFoundException("SENDER_NOT_FOUND"));

        var receiver = usersRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundException("RECEİVER_NOT_FOUND"));

        var car = carRepository.findById(request.getCarId())
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

        WebSocketMessageResponse response = chatMapper.toWebSocketDto(message);
        messagingTemplate.convertAndSend(
                "/topic/conversation." + conversation.getId(),
                response);
    }

    public List<MessageResponse> getMessages(Long conversationId) {
        return messageRepository
                .findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .map(chatMapper::toDto)
                .toList();
    }

    public WebSocketMessageResponse initConversation(Long buyerId, Long sellerId, Long carId) {
        var buyer = usersRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException("BUYER_NOT_FOUND"));
        var seller = usersRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("SELLER_NOT_FOUND"));
        var car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("CAR_NOT_FOUND"));

        var conversation = conversationRepository.findByBuyerIdAndSellerIdAndCarId(buyerId, sellerId, carId)
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setBuyer(buyer);
                    newConversation.setSeller(seller);
                    newConversation.setCar(car);
                    return conversationRepository.save(newConversation);
                });

        var response = new WebSocketMessageResponse();
        response.setConversationId(conversation.getId());
        return response;
    }
}