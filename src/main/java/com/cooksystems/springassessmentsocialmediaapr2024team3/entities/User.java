package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
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

    @Column(name = "firstName", insertable = false, updatable = false)
    private String firstName;

    @Column(name = "lastName", insertable = false, updatable = false)
    private String lastName;

    @Column(name = "email", insertable = false, updatable = false)
    private String email;

    @Column(name = "phone", insertable = false, updatable = false)
    private String phone;

    @Embedded
    private Profile profile;
}