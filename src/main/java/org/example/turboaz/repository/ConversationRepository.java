package org.example.turboaz.repository;

import org.example.turboaz.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByBuyerIdAndSellerIdAndCarId(Long buyerId, Long sellerId, Long carId);
}