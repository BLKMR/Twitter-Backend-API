package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.TweetRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetRepository tweetRepository;

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

        checkUser.setProfile(userMapper.dtoToEntity(updateRequest.getProfile()));

        User updatedUser = userRepository.saveAndFlush(checkUser);

        return userMapper.entityToDto(updatedUser);

    }


    @Override
    public List<UserResponseDto> getFollowing(String username) {
        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(user == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }

        List<User> following = user.getFollowing();
        List<User> returnFollowing = new ArrayList<>();

        for (User followedUser : following) {
            if (!followedUser.isDeleted()) {
                returnFollowing.add(followedUser);
            }
        }

        return userMapper.entitiesToDtos(returnFollowing);
    }


    @Override
    public UserResponseDto deleteUser(Credentials credentials, String username) {
        User checkUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(checkUser == null) {
            throw new NotFoundException("Account is not active or does not exist!");
        }

        String checkCredUsername = checkUser.getCredentials().getUsername();
        String checkCredPassword = checkUser.getCredentials().getPassword();

        if(!checkUser.getCredentials().getUsername().equals(checkCredUsername) || !checkUser.getCredentials().getPassword().equals(checkCredPassword)){
            throw new NotAuthorizedException("Username and/or Password incorrect");
        }

        List<Tweet> tweets = checkUser.getTweets();

        for (Tweet tweet : tweets) {
            tweet.setDeleted(true);
            tweetRepository.saveAndFlush(tweet);
        }

        checkUser.setDeleted(true);
        userRepository.saveAndFlush(checkUser);

        return userMapper.entityToDto(checkUser);

    }


    @Override
    public void unfollowUser(Credentials credentials, String username) {
        User userToUnfollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(userToUnfollow == null) {
            throw new NotFoundException("Account to unfollow is not active or does not exist!");
        }

        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if(user == null) {
            throw new NotFoundException("Your account is not active or does not exist!");
        }

        String checkCredUsername = user.getCredentials().getUsername();
        String checkCredPassword = user.getCredentials().getPassword();

        if(!user.getCredentials().getUsername().equals(checkCredUsername) || !user.getCredentials().getPassword().equals(checkCredPassword)){
            throw new NotAuthorizedException("Username and/or Password incorrect");
        }

        List<User> following = user.getFollowing();

        for (User follow : following) {
            if (follow.getCredentials().getUsername().equals(username)) {
                following.remove(follow);
                userRepository.saveAndFlush(user);

                follow.getFollowers().remove(user);
                userRepository.saveAndFlush(follow);
                
                return;

            }
        }

        throw new NotFoundException("Couldn't find user in your followed users");
    }



}

