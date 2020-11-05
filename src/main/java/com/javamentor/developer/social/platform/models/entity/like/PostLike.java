package com.javamentor.developer.social.platform.models.entity.like;


import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_like")
public class PostLike {

    public PostLike(User user) {
        like.setUser(user);
    }

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @MapsId
    private Like like = new Like(LikeType.POST);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @PrePersist
    private void prePersistFunction() {
        checkConstraints();
    }

    @PreUpdate
    private void preUpdateFunction() {
        checkConstraints();
    }

    private void checkConstraints() {
        if (this.like.getLikeType() != LikeType.POST) {
            throw new ApiRequestException("У экземпляра Like, связанного с PostLike, " +
                    "поле LikeType должно принимать значение LikeType.Post");

        }
    }
}
