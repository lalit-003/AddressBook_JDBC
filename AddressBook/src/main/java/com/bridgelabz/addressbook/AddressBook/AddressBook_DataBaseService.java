package com.bridgelabz.addressbook.AddressBook;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.Empty;

public class AddressBook_DataBaseService {
	
	private PreparedStatement addressBookDataStatement;
	private static AddressBook_DataBaseService addressBookDBService;
	
	public static AddressBook_DataBaseService getInstance() {
		if (addressBookDBService == null)
			addressBookDBService = new AddressBook_DataBaseService();
		return addressBookDBService;
	}
	

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		Connection connection;
		System.out.println("Connecting to database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, "theharyanaking");
		System.out.println("Connection is successful !! " + connection);
		return connection;
	}
	public List<AddressBookData> getList( ResultSet resultSet) throws SQLException
	{
		List<AddressBookData> addressBookList= new ArrayList<>();
		while(resultSet.next()) {
			addressBookList.add(new AddressBookData(resultSet.getString("firstname"), resultSet.getString("lastname"),
					resultSet.getString("address"),resultSet.getString("city"),resultSet.getString("state"),resultSet.getInt("zip"),
					resultSet.getInt("phonenumber"),resultSet.getString("email") ,resultSet.getDate("date_added")));
		}
		return addressBookList;
	}
	
	public List<AddressBookData> readData()
	{
		String sql = "Select * from address_table;";
		List<AddressBookData> addressBookList = new ArrayList<>();
		try(Connection connection = this.getConnection();)
		
		{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		addressBookList = this.getList(resultSet);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return addressBookList;
	}

	public int updateEmployeeSalaryResult(String firstname, String city) {
		String sql = String.format("update address_table set city ='%s' where firstname = '%s';", city,firstname);
		try(Connection connection = this.getConnection();)
		{
		Statement statement = connection.createStatement();
		return statement.executeUpdate(sql);
				}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<AddressBookData> getAddressBookData(String firstname) {
		List<AddressBookData> addressBookList = new ArrayList<>();
		if (addressBookDataStatement == null)
		{
			prepareStatementForAddressBookData();
		}
		try {
		addressBookDataStatement.setString(1, firstname);
		ResultSet resultSet = addressBookDataStatement.executeQuery();
		addressBookList = this.getList(resultSet);
		} catch (Exception e) {
				e.printStackTrace();
			}
			return addressBookList;
		}


	private void prepareStatementForAddressBookData() {
		try {
			Connection connection = getConnection();
			String sql = "SELECT * FROM address_table WHERE firstname = ?";
			addressBookDataStatement = connection.prepareStatement(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int updateCityUsingSQL(String firstname, String city) {
		String sql = "UPDATE address_table SET city = ? WHERE firstname = ? ";
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, firstname);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	public List<AddressBookData> getAddressBookDataForDateRange(Date startDate, Date endDate) {
		String  sql=String.format("select * from address_table where date_added between '%s' AND '%s';",
				(startDate),(endDate));
		return getAddressBookDataUsingDB(sql);
	}


	private List<AddressBookData> getAddressBookDataUsingDB(String sql) {
		List<AddressBookData> addressBookList= new ArrayList<>();
		try(Connection connection = this.getConnection()){
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			addressBookList=this.getList(resultSet);
					}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(addressBookList);
	   return addressBookList;	
	}
}
