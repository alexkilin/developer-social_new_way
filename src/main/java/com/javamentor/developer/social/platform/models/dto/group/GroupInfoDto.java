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
public class GroupInfoDto {

    @ApiModelProperty(notes = "Уникальный идентификационный номер группы. Генерируется автоматически.")
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "Наименование группы. Не должно быть пустым.")
    private String name;

    @NonNull
    @ApiModelProperty(notes = "Категория группы. Не должно быть пустым.")
    private String groupCategory;

    @NonNull
    @ApiModelProperty(notes = "Количество подписчиков группы. Не должно быть пустым.")
    private Long subscribers;
}
