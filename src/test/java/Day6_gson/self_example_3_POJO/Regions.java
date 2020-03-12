package Day6_gson.self_example_3_POJO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Regions{

	@JsonProperty("offset")
	private int offset;

	@JsonProperty("hasMore")
	private boolean hasMore;

	@JsonProperty("limit")
	private int limit;

	@JsonProperty("count")
	private int count;

	@JsonProperty("links")
	private List<LinksItem> links;

	@JsonProperty("items")
	private List<ItemsItem> items;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setHasMore(boolean hasMore){
		this.hasMore = hasMore;
	}

	public boolean isHasMore(){
		return hasMore;
	}

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setLinks(List<LinksItem> links){
		this.links = links;
	}

	public List<LinksItem> getLinks(){
		return links;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}
}