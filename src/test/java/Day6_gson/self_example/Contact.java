
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("premanentAddress")
    @Expose
    private String premanentAddress;
    @SerializedName("_links")
    @Expose
    private Links links;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Contact() {
    }

    /**
     * 
     * @param emailAddress
     * @param phone
     * @param links
     * @param premanentAddress
     */
    public Contact(String phone, String emailAddress, String premanentAddress, Links links) {
        super();
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.premanentAddress = premanentAddress;
        this.links = links;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress(int i) {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPremanentAddress() {
        return premanentAddress;
    }

    public void setPremanentAddress(String premanentAddress) {
        this.premanentAddress = premanentAddress;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
