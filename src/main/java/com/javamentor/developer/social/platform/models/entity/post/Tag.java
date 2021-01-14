package com.javamentor.developer.social.platform.models.entity.post;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(generator = "tags_seq")
    private Long id;

    @Column
    private String text;

}
