package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

<<<<<<< HEAD
import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.web.bind.annotation.*;
>>>>>>> develop

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

<<<<<<< HEAD
    private final TweetService tweetService;

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Integer id) {
        return tweetService.getTweetById(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Integer id) {
        return tweetService.deleteTweetById(id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyToTweet(@RequestBody Credential credentials, @PathVariable Integer id) {
        return tweetService.replyToTweet(credentials, id);
    }
=======

    private final TweetService tweetService;

    @PostMapping
    public SimpleTweetResponseDto createTweet(@RequestBody TweetRequestDto newTweet) {
        return tweetService.createTweet(newTweet);
    }

>>>>>>> develop

}
