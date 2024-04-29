package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.SimpleTweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRepostDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetRequestDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class, CredentialsMapper.class, ProfileMapper.class})
public interface TweetMapper {

    @Mapping(target = "author", source = "author.credentials.username")
    TweetResponseDto entityToDto(Tweet entity);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

    @Mapping(target = "author", source = "author.credentials.username")
        SimpleTweetResponseDto simpleEntityToDto (Tweet tweetCreated);


    @Mapping(target = "author", source = "author.credentials.username")
        TweetRepostDto repostEntityToDto (Tweet entity);



    Object entityToDto(TweetRequestDto tweet);

    Tweet dtoToEntity(TweetRequestDto tweet);
}
