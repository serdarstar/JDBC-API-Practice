
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("_links")
    @Expose
    private Links_ links;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Company() {
    }

    /**
     * 
     * @param address
     * @param companyName
     * @param links
     * @param title
     * @param startDate
     */
    public Company(String companyName, String title, String startDate, Address address, Links_ links) {
        super();
        this.companyName = companyName;
        this.title = title;
        this.startDate = startDate;
        this.address = address;
        this.links = links;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Links_ getLinks() {
        return links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }

}
