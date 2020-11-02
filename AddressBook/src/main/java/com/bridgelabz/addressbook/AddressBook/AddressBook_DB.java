package com.bridgelabz.addressbook.AddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.List;

import com.mysql.cj.jdbc.Driver;

public class AddressBook_DB {

	AddressBook_DataBaseService addressBookService = new AddressBook_DataBaseService();
	
	public List<AddressBookData> readData() {
		return addressBookService.readData();
	}

   	
}
