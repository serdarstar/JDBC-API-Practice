package apitests.Homeworks.Homework_5_Path_and_JSON;

import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class Homework_5 {


        String HarryPotterBaseUri = "https://www.potterapi.com/v1/";
        String apiKey = "$2a$10$jegafz4wGBHGGzAMGHLZf.n8rZ3H5PqZFtmR6PDnAcoi/67cbeB5G";


    /*
    Verify sorting hat
    1. Send a get request to /sortingHat. Request includes :
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that response body contains one of the following houses:
    "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */

        @Test
        public void Test1(){

            Response response = given().accept(ContentType.JSON)
                    .when().get(HarryPotterBaseUri+"sortingHat");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            List<String> houseList = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin","Hufflepuff");
            System.out.println("houseList = " + houseList);
            String actualHouse = response.body().asString().replace("\"","");
            System.out.println("actualHouse = " + actualHouse);

            assertTrue(houseList.contains(actualHouse));
        }

    /*
    Verify bad key
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value invalid
    2. Verify status code 401, content type application/json; charset=utf-8
    3. Verify response status line include message Unauthorized
    4. Verify that response body says "error": "API Key Not Found"
     */

        @Test
        public void test2(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", "invalid")
                    .when().get(HarryPotterBaseUri+"characters");

            assertEquals(response.statusCode(),401);
            assertTrue(response.statusLine().contains("Unauthorized"));
            String bodyMessage = response.body().path("error");
            assertEquals(bodyMessage,"API Key Not Found");

        }

    /*
    Verify no key
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    2. Verify status code 409, content type application/json; charset=utf-8
    3. Verify response status line include message Conflict
    4. Verify that response body says "error": "Must pass API key for request"
     */

        @Test
        public void test3(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),409);
            assertEquals(response.contentType(),"application/json; charset=utf-8");
            assertTrue(response.statusLine().contains("Conflict"));
            assertEquals(response.body().path("error"),"Must pass API key for request");
        }

    /*
    Verify number of characters
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify response contains 195 characters
     */

        @Test
        public void test4(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            //List<Integer> bodyObjects = response.body().path("_id");
            //int numberOfCharacters = bodyObjects.size();

            List<Map<String,Object>> listOfObjects = response.body().as(List.class);
            assertEquals(listOfObjects.size(),195);
        }

    /*
    Verify number of character id and house
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify all characters in the response have id field which is not empty
    4. Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
    5. Verify value of the house in all characters in the response is one of the following:
    "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */

        @Test
        public void test5(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            List<Map<String,Object>> allCharacters = response.body().as(List.class);
            //System.out.println("allCharacters.get(0).get(\"_id\") = " + allCharacters.get(0).get("_id"));

            for (Map<String, Object> allCharacter : allCharacters) {
                assertFalse(allCharacter.get("_id").toString().isEmpty());
            }

            List<String> dumbledoresArmyList = new ArrayList<>();
            for (Map<String, Object> allCharacter : allCharacters) {
                dumbledoresArmyList.add(allCharacter.get("dumbledoresArmy").toString());
            }
            System.out.println("dumbledoresArmyList = " + dumbledoresArmyList);

            for (String s : dumbledoresArmyList) {
                assertTrue(s.equals("true") || s.equals("false"));
            }

            List<String> houseList = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin","Hufflepuff");

            for (Map<String, Object> allCharacter : allCharacters) {
                System.out.println("allCharacter = " + allCharacter.get("house"));
                assertTrue(houseList.contains(allCharacter.get("house")));
            }
        }

    /*
    Verify all character information
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Select name of any random character
    4. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value from step 3
    5. Verify that response contains the same character information from step 3. Compare all fields.
     */

        @Test
        public void test6(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            String anyCharacterName = response.body().path("name[6]");
            //System.out.println("anyCharacterName = " + anyCharacterName);

            List<Map<String,Object>> allCharacters = response.body().as(List.class);
            Map<String,Object> character1 = allCharacters.get(6);
            System.out.println("character1 = " + character1);

            Response response2 = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().queryParam("name",anyCharacterName)
                    .when().get(HarryPotterBaseUri + "characters");

            assertTrue(response2.body().asString().contains(response.body().path("name[6]")));

            List<Map<String,Object>> allCharacter2 = response2.body().as(List.class);
            Map<String,Object> character2 = allCharacter2.get(0);
            System.out.println("character2 = " + character2);

            assertEquals(character1,character2);

        /*
        String characterName = response.body().path("name[6]");
        System.out.println("characterName = " + characterName);
        String characterName2 = response2.body().path("name[0]");
        System.out.println("characterName2 = " + characterName2);
        assertEquals(characterName,characterName2);

        String id = response.body().path("_id[6]");
        String id2 = response2.body().path("_id[0]");
        assertEquals(id,id2);
        */
        }

    /*
    Verify name search
    1. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value Harry Potter
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify name Harry Potter
    4. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param name with value Marry Potter
    5. Verify status code 200, content type application/json; charset=utf-8
    6. Verify response body is empty
     */

        @Test
        public void test7(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().queryParam("name", "Harry Potter")
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");
            assertEquals(response.body().path("name[0]"),"Harry Potter");

            Response response2 = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().queryParam("name", "Marry Potter")
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");
            System.out.println("response2.body().asString() = " + response2.body().asString());
            String responseBody = response2.body().asString();

            List<String> response2Body = response2.body().as(List.class);
            assertEquals(response2Body.size(),0);
        }

    /*
    Verify house members
    1. Send a get request to /houses. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Capture the id of the Gryffindor house
    4. Capture the ids of the all members of the Gryffindor house
    5. Send a get request to /houses/:id. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Path param id with value from step 3
    6. Verify that response contains the same member ids as the step 4
     */

        @Test
        public void test8(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .when().get(HarryPotterBaseUri + "houses");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            List<Map<String,Object>> listOfHouses = response.body().as(List.class);

            String idOfGryffindor = "";
            List<Object> listOfMembersOfGryffindors = new ArrayList<>();
            for (Map<String, Object> listOfHouse : listOfHouses) {
                if(listOfHouse.get("name").equals("Gryffindor")){
                    idOfGryffindor = (String) listOfHouse.get("_id");
                    listOfMembersOfGryffindors.add(listOfHouse.get("members"));
                }
            }

            System.out.println("idOfGryffindor = " + idOfGryffindor);
            System.out.println("listOfMembersOfGryffindors = " + listOfMembersOfGryffindors);

            Response response2 = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().pathParam("_id",idOfGryffindor)
                    .when().get(HarryPotterBaseUri + "houses/{_id}");

            System.out.println("response2.body() = " + response2.body().asString());

            List<String> memberIds = response2.body().path("members._id");
            System.out.println("memberIds = " + memberIds);

            assertEquals(listOfMembersOfGryffindors,memberIds);

            //List<Map<String,List<Object>>> listOfMembers = response.body().as(List.class);
            //System.out.println("listOfMembers.get(0).get(\"members\").get(0) = " + listOfMembers.get(0).get("members").get(0));
        }

    /*
    Verify house members again
    1. Send a get request to /houses/:id. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Path param id with value 5a05e2b252f721a3cf2ea33f
    2. Capture the ids of all members
    3. Send a get request to /characters. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    • Query param house with value Gryffindor
    4. Verify that response contains the same member ids from step 2
     */

        @Test
        public void test9(){

            Response response = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().pathParam("_id", "5a05e2b252f721a3cf2ea33f")
                    .when().get(HarryPotterBaseUri + "houses/{_id}");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json; charset=utf-8");

            List<String> idsOfAllMembers = response.body().path("members._id[0]");
            System.out.println("idsOfAllMembers = " + idsOfAllMembers);

            Response response1 = given().accept(ContentType.JSON)
                    .and().header("Accept", "application/json")
                    .and().queryParam("key", apiKey)
                    .and().queryParam("house", "Gryffindor")
                    .when().get(HarryPotterBaseUri + "characters");

            assertEquals(response1.statusCode(),200);
            assertEquals(response1.contentType(),"application/json; charset=utf-8");

            List<String> idsOfAllMembers1 = response1.body().path("_id");
            System.out.println("idsOfAllMembers1 = " + idsOfAllMembers1);

            assertEquals(idsOfAllMembers,idsOfAllMembers1);

        }

    /*
    Verify house with most members
    1. Send a get request to /houses. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that Gryffindor house has the most members
     */



    /*
    Verify house with most members
    1. Send a get request to /houses. Request includes :
    • Header Accept with value application/json
    • Query param key with value {{apiKey}}
    2. Verify status code 200, content type application/json; charset=utf-8
    3. Verify that Gryffindor house has the most members
     */

    @Test
    public void test10(){

        Response response = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json")
                .and().queryParam("key", apiKey)
                .when().get(HarryPotterBaseUri + "houses");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        List<Map<String,List<Object>>> listOfHouses = response.body().as(List.class);
        //System.out.println(response.body().prettyPrint());

        for (Map<String, List<Object>> listOfHouse : listOfHouses) {
            System.out.println("houseName = " + listOfHouse.get("name"));
            System.out.println("houseMembersNumber = " + listOfHouse.get("members").size());
        }

        int max = Integer.MIN_VALUE;
        Object mostMembersHouse = "";
        for (Map<String, List<Object>> listOfHouse : listOfHouses) {
            if(listOfHouse.get("members").size()>max){
                max = listOfHouse.get("members").size();
                mostMembersHouse = listOfHouse.get("name");
            }
        }

        System.out.println("the house has the most members is = " + mostMembersHouse);

        assertEquals(mostMembersHouse,"Gryffindor");
    }


}
