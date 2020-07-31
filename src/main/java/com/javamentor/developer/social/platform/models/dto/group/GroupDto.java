package com.javamentor.developer.social.platform.models.dto.group;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class GroupDto {

    @ApiModelProperty(notes = "Уникальный идентификационный номер группы. Генерируется автоматически.")
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "Наименование группы. Не должно быть пустым.")
    private String name;

    @NonNull
    @ApiModelProperty(notes = "Дата последнего редактирования группы. Не должно быть пустым.")
    private LocalDateTime lastRedactionDate;

    @NonNull
    @ApiModelProperty(notes = "Дата создания группы. Не должно быть пустым.")
    private LocalDateTime persistDate;

    @NonNull
    @ApiModelProperty(notes = "Ссылка на сайт. Не должно быть пустым.")
    private String linkSite;

    @NonNull
    @ApiModelProperty(notes = "Категория группы. Не должно быть пустым.")
    private GroupCategoryDto groupCategory;

    @NonNull
    @ApiModelProperty(notes = "Посты группы. Не должно быть пустым.")
    private Set<PostDto> posts;
}
