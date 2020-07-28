package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AudioDto {

    @ApiModelProperty(notes = "Автоматически генерируемый ID. Не указывать при создании, " +
            "обязательно указывать при изменении объекта")
    @Null(groups = OnCreate.class, message = "'id' Must be null when creating AudioDto.class")
    @NotNull(groups = OnUpdate.class, message = "'id' Must not accept null values when updating AudioDto.class")
    private Long id;

    @ApiModelProperty(notes = "Адрес, на котором располагается аудио объект",
            required = true)
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "'url' Must not be null when creating and updating AudioDto.class")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "'text' Must not consist of spaces AudioDto.class")
    private String url;

    @ApiModelProperty(notes = "Адрес иконки (превью) аудио объекта")  //тут не обязательно может быть обложка. Если ее нет, то должна быть установленна какаято стандартная.
    private String icon;                                             // этим занимаются фронты или мы должны сделать если нал, то тогда такой то адрес АНАЛОГИЧНО С ИМЕНЕМ И АВТОРОМ Альбомом

    @ApiModelProperty(notes = "Название аудио объекта")
    private String name;

    @ApiModelProperty(notes = "Автор аудио объекта")
    private String author;

    //@ApiModelProperty(notes = "Альбом аудио объекта") // судя по всему нужно добавлять еще эту строчку, ибо фронты по любому захотят получать альбом, к которому относится песня
    //private String album;

    @ApiModelProperty(notes = "Дата публикования медиа объекта, назначается автоматически при создании")
    private LocalDateTime persistDateTime;
}
