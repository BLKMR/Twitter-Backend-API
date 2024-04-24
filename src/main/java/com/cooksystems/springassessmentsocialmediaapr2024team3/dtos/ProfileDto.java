package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
