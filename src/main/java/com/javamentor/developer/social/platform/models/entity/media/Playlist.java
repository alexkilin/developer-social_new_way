package com.javamentor.developer.social.platform.models.entity.media;

import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(generator = "playlists_seq")
    @Column(name = "playlist_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String image;

    @Column(name = "persist_date", nullable = false, updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User ownerUser;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "playlist_has_audios", joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "audios_id"))
    private Set<Audios> playlistContent = new HashSet<>();

    public boolean addAudio(Audios audios) {
        return playlistContent.add(audios);
    }

    public boolean removeAudio(Audios audios) {
        return playlistContent.remove(audios);
    }
}
