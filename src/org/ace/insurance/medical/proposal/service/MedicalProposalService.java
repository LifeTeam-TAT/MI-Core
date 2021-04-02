package org.ace.insurance.medical.proposal.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.common.CustomerStatus;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalStatus;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO;
import org.ace.insurance.medical.productprocess.persistence.interfaces.IProductProcessDAO;
import org.ace.insurance.medical.proposal.CustomerInfoStatus;
import org.ace.insurance.medical.proposal.MP001;
import org.ace.insurance.medical.proposal.MedicalKeyFactorValue;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAddOn;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonBeneficiaries;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.medical.proposal.persistence.interfaces.IMedicalProposalDAO;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.medical.surveyquestion.persistence.interfaces.ISurveyQuestionDAO;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TlfData;
import org.ace.insurance.payment.persistence.interfacs.IAgentCommissionDAO;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.payment.service.interfaces.ITlfDataProcessor;
import org.ace.insurance.payment.service.interfaces.ITlfProcessor;
import org.ace.insurance.product.PremiumCalData;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.persistence.interfaces.IProductDAO;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.persistence.interfaces.ICustomerDAO;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.mobileforhealth.MedicalProposalDTO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.exception.CustomIDGeneratorException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "MedicalProposalService")
public class MedicalProposalService extends BaseService implements IMedicalProposalService {

	@Resource(name = "CustomerDAO")
	private ICustomerDAO customerDAO;

	@Resource(name = "MedicalProposalDAO")
	private IMedicalProposalDAO medicalProposalDAO;

	@Resource(name = "ProcessDAO")
	private IProcessDAO mProcessDAO;

