package apitests.Homeworks.Homewrok_5_Ramazan_C.HarryPotter.pojos;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "name",
        "house",
        "patronus",
        "species",
        "bloodStatus",
        "role",
        "school",
        "deathEater",
        "dumbledoresArmy",
        "orderOfThePhoenix",
        "ministryOfMagic",
        "alias",
        "wand",
        "boggart",
        "animagus",
        "__v"
})
public class Character {

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("house")
    private String house;
    @JsonProperty("patronus")
    private String patronus;
    @JsonProperty("species")
    private String species;
    @JsonProperty("bloodStatus")
    private String bloodStatus;
    @JsonProperty("role")
    private String role;
    @JsonProperty("school")
    private String school;
    @JsonProperty("deathEater")
    private Boolean deathEater;
    @JsonProperty("dumbledoresArmy")
    private Boolean dumbledoresArmy;
    @JsonProperty("orderOfThePhoenix")
    private Boolean orderOfThePhoenix;
    @JsonProperty("ministryOfMagic")
    private Boolean ministryOfMagic;
    @JsonProperty("alias")
    private String alias;
    @JsonProperty("wand")
    private String wand;
    @JsonProperty("boggart")
    private String boggart;
    @JsonProperty("animagus")
    private String animagus;
    @JsonProperty("__v")
    private Integer __v;
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

    @JsonProperty("house")
    public String getHouse() {
        return house;
    }

    @JsonProperty("house")
    public void setHouse(String house) {
        this.house = house;
    }

    @JsonProperty("patronus")
    public String getPatronus() {
        return patronus;
    }

    @JsonProperty("patronus")
    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    @JsonProperty("species")
    public String getSpecies() {
        return species;
    }

    @JsonProperty("species")
    public void setSpecies(String species) {
        this.species = species;
    }

    @JsonProperty("bloodStatus")
    public String getBloodStatus() {
        return bloodStatus;
    }

    @JsonProperty("bloodStatus")
    public void setBloodStatus(String bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("school")
    public String getSchool() {
        return school;
    }

    @JsonProperty("school")
    public void setSchool(String school) {
        this.school = school;
    }

    @JsonProperty("deathEater")
    public Boolean getDeathEater() {
        return deathEater;
    }

    @JsonProperty("deathEater")
    public void setDeathEater(Boolean deathEater) {
        this.deathEater = deathEater;
    }

    @JsonProperty("dumbledoresArmy")
    public Boolean getDumbledoresArmy() {
        return dumbledoresArmy;
    }

    @JsonProperty("dumbledoresArmy")
    public void setDumbledoresArmy(Boolean dumbledoresArmy) {
        this.dumbledoresArmy = dumbledoresArmy;
    }

    @JsonProperty("orderOfThePhoenix")
    public Boolean getOrderOfThePhoenix() {
        return orderOfThePhoenix;
    }

    @JsonProperty("orderOfThePhoenix")
    public void setOrderOfThePhoenix(Boolean orderOfThePhoenix) {
        this.orderOfThePhoenix = orderOfThePhoenix;
    }

    @JsonProperty("ministryOfMagic")
    public Boolean getMinistryOfMagic() {
        return ministryOfMagic;
    }

    @JsonProperty("ministryOfMagic")
    public void setMinistryOfMagic(Boolean ministryOfMagic) {
        this.ministryOfMagic = ministryOfMagic;
    }

    @JsonProperty("alias")
    public String getAlias() {
        return alias;
    }

    @JsonProperty("alias")
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @JsonProperty("wand")
    public String getWand() {
        return wand;
    }

    @JsonProperty("wand")
    public void setWand(String wand) {
        this.wand = wand;
    }

    @JsonProperty("boggart")
    public String getBoggart() {
        return boggart;
    }

    @JsonProperty("boggart")
    public void setBoggart(String boggart) {
        this.boggart = boggart;
    }

    @JsonProperty("animagus")
    public String getAnimagus() {
        return animagus;
    }

    @JsonProperty("animagus")
    public void setAnimagus(String animagus) {
        this.animagus = animagus;
    }

    @JsonProperty("__v")
    public Integer getV() {
        return __v;
    }

    @JsonProperty("__v")
    public void setV(Integer v) {
        this.__v = v;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Character{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", house='" + house + '\'' +
                ", patronus='" + patronus + '\'' +
                ", species='" + species + '\'' +
                ", bloodStatus='" + bloodStatus + '\'' +
                ", role='" + role + '\'' +
                ", school='" + school + '\'' +
                ", deathEater=" + deathEater +
                ", dumbledoresArmy=" + dumbledoresArmy +
                ", orderOfThePhoenix=" + orderOfThePhoenix +
                ", ministryOfMagic=" + ministryOfMagic +
                ", alias='" + alias + '\'' +
                ", wand='" + wand + '\'' +
                ", boggart='" + boggart + '\'' +
                ", animagus='" + animagus + '\'' +
                ", __v=" + __v +
                '}';
    }
}