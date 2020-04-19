#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@restapi
Feature: Test Cards API https://deckofcardsapi.com
  Using this template for my feature file

  @tag1 @smoke_api
  Scenario Outline: Test Scenario for Create a new deck of cards GET https://deckofcardsapi.com/api/deck/new/
    Given set base uri of application '<baseURI>'
    When perform get operation for '<serviceURI>'
    Then verify response code '<code>'
    Then verify response body contains:
      | shuffled  | false |
      | remaining |    52 |
    When reset http request
    Given set base uri of application '<baseURI>'
    When perform post operation for '<serviceURI>' with payload as '<requestPayLoad>' with query parameter as:
      | jokers_enabled | true |

    Examples: 
      | baseURI                     | serviceURI   | params               | code | requestPayLoad |
      | https://deckofcardsapi.com/ | api/deck/new | ?jokers_enabled=true |  200 | joker.json     |

  @tag2 @smoke_api
  Scenario Outline: Test Scenario for Shufle card from a deck of cards GET https://deckofcardsapi.com/api/deck/deck_id/draw/?count=2
    Given set base uri of application '<baseURI>'
    When perform get operation for '<newDeckServiceURI>'
    Then verify response code '<code>'
    Then fetch value for '<deck_key>'
    When reset http request
    Given set base uri of application '<baseURI>'
    When perform get operation with parameter '<deck_key>' for '<shuffleServiceURI>' along with query parameter as:
      | count | 2 |
    Then verify response code '<code>'
    Then verify response body contains:
      | remaining  | 50 |


    Examples: 
      | baseURI                     | newDeckServiceURI | code | deck_key | shuffleServiceURI         |
      | https://deckofcardsapi.com/ | api/deck/new      |  200 | deck_id  | api/deck/deck_id/draw/?count=2 |

