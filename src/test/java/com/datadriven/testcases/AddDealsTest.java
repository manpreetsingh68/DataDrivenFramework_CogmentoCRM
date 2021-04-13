package com.datadriven.testcases;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.datadriven.base.TestBase;
import com.datadriven.utils.TestUtil;

public class AddDealsTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void addDealsTest(Hashtable<String, String> data) {
		
		if(data.get("runmode").equalsIgnoreCase("N") || data.get("runmode").equalsIgnoreCase("No")) {
			throw new SkipException("Skipping the test case as run mode is set to NO");
		}
		
		log.debug("Add deals test execution started");
		
		waitForElementToBeVisible(OR.getProperty("lnkDeals"), 30);
		moveToElement(findElement(OR.getProperty("lnkDeals")));
		
		click(OR.getProperty("lnkDealsSpan"));
		Assert.assertTrue(isElementPresent(OR.getProperty("btnCreateDeal")), "User is not able to land to Add Deals page");
		
		click(OR.getProperty("btnCreateDeal"));
		
		Assert.assertTrue(isElementPresent(OR.getProperty("lblCreateNewDeal")), "User is not able to land to Create New Deal page");
		
		sendKeys(OR.getProperty("txtTitle"), data.get("title"));
		sendKeys(OR.getProperty("txtCompany"), data.get("company"));
		sendKeys(OR.getProperty("txtDescription"), data.get("description"));
		click(OR.getProperty("btnSave"));
	
		Reporter.log("Deal added successfully-> " + data.get("title") + " " + data.get("company"));
		Assert.assertTrue(isElementPresent(OR.getProperty("btnHome")), "User is not able to create a new deal");
	}
}
