package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Data
public class HashTag {

	 @Id
	 @GeneratedValue
	private  Long id;
	 
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
}
