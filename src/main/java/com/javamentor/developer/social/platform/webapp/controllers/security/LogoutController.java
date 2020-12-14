package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logout", produces = "application/json")
@Api(value = "LogoutApi-v2", description = "Logout")
public class LogoutController {

    private SecurityHelper securityHelper;

    @Autowired
    public LogoutController(SecurityHelper securityHelper) {
        this.securityHelper = securityHelper;
    }

    @ApiOperation(value = "Logout")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logout success", response = String.class)})
    @GetMapping
    public void makeLogout() {
        securityHelper.logout();
    }
}
