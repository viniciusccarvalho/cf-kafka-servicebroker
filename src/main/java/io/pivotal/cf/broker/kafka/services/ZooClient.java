package io.pivotal.cf.broker.kafka.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ZooClient {

	@Autowired
	private CuratorFramework client;
	
	@Autowired
	private ObjectMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(ZooClient.class);
	
	public void create(String path, byte[] contents){
		try {
			client.create().creatingParentsIfNeeded().forPath(path,contents);
		} catch (Exception e) {
			logger.error("Could not create data", e);
			throw new IllegalStateException(e);
		}
	}
	
	public List<String> list(String path){
		List<String> children = new ArrayList<String>();
		try {
			children = client.getChildren().forPath(path);
		} catch (Exception e) {
			logger.error("Could not fetch children", e);
			throw new IllegalStateException(e);
		}
		return children;
	}
	
	
	public byte[] get(String path){
		byte[] data = null;
		try {
			data = client.getData().forPath(path);
		} catch (Exception e) {
			logger.error("Could not get data", e);
			throw new IllegalStateException(e);
		}
		return data;
	}
	
	public boolean delete(String path){
		boolean result = false;
		if(!exists(path)){
			return false;
		}
		try {
			client.delete().deletingChildrenIfNeeded().forPath(path);
			result = true;
		} catch (Exception e) {
			logger.error("Could not delete data", e);
			throw new IllegalStateException(e);
		}
		return result;
	}
	
	public boolean exists(String path){
		try {
			return client.checkExists().forPath(path) != null;
		} catch (Exception e) {
			logger.error("Could not check if data exits", e);
			throw new IllegalStateException(e);
		}
	}
	
}
