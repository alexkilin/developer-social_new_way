package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageCreateDto {

    @NotBlank(message = "Url can't be blank")
    @ApiModelProperty(notes = "Url изображения")
    private String url;

    @NotNull(message = "Description can't be null")
    @ApiModelProperty(notes = "Описание")
    private String description;

}