package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.BadRequestException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.TweetMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<UserResponseDto> getAllActiveUsers() {

        List<User> activeUsers = userRepository.findAllByDeletedFalse();

        return userMapper.entitiesToDtos(activeUsers);

    }


    @Override
    public UserResponseDto getActiveUserByUsername(String username) {
        User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (activeUser == null){
            throw new NotFoundException("Account is not active or does not exist!");
        }

        return userMapper.entityToDto(activeUser);
    }

    @Override
    public List<UserResponseDto> getUserFollowers(String username) {
        User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(activeUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }

        List<User> followers = activeUser.getFollowers();
        followers.removeIf(User::isDeleted);

        return userMapper.entitiesToDtos(followers);

    }


    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(checkUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }
        List<Tweet> userTweets = tweetRepository.findAllByAuthorAndDeletedFalseOrderByPostedDesc(checkUser);

        return tweetMapper.entitiesToDtos(userTweets);
    }

    @Override
    public List<TweetResponseDto> getUserMentionedTweets(String username) {
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(checkUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }
        List<Tweet> mentionedTweets = checkUser.getTweetsMentioned();
        mentionedTweets.removeIf(Tweet::isDeleted);
        mentionedTweets.sort(Comparator.comparing(Tweet::getPosted, Comparator.reverseOrder()));

        return tweetMapper.entitiesToDtos(mentionedTweets);
    }

    @Override
    public List<TweetResponseDto> getUserFeed(String username) {
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(checkUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }

        List<Tweet> userTweets = tweetRepository.findAllByAuthorAndDeletedFalseOrderByPostedDesc(checkUser);
        List<Tweet> feed = new ArrayList<>(userTweets);

        List<User> usersFollowed = checkUser.getFollowing();

        for (User following : usersFollowed) {
            List<Tweet> followingTweets = tweetRepository.findAllByAuthorAndDeletedFalse(following);
            feed.addAll(followingTweets);
        }


        feed.sort(Comparator.comparing(Tweet::getPosted, Comparator.reverseOrder()));


        return tweetMapper.entitiesToDtos(feed);
    }


    @Override
    public UserResponseDto updateProfile(String username, ProfileUpdateRequestDto updateRequest){
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(checkUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }
        String checkCredUsername = updateRequest.getCredentials().getUsername();
        String checkCredPassword = updateRequest.getCredentials().getPassword();

        if(!checkUser.getCredentials().getUsername().equals(checkCredUsername) || !checkUser.getCredentials().getPassword().equals(checkCredPassword)){
            throw new NotAuthorizedException("Username and/or Password incorrect");
        }

        User updatedUser = new User();
        if (updateRequest.getProfile() == null){
            updatedUser.getProfile().setEmail(checkUser.getProfile().getEmail());
            updatedUser.getProfile().setPhone(checkUser.getProfile().getPhone());
            updatedUser.getProfile().setFirstName(checkUser.getProfile().getFirstName());
            updatedUser.getProfile().setLastName(checkUser.getProfile().getFirstName());
            updatedUser.setProfile(userMapper.dtoToEntity(updateRequest.getProfile()));
        }


        User saveUpdatedUser = userRepository.saveAndFlush(checkUser);

        return userMapper.entityToDto(saveUpdatedUser);

    }

    @Override
    public void subscribeUser(String username, CredentialsDto credentials){
        User userToSubscribeTo = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(userToSubscribeTo == null) {
            throw new NotFoundException("The user you are trying to subscribe to does not exist.");
        }
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if(checkUser == null) {
            throw new NotFoundException("User account does not exist.");
        }
        String checkCredUsername = credentials.getUsername();
        String checkCredPassword = credentials.getPassword();

        if(!checkUser.getCredentials().getUsername().equals(checkCredUsername) || !checkUser.getCredentials().getPassword().equals(checkCredPassword)){
            throw new NotAuthorizedException("Username and/or Password incorrect");
        }

        if (checkUser.getFollowers().contains(userToSubscribeTo)) {
            throw new BadRequestException("User already subscribed.");
        }

        checkUser.getFollowers().add(userToSubscribeTo);
        userRepository.save(checkUser);


    }



}

