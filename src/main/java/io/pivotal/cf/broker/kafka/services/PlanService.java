package io.pivotal.cf.broker.kafka.services;

import io.pivotal.cf.broker.model.Plan;

public interface PlanService {
	public Plan create(Plan plan);
	public boolean delete(String planId);
}
