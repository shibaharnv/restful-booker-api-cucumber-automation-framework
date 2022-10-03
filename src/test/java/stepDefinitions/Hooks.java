package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;
import resources.TestDataBuild;

public class Hooks {

    public  static String firstName = "";
    public  static String lastName = "";

    public static String checkInDate="";

    public static String checkOutDate="";

    @Before("@PartialUpdate or @DeleteBooking or @GetBookingIds or @PartialUpdateNegative or @GetBookingDetailWithID")
    public void beforeScenario() throws IOException
    {		//execute this code only when place id is null
        //write a code that will give you place id

        StepDefinition m =new StepDefinition();
        TestDataBuild data =new TestDataBuild();

        if(StepDefinition.bookingID==null)
        {

            //Capturing the booking ID
            m.frame_the_create_booking_payload_with(firstName=data.randomStringGeneration(), lastName=data.randomStringGeneration(), checkInDate=data.checkInDateGeneration(), checkOutDate=data.checkOutDateGeneration());
            m.user_calls_with_http_request("CreateBooking","POST");
            m.validate_if_the_api_call_got_success_with_status_code(200);
            m.store_the_from_the_response_body("bookingid");
        }

    }



}