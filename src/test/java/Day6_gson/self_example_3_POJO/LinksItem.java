package Day6_gson.self_example_3_POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LinksItem{

	@JsonProperty("rel")
	private String rel;

	@JsonProperty("href")
	private String href;

	public void setRel(String rel){
		this.rel = rel;
	}

	public String getRel(){
		return rel;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}
}