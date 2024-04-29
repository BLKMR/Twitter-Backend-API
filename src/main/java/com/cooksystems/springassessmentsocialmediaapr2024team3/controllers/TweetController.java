package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;


import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRepostDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.web.bind.annotation.*;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {


    private final TweetService tweetService;

    @PostMapping
    public SimpleTweetResponseDto createTweet(@RequestBody TweetRequestDto newTweet) {
        return tweetService.createTweet(newTweet);
    }

    @PostMapping("/{id}/repost")
    public TweetRepostDto createRepost(@PathVariable Long id, @RequestBody CredentialsDto credentials){
        return tweetService.createRepost(id, credentials);
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Integer id) {
        return tweetService.getTweetById(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Integer id) {
        return tweetService.deleteTweetById(id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyToTweet(@RequestBody TweetRequestDto tweet, @PathVariable Integer id) {
        return tweetService.replyToTweet(tweet, id);
    }

    @PostMapping("/{id}/like")
    public void likeTweet(@RequestBody Credential credentials, @PathVariable Integer id) {
        tweetService.likeTweet(credentials, id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Integer id) {
        return tweetService.getReplies(id);
    }

    @GetMapping("/{id}/likes") 
    public List<UserResponseDto> getLikes(@PathVariable Integer id) {
        return tweetService.getLikes(id);
    }
}
