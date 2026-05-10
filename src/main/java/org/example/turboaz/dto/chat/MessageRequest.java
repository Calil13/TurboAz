package org.example.turboaz.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {
    private Long carId;
    private Long senderId;
    private Long receiverId;
    private String content;
}