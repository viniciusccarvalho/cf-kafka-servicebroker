package io.pivotal.cf.broker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A service offered by this broker.
 * 
 * @author sgreenberg@gopivotal.com
 * @author vcarvalho@gopivotal.com
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ServiceDefinition {

	@JsonSerialize
	@JsonProperty("id")
	private String id = UUID.randomUUID().toString();
	
	@JsonSerialize
	@JsonProperty("name")
	private String name;
	
	@JsonSerialize
	@JsonProperty("description")
	private String description;
	
	@JsonSerialize
	@JsonProperty("bindable")
	private boolean bindable;
	
	@JsonSerialize
	@JsonProperty("plans")
	private List<Plan> plans = new ArrayList<Plan>();
	
	@JsonSerialize
	@JsonProperty("tags")
	private List<String> tags = new ArrayList<String>();
	
	@JsonSerialize
	@JsonProperty("metadata")
	private Map<String,String> metadata = new HashMap<String,String>();
	
	@JsonSerialize
	@JsonProperty("requires")
	private List<String> requires = new ArrayList<String>();
	
	public ServiceDefinition(String id, String name, String description, boolean bindable, List<Plan> plans) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.bindable = bindable;
		this.setPlans(plans);
	}

	public ServiceDefinition(String id, String name, String description, boolean bindable, List<Plan> plans,
			List<String> tags, Map<String,String> metadata, List<String> requires) {
		this(id, name, description, bindable, plans);
		setTags(tags);
		setMetadata(metadata);
		setRequires(requires);
	}
	
	public ServiceDefinition(){}
	
	public ServiceDefinition(String id){
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isBindable() {
		return bindable;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		if ( plans == null ) {
			// ensure serialization as an empty array and not null
			this.plans = new ArrayList<Plan>();
		} else {
			this.plans = plans;
		}
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		if (tags == null) {
			this.tags = new ArrayList<String>();
		} else {
			this.tags = tags;
		}
	}

	public List<String> getRequires() {
		return requires;
	}

	public void setRequires(List<String> requires) {
		if (requires == null) {
			this.requires = new ArrayList<String>();
		} else {
			this.requires = requires;
		}
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		if (metadata == null) {
			this.metadata = new HashMap<String,String>();
		} else {
			this.metadata = metadata;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBindable(boolean bindable) {
		this.bindable = bindable;
	}

}
