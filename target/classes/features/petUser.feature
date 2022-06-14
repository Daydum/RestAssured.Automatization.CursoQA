Feature: petUser

  @postPetUser
  Scenario Outline: (1) Validate post request (create a new user)
    Given the following post request for create a new user
    And the response is 200 for create a new user
    Then the body response for create a new user contains "<code>" and "<message>"
    Examples:
      | code | message |
      | 200  | 1408    |

  @getUserByUsername
  Scenario: (2) Validate get request (get user by username)
    Given obtain user by username
    And the response is 200 for user by username

  @getLogin
  Scenario Outline: (3) Validate get request (logs user into the system)
    Given the user provides the username "<username>" and the password "<password>"
    And the response is 200 for user Login
    Examples:
      | username | password |
      | day      | pet      |

  @getLogout
  Scenario: (4) Validate get request (logs out current logged in user session)
    Given the following request for session logout
    And the response is 200 for logout the session

  @putPetUser
  Scenario Outline: (5) Validate put request (update created user)
    Given the following put request that update the created user
    And the response is 200 for the updated user
    Then the body response for update a new user contains "<code>" and "<message>"
    Examples:
      | code | message |
      | 200  | 1408    |

  @deleteUser
  Scenario Outline: (6) Validate delete request (delete created user)
    Given the following delete request that delete user
    And the response is 200 for the delete user
    Then the delete user body response contains "<code>" and "<message>"
    Examples:
      | code | message |
      | 200  | day     |

