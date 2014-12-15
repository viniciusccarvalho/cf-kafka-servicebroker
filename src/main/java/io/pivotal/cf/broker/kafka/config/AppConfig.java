package io.pivotal.cf.broker.kafka.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {

	@Bean
	public CuratorFramework curatorClient(Environment env){
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(env.getProperty("zookeeper.url"), retryPolicy);
		client.start();
		return client;
	}
	
	
	
}
