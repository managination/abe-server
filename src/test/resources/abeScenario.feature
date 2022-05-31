Feature: Share encrypted messages
  As a client I want to be able to encrypt a message with a policy in order to share only with authorised peers

  Scenario: Encrypt Arbitrary Message
    Given "AuthorityA" has created "Attribute1" and "Attribute2"
    And a topic "Exchange" exists
    And "Alice" is a client that creates an access structure of "Attribute1 OR Attribute2"
    When "Alice" encrypts the message "Hello World" with the access structure
    Then "Alice" receives a ciphertext which can be decrypted