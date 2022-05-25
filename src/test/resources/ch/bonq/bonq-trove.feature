Feature: Create trove, borrow and repay
  As a bonq user, I want to create a trove
  in order to borrow bEUR from the collateral
  and repay my debt in order to withdraw my collateral

  Scenario: Create Trove
    Given The Trove Factory contract has been deployed
    And The user has at least 1 EWT
    When The user asks the Trove Factory to create a trove with "EWT" as collateral
    Then A new Trove smart contract is deployed
    And The Trove can be found in the list of valid troves with "EWT" as collateral
    And The Trove has a TCR of MAX_INT

  Scenario: Add collateral to existing Trove
    Given A Trove from the list of valid troves with "EWT" as collateral
    And The Trove has 0 debt
    When 1000 "EWT" are transferred to the Trove
    Then The Trove has a TCR of "MAX_INT"

  Scenario: Borrow
    Given A Trove from the list of valid troves with "EWT" as collateral
    And The Trove has 0 debt
    And The Trove has 1000 "EWT" collateral
    And The price of EWT is 10 bEUR
    When The Trove owner borrows 1000 bEUR with "themselves" as recipient
    Then 5 bEUR are transferred to the Fee Recipient contract
    And 1000 bEUR are transferred to "the owner"
    And The net debt is 1005 bEUR
    And The total debt is 1006 bEUR
    And The Trove has a TCR of "10000/1005"
