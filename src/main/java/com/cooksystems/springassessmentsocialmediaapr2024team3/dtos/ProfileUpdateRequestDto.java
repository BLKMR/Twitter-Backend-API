package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileUpdateRequestDto {
    private CredentialsDto credentials;
    private ProfileDto profile;
}
