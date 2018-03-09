package com.vv.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

	private static final String DB_LOCATION = "/Users/vidhya/Workspaces/Sandbox/Databases/TweeterDatabase.db";
	private static Connection dbConn = null;
	private static DatabaseHelper dbHelper = null;
	
	private DatabaseHelper() {
		if (dbConn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				dbConn = DriverManager.getConnection("jdbc:sqlite:" + DB_LOCATION);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
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
