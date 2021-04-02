package org.ace.insurance.life.renewal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.LifeEndorseInsuredPerson;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policyLog.service.interfaces.ILifePolicyTimeLineLogService;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.persistence.interfaces.ILifeProposalDAO;
import org.ace.insurance.life.proposalhistory.service.interfaces.ILifeProposalHistoryService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifePolicyService;
import org.ace.insurance.life.renewal.service.interfaces.IRenewalGroupLifeProposalService;
import org.ace.insurance.payment.AccountPayment;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.exception.CustomIDGeneratorException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "RenewalGroupLifeProposalService")
public class RenewalGroupLifeProposalService extends BaseService implements IRenewalGroupLifeProposalService {

	@Resource(name = "LifeProposalDAO")
	private ILifeProposalDAO lifeProposalDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "ProductService")
	private IProductService productService;

	@Resource(name = "RenewalGroupLifePolicyService")
	private IRenewalGroupLifePolicyService renewalGroupLifePolicyService;

	@Resource(name = "AcceptedInfoService")
	private IAcceptedInfoService acceptedInfoService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "CustomerService")
	private ICustomerService customerService;

	@Resource(name = "LifeProposalHistoryService")
	private ILifeProposalHistoryService lifeProposalHistoryService;

	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;

	@Resource(name = "LifeEndorsementService")
	private ILifeEndorsementService lifeEndorsementService;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "LifePolicyTimeLineLogService")
	private ILifePolicyTimeLineLogService lifePolicyTimeLineLogService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal renewalGroupLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status) {
		try {
			// calculatePremium(lifeProposal);
			String productCode = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductGroup().getProposalNoPrefix();
			String proposalNo = null;
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			proposalNo = customIDGenerator.getNextId(SystemConstants.GROUPLIFE_PROPOSAL_NO, productCode);

			// Custom ID GEN For Proposal Insured Person and Beneficiary
			for (ProposalInsuredPerson person : lifeProposal.getProposalInsuredPersonList()) {
				if (person.getInsPersonCodeNo() == null) {
					insPersonCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_CODENO, null);
					person.setInsPersonCodeNo(insPersonCodeNo);
				}
				if (isGroupLife(product)) {
					inPersonGroupCodeNo = customIDGenerator.getNextId(SystemConstants.LIFE_INSUREDPERSON_GROUP_CODENO, null);
					person.setInPersonGroupCodeNo(inPersonGroupCodeNo);
				}
				for (InsuredPersonBeneficiaries beneficiary : person.getInsuredPersonBeneficiariesList()) {
					if (beneficiary.getBeneficiaryNo() == null) {
						beneficiaryNo = customIDGenerator.getNextId(SystemConstants.LIFE_BENEFICIARY_NO, null);
						beneficiary.setBeneficiaryNo(beneficiaryNo);
					}
				}
			}
			lifeProposal.setProposalNo(proposalNo);

			LifeProposal mp = lifeProposalDAO.insert(lifeProposal);
			workFlowDTO.setReferenceNo(mp.getId());
			// FIXME CHECK REFTYPE
			workFlowDTO.setReferenceType(ReferenceType.GROUP_LIFE);
			workFlowDTOService.addNewWorkFlow(workFlowDTO);
			lifeProposalDAO.updateStatus(status, lifeProposal.getPortalId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new LifeProposal", e);
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeProposalDAO.insertSurvey(lifeSurvey);
			lifeProposalDAO.updateInsuPersonMedicalStatus(lifeSurvey.getLifeProposal().getProposalInsuredPersonList());
			lifeProposalDAO.addAttachment(lifeSurvey.getLifeProposal());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Survey", e);
		}
	}

	public LifeProposal calculatePremium(LifeProposal lifeProposal) {
		int paymentType = lifeProposal.getPaymentType().getMonth();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
			for (InsuredPersonKeyFactorValue insukf : pv.getKeyFactorValueList()) {
				keyfatorValueMap.put(insukf.getKeyFactor(), insukf.getValue());
			}

			Double premium = premiumCalculatorService.calculatePremium(keyfatorValueMap, product,
					new PremiumCalData(pv.getProposedSumInsured(), pv.getProposedSumInsured(), null, null));
			double termPremium = 0.0;
			if (premium != null && premium > 0) {
				int paymentTerm = 0;
				if (paymentType > 0) {
					paymentTerm = lifeProposal.getPeriodMonth() / paymentType;
					termPremium = (paymentType * premium) / 12;
					lifeProposal.setPaymentTerm(paymentTerm);
					pv.setBasicTermPremium(termPremium);
				} else {
					// *** Calculation for Lump Sum ***
					lifeProposal.setPaymentTerm(0);
					termPremium = (lifeProposal.getPeriodMonth() * premium) / 12;
					pv.setBasicTermPremium(termPremium);
				}
				pv.setProposedPremium(premium);
				lifeProposal.setPaymentTerm(paymentTerm);
			} else {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}

			List<InsuredPersonAddon> insuredPersonAddOnList = pv.getInsuredPersonAddOnList();
			if (insuredPersonAddOnList != null && !insuredPersonAddOnList.isEmpty()) {
				for (InsuredPersonAddon insuredPersonAddOn : insuredPersonAddOnList) {
					double addOnPremium = 0.0;
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

					insuredPersonAddOn.setProposedPremium(addOnPremium);
				}
			}
			double addOnPremium = pv.getAddOnPremium();
			if (paymentType > 0) {
				termPremium = (paymentType * addOnPremium) / 12;
				pv.setAddOnTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum AddOn Premium***
				termPremium = (lifeProposal.getPeriodMonth() * addOnPremium);
				pv.setAddOnTermPremium(termPremium);
			}
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);

			calculatePremium(lifeProposal);
			lifeProposalDAO.update(lifeProposal);
			if (acceptedInfo != null) {
				acceptedInfoService.addNewAcceptedInfo(acceptedInfo);
				lifeProposal.setSpecialDiscount(acceptedInfo.getDiscountPercent());
				lifeProposalDAO.updateStatus(status, lifeProposal.getPortalId());
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.addWorkFlowHistory(workFlowDTO);
			workFlowDTOService.deleteWorkFlowByRefNo(lifeProposal.getId());
			lifeProposalDAO.updateProposalStatus(ProposalStatus.DENY, lifeProposal.getId());
			// workFlowDTOService.updateWorkFlow(workFlowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal updateRenewalGroupLifeProposalEnquiry(LifeProposal lifeProposal) {
		try {
			lifeProposalHistoryService.addNewLifeProposalHistory(lifeProposal);
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			calculatePremium(lifeProposal);
			// Custom ID GEN For Proposal Insured Person and Beneficiary
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
			if (lifeProposal.getLifePolicy() != null) {
				org.ace.insurance.life.endorsement.LifeEndorseInfo info = null;
				info = lifeEndorsementService.updateLifeEndorseInfo(lifeProposal);
				;
				setEndorsementPremium(info, lifeProposal);
			}

			// Underwriting
			lifeProposalDAO.update(lifeProposal);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
		return lifeProposal;
	}

	private void initializeEndorsementPremium(LifeProposal lifeProposal) {
		for (ProposalInsuredPerson proposalPerson : lifeProposal.getProposalInsuredPersonList()) {
			proposalPerson.setEndorsementNetBasicPremium(0);
			proposalPerson.setInterest(0);
		}
	}

	private void setEndorsementPremium(LifeEndorseInfo lifeEndorseInfo, LifeProposal lifeProposal) {
		initializeEndorsementPremium(lifeProposal);
		for (LifeEndorseInsuredPerson endorsePerson : lifeEndorseInfo.getLifeEndorseInsuredPersonInfoList()) {
			for (ProposalInsuredPerson proposalPerson : lifeProposal.getProposalInsuredPersonList()) {
				if (endorsePerson.getInsuredPersonCodeNo().equals(proposalPerson.getInsPersonCodeNo()) && proposalPerson.getEndorsementStatus() != null) {
					proposalPerson.setEndorsementNetBasicPremium(endorsePerson.getEndorsePremium() + endorsePerson.getEndorseInterest());
					proposalPerson.setInterest(endorsePerson.getEndorseInterest());
				}
			}
		}
	}

	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status) {
		List<Payment> paymentList = new ArrayList<Payment>();
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			// reset insured person start/end date
			if (lifeProposal.getStartDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(lifeProposal.getStartDate());
				if ((KeyFactorChecker.isPersonalAccident(lifeProposal.getProposalInsuredPersonList().get(0).getProduct()))) {
					cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
				} else {
					cal.add(Calendar.YEAR, lifeProposal.getPeriodMonth());
				}
				lifeProposal.setEndDate(cal.getTime());
			}
			calculatePremium(lifeProposal);

			// create LifePolicy
			LifePolicy lifePolicy = new LifePolicy(lifeProposal);

			// recalculate payment term and term premium
			for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
				calculateTermPremium(person, lifePolicy.getPaymentType().getMonth());
			}
			double rate = 1.0;
			if (CurrencyUtils.isUSD(product.getCurrency())) {
				rate = paymentDAO.findActiveRate();
			}
			// create Payment
			Payment payment = new Payment();
			payment.setBank(paymentDTO.getBank());
			payment.setBankAccountNo(paymentDTO.getBankAccountNo());
			payment.setChequeNo(paymentDTO.getChequeNo());
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			// FIXME CHECK REFTYPE
			payment.setReferenceType(PolicyReferenceType.GROUP_LIFE_POLICY);
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountPercent());
			payment.setReceivedDeno(paymentDTO.getReceivedDeno());
			payment.setRefundDeno(paymentDTO.getRefundDeno());
			payment.setConfirmDate(new Date());
			payment.setPoNo(paymentDTO.getPoNo());
			payment.setAccountBank(paymentDTO.getAccountBank());
			payment.setBasicPremium(paymentDTO.getBasicPremium());
			payment.setAddOnPremium(paymentDTO.getAddOnPremium());
			payment.setCurrency(product.getCurrency());
			payment.setRate(rate);
			payment.setAmount(payment.getNetPremium());
			renewalGroupLifePolicyService.addNewLifePolicy(lifePolicy);

			payment.setReferenceNo(lifePolicy.getId());
			paymentList.add(payment);
			lifeProposalDAO.update(lifeProposal);
			for (Payment p : paymentList) {
				p = paymentDAO.insert(p);
			}
			// paymentList = paymentService.prePayment(paymentList);

			// prePaymentGroupLifeProposal(lifeProposal, paymentList, branch,
			// status);

			// if (paymentDTO.getPaymentChannel().equals(PaymentChannel.CHEQUE))
			// {
			// prePaymentLifeProposalTransfer(lifeProposal, paymentList,
			// branch);
			// }
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm a LifeProposal", e);
		}
		return paymentList;
	}

	private void calculateTermPremium(PolicyInsuredPerson policyInsuredPerson, int paymentTypeMonth) {
		double basicPremium = policyInsuredPerson.getPremium();
		double addOnPremium = policyInsuredPerson.getAddOnPremium();
		if (paymentTypeMonth > 0) {
			int paymentTerm = policyInsuredPerson.getLifePolicy().getPeriodMonth() / paymentTypeMonth;
			policyInsuredPerson.getLifePolicy().setLastPaymentTerm(paymentTerm);
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

	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch branch, String status) {
		try {
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			String accountCode = product.getProductGroup().getAccountCode();
			String currencyCode = CurrencyUtils.getCurrencyCode(product.getCurrency());
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			if (lifeProposal.getStartDate() == null) {
				lifeProposal.setStartDate(new Date());
				resetDate(lifeProposal, lifeProposal.getProposalInsuredPersonList().get(0).getProduct());
			}
			lifeProposalDAO.update(lifeProposal);
			LifePolicy lifePolicy = renewalGroupLifePolicyService.activateLifePolicy(lifeProposal);
			List<AgentCommission> agentCommissionList = null;
			if (lifeProposal.getAgent() != null) {
				agentCommissionList = new ArrayList<AgentCommission>();
				double totalCommission = 0.0;
				for (PolicyInsuredPerson pip : lifePolicy.getInsuredPersonInfo()) {
					double commissionPercent = pip.getProduct().getFirstCommission();
					if (commissionPercent > 0) {
						double totalPremium = pip.getBasicTermPremium() + pip.getAddOnTermPremium();
						double commission = (totalPremium / 100) * commissionPercent;
						totalCommission = totalCommission + commission;
					}
				}
				// FIXME CHECK REFTYPE
				agentCommissionList.add(new AgentCommission(lifePolicy.getId(), PolicyReferenceType.GROUP_LIFE_POLICY, lifePolicy.getAgent(), totalCommission, new Date()));
			}
			List<AccountPayment> accountPaymentList = new ArrayList<AccountPayment>();
			/* get AccountPayment of each policy */
			for (Payment payment : paymentList) {
				payment.setFromTerm(1);
				payment.setToTerm(1);
				accountPaymentList.add(new AccountPayment(accountCode, payment));
			}
			// FIXME
			// paymentService.preActivatePayment(accountPaymentList,
			// lifeProposal.getCustomerId(), branch, agentCommissionList, false,
			// currencyCode);
			lifeProposalDAO.updateStatus(RequestStatus.FINISHED.name(), lifeProposal.getId());

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to payment a LifeProposal ID : " + lifeProposal.getId(), e);
		}
	}

	public void resetDate(LifeProposal lifeProposal, Product product) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(lifeProposal.getStartDate());
		if ((KeyFactorChecker.isPersonalAccident(product))) {
			cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
		} else {
			cal.add(Calendar.YEAR, lifeProposal.getPeriodMonth());
		}
		lifeProposal.setEndDate(cal.getTime());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal findLifeProposalById(String id) {
		LifeProposal result = null;
		try {
			result = lifeProposalDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void issueLifeProposal(LifeProposal lifeProposal) {
		try {
			workFlowDTOService.deleteWorkFlowByRefNo(lifeProposal.getId());
			lifeProposalDAO.updateCompleteStatus(true, lifeProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal complete status", e);
		}
	}

}
