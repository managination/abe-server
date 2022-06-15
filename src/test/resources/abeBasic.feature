Feature: Share encrypted messages
  As a client I want to be able to encrypt a message with a policy in order to share only with authorised peers

  Background:
    Given "AuthorityA" has created "Attribute1" and "Attribute2"
    Given "Bob" is a client which has access to the private key of "Attribute1"
    Given a topic "Exchange" exists

  Scenario: Encrypt and publish Arbitrary Message
    Given anyone creates ciphertext by text "Hello World!" and an access structure of "Attribute1 or Attribute2"
    When anyone publishes ciphertext to the topic "Exchange"
    Then a new ciphertext exists in topic "Exchange"

  Scenario: Decrypt Ciphertext
    Given anyone creates ciphertext by text "Hello World!" and an access structure of "Attribute1 or Attribute2"
    And anyone publishes ciphertext to the topic "Exchange"
    When "Bob" decrypts topic "Exchange"
    Then "Bob" is able to read message "Hello World!"

  Scenario: Do not decrypt Ciphertext
    Given anyone creates ciphertext by text "Hello World!" and an access structure of "Attribute1 and Attribute2"
    And anyone publishes ciphertext to the topic "Exchange"
    When "Bob" decrypts topic "Exchange"
    Then "Bob" is not able to read message "Hello World!"
