package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;



import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.ProfileDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.UserResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Profile;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })

public interface UserMapper {

    @Mapping(source = "credentials.username", target = "username")
    UserResponseDto entityToDto(User entity);

    List<UserResponseDto> entitiesToDtos(List<User> entities);

    Profile dtoToEntity(ProfileDto profile);


}
