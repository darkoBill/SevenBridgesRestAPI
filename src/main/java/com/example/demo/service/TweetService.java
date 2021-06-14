package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tweet;
import com.example.demo.repository.TweetRepository;
@Service
@Transactional
public class TweetService {
	
    @Autowired
    private TweetRepository tweetRepository;
    
    public Page<Tweet> listAllTweets(int pageNum) {
        return tweetRepository.findAll(PageRequest.of(pageNum, 5, Sort.by("tweetCreated").descending()));
    }
    
    
    public List<String> getAllUsers(){
    	return tweetRepository.getAllUsers();
    }
    
    public boolean doesTweetExist(@NonNull String id) {
        return tweetRepository.existsById(id);
    }
    
    public List<Tweet> getTweetsByUser(String userName){
    	return tweetRepository.findByUserName(userName);
    	
    } 
    public List<String> getAllIds() {
		return tweetRepository.getAllIds();
	}

    public void saveTweet(Tweet tweet) {
    	tweetRepository.save(tweet);
    }

    public Tweet getTweetById(String id) {
        return tweetRepository.findById(id).get();
    }

    public void deleteTweet(String id) {
    	tweetRepository.deleteById(id);
    }
}
