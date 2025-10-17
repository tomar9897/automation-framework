Feature: Simple Test
  
  Scenario: Open Google
    Given I open Google homepage
    When I search for "selenium"
    Then I should see search results
