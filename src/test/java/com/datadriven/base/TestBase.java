package com.datadriven.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.datadriven.utils.ExcelUtil;
import com.datadriven.utils.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelUtil excel = new ExcelUtil(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\TestData.xlsx");
	public ExtentReports report = ExtentManager.getReportInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
	public void setUp() {
		if(driver==null) {
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			config.load(fis);
			log.debug("Config File loaded!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			OR.load(fis);
			log.debug("OR File loaded!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Browser Build parameter from Jenkins (Optional)
		if(System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		}else {
			browser = config.getProperty("browser");
		}
		
		config.setProperty("browser", browser);
		
		if (config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome browser launched!");
		} else if (config.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
			log.debug("Firefox browser launched!");
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("timeOuts")),
				TimeUnit.SECONDS);
		driver.get(config.getProperty("url"));
		log.debug("Navigated to : " + config.getProperty("url"));	
		}
	}
	
	@BeforeMethod
	public void loginToApp() {
		log.debug("Test execution started");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(OR.getProperty("txtEmailAddress"), 60);
		driver.findElement(By.xpath(OR.getProperty("txtEmailAddress"))).sendKeys(config.getProperty("email"));
		driver.findElement(By.xpath(OR.getProperty("txtPassword"))).sendKeys(config.getProperty("password"));
		driver.findElement(By.xpath(OR.getProperty("btnLogin"))).click();
		
		log.debug("Logged into the application. URL-> " + config.getProperty("url"));
	}
	
	@AfterMethod
	public void logoutOfApp() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waitForElementToBeVisible(OR.getProperty("btnSettingsGear"), 60);
		driver.findElement(By.xpath(OR.getProperty("btnSettingsGear"))).click();
		driver.findElement(By.xpath(OR.getProperty("lnkLogOut"))).click();
		
		log.debug("Logged out of application");
		test.log(LogStatus.INFO, "Logged out of application");
		
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			log.debug("Test Execution completed!");
		}
	}
	
	public WebElement findElement(String locator) {
		waitForElementToBeVisible(locator, 60);
		return driver.findElement(By.xpath(locator));
	}
	
	public void click(String locator) {
		waitForElementToBeClickable(locator, 60);
		driver.findElement(By.xpath(locator)).click();
		log.debug("Clicked on: " + locator);
		test.log(LogStatus.INFO, "Clicked on : " + locator);
	}
	
	public void sendKeys(String locator, String value) {
		waitForElementToBeVisible(locator, 60);
		driver.findElement(By.xpath(locator)).sendKeys(value);
		log.debug("Typed in: " + locator + " Value: " + value);
		test.log(LogStatus.INFO, "Typed in: " + locator + " Value: " + value);
	}  
	
	public void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		log.debug("Moved to element: " + element);
		test.log(LogStatus.INFO, "Moved to element: " + element);
	}
	
	public void waitForElementToBeVisible(String locator, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}
	
	public void waitForElementToBeClickable(String locator, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
	}

	public boolean isElementPresent(String locator) {
		try {
			waitForElementToBeVisible(locator, 60);
			WebElement element = driver.findElement(By.xpath(locator));
			log.debug("Element -> " + element + " found on page");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
