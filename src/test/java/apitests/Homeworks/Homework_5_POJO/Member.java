
package apitests.Homeworks.Homework_5_POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Member {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).toString();
    }

}
