package io.pivotal.cf.broker.kafka.controllers;

import io.pivotal.cf.broker.kafka.services.CatalogService;
import io.pivotal.cf.broker.model.Catalog;
import io.pivotal.cf.broker.model.ServiceDefinition;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/v2/catalog")
public class CatalogController {
	
	private Logger logger = LoggerFactory.getLogger(CatalogController.class);
	
	@Autowired
	private CatalogService service;
	
	@ResponseBody
	@RequestMapping(produces="application/json", method=RequestMethod.GET)
	public Catalog list(HttpServletRequest request){
		
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String key = headerNames.nextElement();
			logger.info("{} : {}", key, request.getHeader(key));
		}
		List<ServiceDefinition> services = service.listServices();
		Catalog catalog = new Catalog(services);
		return catalog;
	}
	
}
