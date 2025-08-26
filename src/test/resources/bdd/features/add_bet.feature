Feature: Add bets

  Scenario: Add a single bet when bets are already present
    Given 2 bets in the Database
    When show all bets
    And user provides a new odds in the text field
    And click the add button
    Then bets are correctly displayed