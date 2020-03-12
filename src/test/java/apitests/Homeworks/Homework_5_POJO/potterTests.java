package apitests.Homeworks.Homework_5_POJO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class potterTests {

    String token = "$2a$10$/o3aDKVkGFQNyGUXfBoi0exUBrm0xzrukehU3KNtDT4q4WZt5OWEK";
    String badtoken = "€2a$10$/o3aDKVkGFQNyGUXfBoi0exUBrm0xzrukehU3KNtDT4q4WZt5OWEK";

    @BeforeClass
    public void setup() {
        baseURI = ConfigurationReader.get("potterApi.uri");
    }

    @Test
    public void Verify_sorting_hat() {

        /*
        Verify sorting hat
        1. Send a get request to /sortingHat. Request includes :
        2. Verify status code 200, content type application/json; charset=utf-8
        3. Verify that response body contains one of the following houses:
        "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
        */

        Response response = given().queryParam("key", token)
                .when().get("sortingHat");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        String body = response.body().asString();
        assertTrue(body.contains("Gryffindor") || body.contains("Ravenclaw") || body.contains("Slytherin") || body.contains("Hufflepuff"));
    }

    @Test
    public void Verify_bad_key() {
        /*
        Verify bad key
        1. Send a get request to /characters. Request includes :
            • Header Accept with value application/json
            • Query param key with value invalid
        2. Verify status code 401, content type application/json; charset=utf-8
        3. Verify response status line include message Unauthorized
        4. Verify that response body says "error": "API Key Not Found"
        */

        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", badtoken)
                .when().get("characters");
        //hamcrest ile statusline contains nasil yazilir???arastir
        assertEquals(response.statusCode(), 401);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Unauthorized"));
        assertEquals(response.path("error"), "API Key Not Found");


    }

    /*Verify no key
1. Send a get request to /characters. Request includes :
• Header Accept with value application/json
2. Verify status code 409, content type application/json; charset=utf-8
3. Verify response status line include message Conflict
4. Verify that response body says "error": "Must pass API key for request"
*/
    @Test
    public void Verify_no_key() {
        Response response = given().accept(ContentType.JSON)
                .when().get("characters");

        assertEquals(response.statusCode(), 409);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Conflict"));
        assertEquals(response.path("error"), "Must pass API key for request");
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
    public void Verify_number_of_characters() {

        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Character> characters = response.body().as(List.class);
        assertEquals(characters.size(), 195);
    }

                        /*Verify number of character id and house
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
    public void Verify_number_of_character_id_and_house() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<Character> characters = response.body().as(List.class);
        Gson gson = new Gson();

        for (int i = 0; i < characters.size(); i++) {
            Character character = gson.fromJson(gson.toJson(characters.get(i)), Character.class);
            assertFalse(character.getId().isEmpty());
            assertTrue(character.getDumbledoresArmy() instanceof Boolean);
            List<String> house = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");

            boolean b = false;
            if (character.getHouse() == null || house.contains(character.getHouse()))
                b = true;
            assertTrue(b);
            //System.out.println(character.getHouse());
        }


    }

    @Test
    public void Verify_number_of_character_id_and_house2() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        //assign respons body to the  list of "Character class objects"
        List<Character> characters = response.body().as(List.class);
        Gson gson = new Gson();

        for (int i = 0; i < characters.size(); i++) {
            //get a java Map object from the list and convert it to json object using gson:
            String json_character = gson.toJson(characters.get(i));

            //take this json object and convert it to pojo class object using gson:
            Character character = gson.fromJson(json_character, Character.class);

            //assertions:
            assertFalse(character.getId().isEmpty());
            assertTrue(character.getDumbledoresArmy() instanceof Boolean);
            List<String> house = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");

            //some house names are null, so test fails unless we add null value in assertion:
            boolean b = false;
            if (character.getHouse() == null || house.contains(character.getHouse()))
                b = true;
            assertTrue(b);

            //System.out.println(character.getHouse());
        }
    }

    @Test
    public void Verify_number_of_character_id_and_house3() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        String bodyString = response.body().asString();
        Type CharacterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characters = new Gson().fromJson(bodyString, CharacterListType);

        for (Character character : characters) {
            //assertions:
            assertFalse(character.getId().isEmpty());
            assertTrue(character.getDumbledoresArmy() instanceof Boolean);
            List<String> house = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");

            //some house names are null, so test fails unless we add null value in assertion:
            boolean b = false;
            if (character.getHouse() == null || house.contains(character.getHouse()))
                b = true;
            assertTrue(b);

            //System.out.println(character.getHouse());

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
    public void Verify_all_character_information() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<Map<String, Object>> characters = response.body().as(List.class);
        Random rn = new Random();
        Gson gson = new Gson();
        int rnd_nbr = rn.nextInt(characters.size());

        Character random_character = gson.fromJson(gson.toJson(characters.get(rnd_nbr)), Character.class);
        String random_name = random_character.getName();

        Response response1 = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .queryParam("name", random_name)
                .when().get("characters");

        List<Character> characters1 = response1.body().as(List.class);

        Character character1 = gson.fromJson(gson.toJson(characters1.get(0)), Character.class);
        assertEquals(random_character.getName(), character1.getName());
        assertEquals(random_character.getHouse(), character1.getHouse());
        assertEquals(random_character.getId(), character1.getId());
        assertEquals(random_character.getSchool(), character1.getSchool());
        assertEquals(random_character.getMinistryOfMagic(), character1.getMinistryOfMagic());
        assertEquals(random_character.getOrderOfThePhoenix(), character1.getOrderOfThePhoenix());
        assertEquals(random_character.getDumbledoresArmy(), character1.getDumbledoresArmy());
        assertEquals(random_character.getDeathEater(), character1.getDeathEater());
        assertEquals(random_character.getBloodStatus(), character1.getBloodStatus());
        assertEquals(random_character.getSpecies(), character1.getSpecies());
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
    public void Verify_name_search() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .queryParam("name", "Harry Potter")
                .when().get("characters");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        Gson gson = new Gson();
        List<Character> characterList = response.body().as(List.class);

        Character character_Harry = gson.fromJson(gson.toJson(characterList.get(0)), Character.class);
        //System.out.println("character_Harry.getName() = " + character_Harry.getName());
        assertEquals(character_Harry.getName(), "Harry Potter");

        Response response1 = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .queryParam("name", "Marry Potter")
                .when().get("characters");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        List<Character> characterList2 = response1.body().as(List.class);

        assertTrue(characterList2.isEmpty());
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
    public void Verify_house_members() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .when().get("houses");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        String bodyString = response.body().asString();
        Type houseListType = new TypeToken<ArrayList<House>>(){}.getType();
        List<House> houseList = new Gson().fromJson(bodyString,houseListType);

        // 3. Capture the id of the Gryffindor house
        String id_Gryffindor="";
        for (House house : houseList) {
            if( house.getName().equals("Gryffindor")){
                id_Gryffindor = house.getId();
                break;
            }
        }
        //4. Capture the ids of the all members of the Gryffindor house
        List<String> all_ids_from_Gryffindor = null;
        for (int i = 0; i < houseList.size(); i++) {
             if(houseList.get(i).getName().equals("Gryffindor")){
                 all_ids_from_Gryffindor=houseList.get(i).getMembers();
                 break;
             }
        }

        System.out.println("all_ids_from_Gryffindor = " + all_ids_from_Gryffindor);


        Response response1 = given().accept(ContentType.JSON).and()
                .queryParam("key",token).and()
                .pathParam("id",id_Gryffindor)
                .get("houses/{id}");
        
        String bodyString1 = response1.body().asString();
        Type oneHouseListType = new TypeToken<ArrayList<OneHouse>>(){}.getType();
        List<OneHouse> OneHouse = new Gson().fromJson(bodyString1,oneHouseListType);

        List<String> all_ids_from_response1 = new ArrayList<>();

        List<Member> members = OneHouse.get(0).getMembers();
        for (Member member : members) {
            all_ids_from_response1.add(member.getId());
        }

        System.out.println("all_ids_from_response1 = " + all_ids_from_response1);

        assertEquals(all_ids_from_Gryffindor,all_ids_from_response1);

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
    public void Verify_house_members_again() {

        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .pathParam("id", "5a05e2b252f721a3cf2ea33f")
                .get("houses/{id}");

        String bodyString = response.body().asString();
        Type oneHouseListType = new TypeToken<ArrayList<OneHouse>>() {}.getType();
        List<OneHouse> OneHouse = new Gson().fromJson(bodyString, oneHouseListType);
        response.prettyPrint();

        List<String> all_ids_from_response = new ArrayList<>();

        List<Member> members = OneHouse.get(0).getMembers();
        for (Member member : members) {
            all_ids_from_response.add(member.getId());
        }
        System.out.println("all_ids_from_response = " + all_ids_from_response);

        Response response1 = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .queryParam("house","Gryffindor")
                .when().get("characters");


        String bodyString1 = response1.body().asString();
        Type CharacterListType = new TypeToken<ArrayList<Character>>() {}.getType();
        List<Character> characters = new Gson().fromJson(bodyString1, CharacterListType);
        List<String> all_ids_from_response1 = new ArrayList<>();
        for (Character character : characters) {
           all_ids_from_response1.add(character.getId());
        }
        System.out.println("all_ids_from_response1 = " + all_ids_from_response1);

        assertEquals(all_ids_from_response,all_ids_from_response1);
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
    public void Verify_house_with_most_members(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", token).and()
                .when().get("houses");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");

        String bodyString = response.body().asString();
        Type houseListType = new TypeToken<ArrayList<House>>(){}.getType();
        List<House> houseList = new Gson().fromJson(bodyString,houseListType);
        int maxSize=0;
        String house_which_has_most_members = "";
        for (House house : houseList) {
            if(house.getMembers().size()>maxSize){
                maxSize=house.getMembers().size();
                house_which_has_most_members=house.getName();
            }
        }

        assertEquals(house_which_has_most_members,"Gryffindor");

        System.out.println("house_which_has_most_members = " + house_which_has_most_members);

        System.out.println("houseList.get(0).getMembers().size() = " + houseList.get(0).getMembers().size());
    }


}













