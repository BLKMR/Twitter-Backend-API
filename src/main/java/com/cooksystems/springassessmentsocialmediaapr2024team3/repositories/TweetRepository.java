package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>  {

    Tweet findByIdAndDeletedFalse(Long id);

    List<Tweet> findAllByAuthorAndDeletedFalseOrderByPostedDesc(User user);

    List<Tweet> findAllByAuthorAndDeletedFalse(User user);

    List<Tweet> findAllByDeletedFalse();

    List<Tweet> findAllByDeletedFalseOrderByPostedDesc();

    List<Tweet> findAllByDeletedFalseOrderByPostedAsc();

    Tweet getById(Integer id);

}
