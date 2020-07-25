package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TestDataInitService {


    private UserService userService;

    @Autowired
    public TestDataInitService(UserService userService){
        this.userService = userService;
    }

    public void createEntity(){
        createUser();
    }

    private void createUser(){
        User user = User.builder()
                .userId(11l)
                .aboutMe("New User")
                .active(null)
                .avatar("New avatar")
                .city("SPb")
                .dateOfBirth(null)
                .education("the best")
                .email("user@user.email")
                .firstName("Mark")
                .id_enable(true)
                .languages(null)
                .lastName("Cucerberg")
                .lastRedactionDate(LocalDateTime.MAX)
                .linkSite("www.mysite.ru")
                .messages(null)
                .password("mepassword")
                .persistDate(LocalDateTime.MAX)
                .posts(null)
                .role(null)
                .status(null)
                .build();
        userService.create(user);
    }
}
