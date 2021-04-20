package com.javamentor.developer.social.platform.models.entity.post;

import com.javamentor.developer.social.platform.models.dto.tags.MostPopularTagsInPostsDto;
import lombok.*;

import javax.persistence.*;

@NamedNativeQuery(
        name = "MostPopularTagsInPosts",
        query = "select t.text as title, round((100.00 * count(*)) / (select count(*) from posts), 2) as rate " +
                "from post_tags pt join tags t on pt.tags_id = t.id " +
                "group by t.text " +
                "order by rate desc",
        resultSetMapping = "MostPopularTagsInPostsMapping"
)
@SqlResultSetMapping(
        name = "MostPopularTagsInPostsMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "title"),
                                @ColumnResult(name = "rate", type = double.class)
                        },
                        targetClass = MostPopularTagsInPostsDto.class
                )
        }
)
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
