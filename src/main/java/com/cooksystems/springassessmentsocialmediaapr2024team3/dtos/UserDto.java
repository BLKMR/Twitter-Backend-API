package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private ProfileDto profile;
    private Timestamp joined;
}

