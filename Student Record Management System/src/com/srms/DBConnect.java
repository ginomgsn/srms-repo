package com.srms;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	public static Connection getConnection() {
		
		try {
			driver = "com.mysql.cj.jdbc.Driver";
			url = "jdbc:mysql://localhost:3306/srms";
			username = "root";
			password = "";
			Class.forName(driver);
			
			java.sql.Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			
			return conn;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return null;	
	}
}
