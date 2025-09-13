Feature: Plan a trip

  Scenario: User clicks Plan and selects Manali
    Given the user is on the ixigo homepage
    When the user clicks the Plan icon
    And the user clicks on Manali
    Then the Manali page should open
