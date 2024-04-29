package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;






import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;


@Component
@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {


	@Mapping(source ="credentials.username", target ="username")
    UserResponseDto entityToDto(User entity);

    List<UserResponseDto> entitiesToDtos(List<User> entities);

	User requestDtoEntity(UserRequestDto userRequestDto);



}
