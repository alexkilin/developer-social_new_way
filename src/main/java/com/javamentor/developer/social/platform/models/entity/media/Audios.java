package com.javamentor.developer.social.platform.models.entity.media;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "audios")
public class Audios {

    public Audios(User user, String icon, String author, String name) {
        media.setUser(user);
        media.setUrl(icon);
        this.author=author;
        this.name=name;
    }

    public Audios(User user, String icon) {
        media.setUser(user);
        media.setUrl(icon);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Media media = new Media(MediaType.AUDIO);

    @Column(name = "icon")
    private String icon;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "album")
    private String album;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Audios.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "users_audios_collections", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "audio_id"))
    private Set<Audios> audios;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.media.getMediaType() != MediaType.AUDIO) {
            throw new ApiRequestException("У экземпляра Media, связанного с Audio, " +
                    "поле mediaType должно принимать значение MediaType.Audio");
        }
    }
}
