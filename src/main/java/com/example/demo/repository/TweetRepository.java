package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, String> {

	@Query(value = "SELECT id FROM DB.tweets WHERE id IS NOT NULL ", nativeQuery = true)
	List<String> getAllIds();

	@Query(value = "SELECT user FROM DB.tweets WHERE user IS NOT NULL ", nativeQuery = true)
	List<String> getAllUsers();
	
	@Query(value = "SELECT * FROM DB.tweets d WHERE d.user = ?1 ", nativeQuery = true)
	List<Tweet> findByUserName(String userName);
	
	
	

}