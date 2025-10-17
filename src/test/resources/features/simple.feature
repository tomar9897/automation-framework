Feature: Simple Test
  @tag1
  Scenario: Open Google
    Given I open Google homepage
    When I search for "selenium"
    Then I should see search results

    @tag2
  Scenario: Open DemoQA
    Given Launch the demo QA