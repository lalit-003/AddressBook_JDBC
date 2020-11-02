package com.bridgelabz.addressbook.AddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBook_DataBaseService {

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		Connection connection;
		System.out.println("Connecting to database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, "theharyanaking");
		System.out.println("Connection is successful !! " + connection);
		return connection;
	}
	
	public List<AddressBookData> readData()
	{
		String sql = "Select * from address_table;";
		List<AddressBookData> addressBookList = new ArrayList<>();
		try(Connection connection = this.getConnection();)
		
		{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while(resultSet.next()) {
			addressBookList.add(new AddressBookData(resultSet.getString("firstname"), resultSet.getString("lastname"),
					resultSet.getString("address"),resultSet.getString("city"),resultSet.getString("state"),resultSet.getInt("zip"),
					resultSet.getInt("phonenumber"),resultSet.getString("email") ));
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return addressBookList;
	}
}
