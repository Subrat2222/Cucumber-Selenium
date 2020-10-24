package com.cucumberFramework.drivers;

import org.openqa.selenium.WebDriver;

public class LocalDriverManager {

    private LocalDriverManager() {

    }

    public static LocalDriverManager getInstance() {
        return instance;
    }
    private static LocalDriverManager instance = new LocalDriverManager();
    ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    public WebDriver getDriver() {
        return webDriver.get();
    }

    public void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public void closeBrowser() {
        webDriver.get().close();
        webDriver.remove();
    }
}
