package org.ace.insurance.life.policyHistory.service.interfaces;

import java.util.List;

import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;

public interface ILifePolicyInsuredPersonInfoHistoryService {
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson);
	public void updatePolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson);
	public void deletePolicyInsuredPersonInfo(PolicyInsuredPersonHistory policyInsuredPerson);
	public PolicyInsuredPersonHistory findPolicyInsuredPersonInfoById(String id);
	public List<PolicyInsuredPersonHistory> findAllPolicyInsuredPersonInfo();
}
