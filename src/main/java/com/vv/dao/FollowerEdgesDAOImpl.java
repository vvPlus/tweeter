package com.vv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vv.db.DatabaseHelper;

public class FollowerEdgesDAOImpl implements FollowerEdgesDAO {

	Connection conn = null;
			
	public FollowerEdgesDAOImpl() {
		DatabaseHelper.getInstance();
		conn = DatabaseHelper.getConnection();
	}
	
	@Override
	public void follow(int userId, int targetUserId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(INSERT_FOLLOWER_QUERY)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, targetUserId);
			stmt.executeUpdate();
		}
	}

	@Override
	public void unfollow(int userId, int targetUserId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(DELETE_FOLLOWER_QUERY)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, targetUserId);
			stmt.executeUpdate();
		}
	}

	@Override
	public List<Integer> getFollowersForUser(int userId) throws SQLException {
		List<Integer> listOfFollowersOfUser = new ArrayList<Integer>();
		try(PreparedStatement stmt = conn.prepareStatement(GET_FOLLOWERS_FOR_USER_ID_QUERY)) {
			stmt.setInt(1, userId);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				listOfFollowersOfUser.add(result.getInt("follower_id"));
			}
		}
		return listOfFollowersOfUser;
	}

}
