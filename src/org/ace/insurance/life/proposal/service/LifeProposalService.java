package org.ace.insurance.life.proposal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.common.CustomerStatus;
import org.ace.insurance.common.EndorsementUtil;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.utils.CurrencyUtils;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.LifeEndorseInsuredPerson;
import org.ace.insurance.life.endorsement.persistence.interfaces.ILifeEndorsementDAO;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyLog.LifePolicyIdLog;
import org.ace.insurance.life.policyLog.LifePolicyTimeLineLog;
import org.ace.insurance.life.policyLog.service.interfaces.ILifePolicyTimeLineLogService;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LPL001;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalTemp;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.persistence.interfaces.ILifeProposalDAO;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.life.proposalhistory.service.interfaces.ILifeProposalHistoryService;
import org.ace.insurance.medical.proposal.CustomerInfoStatus;
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
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.persistence.interfaces.ICustomerDAO;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.organization.persistence.interfaces.IOrganizationDAO;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonInfoDTO;
import org.ace.insurance.web.mobileforlife.LifeProposalDTO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.IDGen;
import org.ace.java.component.idgen.exception.CustomIDGeneratorException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "LifeProposalService")
public class LifeProposalService extends BaseService implements ILifeProposalService {

	@Resource(name = "LifeProposalDAO")
	private ILifeProposalDAO lifeProposalDAO;

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "LifeEndorsementDAO")
	private ILifeEndorsementDAO lifeEndorsementDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "ProductService")
	private IProductService productService;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lifePolicyService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "AcceptedInfoService")
	private IAcceptedInfoService acceptedInfoService;

	@Resource(name = "CustomerDAO")
	private ICustomerDAO customerDAO;

	@Resource(name = "OrganizationDAO")
	private IOrganizationDAO organizationDAO;

	@Resource(name = "LifePolicySummaryService")
	private ILifePolicySummaryService lifePolicySummaryService;

	@Resource(name = "LifeProposalHistoryService")
	private ILifeProposalHistoryService lifeProposalHistoryService;

	@Resource(name = "LifeEndorsementService")
	private ILifeEndorsementService lifeEndorsementService;

	@Resource(name = "LifePolicyTimeLineLogService")
	private ILifePolicyTimeLineLogService lifePolicyTimeLineLogService;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;

	@Resource(name = "AgentCommissionService")
	private IAgentCommissionService agentCommissionService;

	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;

	@Resource(name = "TlfProcessor")
	private ITlfProcessor tlfProcessor;
	@Resource(name = "CustomerService")
	public ICustomerService customerService;

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal calculatePremium(LifeProposal lifeProposal) {
		Double premium;
		Double premiumRate;
		double proposedSI;
		boolean isSeaMen;
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		isSeaMen = KeyFactorChecker.isSeaMenLife(product.getId());

		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			proposedSI = pv.getProposedSumInsured();
			/* set approved SI (after approved, edit SI from enquire) */
			if (pv.isApproved()) {
				pv.setApprovedSumInsured(proposedSI);
			}
			Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
			for (InsuredPersonKeyFactorValue insukf : pv.getKeyFactorValueList()) {
				keyfatorValueMap.put(insukf.getKeyFactor(), insukf.getValue());
			}
			if (isSportMan(product) || isSnakeBite(product) || isSeaMen(product)) {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product, new PremiumCalData(null, null, null, (double) pv.getUnit()));
				
				pv.setProposedSumInsured(pv.getUnit() * product.getSumInsuredPerUnit());
								
			} else {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product, new PremiumCalData(null, proposedSI, null, null));

			}
			if (KeyFactorChecker.isStudentLife(product.getId())) {
				// ratePremium = premium;
				// premium = (ratePremium *
				// lifeProposal.getPaymentType().getMonth());
				switch (lifeProposal.getPaymentType().getMonth()) {
					case 1:
						pv.setProposedPremium(premium * 12);
						break;
					case 6:
						pv.setProposedPremium(premium * 2);
						break;
					case 3:
						pv.setProposedPremium(premium * 4);
						break;
					default:
						pv.setProposedPremium(premium);
				}
				// pv.setProposedPremium(premium);
			} else {
				pv.setProposedPremium(premium);
			}
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
							new PremiumCalData(insuredPersonAddOn.getProposedSumInsured(), proposedSI, pv.getProposedPremium(), null));
					addOnPremiumRate = premiumCalculatorService.findPremiumRate(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn());
					insuredPersonAddOn.setPremiumRate(addOnPremiumRate);
					insuredPersonAddOn.setProposedPremium(addOnPremium);
				}
			}
		}
		return lifeProposal;
	}

	public void calculateTermPremium(LifeProposal lifeProposal) {
		int paymentType = lifeProposal.getPaymentType().getMonth();
		boolean isStudentLife = KeyFactorChecker.isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId());
		int paymentTerm = 0;
		double premium = 0, termPremium = 0, addOnPremium = 0;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			premium = pv.getProposedPremium();
			if (paymentType > 0) {
				if (isStudentLife) {
					paymentTerm = (lifeProposal.getPeriodOfYears() - 3) * 12 / paymentType;
				} else
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

	public void calculateAddOnPremium(ProposalInsuredPerson pv) {
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

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal addNewLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status) {
		try {
			// calculatePremium(lifeProposal);
			calculateTermPremium(lifeProposal);

			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			setProposalNo(lifeProposal);
			initialziedHomeRate(lifeProposal);
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
			if (KeyFactorChecker.isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId())) {
				Customer customer = lifeProposal.getCustomer();
				CustomerInfoStatus s = new CustomerInfoStatus();
				if (customer != null) {
					if (customer.getId() == null) {
						s.setStatusName(CustomerStatus.CONTRACTOR);
						customer.addCustomerInfoStatus(s);
						customer = customerService.addNewCustomer(customer);
					} else {
						/** Exiting Customer **/
						s.setStatusName(CustomerStatus.CONTRACTOR);
						customer.addCustomerInfoStatus(s);
						customer = customerService.updateCustomer(customer);
					}
					lifeProposal.setCustomer(customer);
				}
			}
			lifeProposal = lifeProposalDAO.insert(lifeProposal);
			if (lifeProposal.getId() != null && lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment().size() > 0) {
				String filePath = null;
				for (Attachment a : lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment()) {
					filePath = "/upload/life-proposal/" + lifeProposal.getId() + "/" + lifeProposal.getProposalInsuredPersonList().get(0).getId() + "/Birth_Certificate" + "/"
							+ a.getName();
					a.setFilePath(filePath);
				}
				updateLifeProposal(lifeProposal);
			}
			workFlowDTO.setReferenceNo(lifeProposal.getId());
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
	public LifeProposal renewalGroupLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status) {
		try {
			org.ace.insurance.life.endorsement.LifeEndorseInfo info = null;
			calculatePremium(lifeProposal);
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
			workFlowDTO.setReferenceType(ReferenceType.GROUP_LIFE);
			workFlowDTOService.addNewWorkFlow(workFlowDTO);
			lifeProposalDAO.updateStatus(status, lifeProposal.getPortalId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to renewal GroupLifeProposal.", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to renewal GroupLifeProposal.", e);
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to approve a LifeProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.addWorkFlowHistory(workFlowDTO);
			workFlowDTOService.deleteWorkFlowByRefNo(lifeProposal.getId());
			lifeProposalDAO.updateProposalStatus(ProposalStatus.DENY, lifeProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to reject a LifeProposal", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void informProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status) {
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

	private void calculateDiscount(PolicyInsuredPerson policyInsuredPerson, double discountPercent) {
		/* Reset Discount Basic Premium */
		double basicPremium = policyInsuredPerson.getPremium();
		double discountBasicPremium = basicPremium - Utils.getPercentOf(discountPercent, basicPremium);
		policyInsuredPerson.setPremium(discountBasicPremium);

		/* Reset Discount Basic Term Premium */
		double basicTermPremium = policyInsuredPerson.getBasicTermPremium();
		double discountBasicTermPremium = basicTermPremium - Utils.getPercentOf(discountPercent, basicTermPremium);
		policyInsuredPerson.setBasicTermPremium(discountBasicTermPremium);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status) {
		List<Payment> paymentList = new ArrayList<Payment>();
		try {
			/* exception for double confirm */
			LifePolicy policy = lifePolicyService.findPolicyByProposalId(lifeProposal.getId());
			if (policy != null) {
				throw new SystemException(ErrorCode.PROPOSAL_ALREADY_CONFIRMED, " Proposal is already confirmed.");
			}

			// boolean isRenewal = lifeProposal.getProposalType() ==
			// ProposalType.RENEWAL;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			boolean isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			boolean isFarmer = KeyFactorChecker.isFarmer(product);
			boolean isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			boolean isShortEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			boolean isGovernmentShortEndownment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
			boolean isSportMan = KeyFactorChecker.isSportMan(product);
			boolean isGroupLife = KeyFactorChecker.isGroupLife(product);
			boolean isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			boolean isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
			boolean isSeamanLife = KeyFactorChecker.isSeaMenLife(product.getId());
			PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_POLICY
					: isFarmer ? PolicyReferenceType.FARMER_POLICY
							: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
									: isShortEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
											:isGovernmentShortEndownment ? PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY
											: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
													: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
															: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_POLICY
																	: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																			:isSeamanLife ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			if (lifeProposal.getStartDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(lifeProposal.getStartDate());
				if (KeyFactorChecker.isPersonalAccident(product)) {
					cal.add(Calendar.MONTH, lifeProposal.getPeriodOfInsurance());
				} else {
					cal.add(Calendar.YEAR, lifeProposal.getPeriodOfYears());
				}
				lifeProposal.setEndDate(cal.getTime());
			}
			calculatePremium(lifeProposal);
			initialziedHomeRate(lifeProposal);
			// create LifePolicy
			LifePolicy lifePolicy = new LifePolicy(lifeProposal);
			// recalculate payment term and term premium
			if (!isFarmer && !isPersonalAccident) {
				for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
					calculateTermPremium(person, lifePolicy.getPaymentType().getMonth());
				}
			}

			// current rate for HomeAmount
			double rate = 1.0;
			if (CurrencyUtils.isUSD(product.getCurrency())) {
				rate = paymentDAO.findActiveRate();
			}

			// create Payment

			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, product.getId().trim());
			Payment payment = new Payment();
			payment.setPaymentType(lifePolicy.getPaymentType());
			payment.setInvoiceNo(invoiceNo);
			payment.setBank(paymentDTO.getBank());
			payment.setBankAccountNo(paymentDTO.getBankAccountNo());
			payment.setChequeNo(paymentDTO.getChequeNo());
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			payment.setReferenceType(referenceType);
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountAmount());
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
			payment.setFromTerm(1);
			payment.setToTerm(1);

			lifePolicyService.addNewLifePolicy(lifePolicy);
			payment.setReferenceNo(lifePolicy.getId());
			paymentList.add(payment);
			lifeProposalDAO.update(lifeProposal);

			for (Payment p : paymentList) {
				p = paymentDAO.insert(p);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm a LifeProposal", e);
		}
		return paymentList;
	}

	/*
	 * @Purpose SkipPaymentTLF in confirm stage for farmer (non-Javadoc)
	 * 
	 * @see
	 * org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService#
	 * confirmSkipPaymentTLFLifeProposal(org.ace.insurance.life.proposal.
	 * LifeProposal, org.ace.insurance.common.WorkFlowDTO,
	 * org.ace.insurance.common.PaymentDTO,
	 * org.ace.insurance.system.common.branch.Branch, java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> confirmSkipPaymentTLFLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status) {
		List<Payment> paymentList = new ArrayList<Payment>();
		try {
			/* exception for double confirm */
			LifePolicy policy = lifePolicyService.findPolicyByProposalId(lifeProposal.getId());
			if (policy != null) {
				throw new SystemException(ErrorCode.PROPOSAL_ALREADY_CONFIRMED, " Proposal is already confirmed.");
			}

			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			PolicyReferenceType referenceType = PolicyReferenceType.STUDENT_LIFE_POLICY;
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			if (lifeProposal.getStartDate() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(lifeProposal.getStartDate());
				cal.add(Calendar.YEAR, lifeProposal.getPeriodOfYears());
				lifeProposal.setEndDate(cal.getTime());
			}
			// else {
			// lifeProposal.setStartDate(new Date());
			// resetDate(lifeProposal, product);
			// }
			calculatePremium(lifeProposal);
			initialziedHomeRate(lifeProposal);
			// create LifePolicy
			LifePolicy lifePolicy = new LifePolicy(lifeProposal);
			// recalculate payment term and term premium
			for (PolicyInsuredPerson person : lifePolicy.getPolicyInsuredPersonList()) {
				calculateTermPremium(person, lifePolicy.getPaymentType().getMonth());
			}

			// current rate for HomeAmount
			double rate = 1.0;
			if (CurrencyUtils.isUSD(product.getCurrency())) {
				rate = paymentDAO.findActiveRate();
			}
			// create Payment

			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.LIFE_INVOICE_NO, product.getId().trim());
			Payment payment = new Payment();
			payment.setPaymentType(lifePolicy.getPaymentType());
			payment.setInvoiceNo(invoiceNo);
			payment.setBank(paymentDTO.getBank());
			payment.setBankAccountNo(paymentDTO.getBankAccountNo());
			payment.setChequeNo(paymentDTO.getChequeNo());
			payment.setPaymentChannel(paymentDTO.getPaymentChannel());
			payment.setReferenceType(referenceType);
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountAmount());
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
			payment.setFromTerm(1);
			payment.setToTerm(1);
			lifePolicyService.addNewLifePolicy(lifePolicy);
			lifePolicy = lifePolicyService.activateLifePolicy(lifeProposal);
			String policyNo = lifePolicy.getPolicyNo();
			String lifePolicyId = lifePolicy.getId();
			payment.setReferenceNo(lifePolicy.getId());
			paymentList.add(payment);
			lifeProposalDAO.update(lifeProposal);

			for (Payment p : paymentList) {
				p = paymentDAO.insert(p);
			}
			paymentService.activatePayment(paymentList, policyNo, rate);

			/* Agent Commission */
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();

			if (lifeProposal.getAgent() != null) {
				agentCommissionList = new ArrayList<AgentCommission>();
				payment = paymentList.stream().filter(p -> lifePolicyId.equals(p.getReferenceNo())).findAny().orElse(null);
				String receiptNo = payment.getReceiptNo();
				double netPremium = payment.getNetAgentComPremium();
				double commissionPercent = product.getFirstCommission();
				double agentComm = Utils.getPercentOf(commissionPercent, netPremium);
				agentCommissionList.add(new AgentCommission(lifePolicy.getId(), referenceType, lifePolicy.getPolicyNo(), lifePolicy.getCustomer(), lifePolicy.getOrganization(),
						lifePolicy.getBranch(), lifePolicy.getAgent(), agentComm, new Date(), receiptNo, netPremium, commissionPercent, AgentCommissionEntryType.UNDERWRITING, rate,
						(rate * agentComm), product.getCurrency(), (rate * netPremium)));
				agentCommissionService.addNewAgentCommisssion(agentCommissionList);
			}

			/* TLF */
			List<TlfData> dataList = new ArrayList<>();
			TlfData tlfData = null;
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
			if (lifeProposal.getOrganization() != null) {
				int activePolicyCount = lifeProposal.getOrganization().getActivePolicy() + 1;
				organizationDAO.updateActivePolicy(activePolicyCount, lifeProposal.getCustomerId());
				if (lifeProposal.getOrganization().getActivedDate() == null) {
					organizationDAO.updateActivedPolicyDate(new Date(), lifeProposal.getCustomerId());
				}
			}

			/* Entry to LifePolicyTimeLineLog (Underwriting) */
			if (lifeProposal.getProposalType().equals(ProposalType.UNDERWRITING)) {
				LifePolicyTimeLineLog timeLineLog = new LifePolicyTimeLineLog(lifePolicy.getPolicyNo(), lifePolicy.getActivedPolicyStartDate(),
						lifePolicy.getActivedPolicyEndDate(), lifePolicy.getCommenmanceDate());
				lifePolicyTimeLineLogService.addLifePolicyTimeLineLog(timeLineLog);

				LifePolicyIdLog idLog = new LifePolicyIdLog(null, lifePolicy.getId(), lifePolicy.getLifeProposal().getId(), ProposalType.UNDERWRITING, timeLineLog);
				lifePolicyTimeLineLogService.addLifePolicyIdLog(idLog);
			}

			/* workflow */
			lifeProposalDAO.updateStatus(RequestStatus.FINISHED.name(), lifeProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirmSkipPaymentTLF a LifeProposal ID : " + lifeProposal.getId(), e);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch userBranch, String status) {
		try {
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			boolean isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			boolean isFarmer = KeyFactorChecker.isFarmer(product);
			boolean isSportMan = KeyFactorChecker.isSportMan(product);
			boolean isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			boolean isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			boolean isGovernmentShortTermEndownment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
			boolean isSeamanLife = KeyFactorChecker.isSeaMenLife(product.getId());
			boolean isGroupLife = KeyFactorChecker.isGroupLife(product);
			boolean isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			boolean isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
			PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_POLICY
					: isFarmer ? PolicyReferenceType.FARMER_POLICY
							: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
									: isShortTermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
											 : isGovernmentShortTermEndownment ? PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY
											: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
													: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
															: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_POLICY
																	: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																			:isSeamanLife ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;

			if (isSportMan && lifeProposal.getComplete()) {
				// check is it come from sportManTravelAbroad Policy
				workFlowDTOService.deleteWorkFlowByRefNo(paymentList.get(0).getInvoiceNo());
			} else {
				workFlowDTOService.updateWorkFlow(workFlowDTO);
			}

			double rate = paymentList.get(0).getRate();
			if (lifeProposal.getStartDate() == null) {
				lifeProposal.setStartDate(new Date());
				resetDate(lifeProposal, product);
			}
			lifeProposalDAO.update(lifeProposal);

			LifePolicy lifePolicy = null;
			if (isSportMan && lifeProposal.getComplete()) {
				lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
			} else {
				lifePolicy = lifePolicyService.activateLifePolicy(lifeProposal);
			}
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
						lifePolicy.getBranch(), lifePolicy.getAgent(), agentComm, new Date(), receiptNo, netPremium, commissionPercent, AgentCommissionEntryType.UNDERWRITING, rate,
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
			tlfProcessor.handleTlfProcess(dataList, userBranch);

			/* update ActivePolicy Count in CustomerTable */
			if (lifeProposal.getCustomer() != null) {
				int activePolicyCount = lifeProposal.getCustomer().getActivePolicy() + 1;
				customerDAO.updateActivePolicy(activePolicyCount, lifeProposal.getCustomerId());
				if (lifeProposal.getCustomer().getActivedDate() == null) {
					customerDAO.updateActivedPolicyDate(new Date(), lifeProposal.getCustomerId());
				}
			}
			if (lifeProposal.getOrganization() != null) {
				int activePolicyCount = lifeProposal.getOrganization().getActivePolicy() + 1;
				organizationDAO.updateActivePolicy(activePolicyCount, lifeProposal.getCustomerId());
				if (lifeProposal.getOrganization().getActivedDate() == null) {
					organizationDAO.updateActivedPolicyDate(new Date(), lifeProposal.getCustomerId());
				}
			}

			/* Entry to LifePolicyTimeLineLog (Underwriting) */
			if (lifeProposal.getProposalType().equals(ProposalType.UNDERWRITING)) {
				LifePolicyTimeLineLog timeLineLog = new LifePolicyTimeLineLog(lifePolicy.getPolicyNo(), lifePolicy.getActivedPolicyStartDate(),
						lifePolicy.getActivedPolicyEndDate(), lifePolicy.getCommenmanceDate());
				lifePolicyTimeLineLogService.addLifePolicyTimeLineLog(timeLineLog);

				LifePolicyIdLog idLog = new LifePolicyIdLog(null, lifePolicy.getId(), lifePolicy.getLifeProposal().getId(), ProposalType.UNDERWRITING, timeLineLog);
				lifePolicyTimeLineLogService.addLifePolicyIdLog(idLog);
			}

			/* workflow */
			lifeProposalDAO.updateStatus(RequestStatus.FINISHED.name(), lifeProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to payment a LifeProposal ID : " + lifeProposal.getId(), e);
		}

	}

	public void resetDate(LifeProposal lifeProposal, Product product) {
		if (lifeProposal.getStartDate() != null) {
			Calendar cal = Calendar.getInstance();
			lifeProposal.setStartDate(new Date());
			cal.setTime(lifeProposal.getStartDate());
			cal.add(Calendar.MONTH, lifeProposal.getPeriodMonth());
			lifeProposal.setEndDate(cal.getTime());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal updateLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO) {
		try {
			lifeProposalHistoryService.addNewLifeProposalHistory(lifeProposal);
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			calculatePremium(lifeProposal);
			calculateTermPremium(lifeProposal);
			initialziedHomeRate(lifeProposal);
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
				setEndorsementPremium(info, lifeProposal);
			}

			// Underwriting
			lifeProposalDAO.update(lifeProposal);
			if (workFlowDTO != null)
				workFlowDTOService.updateWorkFlow(workFlowDTO);

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void issueLifeProposal(LifeProposal lifeProposal) {
		try {
			workFlowDTOService.deleteWorkFlowByRefNo(lifeProposal.getId());
			lifeProposalDAO.updateCompleteStatus(true, lifeProposal.getId());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to issue a LifeProposal.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal overWriteLifeProposal(LifeProposal lifeProposal) {
		try {
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			lifeProposalHistoryService.addNewLifeProposalHistory(lifeProposalDAO.findById(lifeProposal.getId()));
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
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteLifeProposal(LifeProposal lifeProposal) {
		try {
			lifeProposalDAO.delete(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a LifeProposal", e);
		}

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
	public LifeProposal findLifePortalById(String id) {
		LifeProposal result = null;
		try {
			result = lifeProposalDAO.findLifePortalById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal Portal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeProposal> findLifeProposal(List<String> proposalIdList) {
		List<LifeProposal> result = null;
		try {
			result = lifeProposalDAO.findByIdList(proposalIdList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeProposal.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeProposal> findByDate(Date startDate, Date endDate) {
		List<LifeProposal> result = null;
		try {
			result = lifeProposalDAO.findByDate(startDate, endDate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO) {
		try {
			LifeProposal lifeProposal = lifeSurvey.getLifeProposal();
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			lifeProposalDAO.insertSurvey(lifeSurvey);
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Survey", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeProposal> findAllLifeProposal() {
		List<LifeProposal> result = null;
		try {
			result = lifeProposalDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of LifeProposal)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal updateLifeProposal(LifeProposal lifeProposal) {
		try {
			// lifeProposalHistoryService.addNewLifeProposalHistory(lifeProposal);
			calculatePremium(lifeProposal);
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
		return lifeProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPL001> findLifeProposalByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList) {
		List<LPL001> result = null;
		try {
			result = lifeProposalDAO.findByEnquiryCriteria(criteria, productList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeProposal by Enquiry Criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProposalInsuredPerson findProposalInsuredPersonById(String id) {
		ProposalInsuredPerson result = null;
		try {
			result = lifeProposalDAO.findProposalInsuredPersonById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ProposalInsuredPerson (ID : " + id + ")", e);
		}
		return result;
	}

	public double calculateInterest(double oneYearPremium, double oneMonthPremium, int passedMonth, int passedYear) {
		double interest = 0.0;
		double totalInterest = 0.0;
		// Calculate Interest for Year
		for (int i = 0; i < passedYear; i++) {
			interest = ((oneYearPremium + interest) * 6.25) / 100;
			totalInterest = totalInterest + interest;
		}
		// Calculate Interest for month
		if (passedMonth > 0) {
			// interest = ((oneYearPremium + interest) * 6.25) / 100;
			// interest = interest * passedMonth / 12;
			interest = ((interest + (oneMonthPremium * passedMonth)) * 6.25) / 100;
			// for year
			interest = interest * passedMonth / 12;
			totalInterest = totalInterest + interest;
		}
		return totalInterest;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeSurvey findSurveyByProposalId(String proposalId) {
		LifeSurvey result = null;
		try {
			result = lifeProposalDAO.findSurveyByProposalId(proposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Proposal Survey Date)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePayment(LifePolicy lifePolicy, WorkFlowDTO workFlowDTO) {
		try {

			workFlowDTOService.updateWorkFlow(workFlowDTO);
			String insPersonCodeNo = null;
			String inPersonGroupCodeNo = null;
			String beneficiaryNo = null;
			Product product = lifePolicy.getLifeProposal().getProposalInsuredPersonList().get(0).getProduct();
			// calculatePremium(lifeProposal);
			// Custom ID GEN For Proposal Insured Person and Beneficiary
			for (ProposalInsuredPerson person : lifePolicy.getLifeProposal().getProposalInsuredPersonList()) {
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
			lifeProposalDAO.update(lifePolicy.getLifeProposal());
			lifePolicyService.deleteLifePolicy(lifePolicy);
			List<Payment> paymentList = paymentService.findByPolicy(lifePolicy.getId());
			paymentService.deletePayments(paymentList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete payment.", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PolicyInsuredPerson> findPolicyInsuredPersonByParameters(Name name, String idNo, String fatherName, Date dob) {
		List<PolicyInsuredPerson> results = null;
		try {
			results = lifeProposalDAO.findPolicyInsuredPersonByParameters(name, idNo, fatherName, dob);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find PolicyInsuredPerson.", e);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findStatusById(String id) {
		String result = null;
		try {
			result = lifeProposalDAO.findStatusById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePortalStatus(String status, String id) {
		try {
			lifeProposalDAO.updateStatus(status, id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Status (Status : " + status + ")", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal findLifeProposalByProposalNo(String proposalNo) {
		LifeProposal result = null;
		try {
			result = lifeProposalDAO.findByProposalNo(proposalNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (No : " + proposalNo + ")", e);
		}
		return result;
	}

	// insuredPersonCodeNo migratiion
	// delete after migration
	public void updateCodeNo(List<ProposalInsuredPerson> proposalPersonList, List<PolicyInsuredPerson> policyPersonList, List<InsuredPersonBeneficiaries> proposalBeneList,
			List<PolicyInsuredPersonBeneficiaries> policyBeneList, List<IDGen> idGenList) {

		// update proposal insured person
		lifeProposalDAO.updateProposalPersonCode(proposalPersonList);

		// update policy insured person
		lifePolicyDAO.updatePolicyPersonCode(policyPersonList);

		// update proposal beneficiary person
		lifeProposalDAO.updateProposalBeneficiaryCode(proposalBeneList);

		// update policy beneficiary person
		lifePolicyDAO.updatePolicyBeneficiaryCode(policyBeneList);

		// update id gen
		for (IDGen idGen : idGenList) {
			customIDGenerator.updateIDGen(idGen);
		}
	}

	private void setProposalNo(LifeProposal lifeProposal) {
		String proposalNo = null;
		String proposalIdGen = null;
		String productId = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId().trim();
		if (KeyFactorChecker.isStudentLife(productId)) {
			proposalIdGen = SystemConstants.STUDENT_LIFE_PROPOSAL_NO;
		} else if (KeyFactorChecker.isPublicTermLife(productId)) {
			proposalIdGen = SystemConstants.PUBLIC_TERM_LIFE_PROPOSAL_NO;
		} else {
			proposalIdGen = SystemConstants.LIFE_PROPOSAL_NO;
		}
		proposalNo = customIDGenerator.getCustomNextId(proposalIdGen, productId);
		lifeProposal.setProposalNo(proposalNo);
	}

	private void initialziedHomeRate(LifeProposal lifeProposal) {
		double rate = 1.0;
		if (KeyFactorChecker.isPersonalAccidentUSD(lifeProposal.getProposalInsuredPersonList().get(0).getProduct())) {
			rate = paymentDAO.findActiveRate();
		}
		lifeProposal.setCurrencyRate(rate);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateLifeProposalAttachment(LifeProposal lifeProposal) {
		try {
			lifeProposalDAO.update(lifeProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to Add Attachment : " + lifeProposal.getId(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean findOverMaxSIByMotherNameAndNRCAndInsuNameAndNRC(LifeProposal lifeProposal, InsuredPersonInfoDTO insuredPersonInfoDTO) {
		boolean result = false;
		try {
			result = lifeProposalDAO.findOverMaxSIByMotherNameAndNRCAndInsuNameAndNRC(lifeProposal, insuredPersonInfoDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find OverMaxSI By MotherName And NRC And InsuName And NRC", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<LifeProposalDTO> findLifeProposal() {
		List<LifeProposalDTO> result=null;
		try {
			result =lifeProposalDAO.findLifeProposal();
		}catch(DAOException e) {
			throw new SystemException(e.getErrorCode(), "Can't find MedicalProposal",e);
		}
		return result;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposalTemp findLifeProposalByProposalNoFromTerm(String proposalNo) {
		LifeProposalTemp result = null;
		try {
			result = lifeProposalDAO.findByProposalNoFromTerm(proposalNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a LifeProposal (No : " + proposalNo + ")", e);
		}
		return result;
	}

}
