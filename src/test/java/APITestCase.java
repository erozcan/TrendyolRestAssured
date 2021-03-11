

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class APITestCase {

    //actually this class is common for all test cases so we could declare it as abstract class

    private final static String API_ROOT="https://reqres.in/";


    RequestSpecification request;

    @BeforeSuite   //precondition of tests
    public void resetAPIURL(){
        RestAssured.baseURI = API_ROOT;
        request = RestAssured.given();

    }



}


