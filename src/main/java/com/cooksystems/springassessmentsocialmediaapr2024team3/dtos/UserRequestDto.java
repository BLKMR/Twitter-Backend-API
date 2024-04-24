package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
    private CredentialsDto credentials;
    private ProfileDto profile;

    long currentTime = System.currentTimeMillis();
    private Timestamp joined = new Timestamp(currentTime);

    private boolean deleted;
    
}
