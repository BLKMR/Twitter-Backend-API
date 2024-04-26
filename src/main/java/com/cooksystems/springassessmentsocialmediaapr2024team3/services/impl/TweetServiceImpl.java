package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashtagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.TweetService;

import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final HashtagRepository hashtagRepository;



    @Override
    public SimpleTweetResponseDto createTweet(TweetRequestDto newTweet) {
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(newTweet.getCredentials().getUsername());
        if (author == null) {
            throw new NotFoundException("User not found");
        }

        Tweet tweetToCreate = new Tweet();
        tweetToCreate.setAuthor(author);
        tweetToCreate.setContent(newTweet.getContent());
        tweetToCreate.setDeleted(false);

        Tweet tweetCreated = tweetRepository.saveAndFlush(tweetToCreate);


        //WHAT DO WE DO ABOUT @MENTIONSSSSSSSSSSSSSSSSS


        List<String> hashtagsToSave = extractHashtags(newTweet.getContent());
        for (String hashtag: hashtagsToSave){
            Hashtag hashtagToSave = new Hashtag();
            Hashtag hashTagExists = hashtagRepository.findByLabel(hashtag);
            if(hashTagExists == null){
                hashtagToSave.setLabel(hashtag);
                hashtagToSave.setFirstUsed(new Timestamp(System.currentTimeMillis()));
                hashtagToSave.setLastUsed(new Timestamp(System.currentTimeMillis()));
                hashtagToSave.getTweets().add(tweetCreated);
                hashtagRepository.saveAndFlush(hashtagToSave);
            } else{
                hashTagExists.setLastUsed(new Timestamp(System.currentTimeMillis()));
                hashTagExists.getTweets().add(tweetCreated);
                hashtagRepository.saveAndFlush(hashTagExists);
            }
        }

        return tweetMapper.simpleEntityToDto(tweetCreated);

    }


    @Override
    public TweetRepostDto createRepost(Long id, CredentialsDto credentials) {
        User author = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if(author == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }
        String checkCredUsername = credentials.getUsername();
        String checkCredPassword = credentials.getPassword();

        if(!author.getCredentials().getUsername().equals(checkCredUsername) || !author.getCredentials().getPassword().equals(checkCredPassword)){
            throw new NotAuthorizedException("Username and/or Password incorrect");
        }

        Tweet originalTweet = tweetRepository.findByIdAndDeletedFalse(id);

        if(originalTweet == null || originalTweet.isDeleted()){
            throw new NotFoundException("Tweet not found/or deleted");
        }

        Tweet repostTweet = new Tweet();
        repostTweet.setAuthor(author);
        repostTweet.setDeleted(false);
        repostTweet.setRepostOf(originalTweet); // Set the repostOf field to the original Tweet
        Tweet savedRepostTweet = tweetRepository.save(repostTweet);


        return tweetMapper.repostEntityToDto(savedRepostTweet);

    }








    public List<String> extractHashtags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            hashtags.add(matcher.group()); // Add the entire matched substring (including the "#")
        }
        return hashtags;
    }







}
