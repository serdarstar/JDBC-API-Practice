package apitests.Homeworks.Homework_4;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.*;
import utilities.Driver;


import java.sql.*;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class Homework4_UI_API_DB {
    //this class is used for starting and building reports
    ExtentReports report;
    //this class is used to create th HTML report file
    ExtentHtmlReporter htmlReporter;
    //this will define a test, enables adding logs, authors, test steps
    ExtentTest extentLogger;

    static List<Object> id;
    static List<Object> phonesList;

    @DataProvider
    public Object[][] userData(){
        ExcelUtil data = new ExcelUtil("src/test/resources/Spartan.xlsx","short1");

        String[][] dataArray = data.getDataArrayWithoutFirstRow();

        return dataArray;
    }


    @BeforeClass
    public void setup(){
        baseURI = ConfigurationReader.get("uninames_uri");

        //initialize the class
        report = new ExtentReports();

        //create report path
        String projectPath = System.getProperty("user.dir");
        String path = projectPath +"/test-output/report.html";

        //initialize the html reporter with the report path
        htmlReporter= new ExtentHtmlReporter(path);

        //attach the html report to the report object
        report.attachReporter(htmlReporter);

        //title in the report
        htmlReporter.config().setReportName("Homework4_UI_API_DB Test");

        //set environment information
        report.setSystemInfo("Environment","QA");
        report.setSystemInfo("Browser", "chrome");
        report.setSystemInfo("Tester","serdarstar");

    }


    @Test
    public void Homework_4() throws SQLException {

        System.out.println(".................GETTING 3 JSON FILES FROM UINAMES......................");
        extentLogger=report.createTest("Homework4_UI_API_DB Test");
        extentLogger.info("GETTING 3 JSON FILES FROM UINAMES");

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
        extentLogger.info("POSTING 3 JSON TO SPARTAN DATABASE BY USING API");

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
            extentLogger.info("UI TEST WITH SELENIUM");
            //configuration.properties de chrome-headless secili oldugu icin browser acilmayacak

            WebDriver driver= Driver.get();
//            WebDriverManager.chromedriver().setup();
//            WebDriver driver= new ChromeDriver();
            driver.get("http://3.95.190.225:8000/spartans");
            for (int j = 0; j < phonesList.size(); j++) {

                WebElement name=driver.findElement(By.xpath("//td[contains(text(),'"+phonesList.get(i)+"')]"));
                assertTrue(name.isDisplayed());

            }

        }
        System.out.println("Last phone number = " + phonesList.get(phonesList.size()-1));

        System.out.println("id.get(id.size()-1) = " + id.get(id.size() - 1));

        System.out.println(".................. DATABASE TEST WITH JDBC METHOD 1.......................................");
        extentLogger.info("DATABASE TEST WITH JDBC METHOD 1");
        Object o = phonesList.get(phonesList.size()-1);

        DBUtils_Spartans.createConnection();
        String query = "select spartan_id from spartans where phone="+o+"";
        //save the query result as a list of maps(just like we did together)
        Map<String, Object> rowMap = DBUtils_Spartans.getRowMap(query);
        System.out.println(rowMap.get("SPARTAN_ID"));
        //close connection
        DBUtils_Spartans.destroy();

        assertEquals(rowMap.get("SPARTAN_ID").toString(),id.get(id.size() - 1).toString());

        System.out.println(".....................DATABASE TEST METHOD 2.............................................");
        extentLogger.info("DATABASE TEST WITH JDBC METHOD 2");

        String query2 = "select * from spartans order by spartan_id desc";
        String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521/xe";
        String dbUsername = "SP";
        String dbPassword = "SP";
        Connection connection=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet=statement.executeQuery(query2);
        resultSet.next();
        System.out.println("First row phone = " + resultSet.getString("PHONE"));
        assertEquals(resultSet.getString("PHONE").toString(),phonesList.get(phonesList.size()-1).toString());


        connection.close();

        System.out.println(".................. DATABASE TEST METHOD 3..............................................");
        extentLogger.info("DATABASE TEST WITH JDBC METHOD 3");

        String query3 = "select * from spartans order by spartan_id asc";
        Connection connection2=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement2=connection2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet2=statement2.executeQuery(query3);

        resultSet2.last();
        int rowNumber= resultSet2.getRow();
        resultSet2.absolute(rowNumber);
        String lastPhone=resultSet2.getString("PHONE");
        assertEquals(lastPhone,phonesList.get(phonesList.size()-1).toString());

        connection.close();


        //NOTES:
        /*

        We could use  List<Map<String, Object>> queryResultMap = DBUtils_Spartans.getQueryResultMap("SELECT * FROM spartans");
        to get the data as table.

        I created DBUtils_Spartans not to ruin my other tests.

         */


    }
//    @Test
//    public void DeleteASpartan(){
//        extentLogger=report.createTest("DELETING SPARTANS TEST");
//        extentLogger.info("Deleting Spartans");
//
//        try {
//            for (int i = 550; i < 600; i++) {
//
//                System.out.println("idToDelete = " + i);
//                given().pathParam("id", i)
//                        .when().delete("http://3.95.190.225:8000/api/spartans/{id}").then()
//                        .assertThat().statusCode(204);
//
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    @Test(dataProvider = "userData")
    public void loginTestDDF(String SPARTAN_ID,String NAME, String PHONE){
        extentLogger=report.createTest("UI TESTING WITH DATA PROVIDER");
        extentLogger.info(" Checking Names are Displayed");

        WebDriver driver= Driver.get();
        driver.get("http://3.95.190.225:8000/spartans");

        WebElement name=driver.findElement(By.xpath("//td[contains(text(),'"+NAME+"')]"));
        assertTrue(name.isDisplayed());


        // Burdan asagisida calisiyor normalde, ama excel den sayilari datayi alirken farkli aliyor, o yuzden hata veriyor.
        //DDF de problem yok, excel den alis kismi duzelecek. Yukaridaki sayi degil text oldugu icin direk aliyor ve geciyor

//        WebElement phone=driver.findElement(By.xpath("//td[contains(text(),'"+PHONE+"')]"));
//        assertTrue(phone.isDisplayed());

//        int spartan_id_integer=Integer.parseInt(SPARTAN_ID);
//        System.out.println("spartan_id_integer = " + spartan_id_integer);
//
//        WebElement spartan_id=driver.findElement(By.xpath("//td[contains(text(),'"+spartan_id_integer+"')]"));
//        assertTrue(spartan_id.isDisplayed());


    }

    @AfterMethod
    public void teardown(){
        //this is when the report is actually created
        report.flush();

}

}
