package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;
import lombok.RequiredArgsConstructor;

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


}

