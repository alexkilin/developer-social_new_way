package com.javamentor.developer.social.platform.models.entity.chat;

import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Media.class, cascade = {CascadeType.MERGE})
    @JoinTable(name = "media_messages", joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> media;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class,cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id",nullable = false)
    private User userSender;

}
