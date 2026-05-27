Feature: Intelligent Multi Attribute Healing

  @intelligentHealing
  Scenario: Heal locator using multiple attributes

    Given user opens demoqa text box page

    When user enters name using broken id locator

    And user enters email using broken name locator

    And user enters address using broken placeholder locator

    And user clicks submit using broken class locator

    Then form should be submitted successfully