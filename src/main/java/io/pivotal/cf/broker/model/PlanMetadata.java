package io.pivotal.cf.broker.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * Plan metadata entity
 * 
 * @author vcarvalho@gopivotal.com
 *
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class PlanMetadata {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonSerialize
	@JsonProperty("bullets")
	private List<String> bullets;
	
	@JsonProperty("displayName")
	private String displayName;
	
	@JsonIgnore
	private Plan plan;
	
	protected Map<String,String> other = new HashMap<>();

	
	@JsonAnyGetter
    public Map<String,String> any() {
        return other;
    }

    @JsonAnySetter
    public void set(String name, String value) {
        other.put(name, value);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getBullets() {
		return bullets;
	}

	public void setBullets(List<String> bullets) {
		this.bullets = bullets;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Map<String, String> getOther() {
		return other;
	}

	public void setOther(Map<String, String> other) {
		this.other = other;
	}
	
}
