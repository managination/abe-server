Feature: Authority functional
  As a client I want to be able to create, read, update, delete authority

  Scenario: Create Authority with list of attributes
    Given that client defines request payload by "authorityName" and list of attributes: "attribute1", "attribute2"
    When client creates an authority by an authority request payload
    Then authority should exists with "authorityName" and list of attributes: "attribute1", "attribute2"

  Scenario: Subscribe authority public keys to all public keys
      Given that public keys object exists
      When an authority is created by "authorityName" and list of attributes: "attribute1", "attribute2"
      And its public keys subscribes to all public keys
      Then all public keys object should contain authority public keys

  Scenario: Client get personal key from authority
    Given that client "Bob" has personal keys "bobsPeKs" and authority "firstAuthority" with attribute "attr1" exists
    When client "Bob" requests personal key by "attr1" from "firstAuthority"
    Then object "bobsPeKs" should contain personal key by "attr1" from "firstAuthority"
