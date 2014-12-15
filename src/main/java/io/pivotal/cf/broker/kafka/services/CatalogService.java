package io.pivotal.cf.broker.kafka.services;

import io.pivotal.cf.broker.model.ServiceDefinition;

import java.util.List;

public interface CatalogService {

	public List<ServiceDefinition> listServices();
	
	
}
