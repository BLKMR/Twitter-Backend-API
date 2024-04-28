package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.HashtagRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.controllers.TweetController;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
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

    public List<String> extractHashtags(String text) {
        List<String> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            hashtags.add(matcher.group()); // Add the entire matched substring (including the "#")
        }
        return hashtags;
    }
    
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

        tweet.setDeleted(true);
        tweetRepository.saveAndFlush(tweet);

        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public TweetResponseDto replyToTweet(TweetRequestDto tweet, Integer id) {
        User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(tweet.getCredentials().getUsername());
        if (activeUser == null){
            throw new NotFoundException("Account is not active or does not exist!");
        }

        if (!tweet.getCredentials().getUsername().equals(activeUser.getCredentials().getUsername()) || !tweet.getCredentials().getPassword().equals(activeUser.getCredentials().getPassword())) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        if (tweet.getContent() == null) {
            throw new NotFoundException("Nothing to tweet");
        }

        Tweet tweetToBeRepliedTo = tweetRepository.getById(id);
        Tweet replyTweet = new Tweet();

        replyTweet.setAuthor(activeUser);
        replyTweet.setContent(tweet.getContent());
        replyTweet.setDeleted(false);
        replyTweet.setPosted(new Timestamp(System.currentTimeMillis()));
        replyTweet.setInReplyTo(tweetToBeRepliedTo);
        tweetRepository.saveAndFlush(replyTweet);

        List<String> hashtagsToSave = extractHashtags(replyTweet.getContent());
        for (String hashtag: hashtagsToSave){
            Hashtag hashtagToSave = new Hashtag();
            Hashtag hashTagExists = hashtagRepository.findByLabel(hashtag);
            if(hashTagExists == null){
                hashtagToSave.setLabel(hashtag);
                hashtagToSave.setFirstUsed(new Timestamp(System.currentTimeMillis()));
                hashtagToSave.setLastUsed(new Timestamp(System.currentTimeMillis()));
                hashtagToSave.getTweets().add(replyTweet);
                hashtagRepository.saveAndFlush(hashtagToSave);
            } else{
                hashTagExists.setLastUsed(new Timestamp(System.currentTimeMillis()));
                hashTagExists.getTweets().add(replyTweet);
                hashtagRepository.saveAndFlush(hashTagExists);
            }
        }

        List<Tweet> replies = tweetToBeRepliedTo.getReplies();
        replies.add(tweetMapper.dtoToEntity(tweet));
        tweetToBeRepliedTo.setReplies(replies);
        tweetRepository.saveAndFlush(tweetToBeRepliedTo);

        return tweetMapper.entityToDto(tweetToBeRepliedTo);
    }

    @Override
    public TweetResponseDto likeTweet(Credential credentials, Integer id) {
        User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if (activeUser == null){
            throw new NotFoundException("Account is not active or does not exist!");
        }

        if (!credentials.getUsername().equals(activeUser.getCredentials().getUsername()) || !credentials.getPassword().equals(activeUser.getCredentials().getPassword())) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        Tweet tweet = tweetRepository.getById(id);

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet either does not exist, or has been deleted");
        }

        activeUser.getLikedTweets().add(tweet);
        userRepository.saveAndFlush(activeUser);

        tweet.getLikes().add(activeUser);
        tweetRepository.saveAndFlush(tweet);
        
        return tweetMapper.entityToDto(tweet);
    }

    @Override
    public List<TweetResponseDto> getReplies(Integer id) {
        Tweet tweet = tweetRepository.getById(id);

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet either does not exist, or has been deleted");
        }

        List<Tweet> replies = tweet.getReplies();
        List<Tweet> returnReplies = new ArrayList<>();

        for (Tweet reply : replies) {
            if (!reply.isDeleted()){
                returnReplies.add(reply);
            }
        }

        return tweetMapper.entitiesToDtos(returnReplies);
    }

    @Override
    public List<UserResponseDto> getLikes(Integer id) {
        Tweet tweet = tweetRepository.getById(id);

        if (tweet == null || tweet.isDeleted()) {
            throw new NotFoundException("Tweet either does not exist, or has been deleted");
        }

        List<User> likes = tweet.getLikes();
        List<User> returnLikes = new ArrayList<>();

        for (User user : likes) {
            if (!user.isDeleted()){
                returnLikes.add(user);
            }
        }

        return userMapper.entitiesToDtos(returnLikes);
    }
}
