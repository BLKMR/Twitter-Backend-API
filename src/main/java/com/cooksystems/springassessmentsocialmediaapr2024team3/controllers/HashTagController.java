package com.cooksystems.springassessmentsocialmediaapr2024team3.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.HashTagDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.dtos.TweetResponseDto;
import com.cooksystems.springassessmentsocialmediaapr2024team3.services.HashTagService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashTagController {


	
	 private final HashTagService hashtagService;

	    @GetMapping
	    public List<HashTagDto> getAllTags() {
	        return hashtagService.getAllTags();
	    }
	    
	    @GetMapping("{label}")
	    public List<TweetResponseDto> getTagsLabel(@PathVariable("label") String label) {
	    	return hashtagService.getTagsLabel(label);
	    }
	    
	    
	   

}
