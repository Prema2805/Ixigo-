Feature: Mobile Login

  @PositiveLogin
  Scenario:  Successful login with valid mobile number
    Given the user is on the login page
    When the user enters mobileno as "<Mobile_no>"
    And enters the correct OTP
    Then the user should be navigated to the booking page

   

  @NegativeLogin
  Scenario:  Unsuccessful login with invalid mobile number
    Given the user is on the login page
    When the user enters invalid mobileno as "<Mobile_No>"
    And the user clicks the login button
    Then the system should display "<error_message>"
