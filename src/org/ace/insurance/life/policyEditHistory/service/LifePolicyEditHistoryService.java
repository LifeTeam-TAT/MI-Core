package org.ace.insurance.life.policyEditHistory.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.LifePolicyAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policyEditHistory.LifePolicyAttachmentEditHistory;
import org.ace.insurance.life.policyEditHistory.LifePolicyEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonBeneficiariesEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.life.policyEditHistory.persistence.interfaces.ILifePolicyEditHistoryDAO;
import org.ace.insurance.life.policyEditHistory.service.interfaces.ILifePolicyEditHistoryService;
import org.ace.insurance.product.Product;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifePolicyEditHistoryService")
public class LifePolicyEditHistoryService extends BaseService implements ILifePolicyEditHistoryService {

	@Resource(name = "LifePolicyEditHistoryDAO")
	private ILifePolicyEditHistoryDAO lifePolicyDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifePolicy(LifePolicy lifePolicy, PolicyStatus status, PolicyHistoryEntryType entryType) {
		try {
			LifePolicyEditHistory lifePolicyHistory = new LifePolicyEditHistory(lifePolicy);
			lifePolicyHistory.setCustomer(lifePolicy.getCustomer());
			lifePolicyHistory.setOrganization(lifePolicy.getOrganization());
			lifePolicyHistory.setBranch(lifePolicy.getBranch());
			lifePolicyHistory.setPaymentType(lifePolicy.getPaymentType());
			lifePolicyHistory.setAgent(lifePolicy.getAgent());
			lifePolicyHistory.setLifeProposal(lifePolicy.getLifeProposal());
			lifePolicyHistory.setPolicyNo(lifePolicy.getPolicyNo());
			lifePolicyHistory.setEndorsementConfirmDate(new Date());
			lifePolicyHistory.setPolicyStatus(status);
			lifePolicyHistory.setPolicyReferenceNo(lifePolicy.getId());
			lifePolicyHistory.setCommenmanceDate(lifePolicy.getCommenmanceDate());
			if (lifePolicy.getAttachmentList() != null) {
				for (LifePolicyAttachment attachment : lifePolicy.getAttachmentList()) {
					lifePolicyHistory.addLifePolicyAttachment(new LifePolicyAttachmentEditHistory(attachment));
				}
			}
			for (PolicyInsuredPerson insuredPerson : lifePolicy.getPolicyInsuredPersonList()) {
				lifePolicyHistory.addPolicyInsuredPersonInfo(new PolicyInsuredPersonEditHistory(insuredPerson));
			}

			int entryCount = findMaximunEntryCount(lifePolicy.getPolicyNo(), entryType) + 1;
			lifePolicyHistory.setEntryCount(entryCount);
			lifePolicyHistory.setEntryType(entryType);

			if (entryType == PolicyHistoryEntryType.ENDORSEMENT) {
				lifePolicyHistory.setEndorsementConfirmDate(new Date());
			}
			lifePolicyDAO.insert(lifePolicyHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePolicyEditHistory", e);
		}
	}

	public List<LifePolicyEditHistory> activateLifePolicy(String lifeProposalId) {
		List<LifePolicyEditHistory> policyList = null;
		Product product = null;
		String productCode = null;
		String policyNo = null;
		try {
			policyList = lifePolicyDAO.findByProposalId(lifeProposalId);
			for (LifePolicyEditHistory lp : policyList) {
				productCode = lp.getInsuredPersonInfo().get(0).getProduct().getProductGroup().getPolicyNoPrefix();
				if (isPublicLife(product)) {
					policyNo = customIDGenerator.getNextId(SystemConstants.PUBLICLIFE_POLICY_NO, productCode);
				} else if (isGroupLife(product)) {
					policyNo = customIDGenerator.getNextId(SystemConstants.GROUPLIFE_POLICY_NO, productCode);
				}
				lp.setPolicyNo(policyNo);
				lp.setCommenmanceDate(new Date());
				lifePolicyDAO.update(lp);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicyEditHistory", e);
		}
		return policyList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicy(LifePolicyEditHistory lifePolicy) {
		try {
			lifePolicyDAO.update(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePolicyEditHistory", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifePolicy(LifePolicyEditHistory lifePolicy) {
		try {
			lifePolicyDAO.delete(lifePolicy);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePolicyEditHistory", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyEditHistory findLifePolicyById(String id) {
		LifePolicyEditHistory result = null;
		try {
			result = lifePolicyDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyByProposalNo(String proposalId) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByProposalId(proposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Policy by ProposalId : " + proposalId, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyByReceiptNo(String receiptNo) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByReceiptNo(receiptNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyEditHistory by ReceiptNo : " + receiptNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findAllLifePolicy() {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyEditHistory)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findAllActiveLifePolicy() {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findAllActiveLifePolicy();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of ActiveLifePolicy)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findByDate(Date startDate, Date endDate) {
		List<LifePolicyEditHistory> result = null;
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
			throw new SystemException(e.getErrorCode(), "Faield to increase LifePolicyEditHistory print count. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId) {
		try {
			lifePolicyDAO.updateCommenmanceDate(date, lifeProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update LifePolicyEditHistory commenmance date. " + e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByEnquiryCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePolicyEditHistory (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	/**
	 * @see org.ace.insurance.life.policy.service.interfaces.ILifePolicyService#findLifePoliciesRequiredForCoinsurance()
	 */
	public List<LifePolicyEditHistory> findLifePoliciesRequiredForCoinsurance() {
		List<LifePolicyEditHistory> ret = null;
		try {
			ret = lifePolicyDAO.findLifePoliciesRequiredForCoinsurance();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to retrieve life policies required for the Co-insurance", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyByPolicyNo(String policyNo) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyEditHistory by ReceiptNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonEditHistory findInsuredPersonByCodeNo(String codeNo, String policyId) {
		PolicyInsuredPersonEditHistory result = null;
		try {
			result = lifePolicyDAO.findInsuredPersonByCodeNo(codeNo, policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find InsuredPerson By CodeNo : " + codeNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonBeneficiariesEditHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonId) {
		PolicyInsuredPersonBeneficiariesEditHistory result = null;
		try {
			result = lifePolicyDAO.findBeneficiaryByCodeNo(codeNo, insuPersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Beneficiary By Code No : " + codeNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyEditHistoryByPolicyNo(String policyNo) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyEditHistory by ReceiptNo : " + policyNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findLifePolicyEditHistoryByProposalNo(String proposalId) {
		List<LifePolicyEditHistory> result = null;
		try {
			result = lifePolicyDAO.findByProposalId(proposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePolicyEditHistory by ProposalNo : " + proposalId, e);
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
}
