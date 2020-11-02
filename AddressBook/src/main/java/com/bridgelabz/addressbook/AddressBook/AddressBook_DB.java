package com.bridgelabz.addressbook.AddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;

import com.mysql.cj.jdbc.Driver;

public class AddressBook_DB {

	public static void main(String args[]) {
		String jdbcURL = "jdbc:mysql://localhost:3306/employee_service?useSSL=false";
		String userName = "root";

		Connection connection;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded !");
		} catch (ClassNotFoundException c) {
			System.out.println("class not found !!");
			c.printStackTrace();
		}

		try {
			System.out.println("Connecting to database : "+jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, "theharyanaking");
			System.out.println("Connection is successful !! " + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
