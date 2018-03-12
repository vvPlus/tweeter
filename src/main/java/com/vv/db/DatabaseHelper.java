package com.vv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vv.resource.FollowEdgeResource;

public class DatabaseHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DatabaseHelper.class);

	private static final String DB_LOCATION = "/Users/vidhya/Workspaces/Sandbox/Databases/TweeterDatabase.db";
	private static Connection dbConn = null;
	private static DatabaseHelper dbHelper = null;
	
	private DatabaseHelper() {
		if (dbConn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				dbConn = DriverManager.getConnection("jdbc:sqlite:" + DB_LOCATION);
			} catch (SQLException e) {
				LOG.error(this.getClass().getName(), e.getMessage());
			} catch (ClassNotFoundException e) {
				LOG.error(this.getClass().getName(), e.getMessage());
			}
		}
	}
	
	public static DatabaseHelper getInstance() {
		if (dbHelper == null) {
			return new DatabaseHelper();
		}
		return dbHelper;
	}
	
	public static Connection getConnection() {
		return dbConn;
	}
}
