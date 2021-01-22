package com.javamentor.developer.social.platform.models.entity.album;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "album_audios")
public class AlbumAudios {

    public AlbumAudios(String name, String icon) {
        album.setName(name);
        album.setIcon(icon);
    }

    public AlbumAudios(String name, String icon, User user) {
        album.setName(name);
        album.setIcon(icon);
        album.setUserOwnerId(user);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Album album = new Album(MediaType.AUDIO);

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Audios.class)
    @JoinTable(name = "album_has_audio", joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "audios_id"))
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
        if (this.album.getMediaType() != MediaType.AUDIO) {
            throw new ApiRequestException("У экземпляра Album, связанного с Audio, " +
                    "поле mediaType должно принимать значение MediaType.Audio");
        }
    }

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public Set<Audios> getAudios() {
        return audios;
    }

    public void setAlbumName(String name) {
        album.setName(name);
    }

    public void setAlbumIcon(String icon) {
        album.setIcon(icon);
    }

    public void setAlbumUser(User albumUser) {
        album.setUserOwnerId(albumUser);
    }

}
