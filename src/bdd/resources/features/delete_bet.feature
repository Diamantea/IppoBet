Feature: Delete bet

  Scenario: Delete the first bet in the list
    Given 2 bets in the Database
    When show all bets
      And user select the first bet in the list
      And click the delete button
    Then first bet in the list is correctly deleted