Feature: Client functional
  As a user I want to be able to create, read, update, delete client

  Scenario: Create new Client
    Given client does not exist with email "alice@mail.com"
    When user creates client with name "Alice" and email "alice@mail.com"
    Then client exists with name "Alice" and email "alice@mail.com"

  Scenario: Update existed client
    Given user creates client with name "Alice2" and email "alice2@mail.com"
    And client exists with name "Alice2" and email "alice2@mail.com"
    When user updates client with name "Alice2" and email "alice2@mail.com" to "Alice Jr" and "alice.jr@mail.com"
    Then client exists with name "Alice Jr" and email "alice.jr@mail.com"

  Scenario: Delete existed client
    Given user creates client with name "Alice3" and email "alice3@mail.com"
    And client exists with name "Alice3" and email "alice3@mail.com"
    When user delete client with name "Alice3" and email "alice3@mail.com"
    Then client does not exist with email "alice3@mail.com"

  Scenario: Create new Client
    Given client does not exist with email "bob@mail.com"
    When user creates client with name "Bob" and email "bob@mail.com"
    Then client exists with name "Bob" and email "bob@mail.com"

  Scenario: Create new Client
    Given client does not exist with email "anna@mail.com"
    When user creates client with name "Anna" and email "anna@mail.com"
    Then client exists with name "Anna" and email "anna@mail.com"

  Scenario: Create new Client
    Given client does not exist with email "maria@mail.com"
    When user creates client with name "Maria" and email "maria@mail.com"
    Then client exists with name "Maria" and email "maria@mail.com"

  Scenario: Create new Client
    Given client does not exist with email "john@mail.com"
    When user creates client with name "John" and email "john@mail.com"
    Then client exists with name "John" and email "john@mail.com"