
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student_ {

    @SerializedName("href")
    @Expose
    private String href;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Student_() {
    }

    /**
     * 
     * @param href
     */
    public Student_(String href) {
        super();
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
