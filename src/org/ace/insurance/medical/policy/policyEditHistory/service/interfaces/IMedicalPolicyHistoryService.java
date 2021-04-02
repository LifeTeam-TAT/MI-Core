package org.ace.insurance.medical.policy.policyEditHistory.service.interfaces;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.medical.policy.MedicalPolicy;

public interface IMedicalPolicyHistoryService {
	
	public void addNewMedicalPolicyHistory(MedicalPolicy medicalPolicy, PolicyStatus status, PolicyHistoryEntryType entryType);


}
