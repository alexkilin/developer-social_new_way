package com.javamentor.developer.social.platform.models.entity.post;

import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String title;

    @NotNull
    @Column(length = 1000)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastRedactionDate;

    @Column(name = "persist_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDate;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Media.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "post_media", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> media;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Repost> reposts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Bookmark> bookmarks;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "group_wal", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<PostComment> postComments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<UserTabs> userTabs;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Set<PostLike> postLikes;

}
