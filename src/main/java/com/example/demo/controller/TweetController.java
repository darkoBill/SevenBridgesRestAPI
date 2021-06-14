package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.Constants;
import com.example.demo.model.Tweet;
import com.example.demo.service.TweetService;
import com.example.demo.support.Utility;
import com.example.demo.support.Validation;

@RestController
@RequestMapping("/tweets")
public class TweetController {

	private static final Logger logger = LoggerFactory.getLogger(TweetController.class);

	@Autowired
	private TweetService tweetService;


	@PostMapping("/")
	public ResponseEntity<String> add(@RequestBody(required = true) Tweet tweet,
			@RequestHeader(value = "user", required = true) String user) {

		if (!Validation.isTweetValid(tweet, tweet.getMaxTextLength())) {
			logger.error(tweet.getTweetText());
			return ResponseEntity.badRequest().body(Constants.INVALID_TWEET_SIZE);
		}
		tweet.setUser(user);
		tweet.setId();
		tweetService.saveTweet(tweet);
		return ResponseEntity.status(HttpStatus.CREATED).body(tweet.getTweetText());
	}

	@GetMapping("")
	public Page<Tweet> list(@RequestParam(defaultValue = "0") int pageNum) {
		return tweetService.listAllTweets(pageNum);
	}

	@GetMapping("/{ids}")
	public ResponseEntity<String> getTweetById(@PathVariable(required = false) String ids) {

		Tweet tweet = new Tweet();
		List<String> existingIds = tweetService.getAllIds();
		List<String> queriedIds = Utility.findLeastOneCommonId(ids, existingIds);

		if (!queriedIds.isEmpty()) {
			tweet = tweetService.getTweetById(queriedIds.get(0));
			return new ResponseEntity<String>(tweet.getTweetText(), HttpStatus.OK);
		} else if (!Utility.doesStringContainNumbers(ids)) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<String>( HttpStatus.OK);//status HttpStatus.NOT_FOUND would be more appropriate but specification says this way
		}
	}


	@GetMapping("user/{username}")
	public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable("username") String username) {
		
		if(Utility.doesStringContainNumbers(username)){
			logger.error(username);
			return new ResponseEntity<List<Tweet>>(HttpStatus.BAD_REQUEST);
		}

		List<String> users = tweetService.getAllUsers();
		String userFound = Utility.findUserFromString(username, users);

		if (!userFound.isBlank()) {
			List<Tweet> listOfTwets = tweetService.getTweetsByUser(userFound);
			return new ResponseEntity<List<Tweet>>(listOfTwets,HttpStatus.OK);
		}

		return new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id,@RequestHeader(value ="user",required = true) String user) {
		
		if(!tweetService.doesTweetExist(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
       Tweet tweet = tweetService.getTweetById(id);
    	
    	if(user.equals(tweet.getUser())){
    		tweetService.deleteTweet(id);
    		return ResponseEntity.ok(Constants.TWEET_DELETED);
    	}
    	return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
	}

}
