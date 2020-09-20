package com.javamentor.developer.social.platform.models.entity.album;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Image.class, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "album_has_image", joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> imageSet = new HashSet<>();

    public boolean addImage(Image image) {
        return imageSet.add(image);
    }

    public boolean removeImage(Image image) {
        return imageSet.remove(image);
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
        if (this.album.getMediaType() != MediaType.IMAGE) {
            throw new ApiRequestException("У экземпляра Album, связанного с IMAGE, " +
                    "поле MediaType должно принимать значение MediaType.IMAGE");
        }
    }
}
