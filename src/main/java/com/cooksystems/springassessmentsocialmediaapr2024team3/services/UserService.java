package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ProfileUpdateRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllActiveUsers();

    UserResponseDto getActiveUserByUsername(String username);

    List<UserResponseDto> getUserFollowers(String username);

    UserResponseDto updateProfile(String username, ProfileUpdateRequestDto updateRequest);
}
