package com.github.oc12345.controller;

import com.github.oc12345.domain.User;
import com.github.oc12345.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/vicinity/{city}")
    public List<User> getUsersAroundCity(@PathVariable("city") String city, @RequestParam(value = "distance",
            defaultValue = "50") int distance) {
        return userService.findUsersInCityWithinDistance(city, distance);
    }

}
