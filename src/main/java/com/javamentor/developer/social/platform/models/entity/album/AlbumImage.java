package com.javamentor.developer.social.platform.models.entity.album;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "album_image")
public class AlbumImage {

    public AlbumImage(String name, String icon) {
        album.setName(name);
        album.setIcon(icon);
    }

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Album album = new Album(MediaType.IMAGE);


    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.album.getMediaType() != MediaType.IMAGE) {
            throw new ApiRequestException("У экземпляра Album, связанного с IMAGE, " +
                    "поле MediaType должно принимать значение MediaType.IMAGE");
        }
    }
}
