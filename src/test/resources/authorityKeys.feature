Feature: Authority functional
  As a client I want to be able to create, read, update, delete authority

  Scenario: Create Authority with list of attributes
    Given that client defines request body by authorityName and list of attributes: attribute1, attribute2
    When client creates an authority by an authority request body
    Then authority should exists with authorityName and list of attributes: attribute1, attribute2