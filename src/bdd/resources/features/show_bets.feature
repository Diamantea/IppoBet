Feature: Show all available bets

  Scenario: Show all available bets:
    Given 2 bets in the Database
    When show all bets
    Then bets are correctly displayed