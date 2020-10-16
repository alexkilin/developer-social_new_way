package com.javamentor.developer.social.platform.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class LanguageDto {

    @ApiModelProperty(notes = "Id языка")
    private Long id;

    @ApiModelProperty(notes = "название языка")
    private String name;

}
