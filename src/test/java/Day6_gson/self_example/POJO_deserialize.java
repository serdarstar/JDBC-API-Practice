package Day6_gson.self_example;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import static io.restassured.RestAssured.*;

public class POJO_deserialize {


    @Test
    public void regionWithPojo(){
        //request
        Response response = get("http://api.cybertektraining.com/students/3408");
        assertEquals(response.statusCode(),200);

        //JSON to Region class
        //Deserizaliton

        //1. KADEME= Burasi ilk seviye, hic problem yok, direk yazdiriyor herseyi
        Cyber student = response.body().as(Cyber.class);
        System.out.println("student.getAdmissionNo() = " + student.getAdmissionNo());
        System.out.println("student.getGender() = " + student.getGender());

        System.out.println("********************************************************************");

        //2. KADEME= Burasi cok onemli: Once Links_ classa , student icinden ulasmamiz gerekiyor ki, Link_ class icindeki metodlara ulasalim
        Links__ links=student.getLinks();

        //Bu sekilde calismiyor cunku bu sadece icindeki linkin adresi
        System.out.println("links.getSelf() = " + links.getSelf());


        //Bir kademe daha asagi inince calisiyor
        System.out.println("links.getSelf().getHref().toString() = " + links.getSelf().getHref().toString());


        //3.KADEME: Links_ inde icinde  Student_ class var. Ona ulasmak icinde once Links_ icinden ulasmamiz gerekiyor.
        //Asagidaki direk ulasmaya calistigi icin hata veriyor
        System.out.println("links.getStudent() = " + links.getStudent().toString());

        //Ama once Links_ class dan olusturdgunuz links object i kullnarak Student_ class tan yeni bir student1 object i olusturursak, calisiyor

        Student__ student1=links.getStudent();
        System.out.println("student1.getHref() = " + student1.getHref());


        System.out.println("********************************************************************");
        //Ayni seyler Company class icinde gecerli
        Company company=student.getCompany();
        System.out.println("company.getAddress().getCity().toString() = " + company.getAddress().getCity().toString());


        Contact contact=student.getContact();
        System.out.println("contact.getPhone().toString() = " + contact.getPhone());
        System.out.println("contact.getEmailAddress() = " + contact.getEmailAddress(1));

        //List<Link> links=employees.getLinks();
        String emailAddress=contact.getEmailAddress(2);
        System.out.println("emailAddress = " + emailAddress);


//        response.body().prettyPrint();
        System.out.println("contact[0].getEmailAddress(1) = " + contact.getLinks());


//        System.out.println("contact.getEmailAddress(0).toString() = " + contact.getEmailAddress(0).toString());
//
//        List<Item> regionList =  student.getItems();
//        System.out.println(regionList.get(0).getRegionName());
//        System.out.println("regionList.get(1).getRegionId() = " + regionList.get(1).getRegionId());
//
//        System.out.println(student.getItems().get(0).getRegionName());
//
//        for (Item item : regionList) {
//            System.out.println(item.getRegionName());
//        }

    }

}
