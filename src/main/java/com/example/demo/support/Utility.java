package com.example.demo.support;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {
	/*
	 * based on this description: Return tweets that have a given hash tag. If
	 * multiple tags are specified, return at least one of the specified tags. 
	 * Even unclear as it is I assumed that request can contain any combination of
	 * numbers and tags as http://localhost:8080/tweets/1#2#3 or
	 * similar so I extracted numbers,compared them
	 * with DB ids and returned the same elements in the list(if those exist)
	 * PS. for this method tests with EDGE cases would be necessary in
	 * professional development
	 */
	public static List<String> findLeastOneCommonId(String ids,List<String> list2) {
		String digits = removeNonDigitCharacters(ids);
		List<String> idsList = convertToStringArray(digits);
	    
	      return idsList.stream()
			    .filter(list2::contains)
			    .collect(Collectors
			    .toList());
	   
	}
	
	private static String removeNonDigitCharacters(String str) {
		 return str.replaceAll("\\D", ",");
	}
	
	public static boolean doesStringContainNumbers(String str) {
		return str.matches(".*\\d.*");
	}
	
	private static List<String> convertToStringArray(String str){
		return  Arrays.asList(str.split(","));
	}
	
	public static int findUserFromList(String user,List<String> users) {
	   return Collections.binarySearch(users, user);
	}
     public static String findUserFromString(String username,List<String> users) {
		String name = "";
		
    	 for(String user : users) {
    		 if(username.contains(user)) 
    			 name = user;
    	 }
    	 return name;
		
	}


}
