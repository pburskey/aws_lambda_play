Feature: Sunny Day

  Background:
    Given an AWS Region: "us-east-2"
    Given an AWS S3 Bucket: "pburskey-home"
    Given an AWS Stack Name: "PropertyConfiguration"
    Given an AWS Stage: "Prod"
    Given a property save uri: "https://ltje43gxdf.execute-api.us-east-2.amazonaws.com/Prod/save"
    Given a property get by id uri: "https://ltje43gxdf.execute-api.us-east-2.amazonaws.com/Prod/"
    Given a property get by category and name uri: "https://ltje43gxdf.execute-api.us-east-2.amazonaws.com/Prod/find"
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
