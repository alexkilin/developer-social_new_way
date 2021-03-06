package com.javamentor.developer.social.platform.models.dto.media.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumImageDto {

    private Long id;
    private String name;
    private String icon;
    private LocalDateTime persistDate;
    private LocalDateTime lastRedactionDate;

}
