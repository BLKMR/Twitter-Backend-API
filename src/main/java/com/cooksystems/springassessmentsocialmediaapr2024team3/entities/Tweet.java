package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "tweet")
public class Tweet {
	

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User author;
	
	@CreationTimestamp
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;
	
	@ManyToOne
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
  private List<Tweet> replies = new ArrayList<>();
	
	@ManyToOne
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf")
  private List<Tweet> reposts = new ArrayList<>();
	
	@ManyToMany
  private List<User> mentions = new ArrayList<>();
	
  @ManyToMany

  private List<HashTag> hashtags = new ArrayList<>();


  @ManyToMany
  private List<User> likes = new ArrayList<>();






  

	
}
