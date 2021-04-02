package org.ace.insurance.life.claim.service.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.java.component.SystemException;

public interface ILifePolicyClaimService {

	List<LifePolicyClaim> findLifePolicyClaimByPolicyNo(String policyNo) throws SystemException;

	int findCountByPolicyNo(String policyNo) throws SystemException;

}