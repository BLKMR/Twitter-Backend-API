package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>  {

    Tweet findByIdAndDeletedFalse(Long id);

    List<Tweet> findAllByAuthorAndDeletedFalseOrderByPostedDesc(User user);

}
