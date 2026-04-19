Feature: Basic UI Validation

  @tag3
  Scenario: Verify Google Search Works
    Given I open Google homepage
    When I search for "selenium"
    Then I should see search results

  @tag3
  Scenario: Verify Google Title
    Given I open Google homepage
    Then I validate page title contains "Google"