package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

@Repository
public interface TweetRepository extends JpaRepository<User, Long>  {

}
