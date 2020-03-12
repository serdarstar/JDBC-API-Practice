package apitests.Homeworks.Homework_3;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.testng.Assert.*;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Homework_3 {


    @BeforeClass
    public void setup(){
        baseURI = ConfigurationReader.get("uninames_uri");
    }

    @Test
    public void Q1(){
        Response response = given().accept(ContentType.JSON).get("");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertNotEquals(response.body().path("name").toString(),"null");
        assertNotEquals(response.body().path("surname").toString(),"null");
        assertNotEquals(response.body().path("gender").toString(),"null");
        assertNotEquals(response.body().path("region").toString(),"null");
    }
    @Test
    public void Q2() {
        Response response = given().accept(ContentType.JSON).queryParam("gender","female").get(baseURI);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertEquals(response.body().path("gender").toString(),"female");
        System.out.println(response.body().path("name").toString());


    }
    @Test
    public void Q3() {
        Response response = given().accept(ContentType.JSON).queryParam("region","Turkey").
                queryParam("gender","female").
                get(baseURI);
        assertEquals(response.statusCode(), 200);
//        System.out.println(response.body().path("region").toString());
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertEquals(response.body().path("region").toString(),"Turkey");
        assertEquals(response.body().path("gender").toString(),"female");
    }

    @Test
    public void Q4() {
        Response response = given().accept(ContentType.JSON).queryParam("gender","females").get(baseURI);
        assertEquals(response.statusCode(), 400);
        assertTrue(response.getStatusLine().contains("Bad Request"));
        System.out.println(response.body().path("error").toString());
        assertEquals(response.body().path("error"),"Invalid gender");
    }
    @Test
    public void Q5() {
        Response response = given().accept(ContentType.JSON).queryParam("region","turkeys").get(baseURI);
        assertEquals(response.statusCode(), 400);
        assertTrue(response.getStatusLine().contains("Bad Request"));
        System.out.println(response.body().path("error").toString());
        assertEquals(response.body().path("error"),"Region or language not found");
    }

    @Test
    public void Q6() {
        Response response = given().accept(ContentType.JSON).queryParam("region","turkey").
                queryParam("amount",10).
                get(baseURI);
        assertEquals(response.statusCode(), 200);

        List<String> fullName=new ArrayList<>();
        fullName.add(response.body().path("name[0]").toString().concat(" ").concat(response.body().path("surname[0]").toString()));
        for (int i = 0; i < 10; i++) {
            fullName.add(response.body().path("name["+i+"]").toString().concat(" ").concat(response.body().path("surname["+i+"]").toString()));
        }

        System.out.println(fullName.toString());
        for (int i = 0; i < fullName.size(); i++) {
            for (int j = i+1; j < fullName.size(); j++) {

                 boolean isEqual=true;
                 if(fullName.get(i).equals(fullName.get(j))){
                     isEqual=false;

                 }
                assertTrue(isEqual);
            }

        }

    }
    @Test
    public void Q7() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("region", "turkey")
                .queryParam("amount", 10)
                .queryParam("gender", "male")
                .get(baseURI);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<String> allRegions=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
             //allRegions.add(response.body().path("region["+i+"]").toString());
             assertEquals(response.body().path("region["+i+"]").toString(),"Turkey");

        }
      //  System.out.println(allRegions.toString());
        List<String> allGender=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //allRegions.add(response.body().path("region["+i+"]").toString());
            assertEquals(response.body().path("gender["+i+"]").toString(),"male");
        }


    }
    @Test
    public void Q8() {
        Response response = given().accept(ContentType.JSON)
                .queryParam("region", "turkey")
                .queryParam("amount", 10)
                .queryParam("gender", "male")
                .get(baseURI);
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<String> allRegions=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            allRegions.add(response.body().path("region["+i+"]").toString());

        }

        assertEquals(allRegions.size(),10);
    }

    @Test
    public void Q8_Solution2() {

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("amount",10);
        paramsMap.put("region","Turkey");
        paramsMap.put("gender","male");

        Response response = given().accept(ContentType.JSON)
                .queryParams(paramsMap)
                .get(baseURI);

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<String> allRegions=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            allRegions.add(response.body().path("region["+i+"]").toString());

        }

        assertEquals(allRegions.size(),10);
    }

    @Test
    public void Homework_4(){

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("amount",10);
        paramsMap.put("region","Turkey");
        paramsMap.put("gender","male");
        paramsMap.put("ext","");

        Response response = given().accept(ContentType.JSON)
                .queryParams(paramsMap)
                .get(baseURI);

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//        response.prettyPrint();
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("name[0]"));
        System.out.println("response.body().path(\"name\").toString() = " + response.body().path("phone[0]").toString().replace(" ","").replace("(","").replace(")",""));

        long phone=Long.parseLong(response.body().path("phone[0]").toString().replace(" ","").replace("(","").replace(")",""));
        Map<String,Object> requestMap = new HashMap<>();
        //adding the values that we want to post
        requestMap.put("gender",response.body().path("gender[0]"));
        requestMap.put("name",response.body().path("name[0]"));
        requestMap.put("phone",phone);
        Response response2 = given().accept(ContentType.JSON).
                    and().contentType(ContentType.JSON)
                    .and().body(requestMap).when().post("http://3.95.190.225:8000/api/spartans");

            //response validations
            assertEquals(response.statusCode(),201);
            assertEquals(response.contentType(),"application/json");

            //verify response body
            String successMessage = response.path("success");
            System.out.println("successMessage = " + successMessage);
            assertEquals(successMessage,"A Spartan is Born!");






//        for (int i = 0; i < 10; i++) {
//            System.out.println("response.body().path(\"gender[\"+i+\"]\") = " +
//
//                    response.body().path("phone["+i+"]").toString().replace(" ","").replace("(","").replace(")",""));
//
//        }

//        for (int i = 0; i < 10; i++) {
//            long phone=Long.parseLong(response.body().path("phone["+i+"]").toString().replace(" ","").replace("(","").replace(")",""));
//            Map<String,Object> requestMap = new HashMap<>();
//            //adding the values that we want to post
//            requestMap.put("gender",response.body().path("gender["+i+"]"));
//            requestMap.put("name",response.body().path("name["+i+"]"));
//            requestMap.put("phone",phone);
//
//            Response response2 = given().accept(ContentType.JSON).
//                    and().contentType(ContentType.JSON)
//                    .and().body(requestMap).when().post("http://3.95.190.225:8000/api/spartans");
//
//            //response validations
//            assertEquals(response.statusCode(),201);
//            assertEquals(response.contentType(),"application/json");
//
//            //verify response body
//            String successMessage = response.path("success");
//            System.out.println("successMessage = " + successMessage);
//            assertEquals(successMessage,"A Spartan is Born!");

//        }




    }

}