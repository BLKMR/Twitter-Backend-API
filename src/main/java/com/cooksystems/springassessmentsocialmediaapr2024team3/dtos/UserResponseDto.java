package com.cooksystems.springassessmentsocialmediaapr2024team3.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private Integer id;
    private String username;
    private String password;
    private Timestamp joined; 
    private boolean deleted;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
