package cucumber.Options;


import utilities.Email;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;


import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(publish = true,features="src/test/java/features",plugin ="json:target/jsonReports/cucumber-report.json",glue= {"stepDefinitions"},tags="@Regression")

//@PartialUpdate or @DeleteBooking  or @GetBookingIds or @CreateBooking or @PartialUpdateNegative or DeleteBookingNegative
// or GetBookingIdsNegative or GetAllBookingIdsNegative or @Regression or GetBookingDetailWithID

public class TestRunner {

    @BeforeClass
    public static void setUp(){
        System.out.println("This is set up method.");
    }

    @AfterClass
    public static void tearDown() throws IOException {

        String recipient=System.getProperty("RecipientList");

        Email.emailTrigger(recipient);
    }
}
