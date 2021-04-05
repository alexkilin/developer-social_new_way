package com.javamentor.developer.social.platform.models.entity.genre;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(generator = "genres_seq")
    private Long id;

    @Column
    private String title;
}