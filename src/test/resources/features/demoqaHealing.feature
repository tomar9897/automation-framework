Feature: DemoQA Self Healing Validation

  @demohealing
  Scenario: Validate multiple locator healing on DemoQA form

    Given I open DemoQA text box page

    When I enter full name using broken locator

    And I enter email using broken locator

    And I enter address using broken locator

    And I click submit using broken locator

    Then I validate form submitted successfully

  @demohealing
  Scenario: Validate persistent cache reuse on DemoQA

    Given I open DemoQA text box page

    When I enter full name again using broken locator

    Then I validate current url contains "text-box"

  @demohealing
  Scenario: Validate healing rejection on DemoQA

    Given I open DemoQA text box page

    When I use invalid broken locator on DemoQA

    Then I should see healing failure handled safely