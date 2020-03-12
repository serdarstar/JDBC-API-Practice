package Day6_gson.self_example_3_POJO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Depts{

	@JsonProperty("department_id")
	private int departmentId;

	@JsonProperty("manager_id")
	private int managerId;

	@JsonProperty("department_name")
	private String departmentName;

	@JsonProperty("links")
	private List<LinksItem> links;

	@JsonProperty("location_id")
	private int locationId;

	public void setDepartmentId(int departmentId){
		this.departmentId = departmentId;
	}

	public int getDepartmentId(){
		return departmentId;
	}

	public void setManagerId(int managerId){
		this.managerId = managerId;
	}

	public int getManagerId(){
		return managerId;
	}

	public void setDepartmentName(String departmentName){
		this.departmentName = departmentName;
	}

	public String getDepartmentName(){
		return departmentName;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setLocationId(int locationId){
		this.locationId = locationId;
	}

	public int getLocationId(){
		return locationId;
	}

	@Override
 	public String toString(){
		return 
			"Depts{" + 
			"department_id = '" + departmentId + '\'' + 
			",manager_id = '" + managerId + '\'' + 
			",department_name = '" + departmentName + '\'' + 
			",links = '" + links + '\'' + 
			",location_id = '" + locationId + '\'' + 
			"}";
		}
}