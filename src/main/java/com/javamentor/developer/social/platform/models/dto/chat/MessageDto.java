package com.javamentor.developer.social.platform.models.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private String message;

    private Boolean is_unread=true;

    private LocalDateTime lastRedactionDate;

    private LocalDateTime persistDate;

    private Set<MediaDto> media;

    private UserDto userSender;

}
