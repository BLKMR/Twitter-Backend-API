package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;

public interface TweetService {


    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);


    TweetRepostDto createRepost(Long id, CredentialsDto credentials);
}
