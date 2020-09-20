package com.javamentor.developer.social.platform.models.entity.media;

import com.google.common.base.Objects;
import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
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
@Table(name = "images")
public class Image {

    public Image(User user, String icon) {
        media.setUser(user);
        media.setUrl(icon);
    }

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Media media = new Media(MediaType.IMAGE);

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "imageSet")
    private Set<AlbumImage> album;

    @PreRemove
    private void preRemoveFunction() {
        album.forEach(a -> a.removeImage(this));
    }

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.media.getMediaType() != MediaType.IMAGE) {
            throw new ApiRequestException("У экземпляра Media, связанного с Image, " +
                    "поле mediaType должно принимать значение MediaType.Image");
        }
    }
}
