package com.datadriven.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.datadriven.base.TestBase;
import com.datadriven.utils.TestUtil;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {
	
	public String messageBody;
	
	public void onTestStart(ITestResult result) {
		test = report.startTest(result.getName());
		// Run Modes
		if (!TestUtil.isRunnable(result.getName(), excel)) {
			throw new SkipException("Skipping the test" + result.getName() + " as the Run Mode is set to NO.");
		}
	}

	public void onTestSuccess(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		TestUtil.captureScreenshot();

		Reporter.log("Click to see screenshot");
		Reporter.log("<br>");
		Reporter.log("<a target=\"blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");

		test.log(LogStatus.PASS, result.getName() + " PASS");
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		report.endTest(test);
		report.flush();
	}

	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		TestUtil.captureScreenshot();

		test.log(LogStatus.FAIL, result.getName() + " FAIL" + " Exception: " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

		Reporter.log("Click to see screenshot");
		Reporter.log("<br>");
		Reporter.log("<a target=\"blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");

		report.endTest(test);
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, "Skipping the test " + result.getName() + " as the Run Mode is NO");
		report.endTest(test);
		report.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ISuite suite) {

		/*MonitoringMail mail = new MonitoringMail();

		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenFramework/Extent_20Reports/";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}*/
	}
}

