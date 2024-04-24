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
public class Tweet {

	
	 @Id
	 @GeneratedValue
	private Long id;
	
	private Integer author;
	
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;
	
	private  Integer inReplyTo;
	
	private  Integer repostOf;
	
}
