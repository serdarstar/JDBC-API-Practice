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
import utilities.Driver;


import java.sql.*;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class Homewrok4_deneme {

    static List<Object> id;
    static List<Object> phonesList;

    @BeforeClass
    public void setup(){
        baseURI = ConfigurationReader.get("uninames_uri");
    }
    @Test
    public void Homework_4() throws SQLException {
        System.out.println(".................GETTING 3 JSON FILES FROM UINAMES......................");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("amount",3);
        paramsMap.put("region","Turkey");
        paramsMap.put("gender","male");
        paramsMap.put("ext","");

        Response response = given().accept(ContentType.JSON)
                .queryParams(paramsMap)
                .get(baseURI);
//      response.prettyPrint();
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        System.out.println("nameFromUINAMES= " + response.body().path("name[0]"));
        System.out.println("phoneNumberFromUINAMES = " + response.body().path("phone[0]").toString().replace(" ","").replace("(","").replace(")",""));

        id=new ArrayList<>();
        phonesList =new ArrayList<>();

        System.out.println("....................POSTING 3 JSON TO SPARTAN DATABASE BY USING API............");
        for (int i = 0; i < 3; i++) {
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
//            System.out.println("successMessage = " + successMessage);
            assertEquals(successMessage,"A Spartan is Born!");
//            System.out.println(response2.body().path("data.id").toString());

            long phoneData=Long.parseLong(response2.body().path("data.phone").toString());
//            System.out.println("phoneData = " + phoneData);
//            System.out.println(response2.body().path("data.id").toString());


            id.add(response2.body().path("data.id"));
            phonesList.add(response2.body().path("data.phone"));
            System.out.println("phonesList.get(0).toString() = " + phonesList.get(0).toString());
            System.out.println("phonesList.size() = " + phonesList.size());

            System.out.println("id.size() = " + id.size());
            System.out.println("phonesList.size() = " + phonesList.size());



           System.out.println(".................. UI TEST WITH SELENIUM..............................................");
//            WebDriver driver= Driver.get();
            WebDriverManager.chromedriver().setup();
            WebDriver driver= new ChromeDriver();
            driver.get("http://3.95.190.225:8000/spartans");
            for (int j = 0; j < phonesList.size(); j++) {

                WebElement name=driver.findElement(By.xpath("//td[contains(text(),'"+phonesList.get(i)+"')]"));
                assertTrue(name.isDisplayed());

            }

        }
        System.out.println("Last phone number = " + phonesList.get(phonesList.size()-1));

        System.out.println("id.get(id.size()-1) = " + id.get(id.size() - 1));

        System.out.println(".................. DATABASE TEST WITH JDBC..............................................");
        Object o = phonesList.get(phonesList.size()-1);

        DBUtils_Spartans.createConnection();
        String query = "select spartan_id from spartans where phone="+o+"";
        //save the query result as a list of maps(just like we did together)
        Map<String, Object> rowMap = DBUtils_Spartans.getRowMap(query);
        System.out.println(rowMap.get("SPARTAN_ID"));
        //close connection
        DBUtils.destroy();

        assertEquals(rowMap.get("SPARTAN_ID").toString(),id.get(id.size() - 1).toString());




        //NOTES:
        /*

        We could use  List<Map<String, Object>> queryResultMap = DBUtils_Spartans.getQueryResultMap("SELECT * FROM spartans");
        to get the data as table.

        I created DBUtils_Spartans not to ruin my other tests.

         */

//        //if you want to print the table
//        for (Map<String, Object> row : queryData) {
//            System.out.println(row.toString());
//        }

    }
    @Test

    public void DeleteASpartan(){
        Random rn=new Random();

        try {
            for (int i = 1; i < 200; i++) {
                int idToDelete = rn.nextInt(400) + 2;
                System.out.println("idToDelete = " + i);
                given().pathParam("id", i)
                        .when().delete("http://3.95.190.225:8000/api/spartans/{id}").then()
                        .assertThat().statusCode(204);

            }
        } catch (Exception e){
            e.printStackTrace();
        }





    }

}
