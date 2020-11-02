package com.javamentor.developer.social.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserUpdateInfoDto {
    @ApiModelProperty(notes = "Автоматически генерируемый ID пользователя. Не указывать при создании, " +
            "обязательно указывать при изменении учетной записи", position = 1, example = "null")
    @Null(groups = OnCreate.class, message = "Поле id должно принимать null значение при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле id не должно принимать null значение при обновлении")
    private Long userId;

    @ApiModelProperty(notes = "Имя пользователя, поле не должно быть пустым",
            required = true, example = "Иван", position = 4)
    @NotNull(groups = OnCreate.class, message = "Поле имя не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле имя не должно быть Null при обновлении")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле имя должно начинаться с буквы")
    private String firstName;

    @ApiModelProperty(notes = "Фамилия пользователя, поле не должна быть пустой",
            required = true, example = "Иванов", position = 5)
    @NotNull(groups = OnCreate.class, message = "Поле фамилия не должно быть Null при создании")
    @NotNull(groups = OnUpdate.class, message = "Поле фамилия не должно быть Null при обновлении")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "[а-яА-ЯёЁa-zA-Z]+.*$", message = "Поле фамилия должно начинаться с буквы")
    private String lastName;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @ApiModelProperty(notes = "Дата рождения пользователя", example = "01.01.2000", position = 6)
    private Date dateOfBirth;

    @ApiModelProperty(notes = "Образование пользователя", example = "Высшее техническое", position = 7)
    private String education;

    @ApiModelProperty(notes = "Любая информация о пользователе",
            example = "About me", position = 8)
    private String aboutMe;

    @ApiModelProperty(notes = "Картинка пользователя", example = "?", position = 10)
    private String avatar;

    @ApiModelProperty(notes = "Email должен быть корректным, смотрите пример",
            required = true, example = "email@email.com", position = 2)
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле Email не должно быть null")
    @Email(groups = {OnCreate.class, OnUpdate.class}, regexp = "^[a-zA-Z0-9]{1,}" + "((\\.|\\_|-{0,})[a-zA-Z0-9]{1,})*" + "@" + "[a-zA-Z0-9]{1,}" +
            "((\\.|\\_|-{0,1})[a-zA-Z0-9]{1,})*" + "\\.[a-zA-Z]{2,}$",
            message = "Email должен быть корректным")
    private String email;

    @ApiModelProperty(notes = "Город пользователя", example = "Moscow", position = 11)
    private String city;

    @ApiModelProperty(notes = "Ссылка на сайт пользователя", example = "www.site.com", position = 9)
    private String linkSite;

    @ApiModelProperty(notes = "Профессия пользователя", example = "Plumber", position = 13)
    private String profession;

}
