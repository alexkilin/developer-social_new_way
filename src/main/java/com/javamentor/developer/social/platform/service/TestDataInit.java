package com.javamentor.developer.social.platform.service;

import com.javamentor.developer.social.platform.service.abstracts.model.user.RoleService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * В этом классе необходимо выполнить инициализацию.
 * И заполнить базу данных случайными значениями.
 * Например:
 * - вызвать все методы по созданию пользователей и ролей
 *  createUser
 *  createChat - передать пользователя
 *  createRole - передать пользоваться
 *  и тд.
 */
public class TestDataInit {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

}
