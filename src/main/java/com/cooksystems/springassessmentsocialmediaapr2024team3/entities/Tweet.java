package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
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
	@JoinTable(name = "user_mentions",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> mentions = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "tweet_hashtags",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashtags_id"))
	private List<Hashtag> hashtags = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "user_likes",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "tweet_id"))
	private List<User> likes = new ArrayList<>();

}
