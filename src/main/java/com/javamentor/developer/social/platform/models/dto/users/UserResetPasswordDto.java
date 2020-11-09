package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserResetPasswordDto {
    @ApiModelProperty(notes = "Должен содержать минимум 8 символов, 1 заглавную букву и 1 цифру",
            required = true, example = "Qwerty12")
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;
}
