package apitests.Homeworks.Homework_5_My_Solutions;


import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.ConfigurationReader;

import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
public class Homework_5 {
    String key = "$2a$10$Jyx8ZN7Xf0kmzjWs7mRVh.ugmEbhBwjGgcLoMO81NhFWlWveSHGp.";
    @BeforeClass
    public static void setup(){
        baseURI = ConfigurationReader.get("harryPotterAPI.uri");
    }
    @Test
    public void Verify_sorting_hat(){
        Response response = given().accept(ContentType.JSON).when().get("sortingHat");
        assertEquals(response.statusCode(),200);
        List<String> hats=new ArrayList<>(Arrays.asList("Gryffindor","Ravenclaw","Slytherin","Hufflepuff"));
        System.out.println("hats = " + hats);
        String result=response.body().prettyPrint().substring(1,response.body().prettyPrint().length()-1);
        System.out.println("result = " + result);
        assertTrue(hats.contains(result));
//      Diger yolu
//        boolean flag=false;
//
//        for (int i = 0; i < hats.size(); i++) {
//            System.out.println(hats.get(i));
//            if(hats.get(i).equals(result)){
//                System.out.println(hats.get(i));
//                flag=true;
//            }
//        }
//
//        assertEquals(flag,true);
    }
    @Test
    public void Verify_bad_key(){
        String key="invalid";
        Response response = given().accept(ContentType.JSON).queryParam("key", key).get("characters");
        assertEquals(response.statusCode(),401);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Unauthorized"));
        assertEquals(response.body().path("error").toString(),"API Key Not Found");
    }
    @Test
    public void Verify_no_key(){
        String key="invalid";
        Response response = given().accept(ContentType.JSON).get("characters");
        assertEquals(response.statusCode(),409);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        assertTrue(response.statusLine().contains("Conflict"));
        assertEquals(response.body().path("error").toString(),"Must pass API key for request");
    }
    @Test
    public void Verify_number_of_characters(){
        String key="$2a$10$Jyx8ZN7Xf0kmzjWs7mRVh.ugmEbhBwjGgcLoMO81NhFWlWveSHGp.";
        Response response = given().accept(ContentType.JSON).queryParam("key",key).get("characters");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json; charset=utf-8");
        Character character=response.body().as((Type) Character[].class);
        System.out.println(character.getName().toString());
//        System.out.println("character.getId().toString() = " + character.getId().toString());
        System.out.println("character.getName().toString() = " + character.getName().toString());
//        System.out.println(response.body().path("_id[10]").toString());
    }
    @Test
    public void houses() {
        Response response = given().accept(ContentType.JSON).queryParam("key", key)
                .get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Character> characters = response.body().as(List.class);
        System.out.println("characters.size() = " + characters.size()); //calisti
        //System.out.println("character.getBloodStatus() = " + character.getBloodStatus()); //calismadi
        Gson gson = new Gson();
        String jsoncharacter = gson.toJson(characters.get(0)); //listeden aldigimiz map'i json'a donusturduk
        Character character0 = gson.fromJson(jsoncharacter, Character.class); // json'i pojo class object 'e donusturduk
        System.out.println("character0.getBloodStatus() = " + character0.getBloodStatus()); //calisti!
        assertEquals(characters.size(), 195);
        System.out.println("..........................SOLUTION 2.....................................................");
        List<Map<String, Object>> charlList=response.body().as(List.class);
        for (Map<String, Object> stringObjectMap : charlList) {
            Assert.assertTrue(stringObjectMap.containsKey("_id"));
            Assert.assertNotNull(stringObjectMap.get("_id"));
            System.out.println("stringObjectMap = " + stringObjectMap);
        }
        System.out.println("charlList.get(1).get(\"_id\") = " + charlList.get(0).get("name"));
    }
    @Test
    public void Verify_number_of_character_id_and_house() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Character> characters = response.body().as(List.class);
        Gson gson = new Gson();
        int nr_of_chars = characters.size();
        for (int i = 0; i < characters.size(); i++) {
            Character character = gson.fromJson(gson.toJson(characters.get(i)), Character.class);
            assertFalse(character.getId().isEmpty());
            assertTrue(character.getDumbledoresArmy() instanceof Boolean);
            List<String> house = Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff");
            boolean b = false;
            if(character.getHouse()==null||house.contains(character.getHouse()))
                b=true;
            assertTrue(b);
            System.out.println("character.getHouse() = " + character.getHouse());
        }
    }
    @Test
    public void verify_all_character_information(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Character> characters = response.body().as(List.class);
        Random rn = new Random();
        Gson gson = new Gson();
        int rnd_nbr = rn.nextInt(characters.size());
        Character random_character = gson.fromJson(gson.toJson(characters.get(rnd_nbr)),Character.class);
        String random_name = random_character.getName();
        Response response2 = given().accept(ContentType.JSON).and()
                .queryParam("key", key).queryParam("name",random_name)
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Character>  characters1 = response2.body().as(List.class);
        Character character1 = gson.fromJson(gson.toJson(characters1.get(0)),Character.class);
        assertEquals(random_character.getName(),character1.getName());
        assertEquals(random_character.getHouse(),character1.getHouse());
        assertEquals(random_character.getId(),character1.getId());
        assertEquals(random_character.getSchool(),character1.getSchool());
        assertEquals(random_character.getMinistryOfMagic(),character1.getMinistryOfMagic());
        assertEquals(random_character.getOrderOfThePhoenix(),character1.getOrderOfThePhoenix());
        assertEquals(random_character.getDumbledoresArmy(),character1.getDumbledoresArmy());
        assertEquals(random_character.getDeathEater(),character1.getDeathEater());
        assertEquals(random_character.getBloodStatus(),character1.getBloodStatus());
        assertEquals(random_character.getSpecies(),character1.getSpecies());
    }
    @Test
    public void verify_name_search(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .queryParam("name","Harry Potter")
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//        List<Map<String,Object>> characters=response.body().as(List.class);
//        System.out.println("characters.get(0).get(\"name\").toString() = " + characters.get(0).get("name").toString());
        List<Character> characters=response.body().as(List.class);
        Gson gson = new Gson();
        Character name = gson.fromJson(gson.toJson(characters.get(0)),Character.class);
        System.out.println("random_character.getName() = " + name.getName());
        assertEquals(name.getName(),"Harry Potter");
        Response response2 = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .queryParam("name","Marry Potter")
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        response2.body().prettyPrint();
        List<Character> characters2=response2.body().as(List.class);
        System.out.println("characters2.size() = " + characters2.size());
        assertEquals(characters2.size(),0);
    }
    @Test
    public void verify_house_members(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .when().get("houses");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<House>  houses= response.body().as(List.class);
        System.out.println("characters.size() = " + houses.size()); //calisti
        //System.out.println("character.getBloodStatus() = " + character.getBloodStatus()); //calismadi
        Gson gson = new Gson();
        String jsoncharacter = gson.toJson(houses.get(0)); //listeden aldigimiz map'i json'a donusturduk
        House house0 = gson.fromJson(jsoncharacter, House.class); // json'i pojo class object 'e donusturduk
        System.out.println("house0.getId() = " + house0.getId());
        List<String> members=house0.getMembers();
        System.out.println("members = " + members);
        /*
        Send a get request to /houses/:id. Request includes :
        • Header Accept with value application/json
        • Query param key with value {{apiKey}}
        • Path param id with value from step 3
                 */
        Response response2 = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .pathParam(":id",house0.getId())
                .when().get("houses/{:id}");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//        response2.prettyPrint();
//         Verify that response contains the same member ids as the step 4
        List<Map<String,Object>> members2=response2.body().as(List.class);
//        System.out.println("members2 = " + members2);
        List<Map<String,Object>> membersIDs= (List<Map<String, Object>>) members2.get(0).get("members");
        for (int i = 0; i < membersIDs.size(); i++) {
            System.out.println("memberIDs = " + membersIDs.get(i).get("_id"));
            assertEquals(membersIDs.get(i).get("_id"),members.get(i));
        }
    }
    @Test
    public void verify_house_members_again() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .pathParam(":id", "5a05e2b252f721a3cf2ea33f")
                .when().get("houses/{:id}");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        List<Map<String, Object>> members2 = response.body().as(List.class);
