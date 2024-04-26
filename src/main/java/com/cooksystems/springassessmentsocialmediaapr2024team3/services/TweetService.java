package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;

import com.cooksystems.springassessmentsocialmediaapr2024team3.controllers.TweetController;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

public interface TweetService {


    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);

    TweetResponseDto getTweetById(Integer id);

    TweetResponseDto deleteTweetById(Integer id);

    TweetResponseDto replyToTweet(Credential credentials, Integer id);

}
