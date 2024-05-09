package org.example.cmpe202_final.controller.user;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.service.user.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> fetchAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public Optional<User> fetchById(@PathVariable("userId") String userId){
        return userService.findById(userId);
    }

    @PostMapping
    public User updateCourse(@RequestBody User user){
        return userService.postUser(user);
    }

}
