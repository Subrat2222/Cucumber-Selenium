package com.cucumberFramework.pageObjects;

import com.cucumberFramework.helper.LoggerHelper;
import com.cucumberFramework.stepdefinitions.ServiceHooks;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {


    private WebDriver driver;
    private static final int TIMEOUT = 15;
    private static final int POLLING = 30;
    private WebDriverWait wait;
    Logger log = LoggerHelper.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebDriverWait getCustomWebDriverWait(int numberOfSeconds) {
        return new WebDriverWait(driver, numberOfSeconds, POLLING);
    }

    public void switchToDefaultContent(){
        driver.switchTo().defaultContent();
    }

    public void switchToParentFrameToChildFrame(WebElement ParentFrame, WebElement ChildFrame) {
        try {
            driver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);
            log.info("Navigated to inner frame with id " + ChildFrame
                    + "which is present on frame with id" + ParentFrame);
        }
        catch (NoSuchFrameException e) {
            log.info("Unable to locate frame with id " +ParentFrame + " or "
                    + ChildFrame + e.getStackTrace());
        } catch (Exception e) {
            log.info("Unable to navigate to inner frame with id "
                    + ChildFrame + "which is present on frame with id"
                    + ParentFrame + e.getStackTrace());
        }


    }

    public void switchToFrame(WebElement ParentFrame) {
        try {
            driver.switchTo().frame(ParentFrame);
            log.info("Navigated to frame with id " + ParentFrame);
        }
        catch (NoSuchFrameException e) {
            log.info("Unable to locate frame with id " +ParentFrame);
        } catch (Exception e) {
            log.info("Unable to navigate to inner frame with id "
                    + ParentFrame + e.getStackTrace());
        }
    }

    public void safeJavaScriptClick(WebElement element) {
        try {
            if (element.isEnabled() && element.isDisplayed()) {
                log.info("Clicking on element with using java script click");

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } else {
                log.info("Unable to click on element");
            }
        } catch (StaleElementReferenceException e) {
            log.error("Element is not attached to the page document "+ e.getStackTrace());
        } catch (NoSuchElementException e) {
            log.error("Element was not found in DOM "+ e.getStackTrace());
        } catch (Exception e) {
            log.error("Unable to click on element "+ e.getStackTrace());
        }
    }

}
