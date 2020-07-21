package com.javamentor.developer.social.platform.models.entity.media;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "videos")
public class Videos {

    public Videos(User user, String icon) {
        media.setUser(user);
        media.setUrl(icon);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Media media = new Media(MediaType.VIDEO);

    @Column(name = "icon")
    private String icon;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.media.getMediaType() != MediaType.VIDEO) {
            throw new ApiRequestException("У экземпляра Media, связанного с Video, " +
                    "поле mediaType должно принимать значение MediaType.Video");
        }
    }
}
