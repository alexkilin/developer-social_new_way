package com.javamentor.developer.social.platform.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageDTO {

    private Long id;

    private String url;

    private String description;

    private LocalDateTime persistDateTime;


}