Feature: Combined Filters
  As a user
  I want to apply multiple filters
  So that I get more specific results

  Scenario: User apply category and city filters
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com"
    Then I verify that "Upcoming Events" is visible successfully

    When I select "Conference" from category dropdown
    And I select "Hyderabad" from city dropdown
    Then I should see "Conference" events in "Hyderabad"