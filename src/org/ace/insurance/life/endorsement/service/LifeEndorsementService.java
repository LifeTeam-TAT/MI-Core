package org.ace.insurance.life.endorsement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.EndorsementUtil;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.endorsement.BeneficiaryEndorseStatus;
import org.ace.insurance.life.endorsement.InsuredPersonEndorseStatus;
import org.ace.insurance.life.endorsement.LifeBeneficiary;
import org.ace.insurance.life.endorsement.LifeEndorseBeneficiary;
import org.ace.insurance.life.endorsement.LifeEndorseChange;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.LifeEndorseInsuredPerson;
import org.ace.insurance.life.endorsement.LifeEndorseItem;
import org.ace.insurance.life.endorsement.persistence.interfaces.ILifeEndorsementDAO;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyLog.LifePolicyEndorseLog;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.insurance.life.policyLog.service.interfaces.ILifePolicyTimeLineLogService;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.persistence.interfaces.ILifeProposalDAO;
import org.ace.insurance.life.proposalhistory.service.interfaces.ILifeProposalHistoryService;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IAgentCommissionService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.payment.service.interfaces.ITlfProcessor;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.persistence.interfaces.ICustomerDAO;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.organization.persistence.interfaces.IOrganizationDAO;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.exception.CustomIDGeneratorException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeEndorsementService")
public class LifeEndorsementService extends BaseService implements ILifeEndorsementService {
	@Resource(name = "LifeEndorsementDAO")
	private ILifeEndorsementDAO lifeEndorseDAO;

	@Resource(name = "LifePolicySummaryService")
	private ILifePolicySummaryService lifePolicySummaryService;

	@Resource(name = "AgentCommissionService")
	private IAgentCommissionService agentCommissionService;

	@Resource(name = "ProductService")
	private IProductService productService;

