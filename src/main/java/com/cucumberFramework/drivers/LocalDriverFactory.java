package com.cucumberFramework.drivers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.HashMap;

public class LocalDriverFactory {


    public static WebDriver createInstance(String browserName) {
        WebDriver driver = null;
        if (browserName.toLowerCase().contains("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            return driver;
        }
        if (browserName.toLowerCase().contains("internet")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
            return driver;
        }
        if (browserName.toLowerCase().contains("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = getChromeDriver();
            return driver;
        }
        return driver;
    }

    private static synchronized ChromeDriver getChromeDriver(){
        ChromeDriverService chromeDriverService = new ChromeDriverService.Builder().usingAnyFreePort().build();
        ChromeOptions options = new ChromeOptions()
                .addArguments("--test-type", "--start-maximized");
        options.addArguments("--disable-infobars", "--disable-browser-side-navigation", "--no-sandbox", "--disable-gpu");
        options.addArguments("--safebrowsing-disable-download-protection");
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("safebrowsing.enabled", "true");
        options.setExperimentalOption("prefs", chromePrefs);
        return new ChromeDriver(chromeDriverService, options);
    }
}
