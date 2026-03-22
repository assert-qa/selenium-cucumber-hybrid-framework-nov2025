Feature: Filter Events by Category
  As a user
  I want to filter events by category
  So that I can find relevant events

  Scenario: User filter events by category
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com"
    Then I verify that "Upcoming Events" is visible successfully

    When I select "Concert" from category dropdown
    Then I should see only "Concert" events displayed