package org.ace.insurance.policytermination.service;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.policytermination.persistance.interfaces.IPolicyTerminationDAO;
import org.ace.insurance.policytermination.service.interfaces.IPolicyTerminationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "PolicyTerminationService")
public class PolicyTerminationService implements IPolicyTerminationService {

	@Resource(name = "PolicyTerminationDAO")
	private IPolicyTerminationDAO policyTerminationdao;

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void addNewPolicyTermination(PolicyTermination policyTermination)
	// {
	// try {
	// policyTerminationdao.insert(policyTermination);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to insert
	// policyTermination", e);
	// }
	//
	// }

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void terminatePolicy(LifePolicy lifePolicy) {
		try {
			// PolicyTermination policyTermination = new
			// PolicyTermination(lifePolicy);
			// policyTerminationdao.insert(policyTermination);
			// lifePolicyDAO.delete(lifePolicy);
			lifePolicy.setPolicyStatus(PolicyStatus.TERMINATE);
			lifePolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert  policyTermination", e);
		}
	}

}
