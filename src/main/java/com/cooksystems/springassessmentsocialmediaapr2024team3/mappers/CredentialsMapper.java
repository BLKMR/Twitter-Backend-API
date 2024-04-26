package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.CredentialsDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto (Credentials entity);

}
