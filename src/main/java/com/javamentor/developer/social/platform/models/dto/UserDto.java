package com.javamentor.developer.social.platform.models.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto implements Serializable {

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

    private RoleDto role;

    private StatusDto status;

    private ActiveDto active;

}
