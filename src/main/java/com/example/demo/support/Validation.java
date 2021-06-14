package com.example.demo.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.model.Tweet;

public class Validation {

	// Function to validate the user
	public static boolean isUserValid(String user,String regex) {

		// Compile the Regex
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(user);
		return m.matches();
	}
	
	public static boolean isTweetValid(Tweet tweet,int maxLength) {
		
		if(tweet.getTweetText().isBlank() || tweet.getTweetText().length() > maxLength) return false;
		return true;
		
	}

}
