
package apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "name",
    "mascot",
    "headOfHouse",
    "houseGhost",
    "founder",
    "__v",
    "school",
    "members",
    "values",
    "colors"
})
public class House {

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mascot")
    private String mascot;
    @JsonProperty("headOfHouse")
    private String headOfHouse;
    @JsonProperty("houseGhost")
    private String houseGhost;
    @JsonProperty("founder")
    private String founder;
    @JsonProperty("__v")
    private Integer _v;
    @JsonProperty("school")
    private String school;
    @JsonProperty("members")
    private List<String> members = null;
    @JsonProperty("values")
    private List<String> values = null;
    @JsonProperty("colors")
    private List<String> colors = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("mascot")
    public String getMascot() {
        return mascot;
    }

    @JsonProperty("mascot")
    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    @JsonProperty("headOfHouse")
    public String getHeadOfHouse() {
        return headOfHouse;
    }

    @JsonProperty("headOfHouse")
    public void setHeadOfHouse(String headOfHouse) {
        this.headOfHouse = headOfHouse;
    }

    @JsonProperty("houseGhost")
    public String getHouseGhost() {
        return houseGhost;
    }

    @JsonProperty("houseGhost")
    public void setHouseGhost(String houseGhost) {
        this.houseGhost = houseGhost;
    }

    @JsonProperty("founder")
    public String getFounder() {
        return founder;
    }

    @JsonProperty("founder")
    public void setFounder(String founder) {
        this.founder = founder;
    }

    @JsonProperty("__v")
    public Integer getV() {
        return _v;
    }

    @JsonProperty("__v")
    public void setV(Integer v) {
        this._v = v;
    }

    @JsonProperty("school")
    public String getSchool() {
        return school;
    }

    @JsonProperty("school")
    public void setSchool(String school) {
        this.school = school;
    }

    @JsonProperty("members")
    public List<String> getMembers() {
        return members;
    }

    @JsonProperty("members")
    public void setMembers(List<String> members) {
        this.members = members;
    }

    @JsonProperty("values")
    public List<String> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<String> values) {
        this.values = values;
    }

    @JsonProperty("colors")
    public List<String> getColors() {
        return colors;
    }

    @JsonProperty("colors")
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
