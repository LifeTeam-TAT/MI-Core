package org.ace.insurance.life.claim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimAmount;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.LifeClaimSuccessor;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimBeneficiaryDAO;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimDAO;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyLog.LifePolicyClaimLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.insurance.life.policyLog.service.interfaces.ILifePolicyTimeLineLogService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Service(value = "LifeClaimService")
public class LifeClaimService implements ILifeClaimService {

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "LifeClaimDAO")
	private ILifeClaimDAO claimDAO;

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "LifeClaimBeneficiaryDAO")
	private ILifeClaimBeneficiaryDAO claimBeneficiaryDAO;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;

	@Resource(name = "ClaimAcceptedInfoService")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	@Resource(name = "LifePolicyTimeLineLogService")
	private ILifePolicyTimeLineLogService lifePolicyTimeLineLogService;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	private void calculateClaimAmount(LifeClaim lifeClaim) {
		double claimAmount = 0.0;
		double totalClaimAmount = 0.0;
		double deficitPremium = 0.0;
		double totalDeficitPremium = 0.0;
		boolean hasSuccessor = false;
		double sumInsured = lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getSumInsured();
		double remainingamount = calculateRemainingPremium(lifeClaim);
		double netSumInsured = sumInsured - remainingamount;
		for (LifeClaimInsuredPersonBeneficiary beneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
			beneficiary.setClaimStatus(ClaimStatus.DEATH_CLAIM);
			lifePolicyDAO.updateBeneficiaryClaimStatusById(beneficiary.getPolicyInsuredPersonBeneficiaries().getId(), ClaimStatus.DEATH_CLAIM);
			claimAmount = Utils.getPercentOf(beneficiary.getPolicyInsuredPersonBeneficiaries().getPercentage(), netSumInsured);
			deficitPremium = Utils.getPercentOf(beneficiary.getPolicyInsuredPersonBeneficiaries().getPercentage(), remainingamount);
			beneficiary.setClaimAmount(claimAmount);
			beneficiary.setDeficitPremium(deficitPremium);
			if (beneficiary.getClaimSuccessor() != null) {
				hasSuccessor = true;
				totalClaimAmount = totalClaimAmount + claimAmount;
				totalDeficitPremium = totalDeficitPremium + deficitPremium;
			}
		}
		if (hasSuccessor) {
			LifeClaimSuccessor successor = lifeClaim.getSuccessor();
			successor.setClaimAmount(totalClaimAmount);
			successor.setDeficitPremium(totalDeficitPremium);
		}
	}

	public Double calculateRemainingPremium(LifeClaim lifeClaim) {
		int remainingMonth = 0;
		DateTime commencementDate = new DateTime(lifeClaim.getLifePolicy().getCommenmanceDate());
		DateTime activePolicyEndate = new DateTime(lifeClaim.getLifePolicy().getActivedPolicyEndDate());
		int passedMonths = Months.monthsBetween(commencementDate, activePolicyEndate).getMonths();
		int paymentType = lifeClaim.getLifePolicy().getPaymentType().getMonth();
		if (paymentType > 0) {
			if (passedMonths % paymentType != 0) {
				passedMonths = passedMonths + 1;
			}
		} else {
			if (passedMonths % 12 != 0) {
				passedMonths = passedMonths + 1;
			}
		}
		Double onemonthpremium = lifeClaim.getLifePolicy().getPremium() / 12;
		int remainMonthforoneyear = passedMonths % 12;
		if (remainMonthforoneyear != 0) {
			remainingMonth = 12 - remainMonthforoneyear;
		}
		Double remainingPremiumForOneYear = onemonthpremium * remainingMonth;
		return remainingPremiumForOneYear;
	}

	private void calculateClaimAmountAfterConfirm(LifeClaim lifeClaim, PaymentDTO payment) {
		double claimAmount = 0.0;
		double totalClaimAmount = 0.0;
		boolean hasSuccessor = false;
		double sumInsured = lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getSumInsured();
		double remainingamount = calculateRemainingPremium(lifeClaim);
		double netSumInsured = sumInsured - remainingamount;
		double serviceCharges = payment.getServicesCharges() / lifeClaim.getClaimInsuredPersonBeneficiaryList().size();
		for (LifeClaimInsuredPersonBeneficiary beneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
			claimAmount = Utils.getPercentOf(beneficiary.getPolicyInsuredPersonBeneficiaries().getPercentage(), netSumInsured);
			claimAmount = claimAmount - serviceCharges;
			beneficiary.setClaimAmount(claimAmount);
			if (beneficiary.getClaimSuccessor() != null) {
				hasSuccessor = true;
				totalClaimAmount = totalClaimAmount + claimAmount;
			}
		}
		if (hasSuccessor) {
			LifeClaimSuccessor successor = lifeClaim.getSuccessor();
			successor.setClaimAmount(totalClaimAmount);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimBeneficiary findLifeClaimBeneficaryByRefundNo(String refundNo) {
		LifeClaimBeneficiary claimBeneficiary = null;
		try {
			claimBeneficiary = claimBeneficiaryDAO.findByRefundNo(refundNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimBeneficiary by RefundNo : " + refundNo, e);
		}
		return claimBeneficiary;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaim findLifeClaimByRequestId(String claimRequestId) {
		LifeClaim lifeClaim = null;
		try {
			lifeClaim = claimDAO.findByClaimRequestId(claimRequestId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Claim by ClaimRequestId : " + claimRequestId, e);
		}
		return lifeClaim;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimInsuredPerson findLifeClaimInsuredPersonById(String policyInsuredPersonId) {
		LifeClaimInsuredPerson lifeClaimInsuredPerson = null;
		try {
			lifeClaimInsuredPerson = claimDAO.findLifeClaimInsuredPersonByPolicyInsuredPersonId(policyInsuredPersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimInsuredPerson by PolicyInsuredPerson Id : " + policyInsuredPersonId, e);
		}
		return lifeClaimInsuredPerson;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO) {
		try {
			String claimRequestId = customIDGenerator.getNextId(SystemConstants.LIFE_CLAIM_NO, null);
			lifeClaim.setClaimRequestId(claimRequestId);
			if (lifeClaim.isDeathClaim()) {
				calculateClaimAmount(lifeClaim);
			} else {
				lifeClaim.getClaimInsuredPerson().setClaimStatus(ClaimStatus.DISABILITY_CLAIM);
				lifePolicyDAO.updateInsuredPersonClaimStatusById(lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getId(), ClaimStatus.DISABILITY_CLAIM);
			}
			claimDAO.insert(lifeClaim);
			workflowDTO.setReferenceNo(lifeClaim.getClaimRequestId());
			workFlowDTOService.addNewWorkFlow(workflowDTO);

			/** Entry LifePolicyClaimLog **/
			LifePolicyTimeLineLog lifePolicyTimeLineLog = lifePolicyTimeLineLogService.findLifePolicyTimeLineLogByPolicyNo(lifeClaim.getLifePolicy().getPolicyNo());
			LifePolicyClaimLog lifePolicyClaimLog = new LifePolicyClaimLog(lifeClaim.getSubmittedDate(), null, lifeClaim, lifePolicyTimeLineLog);
			lifePolicyTimeLineLogService.addLifePolicyClaimLog(lifePolicyClaimLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a newClaim", e);
		}
	}

	public LifeClaimAmount calculateClaimAmount(PolicyInsuredPerson policyInsuredPerson) {
		LifeClaimAmount claimAmount = new LifeClaimAmount();
		int totalSumInsured = 0;
		totalSumInsured = new Double(policyInsuredPerson.getSumInsured()).intValue() + new Double(policyInsuredPerson.getAddOnSumInsure()).intValue();
		claimAmount.setClaimAmount(totalSumInsured);
		claimAmount.setNetAmount(totalSumInsured);
		claimAmount.setPolicyInsuredPerson(policyInsuredPerson);
		return claimAmount;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workflowDTO);
			if (lifeClaim.isDeathClaim()) {
				calculateClaimAmount(lifeClaim);
			}
			claimDAO.update(lifeClaim);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to approve Life Claim", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo) {
		try {
			workflowDTO.setReferenceNo(lifeClaim.getClaimRequestId());
			workFlowDTOService.updateWorkFlow(workflowDTO);
			if (claimAcceptedInfo != null) {
				claimAcceptedInfoService.addNewClaimAcceptedInfo(claimAcceptedInfo);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to inform Life Claim", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> confirmLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO, PaymentDTO paymentDTO) {
		List<Payment> paymentList = new ArrayList<Payment>();
		String refundNo = null;
		boolean hasSuccessor = true;
		double premium = 0.0;
		try {
			Payment payment = null;
			double serviceCharges = paymentDTO.getServicesCharges();
			LifeClaimBeneficiary lifeClaimBeneficiary = null;
			if (lifeClaim.isDeathClaim()) {
				// Calculate Service Charges for each beneficiary
				if (serviceCharges > 0) {
					serviceCharges = Utils.divide(serviceCharges, lifeClaim.getClaimInsuredPersonBeneficiaryList().size());
				}
				for (LifeClaimBeneficiary beneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
					LifeClaimInsuredPersonBeneficiary insuredPersonBeneficiary = beneficiary.castClaimInsuredPersonBeneficiary();
					if (insuredPersonBeneficiary.getClaimSuccessor() == null) {
						refundNo = customIDGenerator.getNextId(SystemConstants.LIFE_CLAIM_REFUND_NO, null);
						beneficiary.setRefundNo(refundNo);
						lifeClaimBeneficiary = beneficiary;
						workflowDTO.setReferenceNo(refundNo);
						workFlowDTOService.addNewWorkFlow(workflowDTO);
						hasSuccessor = false;

						// Add Payment for each beneficiary
						payment = new Payment();

						payment.setConfirmDate(new Date());
						payment.setBank(paymentDTO.getBank());
						payment.setBankAccountNo(paymentDTO.getBankAccountNo());
						payment.setChequeNo(paymentDTO.getChequeNo());
						payment.setPaymentChannel(paymentDTO.getPaymentChannel());
						payment.setServicesCharges(serviceCharges);
						payment.setClaimAmount(beneficiary.getClaimAmount());
						payment.setReferenceNo(beneficiary.getId());
						payment.setReferenceType(PolicyReferenceType.LIFE_CLAIM);
						paymentList.add(payment);
						premium = beneficiary.castClaimInsuredPersonBeneficiary().getDeficitPremium();
					}
				}
				if (hasSuccessor) {
					LifeClaimBeneficiary successor = lifeClaim.getSuccessor();
					refundNo = customIDGenerator.getNextId(SystemConstants.LIFE_CLAIM_REFUND_NO, null);
					successor.setRefundNo(refundNo);
					lifeClaimBeneficiary = successor;
					workflowDTO.setReferenceNo(refundNo);
					workFlowDTOService.addNewWorkFlow(workflowDTO);

					payment = new Payment();
					payment.setConfirmDate(new Date());
					payment.setBank(paymentDTO.getBank());
					payment.setBankAccountNo(paymentDTO.getBankAccountNo());
					payment.setChequeNo(paymentDTO.getChequeNo());
					payment.setPaymentChannel(paymentDTO.getPaymentChannel());
					payment.setServicesCharges(paymentDTO.getServicesCharges());
					payment.setClaimAmount(successor.getClaimAmount());
					payment.setReferenceNo(successor.getId());
					payment.setReferenceType(PolicyReferenceType.LIFE_CLAIM);
					paymentList.add(payment);
					premium = successor.castLifeClaimSuccessor().getDeficitPremium();
				}
				// Re-Calculate ClaimAmount After deduct Service Charges
				calculateClaimAmountAfterConfirm(lifeClaim, paymentDTO);
			} else {
				refundNo = customIDGenerator.getNextId(SystemConstants.LIFE_CLAIM_REFUND_NO, null);
				lifeClaim.getClaimInsuredPerson().setRefundNo(refundNo);
				lifeClaimBeneficiary = lifeClaim.getClaimInsuredPerson();
				workflowDTO.setReferenceNo(refundNo);
				workFlowDTOService.addNewWorkFlow(workflowDTO);

				payment = new Payment();
				payment.setConfirmDate(new Date());
				payment.setBank(paymentDTO.getBank());
				payment.setBankAccountNo(paymentDTO.getBankAccountNo());
				payment.setChequeNo(paymentDTO.getChequeNo());
				payment.setPaymentChannel(paymentDTO.getPaymentChannel());
				payment.setServicesCharges(serviceCharges);
				payment.setClaimAmount(paymentDTO.getClaimAmount());
				payment.setReferenceNo(lifeClaim.getClaimInsuredPerson().getId());
				payment.setReferenceType(PolicyReferenceType.LIFE_DIS_CLAIM);
				paymentList.add(payment);
				lifeClaim.getClaimInsuredPerson().setClaimAmount(payment.getNetClaimAmount());

			}
			// paymentService.prePaymentAndTlfLifeClaim(paymentList,
			// lifeClaimBeneficiary, lifeClaim, premium);
			for (Payment p : paymentList) {
				p = paymentDAO.insert(p);
			}
			paymentDTO = new PaymentDTO(paymentList);
			claimDAO.update(lifeClaim);
			workFlowDTOService.deleteWorkFlowByRefNo(lifeClaim.getClaimRequestId());

			// Update LifePolicyClaimLog
			LifePolicyClaimLog log = lifePolicyTimeLineLogService.findLifePolicyClaimLogByLifeClaimId(lifeClaim.getId());
			log.setClaimConfirmDate(new Date());
			lifePolicyTimeLineLogService.updateLifePolicyClaimLog(log);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm Life Claim", e);
		}
		return paymentList;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void payLifeClaim(LifeClaimBeneficiary claimBeneficiary, Payment payment) {
		try {
			PolicyReferenceType policyReferenceType = null;
			// For PolicyBeneficiary
			if (claimBeneficiary.isClaimInsuredPersonBeneficiary()) {
				claimBeneficiary.castClaimInsuredPersonBeneficiary().setClaimStatus(ClaimStatus.PAID);
				claimBeneficiaryDAO.update(claimBeneficiary);
				lifePolicyDAO.updateBeneficiaryClaimStatusById(claimBeneficiary.castClaimInsuredPersonBeneficiary().getPolicyInsuredPersonBeneficiaries().getId(),
						ClaimStatus.PAID);
				policyReferenceType = PolicyReferenceType.LIFE_CLAIM;
			}
			// For Claim Successor
			if (claimBeneficiary.isSuccessor()) {
				claimBeneficiary.castLifeClaimSuccessor().setClaimStatus(ClaimStatus.PAID);
				claimBeneficiaryDAO.update(claimBeneficiary);
				// List<LifeClaimBeneficiary> insuPersonBeneficiaryList =
				// claimBeneficiaryDAO.findBySuccessorId(claimBeneficiary.getId());
				List<LifeClaimInsuredPersonBeneficiary> insuPersonBeneficiaryList = claimBeneficiary.castLifeClaimSuccessor().getClaimInsuredPersonBeneficiaryList();
				for (LifeClaimBeneficiary beneficiary : insuPersonBeneficiaryList) {
					lifePolicyDAO.updateBeneficiaryClaimStatusById(beneficiary.castClaimInsuredPersonBeneficiary().getPolicyInsuredPersonBeneficiaries().getId(), ClaimStatus.PAID);
					beneficiary.castClaimInsuredPersonBeneficiary().setClaimStatus(ClaimStatus.PAID);
					claimBeneficiaryDAO.update(beneficiary);
				}

				policyReferenceType = PolicyReferenceType.LIFE_CLAIM;
			}
			// For Disability Person
			if (claimBeneficiary.isDisabilityPerson()) {
				claimBeneficiary.castLifeDisabilityPerson().setClaimStatus(ClaimStatus.PAID);
				claimBeneficiaryDAO.update(claimBeneficiary);
				lifePolicyDAO.updateInsuredPersonClaimStatusById(claimBeneficiary.castLifeDisabilityPerson().getPolicyInsuredPerson().getId(), ClaimStatus.PAID);
				policyReferenceType = PolicyReferenceType.LIFE_DIS_CLAIM;
			}
			List<Payment> paymentList = paymentService.findByClaimProposal(claimBeneficiary.getId(), policyReferenceType, false);
			paymentService.activatePaymentAndTLF(paymentList, null, null);
			workFlowDTOService.deleteWorkFlowByRefNo(claimBeneficiary.getRefundNo());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield Life Claim's payment process", e);
		}
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	// public List<LCL001> findLifeClaimByEnquiryCriteria(LCL001 criteria) {
	// List<LCL001> lifeClaimList = null;
	// try {
	// lifeClaimList = claimDAO.findByEnquiryCriteria(criteria);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimList
	// by criteria : " + criteria, e);
	// }
	// return lifeClaimList;
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public String findStatusById(String id) {
		String result = null;
		try {
			result = claimDAO.findStatusById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeClaim (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaim findLifeClaimPortalById(String id) {
		LifeClaim result = null;
		try {
			result = claimDAO.findLifeClaimPortalById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeClaim Portal (ID : " + id + ")", e);
		}
		return result;
	}

}
