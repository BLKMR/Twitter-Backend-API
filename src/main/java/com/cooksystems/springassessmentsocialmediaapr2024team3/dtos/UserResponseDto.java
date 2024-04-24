package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private Integer id;
    private CredentialsDto credentials;
    private ProfileDto profile;
    private Timestamp joined; 
    private boolean deleted;
}
