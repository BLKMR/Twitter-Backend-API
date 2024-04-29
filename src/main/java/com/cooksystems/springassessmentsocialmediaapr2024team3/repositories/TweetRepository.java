package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>  {


	
	Optional<Tweet> findByIdAndDeletedFalse(Long id);
	
	 List<Tweet> findAllByDeletedFalse();
	 
	 List<Tweet> findAllByDeletedFalseOrderByPostedDesc();
	 
	 List<Tweet> findAllByDeletedFalseOrderByPostedAsc();

	
}
