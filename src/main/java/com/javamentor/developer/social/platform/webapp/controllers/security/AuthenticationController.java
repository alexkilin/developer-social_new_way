package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.dto.users.UserRegisterDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.security.util.JwtUtil;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.abstracts.util.VerificationEmailService;
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
@Api(value = "AuthenticationApi-v2", description = "????????????????????????????")
public class AuthenticationController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final SecurityHelper securityHelper;
    private final JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final VerificationEmailService verificationEmailService;

    @Autowired
    public AuthenticationController(UserService userService, SecurityHelper securityHelper, JwtUtil jwtUtil,
                                    UserConverter userConverter, VerificationEmailService verificationEmailService) {
        this.userService = userService;
        this.securityHelper = securityHelper;
        this.jwtUtil = jwtUtil;
        this.userConverter = userConverter;
        this.verificationEmailService = verificationEmailService;
    }


    @ApiOperation(value = "?????????????????? principal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Principal ??????????????", response = PrincipalDto.class)})
    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipal() {
        User user = securityHelper.getPrincipal();
        logger.info(String.format("Principal ??????????????"));
        return ResponseEntity.ok(
                PrincipalDto.builder()
                        .id(user.getUserId())
                        .email(user.getEmail())
                        .name(user.getFirstName())
                        .surname(user.getLastName())
                        .avatarUrl(user.getAvatar())
                        .role(user.getRole().getName()).build());
    }

    @ApiOperation(value = "?????????????????? jwt ???????????? ???? email + password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "?????????? ??????????????")})
    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken( @ApiParam(value = "Mail ?? ????????????")
                                                        @RequestBody @Valid @NonNull UserAuthorizationDto userAuthorizationDto ) {

        Optional<User> optUser = userService.getByEmail(userAuthorizationDto.getEmail());
        if(!optUser.isPresent()) {
            logger.info(String.format("???????????????????????? ?? ?????????? email ???? ????????????????????"));
            return ResponseEntity.badRequest().body(String.format("User with this email does not exist"));
        }

        UserDetails userDetails = optUser.get();
        if(!userDetails.getPassword().equals(userAuthorizationDto.getPassword())) {
            logger.info(String.format("???????????????? ????????????"));
            return ResponseEntity.badRequest().body(String.format("Wrong password, try again"));
        }

        final String jwt = securityHelper.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @ApiOperation(value = "?????????????????????? ????????????????????????")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "???????????????????????? ????????????, ???? ????. ?????????? ???????????????????????? ???????????????????? ???????????? ?????? ?????????????????????????? ??????????????????????", response = UserRegisterDto.class),
            @ApiResponse(code = 400, message = "???????????????????????? ?? ???????????? email ????????????????????. Email ???????????? ???????? ????????????????????", response = String.class),
            @ApiResponse(code = 500, message = "?????????????????? ???????????? ?? ???????????????? ????????????????????????/???????????????? ????. ???????????? ?????? ?????????????????????????? ??????????????????????", response = String.class)
    })
    @PostMapping("/reg")
    @Validated(OnCreate.class)
    public ResponseEntity<?> registerUser(@ApiParam(value = "???????????? ???????????????????????? ????????????????????????") @RequestBody @Valid @NotNull UserRegisterDto userRegisterDto) {
        User user = userService.getByEmail(userRegisterDto.getEmail()).orElseGet(() -> userConverter.toEntity(userRegisterDto));
        if (user.isEnabled()) {
            logger.info(String.format("???????????????????????? ?? email: %s ?????? ???????????????????? ?? ??????????????????????????????", user.getEmail()));
            return ResponseEntity.badRequest().body(String.format("User with email '%s' already registered. Email should be unique", user.getEmail()));
        }
        if (user.getUserId() == null) {
            userService.create(user);
            logger.info(String.format("???????????????????????? ?? email: %s ???????????????? ?? ???????? ????????????", user.getEmail()));
        } else {
            Long id = user.getUserId();
            user = userConverter.toEntity(userRegisterDto);
            user.setUserId(id);
            userService.update(user);
            logger.info(String.format("?????????????????????????????? ???????????? ???????????????????????? ?? ????. ???????????? '%s' ??????????????????", user.getEmail()));
        }
        generateAndSendVerificationEmail(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userConverter.toDto(user));
    }

    private void generateAndSendVerificationEmail(User user) {
        String verificationToken = jwtUtil.createEmailVerificationToken(user.getUsername());
        logger.info(String.format("?????? ?????????????????????????? ?????????????????????? ?????? ???????????????????????? c ????. ???????????? %s ????????????????????????", user.getEmail()));
        verificationEmailService.sendEmail(user.getEmail(), verificationToken);
        logger.info(String.format("???? ?????????? ????. ?????????? ???????????????????????? '%s' ???????????????????? ???????????? ?????????????????????? ??????????", user.getEmail()));
    }

    @ApiOperation(value = "?????????????????????????? ???????????? ????. ?????????? ?????? ??????????????????????")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "???????????????? ?????????? ???????????????????????? ??????????????????????", response = UserRegisterDto.class),
            @ApiResponse(code = 400, message = "???????????? ?????????????????????????? ?????????????????? ???????????? ????????????????????????", response = String.class),
            @ApiResponse(code = 406, message = "?????? ?????????????????????? ??????????????????", response = String.class),
            @ApiResponse(code = 408, message = "???????????? ???????????????? ???????? ?????????????????????? ???????????????????????? ??????????", response = String.class),
            @ApiResponse(code = 409, message = "???????????????? ?????????? ???????????????????????? ?????? ?????????????????????? ??????????", response = String.class)
    })
    @PostMapping("/reg/confirm")
    public ResponseEntity<?> verifyUserEmail(
            @ApiParam(value = "?????? ??????????????????????????, ?????????????????? ???????????????????????? ???? ????. ?????????? ?? ???????? ?????????????????? url")
            @RequestBody @NotNull String token) {

        String email;
        try {
            jwtUtil.extractExpiration(token);
            email = jwtUtil.extractUsername(token);
        } catch (ExpiredJwtException ex) {
            logger.info(String.format("?? ?????????????????????? ???????? ?????????????????????? '%s' ?????????? ???????? ????????????????", token));
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Registration code is outdated");
        } catch (SignatureException | MalformedJwtException ex) {
            logger.info(String.format("???????????????????? ?????? ?????????????????????? '%s' ??????????????????. ?????????????????? ???? ???????????? ['%s']", token, ex.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Registration code is corrupted");
        }

        Optional<User> optUser = userService.getByEmailEagerlyForDtoConversion(email);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return user.isEnabled() ? userAlreadyEnabled(user) : userEnabled(user);
        }

        logger.info(String.format("???????????????????????? ?? ????. ???????????? '%s' ???? ????????????", email));
        return ResponseEntity.badRequest().body(String.format("No matching user record found for verification code '%s'", token));
    }

    private ResponseEntity<Object> userAlreadyEnabled(User user) {
        logger.info(String.format("?????????????? ???????????? ???????????????????????? ?? ????. ???????????? '%s' ???????? ???????????????????????? ??????????", user.getEmail()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User have been activated earlier");
    }

    private ResponseEntity<Object> userEnabled(User user) {
        user.setIsEnable(true);
        userService.update(user);
        logger.info(String.format("?????????????? ???????????? ???????????????????????? ?? ????. ???????????? '%s' ????????????????????????", user.getEmail()));
        return ResponseEntity.ok(userConverter.toDto(user));
    }

}