package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

}
