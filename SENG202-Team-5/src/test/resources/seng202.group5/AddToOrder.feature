# new feature
# Tags: optional
Feature: Add item to order
    Add item to order and check if costs are correct and ingredients are checked

    Scenario: Item is added to empty order
        Given Order exists
        When Burger is added to order
        Then Order costs $5

    Scenario: Item is added to order with item
        Given Order exists
        And Order contains burger
        When Burger is added to order
        Then Order costs $10

    Scenario: Item is added to order with lack of ingrediants
        Given Order exists
        And there are no buns
        When Burger is added to order
        Then Receive error
