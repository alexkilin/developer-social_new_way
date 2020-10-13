package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class StatusDto {

    @ApiModelProperty(notes = "ID пользователя, добавившего статус", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "Текст статуса")
    private String status;
}
