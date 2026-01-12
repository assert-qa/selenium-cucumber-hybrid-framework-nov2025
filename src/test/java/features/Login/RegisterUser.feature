Feature: User Registration
  As a new user
  I want to register an account
  So that I can access the application features

  @smokeTest
  Scenario: Register User
    Given I launch the browser
    When I navigate to url "https://automationexercise.com/"
    Then I verify that home page is visible successfully
    When I click on "Signup / Login" button
    Then I verify "New User Signup!" is visible
    When I enter name "name" and email address "email"
    And I click "Signup" button
    Then I verify that "ENTER ACCOUNT INFORMATION" is visible
    When I fill account details with title "Mr.", password "Test@123", date of birth "15", month "May", year "1990"
    And I select checkbox "Sign up for our newsletter!"
    And I select checkbox "Receive special offers from our partners!"
    And I fill address details:
      | First name  | Test          |
      | Last name   | User          |
      | Company     | Test Company  |
      | Address     | 123 Main St   |
      | Address2    | Apt 4B        |
      | Country     | United States |
      | State       | California    |
      | City        | Los Angeles   |
      | Zipcode     | 90001         |
      | Mobile Number | 1234567890  |
    And I click "Create Account" button
    Then I verify that "ACCOUNT CREATED!" is visible
    When I click "Continue" button
    Then I verify that "Logged in as username" is visible
    When I click "Delete Account" button
    Then I verify that "ACCOUNT DELETED!" is visible
    And I click "Continue" button