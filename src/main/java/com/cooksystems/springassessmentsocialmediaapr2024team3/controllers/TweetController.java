package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import org.springframework.web.bind.annotation.*;

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


}
