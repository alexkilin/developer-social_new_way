package com.javamentor.developer.social.platform.models.entity.album;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "album_video")
public class AlbumVideo {

    public AlbumVideo(String name, String icon) {
        album.setName(name);
        album.setIcon(icon);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Album album = new Album(MediaType.VIDEO);

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Videos.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "album_has_video", joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "videos_id"))
    private Set<Videos> videos;


    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.album.getMediaType() != MediaType.VIDEO) {
            throw new ApiRequestException("У экземпляра Album, связанного с VIDEO, " +
                    "поле MediaType должно принимать значение MediaType.VIDEO");
        }
    }

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public Set<Videos> getVideos() {
        return videos;
    }
}
