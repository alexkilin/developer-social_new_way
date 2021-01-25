package com.javamentor.developer.social.platform.models.entity.chat;

import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "single_chat")
public class SingleChat {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST}, targetEntity = User.class)
    @JoinColumn(name = "user_one_id")
    private User userOne;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, targetEntity = User.class)
    @JoinColumn(name = "user_two_id")
    private User userTwo;

    private boolean deletedForUserOne;

    private boolean deletedForUserTwo;

}
