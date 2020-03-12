package Day6_gson.self_example_3_POJO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ItemsItem{

	@JsonProperty("region_id")
	private int regionId;

	@JsonProperty("region_name")
	private String regionName;

	@JsonProperty("links")
	private List<LinksItem> links;

	public void setRegionId(int regionId){
		this.regionId = regionId;
	}

	public int getRegionId(){
		return regionId;
	}

	public void setRegionName(String regionName){
		this.regionName = regionName;
	}

	public String getRegionName(){
		return regionName;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}
}