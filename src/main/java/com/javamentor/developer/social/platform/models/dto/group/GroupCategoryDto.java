package com.javamentor.developer.social.platform.models.dto.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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
    @ApiModelProperty(notes = "Наименование категории группы. Не должно быть пустым.")
    private String name;
}
