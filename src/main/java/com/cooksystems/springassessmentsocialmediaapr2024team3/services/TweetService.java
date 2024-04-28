package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;

import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;

import com.cooksystems.springassessmentsocialmediaapr2024team3.controllers.TweetController;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

public interface TweetService {


    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);

    TweetResponseDto getTweetById(Integer id);

    TweetResponseDto deleteTweetById(Integer id);

    TweetResponseDto replyToTweet(TweetRequestDto tweet, Integer id);

    void likeTweet(Credential credentials, Integer id);

    List<TweetResponseDto> getReplies(Integer id);

    List<UserResponseDto> getLikes(Integer id);

}
