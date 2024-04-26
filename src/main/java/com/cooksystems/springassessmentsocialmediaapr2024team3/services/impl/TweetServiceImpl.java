package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.controllers.TweetController;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashtagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final UserRepository userRepository;
    
    @Override
    public TweetResponseDto getTweetById(Integer id) {
        Tweet tweet = tweetRepository.getById(id);
        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet either does not exist, or has been deleted");
        }

        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public TweetResponseDto deleteTweetById(Integer id) {
        Tweet tweet = tweetRepository.getById(id);

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet either does not exist, or has been deleted");
        }

        tweetRepository.delete(tweet);

        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public TweetResponseDto replyToTweet(Credential credentials, Integer id) {
        User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if (activeUser == null){
            throw new NotFoundException("Account is not active or does not exist!");
        }

        if (!credentials.getUsername().equals(activeUser.getCredentials().getUsername()) || !credentials.getPassword().equals(activeUser.getCredentials().getPassword())) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        Tweet tweet = tweetRepository.getById(id);
    }
}
