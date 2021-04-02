package org.ace.insurance.life.claim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.life.claim.ClaimMedicalFees;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifeClaimBeneficiaryPerson;
import org.ace.insurance.life.claim.LifeClaimNotification;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeClaimProposalAttachment;
import org.ace.insurance.life.claim.LifeClaimSurvey;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeDisabilityPaymentCriteria;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimProposalDAO;
import org.ace.insurance.life.claim.persistence.interfaces.ILifePolicyClaimDAO;
import org.ace.insurance.life.claim.service.interfaces.IClaimMedicalFeesService;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimNotificationService;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.payment.service.interfaces.ITlfProcessor;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.agent.ClaimMedicalFeesCriteria;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeClaimProposalService")
public class LifeClaimProposalService implements ILifeClaimProposalService {

	@Resource(name = "LifeClaimProposalDAO")
	private ILifeClaimProposalDAO lifeClaimProposalDAO;

	@Resource(name = "LifePolicyClaimDAO")
	private ILifePolicyClaimDAO lifePolicyClaimDAO;

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "ClaimAcceptedInfoService")
	private IClaimAcceptedInfoService claimAcceptedInfoService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Resource(name = "LifeClaimNotificationService")
	private ILifeClaimNotificationService notificationService;
	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;
	@Resource(name = "TlfProcessor")
	private ITlfProcessor tlfProcessor;
	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "ClaimMedicalFeesService")
	private IClaimMedicalFeesService claimMedicalFeesService;

	private void calculateClaimAmount(LifeClaimProposal claimProposal) {
		double totalClaimAmt = 0.00;
		if (claimProposal.getLifePolicyClaim() instanceof LifeHospitalizedClaim) {
			LifeHospitalizedClaim hospitalClaim = (LifeHospitalizedClaim) claimProposal.getLifePolicyClaim();
			totalClaimAmt = totalClaimAmt + hospitalClaim.getHospitalizedAmount();
			for (LifeClaimBeneficiaryPerson benefitPerson : claimProposal.getBeneficiaryList()) {
				if (benefitPerson.getBeneficiaryPerson() != null) {
					double hospitalizedAmount = hospitalClaim.getHospitalizedAmount() * benefitPerson.getBeneficiaryPerson().getPercentage() / 100;
					benefitPerson.setHospitalizedAmount(hospitalizedAmount);
				} else {
					benefitPerson.setHospitalizedAmount(hospitalClaim.getHospitalizedAmount() - claimProposal.getRemainPremium());
				}
			}
		} else if (claimProposal.getLifePolicyClaim() instanceof LifeDeathClaim) {
			LifeDeathClaim lifeDeathClaim = (LifeDeathClaim) claimProposal.getLifePolicyClaim();
			totalClaimAmt = totalClaimAmt + (lifeDeathClaim.getDeathClaimAmount() - claimProposal.getRemainPremium());
			for (LifeClaimBeneficiaryPerson benefitPerson : claimProposal.getBeneficiaryList()) {
				if (benefitPerson.getBeneficiaryPerson() != null) {
					double benefitDeathClaimAmt = lifeDeathClaim.getDeathClaimAmount() * benefitPerson.getBeneficiaryPerson().getPercentage() / 100;
					benefitPerson.setDeathClaimAmount(benefitDeathClaimAmt);
				}
			}
		} else {
			DisabilityLifeClaim disabiliyClaim = (DisabilityLifeClaim) claimProposal.getLifePolicyClaim();
			totalClaimAmt = totalClaimAmt + disabiliyClaim.getDisabilityClaimAmount() - claimProposal.getRemainPremium();
			for (LifeClaimBeneficiaryPerson benefitPerson : claimProposal.getBeneficiaryList()) {
				benefitPerson.setDisabilityAmount(disabiliyClaim.getDisabilityClaimAmount());
			}
		}
		claimProposal.setTotalClaimAmont(totalClaimAmt);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifeClaimProposal(LifePolicyClaim policyClaim, LifeClaimProposal claimProposal, LifeClaimNotification lifeClaimNotification, WorkFlowDTO workFlow,
			Map<String, String> claimAttachMap, String dir) {
		try {
			String productId = claimProposal.getClaimPerson().getProduct().getProductContent().getId();
			String claimProposalNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_CLAIM_PROPOSAL_NO, productId);

			claimProposal.setClaimProposalNo(claimProposalNo);
			String policyNo = claimProposal.getLifePolicy().getPolicyNo();
			policyClaim.setPolicyNo(policyNo);
			policyClaim.setOccurancePlace(claimProposal.getOccurancePlace());
			lifePolicyClaimDAO.insert(policyClaim);
			claimProposal.setLifePolicyClaim(policyClaim);
			calculateClaimAmount(claimProposal);

			lifeClaimProposalDAO.insert(claimProposal);
			for (String fileName : claimAttachMap.keySet()) {
				String filePath = dir + claimProposal.getId() + "/" + fileName;
				claimProposal.addAttachment(new LifeClaimProposalAttachment(fileName, filePath));
			}
			lifeClaimProposalDAO.update(claimProposal);
			lifeClaimNotification.setClaimStatus(ClaimStatus.CLAIM_APPLIED);
			notificationService.updateLifeClaimNotification(lifeClaimNotification);
			workFlow.setReferenceNo(claimProposal.getId());
			workFlowDTOService.addNewWorkFlow(workFlow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add LifeClaimProposal", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewLifeClaimSurvey(LifeClaimSurvey lifeClaimSurvey, WorkFlowDTO workFlow) {
		try {
			lifeClaimProposalDAO.update(lifeClaimSurvey.getLifeClaimProposal());
			lifeClaimProposalDAO.insert(lifeClaimSurvey);
			workFlowDTOService.updateWorkFlow(workFlow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add LifeClaimProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifeClaim(LifeClaimProposal claimProposal, WorkFlowDTO workFlow) {
		try {
			lifePolicyClaimDAO.update(claimProposal.getLifePolicyClaim());
			lifeClaimProposalDAO.update(claimProposal);
			workFlowDTOService.updateWorkFlow(workFlow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to approve LifeClaimProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informLifeClaim(ClaimAcceptedInfo claimAcceptedInfo, LifeClaimProposal lifeClaimProposal, WorkFlowDTO workFlow) {
		try {
			if (lifeClaimProposal.getLifePolicyClaim() instanceof DisabilityLifeClaim) {
				lifeClaimProposalDAO.update((DisabilityLifeClaim) lifeClaimProposal.getLifePolicyClaim());
			}
			lifeClaimProposalDAO.update(lifeClaimProposal);
			if (claimAcceptedInfo != null) {
				// if (claimAcceptedInfo.getClaimAmount() == 0.00) {
				// workFlowDTOService.deleteWorkFlowByRefNo(lifeClaimProposal.getId());
				// } else {
				// workFlowDTOService.updateWorkFlow(workFlow);
				// }
				workFlowDTOService.updateWorkFlow(workFlow);
				claimAcceptedInfoService.addNewClaimAcceptedInfo(claimAcceptedInfo);
			} else {
				workFlowDTOService.updateWorkFlow(workFlow);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to inform LifeClaimProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void confirmLifeClaimPropsal(LifeClaimProposal claimProposal, PaymentDTO paymentDTO, WorkFlowDTO workFlow) {
		Payment payment = null;
		try {
			Product product = claimProposal.getProduct();
			PolicyReferenceType referenceType = PolicyReferenceType.LIFE_CLAIM;
			double rate = 1.0;
			if (CurrencyUtils.isUSD(product.getCurrency())) {
				rate = paymentDAO.findActiveRate();
			}
			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, product.getId().trim());
			List<Payment> paymentList = new ArrayList<>();
			payment = new Payment();
			payment.setInvoiceNo(invoiceNo);
			payment.setReferenceType(referenceType);
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setMedicalFees(paymentDTO.getMedicalFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountAmount());
			payment.setReceivedDeno(paymentDTO.getReceivedDeno());
			payment.setRefundDeno(paymentDTO.getRefundDeno());
			payment.setConfirmDate(new Date());
			payment.setBasicPremium(paymentDTO.getBasicPremium());
			payment.setAddOnPremium(paymentDTO.getAddOnPremium());
			payment.setCurrency(product.getCurrency());
			payment.setRate(rate);
			payment.setAmount(payment.getNetPremium());
			payment.setClaimAmount(paymentDTO.getClaimAmount());
			payment.setFromTerm(1);
			payment.setToTerm(1);
			payment.setReferenceNo(claimProposal.getId());
			paymentList.add(payment);
			// medicalClaimProposalDTO.setTotalAllBeneAmt(paymentDTO.getTotalClaimAmount());
			lifeClaimProposalDAO.update(claimProposal);
			paymentService.preClaimPayment(paymentList);
			workFlowDTOService.updateWorkFlow(workFlow);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm Life Claim", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentLifeClaimProposal(LifeClaimProposal claimProposal, List<Payment> paymentList, Branch userBranch, WorkFlowDTO workFlowDTO) {
		Product product = claimProposal.getProduct();
		boolean isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
		boolean isSportMan = KeyFactorChecker.isSportMan(product);
		boolean isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		boolean isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		boolean isGroupLife = KeyFactorChecker.isGroupLife(product);
		boolean isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
		boolean isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
		PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_LIFE_CLAIM
				: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_LIFE_CLAIM
						: isShortTermEndownment ? PolicyReferenceType.SHORT_TERM_LIFE_CLAIM
								: isSportMan ? PolicyReferenceType.SPORT_MAN_CLAIM
										: isGroupLife ? PolicyReferenceType.GROUP_LIFE_CLAIM
												: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_CLAIM
														: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_CLAIM : PolicyReferenceType.ENDOWNMENT_LIFE_CLAIM;
		// To set complete true in lifeClaimProposal

		lifeClaimProposalDAO.update(claimProposal);
		paymentService.activateClaimPayment(paymentList, claimProposal.getClaimProposalNo(), paymentList.get(0).getRate());
		List<TlfData> dataList = new ArrayList<>();
		TlfData tlfData = null;
		Payment payment = null;

		/* Retrieve Payment from list by policy Id */
		payment = paymentList.stream().filter(p -> claimProposal.getId().equals(p.getReferenceNo())).findAny().orElse(null);

		if (payment.getMedicalFees() > 0.0) {
			ClaimMedicalFees medicalFees = new ClaimMedicalFees();
			medicalFees.setClaimNo(claimProposal.getClaimProposalNo());
			medicalFees.setReferenceNo(claimProposal.getId());
			medicalFees.setAmount(payment.getMedicalFees());
			medicalFees.setReceiptNo(payment.getReceiptNo());
			medicalFees.setCurrency(payment.getCurrency());
			medicalFees.setCustomer(claimProposal.getLifePolicy().getCustomer());
			medicalFees.setOrganization(claimProposal.getLifePolicy().getOrganization());
			medicalFees.setBranch(claimProposal.getLifePolicy().getBranch());
			medicalFees.setReferenceType(referenceType);
			medicalFees.setMedicalFeesStartDate(new Date());
			medicalFees.setPolicyNo(claimProposal.getLifePolicy().getPolicyNo());
			medicalFees.setHospital(claimProposal.getHospital());
			medicalFees.setHospitalCase(claimProposal.getHospitalCase());
			medicalFees.setClaimPerson(claimProposal.getClaimPerson());
			medicalFees.setInformDate(claimProposal.getInformDate());
			claimMedicalFeesService.insert(medicalFees);

		}

		/* Load Account Code */
		tlfData = tlfDataProcessor.getInstance(referenceType, claimProposal, payment);
		dataList.add(tlfData);
		tlfProcessor.handleLifeClaimTLFProcess(dataList, userBranch);
		if (workFlowDTO == null) {
			workFlowDTOService.deleteWorkFlowByRefNo(claimProposal.getId());
		} else {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifeClaimProposal(LifeClaimProposal claimProposal) {
		try {
			lifeClaimProposalDAO.update(claimProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update LifeClaimProposal", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifeClaimProposal(LifeClaimProposal claimProposal) {
		try {
			lifeClaimProposalDAO.delete(claimProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete LifeClaimProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimProposal findLifeClaimProposalById(String id) {
		LifeClaimProposal result = null;
		try {
			result = lifeClaimProposalDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find LifeClaimProposal", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DisabilityLifeClaim> findDisabilityLifeClaimByLifeClaimProposalNo(LifeDisabilityPaymentCriteria criteria) {
		List<DisabilityLifeClaim> result = null;
		try {
			result = lifeClaimProposalDAO.findDisabilityLifeClaimByLifeClaimProposalNo(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find DisabilityLifeClaim", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void confirmLifeDisabilityClaim(DisabilityLifeClaim disabilityClaim, PaymentDTO paymentDTO, WorkFlowDTO workFlow) {
		Payment payment = null;
		try {

			payment = new Payment();
			payment.setConfirmDate(new Date());
			payment.setBank(paymentDTO.getBank());
			payment.setBankAccountNo(paymentDTO.getBankAccountNo());
			payment.setChequeNo(paymentDTO.getChequeNo());
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setClaimAmount(paymentDTO.getClaimAmount());
			payment.setReferenceNo(disabilityClaim.getId());
			payment.setReferenceType(PolicyReferenceType.LIFE_CLAIM);
			// medicalClaimProposalDTO.setTotalAllBeneAmt(paymentDTO.getTotalClaimAmount());
			lifeClaimProposalDAO.update(disabilityClaim);

			// paymentService.prePaymentAndTlfLifeClaimProposal(payment,
			// claimProposal);
			// workFlowDTOService.addNewWorkFlow(workFlow);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm Life Claim", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectLifeClaimPropsal(LifeClaimProposal claimProposal, WorkFlowDTO workFlow) {
		try {
			workFlowService.addWorkFlowHistory(workFlow);
			workFlowService.deleteWorkFlowByRefNo(claimProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm Life Claim", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalDisabilityClaimPercentageByClaimPersonId(String id, String policyNo) {
		double result = 0;
		try {
			result = lifeClaimProposalDAO.findTotalDisabilityClaimPercentageByClaimPersonId(id, policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find Total Disability ClaimPercentage By ClaimPerson Id", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void issueLifeClaimPolicy(LifeClaimProposal lifeClaimProposal) {
		try {
			if (lifeClaimProposal.getLifePolicyClaim() instanceof DisabilityLifeClaim
					&& (KeyFactorChecker.isSnakeBite(lifeClaimProposal.getProduct().getId()) || KeyFactorChecker.isSportMan(lifeClaimProposal.getProduct()))
					|| KeyFactorChecker.isMicroHealth(lifeClaimProposal.getProduct().getId())) {
				lifeClaimProposal.getLifePolicy().setPolicyStatus(PolicyStatus.TERMINATE);
				lifePolicyDAO.update(lifeClaimProposal.getLifePolicy());
			}
			workFlowDTOService.deleteWorkFlowByRefNo(lifeClaimProposal.getId());
			lifeClaimProposalDAO.issueLifeClaimPolicy(lifeClaimProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Issue Life Claim Policy", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LCL001> findLifeClaimProposalByCriteria(LCL001 criteria) {
		List<LCL001> result = null;
		try {
			result = lifeClaimProposalDAO.findLifeClaimProposalByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find LifeClaim Proposal By Criteria" + e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimProposal> findByLifepolicyId(String lifePolicyId) {
		List<LifeClaimProposal> result = null;
		try {
			result = lifeClaimProposalDAO.findByLifepolicyId(lifePolicyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find LifeClaim Proposal By lifePolicyId" + e);
		}
		return result;
	}

	@Override
	public List<LifeClaimMonthlyReportDTO> findLifeClaimByCriteria(LCL001 criteria) {
		List<LifeClaimMonthlyReportDTO> result = null;
		try {
			result = lifeClaimProposalDAO.findLifeClaimByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find all LifeClaim report");
		}
		return result;
	}

	@Override
	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeSanction(ClaimMedicalFeesCriteria claimMedicalFeesCriteria) {
		List<LifeClaimMedicalFeeDTO> result = null;
		try {
			result = lifeClaimProposalDAO.findLifeClaimMedicalFeeSanction(claimMedicalFeesCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find all LifeClaim report");
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void invoicedClaimMedicalFees(List<LifeClaimMedicalFeeDTO> meedicalFeesList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo)
			throws SystemException {
		try {
			String invoiceNo = null;
			List<ClaimMedicalFees> claimMedicalFeesList = null;
			Payment payment = null;
			for (LifeClaimMedicalFeeDTO dto : meedicalFeesList) {
				invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.MEDICALFEES_INVOICE_NO, null);
				claimMedicalFeesList = claimMedicalFeesService.findMedicalFeesBySanctionNo(dto.getSanctionNo());
				for (ClaimMedicalFees claimMedicalFees : claimMedicalFeesList) {
					claimMedicalFees.setInvoiceNo(invoiceNo);
					claimMedicalFees.setInvoiceDate(new Date());
					claimMedicalFeesService.update(claimMedicalFees);

					payment = new Payment();
					payment.setPaymentChannel(paymentChannel);
					payment.setAccountBank(bank);
					payment.setBankAccountNo(bankAccountNo);
					payment.setInvoiceNo(invoiceNo);
					payment.setConfirmDate(new Date());
					payment.setPolicyNo(claimMedicalFees.getPolicyNo());
					payment.setReceiptNo(claimMedicalFees.getReceiptNo());
					payment.setReferenceNo(claimMedicalFees.getId());
					payment.setReferenceType(PolicyReferenceType.CLAIM_MEDICAL_FEES);
					payment.setCurrency(claimMedicalFees.getCurrency());
					payment.setAmount(claimMedicalFees.getAmount());
					payment.setMedicalFees(claimMedicalFees.getAmount());
					paymentDAO.insert(payment);
				}
				workFlow.setReferenceNo(invoiceNo);
				workFlow.setBranchId(dto.getBranchId());
				workFlowService.addNewWorkFlow(workFlow);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Agents.", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentMedicalFeesInvoice(List<ClaimMedicalFees> claimMedFeesList, SalesPoints salePoint, Branch branch) throws SystemException {
		try {
			List<TlfData> dataList = new ArrayList<>();
			TlfData tlfData = null;
			Payment payment = null;
			ClaimMedicalFees medicalFees = null;
			for (ClaimMedicalFees medFees : claimMedFeesList) {
				payment = paymentDAO.findPaymentByReferenceNo(medFees.getId());
				payment.setSalesPoints(salePoint);
				payment.setPaymentDate(new Date());
				payment.setComplete(true);
				paymentDAO.update(payment);
				tlfData = tlfDataProcessor.getClaimMedicalFeesInstance(payment, medFees);
				dataList.add(tlfData);
			}
			tlfProcessor.handleClaimMedicalFeesTlfProcess(dataList, branch);
			workFlowService.deleteWorkFlowByRefNo(payment.getInvoiceNo());

		} catch (

		DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentCommission For Payment By Agent", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void sanctionClaimMedicalFees(List<LifeClaimMedicalFeeDTO> meedicalFeesList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo) {
		try {
			String sanctionNo = null;
			sanctionNo = customIDGenerator.getCustomNextId(SystemConstants.MEDICALFEES_SANCTION_NO, null);
			for (LifeClaimMedicalFeeDTO sanctionInfo : meedicalFeesList) {
				sanctionInfo.setSanctionNo(sanctionNo);
				sanctionInfo.setSanctionDate(new Date());
			}

			claimMedicalFeesService.updateMedicalFeesStaus(meedicalFeesList);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add sanction medical fees", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeInvoice(ClaimMedicalFeesCriteria claimMedicalFeesCriteria) {
		List<LifeClaimMedicalFeeDTO> result = null;
		try {
			result = lifeClaimProposalDAO.findLifeClaimMedicalFeeInvoice(claimMedicalFeesCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Find all LifeClaim report");
		}
		return result;
	}

}
