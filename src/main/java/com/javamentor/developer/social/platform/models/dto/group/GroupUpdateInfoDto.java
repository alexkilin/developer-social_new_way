package com.javamentor.developer.social.platform.models.dto.group;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class GroupUpdateInfoDto {

    @ApiModelProperty(notes = "Уникальный идентификационный номер группы. Генерируется автоматически.")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле id не должно принимать null значение при обновлении")
    private Long id;

    @ApiModelProperty(notes = "Наименование группы. Не должно быть пустым.")
    @NotNull(groups = OnCreate.class, message = "Поле name не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле name не должно быть Null при обновлении")
    private String name;

    @ApiModelProperty(notes = "Ссылка на сайт. Не должно быть пустым.")
    @NotNull(groups = OnCreate.class, message = "Поле linkSite не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле linkSite не должно быть Null при обновлении")
    private String linkSite;

    @ApiModelProperty(notes = "Категория группы. Не должно быть пустым.")
    @NotNull(groups = OnCreate.class, message = "Поле groupCategory не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле groupCategory не должно быть Null при обновлении")
    private String groupCategory;

    @ApiModelProperty(notes = "Описание группы.")
    private String description;

    @ApiModelProperty(notes = "Адрес аватарки группы.")
    private String addressImageGroup;

}
