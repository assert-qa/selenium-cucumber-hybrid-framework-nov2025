Feature: My Bookings
  As a logged-in user
  I want to manage my event bookings
  So that I can review and track my registered events

  Background:
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com/login"
    And I enter registered email address and password
    And I click "sign in" button
    And I click "My Bookings" menu

  Scenario: Verify booking information
    Given I have at least one booking
    Then each booking should display:
      | Event Name     |
      | Event Date     |
      | Event Venue    |
      | Booking Date   |
      | Booking Status |