//        response.prettyPrint();
//        System.out.println("members2 = " + members2);
        List<Map<String, Object>> membersIDs = (List<Map<String, Object>>) members2.get(0).get("members");
        for (int i = 0; i < membersIDs.size(); i++) {
            System.out.println("memberIDs = " + membersIDs.get(i).get("_id"));
        }
        Response response2 = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .queryParam("house", "Gryffindor")
                .when().get("characters");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//            response2.prettyPrint();
        List<Map<String, Object>> members3 = response2.body().as(List.class);
        for (int j = 0; j < members3.size(); j++) {
            System.out.println("members3= " + members3.get(j).get("_id"));
        }
        System.out.println(".....................................................");
        List<Object> member4 = new ArrayList<>();
        for (int i = 0; i < membersIDs.size(); i++) {
            member4.add(membersIDs.get(i).get("_id"));
        }
        System.out.println(member4);
        List<Object> member5 = new ArrayList<>();
        for (int i = 0; i < members3.size(); i++) {
            member5.add(members3.get(i).get("_id"));
        }
        System.out.println(member5);
        assertEquals(member4, member5); // Fail oluyor cunku Character ile gelende bir fazla ID var
    }
    @Test
    public void verify_house_with_the_most_members(){
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("key", key)
                .when().get("houses");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//        response.prettyPrint();
        List<Map<String, Object>> members2 = response.body().as(List.class);
        System.out.println("members2.size() = " + members2.size());
        List<Map<String, Object>> membersCountHouse1 = (List<Map<String, Object>>) members2.get(0).get("members");
        System.out.println("membersCountHouse1.size() = " + membersCountHouse1.size());
        List<Map<String, Object>> membersCountHouse2 = (List<Map<String, Object>>) members2.get(1).get("members");
        System.out.println("membersCountHouse2.size() = " + membersCountHouse2.size());
        List<Map<String, Object>> membersCountHouse3 = (List<Map<String, Object>>) members2.get(2).get("members");
        System.out.println("membersCountHouse3.size() = " + membersCountHouse3.size());
        List<Map<String, Object>> membersCountHouse4 = (List<Map<String, Object>>) members2.get(3).get("members");
        System.out.println("membersCountHouse4.size() = " + membersCountHouse4.size());
        List<Integer> numberOfMembers=new ArrayList<>(Arrays.asList(membersCountHouse1.size(),membersCountHouse2.size(),membersCountHouse3.size(),membersCountHouse4.size()));
        System.out.println("numberOfMembers = " + numberOfMembers);
        int max=numberOfMembers.get(0);
        for (int i = 0; i < numberOfMembers.size(); i++) {
            if(numberOfMembers.get(i)>max){
                max=numberOfMembers.get(i);
            }
        }
        System.out.println(max);
        if(max==membersCountHouse1.size()){
            System.out.println("House of Gryffindor has the most members");
        }else if(max==membersCountHouse2.size()){
            System.out.println("House of Ravenclaw has the most members");
        }else if(max==membersCountHouse3.size()){
            System.out.println("House of Slytherin has the most members");
        }else if(max==membersCountHouse4.size()){
            System.out.println("House of Hufflepuff has the most members");
        }
    }
}