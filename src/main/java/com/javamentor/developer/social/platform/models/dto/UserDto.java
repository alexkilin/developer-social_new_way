package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Language;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserDto implements Serializable {

    private Long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date dateOfBirth;

    private String education;

    private String aboutMe;


    private String avatar;

    private String email;

    private String password;


    private LocalDateTime persistDate;


    private LocalDateTime lastRedactionDate;


    private Boolean id_enable = true;


    private String city;


    private String linkSite;


    private Role role;


    private Status status;


    private Active active;


    private Set<Language> languages;


    private Set<Message> messages;


    private Set<Post> posts;
}
