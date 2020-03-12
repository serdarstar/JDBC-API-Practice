package Day8;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class JsonSchemaValidation {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = ConfigurationReader.get("spartanapi.uri");
    }

    @Test
    public void jsonSchemaValidation(){
    given().accept(ContentType.JSON).pathParam("id", 660)
            .when().get("/spartans/{id}").then().statusCode(200).body(matchesJsonSchemaInClasspath("SingleSpartanSchema.json"));



    }

    @Test
    public void Verify_number_of_characters(){
        String key="$2a$10$Jyx8ZN7Xf0kmzjWs7mRVh.ugmEbhBwjGgcLoMO81NhFWlWveSHGp.";

        Response response = given().accept(ContentType.JSON).queryParam("key",key).get("https://www.potterapi.com/v1/characters");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        Character character=response.body().as(Character.class);
        System.out.println("character.getId().toString() = " + character.getId().toString());


    }



}