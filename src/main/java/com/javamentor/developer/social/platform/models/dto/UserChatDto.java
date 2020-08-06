package com.javamentor.developer.social.platform.models.dto;


import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChatDto {
    private List<ChatDto> chatDto;
    private String firstName;
    private String avatar;
    private String lastName;
}
