package com.javamentor.developer.social.platform.models.dto.chat;

import com.javamentor.developer.social.platform.models.dto.MediaDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageChatDto {

    private Boolean is_unread=true;

    private LocalDateTime lastRedactionDate;

    private LocalDateTime persistDate;

    private List<MediaDto> mediaDto;

    private String userSenderImage;

}
