package com.javamentor.developer.social.platform.models.dto.users;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
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
@Validated
public class UserAuthorizationDto {

    @ApiModelProperty(notes = "Email должен быть корректным, смотрите пример",
            required = true, example = "email@email.com")
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле Email не должно быть null")
    @Email(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email должен быть корректным")
    private String email;

    @ApiModelProperty(notes = "Должен содержать минимум 8 символов, 1 заглавную букву и 1 цифру",
            required = true, example = "Qwerty12", position = 1)
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не менее 8 символов.")
    private String password;
}
