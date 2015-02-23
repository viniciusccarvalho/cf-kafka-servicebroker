package io.pivotal.cf.broker.kafka.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.pivotal.cf.broker.kafka.services.ServiceManagement;
import io.pivotal.cf.broker.kafka.services.ZooClient;
import io.pivotal.cf.broker.model.CreateServiceInstanceRequest;
import io.pivotal.cf.broker.model.ServiceInstance;
import io.pivotal.cf.broker.model.ServiceInstanceBinding;
import io.pivotal.cf.broker.model.ServiceInstanceBindingRequest;

@Component
public class ServiceManagementImpl implements ServiceManagement {
	
	final static String INSTANCES_PATH = "/sb/instances";
	final static String BINDINGS_PATH = "/sb/bindings";
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ZooClient client;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public ServiceInstance createInstance(CreateServiceInstanceRequest serviceRequest) {
		String dashboard = env.getProperty("manager.url") != null ? env.getProperty("manager.url") : "";
		ServiceInstance instance = new ServiceInstance(serviceRequest.getServiceInstanceId(),
													   serviceRequest.getServiceDefinitionId(),
													   serviceRequest.getPlanId(),
													   serviceRequest.getOrganizationGuid(),
													   serviceRequest.getSpaceGuid(), 
													   dashboard);
		
		if(client.exists(INSTANCES_PATH+"/"+instance.getId())){
			throw new IllegalStateException("There's already an instance of this service");
		}
		byte[] body = null;
		try {
			body = mapper.writeValueAsBytes(instance);
		} catch (Exception e) {
			throw new RuntimeException("Could not create json payload");
		}
		client.create(INSTANCES_PATH+"/"+instance.getId(), body);
		return instance;
	}

	@Override
	public boolean removeServiceInstance(String serviceInstanceId) {
		return client.delete(INSTANCES_PATH+"/"+serviceInstanceId);
	}

	@Override
	public List<ServiceInstance> listInstances() {
		List<String> children = client.list(INSTANCES_PATH);
		List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
		for(String child : children){
			try {
				instances.add(mapper.readValue(client.get(INSTANCES_PATH+"/"+child), ServiceInstance.class));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return instances;
	}

	@Override
	public ServiceInstanceBinding createInstanceBinding(ServiceInstanceBindingRequest bindingRequest) {
		if(client.exists(BINDINGS_PATH+"/"+bindingRequest.getBindingId())){
			throw new IllegalStateException("Binding Already exists");
		}
		ServiceInstanceBinding sib = new ServiceInstanceBinding();
		sib.setId(bindingRequest.getBindingId());
		sib.setServiceInstanceId(bindingRequest.getInstanceId());
		sib.setAppGuid(bindingRequest.getAppGuid());
		byte[] body = null;
		try {
			body = mapper.writeValueAsBytes(sib);
		} catch (Exception e) {
			throw new RuntimeException("Could not create json payload");
		}
		client.create(BINDINGS_PATH+"/"+sib.getId(),body);
		Map<String, Object> credentials = new HashMap<String, Object>();
		credentials.put("zookeeper", env.getProperty("zookeeper.url"));
		credentials.put("brokers",getBrokers());
		sib.setCredentials(credentials);
		
		return sib;
	}
	
	private String[] getBrokers(){
		ArrayList<String> list = new ArrayList<>();
		List<String> brokerList = client.list("/brokers/ids");
		for(String brokerId : brokerList){
			try {
				Map<String,Object> broker = mapper.readValue(client.get("/brokers/ids/"+brokerId), Map.class);
				String b = broker.get("host")+":"+broker.get("port");
				list.add(b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list.toArray(new String[list.size()]);
	}

	@Override
	public boolean removeBinding(String serviceBindingId) {
		return client.delete(BINDINGS_PATH+"/"+serviceBindingId);
	}

	@Override
	public List<ServiceInstanceBinding> listBindings() {
		return null;
	}
	
	

}
