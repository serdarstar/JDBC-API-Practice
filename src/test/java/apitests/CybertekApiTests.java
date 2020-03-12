package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class CybertekApiTests {

    @BeforeClass
    public void setUpClass() {
        RestAssured.baseURI = ConfigurationReader.get("cyber.uri");

    }

    @Test
    public void students(){

//        Response response = given().accept(ContentType.JSON).when().get("students");
//        System.out.println("response.statusCode() = " + response.statusCode());
//        System.out.println(response.header("Date"));

        Response response1 = given().accept(ContentType.JSON).and().pathParam("id", 3681).when().get("student/{id}");
        System.out.println("response1.statusCode() = " + response1.statusCode());
        response1.body().prettyPrint();


    }

}