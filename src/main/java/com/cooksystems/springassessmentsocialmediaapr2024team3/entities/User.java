package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.security.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private Timestamp joined; 

    private boolean deleted;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}