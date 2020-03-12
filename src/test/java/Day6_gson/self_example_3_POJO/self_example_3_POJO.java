package Day6_gson.self_example_3_POJO;
import Day6_gson.self_example_2_Employees.Employees;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.DBUtils;
import utilities.DBUtils_Spartans;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class self_example_3_POJO {
    @BeforeClass
    public void setup(){
        baseURI=ConfigurationReader.get("hrapi.uri");
    }


    @Test
    public void employee_POJO(){
        Response response = given().accept(ContentType.JSON).get("departments/20");
        assertEquals(response.statusCode(),200);


        Depts depts=response.body().as(Depts.class);
        System.out.println("depts.getDepartmentId() = " + depts.getDepartmentId());

//        Department department=response.body().as(Department.class);
//        System.out.println("department.getDepartmentId() = " + department.getDepartmentId());




    }

    @Test
    public void houses() {
        String key = "$2a$10$Jyx8ZN7Xf0kmzjWs7mRVh.ugmEbhBwjGgcLoMO81NhFWlWveSHGp.";

        Response response = given().accept(ContentType.JSON).queryParam("key", key)
                .queryParam("_id", "5a05e2b252f721a3cf2ea33f")
                .get("https://www.potterapi.com/v1/houses");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

//        Day6_gson.self_example_3_POJO.Response characterHarry = response.body().as(Day6_gson.self_example_3_POJO.Response.class);
//        System.out.println("characterHarry.getId().toString() = " + characterHarry.getId().toString());

    }

    @Test
    public void regions_jackson(){
        Response response = get("http://54.161.128.36:1000/ords/hr/regions");

//        RegionsGson regionsGson=response.body().as(RegionsGson.class);
//        System.out.println("regionsGson.getCount() = " + regionsGson.getCount());

        Regions regions=response.body().as(Regions.class);
        System.out.println("regions.getCount() = " + regions.getCount());


    }

}
