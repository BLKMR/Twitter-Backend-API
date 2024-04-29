package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.HashtagMapper;
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
    private final HashtagMapper hashtagMapper;



    private Tweet getTweet(Long id) {
        Tweet optional = tweetRepository.findByIdAndDeletedFalse(id);
        if (optional == null) {
            throw new NotFoundException("No Tweet Found");
        }
        return optional;
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


        List<String> mentionsToSave = extractMentions(newTweet.getContent());
        for (String mention : mentionsToSave){
            User mentioned = userRepository.findByCredentialsUsername(mention);
            tweetCreated.getMentions().add(mentioned);
            mentioned.getTweetsMentioned().add(tweetCreated);
        }

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

        Tweet tweetSaved = tweetRepository.saveAndFlush(tweetCreated);

        return tweetMapper.simpleEntityToDto(tweetSaved);

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
        Pattern pattern = Pattern.compile("#[a-zA-Z0-9]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            hashtags.add(matcher.group());
        }
        return hashtags;
    }

    public List<String> extractMentions(String text) {
        List<String> mentions = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=@)[a-zA-Z0-9]+\\S*");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            mentions.add(matcher.group());
        }
        return mentions;
    }




    @Override
    public List<TweetResponseDto> getReposts(Long id) {
        Tweet tweet = getTweet(id);
        List<Tweet> active = new ArrayList<>();


        for(Tweet repost : tweet.getReposts())
        {
            getTweet(repost.getId());//repost.setRepostOf(tweet);
            active.add(repost);

        }



        return tweetMapper.entitiesToDtos(active);

    }

    @Override
    public List<UserResponseDto> getMentions(Long id) {
        Tweet tweet = getTweet(id);

        List<User> users = new ArrayList<>();

        for(User mention: tweet.getMentions())
        {
            getUser(mention.getCredentials().getUsername());
            users.add(mention);

        }

        return userMapper.entitiesToDtos(users) ;
    }

    @Override
    public List<HashtagDto> getTweetTags(Long id) {
        Tweet tweet = getTweet(id);

        List<Hashtag> tags = new ArrayList<>();
        for( Hashtag saved_tags : tweet.getHashtags())
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

        if (targetTweet.isDeleted()) {
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

        List<String> mentionsToSave = extractMentions(replyTweet.getContent());
        for (String mention : mentionsToSave){
            User mentioned = userRepository.findByCredentialsUsername(mention);
            replyTweet.getMentions().add(mentioned);
            mentioned.getTweetsMentioned().add(replyTweet);
        }

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
    public void likeTweet(Credential credentials, Integer id) {
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
