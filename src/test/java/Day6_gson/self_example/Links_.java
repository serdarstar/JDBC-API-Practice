
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @SerializedName("student")
    @Expose
    private Student_ student;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Links_() {
    }

    /**
     * 
     * @param student
     */
    public Links_(Student_ student) {
        super();
        this.student = student;
    }

    public Student_ getStudent() {
        return student;
    }

    public void setStudent(Student_ student) {
        this.student = student;
    }

}
