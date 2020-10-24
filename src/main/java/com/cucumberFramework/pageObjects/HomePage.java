package com.cucumberFramework.pageObjects;

import java.util.List;
import java.util.NoSuchElementException;
import com.cucumberFramework.helper.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

	WebDriver driver;
	Logger log = LoggerHelper.getLogger(HomePage.class);

	@FindBy(xpath="//a[@class='btn buy']")
	private WebElement buyNow;

	@FindBy(xpath="//div[@class='cart-checkout']")
	private WebElement cartCheckout;

	@FindBy(xpath="//div[@class='text-button-main']")
	private WebElement continueButton;

	@FindBy(xpath="//div[@class='list-title text-actionable-bold' and contains(text(),'Credit/Debit Card')]")
	private WebElement creditAndDebitCard;

	@FindBy(xpath="//input[@name='cardnumber']")
	private WebElement cardNumber;

	@FindBy(xpath="//input[@placeholder='MM / YY']")
	private WebElement expiryDate;

	@FindBy(xpath="//input[@inputmode='numeric']")
	private WebElement cvvNumber;

	@FindBy(xpath="//div[@class='text-button-main']")
	private WebElement payNowButton;

	@FindBy(xpath="//input[@type='email' and @class='filled']")
	private WebElement emailIdInPaymentPage;

	@FindBy(xpath="//input[@type='password']")
	private WebElement OTP;

	@FindBy(xpath="//button[@name='ok']")
	private WebElement okButton;

	@FindBy(xpath="//iframe[@id='snap-midtrans']")
	private WebElement parentFrame;

	@FindBy(xpath="//*[@id='application']/div[3]/div/div/div/iframe")
	private WebElement child_frame;

	@FindBy(xpath="//select[@id='quantity' or @name='quantity']")
	private List<WebElement> qtyField;

	@FindBy(xpath="//span[@class='text-page-title']")
	private WebElement paymentSelectionTitle;

	@FindBy(xpath="//div[@class='text-success text-bold']")
	private WebElement transactionSuccessMessageText;
	@FindBy(xpath="//div[@class='text-failed text-bold']")
	private WebElement transactionFailMessageText;

	@FindBy(xpath="//div[@class='text-button-main']")
	private WebElement chooseDifferentPaymentOption;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean clickOnBuyNowButton(){
		try {
			waitForElementToBeClickable(buyNow);
			buyNow.click();
		}catch(NoSuchElementException e){
			log.error(e);
		}
		return true;
	}

	public boolean clickOnButtonNameWithoutFrame(String buttonName) {
		boolean status = true;
		try {
//			JavascriptExecutor js = (JavascriptExecutor)driver;
			switch (buttonName.toLowerCase()) {
				case "buy now":
//					safeJavaScriptClick(buyNow);
//					js.executeScript("arguments[0].click();", buyNow);
					waitForElementToBeClickable(buyNow);
					buyNow.click();
					break;
				case "check out":
//					safeJavaScriptClick(cartCheckout);
//					js.executeScript("arguments[0].click();", cartCheckout);
					waitForElementToBeClickable(cartCheckout);
					cartCheckout.click();
					break;
			}
		} catch (Exception e) {
			status = false;
			log.error("Failed to click on butotn Name: "+buttonName, e);
		}
		return status;
	}

	public String transactionMessage(String expectedMessage) {
		switchToFrame(parentFrame);
		String messageText = "";
		try {
			switch (expectedMessage.toLowerCase()) {
				case "transaction successful":
					if (transactionSuccessMessageText.isDisplayed()) {
						messageText = transactionSuccessMessageText.getText();
					} else {
						messageText = ((JavascriptExecutor) driver).executeScript(
								"return arguments[0].innerHTML", transactionSuccessMessageText).toString().replace("<span>","").replace("</span>","");
					}
					break;
				case "transaction failed":
					if (transactionFailMessageText.isDisplayed()) {
						messageText = transactionFailMessageText.getText();

					} else {
						messageText = ((JavascriptExecutor) driver).executeScript(
								"return arguments[0].innerHTML", transactionFailMessageText).toString().replace("<span>","").replace("</span>","");
					}
					break;
			}
		} catch(NoSuchElementException ne) {
			log.error("status message not displayed..... checking from the page source"+ ne);
		}
		switchToDefaultContent();
		return messageText.trim();
	}

	public String getTitleOnPaymentSelectionPage(){

		switchToFrame(parentFrame);
		String titlePaymentSelection="";
		try {
			waitForElementToBeClickable(paymentSelectionTitle);
			titlePaymentSelection = paymentSelectionTitle.getText().trim();
		}catch(NoSuchElementException e){

		}
		switchToDefaultContent();
		return titlePaymentSelection;
	}

	public boolean clickOnCreditAndDebitCard(){
		switchToFrame(parentFrame);
		try {
			waitForElementToBeClickable(creditAndDebitCard);
			creditAndDebitCard.click();
		}catch(NoSuchElementException e) {
			log.error(e);
		}
		switchToDefaultContent();
		return true;

	}

	public boolean clickOnCheckOutButton() {
		try {
			waitForElementToBeClickable(cartCheckout);
			cartCheckout.click();
		}catch(NoSuchElementException e){
			log.error(e);
		}
		return true;
	}

	public boolean enterOTPDetails(String otpDetails) {
		switchToParentFrameToChildFrame(parentFrame,child_frame);
		try {
			waitForElementToBeClickable(OTP);
			OTP.sendKeys(otpDetails);
		}catch(NoSuchFrameException e){
			log.error(e);
		}
		switchToDefaultContent();
		return true;

	}

	public void clickOnButtonName(String buttonName) {
		boolean status = true;
		try {
			switchToFrame(parentFrame);
			switch (buttonName.toLowerCase()) {
				case "continue" -> {
//					waitForElementToBeClickable(continueButton);
					safeJavaScriptClick(continueButton);
				}
				case "paynow" -> {
//					waitForElementToBeClickable(payNowButton);
					safeJavaScriptClick(payNowButton);
				}
				case "use different payment option" -> {
//					waitForElementToBeClickable(chooseDifferentPaymentOption);
					safeJavaScriptClick(chooseDifferentPaymentOption);
				}
			}
		} catch (Exception e) {
			status = false;
//			log.error("Failed to click on butotn Name: "+buttonName, e);
		}
		switchToDefaultContent();
	}

	public boolean clickOnPaymentType(String PaymentType) {
		boolean status = true;
		try {
			switchToFrame(parentFrame);
			switch (PaymentType.toLowerCase()) {
				case "creditdebit":
//					waitForElementToBeClickable(creditAndDebitCard);
					creditAndDebitCard.click();
					break;
			}
		} catch (Exception e) {
			status = false;
			log.error("Failed to click on butotn Name: "+PaymentType, e);
		}
		switchToDefaultContent();
		return status;
	}

	public boolean enterCreditCardDetails(String cardNumberDetails,String expiryDateDetails,String cvvDetails ) throws InterruptedException {
		try {
			switchToFrame(parentFrame);
			waitForElementToBeClickable(cardNumber);
			cardNumber.sendKeys(cardNumberDetails);
			waitForElementToBeClickable(expiryDate);
			expiryDate.sendKeys(expiryDateDetails);
			waitForElementToBeClickable(cvvNumber);
			cvvNumber.sendKeys(cvvDetails);
		} catch (Exception e) {
			log.error("Failed to enter the details", e);
		}
		switchToDefaultContent();
		return true;
	}

	public boolean clickOnButtonInOTPDetailsPage(String PaymentType) {
		boolean status = true;
		try {
			switchToParentFrameToChildFrame(parentFrame,child_frame);
			switch (PaymentType.toLowerCase()) {
				case "ok":
//					waitForElementToBeClickable(okButton);
					okButton.click();
					break;
			}
		} catch (Exception e) {
			status = false;
			log.error("Failed to click on butotn Name: "+PaymentType, e);
		}
		switchToDefaultContent();
		return status;
	}

}
