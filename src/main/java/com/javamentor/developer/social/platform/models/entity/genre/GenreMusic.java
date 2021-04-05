package com.javamentor.developer.social.platform.models.entity.genre;

import com.javamentor.developer.social.platform.models.entity.media.Audios;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "music_genres")
public class GenreMusic {

    @Id
    @GeneratedValue(generator = "genres_music_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "audio_id")
    private Audios audios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreMusic userTabs = (GenreMusic) o;
        return Objects.equals(id, userTabs.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}