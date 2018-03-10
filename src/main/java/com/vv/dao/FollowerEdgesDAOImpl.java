package com.vv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unfollow(int userId, int targetUserId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement(DELETE_FOLLOWER_QUERY)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, targetUserId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
