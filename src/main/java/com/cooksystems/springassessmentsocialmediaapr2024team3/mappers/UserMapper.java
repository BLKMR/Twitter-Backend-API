package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {

	
}
