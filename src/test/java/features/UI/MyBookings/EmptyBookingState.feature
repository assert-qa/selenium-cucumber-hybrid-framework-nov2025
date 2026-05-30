Feature: My Bookings
  As a logged-in user
  I want to manage my event bookings
  So that I can review and track my registered events

  Background:
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com/login"
    And I enter registered email address and password
    And I click "sign in" button

  Scenario: User has no bookings
    Given I have no event bookings
    When I navigate to My Bookings page
    Then I should see "No bookings found" message