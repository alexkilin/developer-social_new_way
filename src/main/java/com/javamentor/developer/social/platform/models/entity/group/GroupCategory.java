package com.javamentor.developer.social.platform.models.entity.group;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "group_category")
public class GroupCategory {

    @Id
    @GeneratedValue(generator = "group_category_seq")
    private Long id;

    @Column
    private String category;
}