	@Resource(name = "ProductDAO")
	private IProductDAO productDAO;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "AgentCommissionDAO")
	private IAgentCommissionDAO agentCommissionDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "MedicalPolicyService")
	private IMedicalPolicyService medicalPolicyService;

	@Resource(name = "CustomerService")
	private ICustomerService customerService;
	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;

	@Resource(name = "ProductService")
	private IProductService productService;

	@Resource(name = "SurveyQuestionDAO")
	private ISurveyQuestionDAO surveyQuestionDAO;

	@Resource(name = "ProductProcessDAO")
	private IProductProcessDAO productProcessDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Resource(name = "CurrencyService")
	private ICurrencyService currencyService;

	@Resource(name = "AcceptedInfoService")
	private IAcceptedInfoService acceptedInfoService;

	@Resource(name = "TlfDataProcessor")
	private ITlfDataProcessor tlfDataProcessor;

	@Resource(name = "TlfProcessor")
	private ITlfProcessor tlfProcessor;

	/** Underwriting calculate premium */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal calculatePremium(MedicalProposal medicalProposal) {
		double premium, addOnPremium, riskSIperUnit;
		int paymentTerm = 0;
		int paymentType = medicalProposal.getPaymentType().getMonth();
		int periodOfMonth = medicalProposal.getPeriodMonth();
		/* if not lumpSum payment */
		if (paymentType > 0) {
			paymentTerm = periodOfMonth / paymentType;
			if ((periodOfMonth % paymentType) > 0) {
				paymentTerm = paymentTerm + 1;
			}
		} else {
			/* lumpSum payment */
			paymentTerm = 1;
		}
		medicalProposal.setPaymentTerm(paymentTerm);
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			try {
				Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
				for (MedicalKeyFactorValue keyfactor : person.getKeyFactorValueList()) {
					keyfatorValueMap.put(keyfactor.getKeyFactor(), keyfactor.getValue());
				}
				premium = premiumCalculatorService.calculatePremium(keyfatorValueMap, person.getProduct(), new PremiumCalData(null, null, null, (double) person.getUnit()));
				person.setPremium(premium * paymentTerm);

				riskSIperUnit = person.getProduct().getSumInsuredPerUnit();
				person.setSumInsured(person.getUnit() * riskSIperUnit);

				for (MedicalProposalInsuredPersonAddOn insuredPersonAddOn : person.getInsuredPersonAddOnList()) {
					try {
						Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
						for (MedicalKeyFactorValue kfv : insuredPersonAddOn.getKeyFactorValueList()) {
							addOnKeyfatorValueMap.put(kfv.getKeyFactor(), kfv.getValue());
						}
						addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn(),
								new PremiumCalData(null, null, person.getPremium(), (double) insuredPersonAddOn.getUnit()));
						insuredPersonAddOn.setPremium(addOnPremium * paymentTerm);
						riskSIperUnit = insuredPersonAddOn.getAddOn().getSumInsuredPerUnit();
						insuredPersonAddOn.setSumInsured(insuredPersonAddOn.getUnit() * riskSIperUnit);
					} catch (SystemException e) {
						if (e.getSource() == null)
							e.setSource("Insured Person - " + person.getFullName() + ", AddOn - " + insuredPersonAddOn.getAddOn().getName());
						throw e;
					}
				}
			} catch (SystemException e) {
				if (e.getSource() == null)
					e.setSource("Insured Person - " + person.getFullName() + ", Product - " + person.getProduct().getName());
				throw e;
			}
		}
		return medicalProposal;
	}

	/** Underwriting */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal addNewMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO) {
		try {
			resetEndDate(medicalProposal);
			calculateTermPremium(medicalProposal);
			setProposalNo(medicalProposal);
			setCodeNo(medicalProposal);
			handleCustomer(medicalProposal);
			medicalProposal = medicalProposalDAO.insert(medicalProposal);
			workFlowDTO.setReferenceNo(medicalProposal.getId());
			workFlowService.addNewWorkFlow(workFlowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalProposal", e);
		}
		return medicalProposal;
	}

	/** Confirm Edit, Enquire */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal updateMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO) {
		try {
			calculateTermPremium(medicalProposal);
			setCodeNo(medicalProposal);
			handleCustomer(medicalProposal);
			medicalProposalDAO.update(medicalProposal);
			if (workFlowDTO != null) {
				workFlowService.updateWorkFlow(workFlowDTO, workFlowDTO.getWorkflowTask());
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalProposal", e);
		} catch (CustomIDGeneratorException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalProposal for customer  id generator ", e);
		} catch (SystemException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new MedicalProposal ", e);
		}
		return medicalProposal;
	}

	/** Survey */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurvey(MedicalSurvey medicalSurvey, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			medicalProposalDAO.insertSurvey(medicalSurvey);
			medicalProposalDAO.update(medicalSurvey.getMedicalProposal());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Survey", e);
		}
	}

	/** Approve */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void approveMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO) {
		try {
			medicalProposalDAO.update(medicalProposal);
			workFlowDTOService.updateWorkFlow(workFlowDTO);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a LifeProposal", e);
		}
	}

	/** Inform */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void informProposal(String medicalProposalId, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo) {
		try {
			workFlowService.updateWorkFlow(workFlowDTO);
			if (acceptedInfo != null) {
				acceptedInfo.setReferenceType(workFlowDTO.getReferenceType());
				acceptedInfoService.addNewAcceptedInfo(acceptedInfo);
				medicalProposalDAO.updateSpecialDiscount(medicalProposalId, acceptedInfo.getDiscountPercent());
				if (acceptedInfo.getNcbPremium() > 0)
					medicalProposalDAO.updateNCBAmount(medicalProposalId, acceptedInfo.getNcbPremium());
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to inform  MedicalProposal", e);
		}
	}

	/** Confirm */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> confirmMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO) {
		List<Payment> paymentList = new ArrayList<Payment>();
		try {

			Currency cur = currencyService.findCurrencyByCurrencyCode("KYT");
			workFlowService.updateWorkFlow(workFlowDTO);
			calculateTermPremium(medicalProposal);
			resetEndDate(medicalProposal);

			/* create MedicalPolicy */
			Product product = medicalProposal.getMedicalProposalInsuredPersonList().get(0).getProduct();
			MedicalPolicy medicalPolicy = new MedicalPolicy(medicalProposal);

			/* create Payment */
			Payment payment = new Payment();
			String invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_INVOICE_NO, product.getId());
			payment.setInvoiceNo(invoiceNo);
			payment.setCurrency(cur);
			payment.setPaymentType(paymentDTO.getPaymentType());
			payment.setReferenceType(paymentDTO.getReferenceType());
			payment.setBasicPremium(paymentDTO.getBasicPremium());
			payment.setAddOnPremium(paymentDTO.getAddOnPremium());
			payment.setStampFees(paymentDTO.getStampFees());
			payment.setServicesCharges(paymentDTO.getServicesCharges());
			payment.setSpecialDiscount(paymentDTO.getDiscountAmount());
			payment.setNcbPremium(paymentDTO.getNcbPremium());
			payment.setReceivedDeno(paymentDTO.getReceivedDeno());
			payment.setRefundDeno(paymentDTO.getRefundDeno());
			payment.setConfirmDate(new Date());
			payment.setAmount(payment.getNetPremium());
			payment.setFromTerm(1);
			payment.setToTerm(1);

			medicalPolicy.setLastPaymentTerm(1);
			medicalPolicy.setDelFlag(false);
			if (medicalProposal.getOldMedicalPolicy() != null) {
				medicalPolicy.setReferencePolicyNo(medicalProposal.getOldMedicalPolicy().getPolicyNo());
			}

			medicalProposalDAO.update(medicalProposal);
			medicalPolicyService.addNewMedicalPolicy(medicalPolicy);
			payment.setReferenceNo(medicalPolicy.getId());
			payment = paymentDAO.insert(payment);
			paymentList.add(payment);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to confirm a MedicalProposal", e);
		}
		return paymentList;
	}

	/** Reject */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void rejectMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.addWorkFlowHistory(workFlowDTO);
			medicalProposalDAO.updateProposalStatus(ProposalStatus.DENY, medicalProposal.getId());
			workFlowDTOService.deleteWorkFlowByRefNo(workFlowDTO.getReferenceNo());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MedicalProposal", e);
		}
	}

	/** Payment */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void paymentMedicalProposal(MedicalProposal medicalProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch userBranch) {
		try {
			double rate = 1;
			/* choose paymentDate is startDate */
			if (medicalProposal.getStartDate() == null) {
				medicalProposal.setStartDate(new Date());
				resetEndDate(medicalProposal);
			}

			/* activate policy and payment */
			MedicalPolicy medicalPolicy = medicalPolicyService.activateMedicalPolicy(medicalProposal, rate);
			String policyNo = medicalPolicy.getPolicyNo();
			paymentService.activatePayment(paymentList, policyNo, rate);
			workFlowService.updateWorkFlow(workFlowDTO);
			medicalProposalDAO.update(medicalProposal);

			String policyId = medicalPolicy.getId();
			/* Retrieve payment from list by policy Id */
			Payment payment = paymentList.stream().filter(p -> policyId.equals(p.getReferenceNo())).findAny().orElse(null);

			/* Agent Commission */
			PolicyReferenceType policyRefType = payment.getReferenceType();
			AgentCommission agentCommission = null;
			List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
			if (medicalPolicy.getAgent() != null) {
				String receiptNo = payment.getReceiptNo();
				double netPremium = payment.getNetAgentComPremium();
				double commPercent = medicalProposal.getMedicalProposalInsuredPersonList().get(0).getProduct().getFirstCommission();
				double agentComm = Utils.getPercentOf(commPercent, netPremium);
				agentCommission = new AgentCommission(policyId, policyRefType, policyNo, medicalPolicy.getCustomer(), medicalPolicy.getOrganization(), medicalPolicy.getBranch(),
						medicalPolicy.getAgent(), agentComm, new Date(), receiptNo, netPremium, commPercent, AgentCommissionEntryType.UNDERWRITING, rate, (rate * agentComm),
						payment.getCurrency(), (rate * netPremium));
				agentCommissionList.add(agentCommission);
				agentCommissionDAO.addNewAgentCommisssion(agentCommission);
			}

			/* TLF */
			List<TlfData> dataList = new ArrayList<>();
			TlfData tlfData = tlfDataProcessor.getInstance(policyRefType, medicalPolicy, payment, agentCommissionList, false);
			dataList.add(tlfData);
			tlfProcessor.handleTlfProcess(dataList, userBranch);

			/* update ActivePolicy Count in CustomerTable */
			if (medicalProposal.getCustomer() != null) {
				int activePolicyCount = medicalProposal.getCustomer().getActivePolicy() + 1;
				customerDAO.updateActivePolicy(activePolicyCount, medicalProposal.getCustomer().getId());
				if (medicalProposal.getCustomer().getActivedDate() == null) {
					customerDAO.updateActivedPolicyDate(new Date(), medicalProposal.getCustomer().getId());
				}
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to payment a MedicalProposal ID : " + medicalProposal.getId(), e);
		}
	}

	/** Issue */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void issueMedicalProposal(String medicalProposalId) {
		try {
			workFlowService.deleteWorkFlowByRefNo(medicalProposalId);
			medicalProposalDAO.updateCompleteStatus(true, medicalProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a MotorProposal complete status", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal findMedicalProposalById(String id) {
		MedicalProposal result = null;
		try {
			result = medicalProposalDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MedicalProposal (ID : " + id + ")", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MP001> findAllMedicalPolicy(EnquiryCriteria enquiryCriteria) {
		List<MP001> result = null;
		try {
			result = medicalProposalDAO.findByEnquiryCriteria(enquiryCriteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of MedicalPolicy)", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalSurvey findMedicalSurveyByProposalId(String medicalProposalId) {
		MedicalSurvey result = null;
		try {
			result = medicalProposalDAO.findSurveyByProposalId(medicalProposalId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MedicalProposal (ID : " + medicalProposalId + ")", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePayment(MedicalPolicy medicalPolicy, WorkFlowDTO workFlowDTO) {
		try {
			workFlowDTOService.updateWorkFlow(workFlowDTO);
			setCodeNo(medicalPolicy.getMedicalProposal());
			medicalProposalDAO.update(medicalPolicy.getMedicalProposal());
			medicalPolicyService.deleteMedicalPolicy(medicalPolicy);
			List<Payment> paymentList = paymentService.findByPolicy(medicalPolicy.getId());
			paymentService.deletePayments(paymentList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete payment.", e);
		}
	}

	private void setProposalNo(MedicalProposal medicalProposal) {
		Product product = medicalProposal.getMedicalProposalInsuredPersonList().get(0).getProduct();
		String productId = product.getId().trim();
		String proposalNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_PROPOSAL_NO, productId);
		medicalProposal.setProposalNo(proposalNo);
	}

	private void setCodeNo(MedicalProposal medicalProposal) {
		String beneficiaryNo, guardianNo, insuPersonCode;
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			for (MedicalProposalInsuredPersonBeneficiaries beneficiary : person.getInsuredPersonBeneficiariesList()) {
				if (beneficiary.getBeneficiaryNo() == null) {
					beneficiaryNo = customIDGenerator.getCustomNextId(SystemConstants.HEALTH_BENEFICIARY_NO, null);
					beneficiary.setBeneficiaryNo(beneficiaryNo);
				}
			}
			if (person.getGuardian() != null) {
				guardianNo = customIDGenerator.getCustomNextId(SystemConstants.GUARDIAN_NO, null);
				person.getGuardian().setGuardianNo(guardianNo);
			}
			if (person.getInsPersonCodeNo() == null) {
				insuPersonCode = customIDGenerator.getNextId(SystemConstants.HEALTH_INSUPERSON_CODE_NO, null);
				person.setInsPersonCodeNo(insuPersonCode);
			}
		}
	}

	private void handleCustomer(MedicalProposal medicalProposal) {
		List<MedicalProposalInsuredPerson> insuredPersonList = medicalProposal.getMedicalProposalInsuredPersonList();
		Customer customer = medicalProposal.getCustomer();
		/** Customer */
		if (customer != null && customer.getId() == null) {
			/** New **/
			CustomerInfoStatus status = new CustomerInfoStatus();
			status.setStatusName(CustomerStatus.CONTRACTOR);
			customer.addCustomerInfoStatus(status);
			if (customer.getOccupation() != null && customer.getOccupation().getId() == null) {
				customer.setOccupation(null);
			}
			if (customer.getCountry() != null && customer.getCountry().getId() == null) {
				customer.setCountry(null);
			}
			customer = customerService.addNewCustomer(customer);
		} else if (customer != null) {
			/** Exiting **/
			if (customer.existCustomerInfoStatus(CustomerStatus.CONTRACTOR)) {
				CustomerInfoStatus status = new CustomerInfoStatus();
				status.setStatusName(CustomerStatus.CONTRACTOR);
				customer.addCustomerInfoStatus(status);
			}
			customer = customerService.updateCustomer(customer);
		}

		/** Insured Person */
		for (MedicalProposalInsuredPerson insuredPerson : insuredPersonList) {
			Customer insuredusCustomer = insuredPerson.getCustomer();
			if (insuredusCustomer.getId() == null) {
				/** New **/
				/* same with customer (self) */
				if (KeyFactorIDConfig.isSelfRelationship(insuredPerson.getRelationship().getId()) && customer != null) {
					insuredusCustomer = customer;
					insuredusCustomer.loadTransientIdNo();
					CustomerInfoStatus insuStatus = new CustomerInfoStatus();
					insuStatus.setStatusName(CustomerStatus.INSUREDPERSON);
					insuredusCustomer.addCustomerInfoStatus(insuStatus);
					if (insuredusCustomer.getOccupation() != null && insuredusCustomer.getOccupation().getId() == null) {
						insuredusCustomer.setOccupation(null);
					}
					if (insuredusCustomer.getCountry() != null && insuredusCustomer.getCountry().getId() == null) {
						insuredusCustomer.setCountry(null);
					}
					customer = customerService.updateCustomer(insuredusCustomer);
					insuredPerson.setCustomer(customer);
				} else {
					CustomerInfoStatus insuStatus = new CustomerInfoStatus();
					insuStatus.setStatusName(CustomerStatus.INSUREDPERSON);
					insuredusCustomer.addCustomerInfoStatus(insuStatus);
					if (insuredusCustomer.getOccupation() != null && insuredusCustomer.getOccupation().getId() == null) {
						insuredusCustomer.setOccupation(null);
					}
					if (insuredusCustomer.getCountry() != null && insuredusCustomer.getCountry().getId() == null) {
						insuredusCustomer.setCountry(null);
					}
					insuredusCustomer = customerService.addNewCustomer(insuredusCustomer);
				}
			} else {
				/** Exiting **/
				/* same with customer */
				if (customer != null && insuredusCustomer.getId().equals(customer.getId())) {
					insuredusCustomer = customer;
					insuredusCustomer.loadTransientIdNo();
				}
				if (insuredusCustomer.existCustomerInfoStatus(CustomerStatus.INSUREDPERSON)) {
					CustomerInfoStatus status = new CustomerInfoStatus();
					status.setStatusName(CustomerStatus.INSUREDPERSON);
					insuredusCustomer.addCustomerInfoStatus(status);
				}
				insuredusCustomer.loadTransientIdNo();
				insuredusCustomer = customerService.updateCustomer(insuredusCustomer);
			}

			/** Guardian **/
			if (insuredPerson.getGuardian() != null) {
				Customer guardianCustomer = insuredPerson.getGuardian().getCustomer();
				if (guardianCustomer.getId() == null) {
					/** New **/
					/* same with customer */
					if (insuredPerson.getGuardian().isSameCustomer()) {
						guardianCustomer = customer;
						guardianCustomer.loadTransientIdNo();
						CustomerInfoStatus insuStatus = new CustomerInfoStatus();
						insuStatus.setStatusName(CustomerStatus.GUARDIAN);
						guardianCustomer.addCustomerInfoStatus(insuStatus);
						if (guardianCustomer.getOccupation() != null && guardianCustomer.getOccupation().getId() == null) {
							guardianCustomer.setOccupation(null);
						}
						if (guardianCustomer.getCountry() != null && guardianCustomer.getCountry().getId() == null) {
							guardianCustomer.setCountry(null);
						}
						customer = customerService.updateCustomer(guardianCustomer);
						insuredPerson.getGuardian().setCustomer(customer);
					} else {
						CustomerInfoStatus insuStatus = new CustomerInfoStatus();
						insuStatus.setStatusName(CustomerStatus.GUARDIAN);
						guardianCustomer.addCustomerInfoStatus(insuStatus);
						if (guardianCustomer.getOccupation() != null && guardianCustomer.getOccupation().getId() == null) {
							guardianCustomer.setOccupation(null);
						}
						if (guardianCustomer.getCountry() != null && guardianCustomer.getCountry().getId() == null) {
							guardianCustomer.setCountry(null);
						}
						guardianCustomer = customerService.addNewCustomer(guardianCustomer);
					}
				} else {
					/** Exiting **/
					/* same with customer */
					if (customer != null && guardianCustomer.getId().equals(customer.getId())) {
						guardianCustomer = customer;
						guardianCustomer.loadTransientIdNo();
					}
					if (guardianCustomer.existCustomerInfoStatus(CustomerStatus.GUARDIAN)) {
						CustomerInfoStatus status = new CustomerInfoStatus();
						status.setStatusName(CustomerStatus.GUARDIAN);
						guardianCustomer.addCustomerInfoStatus(status);
					}
					guardianCustomer = customerService.updateCustomer(guardianCustomer);
				}
			}
		}
	}

	private void calculateTermPremium(MedicalProposal medicalProposal) {
		double premium = 0.0, addOnPremium = 0.0, termPremium = 0.0, addOnTermPremium = 0.0;
		int paymentTerm = medicalProposal.getPaymentTerm();
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			premium = person.getPremium();
			addOnPremium = person.getAddOnPremium();

			termPremium = Utils.divide(premium, paymentTerm);
			addOnTermPremium = Utils.divide(addOnPremium, paymentTerm);
			person.setBasicTermPremium(termPremium);
			person.setAddOnTermPremium(addOnTermPremium);
		}
	}

	private void resetEndDate(MedicalProposal medicalProposal) {
		if (medicalProposal.getStartDate() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(medicalProposal.getStartDate());
			int periodOfMonth = medicalProposal.getPeriodMonth();
			cal.add(Calendar.MONTH, periodOfMonth);
			medicalProposal.setEndDate(cal.getTime());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMedicalProposalAttachment(MedicalProposal medicalProposal) {
		try {
			medicalProposalDAO.updateMedicalProposalAttachment(medicalProposal);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a MedicalProposal Attachment", e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<MedicalProposalDTO> findMedicalProposal() {
		List<MedicalProposalDTO> result=null;
		try {
			result = medicalProposalDAO.findMedicalProposal();
		}catch(DAOException e) {
			throw new SystemException(e.getErrorCode(), "Can't find MedicalProposal",e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal findLifeProposalByProposalNoFromTerm(String proposalNo) {
		MedicalProposal result = null;
		try {
			result = medicalProposalDAO.findByProposalNoFromTerm(proposalNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a LifeProposal (No : " + proposalNo + ")", e);
		}
		return result;
	}


}