	@Resource(name = "LifeProposalDAO")
	private ILifeProposalDAO lifeProposalDAO;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "LifePolicyTimeLineLogService")
	private ILifePolicyTimeLineLogService lifePolicyTimeLineLogService;

	@Resource(name = "AcceptedInfoService")
	private IAcceptedInfoService acceptedInfoService;

	@Resource(name = "LifeProposalHistoryService")
	private ILifeProposalHistoryService lifeProposalHistoryService;

	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;

	@Resource(name = "TlfProcessor")
	private ITlfProcessor tlfProcessor;

	@Resource(name = "CustomerDAO")
	private ICustomerDAO customerDAO;

	@Resource(name = "OrganizationDAO")
	private IOrganizationDAO organizationDAO;

	private LifePolicySummary lifePolicySummary;

	boolean isPaidPremium = false;

	/** used in proposal form and confirm stage */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeEndorseInfo preCalculatePremium(LifeProposal lifeProposal) {
		LifeEndorseInfo lifeEndorseInfo;
		calculatePremium(lifeProposal);
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		setCodeNo(lifeProposal);
		if (isGroupLife(product)) {
			lifeEndorseInfo = checkChangesForGroup(lifeProposal);
			calculateEndorseForGroup(lifeEndorseInfo);
		} else {
			lifeEndorseInfo = checkChanges(lifeProposal);
			lifePolicySummary = calculateEndorsementPremium(lifeEndorseInfo);
		}
		setEndorsementPremium(lifeEndorseInfo, lifeProposal);
		return lifeEndorseInfo;
	}

	/** used in proposal form */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal addNewLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO, String status) {
		try {
			calculateTermPremium(lifeProposal);
			String productCode = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductGroup().getProposalNoPrefix();
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			setProposalNo(lifeProposal, productCode, product);
			lifePolicyService.updateEndorsementStatus(true, lifeProposal.getLifePolicy());
			// Custom ID GEN For Proposal Insured Person and Beneficiary
			setCodeNo(lifeProposal);
			if (!isGroupLife(product)) {
				lifePolicySummaryService.updateLifePolicySummary(lifePolicySummary);
			}
			lifeEndorseDAO.insert(lifeEndorseInfo);
			LifeProposal mp = lifeEndorseDAO.insert(lifeProposal);
			workFlowDTO.setReferenceNo(mp.getId());
			if (isPublicLife(product)) {
				workFlowDTO.setReferenceType(ReferenceType.ENDOWMENT_LIFE);
			} else if (isGroupLife(product)) {
				workFlowDTO.setReferenceType(ReferenceType.GROUP_LIFE);
			} else if (isSportMan(product)) {
				workFlowDTO.setReferenceType(ReferenceType.SPORT_MAN);
			} else if (isPersonalAccident(product)) {
				workFlowDTO.setReferenceType(ReferenceType.PA);
			} else if (isShortEndowmentLife(product)) {
				workFlowDTO.setReferenceType(ReferenceType.SHORT_ENDOWMENT_LIFE);
			}
			workFlowDTOService.addNewWorkFlow(workFlowDTO);
			lifeProposalDAO.updateStatus(status, lifeProposal.getPortalId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		}
		return lifeProposal;
	}

	/** used in proposal form */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal addNewShortEndowLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String name, LifeEndorseInfo endorseInfo) {
		try {
			String productCode = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductGroup().getProposalNoPrefix();
			String proposalNo = null;
			String insPersonCodeNo = null;
			String beneficiaryNo = null;
			calculateTermPremium(lifeProposal);
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

			lifeProposal.setProposalType(ProposalType.ENDORSEMENT);
			if (isShortEndowmentLife(product)) {
				proposalNo = customIDGenerator.getNextId(SystemConstants.SHORT_ENDOWMENT_PROPOSAL_ENDORSEMENT_NO, productCode);
			}

			// Custom ID GEN For Proposal Insured Person and Beneficiary
			for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
				if (person.getInsPersonCodeNo() == null) {
					insPersonCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_CODENO, null);
					person.setInsPersonCodeNo(insPersonCodeNo);
				}
				for (InsuredPersonBeneficiaries beneficiary : person.getInsuredPersonBeneficiariesList()) {
					if (beneficiary.getBeneficiaryNo() == null) {
						beneficiaryNo = customIDGenerator.getNextId(SystemConstants.LIFE_BENEFICIARY_NO, null);
						beneficiary.setBeneficiaryNo(beneficiaryNo);
					}
				}
			}
			lifeProposal.setProposalNo(proposalNo);
			lifeEndorseDAO.insert(endorseInfo);
			lifePolicyService.updateEndorsementStatus(true, lifeProposal.getLifePolicy());
			LifeProposal mp = lifeEndorseDAO.insert(lifeProposal);
			workFlowDTO.setReferenceNo(mp.getId());
			workFlowDTO.setReferenceType(ReferenceType.SHORT_ENDOWMENT_LIFE);
			workFlowDTOService.addNewWorkFlow(workFlowDTO);
			lifeProposalDAO.updateStatus(name, lifeProposal.getPortalId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		}
		return lifeProposal;
	}

	/** used in proposal form */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal addNewNonFinancialLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, String name) {
		try {
			calculateTermPremium(lifeProposal);
			String productCode = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductGroup().getProposalNoPrefix();
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			lifeProposal.setProposalType(ProposalType.ENDORSEMENT);
			setProposalNo(lifeProposal, productCode, product);
			lifePolicyService.updateEndorsementStatus(true, lifeProposal.getLifePolicy());

			// Custom ID GEN For Proposal Insured Person and Beneficiary
			setCodeNo(lifeProposal);
			lifeProposal = lifeEndorseDAO.insert(lifeProposal);
			LifePolicy lifePolicy = new LifePolicy(lifeProposal);
			lifePolicy.setId(lifeProposal.getLifePolicy().getId());
			lifePolicy.setPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
			lifePolicy.setEndorsementConfirmDate(new Date());
			lifePolicy.setEndorsementApplied(lifeProposal.getLifePolicy().isEndorsementApplied());
			lifePolicy.setCommenmanceDate(lifeProposal.getLifePolicy().getCommenmanceDate());
			lifePolicy.setActivedPolicyStartDate(lifeProposal.getLifePolicy().getActivedPolicyStartDate());
			lifePolicy.setActivedPolicyEndDate(lifeProposal.getLifePolicy().getActivedPolicyEndDate());
			lifePolicyService.updateLifePolicy(lifePolicy);

			lifeEndorseInfo.setEndorsePolicyReferenceNo(lifePolicy.getId());
			lifeEndorseDAO.insert(lifeEndorseInfo);
			lifeProposal.setLifePolicy(lifePolicy);
			lifeEndorseDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Non-Financial Endorsement LifeProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Non-Financial Endorsement LifeProposal", e);
		}
		return lifeProposal;
	}

	/** used in survey form */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeEndorseDAO.insertSurvey(lifeSurvey);
			lifeEndorseDAO.updateInsuPersonMedicalStatus(lifeSurvey.getLifeProposal().getProposalInsuredPersonList());
			lifeEndorseDAO.addAttachment(lifeSurvey.getLifeProposal());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Survey", e);
		}
	}

	/** used in proposal form and Edit proposal form */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal updateLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO) {
		try {
			LifeProposal oldLifeProposal = lifeProposalDAO.findById(lifeProposal.getId());
			lifeProposalHistoryService.addNewLifeProposalHistory(oldLifeProposal);
			calculateTermPremium(lifeProposal);
			setCodeNo(lifeProposal);
			if (lifeEndorseInfo != null) {
				LifeEndorseInfo oldLifeEndorseInfo = lifeEndorseDAO.findBySourcePolicyReferenceNo(lifeProposal.getLifePolicy().getId());
				lifeEndorseDAO.insert(lifeEndorseInfo);
				lifeEndorseDAO.deleteLifeEndorseInfo(oldLifeEndorseInfo);
			}
			// Underwriting
			lifeEndorseDAO.update(lifeProposal);
			if (workFlowDTO != null)
				workFlowDTOService.updateWorkFlow(workFlowDTO);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
		return lifeProposal;
	}

	/** used in approved form */
	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeEndorseDAO.updateInsuredPersonApprovalInfo(lifeProposal.getProposalInsuredPersonList());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to approve a LifeProposal", e);
		}
	}

	/** used in inform form */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void informLifeEndorse(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			if (!EndorsementUtil.isEndorsementProposal(lifeProposal.getLifePolicy())) {
				calculatePremium(lifeProposal);
			}
			if (acceptedInfo != null) {
				acceptedInfoService.addNewAcceptedInfo(acceptedInfo);
				lifeProposal.setSpecialDiscount(acceptedInfo.getDiscountPercent());
				lifeProposalDAO.update(lifeProposal);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to inform a LifeProposal", e);
		}
	}

	/** used in confirm form */
	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.addWorkFlowHistory(workFlowDTO);
			lifeProposalDAO.updateProposalStatus(ProposalStatus.DENY, lifeProposal.getId());
			workFlowDTOService.deleteWorkFlowByRefNo(workFlowDTO.getReferenceNo());
			lifeEndorseDAO.deleteLifeEndorseInfo(lifeEndorseInfo);
			lifePolicyDAO.updateEndorsementStatus(false, lifeProposal.getLifePolicy().getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to reject a LifeProposal", e);
		}
	}

	/** used in confirm form */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status) {
		List<Payment> paymentList = new ArrayList<Payment>();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		boolean isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
		boolean isFarmer = KeyFactorChecker.isFarmer(product);
		boolean isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		boolean isGroupLife = KeyFactorChecker.isGroupLife(product);
		boolean isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		boolean isSportMan = KeyFactorChecker.isSportMan(product);
		PolicyReferenceType referenceType = isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
				: isPersonalAccident ? PolicyReferenceType.PA_POLICY
						: isFarmer ? PolicyReferenceType.FARMER_POLICY
								: isShortTermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
										: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
												: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);

			/* create LifePolicy */
			LifePolicy lifePolicy = new LifePolicy(lifeProposal);
			/* Set Id and Version for endorsement process */
			lifePolicy.setId(lifeProposal.getLifePolicy().getId());
			lifePolicy.setVersion(lifeProposal.getLifePolicy().getVersion());
			lifePolicy.setCoverageDate(lifeProposal.getLifePolicy().getCoverageDate());
			lifePolicy.setPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
			lifePolicy.setEndorsementApplied(lifeProposal.getLifePolicy().isEndorsementApplied());
			lifePolicy.setCoinsuranceApplied(lifeProposal.getLifePolicy().isCoinsuranceApplied());
			lifePolicy.setEndorsementConfirmDate(new Date());
			lifePolicy.setCommenmanceDate(lifeProposal.getLifePolicy().getCommenmanceDate());
			lifePolicy.setActivedPolicyStartDate(lifeProposal.getLifePolicy().getActivedPolicyStartDate());
			lifePolicy.setActivedPolicyEndDate(lifeProposal.getLifePolicy().getActivedPolicyEndDate());
			lifePolicy.setLastPaymentTerm(lifeProposal.getLifePolicy().getLastPaymentTerm());
			for (PolicyInsuredPerson newPolicyPerson : lifePolicy.getPolicyInsuredPersonList()) {
				for (PolicyInsuredPerson oldPolicyPerson : lifeProposal.getLifePolicy().getPolicyInsuredPersonList()) {
					if (oldPolicyPerson.getInsPersonCodeNo().equals(newPolicyPerson.getInsPersonCodeNo())) {
						newPolicyPerson.setId(oldPolicyPerson.getId());
						newPolicyPerson.setVersion(oldPolicyPerson.getVersion());
					}
				}

			}
			/* recalculate payment term and term premium */
			for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
				calculateTermPremium(person, lifePolicy.getPaymentType().getMonth());
			}
			/* create Payment */
			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, product.getId().trim());
			Payment payment = new Payment();
			payment.setInvoiceNo(invoiceNo);
			payment.setBank(paymentDTO.getBank());
			payment.setBankAccountNo(paymentDTO.getBankAccountNo());
			payment.setChequeNo(paymentDTO.getChequeNo());
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			// FIXME CHECK REFTYPE
			payment.setReferenceType(referenceType);
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountPercent());
			payment.setReceivedDeno(paymentDTO.getReceivedDeno());
			payment.setRefundDeno(paymentDTO.getRefundDeno());
			payment.setEndorsementConfirmDate(new Date());
			payment.setConfirmDate(new Date());
			payment.setPoNo(paymentDTO.getPoNo());
			payment.setAccountBank(paymentDTO.getAccountBank());
			payment.setBasicPremium(lifePolicy.getEndorsementBasicPremium());
			payment.setAddOnPremium(lifePolicy.getEndorsementAddOnPremium());
			payment.setCurrency(product.getCurrency());
			payment.setFromTerm(1);
			payment.setToTerm(1);
			/* calculate payment term and term premium for discount */
			if (paymentDTO.getDiscountPercent() > 0.0) {
				for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
					calculateDiscount(person, paymentDTO.getDiscountPercent(), true);
				}
			}
			if (EndorsementUtil.isTerminatePolicy(lifeProposal)) {
				lifePolicyService.terminateLifePolicy(lifePolicy);
			} else {
				lifePolicyService.updateLifePolicyEndorsement(lifePolicy);
			}
			String sourcePolicyId = lifeProposal.getLifePolicy().getId();
			/* update lifeendorseinfo for changed policy id */
			lifeEndorseDAO.updateEndorsePolicyReferenceNo(sourcePolicyId, lifePolicy.getId());
			/* reset new motorpolicy id for endorsement case */
			lifeProposal.setLifePolicy(lifePolicy);
			/* For Public Life */
			if (lifeProposal.getProposalInsuredPersonList().size() == 1) {
				LifePolicySummary summary = lifePolicySummaryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getId());
				if (summary != null) {
					if (summary.getCoverTime() > 0) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(lifeProposal.getLifePolicy().getActivedPolicyEndDate());
						for (int i = 0; i < summary.getCoverTime(); i++) {
							cal.add(Calendar.MONTH, lifeProposal.getPaymentType().getMonth());
							cal.setTime(cal.getTime());
						}
						lifePolicy.setActivedPolicyEndDate(cal.getTime());
					}
				}
			}
			payment.setReferenceNo(lifePolicy.getId());
			paymentList.add(payment);
			lifeEndorseDAO.update(lifeProposal);
			for (Payment p : paymentList) {
				p = paymentDAO.insert(p);
			}
			/* Entry to LifePolicyEndorseLog (Endorsement) */
			Date endorsementDate = lifeProposal.getSubmittedDate();
			Date endorsementConfirmDate = lifePolicy.getEndorsementConfirmDate();
			LifeEndorseInfo endorseInfo = findByEndorsePolicyReferenceNo(lifePolicy.getId());
			LifePolicyTimeLineLog timeLineLog = lifePolicyTimeLineLogService.findLifePolicyTimeLineLogByPolicyNo(lifePolicy.getPolicyNo());
			LifePolicyEndorseLog endorseLog = new LifePolicyEndorseLog(endorsementDate, endorsementConfirmDate, endorseInfo, timeLineLog);
			lifePolicyTimeLineLogService.addLifePolicyEndorseLog(endorseLog);

			LifePolicyIdLog idLog = new LifePolicyIdLog(sourcePolicyId, lifePolicy.getId(), lifePolicy.getLifeProposal().getId(), ProposalType.ENDORSEMENT, timeLineLog);
			lifePolicyTimeLineLogService.addLifePolicyIdLog(idLog);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm a LifeProposal", e);
		}
		return paymentList;
	}

	/** used in payment form */
	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch branch, String status) {
		try {
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			boolean isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			boolean isFarmer = KeyFactorChecker.isFarmer(product);
			boolean isSportMan = KeyFactorChecker.isSportMan(product);
			boolean isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			boolean isGroupLife = KeyFactorChecker.isGroupLife(product);
			PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_POLICY
					: isFarmer ? PolicyReferenceType.FARMER_POLICY
							: isShortTermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
											: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;

			workFlowDTOService.updateWorkFlow(workFlowDTO);

			double rate = paymentList.get(0).getRate();
			if (lifeProposal.getStartDate() == null) {
				lifeProposal.setStartDate(new Date());
				resetDate(lifeProposal, product);
			}
			lifeProposalDAO.update(lifeProposal);

			LifePolicy lifePolicy = lifePolicyService.activateLifePolicy(lifeProposal);
			String policyNo = lifePolicy.getPolicyNo();
			String lifePolicyId = lifePolicy.getId();
			paymentService.activatePayment(paymentList, policyNo, rate);

			/* Agent Commission */
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();

			if (lifeProposal.getAgent() != null) {
				Payment payment = null;
				agentCommissionList = new ArrayList<AgentCommission>();
				payment = paymentList.stream().filter(p -> lifePolicyId.equals(p.getReferenceNo())).findAny().orElse(null);
				String receiptNo = payment.getReceiptNo();
				double netPremium = payment.getNetAgentComPremium();
				double commissionPercent = product.getFirstCommission();
				double agentComm = Utils.getPercentOf(commissionPercent, netPremium);
				agentCommissionList.add(new AgentCommission(lifePolicy.getId(), referenceType, lifePolicy.getPolicyNo(), lifePolicy.getCustomer(), lifePolicy.getOrganization(),
						lifePolicy.getBranch(), lifePolicy.getAgent(), agentComm, new Date(), receiptNo, netPremium, commissionPercent, AgentCommissionEntryType.ENDORSEMENT, rate,
						(rate * agentComm), product.getCurrency(), (rate * netPremium)));
				agentCommissionService.addNewAgentCommisssion(agentCommissionList);
			}

			/* TLF */
			List<TlfData> dataList = new ArrayList<>();
			TlfData tlfData = null;
			Payment payment = null;
			/* Retrieve Payment from list by policy Id */
			payment = paymentList.stream().filter(p -> lifePolicyId.equals(p.getReferenceNo())).findAny().orElse(null);
			/* Load Account Code */
			tlfData = tlfDataProcessor.getInstance(referenceType, lifePolicy, payment, agentCommissionList, false);
			dataList.add(tlfData);
			tlfProcessor.handleTlfProcess(dataList, branch);

			/* update ActivePolicy Count in CustomerTable */
			if (lifeProposal.getCustomer() != null) {
				int activePolicyCount = lifeProposal.getCustomer().getActivePolicy() + 1;
				customerDAO.updateActivePolicy(activePolicyCount, lifeProposal.getCustomerId());
				if (lifeProposal.getCustomer().getActivedDate() == null) {
					customerDAO.updateActivedPolicyDate(new Date(), lifeProposal.getCustomerId());
				}
			}

			/* update ActivePolicy Count in OrganizationTable */
			if (lifeProposal.getOrganization() != null) {
				int activePolicyCount = lifeProposal.getOrganization().getActivePolicy() + 1;
				organizationDAO.updateActivePolicy(activePolicyCount, lifeProposal.getCustomerId());
				if (lifeProposal.getOrganization().getActivedDate() == null) {
					organizationDAO.updateActivedPolicyDate(new Date(), lifeProposal.getCustomerId());
				}
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to payment a LifeProposal ID : " + lifeProposal.getId(), e);
		}

	}

	/** used in issue form */
	@Transactional(propagation = Propagation.REQUIRED)
	public void issueLifeProposal(LifeProposal lifeProposal) {
		try {
			workFlowDTOService.deleteWorkFlowByRefNo(lifeProposal.getId());
			lifeEndorseDAO.updateCompleteStatus(true, lifeProposal.getId());
			if (lifeProposal.getLifePolicy() != null) {
				lifeProposal.getLifePolicy().setIssueDate(new Date());
				lifeProposal.getLifePolicy().setEndorsementApplied(false);
				lifePolicyService.updateEndorsementStatusAndIssueDate(lifeProposal.getLifePolicy());
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to issue a LifeProposal.", e);
		}
	}

	// for public life
	/** used in endorse proposal detail */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeEndorseInfo findLastLifeEndorseInfoByPolicyId(String policyId) {
		LifeEndorseInfo lifeEndorseInfo = null;
		try {
			lifeEndorseInfo = lifeEndorseDAO.findLastLifeEndorseInfoByPolicyId(policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find last LifeEndorseInfo", e);
		}
		return lifeEndorseInfo;
	}

	/** used in endorse proposal detail */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeEndorseInfo findByEndorsePolicyReferenceNo(String policyId) {
		LifeEndorseInfo lifeEndorseInfo = null;
		try {
			lifeEndorseInfo = lifeEndorseDAO.findByEndorsePolicyReferenceNo(policyId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeEndorseInfo By EndorsePolicyReferenceNo", e);
		}
		return lifeEndorseInfo;
	}

	/** used in lifeProposalService and RenewalGroupLifeProposalService */
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeEndorseInfo updateLifeEndorseInfo(LifeProposal lifeProposal) {
		LifeEndorseInfo lifeEndorseInfo = null;
		try {
			LifeEndorseInfo oldLifeEndorseInfo = lifeEndorseDAO.findBySourcePolicyReferenceNo(lifeProposal.getLifePolicy().getId());
			if (lifeProposal.getLifePolicy().getPolicyInsuredPersonList().size() == 1) {
				lifeEndorseInfo = checkChanges(lifeProposal);
				calculateEndorsementPremium(lifeEndorseInfo);
			} else {
				lifeEndorseInfo = checkChangesForGroup(lifeProposal);
				calculateEndorseForGroup(lifeEndorseInfo);
			}
			lifeEndorseDAO.insert(lifeEndorseInfo);
			lifeEndorseDAO.deleteLifeEndorseInfo(oldLifeEndorseInfo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update LifeEndorseInfo", e);
		}
		return lifeEndorseInfo;
	}

	private void setProposalNo(LifeProposal lifeProposal, String productCode, Product product) {
		String proposalNo = null;
		if (isPublicLife(product)) {
			proposalNo = customIDGenerator.getNextId(SystemConstants.PUBLICLIFE_PROPOSAL_ENDORSEMENT_NO, productCode);
		} else if (isGroupLife(product)) {
			proposalNo = customIDGenerator.getNextId(SystemConstants.GROUPLIFE_PROPOSAL_ENDORSEMENT_NO, productCode);
		} else if (isSportMan(product)) {
			proposalNo = customIDGenerator.getNextId(SystemConstants.LIFE_SPORTMAN_ENDORSEMENT_NO, productCode);
		} else if (isShortEndowmentLife(product)) {
			proposalNo = customIDGenerator.getNextId(SystemConstants.SHORT_ENDOWMENT_PROPOSAL_ENDORSEMENT_NO, productCode);
		} else if (isPersonalAccident(product)) {
			proposalNo = customIDGenerator.getNextId(SystemConstants.PERSONAL_ACCIDENT_PROPOSAL_ENDORSEMENT_NO, productCode);
		}
		lifeProposal.setProposalNo(proposalNo);
	}

	private LifeProposal calculatePremium(LifeProposal lifeProposal) {
		Double premium;
		Double premiumRate;
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
			for (InsuredPersonKeyFactorValue insukf : pv.getKeyFactorValueList()) {
				keyfatorValueMap.put(insukf.getKeyFactor(), insukf.getValue());
			}
			if (isSportMan(product) || isSnakeBite(product)) {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product, new PremiumCalData(null, null, null, (double) pv.getUnit()));
				pv.setProposedSumInsured(pv.getUnit() * product.getSumInsuredPerUnit());
			} else {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product, new PremiumCalData(null, pv.getProposedSumInsured(), null, null));
			}
			pv.setProposedPremium(premium);
			if (premium == null || premium < 0) {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}

			List<InsuredPersonAddon> insuredPersonAddOnList = pv.getInsuredPersonAddOnList();
			if (insuredPersonAddOnList != null && !insuredPersonAddOnList.isEmpty()) {
				for (InsuredPersonAddon insuredPersonAddOn : insuredPersonAddOnList) {
					double addOnPremium = 0.0;
					double addOnPremiumRate = 0.0;
					Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
					if (insuredPersonAddOn.getAddOn().isBaseOnKeyFactor()) {
						for (KeyFactor kf : insuredPersonAddOn.getAddOn().getKeyFactorList()) {
							innerLoop: for (InsuredPersonKeyFactorValue ipKf : pv.getKeyFactorValueList()) {
								if (kf.equals(ipKf.getKeyFactor())) {
									addOnKeyfatorValueMap.put(kf, ipKf.getValue());
									break innerLoop;
								}
							}
							if (KeyFactorChecker.isGender(kf)) {
								addOnKeyfatorValueMap.put(kf, pv.getGender().getLabel());
							}
						}
					}
					addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn(),
							new PremiumCalData(insuredPersonAddOn.getProposedSumInsured(), pv.getProposedSumInsured(), pv.getProposedPremium(), null));
					addOnPremiumRate = premiumCalculatorService.findPremiumRate(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn());
					insuredPersonAddOn.setPremiumRate(addOnPremiumRate);
					insuredPersonAddOn.setProposedPremium(addOnPremium);
				}
			}
		}
		return lifeProposal;
	}

	private void setCodeNo(LifeProposal lifeProposal) {
		String insPersonCodeNo = null;
		String inPersonGroupCodeNo = null;
		String beneficiaryNo = null;
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
			if (person.getInsPersonCodeNo() == null) {
				insPersonCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_CODENO, null);
				person.setInsPersonCodeNo(insPersonCodeNo);
			}

			if (isGroupLife(product)) {
				if (person.getInPersonGroupCodeNo() == null) {
					inPersonGroupCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_GROUP_CODENO, null);
					person.setInPersonGroupCodeNo(inPersonGroupCodeNo);
				}
			}
			for (InsuredPersonBeneficiaries beneficiary : person.getInsuredPersonBeneficiariesList()) {
				if (beneficiary.getBeneficiaryNo() == null) {
					beneficiaryNo = customIDGenerator.getNextId(SystemConstants.LIFE_BENEFICIARY_NO, null);
					beneficiary.setBeneficiaryNo(beneficiaryNo);
				}
			}
		}
	}

	private void calculateDiscount(PolicyInsuredPerson policyInsuredPerson, double discountPercent, boolean isEndorse) {
		/* Reset Discount EndorsementNetBasicPremium */
		double endorsementBasicPremium = policyInsuredPerson.getEndorsementNetBasicPremium();
		double discountEndorsementBasicPremium = endorsementBasicPremium - Utils.getPercentOf(discountPercent, endorsementBasicPremium);
		policyInsuredPerson.setEndorsementNetBasicPremium(discountEndorsementBasicPremium);
	}

	private void setEndorsementPremium(LifeEndorseInfo lifeEndorseInfo, LifeProposal lifeProposal) {
		initializeEndorsementPremium(lifeProposal);
		for (LifeEndorseInsuredPerson endorsePerson : lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList()) {
			for (ProposalInsuredPerson proposalPerson : lifeProposal.getProposalInsuredPersonList()) {
				if (endorsePerson.getInsuredPersonCodeNo().equals(proposalPerson.getInsPersonCodeNo())) {
					// TODO FIXME REMOVE INTEREST REQUEST BY FNI
					// proposalPerson.setEndorsementNetBasicPremium(endorsePerson.getEndorsePremium()
					// + endorsePerson.getEndorseInterest());
					proposalPerson.setEndorsementNetBasicPremium(endorsePerson.getEndorsePremium());
					proposalPerson.setInterest(0.0);
				}
			}
		}
	}

	private void initializeEndorsementPremium(LifeProposal lifeProposal) {
		for (ProposalInsuredPerson proposalPerson : lifeProposal.getProposalInsuredPersonList()) {
			proposalPerson.setEndorsementNetBasicPremium(0);
			proposalPerson.setInterest(0);
		}
	}

	// For Public Life (SI Increase, SI Decrease, Term Increase, Term Decrease,
	// Terminate Customer)
	private LifePolicySummary calculateEndorsementPremium(LifeEndorseInfo lifeEndorseInfo) {
		LifeEndorseChange lifeEndorseChange = null;
		LifePolicySummary lifePolicySummary = null;
		double endorsePremium = 0.0;
		double oldAmount = 0;
		double newAmount = 0;
		double differenceSI = 0;
		double oldPremium = 0.0;
		double newPremium = 0.0;
		double differencePremium = 0.0;
		double additionalPre = 0.0;
		int oldPeriodOfYear = 0;
		double totalInterest = 0.0;
		int covertime = 0;
		double refundAmount = 0.0;
		double paidUpValue = 0.0;
		int passedMonths = 0;
		int passedYears = 0;
		int month = 0;
		int paymentType;
		int paidTerm;
		LifePolicy endorsePolicy = lifePolicyDAO.findLifePolicyByPolicyNo(lifeEndorseInfo.getLifePolicyNo());
		paidTerm = endorsePolicy.getLastPaymentTerm();
		lifePolicySummary = lifePolicySummaryService.findLifePolicyByPolicyNo(lifeEndorseInfo.getSourcePolicyReferenceNo());

		if (lifePolicySummary == null) {
			lifePolicySummary = new LifePolicySummary();
			lifePolicySummary.setPolicyNo(lifeEndorseInfo.getSourcePolicyReferenceNo());
		} else {
			lifePolicySummary.setCoverTime(0);
			refundAmount = lifePolicySummary.getRefund();
			paidUpValue = lifePolicySummary.getPaidUpValue();
		}

		for (LifeEndorseInsuredPerson lifeEndorseInsuredPerson : lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList()) {
			oldPeriodOfYear = lifeEndorseInsuredPerson.getPeriodOfMonths() / 12;
			passedMonths = lifeEndorseInfo.getPassedMonth();
			passedYears = passedMonths / 12;

			// SI Increase
			if (EndorsementUtil.isLifeSIIncreOnly(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.INCREASE_SI);

				oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);
				newPremium = Utils.divide(lifeEndorseChange.getNewPremium(), 12);
				differencePremium = Utils.getTwoDecimalPoint(newPremium - oldPremium);
				additionalPre = Utils.getTwoDecimalPoint(differencePremium * paidTerm);

				if (newPremium > oldPremium) {
					month = passedMonths % 12;
					double oneYearPremium = differencePremium * 12;
					// Interest Calculation Method
					// TODO FIXME REMOVE INTEREST REQUEST BY FNI
					// totalInterest = calculateInterest(oneYearPremium,
					// differencePremium, month, passedYears);
				}

				lifePolicySummary.setInterest(totalInterest);

				lifeEndorseChange.setEndorsePremium(additionalPre);
				lifeEndorseChange.setInterest(totalInterest);
			}

			if (lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus().equals(InsuredPersonEndorseStatus.REPLACE)) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.REPLACE);
			}

			if (EndorsementUtil.isLifeInsuredPersonNew(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.INSUREDPERSON_NEW);
				lifeEndorseChange.setEndorsePremium(lifeEndorseChange.getNewPremium());
			}

			// SI Decrease
			if (EndorsementUtil.isLifeSIDecreOnly(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.DECREASE_SI);
				oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);
				newPremium = Utils.divide(lifeEndorseChange.getNewPremium(), 12);
				oldAmount = Double.parseDouble(lifeEndorseChange.getOldValue());
				newAmount = Double.parseDouble(lifeEndorseChange.getNewValue());
				differenceSI = Utils.getTwoDecimalPoint(newAmount - oldAmount);

				if (isPaidPremium && passedMonths % 12 > 5) {
					int restMonth = 12 - (passedMonths % 12);
					passedYears = passedYears + 1;
					if ((oldPeriodOfYear <= 12 && passedYears >= 2) || oldPeriodOfYear > 12 && passedYears >= 3) {
						paidUpValue = paidUpValue + Utils.divide(Utils.getTwoDecimalPoint(passedYears * differenceSI), oldPeriodOfYear);
						endorsePremium = Utils.getTwoDecimalPoint(oldPremium * restMonth);
						lifePolicySummary.setPaidUpValue(paidUpValue);
						lifeEndorseChange.setEndorsePremium(endorsePremium);
						lifeEndorseChange.setPaidUpValue(paidUpValue);
					}
				} else {
					if ((oldPeriodOfYear <= 12 && passedYears >= 2) || oldPeriodOfYear > 12 && passedYears >= 3) {
						paidUpValue = paidUpValue + Utils.divide(Utils.getTwoDecimalPoint(passedYears * differenceSI), oldPeriodOfYear);
						paidUpValue = Utils.nagateIfNagative(paidUpValue);
						lifePolicySummary.setPaidUpValue(paidUpValue);
						lifeEndorseChange.setPaidUpValue(paidUpValue);
					}
				}

				lifeEndorseChange.setEndorsePremium(newPremium);

			}

			// Term Increase
			if (EndorsementUtil.isLifeTermIncreOnly(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.INCREASE_TERM);
				oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);
				newPremium = Utils.divide(lifeEndorseChange.getNewPremium(), 12);
				differencePremium = newPremium - oldPremium;
				additionalPre = Utils.getTwoDecimalPoint(differencePremium * paidTerm);
				paymentType = lifeEndorseInfo.getMonthsOfPaymentType();
				additionalPre = additionalPre + refundAmount;
				covertime = (int) Utils.divide(additionalPre, (newPremium * paymentType));
				covertime = (int) Utils.nagateIfNagative(covertime);
				refundAmount = (additionalPre % (newPremium * paymentType));
				refundAmount = Utils.nagateIfNagative(refundAmount);

				lifeEndorseChange.setEndorsePremium(additionalPre); // old
				// code
				lifeEndorseChange.setCoverTime(covertime);
				lifeEndorseChange.setRefundAmount(refundAmount);
				lifePolicySummary.setCoverTime(covertime);
				lifePolicySummary.setRefund(refundAmount);
			}

			// Term Decreased
			if (EndorsementUtil.isLifeTermDecreOnly(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.DECREASE_TERM);

				oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);
				newPremium = Utils.divide(lifeEndorseChange.getNewPremium(), 12);
				differencePremium = newPremium - oldPremium;
				additionalPre = Utils.getTwoDecimalPoint(differencePremium * paidTerm);

				if (newPremium > oldPremium) {
					month = passedMonths % 12;
					double oneYearPremium = Utils.getTwoDecimalPoint(differencePremium * 12);
					// Interest Calculation Method
					// TODO FIXME REMOVE INTEREST REQUEST BY FNI
					// totalInterest = calculateInterest(oneYearPremium,
					// differencePremium, month, passedYears);
				}
				lifePolicySummary.setInterest(totalInterest);
				lifeEndorseChange.setEndorsePremium(additionalPre);
				lifeEndorseChange.setInterest(totalInterest);
			}

			// Terminate By Customer
			if (lifeEndorseChange != null) {
				if (lifeEndorseChange.getLifeEndorseItem().equals(LifeEndorseItem.TERMINATE_CUSTOMER)) {
					oldAmount = Double.parseDouble(lifeEndorseChange.getOldValue());
					oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);

					if (passedMonths % 12 > 5) {
						int restMonth = 12 - (passedMonths % 12);
						passedYears = passedYears + 1;
						if ((oldPeriodOfYear <= 12 && passedYears >= 2) || oldPeriodOfYear > 12 && passedYears >= 3) {
							paidUpValue = paidUpValue + Utils.divide(Utils.getTwoDecimalPoint(passedYears * oldAmount), oldPeriodOfYear);
							paidUpValue = Utils.nagateIfNagative(paidUpValue);
							endorsePremium = Utils.getTwoDecimalPoint(oldPremium * restMonth);
							endorsePremium = Utils.nagate(endorsePremium);

							lifePolicySummary.setPaidUpValue(paidUpValue);
							lifeEndorseChange.setPaidUpValue(paidUpValue);
							lifeEndorseChange.setEndorsePremium(endorsePremium);
						}
					} else {
						if ((oldPeriodOfYear <= 12 && passedYears >= 2) || oldPeriodOfYear > 12 && passedYears >= 3) {
							paidUpValue = paidUpValue + Utils.divide(Utils.getTwoDecimalPoint(passedYears * oldAmount), oldPeriodOfYear);
							paidUpValue = Utils.nagateIfNagative(paidUpValue);

							lifeEndorseChange.setPaidUpValue(paidUpValue);
							lifePolicySummary.setPaidUpValue(paidUpValue);
						}
					}
				}
			}
		}

		return lifePolicySummary;
	}

	// For Group Life
	private void calculateEndorseForGroup(LifeEndorseInfo lifeEndorseInfo) {
		int passedMonths = lifeEndorseInfo.getPassedMonth();
		double oldPremium = 0.0;
		double newPremium = 0.0;
		double differencePremium = 0.0;
		double additionalPre = 0.0;

		LifeEndorseChange lifeEndorseChange = null;
		for (LifeEndorseInsuredPerson lifeEndorseInsuredPerson : lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList()) {
			lifeEndorseChange = new LifeEndorseChange();
			if ((InsuredPersonEndorseStatus.REPLACE.equals(lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus()))
					|| InsuredPersonEndorseStatus.EDIT.equals(lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus())) {
				if (EndorsementUtil.isLifeSIIncreOnly(lifeEndorseInsuredPerson.getLifeEndorseItemList())) {
					lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeByItem(LifeEndorseItem.INCREASE_SI);
					passedMonths = lifeEndorseInfo.getPassedMonth();
					Utils.divide(lifeEndorseChange.getOldPremium(), 12);
					oldPremium = Utils.divide(lifeEndorseChange.getOldPremium(), 12);
					newPremium = Utils.divide(lifeEndorseChange.getNewPremium(), 12);
					differencePremium = newPremium - oldPremium;
					additionalPre = Utils.getTwoDecimalPoint(differencePremium * passedMonths);
					if (newPremium > oldPremium) {
						lifeEndorseChange.setEndorsePremium(additionalPre);
					}
				}
			} else if (InsuredPersonEndorseStatus.NEW.equals(lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus())) {
				lifeEndorseChange = lifeEndorseInsuredPerson.getLifeEndorseChangeList().get(0);
				newPremium = lifeEndorseChange.getNewPremium();
				lifeEndorseChange.setEndorsePremium(newPremium);
			}
		}
	}

	// For Group Checkers
	private LifeEndorseInfo checkChangesForGroup(LifeProposal lifeProposal) {
		List<LifeEndorseChange> lifeEndorseChangeList = new ArrayList<LifeEndorseChange>();
		List<LifeEndorseInsuredPerson> lifeEndorseInsuredPersonList = new ArrayList<LifeEndorseInsuredPerson>();
		LifeEndorseInsuredPerson lifeEndorseInsuredPerson = null;
		List<LifeBeneficiary> beneficiaryList = new ArrayList<LifeBeneficiary>();

		List<LifeBeneficiary> newBenificiariesList = new ArrayList<LifeBeneficiary>();
		List<LifeBeneficiary> oldBeneficiariesList = new ArrayList<LifeBeneficiary>();

		LifeEndorseInfo lifeEndorseInfo = null;
		List<ProposalInsuredPerson> newProposalInsuredPersonList = null;

		String policyNo = lifeProposal.getLifePolicy().getPolicyNo();
		LifePolicy lp = lifePolicyService.findLifePolicyByPolicyNo(policyNo);
		Date startDate = lp.getCommenmanceDate();
		Date endDate = lp.getActivedPolicyEndDate();
		int passedMonths = Utils.monthsBetween(startDate, endDate, false, true);
		lifeEndorseInfo = new LifeEndorseInfo();
		lifeEndorseInfo.setLifePolicyNo(lp.getPolicyNo());
		lifeEndorseInfo.setSourcePolicyReferenceNo(lp.getId());
		lifeEndorseInfo.setCommenmanceDate(lp.getCommenmanceDate());
		lifeEndorseInfo.setActivePolicyEndDate(lp.getActivedPolicyEndDate());
		lifeEndorseInfo.setEndorsementDate(lifeProposal.getSubmittedDate());
		lifeEndorseInfo.setProductId(lp.getPolicyInsuredPersonList().get(0).getProduct().getId());
		lifeEndorseInfo.setPassedMonth(passedMonths);
		lifeEndorseInfo.setMonthsOfPaymentType(lp.getPaymentType().getMonth());

		// For Organization Change
		if (Utils.isNotNull(lifeProposal.getOrganization()) && Utils.isNotNull(lp.getOrganization())) {
			if (!lifeProposal.getOrganization().getId().equals(lp.getOrganization().getId())) {
				LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.ORGANIZATION_CHANGE, lp.getOrganization().getId(), lifeProposal.getOrganization().getId());
				lifeEndorseChangeList.add(endorseChange);
			}
		}

		// For New InsuredPerson
		newProposalInsuredPersonList = EndorsementUtil.getNewProposalInsuredPersonItemList(lp.getPolicyInsuredPersonList(), lifeProposal.getProposalInsuredPersonList());
		if (newProposalInsuredPersonList != null) {
			for (ProposalInsuredPerson proposalInsuredPerson : newProposalInsuredPersonList) {
				lifeEndorseInsuredPerson = new LifeEndorseInsuredPerson();
				lifeEndorseInsuredPerson.setInsuredPersonCodeNo(proposalInsuredPerson.getInsPersonCodeNo());
				lifeEndorseInsuredPerson.setPeriodOfMonths(lifeProposal.getPeriodMonth());
				lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.NEW);
				lifeEndorseInsuredPersonList.add(lifeEndorseInsuredPerson);
				LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.INSUREDPERSON_NEW, 0 + "", proposalInsuredPerson.getProposedSumInsured() + "", 0,
						proposalInsuredPerson.getProposedPremium());
				lifeEndorseChangeList.add(endorseChange);

				for (InsuredPersonBeneficiaries newBeneficiaries : proposalInsuredPerson.getInsuredPersonBeneficiariesList()) {
					newBenificiariesList.add(new LifeBeneficiary(newBeneficiaries));
				}

				if (!newBenificiariesList.isEmpty()) {
					for (LifeBeneficiary lifeBeneficiary : newBenificiariesList) {
						endorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary.getBeneficiaryNo(), lifeBeneficiary.getPercentage(),
								BeneficiaryEndorseStatus.NEW, lifeBeneficiary.getInsuredPersonCodeNo(), lifeBeneficiary.getInitialId(), lifeBeneficiary.getIdNo(),
								lifeBeneficiary.getAge(), lifeBeneficiary.getName(), lifeBeneficiary.getIdType(), lifeBeneficiary.getResidentAddress(),
								lifeBeneficiary.getRelationship(), lifeBeneficiary.getGender()));
					}
				}

				for (LifeEndorseChange lifeEndorseChange : lifeEndorseChangeList) {
					lifeEndorseInsuredPerson.addLifeEndorseChange(lifeEndorseChange);
				}
				lifeEndorseInfo.addLifeEndorseInsuredPerson(lifeEndorseInsuredPerson);
				newBenificiariesList.clear();
				lifeEndorseChangeList.clear();
			}
		}

		// For Replace InsuredPerson
		for (ProposalInsuredPerson proposalInsuredPerson : lifeProposal.getProposalInsuredPersonList()) {
			for (PolicyInsuredPerson policyInsuredPerson : lp.getPolicyInsuredPersonList()) {
				if (proposalInsuredPerson.getInsPersonCodeNo().equals(policyInsuredPerson.getInsPersonCodeNo())) {
					lifeEndorseInsuredPerson = new LifeEndorseInsuredPerson();
					lifeEndorseInsuredPerson.setInsuredPersonCodeNo(proposalInsuredPerson.getInsPersonCodeNo());
					lifeEndorseInsuredPerson.setPeriodOfMonths(lifeProposal.getPeriodMonth());
					// InsuredPerson Change
					if (EndorsementUtil.isInsuredPersonChanges(proposalInsuredPerson, policyInsuredPerson)) {
						lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.REPLACE);
						lifeEndorseInsuredPersonList.add(lifeEndorseInsuredPerson);
						LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.REPLACE);
						lifeEndorseChangeList.add(lifeEndorseChange);
					}

					if (EndorsementUtil.isNRCNOChanges(proposalInsuredPerson, policyInsuredPerson)) {
						lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.NRCNO_CHANGE);
						lifeEndorseInsuredPersonList.add(lifeEndorseInsuredPerson);
						LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.NRCNO_CHANGE);
						lifeEndorseChangeList.add(lifeEndorseChange);
					}

					// Beneficiary Change
					if (EndorsementUtil.isBeneficiaryPersonChanges(proposalInsuredPerson, policyInsuredPerson)) {
						if (lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus() == null)
							lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.EDIT);

						for (InsuredPersonBeneficiaries newBeneficiaries : proposalInsuredPerson.getInsuredPersonBeneficiariesList()) {
							newBenificiariesList.add(new LifeBeneficiary(newBeneficiaries));
						}

						for (PolicyInsuredPersonBeneficiaries oldBeneficiaries : policyInsuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
							oldBeneficiariesList.add(new LifeBeneficiary(oldBeneficiaries));
						}

						// Add New Beneficiary
						beneficiaryList = EndorsementUtil.getNewBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
						if (!beneficiaryList.isEmpty()) {
							LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
							for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
								lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.NEW));
							}
							lifeEndorseChangeList.add(lifeEndorseChange);
						}
						// Delete Beneficiary
						beneficiaryList = EndorsementUtil.getRemoveBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
						if (!beneficiaryList.isEmpty()) {
							LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
							for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
								lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.DELETE));
							}
							lifeEndorseChangeList.add(lifeEndorseChange);
						}

						// Replace Beneficiary
						beneficiaryList = EndorsementUtil.getReplaceBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
						if (!beneficiaryList.isEmpty()) {
							LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
							for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
								lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.REPLACE));
							}
							lifeEndorseChangeList.add(lifeEndorseChange);
						}

					}

					// SI Increase
					if (EndorsementUtil.isSumInsuredChanges(proposalInsuredPerson, policyInsuredPerson)) {
						if (lifeEndorseInsuredPerson.getInsuredPersonEndorseStatus() == null)
							lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.EDIT);

						if (Utils.isNotNull(lifeProposal.getProposalInsuredPersonList()) && Utils.isNotNull(lp.getPolicyInsuredPersonList())) {
							if (proposalInsuredPerson.getProposedSumInsured() > policyInsuredPerson.getSumInsured()) {
								LifeEndorseChange change = new LifeEndorseChange(LifeEndorseItem.INCREASE_SI, policyInsuredPerson.getSumInsured() + "",
										proposalInsuredPerson.getProposedSumInsured() + "", policyInsuredPerson.getPremium(), proposalInsuredPerson.getProposedPremium());
								lifeEndorseChangeList.add(change);
							}
						}
					}

					if (!lifeEndorseChangeList.isEmpty()) {
						for (LifeEndorseChange change : lifeEndorseChangeList) {
							lifeEndorseInsuredPerson.addLifeEndorseChange(change);
						}
						lifeEndorseInfo.addLifeEndorseInsuredPerson(lifeEndorseInsuredPerson);
						beneficiaryList.clear();
						oldBeneficiariesList.clear();
						newBenificiariesList.clear();
						lifeEndorseChangeList.clear();
					}
				}
			}
		}
		return lifeEndorseInfo;
	}

	private double calculateInterest(double oneYearPremium, double oneMonthPremium, int passedMonth, int passedYear) {
		double interest = 0.0;
		double totalInterest = 0.0;
		// Calculate Interest for Year
		for (int i = 0; i < passedYear; i++) {
			interest = ((oneYearPremium + interest) * 6.25) / 100;
			totalInterest = totalInterest + interest;
		}
		// Calculate Interest for month
		if (passedMonth > 0) {
			// old code >> interest = ((interest + (oneMonthPremium *
			// passedMonth)) * 6.25) / 100;
			interest = ((interest + (oneMonthPremium * passedMonth)) * 6.25) / 100;
			interest = interest * passedMonth / 12;
			totalInterest = totalInterest + interest;
		}
		return totalInterest;
	}

	private LifeEndorseInfo checkChanges(LifeProposal newData) {

		List<LifeEndorseChange> lifeEndorseChangeList = new ArrayList<LifeEndorseChange>();
		LifeEndorseInsuredPerson lifeEndorseInsuredPerson = null;
		List<LifeBeneficiary> beneficiaryList = null;
		List<LifeBeneficiary> newBenificiariesList = new ArrayList<LifeBeneficiary>();
		List<LifeBeneficiary> oldBeneficiariesList = new ArrayList<LifeBeneficiary>();

		ProposalInsuredPerson newPerson = newData.getProposalInsuredPersonList().get(0);
		PolicyInsuredPerson oldPerson = newData.getLifePolicy().getPolicyInsuredPersonList().get(0);
		LifePolicy oldData = newData.getLifePolicy();
		Date startDate = oldData.getCommenmanceDate();
		Date endorseDate = newData.getSubmittedDate();
		int passedMonths = Utils.monthsBetween(startDate, endorseDate, false, true);
		LifeEndorseInfo lifeEndorseInfo = new LifeEndorseInfo();
		lifeEndorseInfo.setLifePolicyNo(oldData.getPolicyNo());
		lifeEndorseInfo.setSourcePolicyReferenceNo(oldData.getId());
		lifeEndorseInfo.setCommenmanceDate(oldData.getCommenmanceDate());
		lifeEndorseInfo.setActivePolicyEndDate(oldData.getActivedPolicyEndDate());
		lifeEndorseInfo.setEndorsementDate(newData.getSubmittedDate());
		lifeEndorseInfo.setProductId(oldData.getPolicyInsuredPersonList().get(0).getProduct().getId());
		lifeEndorseInfo.setPassedMonth(passedMonths);
		lifeEndorseInfo.setMonthsOfPaymentType(oldData.getPaymentType().getMonth());

		for (ProposalInsuredPerson proposalInsuredPerson : newData.getProposalInsuredPersonList()) {
			for (PolicyInsuredPerson policyInsuredPerson : oldData.getPolicyInsuredPersonList()) {
				if (proposalInsuredPerson.getInsPersonCodeNo().equals(policyInsuredPerson.getInsPersonCodeNo())) {
					lifeEndorseInsuredPerson = new LifeEndorseInsuredPerson();
					lifeEndorseInsuredPerson.setInsuredPersonCodeNo(proposalInsuredPerson.getInsPersonCodeNo());
					lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.EDIT);
					lifeEndorseInsuredPerson.setPeriodOfMonths(newData.getPeriodMonth() * newData.getPaymentType().getMonth());

					for (InsuredPersonBeneficiaries newBeneficiaries : proposalInsuredPerson.getInsuredPersonBeneficiariesList()) {
						newBenificiariesList.add(new LifeBeneficiary(newBeneficiaries));
					}

					for (PolicyInsuredPersonBeneficiaries oldBeneficiaries : policyInsuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
						oldBeneficiariesList.add(new LifeBeneficiary(oldBeneficiaries));
					}

					// InsuredPerson Change
					if (Utils.isNotNull(newData.getCustomer()) && Utils.isNotNull(oldData.getCustomer())) {
						if (!newData.getCustomer().getId().equals(oldData.getCustomer().getId())) {
							LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.INSUREDPERSON_NEW, oldData.getCustomerId(), newData.getCustomerId());
							lifeEndorseChangeList.add(endorseChange);
						}
					}

					// SI Increase
					if (Utils.isNotNull(newData.getProposalInsuredPersonList()) && Utils.isNotNull(oldData.getPolicyInsuredPersonList())) {
						if (newPerson.getProposedSumInsured() > oldPerson.getSumInsured()) {

							LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.INCREASE_SI, String.valueOf(oldPerson.getSumInsured()),
									String.valueOf(newPerson.getProposedSumInsured()), oldPerson.getPremium(), newPerson.getProposedPremium());
							lifeEndorseChangeList.add(endorseChange);
						}
					}

					// SI Decrease
					if (Utils.isNotNull(newData.getProposalInsuredPersonList()) && Utils.isNotNull(oldData.getPolicyInsuredPersonList())) {
						if (newPerson.getProposedSumInsured() < oldPerson.getSumInsured()) {
							isPaidPremium = newPerson.getIsPaidPremiumForPaidup();
							LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.DECREASE_SI, String.valueOf(oldPerson.getSumInsured()),
									String.valueOf(newPerson.getProposedSumInsured()), oldPerson.getPremium(), newPerson.getProposedPremium());
							lifeEndorseChangeList.add(endorseChange);
						}
					}

					// Term Increase
					if (Utils.isNotNull(newData.getProposalInsuredPersonList()) && Utils.isNotNull(oldData.getPolicyInsuredPersonList())) {
						if (newData.getPeriodMonth() > oldData.getPeriodOfInsurance()) {
							lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.TERM_CHANGE);
							LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.INCREASE_TERM, String.valueOf(newData.getLifePolicy().getPeriodMonth()),
									String.valueOf(newData.getPeriodMonth()), oldPerson.getPremium(), newPerson.getProposedPremium());
							lifeEndorseChangeList.add(endorseChange);
						}
					}

					// Term Decrease
					if (Utils.isNotNull(newData.getProposalInsuredPersonList()) && Utils.isNotNull(oldData.getPolicyInsuredPersonList())) {
						if (newData.getPeriodMonth() < oldData.getPeriodOfInsurance()) {
							lifeEndorseInsuredPerson.setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus.TERM_CHANGE);
							LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.DECREASE_TERM, String.valueOf(newData.getLifePolicy().getPeriodMonth()),
									String.valueOf(newData.getPeriodMonth()), oldPerson.getPremium(), newPerson.getProposedPremium());
							lifeEndorseChangeList.add(endorseChange);
						}
					}

					// Terminate Customer
					if (EndorsementStatus.TERMINATE_BY_CUSTOMER.equals(newPerson.getEndorsementStatus())) {
						LifeEndorseChange endorseChange = new LifeEndorseChange(LifeEndorseItem.TERMINATE_CUSTOMER, oldData.getSumInsured(), 0);
						lifeEndorseChangeList.add(endorseChange);
					}

					// Beneficiary Change
					// Add New Beneficiary
					beneficiaryList = EndorsementUtil.getNewBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
					if (!beneficiaryList.isEmpty()) {
						LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
						for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
							lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.NEW));
						}
						lifeEndorseChangeList.add(lifeEndorseChange);
					}
					beneficiaryList = Collections.EMPTY_LIST;

					// Delete Beneficiary
					beneficiaryList = EndorsementUtil.getRemoveBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
					if (!beneficiaryList.isEmpty()) {
						LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
						for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
							lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.DELETE));
						}
						lifeEndorseChangeList.add(lifeEndorseChange);
					}

					// Replace Beneficiary
					beneficiaryList = EndorsementUtil.getReplaceBeneficiaryItemList(oldBeneficiariesList, newBenificiariesList);
					if (!beneficiaryList.isEmpty()) {
						LifeEndorseChange lifeEndorseChange = new LifeEndorseChange(LifeEndorseItem.BENEFICIARY_CHANGE);
						for (LifeBeneficiary lifeBeneficiary : beneficiaryList) {
							lifeEndorseChange.addLifeEndorseBeneficiary(new LifeEndorseBeneficiary(lifeBeneficiary, BeneficiaryEndorseStatus.REPLACE));
						}
						lifeEndorseChangeList.add(lifeEndorseChange);
					}

					if (lifeEndorseChangeList.isEmpty()) {
						lifeEndorseInsuredPerson.setLifeEndorseChangeList(Collections.EMPTY_LIST);
					} else {
						for (LifeEndorseChange change : lifeEndorseChangeList) {
							lifeEndorseInsuredPerson.addLifeEndorseChange(change);
						}
					}
					lifeEndorseInfo.addLifeEndorseInsuredPerson(lifeEndorseInsuredPerson);
				}
			}
		}
		return lifeEndorseInfo;
	}

	private void calculateTermPremium(PolicyInsuredPerson policyInsuredPerson, int paymentTypeMonth) {
		double basicPremium = policyInsuredPerson.getPremium();
		double addOnPremium = policyInsuredPerson.getAddOnPremium();
		if (paymentTypeMonth > 0) {
			int paymentTerm = policyInsuredPerson.getLifePolicy().getPeriodMonth() / paymentTypeMonth;

			// TODO FIXME PSH --WHY Lastpayment to payment term
			// policyInsuredPerson.getLifePolicy().setLastPaymentTerm(paymentTerm);
			/* Basic Term Premium */
			double basicTermPremium = (paymentTypeMonth * basicPremium) / 12;
			policyInsuredPerson.setBasicTermPremium(basicTermPremium);
			/* AddOn Term Premium */
			double addOnTermPremium = (paymentTypeMonth * addOnPremium) / 12;
			policyInsuredPerson.setAddOnTermPremium(addOnTermPremium);
		} else {
			policyInsuredPerson.getLifePolicy().setLastPaymentTerm(1);
			/* Basic Term Premium For Lump Sum */
			double basicTermPremium = (policyInsuredPerson.getLifePolicy().getPeriodMonth() * basicPremium) / 12;
			policyInsuredPerson.setBasicTermPremium(basicTermPremium);
			/* AddOn Term Premium For Lump Sum */
			double addOnTermPremium = (policyInsuredPerson.getLifePolicy().getPeriodMonth() * addOnPremium) / 12;
			policyInsuredPerson.setAddOnTermPremium(addOnTermPremium);
		}
	}

	private void calculateTermPremium(LifeProposal lifeProposal) {
		int paymentType = lifeProposal.getPaymentType().getMonth();
		int paymentTerm = 0;
		double premium = 0, termPremium = 0, addOnPremium = 0;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			premium = pv.getProposedPremium();
			if (paymentType > 0) {
				paymentTerm = lifeProposal.getPeriodMonth() / paymentType;// lifeProposal.getPeriodOfYear()*12
				termPremium = (paymentType * premium) / 12;
				pv.setBasicTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum ***
				if (KeyFactorChecker.isPersonalAccident(lifeProposal.getProposalInsuredPersonList().get(0).getProduct()))
					termPremium = (premium / 12) * lifeProposal.getPeriodMonth();
				else
					termPremium = (lifeProposal.getPeriodOfYears() * premium);
				pv.setBasicTermPremium(termPremium);
			}
			lifeProposal.setPaymentTerm(paymentTerm);

			addOnPremium = pv.getAddOnPremium();
			if (paymentType > 0) {
				termPremium = (paymentType * addOnPremium) / 12;
				pv.setAddOnTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum AddOn Premium***
				termPremium = (lifeProposal.getPeriodMonth() * addOnPremium);
				pv.setAddOnTermPremium(termPremium);
			}
		}
	}

	private void resetDate(LifeProposal lifeProposal, Product product) {
		if (lifeProposal.getStartDate() != null) {
			Calendar cal = Calendar.getInstance();
			lifeProposal.setStartDate(new Date());
			cal.setTime(lifeProposal.getStartDate());
			cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
			lifeProposal.setEndDate(cal.getTime());
		}
	}

}
