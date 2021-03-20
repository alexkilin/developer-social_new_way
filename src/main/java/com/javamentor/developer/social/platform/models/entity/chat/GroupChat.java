package com.javamentor.developer.social.platform.models.entity.chat;

import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiIgnore
@NoArgsConstructor
@Table(name = "group_chats")
public class GroupChat {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Chat chat;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class, mappedBy = "groupChats")
    private Set<User> users;

}
