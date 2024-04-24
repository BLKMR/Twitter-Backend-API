package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
