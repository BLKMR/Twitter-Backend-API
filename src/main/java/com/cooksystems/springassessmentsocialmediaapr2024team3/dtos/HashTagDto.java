package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashTagDto {

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

}