package com.cooksystems.springassessmentsocialmediaapr2024team3.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.HashTag;

@Mapper(componentModel = "spring")
public interface HashTagMapper {




	 HashTagDto entityToDto(HashTag entity);

	
	@Mapping(source ="firstUsed", target ="firstUsed")
	@Mapping(source ="lastUsed", target ="lastUsed")
	   List<HashTagDto> entitiesToDtos(List<HashTag> entities);

}
