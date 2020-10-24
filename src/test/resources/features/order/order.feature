Feature:Coco Feature File for ordering Pillow using credit card
  Background: Users select the credit card payment method for purchasing pillow
    When User Click on "Buy Now" Button in homepage
    When User Click on "Check out" Button in homepage
    When User Click on "Continue" Button
    Then User Should see the Payment option Selection Page
    When User Click on "CreditDebit" Payment method

  Scenario: Purchasing a pillow using valid Credit card payment method
    Then User Enter Valid Credit card "4811111111111114" and "12/24" and CVV "123"
    Then User Click on "PayNow" Button
    Then User Enter the OTP "112233" details
    Then User Click on "OK" button in OTP details page
    Then Validate the transaction message"Transaction successful" in confirmation page

  Scenario: Purchasing a pillow using valid Credit card payment method with wrong CVV
    Then User Enter Valid Credit card "4811111111111114" and "12/20" and CVV "444"
    Then User Click on "PayNow" Button
    Then User Enter the OTP "112233" details
    Then User Click on "OK" button in OTP details page
    Then Validate the transaction message"Transaction failed" in confirmation page

  Scenario: Purchasing a pillow using invalid Credit card payment method,after updating the valid card
    Then User Enter Valid Credit card "4911111111111113" and "12/20" and CVV "123"
    Then User Click on "PayNow" Button
    Then User Enter the OTP "112233" details
    Then User Click on "OK" button in OTP details page
    Then Validate the transaction message"Transaction failed" in confirmation page
    When User Click on "Use Different Payment Option" Button
    Then User Click on "CreditDebit" Payment method
    Then User Enter Valid Credit card "4811111111111114" and "12/20" and CVV "123"
    Then User Click on "PayNow" Button
    Then User Enter the OTP "112233" details
    Then User Click on "OK" button in OTP details page
    Then Validate the transaction message"Transaction successful" in confirmation page




