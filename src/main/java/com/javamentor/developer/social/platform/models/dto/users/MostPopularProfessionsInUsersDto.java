package com.javamentor.developer.social.platform.models.dto.users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class MostPopularProfessionsInUsersDto {

    private final String title;
    private final double rate;

}
