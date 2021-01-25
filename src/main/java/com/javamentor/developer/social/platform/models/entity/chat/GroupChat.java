package com.javamentor.developer.social.platform.models.entity.chat;

import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class, mappedBy = "groupChats", cascade = {CascadeType.PERSIST})
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST},targetEntity = Message.class)
    @JoinTable(joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> messages;
}
