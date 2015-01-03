package io.pivotal.cf.kafka;

import io.pivotal.cf.broker.model.ServiceInstance;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CuratorTest {

	private ObjectMapper mapper = new ObjectMapper();
	private TestingServer zkServer;
	private CuratorFramework cli;
	
	@Before
	public void startZookeeper() throws Exception {
		zkServer = new TestingServer(2181);
	    cli = CuratorFrameworkFactory.newClient(zkServer.getConnectString(), new RetryOneTime(2000));
	}
	
	@Test
	public void writeServiceDefinition() throws Exception {
		cli.start();
		
		ServiceInstance instance = new ServiceInstance("1", "1", "1", "uuid-1", "uuid-1", "http://google.com");
		cli.create().creatingParentsIfNeeded().forPath("/sb/instances/"+instance.getId(), mapper.writeValueAsBytes(instance));
	}
	
}
