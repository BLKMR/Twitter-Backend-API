package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@Data

public class TweetRepostDto {

    private Long id;

    private String author;

    private Timestamp posted;

    @JsonIgnore
    private String content;

    @JsonIgnore
    private TweetResponseDto inReplyTo;

    private SimpleTweetResponseDto repostOf;
}
