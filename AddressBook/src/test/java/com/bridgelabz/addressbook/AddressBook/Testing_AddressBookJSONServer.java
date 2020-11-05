package com.bridgelabz.addressbook.AddressBook;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testing_AddressBookJSONServer {

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private AddressBookData[] getContactList() {
		Response response = RestAssured.get("/AddressBook");
		System.out.println("Address Book Contacts  in json server  are : \n" + response.asString());
		AddressBookData[] arrayOfContacts = new Gson().fromJson(response.asString(), AddressBookData[].class);
		return arrayOfContacts;
	}

	private Response addContactToJsonServer(AddressBookData addressBookData) {
		String contact = new Gson().toJson(addressBookData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contact);
		//System.out.println(contact);
		return request.post("/AddressBook");
	}

	// Test to retrieve contacts from json server
	@Test
	public void givenAddressBookDataInJSONServer_WhenRetreived_ShouldMatchCount() {
		AddressBookData[] arrayOfContacts = getContactList();
		AddressBook_DB contact;
		contact = new AddressBook_DB(Arrays.asList(arrayOfContacts));
		long entries = contact.countEntries();
		Assert.assertEquals(2, entries);
	}
	
	//adding new contact to json server and than counting
		@Test
		public void givenNewEmployee_WhenAdded_ShouldMatchCountand201ResponseAndCount() throws SQLException
		{
			AddressBookData[] arrayOfEmps = getContactList();
		     AddressBook_DB addressBook;
			addressBook = new AddressBook_DB(Arrays.asList(arrayOfEmps));

			AddressBookData addressBookData = null;
			addressBookData = new AddressBookData(0,"sharwan", "singh", "khanpur", "south", "delhi", 123455, 12324435,
					"sharwan@abc.com");
			Response response = addContactToJsonServer(addressBookData);
			int statusCode = response.getStatusCode();
			System.out.println(statusCode);
			Assert.assertEquals(201,statusCode);

			addressBookData = new Gson().fromJson(response.asString(),AddressBookData.class);
			addressBook.addContactToBook(addressBookData);
			long entries = addressBook.countEntries();
			Assert.assertEquals(3,entries);
		}

	// adding multiple contacts to json server and then counting
	@Test
	public void givenMultipleContacts_WhenAdded_ShouldMatchCountand201ResponseAndCount() throws SQLException {
		AddressBookData[] arrayOfContacts = getContactList();
		AddressBook_DB addressBookDB;
		addressBookDB = new AddressBook_DB(Arrays.asList(arrayOfContacts));

		AddressBookData[] arrayOfContactEntries = {
				new AddressBookData(0,"sharwan", "singh", "khanpur", "south", "delhi", 123455, 12324435,
						"sharwan@abc.com"),
				new AddressBookData(0,"ankur", "singh", "khanpur", "south", "delhi", 123455, 12324435, "ankur@abc.com"),
				new AddressBookData(0,"Bittu", "singh", "khanpur", "south", "delhi", 123455, 12324435, "bittu@abc.com"),
				new AddressBookData(0,"Amit", "singh", "khanpur", "south", "delhi", 123455, 12324435, "amit@abc.com") 
				};

		for (AddressBookData addressBookData : arrayOfContactEntries) {
			Response response = addContactToJsonServer(addressBookData);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);

			addressBookData = new Gson().fromJson(response.asString(), AddressBookData.class);
			addressBookDB.addContactToBook(addressBookData);
		}
		long entries = addressBookDB.countEntries();
		Assert.assertEquals(7, entries);
	}
	
	//updating contact details(city) to json server
	  @Test 
	  public void givenNewCityToEmployee_WhenUpdated_ShouldMatch200Response()
	  {
		  AddressBookData[] arrayOfContacts = getContactList();
			AddressBook_DB addressBookDB;
			addressBookDB = new AddressBook_DB(Arrays.asList(arrayOfContacts));

			addressBookDB.updateAddressBookDataJSONServer("Amit", "jaipur");
			AddressBookData addressBookData = addressBookDB.getAddressBookData("Amit");

			 String empJson = new Gson().toJson(addressBookData);
	          RequestSpecification request = RestAssured.given();
	          request.header("Content-Type","application/json");
	          request.body(empJson);
              System.out.println(addressBookData.getId());
              System.out.println(addressBookData.getCity());
	          Response response = request.put("/AddressBook/"+addressBookData.getId());
				int statusCode = response.getStatusCode();
				Assert.assertEquals(200,statusCode);
	  }

}
