package com.javamentor.developer.social.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String education;

    private String aboutMe;

    private String avatar;

    private String email;

    private String password;

    private LocalDateTime persistDate;

    private LocalDateTime lastRedactionDate;

    private String city;

    private String linkSite;

    private Role role;

    private Status status;

    private Active active;

    private Set<Language> languages;

    private Set<Message> messages;

    private Set<Post> posts;

}
