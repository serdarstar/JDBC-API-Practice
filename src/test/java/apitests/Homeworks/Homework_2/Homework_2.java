package apitests.Homeworks.Homework_2;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

public class Homework_2 {

    @BeforeClass

    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("spartanapi.uri");
    }

    @Test
    public void Q1(){
        Response response=given().accept(ContentType.JSON).
                and().pathParam("id",20).when().get("/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json;charset=UTF-8");
        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.header("Transfer-Encoding"),"chunked");

        int id=response.body().path("id");
        assertEquals(id,20);

        assertEquals(response.body().path("name"), "Lothario");
        assertEquals(response.body().path("gender"),"Male");

        long phone=response.body().path("phone");
        assertEquals(phone,7551551687L);
    }

    @Test
    public void Q2(){
        Response response=given().accept(ContentType.JSON).and().
                queryParam("gender", "Female")
                .queryParam("nameContains","r").when().get("/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(), "application/json;charset=UTF-8");

        List<String> gender=response.path("content.gender");
        System.out.println("gender = " + gender);
        for (String s : gender) {
            assertEquals(s,"Female");
        }

        List<String> names=response.path("content.name");
        System.out.println(names);
        boolean contains_r=false;
        for (int i = 0; i < names.size(); i++) {
          if(names.get(i).toLowerCase().contains("r")){
              System.out.println("ok");
              contains_r=true;

          }
            assertEquals(contains_r,true);
        }


    }

}
