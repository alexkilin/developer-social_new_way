package com.javamentor.developer.social.platform.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserFriendDto {

    Long id;
    String fullName;
    String avatar;
    String education;
    String profession;
    String status;
}
