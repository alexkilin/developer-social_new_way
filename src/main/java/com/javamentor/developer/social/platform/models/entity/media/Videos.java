package com.javamentor.developer.social.platform.models.entity.media;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "videos")
public class Videos {

    public Videos(User user, String icon, String name) {
        media.setUser(user);
        media.setUrl(icon);
        this.name = name;
    }

    public Videos(User user, String icon) {
        media.setUser(user);
        media.setUrl(icon);
    }

    public Videos(User user, String icon, String name, String author) {
        media.setUser(user);
        media.setUrl(icon);
        this.name = name;
        this.author = author;
    }

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @MapsId
    private Media media = new Media(MediaType.VIDEO);

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "author")
    private String author;

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
