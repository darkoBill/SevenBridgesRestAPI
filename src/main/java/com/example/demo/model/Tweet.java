package com.example.demo.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "tweets")
public class Tweet {
   //forced to this approach instead of @GeneratedValue(strategy = GenerationType.IDENTITY) because of yml input parameter String constraints in sense of checking
	private final static AtomicInteger counter = new AtomicInteger();

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private final int maxTextLength = 320;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date tweetCreated;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@Column(length = maxTextLength)
	private String tweetText;

	@Column
	private String user;

	public Tweet() {}

	public Tweet(String id, String tweetText, Date tweetCreated) {
		this.id = id;
		this.tweetText = tweetText;
		this.tweetCreated = tweetCreated;
	}

	public String getId() {
		return id;
	}

	public void setId() {
		this.id = String.valueOf(getAtomicId());
	}
	private static int getAtomicId() {
		return counter.incrementAndGet();
	}

	public String getTweetText() {
		return tweetText;
	}

	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}

	public int getMaxTextLength() {
		return maxTextLength;
	}

	@PrePersist
	protected void onCreate() {
		tweetCreated = new Date();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
