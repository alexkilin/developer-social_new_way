package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class MediaPostDto {

    @ApiModelProperty(notes = "Айди пользователя, загрузившего медиа")
    @NotNull
    private Long userId;

    @ApiModelProperty(notes = "Вид медиаконтента")
    @NotNull
    private String mediaType;

    @ApiModelProperty(notes = "Ссылка на контент")
    @NotNull
    private String url;
}
