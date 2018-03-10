package com.vv.dao;

import java.sql.SQLException;

import com.vv.model.User;

public interface FollowerEdgesDAO {

	String INSERT_FOLLOWER_QUERY = "INSERT INTO Follower (user_id, follower_id)"
            + " VALUES (? ,?)";
	
	String DELETE_FOLLOWER_QUERY = "DELETE FROM Follower"
			+ " WHERE user_id = ? AND follower_id = ?";
	
	public void follow(int userId, int targetUserId) throws SQLException;
	
	public void unfollow(int userId, int targetUserId) throws SQLException;
	
}
