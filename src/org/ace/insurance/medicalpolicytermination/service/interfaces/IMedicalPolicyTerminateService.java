package org.ace.insurance.medicalpolicytermination.service.interfaces;

import org.ace.insurance.medical.policy.MedicalPolicy;

public interface IMedicalPolicyTerminateService {
	// public void addNewPolicyTermination(MedicalPolicyTerminate
	// policyTermination);

	public void terminatePolicy(MedicalPolicy lifePolicy);
}
