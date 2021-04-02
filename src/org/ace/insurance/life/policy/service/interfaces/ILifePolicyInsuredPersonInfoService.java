package org.ace.insurance.life.policy.service.interfaces;

import java.util.List;

import org.ace.insurance.life.policy.PolicyInsuredPerson;

public interface ILifePolicyInsuredPersonInfoService {
	public void addNewPolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson);
	public void updatePolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson);
	public void deletePolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson);
	public PolicyInsuredPerson findPolicyInsuredPersonInfoById(String id);
	public List<PolicyInsuredPerson> findAllPolicyInsuredPersonInfo();
}
