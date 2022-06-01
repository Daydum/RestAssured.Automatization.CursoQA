Feature: petStore

  @getInventoryByStatus
  Scenario: (1) Validate get request (returns pet inventories by status)
    Given obtain pet inventory by status
    And the response is 200 for return inventory by status

  @postPetOrder
  Scenario Outline: (2) Validate post request (place an order for a pet)
    Given post request that place an order for a pet
    And the response is 200 for the order placed
    Then the body contain the "<petId>", "<quantity>" and "<status>"of the placed order
    Examples:
      | petId | quantity | status |
      | 1408  |     2    | placed |

  @getPetOrderById
  Scenario: (3) Validate get request (find purchase order by ID)
    Given obtain pet order by 2
    And the response is 200 for pet order by ID

  @deletePetOrder
  Scenario Outline: (4) Validate delete request (find purchase order by ID)
    Given the following delete request that delete pet order by id = 2
    And the response is 200 for the delete pet order
    Then the delete body response contains "<code>" and "<message>"
    Examples:
      | code | message |
      | 200  |    2    |