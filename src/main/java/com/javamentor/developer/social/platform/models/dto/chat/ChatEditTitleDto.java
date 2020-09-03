package com.javamentor.developer.social.platform.models.dto.chat;

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
public class ChatEditTitleDto {
    @ApiModelProperty(notes = "Id чата.")
    @NotNull(message = "Id must not be null")
    private Long id;

    @ApiModelProperty(notes = "Новое название чата")
    @NotBlank(message = "New title must not be null or blank")
    private String title;
}
