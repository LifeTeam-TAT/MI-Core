package org.ace.insurance.policytermination.service.interfaces;

import org.ace.insurance.life.policy.LifePolicy;

public interface IPolicyTerminationService {

	// public void addNewPolicyTermination(PolicyTermination policyTermination);

	public void terminatePolicy(LifePolicy lifePolicy);

}
