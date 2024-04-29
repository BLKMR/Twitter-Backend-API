package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;

import java.util.List;

public interface TweetService {


    List<TweetResponseDto> getAllTweets();

    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);


    TweetRepostDto createRepost(Long id, CredentialsDto credentials);

    List<TweetResponseDto> getReposts(Long id);

    List<UserResponseDto> getMentions(Long id);

    List<HashtagDto> getTweetTags(Long id);

    ContextDto getContext(Long id);
}
