
package org.ace.insurance.life.claim.service;

import java.util.List;

import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifePolicyClaimDAO;
import org.ace.insurance.life.claim.service.interfaces.ILifePolicyClaimService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifePolicyClaimService")
public class LifePolicyClaimService implements ILifePolicyClaimService {

	@Autowired
	private ILifePolicyClaimDAO lifePolicyClaimDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyClaim> findLifePolicyClaimByPolicyNo(String policyNo) throws SystemException {
		try {
			return lifePolicyClaimDAO.findLifepolicyClaimByPolicyNo(policyNo);
		} catch (DAOException de) {
			throw new SystemException(de.getErrorCode(), "Faield to find Life Policy Claim by PolicyNO : " + policyNo, de);
		}
	}

	@Override
	public int findCountByPolicyNo(String policyNo) throws SystemException {
		try {
			return lifePolicyClaimDAO.findCountByPolicyNo(policyNo);
		} catch (DAOException de) {
			throw new SystemException(de.getErrorCode(), "Fail to find claim count by policy no".concat(policyNo), de);
		}
	}

}
