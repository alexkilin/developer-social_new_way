package com.javamentor.developer.social.platform.models.entity.chat;

import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column(name = "is_unread", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean is_unread=true;

    @Column(name = "persist_date")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastRedactionDate;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Media.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "media_messages", joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> media;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private User userSender;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Chat.class, cascade = {CascadeType.PERSIST})
    private Chat chat;
}
