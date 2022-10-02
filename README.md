# restful-booker-api-cucumber-automation-framework 

Restful Booker API testing using rest-assured

This is a  Rest API test automation solution for endpoints available in http://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBookings
The APIs represent a booking system where user can create, get booking details, update ,delete and bookings.

## Technology Stack

- Java
- Cucumber
- Maven
- RestAssured

## Prerequisites
- Java - Version : 1.8 
- Maven - Version : 3.8.1

## The project directory structure

```bash
src
 + main
    + java                          
      + pojo                        pojos of all endpoints                 
      + utilities                   utility email class
 + test
    + java                          
      + cucumber.Options            Test runner and cucumber configurations
      + features                    feature file
      + resources                   property files,Testdata build and common utility methods for testcases.
      + stepdefinitions             Step definitions for the BDD feature
      + logs                        logs.txt for capturing logs 




