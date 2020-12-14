package com.javamentor.developer.social.platform.webapp.controllers.security;

import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logout", produces = "application/json")
public class LogoutController {

    private SecurityHelper securityHelper;

    @Autowired
    public LogoutController(SecurityHelper securityHelper) {
        this.securityHelper = securityHelper;
    }

    @GetMapping
    public void makeLogout() {
        securityHelper.logout();
    }
}
