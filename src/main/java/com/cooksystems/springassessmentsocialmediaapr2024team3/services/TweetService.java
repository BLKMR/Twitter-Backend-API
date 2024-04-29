package com.cooksystems.springassessmentsocialmediaapr2024team3.services;


import java.util.List;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ContextDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashtagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	

	List<TweetResponseDto> getReposts(Long id);



	List<UserResponseDto> getMentions(Long id);



	List<HashtagDto> getTweetTags(Long id);



	ContextDto getContext(Long id);


    SimpleTweetResponseDto createTweet(TweetRequestDto newTweet);




}
