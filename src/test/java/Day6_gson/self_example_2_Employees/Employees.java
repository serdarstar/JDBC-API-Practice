
package Day6_gson.self_example_2_Employees;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employees {

    @SerializedName("employee_id")
    @Expose
    private Integer employeeId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("hire_date")
    @Expose
    private String hireDate;
    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("salary")
    @Expose
    private Integer salary;
    @SerializedName("commission_pct")
    @Expose
    private Object commissionPct;
    @SerializedName("manager_id")
    @Expose
    private Integer managerId;
    @SerializedName("department_id")
    @Expose
    private Integer departmentId;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Employees() {
    }

    /**
     * 
     * @param firstName
     * @param lastName
     * @param jobId
     * @param hireDate
     * @param phoneNumber
     * @param departmentId
     * @param employeeId
     * @param links
     * @param managerId
     * @param salary
     * @param email
     * @param commissionPct
     */
    public Employees(Integer employeeId, String firstName, String lastName, String email, String phoneNumber, String hireDate, String jobId, Integer salary, Object commissionPct, Integer managerId, Integer departmentId, List<Link> links) {
        super();
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.jobId = jobId;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.links = links;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Object getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Object commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
