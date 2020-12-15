package com.javamentor.developer.social.platform.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrincipalDto {

    private String email;
    private String name;
    private String surname;
    private String avatarUrl;
    private String role;
}
