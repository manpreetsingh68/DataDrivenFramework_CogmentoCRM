package com.datadriven.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.datadriven.base.TestBase;
import com.datadriven.utils.TestUtil;

public class AddContactsTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addContactsTest(Hashtable<String, String> data) {
		
		if(data.get("runmode").equalsIgnoreCase("N") || data.get("runmode").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping the test case as run mode is set to NO");
		}
		
		log.debug("Add contact test execution started");
		
		waitForElementToBeVisible(OR.getProperty("lnkContacts"), 30);
		moveToElement(findElement(OR.getProperty("lnkContacts")));
		
		click(OR.getProperty("lnkContactsSpan"));
		Assert.assertTrue(isElementPresent(OR.getProperty("btnCreateContact")), "User is not able to land to Add contacts page");
		
		click(OR.getProperty("btnCreateContact"));
		
		Assert.assertTrue(isElementPresent(OR.getProperty("lblCreateNewContact")), "User is not able to land to Create New Contact page");
		
		sendKeys(OR.getProperty("txtFirstName"), data.get("firstname"));
		sendKeys(OR.getProperty("txtLastName"), data.get("lastname"));
		sendKeys(OR.getProperty("txtCompany"), data.get("company"));
		click(OR.getProperty("btnSave"));
	
		Reporter.log("Contact added successfully-> " + data.get("firstname") + " " + data.get("lastname"));
		Assert.assertTrue(isElementPresent(OR.getProperty("btnHome")), "User is not able to create a new contact");
	}
}
