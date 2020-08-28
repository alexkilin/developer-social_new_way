package com.javamentor.developer.social.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumImageDTO {

    private Long          id;
    private String        name;
    private String        icon;
    private LocalDateTime persistDate;
    private LocalDateTime lastRedactionDate;

}
