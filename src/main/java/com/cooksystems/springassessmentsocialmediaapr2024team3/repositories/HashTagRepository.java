package com.cooksystems.springassessmentsocialmediaapr2024team3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cooksystems.springassessmentsocialmediaapr2024team3.entities.HashTag;


@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

	Optional<HashTag> findById(Long id);
	
	
	


}
