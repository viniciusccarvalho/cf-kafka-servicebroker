package io.pivotal.cf.broker.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A service plan available for a ServiceDefinition
 * 
 * @author sgreenberg@gopivotal.com
 * @author vcarvalho@gopivotal.com
 */
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Plan {

	@JsonSerialize
	@JsonProperty("id")
	private String id = UUID.randomUUID().toString();
	
	@JsonSerialize
	@JsonProperty("name")
	private String name;
	
	@JsonSerialize
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("metadata")
	private PlanMetadata metadata;
	
	
	private ServiceDefinition serviceDefinition;
	
	
	public Plan(){}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}



	public ServiceDefinition getServiceDefinition() {
		return serviceDefinition;
	}

	public void setServiceDefinition(ServiceDefinition sid) {
		this.serviceDefinition = sid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PlanMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(PlanMetadata metadata) {
		this.metadata = metadata;
	}
	
}
