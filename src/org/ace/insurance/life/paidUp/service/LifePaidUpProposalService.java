package org.ace.insurance.life.paidUp.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.PaidUpClaimStatus;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.persistence.interfaces.ILifePaidUpProposalDAO;
import org.ace.insurance.life.paidUp.service.interfaces.ILifePaidUpProposalService;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.joda.time.Period;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("LifePaidUpProposalService")
public class LifePaidUpProposalService extends BaseService implements ILifePaidUpProposalService {
	@Resource(name = "LifePaidUpProposalDAO")
	private ILifePaidUpProposalDAO lifePaidUpProposalDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "ClaimProductService")
	private IClaimProductService claimProductService;

	@Resource(name = "ClaimAcceptedInfoService")
	private IClaimAcceptedInfoService claimAcceptedInfoService;
	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public LifePaidUpProposal addNewLifePaidUpProposal(LifePaidUpProposal proposal, WorkFlowDTO workFlowDTO) {
		try {
			String proposalNo = null;
			proposalNo = customIDGenerator.getNextId(SystemConstants.LIFE_PAIDUP_PROPOSAL_NO, null);
			proposal.setProposalNo(proposalNo);
			PolicyInsuredPerson insuredPerson = proposal.getLifePolicy().getInsuredPersonInfo().get(0);
			double sumInsured = insuredPerson.getSumInsured();
			int policyPeriod = DateUtils.yearsBetween(proposal.getLifePolicy().getActivedPolicyStartDate(), proposal.getLifePolicy().getActivedPolicyEndDate(), false, false);
			Period period = Utils.getPeriod(insuredPerson.getLifePolicy().getActivedPolicyStartDate(), insuredPerson.getLifePolicy().getCoverageDate(), false, true);
			int paymentYear = period.getYears();
			int paymentMonth = period.getMonths();
			if (paymentMonth >= 6) {
				paymentYear += 1;
			}
			double paidUpAmount = (sumInsured * paymentYear) / policyPeriod;
			proposal.setPaidUpAmount(paidUpAmount);
			proposal.setRealPaidUpAmount(paidUpAmount - proposal.getReqAmount());
			proposal.setActivedPolicyStartDate(insuredPerson.getLifePolicy().getActivedPolicyStartDate());
			proposal.setActivedPolicyEndDate(insuredPerson.getLifePolicy().getActivedPolicyEndDate());
			proposal = lifePaidUpProposalDAO.insert(proposal);
			workFlowDTO.setReferenceNo(proposal.getId());
			workFlowDTOService.addNewWorkFlow(workFlowDTO);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePaidUpProposal", e);
		}

		return proposal;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public LifePaidUpProposal updateLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workFlowDTO) {
		try {
			PolicyInsuredPerson insuredPerson = lifePaidUpProposal.getLifePolicy().getInsuredPersonInfo().get(0);
			double sumInsured = insuredPerson.getSumInsured();
			int policyPeriod = lifePaidUpProposal.getLifePolicy().getPeriodMonth();
			Period period = Utils.getPeriod(insuredPerson.getLifePolicy().getActivedPolicyStartDate(), insuredPerson.getLifePolicy().getActivedPolicyEndDate(), false, true);
			int paymentYear = period.getYears();
			int paymentMonth = period.getMonths();
			if (paymentMonth >= 6) {
				paymentYear += 1;
			}
			double paidUpAmount = (sumInsured * paymentYear) / policyPeriod;
			lifePaidUpProposal.setPaidUpAmount(paidUpAmount);
			lifePaidUpProposal.setApproved(false);
			lifePaidUpProposalDAO.update(lifePaidUpProposal);
			workFlowDTO.setReferenceNo(lifePaidUpProposal.getId());
			workFlowDTOService.updateWorkFlow(workFlowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifePaidUpProposal", e);
		}
		return lifePaidUpProposal;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal) {
		try {
			lifePaidUpProposalDAO.update(lifePaidUpProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePaidUpProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateCompleteStatus(String policyNo) {
		try {
			lifePaidUpProposalDAO.update(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifePaidUpProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal) {
		try {
			lifePaidUpProposalDAO.delete(lifePaidUpProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifePaidUpProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<LifePaidUpProposal> findAllLifePaidUpProposal() {
		List<LifePaidUpProposal> result = null;
		try {
			result = lifePaidUpProposalDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifePaidUpProposal)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public LifePaidUpProposal findLifePaidUpProposalById(String id) {
		LifePaidUpProposal result = null;
		try {
			result = lifePaidUpProposalDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifePaidUpProposal (ID : " + id + ")", e);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal findByProposalNo(String proposalNo) {
		LifePaidUpProposal result = null;
		try {
			result = lifePaidUpProposalDAO.findByProposalNo(proposalNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a LifePaidUpProposal", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePaidUpProposal findByPolicyNo(String policyNo) {
		LifePaidUpProposal result = null;
		try {
			result = lifePaidUpProposalDAO.findByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a LifePaidUpProposal", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifePaidUpProposalDAO.update(lifePaidUpProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to approve a LifePaidUpProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			workflowDTO.setReferenceNo(lifePaidUpProposal.getId());
			workFlowDTOService.updateWorkFlow(workflowDTO);
			if (claimAcceptedInfo != null) {
				claimAcceptedInfoService.addNewClaimAcceptedInfo(claimAcceptedInfo);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to inform Life PaidUp", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void confirmLifePaidUpProposal(LifePaidUpProposal paidUpProposal, PolicyStatus status) {
		try {
			paidUpProposal.setClaimStatus(PaidUpClaimStatus.CLAIM_APPLIED);
			lifePaidUpProposalDAO.update(paidUpProposal);
			lifePolicyService.updatePolicyStatusById(paidUpProposal.getLifePolicy().getId(), status);

			workFlowDTOService.deleteWorkFlowByRefNo(paidUpProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm LifePaidUpProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectLifePaidUpProposal(LifePaidUpProposal paidUpProposal) {
		try {
			paidUpProposal.setClaimStatus(PaidUpClaimStatus.CLAIM_REJECTED);
			lifePaidUpProposalDAO.update(paidUpProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to reject a LifePaidUpProposal", e);
		}
	}
}
