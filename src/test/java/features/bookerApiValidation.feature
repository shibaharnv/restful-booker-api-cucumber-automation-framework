Feature: Validating Booker Apis

  @CreateBooking @Regression
  Scenario Outline: Create a new booking

    Given Frame the create_booking Payload with "<firstname>" "<lastname>" "<checkin>" "<checkout>"
    When  The user calls "<bookingapi>" with "<httpmethod>"
    Then Validate if the API call got success with status code 200
    And  Store the "bookingid" from the response body
    Examples:
      | firstname | lastname | checkin    | checkout   | bookingapi    | httpmethod |
      | fnvalue1  | lnvalue1 | 2018-01-01 | 2018-01-02 | CreateBooking | POST       |

  @GetBookingDetailWithID @Regression
  Scenario: Get the booking details with booking id

    Given Setting up the request specification for get call
    When The user getting the booking details with "Booking_ID"
    Then Validate if the API call got success with status code 200


  @PartialUpdate @Regression
  Scenario Outline: Validate paritalUpdateBooking with names for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking with the "<firstname>" "<lastname>"
    Then  I check if the patch values "<firstname>" "<lastname>" are updated in the response
    Examples:
      | firstname | lastname |
      | fnames1   | lnames1  |
      | fnames2   | lnames2  |


  @PartialUpdate @Regression
  Scenario Outline: Validate paritalUpdateBooking with prices for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking with <totalprice>,"<depositpaid>"
    Then  I check if the patch values <totalprice>,"<depositpaid>" are updated in the response
    Examples:
      | totalprice | depositpaid |
      | 111        | true        |
      | 112        | false       |


  @PartialUpdate @Regression
  Scenario Outline: Validate paritalUpdateBooking with booking dates for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking dates with the "<checkin>" "<checkout>"
    Then  I check if the patch values "<checkin>" "<checkout>" dates are updated in the response
    Examples:
      | checkin    | checkout   |
      | 2018-01-01 | 2018-01-02 |
      | 2020-01-02 | 2020-01-04 |


  @PartialUpdate @Regression
  Scenario Outline: Validate paritalUpdateBooking with additional needs for booker API

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the booking needs with "<additionalneeds>"
    Then  I check if the patch values "<additionalneeds>" dates are updated in the response
    Examples:
      | additionalneeds |
      | dinner          |
      | lunch           |


  @PartialUpdateNegative @PartialUpdate @Regression
  Scenario Outline: Validate different error status codes paritalUpdateBooking

    Given Setting up the request specification for partialUpdateBooking
    When  I partially Update the values with "<parameter1>" "<parameter2>" for <statuscode>
    Then  Validate the <statuscode> from response

    Examples:
      | statuscode | parameter1 | parameter2 |
      | 403        | firstname  | lastname   |
      | 403        | 2018-01-01 | 2018-02-01 |
      | 403        | 111        | true       |
      | 403        | breakfast  | null       |
      | 400        | abcname    | xyzname    |
      | 400        | 2018-02-03 | 2018-04-05 |
      | 400        | 111        | true       |
      | 400        | dinner     | null       |


  @DeleteBooking @Regression
  Scenario Outline: Validate delete booking functionality

    Given Setting up the request specification for DeleteBooking
    When  The user calls "<bookingapi>" with "<httpmethod>"
    Then  Ensure deletebooking is working by doing get call and check the <statuscode>
    Examples:
      | bookingapi    | httpmethod | statuscode |
      | DeleteBooking | DELETE     | 404        |


  @DeleteBookingNegative @DeleteBooking @Regression
  Scenario Outline: Validate delete booking functionality

    Given Setting up the request specification for DeleteBooking
    When  The user calls "<bookingapi>" with "<httpmethod>" to check <statuscode>
    Then  Validate the <statuscode> from response
    Examples:
      | bookingapi    | httpmethod | statuscode |
      | DeleteBooking | DELETE     | 403        |
      | DeleteBooking | PUT        | 400        |
      | DeleteBooking | DELETE     | 405        |


  @GetBookingIds @Regression
  Scenario: Get all the Bookingids without filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint
    Then  Validate if the API call got success with status code 200
    And   Check if the response contains array of json objects


  @GetAllBookingIdsNegative @Regression
  Scenario Outline: Get all the Bookingids without filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint for <statuscode>
    Then  Validate the <statuscode> from response


    Examples:
      | statuscode |
      | 500        |
      | 404        |

  @GetBookingIds @Regression
  Scenario: Get all the Bookingids with name filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoint with names
    Then  Validate if the API call got success with status code 200
    And   Check if the response contains array of json objects


  @GetBookingIds @Regression
  Scenario Outline: Get all the Bookingids with name filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds name endpoints with "<firstname>" and "<lastname>"
    Then  Validate if the API call got success with status code 200
    And   Check if the response contains array of json objects

    Examples:
      | firstname | lastname |
      | test1     | test2    |






#
  @GetBookingIds @Regression
  Scenario Outline: Get all the Bookingids with filter

    Given Setting up the request specification for get call
    When  User hits the getBookingIds endpoints with "<checkin>" and "<checkout>"
    Then  Validate if the API call got success with status code 200
    #And   Check if the response contains array of json objects

    Examples:
      | checkin    | checkout   |
      | 2018-01-01 | 2018-01-02 |
      | 2018-02-01 | 2018-03-02 |


  @GetBookingIdsNegative @GetBookingIds @Regression
  Scenario Outline: Get all the Bookingids with filter

    Given Setting up the request specification for get call
    When  The user hits on the getBookingIds with parameters "<parameter1>" and "<parameter2>" for <statuscode>
    Then  Validate the <statuscode> from response

    Examples:
      | statuscode | parameter1 | parameter2 |
      | 404        | zzzz       | xxxx       |
      | 404        | 2018-01-01 | 2018-02-01 |












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



  #  @GetBookingIds @Regression
#  Scenario: Get all the Bookingids with date filter
#
#    Given Setting up the request specification for get call
#    When  User hits the getBookingIds endpoint with dates
#    Then  Validate if the API call got success with status code 200
#    #And   Check if the response contains array of json objects



  ######partial working @PartialUpdate
  ###@Testing
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



  ## Negative testing
#  @PartialUpdateNegative @PartialUpdate
#  Scenario Outline: Validate different error status codes paritalUpdateBooking
#
#    Given Setting up the request specification for partialUpdateBooking
#    When  I partially Update the booking names for <statuscode> with the "<firstname>" "<lastname>"
#    Then  Validate the <statuscode> from response
#
#    Examples:
#      | statuscode | firstname  | lastname  |
#      | 403        | abc | xyz |
#      | 400        | edf | ghi |
##      | 404        | firstname3 | lasstname3 |


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


#  @Testing
#  Scenario: Validate paritalUpdateBooking with names for booker API
#
#    Given Validate if the create booking API is active
#    When  I partially Update the booking with the values mentioned below
#          | firstname | lastname |
#          | fnames1   | lnames1  |
#          | fnames2   | lnames2  |







#    @Testing
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



