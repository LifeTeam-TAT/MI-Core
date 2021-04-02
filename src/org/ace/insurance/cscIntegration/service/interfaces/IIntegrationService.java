package org.ace.insurance.cscIntegration.service.interfaces;

import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.cscIntegration.Integration;

public interface IIntegrationService {
	public List<Integration> findCSCPolicyByAcePolicyNo(String policyNo, InsuranceType type);
}
