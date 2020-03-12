package Day6_gson;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.util.List;

public class POJO_deserialize {

    @Test
    public void oneSpartanWithPOJO(){
        Response response = given()
                .accept(ContentType.JSON).pathParam("id",15)
                .when().get("http://3.95.190.225:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        Spartan spartan1=response.body().as(Spartan.class);
        System.out.println("spartan1.toString() = " + spartan1.toString());

        System.out.println("spartan1.getGender() = " + spartan1.getGender());
        System.out.println("spartan1.getId() = " + spartan1.getId());
        System.out.println("spartan1.getName() = " + spartan1.getName());
        System.out.println("spartan1.getPhone() = " + spartan1.getPhone());
        assertEquals(spartan1.getId(),15);
        assertEquals(spartan1.getGender(),"Female");
        assertEquals(spartan1.getName(),"Meta");
        assertEquals(spartan1.getPhone(), Long.valueOf(1938695106));

    }
    @Test
    public void regionWithPojo(){
        //request
        Response response = get("http://3.95.190.225:1000/ords/hr/regions");
        assertEquals(response.statusCode(),200);

        //JSON to Region class
        //Deserizaliton
       //1. Kademe, body deki ilk seviye key ler
        Region regions = response.body().as(Region.class);
//        response.body().prettyPrint();

        System.out.println(regions.getCount());

        //Bb ilke seviyede baska links isimli baska bi key var ve value leri yine farkli linkler. Bunlara ulasmak icin
        //ilk seviye icinde yer alan method ile list olusturuyoruz. Olusan listten istegimiz index number a gore istedigimiz
        // link e ulasiyoruz

        List<Item> regionList =  regions.getItems();
        System.out.println(regionList.get(0).getRegionName());
        System.out.println("regionList.get(1).getRegionId() = " + regionList.get(1).getRegionId());

        System.out.println(regions.getItems().get(0).getRegionName());

        //veya hepsini yazdiradabiliriz
        for (Item item : regionList) {
            System.out.println(item.getRegionName());
        }

        //2. Kademe: Her item icinde ayrica linkler var. Bunlari ayri bir class olarak olusturuyor. Biz istedigimiz item i index ile cagirip, daha
        //sonra o index ten getLinks methodu ile linkleri listeleyip, yine bu listeden index ile istedigmiz linki secip onun icindeki key and value ye
        // ulasabiliyoruz. Ornek:

        System.out.println("regions.getItems().get(0).getLinks().get(0).getHref() = " + regions.getItems().get(0).getLinks().get(0).getHref());
        System.out.println("regions.getItems().get(0).getLinks().get(0).getHref() = " + regions.getItems().get(1).getLinks().get(0).getHref());
        System.out.println("regions.getItems().get(0).getLinks().get(0).getHref() = " + regions.getItems().get(2).getLinks().get(0).getHref());
        System.out.println("regions.getItems().get(0).getLinks().get(0).getHref() = " + regions.getItems().get(2).getLinks().get(0).getRel());

        for (int i = 0; i <= regions.getCount()-1; i++) {
            System.out.println("Href = " + regions.getItems().get(i).getLinks().get(0).getHref());

        }
//        System.out.println("****************************************");
//        List<Link> regionList2 =  regions.getItems().get(2).getLinks();
//        for (int i = 0; i <= regionList.size(); i++) {
//            System.out.println(regionList2.get(i).getHref());
//
//        }


    }

    @Test
    public void GsonExample(){

        //creating gson object
        Gson gson = new Gson();

        //De-Serialize and serialize with gson object
        //Deseriailze -->JSON TO Java Object

        String myjson = "{\n" +
                "    \"id\": 15,\n" +
                "    \"name\": \"Meta\",\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"phone\": 1938695106\n" +
                "}";

        //converting json to pojo(Spartan class)
        Spartan spartan15= gson.fromJson(myjson,Spartan.class);

        System.out.println("spartan15.getName() = " + spartan15.getName());
        System.out.println("spartan15.getPhone() = " + spartan15.getPhone());
        //-----------------------SERIALIZATION-------------
        //Java object to JSON
        Spartan spartanEU = new Spartan(10,"Mike","Male",5714788554L);
        //it will take spartan eu information and convert to json
        String jsonSpartanEU = gson.toJson(spartanEU);

        System.out.println("jsonSpartanEU = " + jsonSpartanEU);





    }
}
