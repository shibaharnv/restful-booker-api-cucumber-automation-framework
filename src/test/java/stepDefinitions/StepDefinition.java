package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

import static io.restassured.RestAssured.given;
import static stepDefinitions.Hooks.*;


public class StepDefinition extends Utils {

    ResponseSpecification responseSpec;

    RequestSpecification reqObj;
    Response response;

    TestDataBuild data = new TestDataBuild();

    static String bookingID;

    String newToken;


    //This method sets request specification and payload
    @Given("Frame the create_booking Payload with {string} {string} {string} {string}")
    public void frame_the_create_booking_payload_with(String firstName, String lastName, String checkIn, String checkOut) throws IOException {

        reqObj = given().spec(requestSpecificationMethod()).body(data.createBookingPayload(firstName, lastName, checkIn, checkOut));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String string, String string2) {

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().post("/booking").then().spec(responseSpec).log().all().extract().response();
    }


    @Then("Validate if the API call got success with status code {int}")
    public void validate_if_the_api_call_got_success_with_status_code(int status_code) {
        assertEquals(response.getStatusCode(), status_code);
    }

    @Then("Store the {string} from the response body")
    public void store_the_from_the_response_body(String bookingid) {

        String res = response.asString();
        JsonPath js = new JsonPath(res);
        bookingID = js.get(bookingid).toString();
        System.out.println("bookingID " + bookingID);
    }


    @Given("Booking API is active")
    public void booking_api_is_active() throws IOException {

        //Capturing the booking ID
        frame_the_create_booking_payload_with("fsname", "lastname", "checkin", "checkout");
        user_calls_with_http_request("CreateBooking", "POST");
        validate_if_the_api_call_got_success_with_status_code(200);
        store_the_from_the_response_body("bookingid");

        //Setting the request specification
        reqObj = given().spec(requestSpecificationMethod());

    }

    @When("The user getting the booking details with {string}")
    public void the_user_getting_the_booking_details_with(String Booking_ID) {

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().get("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

    }

    //partial update


    @Given("Validate if the create booking API is active")
    public void validate_if_the_create_booking_api_is_active() throws IOException {

        //Capturing the booking ID
        frame_the_create_booking_payload_with(data.randomStringGeneration(), data.randomStringGeneration(), data.checkInDateGeneration(), data.checkOutDateGeneration());
        user_calls_with_http_request("CreateBooking", "POST");
        validate_if_the_api_call_got_success_with_status_code(200);
        store_the_from_the_response_body("bookingid");

        //Setting the request specification
        reqObj = given().spec(requestSpecificationMethod());
    }


    @Given("Setting up the request specification")
    public void setting_up_the_request_specification() throws IOException {
        reqObj = given().spec(requestSpecificationMethod());
        newToken = tokenGeneration();
        System.out.println(newToken);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken);
    }


