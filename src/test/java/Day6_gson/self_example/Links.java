
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("student")
    @Expose
    private Student student;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Links() {
    }

    /**
     * 
     * @param student
     */
    public Links(Student student) {
        super();
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
