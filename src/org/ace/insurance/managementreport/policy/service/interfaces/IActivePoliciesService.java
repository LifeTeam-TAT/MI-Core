package org.ace.insurance.managementreport.policy.service.interfaces;

import org.ace.insurance.managementreport.policy.ActivePolicies;

public interface IActivePoliciesService {
	public ActivePolicies findActivePoliciesByProducts();
	public ActivePolicies findTotalSumInsuredByProducts();
	public ActivePolicies findTotalPremiumByProducts();
	public ActivePolicies findLifePolicyByTimeLine(int year);
	
	//To FIXME by THK
	public ActivePolicies findFirePolicyByTimeLine(int year);
	public ActivePolicies findMotorPolicyByTimeLine(int year);
}
