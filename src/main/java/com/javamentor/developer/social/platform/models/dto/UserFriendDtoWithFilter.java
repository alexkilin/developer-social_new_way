package com.javamentor.developer.social.platform.models.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserFriendDtoWithFilter {

    Long id;
    String fullName;
    String avatar;
    String education;
    String profession;
    String status;
    Date dateOfBirth;
}