package com.javamentor.developer.social.platform.models.dto;

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
public class MediaPostDto {
    @ApiModelProperty(notes = "Айди медиа", example = "1")
    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private Long id;

    @ApiModelProperty(notes = "Айди пользователя, загрузившего медиа", example = "1")
    @NotNull
    private Long userId;

    @ApiModelProperty(notes = "Вид медиаконтента", example = "IMAGE")
    @NotNull
    private String mediaType;

    @ApiModelProperty(notes = "Ссылка на контент")
    @NotNull
    private String url;

    @ApiModelProperty(notes = "Id поста")
    private Long postId;


}
