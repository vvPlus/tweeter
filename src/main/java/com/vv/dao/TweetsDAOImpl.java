package com.vv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import com.vv.db.DatabaseHelper;
import com.vv.exception.ResourceNotFoundException;
import com.vv.model.Tweet;

public class TweetsDAOImpl implements TweetsDAO {
	
	private Connection conn = null;

	public TweetsDAOImpl() {
		DatabaseHelper.getInstance();
		conn = DatabaseHelper.getConnection();
	}

	@Override
	public Tweet getTweet(int tweetId) throws SQLException, ResourceNotFoundException {
		try (PreparedStatement stmt = conn.prepareStatement(GET_TWEET_QUERY)) {
			stmt.setInt(1,  tweetId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				Tweet tweet = new Tweet();
				tweet.setTweetId(result.getInt(1));
				tweet.setTweetText(result.getString(2));
				tweet.setCreatedAt(result.getString(3));
				tweet.setCreatorId(result.getInt(4));
				return tweet;
			} else {
				throw new ResourceNotFoundException("Tweet with id " + tweetId + " not found.");
			}
		}
	}

	@Override
	public void createTweet(Tweet tweet) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(INSERT_TWEET_QUERY)) {
			stmt.setString(1, tweet.getTweetText());
			stmt.setString(2, tweet.getCreatedAt());
			stmt.setInt(3, tweet.getCreatorId());
			stmt.executeUpdate();
		}
	}

	@Override
	public List<Tweet> getTweetsForUser(List<Integer> tweetIds) throws SQLException {
		StringJoiner tweetIdsPlaceholder = new StringJoiner(",");
		for (int i = 0; i < tweetIds.size(); i++) {
			tweetIdsPlaceholder.add("?");
		}

		List<Tweet> listOfTweets = new ArrayList<>();
		String query = String.format(GET_TWEETS_QUERY, tweetIdsPlaceholder.toString());

		try(PreparedStatement stmt = conn.prepareStatement(query)) {
			for (int i = 1; i <= tweetIds.size(); i++) {
				stmt.setInt(i, tweetIds.get(i - 1));
			}
			
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Tweet tweet = new Tweet();
				tweet.setTweetId(result.getInt(1));
				tweet.setTweetText(result.getString(2));
				tweet.setCreatedAt(result.getString(3));
				tweet.setCreatorId(result.getInt(4));
				listOfTweets.add(tweet);
			}
		}
		return listOfTweets;
	}

	@Override
	public int getLastTweetId() throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(GET_LAST_TWEET_QUERY)) {
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				return result.getInt(1);
			}
		}
		throw new RuntimeException("Couldn't find any tweets in the table.");
	}
}
