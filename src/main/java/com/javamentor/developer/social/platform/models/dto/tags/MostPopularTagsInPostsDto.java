package com.javamentor.developer.social.platform.models.dto.tags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class MostPopularTagsInPostsDto {

    private final String title;
    private final double rate;
}