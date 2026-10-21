package com.airline.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.airline.driverfactory.DriverFactory;
import com.airline.utilities.ExtentManager;
import com.airline.utilities.RetryAnalyzer;




public class TestListener implements ITestListener, IAnnotationTransformer {

//	@Override
//	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//		annotation.setRetryAnalyzer(RetryAnalyzer.class);
//	}

	// Triggered when a test starts
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		// Start logging in Extent Reports
		ExtentManager.startTest(testName);
		ExtentManager.logStep("Test Started: " + testName);
	}

	// Triggered when a Test succeeds
	@Override
	public void onTestSuccess(ITestResult result) {
	    String testName = result.getMethod().getMethodName();

	    if (result.getTestClass().getName().toLowerCase().contains("api")) {
	        ExtentManager.logStepValidationForAPI(
	            "Test End: " + testName + " - Test Passed"
	        );
	        return;
	    }

	    WebDriver driver = DriverFactory.getDriver();

	    if (driver != null) {
	        ExtentManager.logStepWithScreenshot(
	            driver,
	            "Test Passed Successfully!",
	            "Test End: " + testName + " - Test Passed"
	        );
	    } else {
	        ExtentManager.getTest().warning(
	            "Test passed, but screenshot was skipped because WebDriver is unavailable."
	        );
	    }
	}

	// Triggered when a Test Fails

@Override
public void onTestFailure(ITestResult result) {

    String testName = result.getMethod().getMethodName();

    String failureMessage = result.getThrowable() != null
            ? result.getThrowable().getMessage()
            : "No exception message available";

    ExtentManager.logStep(failureMessage);

    // API tests do not require browser screenshots
    if (result.getTestClass().getName()
            .toLowerCase().contains("api")) {

        ExtentManager.logFailureAPI(
                "Test End: " + testName + " - Test Failed");
        return;
    }

    // Retrieve the driver from DriverFactory; no reflection needed
    WebDriver listenerDriver = DriverFactory.getDriver();

    if (listenerDriver == null) {
        System.err.println(
                "Skipping screenshot: WebDriver is null for "
                        + testName);

        ExtentManager.logFailureAPI(
                "Test End: " + testName
                        + " - Test Failed (WebDriver unavailable)");
        return;
    }

    try {
        // Verify that the browser session is still active
        listenerDriver.getWindowHandle();

        ExtentManager.logFailure(
                listenerDriver,
                "Test Failed!",
                "Test End: " + testName + " - Test Failed");

    } catch (org.openqa.selenium.WebDriverException e) {

        System.err.println(
                "Unable to capture failure screenshot for "
                        + testName + ": " + e.getMessage());

        ExtentManager.logFailureAPI(
                "Test End: " + testName
                        + " - Test Failed (Browser session unavailable)");
    }
}


	// Triggered when a Test skips
	@Override
	public void onTestSkipped(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentManager.logSkip("Test Skipped " + testName);
	}

	// Triggered when a suite Starts
	@Override
	public void onStart(ITestContext context) {
		// Initialize the Extent Reports
		ExtentManager.getReporter();
	}

	// Triggered when the suite ends
	@Override
	public void onFinish(ITestContext context) {
		// Flush the Extent Reports
		ExtentManager.endTest();

	}

}


