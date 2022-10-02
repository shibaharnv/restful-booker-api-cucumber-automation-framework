Feature: Validating Booker Apis

  @CreateBooking
  Scenario Outline: Create a new booking

    Given Frame the create_booking Payload with "<firstname>" "<lastname>" "<checkin>" "<checkout>"
    When  The user calls "<bookingapi>" with "<httpmethod>"
    Then Validate if the API call got success with status code 200
    And  Store the "bookingid" from the response body
    Examples:
      | firstname | lastname | checkin    | checkout   | bookingapi    | httpmethod |
      | fnvalue1  | lnvalue1 | 2018-01-01 | 2018-01-02 | CreateBooking | POST       |


  Scenario: Get the booking details with booking id

    Given Booking API is active
    When The user getting the booking details with "Booking_ID"
    Then Validate if the API call got success with status code 200



  ######ALL for partial update working



  @PartialUpdate
  Scenario Outline: Validate paritalUpdateBooking with names for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking with the "<firstname>" "<lastname>"
    Then  I check if the patch values "<firstname>" "<lastname>" are updated in the response
    Examples:
      | firstname | lastname |
      | fnames1   | lnames1  |
      | fnames2   | lnames2  |


  @PartialUpdate
  Scenario Outline: Validate paritalUpdateBooking with prices for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking with <totalprice>,"<depositpaid>"
    Then  I check if the patch values <totalprice>,"<depositpaid>" are updated in the response
    Examples:
      | totalprice | depositpaid |
      | 111        | true        |
      | 112        | false       |


  @PartialUpdate
  Scenario Outline: Validate paritalUpdateBooking with booking dates for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking dates with the "<checkin>" "<checkout>"
    Then  I check if the patch values "<checkin>" "<checkout>" dates are updated in the response
    Examples:
      | checkin    | checkout   |
      | 2018-01-01 | 2018-01-02 |
      | 2020-01-02 | 2020-01-04 |


  @PartialUpdate
  Scenario Outline: Validate paritalUpdateBooking with additional needs for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking dates with "<additionalneeds>"
    Then  I check if the patch values "<additionalneeds>" dates are updated in the response
    Examples:
      | additionalneeds |
      | dinner          |
      | lunch           |


  @DeleteBooking
  Scenario Outline: Validate delete booking functionality

    Given Setting up the request specification for DeleteBooking
    When  The user calls "<bookingapi>" with "<httpmethod>"
    Then  Ensure deletebooking is working by doing get call and check the <statuscode>
    Examples:
      | bookingapi    | httpmethod | statuscode |
      | DeleteBooking | DELETE     | 404        |









  ### GET ALL BOOKING IDS

  @GetBookingIds
  Scenario: Get all the Bookingids without filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint
    Then  Validate if the API call got success with status code 200
    And   Check if the response contains array of json objects


  @GetBookingIds
  Scenario: Get all the Bookingids with name filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint with names
    Then  Validate if the API call got success with status code 200
    And   Check if the response contains array of json objects


  @GetBookingIds
  Scenario: Get all the Bookingids with date filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint with dates
    Then  Validate if the API call got success with status code 200
    #And   Check if the response contains array of json objects



#
  @GetBookingIds
  Scenario Outline: Get all the Bookingids with filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoints with "<checkin>" and "<checkout>"
    Then  Validate if the API call got success with status code 200
    #And   Check if the response contains array of json objects

    Examples:
      | checkin    | checkout   |
      | 2018-01-01 | 2018-01-02 |










####Above working


#  Scenario Outline: Update a booking in the API
#    Given Booking API is active
#    When I UPDATE a booking
#      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <additionalneeds> |
##    Then I see response has <code> status code
##    And I verify booking request response as per booking model
#
#    Examples:
#      | firstname | lastname | totalprice | depositpaid | additionalneeds | code |
#      | Lease     | Plan     |        100 | true        | Lunch           | 200  |


  ######partial working

#  Scenario Outline: Update a booking in the API
#    Given Booking API is active
#    When I UPDATE a booking
#      | <firstname> | <lastname> |
##    Then I see response has <code> status code
##    And I verify booking request response as per booking model
#
#    Examples:
#      | firstname | lastname |
#      | fnames1     | fnames1 |
#      | fnames2     | fnames2 |
#      | fnames3     | fnames3 |



#  Scenario Outline: Do parital update for names for booker API
#    Given Booking API is active
#    When I UPDATE a bookingsmap
#      | firstname | lastname |
#      | fnames1     | lnames1 |
#      | fnames2     | lnames2 |
#      | fnames3     | lnames3 |
##    Then I see response has <code> status code
##    And I verify booking request response as per booking model
#
#    Examples:
#      | firstname | lastname |
#      | fnames1     | fnames1 |
#      | fnames2     | fnames2 |
#      | fnames3     | fnames3 |



 # Scenario: Validate paritalUpdateBooking with names for booker API
#
#    Given Validate if the create booking API is active
#    When  I partially Update the booking with the values mentioned below
#          | firstname | lastname |
#          | fnames1   | lnames1  |
#          | fnames2   | lnames2  |












#  Scenario Outline: Testing sceanrio
#
#    Given Testing this <totalprice>
#
#    Examples:
#      | totalprice |
#      | true       |





#  Scenario: Do parital update for price for booker API
#    Given Booking API is active
#    When I UPDATE a bookingsmap
#      | price   | depositpaid |
#      | 100 | true     |
#      | 110 | true     |
#      | 120 | true     |

#    Then I see response has <code> status code
#    And I verify booking request response as per booking model



#
#  Scenario: Update a booking in the API
#    Given Booking API is active
#    When I UPDATE a booking
#      | fname1 | lname1 |
#
##    Then  I see response has <code> status code
##    And I verify booking request response as per booking model
#



#  Scenario Outline: Create a new booking
#
#    Given Frame the create_booking Payload with "<firstname>" "<lastname>" "<checkin>" "<checkout>"
#    When user calls "CreateBooking" with "POST" http request
#    Then Validate if the API call got success with status code 200
#    And  Store the "bookingid" from the response body
#    Examples:
#      | firstname | lastname | checkin    | checkout   |
#      | fnvalue1  | lnvalue1 | 2018-01-01 | 2018-01-02 |

#



#  @GetBookingIds
#  Scenario Outline: Get all the Bookingids with filter
#
#    Given Setting up the request specification for get call
#    When  User hits the getBookingIds endpoint with "<firstname>" and "<lastname>"
#    Then  Validate if the API call got success with status code 200
#    And   Check if the response contains array of json objects
#
#    Examples:
#      | firstname | lastname |
#      | test1     | test2    |



