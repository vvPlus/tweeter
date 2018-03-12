package com.vv.dao;

import java.sql.SQLException;
import java.util.List;

public interface FollowerEdgesDAO {

	String INSERT_FOLLOWER_QUERY = "INSERT INTO Follower (follower_id, user_id)"
            + " VALUES (? ,?)";
	
	String DELETE_FOLLOWER_QUERY = "DELETE FROM Follower"
			+ " WHERE user_id = ? AND follower_id = ?";
	
	String GET_FOLLOWERS_FOR_USER_ID_QUERY = "SELECT follower_id FROM Follower WHERE user_id = ?";
	
	void follow(int userId, int targetUserId) throws SQLException;
	
	void unfollow(int userId, int targetUserId) throws SQLException;
	
	List<Integer> getFollowersForUser(int userId) throws SQLException;
	
}
