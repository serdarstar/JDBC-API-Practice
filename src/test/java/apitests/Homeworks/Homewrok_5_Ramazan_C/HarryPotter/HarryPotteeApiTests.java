package apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter;


import apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos.House;
import apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos.Character;
import apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos.HouseWithMembers;
import apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos.Member;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class HarryPotteeApiTests {

    String key = "$2a$10$sGcwdh.aXeEohLwmxZm/K.saWFGgWPwUV7V5NMuIRVa5.mjb6D.Mm";

    @BeforeClass
    public void setUpClass() {

        RestAssured.baseURI = ConfigurationReader.get("harryPotterApiUrl");
    }

    /*
        Verify sorting hat
            1. Send a get request to /sortingHat. Request includes :
            2. Verify status code 200, content type application/json; charset=utf-8
            3. Verify that response body contains one of the following houses:
            "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    public void sortingHatTest() {
        Response response = given().accept(ContentType.JSON)
                .when().get("sortingHat");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

        String[] list = {"\"Gryffindor\"", "\"Ravenclaw\"", "\"Slytherin\"", "\"Hufflepuf\""};
        List<String> expectedList= Arrays.asList(list);

        assertTrue(expectedList.contains(response.body().asString()));
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
    public void verifyBadKey(){
        Response response = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json")
                .and().queryParam("key", "123654")
                .when().get("/characters");

        response.then().assertThat().statusCode(401).and().contentType("application/json; charset=utf-8");

        assertTrue(response.statusLine().contains("Unauthorized"));

        assertEquals(response.body().path("error"),"API Key Not Found");
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
    public void verifyNoKey(){
        Response response = given().accept(ContentType.JSON)
                .header("Accept", "application/json")
                .when().get("/characters");
        response.then().assertThat().statusCode(409).and().contentType("application/json; charset=utf-8");

        assertTrue(response.statusLine().contains("Conflict"));

        assertEquals(response.body().path("error"),"Must pass API key for request");

    }

    /*
        Verify number of characters
            1. Send a get request to /characters. Request includes :
            • Header Accept with value application/json
            • Query param key with value {{apiKey}}
            2. Verify status code 200, content type application/json; charset=utf-8
            3. Verify response contains 194 characters
     */
    @Test
    public void verifyNumberofCharacters(){

        Response response = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json")
                .and().queryParam("key", key)
                .when().get("/characters");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

        //to create List of Character use this method. more info https://github.com/google/gson/blob/master/UserGuide.md
        // or watch this video https://www.youtube.com/watch?v=ZZddxpxGQPE&list=PLpUMhvC6l7AOy4UEORSutzFus98n-Es_l&index=3
        //special thanks for Harun Ula
        String bodyString = response.body().asString();
        Type CharacterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characterList=new Gson().fromJson(bodyString,CharacterListType);

        //System.out.println("characterList.size() = " + characterList.size());
        assertEquals(characterList.size(),194);
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
    public void verifyIdAndHouse(){
        Response response = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json")
                .and().queryParam("key", key)
                .when().get("/characters");
        response.then().assertThat().statusCode(200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");

        //System.out.println(response.prettyPrint());

        //if needed, look line 113
        String bodyString = response.body().asString();
        Type CharacterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characterList=new Gson().fromJson(bodyString,CharacterListType);
        //System.out.println("characterList = " + characterList);

        for (Character character : characterList) {
           /*
            try {
                assertFalse(character.getId().isEmpty());
            }catch (NullPointerException e){ //for catch which character I get failure if needed.
                System.out.println("character = " + character.getName());
                System.out.println("character = " + character.getId());
                break;
            }

            */

            assertFalse(character.getId().isEmpty());

            assertTrue(character.getDumbledoresArmy() instanceof Boolean);

            List<String> house = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");

            if(character.getHouse() == null){ // becouse some characters are not member of any houses so their value is null
                continue;
            }else {
                assertTrue(house.contains(character.getHouse()));
            }
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
    public void verifyAllCharacterInformation(){
        Response response = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json")
                .and().queryParam("key", key)
                .when().get("/characters");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");
        //System.out.println(response.prettyPrint());

        //if needed, look line 113
        String bodyString = response.body().asString();
        Type CharacterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characterList=new Gson().fromJson(bodyString,CharacterListType);

        //this also good solution with using Array
        //Character[] characterArray= new Gson().fromJson(response.body().asString(),Character[].class);
        //System.out.println("characterArray.length = " + characterArray.length);

        //3. Select name of any random character
        int rnd=new Random().nextInt(characterList.size());
        System.out.println("rnd = " + rnd);
        String name= characterList.get(rnd).getName();
        System.out.println("name = " + name);

        Response responseName = given().accept(ContentType.JSON)
                .and().header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .queryParam("name", name)
                .when().get("/characters");
        //System.out.println("responseName.prettyPrint() = " + responseName.prettyPrint());

        //for create object using array(not need any Type)
        Character[] characterName=new Gson().fromJson(responseName.body().asString(),Character[].class);

//        System.out.println("characterName.getName() = " + characterName[0].getName());//
//        System.out.println("characterList.get(rnd).toString() = " + characterList.get(rnd).toString());//
//        System.out.println("characterName[0].toString() = " + characterName[0].toString());
        assertEquals(characterName[0].toString(), characterList.get(rnd).toString());
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
    public void verifyNameSearch(){
        Response response = given().accept(ContentType.JSON)
                .header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .queryParam("name", "Harry Potter")
                .when().get("/characters");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

        Character[] character=new Gson().fromJson(response.body().asString(),Character[].class);
        assertEquals(character[0].getName(),"Harry Potter");

        Response response2 = given().accept(ContentType.JSON)
                .header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .queryParam("name", "Marry Potter")
                .when().get("/characters");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().contentType("application/json; charset=utf-8");

        response2.prettyPrint();
        //assertTrue(response2.body().asString().isEmpty());

        String[] string=new Gson().fromJson(response2.body().asString(),String[].class);
        // string.length should be 0     // if it is 0 that means array is empty.

        assertEquals(string.length, 0);

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
    public void houseMembers(){

        Response response = given().accept(ContentType.JSON).and()
                .header("Accept", "application/json").and()
                .queryParam("key", key)
                .when().get("/houses");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

        //if needed, look line 113
        Type HouseListType=new TypeToken<ArrayList<House>>() {}.getType();
        String bodyString = response.body().asString();
        List<House> houses= new Gson().fromJson(bodyString,HouseListType);

        System.out.println("houses.size() = " + houses.size());
        String houseId="";
        List<String> members = null;
        for (House house : houses) {
            if (house.getName().equals("Gryffindor")){
                houseId=house.getId();
                members=house.getMembers();
                break;
            }
        }
        System.out.println("members.size() = " + members.size());

        Response responseHouse = given().accept(ContentType.JSON).and()
                .header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .pathParam("id", houseId)
                .when().get("/houses/{id}");

        //responseHouse.prettyPrint();

        //if needed, look line 113
        Type houseWithMembersType = new TypeToken<ArrayList<HouseWithMembers>>() {}.getType();
        List<HouseWithMembers> houseWithMembers=new Gson().fromJson(responseHouse.body().asString(),houseWithMembersType);

        //System.out.println("houseWithMembers.size() = " + houseWithMembers.size());

        List<String> membersWithId =new  ArrayList<String>();

        List<Member> memberList = houseWithMembers.get(0).getMembers();
        //System.out.println("memberList = " + memberList.size());
        for (Member member : memberList) {
            membersWithId.add(member.getId());
        }

//        System.out.println("members = " + members);
//        System.out.println("membersWithId = " + membersWithId);

        assertEquals(members,membersWithId);
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
    public void verifyHoseMembersAgain(){
        Response responseWithId = given().accept(ContentType.JSON).and()
                .header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .pathParam("id", "5a05e2b252f721a3cf2ea33f")
                .when().get("/houses/{id}");

        //if needed, look line 113
        Type houseWithMembersType = new TypeToken<ArrayList<HouseWithMembers>>() {}.getType();
        List<HouseWithMembers> houseWithId=new Gson().fromJson(responseWithId.body().asString(),houseWithMembersType);
        //System.out.println("houseWithId.size() = " + houseWithId.size());

        //System.out.println("houseWithId.get(0).getName() = " + houseWithId.get(0).getName());

        List<Member> membersWithId=houseWithId.get(0).getMembers();
        List<String> membersIdWithId=new ArrayList<>();

        for (Member member : membersWithId) {
            membersIdWithId.add(member.getId());
        }
        //System.out.println("membersIdWithId.size() = " + membersIdWithId.size());

        Response responseWithName = given().accept(ContentType.JSON).and()
                .header("Accept", "application/json").and()
                .queryParam("key", key).and()
                .queryParam("house", "Gryffindor")
                .when().get("/characters");

        for (String id : membersIdWithId) {
            assertTrue(responseWithName.body().asString().contains(id));
        }

        //but in the second response body contains one more character whose name is Minerva McGonagall.
        //it doesn't exist in member of first response of house Gryffindor. it assign to head of House

//        Type characterType = new TypeToken<ArrayList<Character>>() {}.getType();//
//        List<Character> characterList=new Gson().fromJson(responseWithName.body().asString(),characterType);//
//        System.out.println("characterList.size() = " + characterList.size());
//
//        /*
//         * The second string begins after 40 characters. The dash means that the
//         * first string is left-justified.
//         */
//        String format = "%-40s%s%n";
//        System.out.printf(format, "****Character name ****", "****Member name**** ");
//        for (int i = 0; i < 40; i++) {
//            System.out.printf(format,characterList.get(i).getName(),membersWithId.get(i).getName());
//        }
    }

    /*
        Verify house with most members
            1. Send a get request to /houses. Request includes :
            • Header Accept with value application/json
            • Query param key with value {{apiKey}}
            2. Verify status code 200, content type application/json; charset=utf-8
            3. Verify that Gryffindor house has the most members
    */
    @Test
    public void verifyHouseWithMostMembers(){

        Response response = given().accept(ContentType.JSON).and()
                .header("Accept", "application/json").and()
                .queryParam("key", key)
                .when().get("/houses");
        response.then().assertThat().statusCode(200).and().contentType("application/json; charset=utf-8");

        //if needed, look line 113
        Type houseListType = new TypeToken<ArrayList<House>>() {}.getType();
        List<House> houses=new Gson().fromJson(response.body().asString(),houseListType);
        
        int memberOfGryffindor = 0;
        for (House house : houses) {
            if(house.getName().equals("Gryffindor"))
                memberOfGryffindor=house.getMembers().size();
        }
        //System.out.println("memberOfGryffindor = " + memberOfGryffindor);

        for (House house : houses) {
            if(house.getName().equals("Gryffindor")){
                continue;
            }else {
                assertTrue(memberOfGryffindor>house.getMembers().size());
            }
        }
    }
}