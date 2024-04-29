package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;



import java.util.List;



import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;

@Component
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

	List<TweetResponseDto> entitiesToDtos(List<Tweet> activeUsers);

	TweetResponseDto entityToDto(Tweet tweet);


}
