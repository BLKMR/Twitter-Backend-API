package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashtagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {

    List<HashtagDto> getAllTags();



    List<TweetResponseDto> getTagsLabel(String label);


}
