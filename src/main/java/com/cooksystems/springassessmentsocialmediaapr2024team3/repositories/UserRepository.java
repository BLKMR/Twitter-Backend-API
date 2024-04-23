package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;


import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
