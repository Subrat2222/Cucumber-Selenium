package com.cucumberFramework.testBase;

import com.cucumberFramework.drivers.LocalDriverManager;
import com.cucumberFramework.helper.GlobalVariables;
import com.cucumberFramework.helper.LoggerHelper;
import com.cucumberFramework.pageObjects.HomePage;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;


public class TestBase {
    Logger log = LoggerHelper.getLogger(TestBase.class);
    protected WebDriver driver;
    public HomePage homePage;
    HashMap<String,String> testData = new HashMap<>();
    public TestBase(){
        if(this.driver==null){
            driver = LocalDriverManager.getInstance().getDriver();
        }
        addToTestData("SuccessMessages.properties");
    }


    /***
     * Initialize Global Variables
     */
    public void initializeGlobalVariables() {
        try {
            FileReader reader=new FileReader("config.properties");
            GlobalVariables.configProperties =new Properties();
            GlobalVariables.configProperties.load(reader);
            GlobalVariables.baseDir = System.getProperty("user.dir");
            GlobalVariables.baseURL =GlobalVariables.configProperties.getProperty("url");
            GlobalVariables.browserName = GlobalVariables.configProperties.getProperty("browserName");
            GlobalVariables.pageLoadTimeout = Integer.parseInt(GlobalVariables.configProperties.getProperty("pageLoadTimeout"));
            GlobalVariables.implicitWaitTimeout = Integer.parseInt(GlobalVariables.configProperties.getProperty("implicitWaitTimeout"));

        }catch (IOException e) {
            log.error("Failed to read config.properties"+ e);
        }
    }

    /***
     * Adds data to test data map.
     *
     * @param fileName		FileName data to be added to map
     */
    protected void addToTestData(String fileName) {
        String filePath = "testData/"+fileName;
        Properties prop = null;
        try {
            File file = new File(filePath);
            if(file.exists()) {
                FileReader reader=new FileReader(filePath);
                prop =new Properties();
                prop.load(reader);
                for (final String name: prop.stringPropertyNames()) {
                    testData.put(name, prop.getProperty(name));
                }
            } else {
                log.warn("Test Data File not present: " + fileName);
            }
        }catch (IOException e) {
            log.error("Failed to read "+filePath+" and add to testData map"+ e);
        }
    }

    /***
     * Captures screenshot
     *
     * @return			Returns file path of the screenshot
     */
    synchronized public String captureScreenShot() {
        File srcFile = ((TakesScreenshot) LocalDriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String ts = timestamp.toString().replace(" ", "").replace(".", "_").replace(":", "_");
        String destFilePath = GlobalVariables.baseDir + "/reports/ExtentReports/" + GlobalVariables.currentExtentReportDir + "/screenshots/" + ts + ".jpeg";
        try {
            FileUtils.copyFile(srcFile, new File(destFilePath));
            log.info("Screen shot File " + destFilePath + " copied");
        } catch (Exception e) {
            log.error("Failed to capture to screen shot");
        }
        return destFilePath;
    }


}
