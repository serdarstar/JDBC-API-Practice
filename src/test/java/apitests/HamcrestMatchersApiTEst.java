package apitests;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

public class HamcrestMatchersApiTEst {


    /*
    given accept type is Json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106
     */

        @Test
        public void singleSpartanbyChaining(){

            given().accept(ContentType.JSON)
                    .and().pathParam("id", 15)
                    .when().get("http://54.161.128.36:8000/api/spartans/{id}")
                    .then().assertThat().statusCode(200)
                    .and().assertThat().contentType("application/json;charset=UTF-8")
                    .and().assertThat().body("id",equalTo(15),
                    "name",equalTo("Meta")
                    ,"gender",equalTo("Female"),
                    "phone",equalTo(1938695106));


        }

    @Test
    public void teacherData(){
        given().accept(ContentType.JSON).and()
                .pathParam("name","Madham").and()
                .when().get("http://api.cybertektraining.com/teacher/name/{name}")
                .then().assertThat().statusCode(200)
                .and().contentType("application/json;charset=UTF-8")
                .and().body("teachers.firstName",equalTo("Madham"),
                "teachers.lastName[0]",equalTo("Mask"),
                "teachers.emailAddress[0]",equalTo("jackma@gmail.com"));


    }

    @Test
    public void teacherAllDataWithHamcrest(){
        given().accept(ContentType.JSON)
                .pathParam("name","Computer").and()
                .when().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().assertThat().statusCode(200).contentType("application/json;charset=UTF-8")
                .and().body("teachers.firstName",hasItems("Madham","Ruslan","Alihan"));


    }
}
