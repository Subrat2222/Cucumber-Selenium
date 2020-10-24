package com.cucumberFramework.testRunner;

import com.cucumberFramework.helper.GlobalVariables;
import com.cucumberFramework.reporter.ExtentManager;
import com.cucumberFramework.testBase.TestBase;
import org.testng.annotations.*;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

import java.io.IOException;


@CucumberOptions(features = "src/test/resources/features/order", glue = { "com/cucumberFramework/stepdefinitions" }, plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
		"json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt" },
		monochrome = true, dryRun = false)
public class TestRunner {
TestBase testBase= new TestBase();
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs cucumber Features", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void testDownClass() {
		testNGCucumberRunner.finish();
	}

	/***
	 * Setting up the extent report
	 *
	 * @throws IOException
	 */
	@BeforeSuite
	public void beforeSuite() throws IOException {
		testBase.initializeGlobalVariables();
		GlobalVariables.extentReports = ExtentManager.setupExtentReport();
	}

	@AfterSuite
	public void afterSuite() throws IOException {
		GlobalVariables.extentReports.setSystemInfo("Device Type", GlobalVariables.deviceType);
		if(!GlobalVariables.deviceType.toLowerCase().equals("desktop")) {
			GlobalVariables.extentReports.setSystemInfo("Device Name", GlobalVariables.configProperties.getProperty("deviceName"));
		}
		GlobalVariables.extentReports.flush();
	}

}
