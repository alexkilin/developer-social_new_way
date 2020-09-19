package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
public class VideoDto {

    @ApiModelProperty(notes = "Автоматически генерируемый ID. Не указывать при создании, " +
            "обязательно указывать при изменении объекта")
    @Null(groups = OnCreate.class, message = "'id' Must be null when creating VideoDto.class")
    @NotNull(groups = OnUpdate.class, message = "'id' Must not accept null values when updating VideoDto.class")
    private Long id;

    @ApiModelProperty(notes = "Адрес, на котором располагается видео объект",
            required = true, example = "/stuff/video_341.mpg")
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "'url' Must not be null when creating and updating VideoDto.class")
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "'text' Must not consist of spaces VideoDto.class")
    private String url;

    @ApiModelProperty(notes = "Название видео объекта", example = "Matrix")
    private String name;

    @ApiModelProperty(notes = "Адрес иконки (превью) видео объекта", example = "/icons/132_1.jpg")
    private String icon;

    @ApiModelProperty(notes = "Дата публикования медиа объекта, назначается автоматически при создании", hidden = true, example = "2020-09-14T23:24:17.900994")
    private LocalDateTime persistDateTime;
}
