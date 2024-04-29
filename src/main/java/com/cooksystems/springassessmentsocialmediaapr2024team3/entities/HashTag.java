package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class HashTag {
	
	@Id
	@GeneratedValue
	private Long id;
	 
	@Column(nullable = false, unique = true)
	private String label;
	
	@CreationTimestamp
	private Timestamp firstUsed;
	
	@UpdateTimestamp
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy = "hashtags")
  private List<Tweet> tweets = new ArrayList<>();
}
