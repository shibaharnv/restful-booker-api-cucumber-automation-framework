package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Utils {

    public static RequestSpecification req;

    public RequestSpecification requestSpecificationMethod() throws IOException {

        if(req==null) {

            PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));

            req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;

    }

    public String tokenGeneration() throws IOException {


        String payLoad="{\n" +
                "    \"username\" : \""+getGlobalValue("username")+"\",\n" +
                "    \"password\" : \""+getGlobalValue("password")+"\"\n" +
                "}";

        Response response=given().baseUri(getGlobalValue("baseUrl"))
                .contentType("application/json")
                .body(payLoad)
                .when()
                .post("/auth").then().log().all().extract().response();

        String token= response.jsonPath().getString("token");
        return token;
    }


    public static String getGlobalValue(String key) throws IOException {

        Properties prop = new Properties();
        String path =System.getProperty("user.dir");
        String end="/src/test/java/resources/global.properties";
        String fullPath =path.concat(end);
        FileInputStream fis =new FileInputStream(fullPath);
        prop.load(fis);
        return prop.getProperty(key);

    }


    public String getJsonPath(Response response,String key)
    {
        String resp=response.asString();
        JsonPath   js = new JsonPath(resp);
        return js.get(key).toString();
    }




//    public static String getGlobalValue(String key) throws IOException
//    {
//        Properties prop =new Properties();
//
//        String path =System.getProperty("user.dir");
//
//        String end="/src/test/java/resources/global.properties";
//
//        String fullPath =path.concat(end);
//        //FileInputStream fis =new FileInputStream("/Users/snagarajan/Desktop/ApiTestAutomation/APIFramework/src/test/java/resources/global.properties");
//        FileInputStream fis =new FileInputStream(fullPath);
//        prop.load(fis);
//        return prop.getProperty(key);
//
//    }
}
