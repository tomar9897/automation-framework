Feature: Simple Test
  @tag1
  Scenario: Open Google
    Given I open Google homepage
    When I search for "selenium"
    Then I should see search results

  @tag2
  Scenario: Open DemoQA
    Given Launch the demo QA
    And wait for "10" seconds
    Then get the title
   # Then Click "//button[@id='windowButton']"
    When user perform the following actions
  #   | Locator     |  valuetobeupdated    | action          |
  #   | forms       | Forms                | scrolltoelement |
  #   | forms       | Forms                | click           |
  #   | practiceform|                      | click           |
  # And wait for "10" seconds
  # Given Fill and submit the practice form
     
    