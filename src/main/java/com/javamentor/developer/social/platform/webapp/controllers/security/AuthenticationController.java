package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.dto.users.UserRegisterDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.JwtUtil;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.UserConverter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@Api(value = "AuthenticationApi-v2", description = "Аутентификация")
public class AuthenticationController {

    private UserService userService;
    private UserConverter userConverter;
    private SecurityHelper securityHelper;
    private final JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthenticationController( UserService userService, SecurityHelper securityHelper, JwtUtil jwtUtil,
                                     UserConverter userConverter) {
        this.userService = userService;
        this.securityHelper = securityHelper;
        this.jwtUtil = jwtUtil;
        this.userConverter = userConverter;
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
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .name(user.getFirstName())
                        .surname(user.getLastName())
                        .avatarUrl(user.getAvatar())
                        .role(user.getRole().getName()).build());
    }

    @ApiOperation(value = "Получение jwt токена по email + password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Токен получен")})
    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken( @ApiParam(value = "Mail и пароль")
                                                        @RequestBody @Valid @NonNull UserAuthorizationDto userAuthorizationDto ) {

        Optional<User> optUser = userService.getByEmail(userAuthorizationDto.getEmail());
        if(!optUser.isPresent()) {
            logger.info(String.format("Пользователя с таким email не существует"));
            return ResponseEntity.badRequest().body(String.format("User with this email does not exist"));
        }

        UserDetails userDetails = optUser.get();
        if(!userDetails.getPassword().equals(userAuthorizationDto.getPassword())) {
            logger.info(String.format("Неверный пароль"));
            return ResponseEntity.badRequest().body(String.format("Wrong password, try again"));
        }

        final String jwt = securityHelper.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @ApiOperation(value = "Подтверждение адреса эл. почты при регистрации")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Почтовый адрес пользователя подтвержден", response = UserRegisterDto.class),
            @ApiResponse(code = 400, message = "Ошибка подтверждения почтового адреса пользователя", response = String.class),
            @ApiResponse(code = 406, message = "Код регистрации поврежден", response = String.class),
            @ApiResponse(code = 408, message = "Период действия кода регистрации пользователя истек", response = String.class),
            @ApiResponse(code = 409, message = "Почтовый адрес пользователь был подтвержден ранее", response = String.class)
    })
    @PostMapping("/reg")
    public ResponseEntity<?> verifyUserEmail(
            @ApiParam(value = "Код подтверждения, высланный пользователю на эл. почту в виде параметра url")
            @RequestBody @NotNull String token) {

        String email;
        try {
            jwtUtil.extractExpiration(token);
            email = jwtUtil.extractUsername(token);
        } catch (ExpiredJwtException ex) {
            logger.info(String.format("У полученного кода регистрации '%s' вышел срок действия", token));
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Registration code is outdated");
        } catch (SignatureException | MalformedJwtException ex) {
            logger.info(String.format("Полученный код регистрации '%s' поврежден. Сообщение об ошибке ['%s']", token, ex.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Registration code is corrupted");
        }

        Optional<User> optUser = userService.getByEmail(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return user.isEnabled() ? userAlreadyEnabled(user) : userEnabled(user);
        }

        logger.info(String.format("Пользователь с эл. почтой '%s' не найден", email));
        return ResponseEntity.badRequest().body(String.format("No matching user record found for verification code '%s'", token));
    }

    private ResponseEntity<Object> userAlreadyEnabled(User user) {
        logger.info(String.format("Учетная запись пользователя с эл. почтой '%s' была активирована ранее", user.getEmail()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User have been activated earlier");
    }

    private ResponseEntity<Object> userEnabled(User user) {
        user.setIsEnable(true);
        userService.update(user);
        logger.info(String.format("Учетная запись пользователя с эл. почтой '%s' активирована", user.getEmail()));
        return ResponseEntity.ok(userConverter.toDto(user));
    }

}