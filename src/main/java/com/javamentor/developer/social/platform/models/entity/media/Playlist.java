package com.javamentor.developer.social.platform.models.entity.media;

import com.javamentor.developer.social.platform.models.entity.user.User;
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
@Table(name = "playlists")
public class Playlist {

    @Id
    @Column(name = "playlist_id")
    Long id;

    @Column
    String name;

    @Column
    String image;

    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    User ownerUser;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "playlist_has_audios", joinColumns = @JoinColumn(name = "audios_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    Set<Audios> playlistContent;
}
