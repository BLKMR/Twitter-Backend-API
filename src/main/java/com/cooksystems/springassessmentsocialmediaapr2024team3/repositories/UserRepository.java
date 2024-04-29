package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByDeletedFalse();



    User findByCredentialsUsernameAndDeletedFalse(String username);
    
    User findByCredentialsUsername(String username);


	List<User> findByCredentialsUsernameAndDeletedTrue(String username);


	
	


}
