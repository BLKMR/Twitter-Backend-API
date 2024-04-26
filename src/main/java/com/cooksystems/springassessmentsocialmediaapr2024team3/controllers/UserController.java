package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ProfileUpdateRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Profile;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.web.bind.annotation.*;

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


    @PatchMapping("/@{username}")
    public UserResponseDto updateProfile(@PathVariable String username, @RequestBody ProfileUpdateRequestDto updateRequest){
        return userService.updateProfile(username, updateRequest);

    }



}
