package org.ace.insurance.life.lifePolicySummary.Service.Interfaces;

import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;

public interface ILifePolicySummaryService {

	public void addNewLifePolicySummary(LifePolicySummary lifePolicySummary);
	public void updateLifePolicySummary(LifePolicySummary lifePolicySummary);
	public LifePolicySummary findLifePolicySummaryById(String id);
	public LifePolicySummary findLifePolicyByPolicyNo(String policyNo);
	public void deleteLifePolicySummary(LifePolicySummary lifePolicySummary) ;
	
}
