package org.ace.insurance.life.policyHistory.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.LifePolicyAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policyHistory.LifePolicyAttachmentHistory;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.life.policyHistory.persistence.interfaces.ILifePolicyHistoryDAO;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifePolicyHistoryService")
public class LifePolicyHistoryService extends BaseService implements ILifePolicyHistoryService {

	@Resource(name = "LifePolicyHistoryDAO")
	private ILifePolicyHistoryDAO lifePolicyDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifePolicy(LifePolicy lifePolicy, PolicyStatus status, PolicyHistoryEntryType entryType) {
		try {
			LifePolicyHistory lifePolicyHistory = new LifePolicyHistory(lifePolicy);
			lifePolicyHistory.setCustomer(lifePolicy.getCustomer());
			lifePolicyHistory.setOrganization(lifePolicy.getOrganization());
			lifePolicyHistory.setBranch(lifePolicy.getBranch());
			lifePolicyHistory.setSaleBank(lifePolicy.getSaleBank());
			lifePolicyHistory.setSaleChannelType(lifePolicy.getSaleChannelType());
			lifePolicyHistory.setPaymentType(lifePolicy.getPaymentType());
			lifePolicyHistory.setAgent(lifePolicy.getAgent());
			lifePolicyHistory.setLifeProposal(lifePolicy.getLifeProposal());
			lifePolicyHistory.setPolicyNo(lifePolicy.getPolicyNo());
			lifePolicyHistory.setEndorsementConfirmDate(new Date());
			lifePolicyHistory.setPolicyStatus(status);
			lifePolicyHistory.setPolicyReferenceNo(lifePolicy.getId());
			lifePolicyHistory.setPeriodMonth(lifePolicy.getPeriodMonth());
			lifePolicyHistory.setCommenmanceDate(lifePolicy.getCommenmanceDate());
			if (lifePolicy.getAttachmentList() != null) {
				for (LifePolicyAttachment attachment : lifePolicy.getAttachmentList()) {
					lifePolicyHistory.addLifePolicyAttachment(new LifePolicyAttachmentHistory(attachment));
				}
			}
			for (PolicyInsuredPerson insuredPerson : lifePolicy.getPolicyInsuredPersonList()) {
				lifePolicyHistory.addPolicyInsuredPersonInfo(new PolicyInsuredPersonHistory(insuredPerson));
			}

			int entryCount = findMaximunEntryCount(lifePolicy.getPolicyNo(), entryType) + 1;
			lifePolicyHistory.setEntryCount(entryCount);
			lifePolicyHistory.setEntryType(entryType);

			if (entryType == PolicyHistoryEntryType.ENDORSEMENT) {
				lifePolicyHistory.setEndorsementConfirmDate(new Date());
			}
			lifePolicyDAO.insert(lifePolicyHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyHistory", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicy(LifePolicyHistory lifePolicy) {
		try {
			lifePolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicyHistory", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicy(LifePolicyHistory lifePolicy) {
		try {
			lifePolicyDAO.delete(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePolicyHistory", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyHistory findLifePolicyById(String id) {
		LifePolicyHistory result = null;
		try {
			result = lifePolicyDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findLifePolicyByReceiptNo(String receiptNo) {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findByReceiptNo(receiptNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyHistory by ReceiptNo : " + receiptNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findAllLifePolicy() {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyHistory)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findAllActiveLifePolicy() {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findAllActiveLifePolicy();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ActiveLifePolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findByDate(Date startDate, Date endDate) {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findByDate(startDate, endDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void increaseLifePolicyPrintCount(String lifeProposalId) {
		try {
			lifePolicyDAO.increasePrintCount(lifeProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to increase LifePolicyHistory print count. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId) {
		try {
			lifePolicyDAO.updateCommenmanceDate(date, lifeProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update LifePolicyHistory commenmance date. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria) {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findByEnquiryCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicyHistory (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	/**
	 * @see org.ace.insurance.life.policy.service.interfaces.ILifePolicyService#findLifePoliciesRequiredForCoinsurance()
	 */
	public List<LifePolicyHistory> findLifePoliciesRequiredForCoinsurance() {
		List<LifePolicyHistory> ret = null;
		try {
			ret = lifePolicyDAO.findLifePoliciesRequiredForCoinsurance();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve life policies required for the Co-insurance", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findLifePolicyByPolicyNo(String policyNo) {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyHistory by ReceiptNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonHistory findInsuredPersonByCodeNo(String codeNo, String policyId) {
		PolicyInsuredPersonHistory result = null;
		try {
			result = lifePolicyDAO.findInsuredPersonByCodeNo(codeNo, policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find InsuredPerson By CodeNo : " + codeNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonBeneficiariesHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonId) {
		PolicyInsuredPersonBeneficiariesHistory result = null;
		try {
			result = lifePolicyDAO.findBeneficiaryByCodeNo(codeNo, insuPersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Beneficiary By Code No : " + codeNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyHistory> findLifePolicyHistoryByPolicyNo(String policyNo) {
		List<LifePolicyHistory> result = null;
		try {
			result = lifePolicyDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyHistory by ReceiptNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType) {
		int result = 0;
		try {
			result = lifePolicyDAO.findMaximunEntryCount(policyNo, entryType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Maxumun EntryCount (policyNo : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyHistory findByPolicyReferenceNo(String policyID) {
		LifePolicyHistory result = null;
		try {
			result = lifePolicyDAO.findByPolicyReferenceNo(policyID);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyHistory by ReceiptNo : " + policyID, e);
		}
		return result;
	}

}
