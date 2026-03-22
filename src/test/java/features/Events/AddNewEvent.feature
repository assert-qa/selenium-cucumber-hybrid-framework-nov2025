Feature: Add New Event
  As an admin
  I want to add new events
  So that users can see new events

  Scenario: Admin adds new event successfully
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com/login"
    Then I verify that "Sign in to EventHub" is visible successfully

    When I enter admin email address and password
    And I click "sign in" button

    When I click "Add New Event" button
    Then I should be redirected to event creation page