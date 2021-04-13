package com.datadriven.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.datadriven.base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date date = new Date();
		screenshotName = date.toString().replace(":", " ").replace(" ", "_") + ".jpg";
		
		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method method) {
		String sheetName = method.getName();
		int rows = excel.getRowCount(sheetName);
		int columns = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String, String> table = null;
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			
			table = new Hashtable<String, String>();
			
			for (int colNum = 0; colNum < columns; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}
	
	public static boolean isRunnable(String testName, ExcelUtil excel) {
		
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		
		for(int rowNum = 2; rowNum <= rows; rowNum++) {
			String testCase = excel.getCellData(sheetName, "TestCaseID", rowNum);
			
			if(testCase.equalsIgnoreCase(testName)) {
				String runMode = excel.getCellData(sheetName, "RunMode", rowNum);
				
				if(runMode.equalsIgnoreCase("Y") || runMode.equalsIgnoreCase("Yes"))
					return true;
				else
					return false;
			}
		}
		return false;
	}
}
