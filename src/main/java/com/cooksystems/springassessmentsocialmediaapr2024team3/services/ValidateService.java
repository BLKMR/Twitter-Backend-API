package com.cooksystems.springassessmentsocialmediaapr2024team3.services;

public interface ValidateService {

    boolean usernameExists(String username);

    boolean usernameAvailable(String username);

    boolean hashtagExists(String label);
}
