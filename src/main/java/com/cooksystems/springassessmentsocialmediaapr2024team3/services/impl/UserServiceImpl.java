package com.cooksystems.springassessmentsocialmediaapr2024team3.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import com.cooksystems.springassessmentsocialmediaapr2024team3.exceptions.NotFoundException;
import com.cooksystems.springassessmentsocialmediaapr2024team3.mappers.UserMapper;
import com.cooksystems.springassessmentsocialmediaapr2024team3.repositories.UserRepository;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


	private final UserRepository userRepository;
	private final UserMapper userMapper;

	private User getUser(UserRequestDto optional) {
		if (userRepository.findByCredentialsUsernameAndDeletedFalse(optional.getCredentials().getUsername()) != null
				|| optional.getCredentials().getUsername() == null || optional.getCredentials().getPassword() == null
				|| optional.getProfile().getFirstName() == null || optional.getProfile().getLastName() == null
				|| optional.getProfile().getEmail() == null || optional.getProfile().getPhone() == null) 
		{
			throw new NotFoundException("Need all Required fields");
		}

		return userMapper.requestDtoEntity(optional);
	}

	@Override
	public List<UserResponseDto> getAllActiveUsers() {

		List<User> activeUsers = userRepository.findAllByDeletedFalse();

		return userMapper.entitiesToDtos(activeUsers);

	}

	@Override
	public UserResponseDto getActiveUserByUsername(String username) {
		User activeUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (activeUser == null) {
			throw new NotFoundException("Account is not active or does not exist!");
		}

		return userMapper.entityToDto(activeUser);
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User new_user = getUser(userRequestDto);
		
		
		
		for(User user1:	userRepository.findByCredentialsUsernameAndDeletedTrue(userRequestDto.getCredentials().getUsername()))
		{
			if(user1 != null)
			{
				user1.setDeleted(false);
				return userMapper.entityToDto(userRepository.saveAndFlush(user1));
			}
		}
		
		
		 return userMapper.entityToDto(userRepository.saveAndFlush(new_user));
			
		
	}




}
