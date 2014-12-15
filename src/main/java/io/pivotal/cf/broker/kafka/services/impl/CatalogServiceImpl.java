package io.pivotal.cf.broker.kafka.services.impl;

import io.pivotal.cf.broker.kafka.services.CatalogService;
import io.pivotal.cf.broker.model.ServiceDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<ServiceDefinition> listServices() {
		List<ServiceDefinition> services = new ArrayList<ServiceDefinition>();
		try {
			InputStream in = CatalogServiceImpl.class.getClassLoader().getResourceAsStream("ServiceDescription.json");
			ServiceDefinition def = mapper.readValue(in, ServiceDefinition.class);
			services.add(def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return services;
	}

}
