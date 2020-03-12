
package Day6_gson.self_example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links__ {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("student")
    @Expose
    private Student__ student;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Links__() {
    }

    /**
     * 
     * @param student
     * @param self
     */
    public Links__(Self self, Student__ student) {
        super();
        this.self = self;
        this.student = student;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }

    public Student__ getStudent() {
        return student;
    }

    public void setStudent(Student__ student) {
        this.student = student;
    }

}
