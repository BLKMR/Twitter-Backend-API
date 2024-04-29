package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashtagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TweetMapper.class)
public interface HashtagMapper {

    HashtagDto entityToDto(Hashtag entity);


    @Mapping(source ="firstUsed", target ="firstUsed")
    @Mapping(source ="lastUsed", target ="lastUsed")
    List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

}
