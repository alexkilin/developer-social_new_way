package com.javamentor.developer.social.platform.models.dto;

import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class UserRegisterDto {
    @ApiModelProperty(notes = "Имя пользователя, поле не должно быть пустым",
            required = true, example = "Иван", position = 2)
    @NotNull(groups = OnCreate.class, message = "Поле имя не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле имя не должно быть Null при обновлении")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле имя должно начинаться с буквы")
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя, поле не должна быть пустой",
            required = true, example = "Иванов", position = 3)
    @NotNull(groups = OnCreate.class, message = "Поле фамилия не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле фамилия не должно быть Null при обновлении")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле фамилия должно начинаться с буквы")
    private String lastName;

    @ApiModelProperty(notes = "Email должен быть корректным, смотрите пример",
            required = true, example = "email@email.com", position = 0)
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
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;
}
