package com.cucumberFramework.helper;


import com.aventstack.extentreports.ExtentReports;

import java.util.Properties;

public class GlobalVariables {

	public static Properties configProperties = new Properties();
	public static String baseURL;
	public static String currentExtentReportDir;
	public static String baseDir;
	public static String browserName;
	public static ExtentReports extentReports;
	public static String deviceType = "desktop";
	public static int pageLoadTimeout;
	public static int implicitWaitTimeout;
	
}
