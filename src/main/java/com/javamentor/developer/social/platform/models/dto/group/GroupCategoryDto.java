package com.javamentor.developer.social.platform.models.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class GroupCategoryDto {

    @ApiModelProperty(notes = "Уникальный идентификационный номер категории. Генерируется автоматически.")
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "Наименование категории. Не должно быть пустым.")
    private String category;
}
