package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByDeletedFalse();

    User findByCredentialsUsernameAndDeletedFalse(String username);


    List<User> findAllByFollowersContaining(User user);

    User findByCredentialsUsername(String username);

    User getById(Integer id);
}
