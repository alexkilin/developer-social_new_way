package com.javamentor.developer.social.platform.models.entity.user;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(generator = "language_seq")
    private Long id;

    @Column
    private String name;

    @Transient
    @JsonIgnore
    @ManyToMany(mappedBy = "languages")
    private Set<User> users;
}
