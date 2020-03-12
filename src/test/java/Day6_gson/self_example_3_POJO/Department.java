
package Day6_gson.self_example_3_POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {

    @SerializedName("department_id")
    @Expose
    private Integer departmentId;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("manager_id")
    @Expose
    private Integer managerId;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Department() {
    }

    /**
     * 
     * @param departmentName
     * @param locationId
     * @param departmentId
     * @param links
     * @param managerId
     */
    public Department(Integer departmentId, String departmentName, Integer managerId, Integer locationId, List<Link> links) {
        super();
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.locationId = locationId;
        this.links = links;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
