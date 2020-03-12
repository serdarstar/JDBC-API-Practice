package apitests.Homeworks.Homework_4;

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

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class Homework_4 {
    @BeforeClass
    public void setup(){
        baseURI = ConfigurationReader.get("uninames_uri");
    }
    @Test
    public void Homework_4() {

     Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("amount",2);
        paramsMap.put("region","Turkey");
        paramsMap.put("gender","male");
        paramsMap.put("ext","");

        Response response = given().accept(ContentType.JSON)
                .queryParams(paramsMap)
                .get(baseURI);
        response.prettyPrint();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//        response.prettyPrint();
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name[0]"));
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("phone[0]").toString().replace(" ","").replace("(","").replace(")",""));

        for (int i = 0; i < 2; i++) {
            long phone=Long.parseLong(response.body().path("phone["+i+"]").toString().replace(" ","").replace("(","").replace(")",""));
            Map<String,Object> requestMap = new HashMap<>();
            //adding the values that we want to post
            String gender=response.body().path("gender["+i+"]").toString().replace("m", "M");
            requestMap.put("gender",gender);
            requestMap.put("name",response.body().path("name["+i+"]"));
            requestMap.put("phone",phone);

            Response response2 = given().accept(ContentType.JSON).
                    and().contentType(ContentType.JSON)
                    .and().body(requestMap).when().post("http://3.95.190.225:8000/api/spartans");

            //response validations
            assertEquals(response2.statusCode(),201);
            assertEquals(response2.contentType(),"application/json");

            //verify response body
            String successMessage = response2.path("success");
            System.out.println("successMessage = " + successMessage);
            assertEquals(successMessage,"A Spartan is Born!");
            System.out.println(response2.body().path("data.id").toString());

            long phoneData=Long.parseLong(response2.body().path("data.phone").toString());
            System.out.println("phoneData = " + phoneData);
            System.out.println(response2.body().path("data.id").toString());

//
//            List<Long> phoneNumbers=new ArrayList<>();
//            phoneNumbers.add(phone);
//            System.out.println("phoneNumbers = " + phoneNumbers);
//            System.out.println("phoneNumbers.size() = " + phoneNumbers.size());

//            phones2(phoneData);

//            WebDriver driver= new ChromeDriver();
//            driver.get("http://3.95.190.225:8000/spartans");
//            WebElement name=driver.findElement(By.xpath("//td[contains(text(),'3584128232')]"));
//            assertTrue(name.isDisplayed());


        }

    }
    public List<Long> phones2 (long phone){
        List<Long> phoneNumbers=new ArrayList<>();
        phoneNumbers.add(phone);
        System.out.println("phoneNumbers = " + phoneNumbers);
        System.out.println("phoneNumbers.size() = " + phoneNumbers.size());
        return phoneNumbers;
    }

    @Test
    public void Homework4(){
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
        driver.get("http://3.95.190.225:8000/spartans");
//        WebElement phone=


    }
    public static void main(String[] args) throws SQLException {

        //connections strings
//        String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521/xe";
//        String dbUsername="SP";
//        String dbPassword="SP";
//
//        //create connection to database
//        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
//
//        //create statement object
//        Statement statement=connection.createStatement();
//
//        ResultSet resultSet=statement.executeQuery("Select * from spartans");

        //move pointer to next row
//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));

//        while(resultSet.next()){
//            System.out.println(resultSet.getString(2)+"-"+
//                    resultSet.getString(3)+"-"+
//                    resultSet.getObject("phone")) ;
//            System.out.println(resultSet.getObject("phone"));
//        }
//
        DBUtils_Spartans.createConnection();
        //save the query result as a list of maps(just like we did together)
        List<Map<String, Object>> queryData = DBUtils_Spartans.getQueryResultMap("Select phone from spartans where spartan_id>468");
        //printing the result
        for (Map<String, Object> row : queryData) {
            System.out.println(row);
        }


        //close connection
//        resultSet.close();
//        statement.close();
//        connection.close();




    }
}
