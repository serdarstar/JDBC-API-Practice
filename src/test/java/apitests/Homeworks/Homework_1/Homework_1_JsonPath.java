package apitests.Homeworks.Homework_1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class Homework_1_JsonPath {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("hrapi.uri");
    }

    @Test
    public void Homework1_Q1_withJsonPath(){

        Response response=given().accept(ContentType.JSON).
                and().queryParam("q","{\"country_id\":\"US\"}").
                when().get("/countries");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.header("Content-Type"), "application/json");
        assertEquals(response.path("items.country_id[0]"),"US");
        assertEquals(response.path("items.country_name[0]"),"United States of America");
        int region_id=response.path("items.region_id[0]");
        assertEquals(region_id,2);
    }
    @Test
    public void Homework1_Q2_JsonPath(){

        Response response=given().accept(ContentType.JSON).
                and().queryParam("q","{\"department_id\":80}").
                when().get("/employees");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.header("Content-Type"), "application/json");
        List<String> job_ids=response.path("items.job_id");
        for (String job_id : job_ids) {
            boolean starting=false;
            if (job_id.startsWith("SA")){
                starting=true;
            }
            assertEquals(starting,true);
        }

        List<Integer> department_Id=response.path("items.department_id");
        for (int  integer : department_Id) {
            assertEquals(integer,80);
        }
        int count=response.path("count");
        assertEquals(count,25);

    }


}
