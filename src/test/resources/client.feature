Feature: Client functional
  As a user I want to be able to create, read, update, delete client

  Scenario: Create new Client
    Given client does not exist with email "alice@mail.com"
    When user creates client with name "Alice" and email "alice@mail.com"
    Then client exists with name "Alice" and email "alice@mail.com"

  Scenario: Update existed client
    Given user creates client with name "Alice" and email "alice@mail.com"
    And client exists with name "Alice" and email "alice@mail.com"
    When user updates client with name "Alice" and email "alice@mail.com" to "Alice Jr" and "alice.jr@mail.com"
    Then client exists with name "Alice Jr" and email "alice.jr@mail.com"

  Scenario: Delete existed client
    Given user creates client with name "Alice" and email "alice@mail.com"
    And client exists with name "Alice" and email "alice@mail.com"
    When user delete client with name "Alice" and email "alice@mail.com"
    Then client does not exist with email "alice@mail.com"