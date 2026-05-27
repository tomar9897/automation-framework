Feature: Self Healing Framework Validation

  @cacheHealing
  Scenario: Validate cached healing reuse
    Given user opens demoqa text box page
    When user enters name using broken id locator
    And user enters name again using same broken locator
    Then cached healing should work successfully

  @deterministicHealing
  Scenario: Validate deterministic healing without AI
    Given user opens demoqa text box page
    When user enters name using broken id locator
    And user enters email using broken name locator
    And user enters address using broken placeholder locator
    Then deterministic healing should complete successfully

  @aiHealing
  Scenario: Validate AI fallback healing
    Given user opens demoqa text box page
    When user clicks submit using broken class locator
    Then AI healing should recover locator successfully

  @safeRejection
  Scenario: Validate safe rejection for impossible locator
    Given user opens demoqa text box page
    When user clicks using impossible broken locator
    Then framework should safely reject healing

  @multiHealing
  Scenario: Validate multiple healing flows together
    Given user opens demoqa text box page
    When user enters name using broken id locator
    And user enters email using broken name locator
    And user enters address using broken placeholder locator
    And user clicks submit using broken class locator
    Then complete healing flow should work successfully