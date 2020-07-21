package com.javamentor.developer.social.platform.models.entity.user;

import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;


    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    @NotNull
    private Date date_of_birth;

    @Column(name = "education")
    private String education;

    @Column
    private String about_me;


    @Column(name = "image")
    private String avatarka;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persist_date;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime last_redaction_date;


    @Column(name = "is_enable", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean id_enable = true;

    @Column(name = "city")
    private String city;

    @Column(name = "link_site")
    private String link_site;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Status.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Active.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "active_id", nullable = false)
    private Active active;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Language.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "user_languages", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Message.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "stared_messages", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id"))
    private Set<Message> messages;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Post.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "bookMarks", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> posts;
}
