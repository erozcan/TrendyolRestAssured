
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;


public class TestBooks extends APITestCase {

  private final static   String BOOKS_ENDPOINT = "/books";

    @Test(priority = 1)
    public void EmptyStoreAtStarts() {

        request
                .when()
                .get(BOOKS_ENDPOINT)
                .then()
                .body("$", Matchers.hasSize(0)).log().body();


    }


    @Test(priority = 2)
    public void TitleIsRequired() {

        Map<String, Object> authorName = new HashMap<>();
        authorName.put("author", "Test Person");

        request
                .contentType("application/json")
                .body(authorName)
                .when()
                .post(BOOKS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Field 'title' is required"))
                .log().body();

    }

    @Test(priority = 3)
    public void AuthorIsRequired() {

        Map<String, Object> bookName = new HashMap<>();
        bookName.put("title", "Suc ve Ceza");

        request
                .contentType("application/json")
                .body(bookName)
                .when()
                .post(BOOKS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Field 'author' is required"))
                .log().body();

    }

    @Test(priority = 4)
    public void EmptyTitle() {

        Map<String, Object> reqparameters = new HashMap<>();
        reqparameters.put("title", "");
        reqparameters.put("author", "TestPerson");

        request
                .contentType("application/json")
                .body(reqparameters)
                .when()
                .post(BOOKS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)//.statusCode(400)
                .assertThat()
                .body("error", equalTo("Field 'title' cannot be empty"))
                .log().body();

    }


    @Test(priority = 5)
    public void EmptyAuthor() {

        Map<String, Object> reqparameters = new HashMap<>();
        reqparameters.put("title", "Suc ve Ceza");
        reqparameters.put("author", "");


        request
                .contentType("application/json")
                .body(reqparameters)
                .when()
                .post(BOOKS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST) //.statusCode(400)
                .assertThat()
                .body("error", equalTo("Field 'author' cannot be empty"))
                .log().body();

    }


    @Test(priority = 6)
    public void IDIsReadOnly() {

        Map<String, Object> reqparameters = new HashMap<>();
        reqparameters.put("id", "5");//you can only read this value
        reqparameters.put("title", "Suç ve Ceza");
        reqparameters.put("author", "Test Person");

        Response response =
                (Response) request
                        .contentType("application/json")
                        .body(reqparameters)
                        .when()
                        .put(BOOKS_ENDPOINT)
                        .then().assertThat().statusCode(200)//it might come different from 200
                        .log().body();

    }

    @Test(priority = 7)
    public void CreateNewBook() {

        Map<String, Object> reqparameters = new HashMap<>();
        reqparameters.put("title", "Suç ve Ceza");
        reqparameters.put("author", "Test Person");

        //create book
        Response putResponse =
                (Response) request
                        .contentType("application/json")
                        .body(reqparameters)
                        .when()
                        .put(BOOKS_ENDPOINT)
                        .then()
                        .assertThat().statusCode(201);

    }

}