    @Given("Setting up the request specification for partialUpdateBooking")
    public void setting_up_the_request_specification_for_partial_update_booking() throws IOException {
        reqObj = given().spec(requestSpecificationMethod());
        newToken = tokenGeneration();
        System.out.println(newToken);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken);
    }


    @When("I partially Update the booking with the {string} {string}")
    public void i_partially_update_the_booking_with_the(String firstName, String lastName) throws IOException {

        reqObj = reqObj.body(data.partialUpdateNamesPayload(firstName, lastName));
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

    }


    @Then("I check if the patch values {string} {string} are updated in the response")
    public void i_check_if_the_patch_values_are_updated_in_the_response(String expectedFirstName, String expectedLastName) {

        String actual_firstName = getJsonPath(response, "firstname");
        String actual_lastname = getJsonPath(response, "lastname");
        assertEquals(actual_firstName, expectedFirstName);
        assertEquals(actual_lastname, expectedLastName);
        System.out.println("Assertion Passed:Partial name update Working successfully");

    }


    @When("I partially Update the booking with {int},{string}")
    public void i_partially_update_the_booking_with(Integer totalPrice, String depositPaid) throws IOException {

        newToken = tokenGeneration();
        System.out.println(newToken);
        Boolean depositpaidBoolean = Boolean.valueOf(depositPaid);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken)
                .body(data.partialUpdatePricesPayload(totalPrice, depositpaidBoolean));
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();


    }


    @Then("I check if the patch values {int},{string} are updated in the response")
    public void i_check_if_the_patch_values_are_updated_in_the_response(Integer expectedTotalPrice, String expectedDepositPaid) {

        String actual_totalPrice = getJsonPath(response, "totalprice");
        String actual_DepositPaid = getJsonPath(response, "depositpaid");
        assertEquals(actual_totalPrice, expectedTotalPrice.toString());
        assertEquals(actual_DepositPaid, expectedDepositPaid);
        System.out.println("Assertion Passed:Partial price update Working successfully");
    }


    @When("I partially Update the booking dates with the {string} {string}")
    public void i_partially_update_the_booking_dates_with_the(String checkIn, String checkOut) throws IOException {

        newToken = tokenGeneration();
        System.out.println(newToken);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken)
                .body(data.partialUpdateBookingDatePayload(checkIn, checkOut));
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

    }



    @When("I partially Update the values with {string} {string} for {int}")
    public void i_partially_update_the_values_with_for(String param1, String param2, Integer statusCode) throws IOException {

        if (statusCode == 403) {
            String invalidToken = newToken + "xyz";


            if (isStringOnlyAlphabet(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + invalidToken)
                        .body(data.partialUpdateNamesPayload(param1, param2));
            }

            if (isStringOnlyDate(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + invalidToken)
                        .body(data.partialUpdateBookingDatePayload(param1, param2));
            }

            if (isStringOnlyDigits(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + invalidToken)
                        .body(data.partialUpdateBookingDatePayload(param1, param2));
            }

            if(param2.equalsIgnoreCase("null"))
            {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + invalidToken)
                        .body(data.partialUpdateAdditonalPayload(param1));
            }

            responseSpec = new ResponseSpecBuilder().expectStatusCode(403).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

        }
        if (statusCode == 400) {

            if (isStringOnlyAlphabet(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + newToken)
                        .body(data.partialUpdateBookingDatePayload(param1, param2) + "dummydata");
            }

            if (isStringOnlyDate(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + newToken)
                        .body(data.partialUpdateBookingDatePayload(param1, param2) + "dummydata");
            }

            if (isStringOnlyDigits(param1)) {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + newToken)
                        .body(data.partialUpdateBookingDatePayload(param1, param2) + "dummydata");
            }

            if(param2.equalsIgnoreCase("null"))
            {
                reqObj = given().spec(requestSpecificationMethod())
                        .header("Cookie", "token=" + newToken)
                        .body(data.partialUpdateAdditonalPayload(param1) + "dummydata");
            }

            responseSpec = new ResponseSpecBuilder().expectStatusCode(400).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

        }


    }


    @Then("Validate the {int} from response")
    public void validate_the_from_response(Integer statusCode) {

        assertEquals(response.getStatusCode(), (int) statusCode);
        System.out.println("Status code Assertion Passed ");


    }


    @Then("I check if the patch values {string} {string} dates are updated in the response")
    public void i_check_if_the_patch_values_dates_are_updated_in_the_response(String expectedcheckIn, String expectedcheckOut) {

        String actual_checkin = getJsonPath(response, "bookingdates.checkin");
        String actual_checkout = getJsonPath(response, "bookingdates.checkout");
        assertEquals(actual_checkin, expectedcheckIn);
        assertEquals(actual_checkout, expectedcheckOut);
        System.out.println("Assertion Passed:Partial booking dates update Working successfully");


    }


    @When("I partially Update the booking needs with {string}")
    public void i_partially_update_the_booking_needs_with(String additionalNeeds) throws IOException {

        newToken = tokenGeneration();
        System.out.println(newToken);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken)
                .body(data.partialUpdateAdditonalPayload(additionalNeeds));
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().patch("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

    }

    @Then("I check if the patch values {string} dates are updated in the response")
    public void i_check_if_the_patch_values_dates_are_updated_in_the_response(String expectedAdditionalNeeds) {

        String actual_additionalneeds = getJsonPath(response, "additionalneeds");
        assertEquals(actual_additionalneeds, expectedAdditionalNeeds);
        System.out.println("Assertion Passed:Partial additonal need update Working successfully");
    }


    @Given("Setting up the request specification for DeleteBooking")
    public void setting_up_the_request_specification_for_delete_booking() throws IOException {
        reqObj = given().spec(requestSpecificationMethod());
        newToken = tokenGeneration();
        System.out.println(newToken);
        reqObj = given().spec(requestSpecificationMethod())
                .header("Cookie", "token=" + newToken);
    }

    @When("The user calls {string} with {string}")
    public void the_user_calls_with(String bookingApi, String httpMethod) throws IOException {

        if (bookingApi.equalsIgnoreCase("DeleteBooking") && httpMethod.equalsIgnoreCase("DELETE")) {

            responseSpec = new ResponseSpecBuilder().expectStatusCode(201).build();
            response = reqObj.when().delete("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();
        }

        if (bookingApi.equalsIgnoreCase("CreateBooking") && httpMethod.equalsIgnoreCase("Post")) {
            responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
            response = reqObj.when().post("/booking").then().spec(responseSpec).log().all().extract().response();
        }

    }

    @Then("Ensure deletebooking is working by doing get call and check the {int}")
    public void ensure_deletebooking_is_working_by_doing_get_call_and_check_the(Integer statusCode) {

        responseSpec = new ResponseSpecBuilder().expectStatusCode(statusCode).build();
        response = reqObj.when().get("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

    }


    @When("The user calls {string} with {string} to check {int}")
    public void the_user_calls_with_to_check(String bookingApi, String httpMethod, Integer statusCode) throws IOException {

        if(statusCode==403)
        {
            String invalidToken = newToken + "xyz";
            reqObj = given().spec(requestSpecificationMethod())
                    .header("Cookie", "token=" + invalidToken);
            responseSpec = new ResponseSpecBuilder().expectStatusCode(403).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().delete("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();

        }

        if(statusCode==400 && httpMethod.equalsIgnoreCase("PUT"))
        {
            responseSpec = new ResponseSpecBuilder().expectStatusCode(400).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().put("/booking/" + bookingID + "").then().spec(responseSpec).log().all().extract().response();
        }


        if(statusCode==405 && bookingApi.equalsIgnoreCase("DeleteBooking"))
        {

            responseSpec = new ResponseSpecBuilder().expectStatusCode(405).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().delete("/booking/" + "xyzzz").then().spec(responseSpec).log().all().extract().response();
        }



    }

    @Given("Setting up the request specification for get call")
    public void setting_up_the_request_specification_for_get_call() throws IOException {

        reqObj = given().spec(requestSpecificationMethod());
    }


    @When("The user hits on the getBookingIds with parameters {string} and {string} for {int}")
    public void the_user_hits_on_the_get_booking_ids_with_parameters_and_for(String param1, String param2, Integer statusCode) throws IOException {

        if (statusCode == 404) {

            if (isStringOnlyAlphabet(param1)) {

                responseSpec = new ResponseSpecBuilder().expectStatusCode(404).expectContentType(ContentType.TEXT).build();
                response = reqObj.queryParam("firstname", param1)
                        .queryParam("lastname", param2)
                        .when().put("/booking/").then().spec(responseSpec).log().all().extract().response();
            }


            if (isStringOnlyDate(param1)) {

                responseSpec = new ResponseSpecBuilder().expectStatusCode(404).expectContentType(ContentType.TEXT).build();
                response = reqObj.queryParam("checkin", param1)
                        .queryParam("checkout", param2)
                        .when().put("/booking/").then().spec(responseSpec).log().all().extract().response();
            }


        }

    }



    @When("User hits the getBookingIds endpoint")
    public void user_hits_the_get_booking_ids_endpoint() {

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.when().get("/booking/").then().spec(responseSpec).log().all().extract().response();
    }


    @When("User hits the getBookingIds endpoint for {int}")
    public void user_hits_the_get_booking_ids_endpoint_for(Integer statusCode) {

        if(statusCode==500) {
            responseSpec = new ResponseSpecBuilder().expectStatusCode(500).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().post("/booking/").then().spec(responseSpec).log().all().extract().response();
        }

        if(statusCode==404) {
            responseSpec = new ResponseSpecBuilder().expectStatusCode(404).expectContentType(ContentType.TEXT).build();
            response = reqObj.when().patch("/booking/").then().spec(responseSpec).log().all().extract().response();
        }
    }


    @Then("Check if the response contains array of json objects")
    public void check_if_the_response_contains_array_of_json_objects() {

        //Capturing the booking ids in a list
        List<String> completedlist = response.jsonPath().getList("bookingid");
        assertFalse(completedlist.size() == 0);
        System.out.println("Assertion passed :Get allbooking ids response contains json array of objects");

    }


    @When("User hits the getBookingIds endpoint with names")
    public void user_hits_the_get_booking_ids_endpoint_with_names() {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .when().get("/booking/").then().spec(responseSpec).log().all().extract().response();
    }

    @When("User hits the getBookingIds endpoint with dates")
    public void user_hits_the_get_booking_ids_endpoint_with_dates() {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.queryParam("checkin", checkInDate)
                .queryParam("checkout", checkOutDate)
                .when().get("/booking/").then().spec(responseSpec).log().all().extract().response();
    }


    @When("User hits the getBookingIds endpoints with {string} and {string}")
    public void user_hits_the_get_booking_ids_endpoints_with_and(String checkIn, String checkOut) {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.queryParam("checkin", checkIn)
                .queryParam("checkout", checkOut)
                .when().get("/booking/").then().spec(responseSpec).log().all().extract().response();
    }

    @When("User hits the getBookingIds name endpoints with {string} and {string}")
    public void user_hits_the_get_booking_ids_name_endpoints_with_and(String firstName, String lastName) {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = reqObj.queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .when().get("/booking/").then().spec(responseSpec).log().all().extract().response();
    }




}
