@api
Feature: API Automation Demo

  Scenario: Verify GET users API
    Given user calls GET endpoint "/api/users?page=2"
    Then response code should be 200
    And response should contain key "email"
