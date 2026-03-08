Feature: Product Detail Page

  Background:
    Given I launch the browser
    When I navigate to url "http://automationexercise.com"
    Then I verify that home page is visible successfully

  Scenario: Verify Product Detail Information
    When I click on 'Products' button
    Then I verify user is navigated to All Products page successfully
    And The products list is visible
    When I click on 'View Product' of first product
    Then User is landed to product detail page
    And I verify that product detail is visible:
      | product name |
      | category     |
      | price        |
      | availability |
      | condition    |
      | brand        |