package apitests.Homeworks.Homework_1;
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

public class Homework_1_Hamcrest {
    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("hrapi.uri");
    }
    @Test
    public void Homework_1_Q1_Hamcrest(){

        given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"country_id\":\"US\"}")
                .when().get("/countries")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .assertThat().body("items.country_id[0]",equalTo("US"),
                                    "items.country_name[0]",equalTo("United States of America"),
                                   "items.region_id[0]",equalTo(2)
                );

    }
    @Test
    public void Homework_1_Q2_Hamcrest(){

        given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"department_id\":80}")
                .when().get("/employees")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .assertThat().body("items.job_id", hasItems("SA_MAN","SA_REP"))
               ;

    }


}
