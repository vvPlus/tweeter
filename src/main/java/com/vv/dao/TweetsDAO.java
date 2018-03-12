package com.vv.dao;

import java.sql.SQLException;
import java.util.List;

import com.vv.exception.ResourceNotFoundException;
import com.vv.model.Tweet;

public interface TweetsDAO {
	
	String GET_TWEET_QUERY = "SELECT tweet_id, tweet_text, created_at, creator_id"
			+ " FROM Tweet WHERE tweet_id = ?";

	String INSERT_TWEET_QUERY = "INSERT INTO Tweet (tweet_text, created_at, creator_id)"
            + " VALUES (? ,?, ?)";

	String GET_LAST_TWEET_QUERY = "SELECT last_insert_rowid() FROM Tweet";

	String GET_TWEETS_QUERY = "SELECT tweet_id, tweet_text, created_at, creator_id"
			+ " FROM Tweet WHERE tweet_id IN ( %s ) ORDER BY created_at DESC";

	Tweet getTweet(int tweetId) throws SQLException, ResourceNotFoundException;

	void createTweet(Tweet tweet) throws SQLException;

	List<Tweet> getTweetsForUser(List<Integer> tweetIds) throws SQLException;
	
	int getLastTweetId() throws SQLException;
}
