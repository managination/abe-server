Feature: Work with authorities functionality

  Scenario: Create new authority
    Given Client defines "authorityName", "attributesList"
    When Client creates authority with "authorityName", "attributesList"
    Then "Authority" exists in database
