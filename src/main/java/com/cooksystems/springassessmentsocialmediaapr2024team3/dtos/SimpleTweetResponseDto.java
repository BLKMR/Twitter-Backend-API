package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SimpleTweetResponseDto {

    private Long id;

    private String author;


    private Timestamp posted;

    private String content;

}
