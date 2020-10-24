package com.cucumberFramework.stepdefinitions;

import com.cucumberFramework.helper.LoggerHelper;
import com.cucumberFramework.pageObjects.HomePage;
import com.cucumberFramework.testBase.TestBase;
import cucumber.api.PendingException;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.testng.Assert;


public class HomePageStepDefinitions extends TestBase {

	public HomePageStepDefinitions(){
		homePage= new HomePage(driver);
	}
	Logger log = LoggerHelper.getLogger(HomePageStepDefinitions.class);
	@When("^User click on Buy Now Button$")
	public void userClickOnBuyNowButton() {
		Assert.assertTrue(homePage.clickOnBuyNowButton(),"Buy now button does not present in home page");
	}

	@Then("^User should see the checkoutPage$")
	public void userShouldSeeTheCheckoutPage()  {
		Assert.assertTrue(homePage.clickOnCheckOutButton(),"Checkout button does not displayed");
	}

	@When("^User Click on Checkout Button$")
	public void userClickOnCheckoutButton() {
		Assert.assertTrue(homePage.clickOnCheckOutButton(),"Checkout button does not present");
	}

	@When("^User Click on \"([^\"]*)\" Payment method$")
	public void userClickOnPaymentMethod(String paymentName) throws InterruptedException {
		homePage.clickOnPaymentType(paymentName);
	}

	@When("^User Click on \"([^\"]*)\" Button$")
	public void userClickOnButton(String buttonName) throws InterruptedException {
		homePage.clickOnButtonName(buttonName);
	}

	@When("^User Click on Credit Card Payment method$")
	public void userClickOnCreditCardPaymentMethod() {
		homePage.clickOnCreditAndDebitCard();
	}

	@Then("^User Should see the Payment option Selection Page$")
	public void userShouldSeeThePaymentSelectionPage() throws InterruptedException {
		String actualTitle = homePage.getTitleOnPaymentSelectionPage();
		Assert.assertTrue(actualTitle.contains("Select Payment"),"Payment selection title is not displayed");
	}

	@Then("^User Enter Valid Credit card \"([^\"]*)\" and \"([^\"]*)\" and CVV \"([^\"]*)\"$")
	public void userEnterValidCreditCardAndAndCVV(String cardNumber, String expiryDate, String cvvNumber) throws Throwable {
		homePage.enterCreditCardDetails(cardNumber,expiryDate,cvvNumber);
	}

	@Then("^User Enter the OTP \"([^\"]*)\" details$")
	public void userEnterTheOTPDetails(String otpDetails) throws Throwable {
		homePage.enterOTPDetails(otpDetails);
	}

	@Then("^User Click on \"([^\"]*)\" button in OTP details page$")
	public void userClickOnButtonInOTPDetailsPage(String buttonName) throws Throwable {
		homePage.clickOnButtonInOTPDetailsPage(buttonName);
	}

	@Then("^Validate the transaction message\"([^\"]*)\" in confirmation page$")
	public void validateTheTransactionMessageInConfirmationPage(String expectedMessage) throws Throwable {
			String actualMessage = homePage.transactionMessage(expectedMessage).trim();
			Assert.assertEquals(actualMessage,expectedMessage,actualMessage+" and " + expectedMessage+ "does not matched");
	}

	@When("^User Click on \"([^\"]*)\" Button in homepage$")
	public void userClickOnButtonWithoutFrame(String buttonName) throws Throwable {
		homePage.clickOnButtonNameWithoutFrame(buttonName);
	}

}