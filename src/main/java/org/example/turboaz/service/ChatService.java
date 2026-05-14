package org.example.turboaz.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.turboaz.dto.chat.ConversationResponse;
import org.example.turboaz.dto.chat.MessageRequest;
import org.example.turboaz.dto.chat.MessageResponse;
import org.example.turboaz.dto.chat.WebSocketMessageResponse;
import org.example.turboaz.entity.Conversation;
import org.example.turboaz.entity.Message;
import org.example.turboaz.entity.Users;
import org.example.turboaz.exception.AccessDeniedException;
import org.example.turboaz.exception.NotFoundException;
import org.example.turboaz.mapper.ChatMapper;
import org.example.turboaz.repository.CarRepository;
import org.example.turboaz.repository.ConversationRepository;
import org.example.turboaz.repository.MessageRepository;
import org.example.turboaz.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UsersRepository usersRepository;
    private final CarRepository carRepository;
    private final ChatMapper chatMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CHAT_KEY = "chat:conversation:";
    private static final int MAX_MESSAGES = 20;

    public void sendMessage(MessageRequest request) {

        var sender = usersRepository.findById(request.getSenderId())
                .orElseThrow(() -> new NotFoundException("SENDER_NOT_FOUND"));

        var receiver = usersRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundException("RECEIVER_NOT_FOUND"));

        var car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new NotFoundException("CAR_NOT_FOUND"));

        Conversation conversation = conversationRepository
                .findConversation(request.getSenderId(), request.getReceiverId(), request.getCarId())
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

        WebSocketMessageResponse webSocketResponse = chatMapper.toWebSocketDto(message);
        MessageResponse messageResponse = chatMapper.toDto(message);

        String key = CHAT_KEY + conversation.getId();
        redisTemplate.opsForList().rightPush(key, messageResponse);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);

        redisTemplate.opsForList().trim(key, -MAX_MESSAGES, -1);

        messagingTemplate.convertAndSend(
                "/topic/conversation." + conversation.getId(),
                webSocketResponse);
    }

    public List<MessageResponse> getMessages(Long conversationId) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> {
                    log.error("User not found for email: {}", currentEmail);
                    return new NotFoundException("USER_NOT_FOUND");
                });

        var conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("CONVERSATION_NOT_FOUND"));

        boolean isBuyer = conversation.getBuyer().getId().equals(user.getId());
        boolean isSeller = conversation.getSeller().getId().equals(user.getId());

        if (!isBuyer && !isSeller) {
            throw new AccessDeniedException("You do not have access to this conversation!");
        }

        String key = CHAT_KEY + conversationId;

        List<Object> cached = redisTemplate.opsForList().range(key, 0, -1);

        if (cached != null && !cached.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            return cached.stream()
                    .map(obj -> objectMapper.convertValue(obj, MessageResponse.class))
                    .toList();
        }

        List<MessageResponse> messages = messageRepository
                .findByConversationIdOrderBySentAtAsc(conversationId)
                .stream()
                .map(chatMapper::toDto)
                .toList();

        if (!messages.isEmpty()) {
            messages.forEach(msg -> redisTemplate.opsForList().rightPush(key, msg));
            redisTemplate.expire(key, 1, TimeUnit.HOURS);
        }

        return messages;
    }

    public Page<ConversationResponse> getMyConversations(Pageable pageable) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        var user = usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        var conversations = conversationRepository.findByBuyerIdOrSellerId(user.getId(), user.getId(), pageable);

        return conversations.map(conv -> {
            ConversationResponse response = chatMapper.toDto(conv);

            Users otherPerson = conv.getBuyer().getId().equals(user.getId())
                    ? conv.getSeller() : conv.getBuyer();

            response.setOtherPersonName(otherPerson.getName());

            return response;
        });
    }

    public WebSocketMessageResponse initConversation(Long buyerId, Long sellerId, Long carId) {
        var buyer = usersRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException("BUYER_NOT_FOUND"));
        var seller = usersRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("SELLER_NOT_FOUND"));
        var car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("CAR_NOT_FOUND"));

        var conversation = conversationRepository.findConversation(buyerId, sellerId, carId)
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

    public void deleteMessage(Long id) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        var message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MESSAGE_NOT_FOUND"));

        messageRepository.delete(message);
    }

    public void deleteConv(Long id) {
        String currentEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        usersRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        var conv = conversationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MESSAGE_NOT_FOUND"));

        conversationRepository.delete(conv);
    }
}