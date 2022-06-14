Feature: pet

  @getById
  Scenario: (1) Validate get request (find pet by id)
    Given obtain pet by 1408
    And the response is 200 for pet by id

  Scenario: (2) Validate get request (find pet by wrong id)
    Given obtain pet by 1405
    And the response is 404 for pet by id

  @getPetByStatus
  Scenario: (3) Validate get request (find pet by status)
    Given obtain pet by status
    And the response is 200 for pet by status

  @postPet
  Scenario Outline: (4) Validate post request (add new pet)
    Given post request that add a new pet
    And the response is 200 for create new pet
    Then the body contain the "<name>", the "<id>" and the "<status>" of the created pet
    Examples:
      | name | id   |  status   |
      | Jack | 1408 | available |

  @putPet
  Scenario Outline: (5) Validate put request (update created pet)
    Given the following put request that update the created pet
    And the response is 200 for the put
    Then the body response contains update "<updated_name>"
    Examples:
      | updated_name |
      | Jack Sparrow |

  @deletePet
  Scenario Outline: (6) Validate delete request (delete created pet)
    Given the following delete request that delete pet
    And the response is 200 for the delete
    Then the body response contains "<code>" and "<message>"
    Examples:
      |  code  |  message  |
      |  200   |  1408     |






























