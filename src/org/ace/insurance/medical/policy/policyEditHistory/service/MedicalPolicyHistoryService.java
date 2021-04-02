package org.ace.insurance.medical.policy.policyEditHistory.service;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.policyEditHistory.MedicalPolicyEditHistory;
import org.ace.insurance.medical.policy.policyEditHistory.MedicalPolicyInuredPersonEditHistory;
import org.ace.insurance.medical.policy.policyEditHistory.persistance.interfaces.IMedicalPolicyEditHistoryDAO;
import org.ace.insurance.medical.policy.policyEditHistory.service.interfaces.IMedicalPolicyHistoryService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalPolicyHistoryService")
public class MedicalPolicyHistoryService implements IMedicalPolicyHistoryService {

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "MedicalPolicyEditHistoryDAO")
	private IMedicalPolicyEditHistoryDAO medicalPolicyEditHistoryDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewMedicalPolicyHistory(MedicalPolicy medicalPolicy, PolicyStatus status, PolicyHistoryEntryType entryType) {
		try {
			MedicalPolicyEditHistory medicalPolicyHistory = new MedicalPolicyEditHistory(medicalPolicy);
			medicalPolicyHistory.setActivedPolicyStartDate(medicalPolicy.getActivedPolicyStartDate());
			medicalPolicyHistory.setActivedPolicyEndDate(medicalPolicy.getActivedPolicyEndDate());
			medicalPolicyHistory.setCustomer(medicalPolicy.getCustomer());
			medicalPolicyHistory.setOrganization(medicalPolicy.getOrganization());
			medicalPolicyHistory.setBranch(medicalPolicy.getBranch());
			medicalPolicyHistory.setPaymentType(medicalPolicy.getPaymentType());
			medicalPolicyHistory.setAgent(medicalPolicy.getAgent());
			medicalPolicyHistory.setMedicalProposal(medicalPolicy.getMedicalProposal());
			medicalPolicyHistory.setPolicyNo(medicalPolicy.getPolicyNo());
			medicalPolicyHistory.setPolicyStatus(status);
			medicalPolicyHistory.setReferencePolicyNo(medicalPolicy.getId());
			medicalPolicyHistory.setCommenmanceDate(medicalPolicy.getCommenmanceDate());
			medicalPolicyHistory.setSalesPoints(medicalPolicy.getSalesPoints());
			for (MedicalPolicyInsuredPerson insuredPerson : medicalPolicy.getPolicyInsuredPersonList()) {
				medicalPolicyHistory.addPolicyInsuredPersonInfo(new MedicalPolicyInuredPersonEditHistory(insuredPerson));
			}

			medicalPolicyEditHistoryDAO.insert(medicalPolicyHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyEditHistory", e);
		}

	}
}
