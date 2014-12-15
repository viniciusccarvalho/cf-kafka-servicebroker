package io.pivotal.cf.kafka;

import io.pivotal.cf.broker.model.ServiceInstance;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CuratorTest {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void writeServiceDefinition() throws Exception {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
		client.start();
		
		ServiceInstance instance = new ServiceInstance("1", "1", "1", "uuid-1", "uuid-1", "http://google.com");
		client.create().creatingParentsIfNeeded().forPath("/sb/instances/"+instance.getId(), mapper.writeValueAsBytes(instance));
	}
	
}
