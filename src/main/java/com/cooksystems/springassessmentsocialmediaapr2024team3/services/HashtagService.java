package com.cooksystems.springassessmentsocialmediaapr2024team3.services;
import java.util.List;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;

public interface HashTagService {


	List<HashTagDto> getAllTags();

	

	List<TweetResponseDto> getTagsLabel(String label);




	




}
