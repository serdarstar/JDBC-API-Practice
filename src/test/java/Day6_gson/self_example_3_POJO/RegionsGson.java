package Day6_gson.self_example_3_POJO;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class RegionsGson{

	@SerializedName("offset")
	private int offset;

	@SerializedName("hasMore")
	private boolean hasMore;

	@SerializedName("limit")
	private int limit;

	@SerializedName("count")
	private int count;

	@SerializedName("links")
	private List<LinksItem> links;

	@SerializedName("items")
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