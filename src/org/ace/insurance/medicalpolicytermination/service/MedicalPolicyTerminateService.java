package org.ace.insurance.medicalpolicytermination.service;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.medicalpolicytermination.persistance.interfaces.IMedicalPolicyTerminateDAO;
import org.ace.insurance.medicalpolicytermination.service.interfaces.IMedicalPolicyTerminateService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalPolicyTerminateService")
public class MedicalPolicyTerminateService implements IMedicalPolicyTerminateService {

	@Resource(name = "MedicalPolicyTerminateDAO")
	private IMedicalPolicyTerminateDAO policyTerminationdao;

	@Resource(name = "MedicalPolicyDAO")
	private IMedicalPolicyDAO medicalPolicyDAO;

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void addNewPolicyTermination(MedicalPolicyTerminate
	// policyTermination) {
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
	public void terminatePolicy(MedicalPolicy lifePolicy) {
		try {
			lifePolicy.setPolicyStatus(PolicyStatus.TERMINATE);
			medicalPolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert  MedicalPolicyTerminate", e);
		}
	}

}
