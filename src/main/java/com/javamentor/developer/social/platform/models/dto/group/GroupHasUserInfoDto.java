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
public class GroupHasUserInfoDto {

    @ApiModelProperty(notes = "Уникальный идентификационный номер группы. Генерируется автоматически.")
    private Long id;

    @ApiModelProperty(notes = "Логическая переменная. Есть ли user в данной группе.")
    private boolean groupHasUser;
}
