Feature: Rental Car Details

Background: Send Request
Given Request Is Sent
When List Of All Cars Is Received

Scenario: All Cars 
Then Print All Cars

Scenario: Blue Tesla Cars and Notes
And "Tesla" and "Blue" Is Entered
Then Print The Cars

Scenario: Cars With Lowest PerDayRent Price
Then Print Cars With Lowest PerDayRent Price

Scenario: Cars With Lowest PerDayRent Price After Discount
Then Print Cars With Lowest PerDayRent Price After Discount