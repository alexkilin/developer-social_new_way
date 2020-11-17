package com.javamentor.developer.social.platform.models.entity.like;

import com.javamentor.developer.social.platform.models.entity.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "`like`")
public class Like {

    public Like(LikeType likeType) {
        this.likeType =likeType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated
    @NotNull
    private LikeType likeType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

}

