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
@Table(name = "active")
public class Active {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Transient
    @JsonIgnore
    @OneToMany(mappedBy = "active")
    private Set<User> users;

    public Active(String name) {
        this.name = name;
    }
}
