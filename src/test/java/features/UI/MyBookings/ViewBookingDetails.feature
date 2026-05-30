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

  Scenario: User views booking details
    Given I have at least one booking
    When I click on any booked event
    Then I should be redirected to booking detail page
    And I should see complete booking information