package com.bridgelabz.addressbook.AddressBook;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class Testing_AddressBook
{
    AddressBook_DB addressBook = new AddressBook_DB() ;
    
    // UC16 - Ability to retrieve data from database
         @Test
        public void  givenAddressBookData_WhenReturned_ShouldMatchEntryCount()
        {
        	List<AddressBookData> addressBookList = addressBook.readData();
        	Assert.assertEquals(7,addressBookList.size());
        }
         
      // UC17 update and sync data in database (using prepared statement)
 		@Test
 		public void givenCity_WhenUpdated_ShouldSyncWithDatabaseUsingPreparedStatement()
 		{
 			List<AddressBookData> addressBookData = addressBook.readData();
 			addressBook.updateAddressBookData("Priya","Chandigarh");
 			boolean result = addressBook.checkAddressBookDataSyncWithDB("Priya");
 			Assert.assertTrue(result);
 		}
}
