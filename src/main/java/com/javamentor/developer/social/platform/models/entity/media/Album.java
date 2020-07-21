package com.javamentor.developer.social.platform.models.entity.media;


import com.javamentor.developer.social.platform.models.entity.media.Media;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;
}
