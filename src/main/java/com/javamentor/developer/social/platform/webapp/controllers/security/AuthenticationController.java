package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@Api(value = "AuthenticationApi-v2", description = "Аутентификация")
public class AuthenticationController {

    private UserService userService;
    private SecurityHelper securityHelper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthenticationController( UserService userService , SecurityHelper securityHelper ) {
        this.userService = userService;
        this.securityHelper = securityHelper;
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
}
