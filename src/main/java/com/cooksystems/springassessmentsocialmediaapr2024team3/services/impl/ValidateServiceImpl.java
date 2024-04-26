package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.services.ValidateService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public boolean usernameExists(String username){
        User usernameExists = userRepository.findByCredentialsUsername(username);
        return usernameExists != null;
    }

    @Override
    public boolean usernameAvailable(String username){
        User usernameExists = userRepository.findByCredentialsUsername(username);
        return usernameExists == null;
    }

}
