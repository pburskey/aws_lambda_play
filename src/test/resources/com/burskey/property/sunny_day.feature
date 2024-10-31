Feature: Sunny Day

  Background:
    Given an AWS Stage: "Prod"
#    Given a property save uri: "https://zeb8w5qk26.execute-api.us-east-2.amazonaws.com/Stage/save"
#    Given a property get by id uri: "https://zeb8w5qk26.execute-api.us-east-2.amazonaws.com/Stage/"
#    Given a property get by category and name uri: "https://zeb8w5qk26.execute-api.us-east-2.amazonaws.com/Stage/find"
    When I bootstrap the URIs
    Then I have a save uri
    Then I have a find by id uri
    Then I have a find by category and name uri
    Given an AWS Client


  Scenario: Simple Save Property
    Given that I want to save a property
      | Name  | Value  | Description  | Category  |
      | aName | aValue | aDescription | aCategory |
    When I ask the service to save the property
    Then the service responds with status code: 200
    And the property has an id


  Scenario: Simple Find Property By ID
    Given a saved property
    When I ask the service to find the property by ID
    Then the service responds with status code: 200
    And the property exists

  Scenario: Simple Find Property By category and name
    Given a saved property
    When I ask the service to find the property by category and name
    Then the service responds with status code: 200
    And the property exists
