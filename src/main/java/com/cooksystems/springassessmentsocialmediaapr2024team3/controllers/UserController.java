package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllActiveUsers();
    }

    @GetMapping("/@{username}")
    public UserResponseDto getActiveUserByUsername(@PathVariable String username) {
        return userService.getActiveUserByUsername(username);
    }


    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getUserFollowers(@PathVariable String username) {
        return userService.getUserFollowers(username);
    }


}
