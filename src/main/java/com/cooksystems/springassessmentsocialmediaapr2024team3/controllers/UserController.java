package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getUserTweets(@PathVariable String username){
        return userService.getUserTweets(username);
    }

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getUserMentionedTweets(@PathVariable String username){
        return userService.getUserMentionedTweets(username);
    }

    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable String username){
        return userService.getUserFeed(username);
    }


    @PatchMapping("/@{username}")
    public UserResponseDto updateProfile(@PathVariable String username, @RequestBody ProfileUpdateRequestDto updateRequest){
        return userService.updateProfile(username, updateRequest);

    }

    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getFollowing(@PathVariable String username){
        return userService.getFollowing(username);
    }

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@RequestBody Credentials credentials, @PathVariable String username) {
        return userService.deleteUser(credentials, username);
    }

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@RequestBody Credentials credentials, @PathVariable String username) {
        userService.unfollowUser(credentials, username);
    }    @PostMapping("/@{username}/follow")
    public ResponseEntity<ErrorDto> subscribeUser(@PathVariable String username, @RequestBody CredentialsDto credentials){
        try{
            userService.subscribeUser(username, credentials);
            return ResponseEntity.ok().build();
        }
        catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }



}
