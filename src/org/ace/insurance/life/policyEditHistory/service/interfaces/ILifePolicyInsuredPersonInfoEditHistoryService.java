package org.ace.insurance.life.policyEditHistory.service.interfaces;

import java.util.List;

import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;

public interface ILifePolicyInsuredPersonInfoEditHistoryService {
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson);

	public void updatePolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson);

	public void deletePolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson);

	public PolicyInsuredPersonEditHistory findPolicyInsuredPersonInfoById(String id);

	public List<PolicyInsuredPersonEditHistory> findAllPolicyInsuredPersonInfo();
}
