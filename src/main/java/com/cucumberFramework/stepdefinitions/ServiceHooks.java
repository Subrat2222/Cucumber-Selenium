package com.cucumberFramework.stepdefinitions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cucumberFramework.drivers.LocalDriverFactory;
import com.cucumberFramework.drivers.LocalDriverManager;
import com.cucumberFramework.helper.GlobalVariables;
import com.cucumberFramework.reporter.ExtentFactory;
import com.cucumberFramework.testBase.TestBase;
import cucumber.runtime.ScenarioImpl;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.cucumberFramework.helper.LoggerHelper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.IOException;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;


public class ServiceHooks{
	protected WebDriver driver;
	ExtentTest extentTest;
	Logger log = LoggerHelper.getLogger(ServiceHooks.class);
TestBase testBase = new TestBase();
	@Before
	public void initializeTest(Scenario scenario) {
		LocalDriverManager.getInstance().setWebDriver(LocalDriverFactory.createInstance(GlobalVariables.browserName));
		driver = LocalDriverManager.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(GlobalVariables.implicitWaitTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(GlobalVariables.pageLoadTimeout,TimeUnit.SECONDS);
		log.info("Driver initialized successfully");
		driver.get(GlobalVariables.baseURL);
		log.info("URL Launched successfully");
		String methodName = scenario.getName();
		log.info(methodName+" Test Method started");
		extentTest = GlobalVariables.extentReports.createTest(methodName);
		ExtentFactory.getInstance().setExtentTest(extentTest);
	}

	@After
	public void endTest(Scenario scenario) {
		if (scenario.isFailed()) {
			ExtentFactory.getInstance().getExtentTest().fail("Test Scenario :  "+scenario.getName()+ " Failed");
			try {
				ExtentFactory.getInstance().getExtentTest().addScreenCaptureFromPath(testBase.captureScreenShot(), scenario.getName());
			} catch (IOException e) {
				log.error("Failed to attach screen shot to extent report "+ e);
			}
		} else {
			try {
				log.info(scenario.getName() + " is Passed");
				ExtentFactory.getInstance().getExtentTest().pass("Test Scenario : "+scenario.getName()+ " Passed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LocalDriverManager.getInstance().closeBrowser();
	}


}
