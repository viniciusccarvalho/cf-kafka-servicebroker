package io.pivotal.cf.broker.kafka.services;

import io.pivotal.cf.broker.model.CreateServiceInstanceRequest;
import io.pivotal.cf.broker.model.ServiceInstance;
import io.pivotal.cf.broker.model.ServiceInstanceBinding;
import io.pivotal.cf.broker.model.ServiceInstanceBindingRequest;

import java.util.List;

public interface ServiceManagement {
	
	public ServiceInstance createInstance(CreateServiceInstanceRequest serviceRequest);
	
	public boolean removeServiceInstance(String serviceInstanceId);
	
	public List<ServiceInstance> listInstances();
	
	public ServiceInstanceBinding createInstanceBinding(ServiceInstanceBindingRequest bindingRequest);
	
	public boolean removeBinding(String serviceBindingId);
	
	public List<ServiceInstanceBinding> listBindings();
	
}
