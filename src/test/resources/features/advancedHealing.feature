Feature: Advanced Self Healing Validation

  @healing
  Scenario: Validate multiple locator healing and cache reuse

    Given I open Google homepage

    # Search box healing
    When I search using broken xpath

    Then I should see search results

    # Reuse same broken locator again
    When I search again using same broken xpath

    Then I validate current url contains "persistent+healing+cache"

  @healing
  Scenario: Validate healing rejection for invalid locator

    Given I open Google homepage

    When I search using invalid broken xpath

    Then I should see healing failure handled safely