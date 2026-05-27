Feature: Add New Event
  As an admin
  I want to add new events
  So that users can see new events

  Scenario: Admin adds new event successfully
    Given I launch the browser
    When I navigate to url "https://eventhub.rahulshettyacademy.com/login"
    And I enter registered email address and password
    And I click "sign in" button
    And I navigate to "Events" menu
    Then I verify that "Upcoming Events" is visible successfully

    When I click "Add New Event" button
    Then I should be redirected to event creation page
    And I enter event title "<title>"
    And I enter event description "<description>"
    And I select event category "<category>"
    And I enter event venue "<venue>"
    And I enter event city "<city>"
    And I enter ticket price "<price>"
    And I enter total seats "<seats>"
    And I click "Add Event" button

    Examples:
      | title                    | description                | category   | venue                     | city    | price | seats |
      | QA Automation Conference | Selenium and API workshop  | Conference | Jakarta Convention Center | Jakarta | 1500  | 500   |