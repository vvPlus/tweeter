package com.vv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.vv.db.DatabaseHelper;
import com.vv.model.Tweet;

public class TweetsDAOImpl implements TweetsDAO {
	
	Connection conn = null;

	public TweetsDAOImpl() {
		DatabaseHelper.getInstance();
		conn = DatabaseHelper.getConnection();
	}
	
	@Override
	public void createTweet(Tweet tweet) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(INSERT_TWEET_QUERY)) {
			stmt.setString(1, tweet.getTweetText());
			stmt.setString(2, tweet.getCreatedAt());
			stmt.setInt(3, tweet.getCreatorId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
