package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ProfileUpdateRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllActiveUsers();

    UserResponseDto getActiveUserByUsername(String username);

    List<UserResponseDto> getUserFollowers(String username);

    UserResponseDto updateProfile(String username, ProfileUpdateRequestDto updateRequest);

    List<UserResponseDto> getFollowing(String username);

    UserResponseDto deleteUser(Credentials credentials, String username);

    void unfollowUser(Credentials credentials, String username);

    List<TweetResponseDto> getUserTweets(String username);

    void subscribeUser(String username, CredentialsDto credentials);

    List<TweetResponseDto> getUserMentionedTweets(String username);

    List<TweetResponseDto> getUserFeed(String username);
}
