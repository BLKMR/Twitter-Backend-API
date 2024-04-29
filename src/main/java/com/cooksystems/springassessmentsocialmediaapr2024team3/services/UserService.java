package com.cooksystems.springassessmentsocialmediaapr2024team3.services;



import java.util.List;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ProfileUpdateRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;

public interface UserService {

    List<UserResponseDto> getAllActiveUsers();

    UserResponseDto getActiveUserByUsername(String username);


	UserResponseDto createUser(UserRequestDto userRequestDto);

    List<UserResponseDto> getUserFollowers(String username);

    UserResponseDto updateProfile(String username, ProfileUpdateRequestDto updateRequest);



}
