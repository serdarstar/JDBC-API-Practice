package apitests.Homeworks.Homework_1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
public class Homework_1_Path {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI= ConfigurationReader.get("hrapi.uri");
    }

    @Test
    public void Homework1_Q1_withPath(){

        Response response=given().accept(ContentType.JSON).
                and().pathParam("country_id","US").
                when().get("/countries/{country_id}");
        assertEquals(response.statusCode(), 200);

        assertEquals(response.header("Content-Type"), "application/json");
        assertEquals(response.path("country_id"),"US");
        assertEquals(response.path("country_name"),"United States of America");
        int region_id=response.path("region_id");
        assertEquals(region_id,2);
    }

    @Test
    public void Homework1_Q2_withPath() {

        Response response = given().accept(ContentType.JSON).
                and().queryParam("q", "{\"department_id\":80}").
                when().get("/employees");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.header("Content-Type"), "application/json");

        List<String> job_ids=response.path("items.job_id");
        System.out.println(job_ids);
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

    @Test
    public void Homework1_Q3_withPath() {
        Response response = given().accept(ContentType.JSON).and().
                queryParam("q", "{\"region_id\":3}").when().get("/countries");
        assertEquals(response.statusCode(),200);

        List<Integer> region_id=response.path("items.region_id");
        for (int  integer : region_id) {
            assertEquals(integer,3);
        }

        assertEquals(response.path("count").toString(),"6");
        assertEquals(response.path("hasMore").toString(),"false");

        List<String> country_names=response.path("items.country_name");
        List<String> country_names_expected=new ArrayList<>(Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore"));
        assertEquals(country_names,country_names_expected);

       //ekstra deneme
        System.out.println(response.path("items").toString());






    }



}
