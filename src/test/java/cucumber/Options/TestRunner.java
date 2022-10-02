package cucumber.Options;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import  bdd.apiFramework.Email;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(publish = true,features="src/test/java/features",plugin ="json:target/jsonReports/cucumber-report.json",glue= {"stepDefinitions"},tags="@CreateBooking")

//@PartialUpdate or @DeleteBooking  or @GetBookingIds or @CreateBooking

public class TestRunner {

    @BeforeClass
    public static void setUp(){
        System.out.println("THIS IS BEFORE CLASS");
    }

    @AfterClass
    public static void tearDown() throws IOException {

        String recipient=System.getProperty("RecipientList");

        Email.emailTrigger(recipient);
    }
}
