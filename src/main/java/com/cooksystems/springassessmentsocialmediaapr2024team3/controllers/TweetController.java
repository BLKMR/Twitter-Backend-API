package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ContextDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

	
	

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }
    
    @GetMapping("/{id}/reposts") 
    public List<TweetResponseDto> getReposts(@PathVariable ("id") Long id) {
        return tweetService.getReposts(id);
    }
    
    
    @GetMapping("/{id}/mentions") 
    public List<UserResponseDto> getMentions(@PathVariable ("id") Long id) {
        return tweetService.getMentions(id);
    }
    
    @GetMapping("{id}/tags")
    public List<HashTagDto> getTweetTags(@PathVariable ("id") Long id) {
        return tweetService.getTweetTags(id);
    }
    
    
    @GetMapping("{id}/context")
    public ContextDto getContext(@PathVariable ("id") Long id) {
        return tweetService.getContext(id);
    }
    
    
    
    

  
    
}
