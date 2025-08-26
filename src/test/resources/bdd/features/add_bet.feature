Feature: Add bets

  Scenario: Add a single bet when bets are already present:
    Given 2 bets in the Database
      And user provides a new odds in the text field
    When click the add button
      And show all bets
    Then bets are correctly displayed