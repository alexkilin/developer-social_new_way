package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.service.impl.UserControllerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/user")
public class UserResourceController {

    private final UserControllerDtoService userService;

    @Autowired
    public UserResourceController(UserControllerDtoService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        userService.create(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/getUserFriends")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Long id) {
        List<UserDto> userFriends = userService.getUserFriendsById(id);
        return ResponseEntity.ok(userFriends);
    }
}
