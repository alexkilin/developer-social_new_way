package com.javamentor.developer.social.platform.models.entity.chat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(generator = "chats_seq")
    private Long id;

    private String title;

    private String image;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST},targetEntity = Message.class)
    @JoinTable(joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> messages;

    @Column(name = "persist_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;
}
