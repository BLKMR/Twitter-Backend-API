package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ContextDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.HashTag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.HashTagMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashTagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
	

	@Autowired
		private final TweetRepository tweetRepository;
	@Autowired
		private final TweetMapper tweetMapper;
	@Autowired
	   private final UserRepository userRepository;
	@Autowired
	    private final UserMapper userMapper;
	
	@Autowired
	private HashTagRepository hashtagRepository;

	@Autowired
	 private HashTagMapper hashtagMapper;
	
	  
	  private Tweet getTweet(Long id) {
	      Optional<Tweet> optional = tweetRepository.findByIdAndDeletedFalse(id);
	      if (optional.isEmpty()) {
	    	  throw new NotFoundException("No Tweet Found");
	      }
	      return optional.get();
	}
	  
	  
	  private User getUser(String username) {
	      User optional = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
	      if (optional == null) {
	    	  throw new NotFoundException("User Not Found");
	      }
	      return optional;
	}
	
	
	
	 @Override
	public List<TweetResponseDto> getAllTweets() {
		     List<Tweet> activeUsers = tweetRepository.findAllByDeletedFalseOrderByPostedDesc();

		        return tweetMapper.entitiesToDtos(activeUsers);
	}







	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		Tweet tweet = getTweet(id);
		List<Tweet> active = new ArrayList<>();
		
		
		for(Tweet repost : tweet.getReposts())
		{
			if(getTweet(repost.getId()) == null)
			{
				throw new NotFoundException("Repost Not Found!");
			}
			else
			{
				//repost.setRepostOf(tweet);
				active.add(repost);
			}
			
		}
		
		
		
		return tweetMapper.entitiesToDtos(active);
		
	}



	@Override
	public List<UserResponseDto> getMentions(Long id) {
		Tweet tweet = getTweet(id);
		
		List<User> users = new ArrayList<>();
		 
		for(User mention: tweet.getMentions())
		{
			if(getUser(mention.getCredentials().getUsername()) == null)
			{
				throw new NotFoundException("No mentions found");
			}
			else
			{
				users.add(mention);
			}
			
		}
		
		
		
		
		return userMapper.entitiesToDtos(users) ;
	}


	@Override
	public List<HashTagDto> getTweetTags(Long id) {
		Tweet tweet = getTweet(id);
		
		List<HashTag> tags = new ArrayList<>();
		for( HashTag saved_tags : tweet.getHashtags())
		{
			if(saved_tags != null)
			{
				tags.add(saved_tags);
			}
			
			  
		}
		
		return hashtagMapper.entitiesToDtos(tags);
	}


	@Override
	public ContextDto getContext(Long id) {
		  Tweet targetTweet = getTweet(id);

	        if (targetTweet == null || targetTweet.isDeleted()) {
	            // Return an error response if the target tweet doesn't exist or is deleted
	            return null;
	        }

	        ContextDto context = new ContextDto();

	        List<TweetResponseDto> beforeChain = new ArrayList<>();
	        List<TweetResponseDto> afterChain = new ArrayList<>();

	        // Start building the before chain
	        buildBeforeChain(targetTweet.getInReplyTo(), beforeChain);

	        // Add the target tweet to the before chain
	        beforeChain.add(tweetMapper.entityToDto(targetTweet));

	        // Start building the after chain
	        buildAfterChain(targetTweet.getReplies(), afterChain);

	        context.setBefore(beforeChain);
	        context.setAfter(afterChain);

	        return context;
	
	}

	  private void buildBeforeChain(Tweet tweet, List<TweetResponseDto> chain) {
	        if (tweet == null || tweet.isDeleted()) {
	            return;
	        }

	        buildBeforeChain(tweet.getInReplyTo(), chain);
	        chain.add(tweetMapper.entityToDto(tweet));
	    }
	  
	  
	   private void buildAfterChain(List<Tweet> replies, List<TweetResponseDto> chain) {
	        for (Tweet reply : replies) {
	            if (!reply.isDeleted()) {
	                chain.add(tweetMapper.entityToDto(reply));
	                buildAfterChain(reply.getReplies(), chain);
	            } else {
	                // Include transitive replies if the reply is deleted
	                buildAfterChain(reply.getReplies(), chain);
	            }
	        }
	    }
	  

	
}
