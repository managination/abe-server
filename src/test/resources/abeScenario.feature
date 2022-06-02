Feature: Share encrypted messages
  As a client I want to be able to encrypt a message with a policy in order to share only with authorised peers

  Scenario: Encrypt Arbitrary Message
    Given "AuthorityA" has created "Attribute1" and "Attribute2"
    And a topic "Exchange" exists
    And "Alice" is a client that creates an access structure of "Attribute1 OR Attribute2"
    When "Alice" encrypts the message "Hello World" with the access structure
    Then "Alice" receives a ciphertext which can be decrypted

  Scenario: Decrypt Ciphertext
    Given "AuthorityA" has created "Attribute1" and "Attribute2"
    And "Bob" is a client which has access to the private key of "Attribute1"
    When "Bob" receives the ciphertext of "Hello World" encrypted with the access structure "Attribute1 OR Attribute2"
    Then "Bob" is able to decrypt the message and read "Hello World"