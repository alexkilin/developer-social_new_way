package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@Api(value = "AuthenticationApi-v2", description = "Аутентификация")
public class AuthenticationController {

    private UserService userService;
    private SecurityHelper securityHelper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthenticationController (UserService userService, SecurityHelper securityHelper) {
        this.userService = userService;
        this.securityHelper = securityHelper;
    }

    @ApiOperation(value = "Получение jwt токена по email + password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Токен получен")})
    @GetMapping("/token")
    public ResponseEntity<String> generateToken(
            @ApiParam(value = "Mail и пароль")
            @RequestBody @Valid @NonNull UserAuthorizationDto userAuthorizationDto) {

        Optional<User> optUser = userService.getByEmail(userAuthorizationDto.getEmail());
        if (!optUser.isPresent()) {
            logger.info(String.format("Пользователя с таким email не существует"));
            return ResponseEntity.badRequest().body(String.format("User with this email does not exist"));
        }

        User user = optUser.get();
        if (!user.getPassword().equals(userAuthorizationDto.getPassword())) {
            logger.info(String.format("Неверный пароль"));
            return ResponseEntity.badRequest().body(String.format("Wrong password, try again"));
        }

        return ResponseEntity.ok(securityHelper.generateToken(user));
    }

    @ApiOperation(value = "Получение principal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Principal получен", response = PrincipalDto.class)})
    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        User user = securityHelper.getPrincipal();
        logger.info(String.format("Principal получен"));
        return ResponseEntity.ok(
                    PrincipalDto.builder()
                        .email(user.getEmail())
                        .name(user.getFirstName())
                        .surname(user.getLastName())
                        .avatarUrl(user.getAvatar())
                        .role(user.getRole().getName()).build());
    }
}
