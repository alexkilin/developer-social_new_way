package com.javamentor.developer.social.platform.models.dto.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @ApiModelProperty(notes = "Дата рождения пользователя", example = "01.01.2000", position = 6)
    private LocalDate dateOfBirth;

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

    @ApiModelProperty(notes = "Должен содержать минимум 8 символов, 1 заглавную букву и 1 цифру",
            required = true, example = "Qwerty12", position = 3)
    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Поле password не должно быть null")
    @Pattern(groups = {OnCreate.class, OnUpdate.class}, regexp = "^(?=.*\\d)(?=.*[A-Z])[a-zA-Z0-9]+$",
            message = "Поле password должен содержать 1 цифру, 1 заглавную букву.")
    @Size(groups = OnCreate.class, min = 8, message = "Поле password должен быть не мение 8 символов.")
    private String password;

    @ApiModelProperty(notes = "Город пользователя", example = "Moscow", position = 11)
    private String city;

    @ApiModelProperty(notes = "Ссылка на сайт пользователя", example = "www.site.com", position = 9)
    private String linkSite;

    @ApiModelProperty(notes = "Профессия пользователя", example = "Plumber", position = 13)
    private String profession;

    @ApiModelProperty(notes = "Автоматически назначается при создании всем пользователям, явно указывать не нужно",
            example = "не указывать", hidden = true)
    @Null(groups = OnCreate.class, message = " 'role' автоматически назначается при создании всем пользователям, " +
            "явно указывать не нужно")
    @NotNull(groups = OnUpdate.class, message = "Поле roleName не должно быть null при обновлении")
    private String roleName;

    @ApiModelProperty(notes = "Статус пользователя, придуманный пользователем",
            example = "free", position = 12)
    private String status;

    @ApiModelProperty(notes = "Показатель активности на сайте",
            example = "Active", position = 14)
    @NotNull(groups = OnCreate.class, message = "Поле activeName не должно принимать null значение при создании")
    private String activeName;

    @ApiModelProperty(notes = "не обязательное поле", example = "Russian", hidden = true)
    private List<LanguageDto> languages;



}
