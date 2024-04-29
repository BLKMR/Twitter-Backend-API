package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ContextDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashtagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRepostDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;

import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;

import com.cooksystems.springassessmentsocialmediaapr2024team3.controllers.TweetController;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

public interface TweetService {


    List<TweetResponseDto> getAllTweets();

    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);
    
    TweetRepostDto createRepost(Long id, CredentialsDto credentials);

    List<TweetResponseDto> getReposts(Long id);

    List<UserResponseDto> getMentions(Long id);

    List<HashtagDto> getTweetTags(Long id);

    ContextDto getContext(Long id);

    TweetResponseDto getTweetById(Integer id);

    TweetResponseDto deleteTweetById(Integer id);

    TweetResponseDto replyToTweet(TweetRequestDto tweet, Integer id);

    void likeTweet(Credential credentials, Integer id);

    List<TweetResponseDto> getReplies(Integer id);

    List<UserResponseDto> getLikes(Integer id);
}
