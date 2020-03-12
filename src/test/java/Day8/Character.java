
package Day8;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Character {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("ministryOfMagic")
    @Expose
    private Boolean ministryOfMagic;
    @SerializedName("orderOfThePhoenix")
    @Expose
    private Boolean orderOfThePhoenix;
    @SerializedName("dumbledoresArmy")
    @Expose
    private Boolean dumbledoresArmy;
    @SerializedName("deathEater")
    @Expose
    private Boolean deathEater;
    @SerializedName("bloodStatus")
    @Expose
    private String bloodStatus;
    @SerializedName("species")
    @Expose
    private String species;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("patronus")
    @Expose
    private String patronus;
    @SerializedName("wand")
    @Expose
    private String wand;
    @SerializedName("boggart")
    @Expose
    private String boggart;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Character() {
    }

    /**
     * 
     * @param patronus
     * @param role
     * @param ministryOfMagic
     * @param orderOfThePhoenix
     * @param house
     * @param bloodStatus
     * @param school
     * @param species
     * @param v
     * @param deathEater
     * @param dumbledoresArmy
     * @param wand
     * @param name
     * @param alias
     * @param id
     * @param boggart
     */
    public Character(String id, String name, String role, String house, String school, Integer v, Boolean ministryOfMagic, Boolean orderOfThePhoenix, Boolean dumbledoresArmy, Boolean deathEater, String bloodStatus, String species, String alias, String patronus, String wand, String boggart) {
        super();
        this.id = id;
        this.name = name;
        this.role = role;
        this.house = house;
        this.school = school;
        this.v = v;
        this.ministryOfMagic = ministryOfMagic;
        this.orderOfThePhoenix = orderOfThePhoenix;
        this.dumbledoresArmy = dumbledoresArmy;
        this.deathEater = deathEater;
        this.bloodStatus = bloodStatus;
        this.species = species;
        this.alias = alias;
        this.patronus = patronus;
        this.wand = wand;
        this.boggart = boggart;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getMinistryOfMagic() {
        return ministryOfMagic;
    }

    public void setMinistryOfMagic(Boolean ministryOfMagic) {
        this.ministryOfMagic = ministryOfMagic;
    }

    public Boolean getOrderOfThePhoenix() {
        return orderOfThePhoenix;
    }

    public void setOrderOfThePhoenix(Boolean orderOfThePhoenix) {
        this.orderOfThePhoenix = orderOfThePhoenix;
    }

    public Boolean getDumbledoresArmy() {
        return dumbledoresArmy;
    }

    public void setDumbledoresArmy(Boolean dumbledoresArmy) {
        this.dumbledoresArmy = dumbledoresArmy;
    }

    public Boolean getDeathEater() {
        return deathEater;
    }

    public void setDeathEater(Boolean deathEater) {
        this.deathEater = deathEater;
    }

    public String getBloodStatus() {
        return bloodStatus;
    }

    public void setBloodStatus(String bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    public String getWand() {
        return wand;
    }

    public void setWand(String wand) {
        this.wand = wand;
    }

    public String getBoggart() {
        return boggart;
    }

    public void setBoggart(String boggart) {
        this.boggart = boggart;
    }

}
