package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.HashTag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.HashtagMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashTagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.HashTagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class HashTagServiceImpl implements HashTagService {
	
	




	@Autowired
	HashTagRepository hashtagRepository;

	@Autowired
	HashtagMapper hashtagMapper;

	@Autowired
	TweetRepository tweetRepository;

	@Autowired
	TweetMapper tweetMapper;

	private Tweet getTweet(Long id) {
		Optional<Tweet> optional = tweetRepository.findByIdAndDeletedFalse(id);
		if (optional.isEmpty()) {
			 throw new NotFoundException("Tweet Not Found");
		}
		return optional.get();
	}

	@Override
	public List<HashtagDto> getAllTags() {
		List<HashTag> tags = hashtagRepository.findAll();

		return hashtagMapper.entitiesToDtos(tags);

	}

	@Override
	public List<TweetResponseDto> getTagsLabel(String label) {


		List<Tweet> tweets = tweetRepository.findAllByDeletedFalseOrderByPostedDesc();

		List<Tweet> good = new ArrayList<>();
		String label1 = "";

		  try {
			label1 = URLDecoder.decode(label, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		  
		for (Tweet tweet : tweets)
		{
			if(tweet.getContent() != null)
			{
				String lowercaseContent = tweet.getContent().toLowerCase();
	            String lowercaseLabel = label1.toLowerCase();
	            if (lowercaseContent.contains(lowercaseLabel)) {
	                good.add(tweet);
	            }
			}
		}
		
		 if (good.isEmpty()) {
			 throw new NotFoundException("No tweets with the given hashtag found");// No tweets with the given hashtag found
		    }
		
		

		return tweetMapper.entitiesToDtos(good);

	}

	

}
