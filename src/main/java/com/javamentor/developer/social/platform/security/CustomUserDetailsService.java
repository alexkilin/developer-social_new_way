package com.javamentor.developer.social.platform.security;

import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("custom")
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService (UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userService.getByEmailWithRole(userName).orElseThrow(() ->
                new UsernameNotFoundException("Unknown user: " + userName));
    }
}
