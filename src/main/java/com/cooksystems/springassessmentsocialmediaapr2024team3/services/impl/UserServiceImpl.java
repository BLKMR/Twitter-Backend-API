package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.*;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotAuthorizedException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
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


        //CHECK FIELDS IF THEY MATCH AND IF THEY DON'T UPDATE THEM?!?!
        //WHAT IF FIELDS ARE EMPTY?
        //HELP?!
        checkUser.setProfile(userMapper.dtoToEntity(updateRequest.getProfile()));

        User updatedUser = userRepository.saveAndFlush(checkUser);

        return userMapper.entityToDto(updatedUser);

    }



}

