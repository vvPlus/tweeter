package com.vv.dao;

import java.sql.SQLException;

import com.vv.model.Tweet;

public interface TweetsDAO {
	
	String INSERT_TWEET_QUERY = "INSERT INTO Tweet (tweet_text, created_at, created_by)"
            + " VALUES (? ,?, ?)";
	
	public void createTweet(Tweet tweet) throws SQLException;
}
