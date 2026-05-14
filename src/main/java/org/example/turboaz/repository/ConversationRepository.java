package org.example.turboaz.repository;

import org.example.turboaz.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE c.car.id = :carId AND ((c.buyer.id = :u1 AND c.seller.id = :u2) OR (c.buyer.id = :u2 AND c.seller.id = :u1))")
    Optional<Conversation> findConversation(@Param("u1") Long u1, @Param("u2") Long u2, @Param("carId") Long carId);

    Page<Conversation> findByBuyerIdOrSellerId(Long buyerId, Long sellerId, Pageable pageable);
}