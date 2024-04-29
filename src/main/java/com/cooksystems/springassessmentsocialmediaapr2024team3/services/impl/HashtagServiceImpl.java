package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashtagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.HashtagMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashtagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    HashtagMapper hashtagMapper;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    TweetMapper tweetMapper;

    private Tweet getTweet(Long id) {
        Tweet optional = tweetRepository.findByIdAndDeletedFalse(id);
        if (optional == null) {
            throw new NotFoundException("Tweet Not Found");
        }
        return optional;
    }

    @Override
    public List<HashtagDto> getAllTags() {
        List<Hashtag> tags = hashtagRepository.findAll();

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
