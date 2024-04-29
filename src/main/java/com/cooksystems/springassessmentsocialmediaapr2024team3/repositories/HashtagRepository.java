package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByLabel(String hashtag);

    Optional<Hashtag> findById(Long id);

}
