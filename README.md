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
      
```      

## Installation and Test Execution

- Clone the repository with below command

```
git clone -b main https://github.com/shibaharnv/restful-booker-api-cucumber-automation-framework.git

```


Open the project in any IDE Eclipse/IntelliJ.

Navigate to "restful-booker-api-cucumber-automation-framework" folder where we have pom.xml 

Run the following command in Terminal and build the project. It will automatically download all the required dependencies.

```
$ mvn clean install
```

## Execute Tests

Run the below command where "-DRecipientList" parameter value can be updated to your email id.

Test results report link will be sent to the mentioned email id.

### After running the below command please wait for less than a minute since all the maven logs are captured in project basedir/mavenlogs.txt file location.

mvn -Dcucumber.filter.tags="@CreateBooking" test verify --log-file mavenlogs.txt -DRecipientList="shibaharn@gmail.com"

Once the execution is done you must be receiving an email with test results link



