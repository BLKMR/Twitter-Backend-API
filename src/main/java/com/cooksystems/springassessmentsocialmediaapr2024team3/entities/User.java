package com.cooksystems.springassessmentsocialmediaapr2024team3.entities;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "user_table")
@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Credentials credentials;
    
    @Embedded
    private Profile profile;

    @CreationTimestamp
    private Timestamp joined; 
    
    private boolean deleted;
    
    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;
    
    @ManyToMany
    private List<User> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "mentions")
    private List<Tweet> tweetsMentioned = new ArrayList<>();

    @ManyToMany(mappedBy = "likes")
    private List<Tweet> likedTweets = new ArrayList<>();

}