package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Credentials {
    private String username;
    private String password;
}
