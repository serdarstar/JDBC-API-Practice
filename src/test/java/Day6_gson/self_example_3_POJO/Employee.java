package Day6_gson.self_example_3_POJO;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Employee {

	@SerializedName("commission_pct")
	private Object commissionPct;

	@SerializedName("manager_id")
	private int managerId;

	@SerializedName("department_id")
	private int departmentId;

	@SerializedName("job_id")
	private String jobId;

	@SerializedName("employee_id")
	private int employeeId;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("links")
	private List<LinksItem> links;

	@SerializedName("hire_date")
	private String hireDate;

	@SerializedName("salary")
	private int salary;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public void setCommissionPct(Object commissionPct){
		this.commissionPct = commissionPct;
	}

	public Object getCommissionPct(){
		return commissionPct;
	}

	public void setManagerId(int managerId){
		this.managerId = managerId;
	}

	public int getManagerId(){
		return managerId;
	}

	public void setDepartmentId(int departmentId){
		this.departmentId = departmentId;
	}

	public int getDepartmentId(){
		return departmentId;
	}

	public void setJobId(String jobId){
		this.jobId = jobId;
	}

	public String getJobId(){
		return jobId;
	}

	public void setEmployeeId(int employeeId){
		this.employeeId = employeeId;
	}

	public int getEmployeeId(){
		return employeeId;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setHireDate(String hireDate){
		this.hireDate = hireDate;
	}

	public String getHireDate(){
		return hireDate;
	}

	public void setSalary(int salary){
		this.salary = salary;
	}

	public int getSalary(){
		return salary;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"commission_pct = '" + commissionPct + '\'' + 
			",manager_id = '" + managerId + '\'' + 
			",department_id = '" + departmentId + '\'' + 
			",job_id = '" + jobId + '\'' + 
			",employee_id = '" + employeeId + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",links = '" + links + '\'' + 
			",hire_date = '" + hireDate + '\'' + 
			",salary = '" + salary + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}