package com.javamentor.developer.social.platform.models.entity.comment;

import com.javamentor.developer.social.platform.exception.ApiRequestException;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_comment")
public class PostComment {

    public PostComment(String text, User user) {
        comment.setComment(text);
        comment.setUser(user);
    }

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private Comment comment = new Comment(CommentType.POST);


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
        if (this.comment.getCommentType() != CommentType.POST) {
            throw new ApiRequestException("У экземпляра Comment, связанного с Post, " +
                    "поле commentType должно принимать значение CommentType.Post");
        }
    }
}
