package Day6_gson.self_example_2_Employees;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.get;
import static org.testng.Assert.assertEquals;

public class POJO_deserialize {


    @Test
    public void employeesWithPojo(){
        //request
        Response response = get(ConfigurationReader.get("hrapi.uri").concat("/employees/105"));
        assertEquals(response.statusCode(),200);

        //JSON to Region class
        //Deserizaliton
        Employees employees = response.body().as(Employees.class);
        System.out.println("employees.getDepartmentId() = " + employees.getDepartmentId());

        List<Link> links=employees.getLinks();
        System.out.println("links.get(1).getHref().toString() = " + links.get(1).getHref().toString());


        for (Link item : links) {
            System.out.println(item.getHref());
        }

    }

}
