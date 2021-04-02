/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.agent.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.AgentCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.interfaces.IPolicy;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentInvoiceReportDAO;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSanctionDAO;
import org.ace.insurance.system.common.agent.AGP001;
import org.ace.insurance.system.common.agent.AGP002;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.AgentPortfolio;
import org.ace.insurance.system.common.agent.persistence.interfaces.IAgentDAO;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.currency.service.interfaces.ICurrencyService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentService")
public class AgentService extends BaseService implements IAgentService {

	@Resource(name = "AgentDAO")
	private IAgentDAO agentDAO;

	@Resource(name = "AgentSanctionDAO")
	private IAgentSanctionDAO agentSanctionDAO;

	@Resource(name = "AgentInvoiceReportDAO")
	private IAgentInvoiceReportDAO agentInvoiceDAO;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	@Resource(name = "MedicalPolicyService")
	private IMedicalPolicyService medicalPolicyService;

	@Resource(name = "LifePolicyService")
	private ILifePolicyService lPolicyService;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "CurrencyService")
	private ICurrencyService currencyService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addNewAgent(Agent agent) {
		try {
			String agnetCode = null;
			if (agent.getGroupType().equals(ProductGroupType.LIFE)) {
				agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_LIFE_NO, null);
			} else if (agent.getGroupType().equals(ProductGroupType.NONLIFE)) {
				agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_NONELIFE_NO, null);
			} else if (agent.getGroupType().equals(ProductGroupType.COMPOSITE)) {
				agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_COMPOSITE_NO, null);
			}
			agent.setCodeNo(agnetCode);
			agentDAO.insert(agent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Agent", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewAgentPortfolio(AgentPortfolio agentPortfolio) {
		try {
			agentDAO.insertAgentPortfolio(agentPortfolio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Agent", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AGP001> findAgentPortfolioByPolicyNo(String policyNo) {
		List<AGP001> results = null;
		try {
			results = agentDAO.findAgentPortfolioByPolicyNo(policyNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find AgentPortfolioDTO By PolicyNo", e);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Agent updateAgent(Agent agent) {
		try {
			Agent existingAgent = findAgentById(agent.getId());
			if (existingAgent.getGroupType() == null || !existingAgent.getGroupType().equals(agent.getGroupType())) {
				if (agent.getGroupType().equals(ProductGroupType.LIFE)) {
					String agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_LIFE_NO, null);
					agent.setCodeNo(agnetCode);
				} else if (agent.getGroupType().equals(ProductGroupType.NONLIFE)) {
					String agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_NONELIFE_NO, null);
					agent.setCodeNo(agnetCode);
				} else if (agent.getGroupType().equals(ProductGroupType.COMPOSITE)) {
					String agnetCode = customIDGenerator.getNextId(SystemConstants.AGENT_COMPOSITE_NO, null);
					agent.setCodeNo(agnetCode);
				}
			}
			agent = agentDAO.update(agent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update Agent", e);
		}
		return agent;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentPortfolio updateAgentPortfolio(AgentPortfolio portfolio) {
		try {
			portfolio = agentDAO.updateAgentPortfolio(portfolio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update Agent", e);
		}
		return portfolio;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAgent(Agent agent) {
		try {
			agentDAO.delete(agent);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete  Agent", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAgentPortfolio(AgentPortfolio portfolio) {
		try {
			agentDAO.deleteAgentPortfolio(portfolio);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete  Agent", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Agent> findAllAgent() {
		List<Agent> result = null;
		try {
			result = agentDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all  Agents", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Agent findAgentById(String id) {
		Agent result = null;
		try {
			result = agentDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Agent (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentPortfolio findAgentPortfolioById(String id) {
		AgentPortfolio result = null;
		try {
			result = agentDAO.findAgentPortfolioById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Agent (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<Agent> findAgentByCriteria(AgentCriteria criteria) {
		List<Agent> result = null;
		try {
			result = agentDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Agent (criteriaValue : " + criteria.getCriteriaValue() + ")", e);
		}
		return result;
	}

	// @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	// public List<Agent> findAgentByCriteria(AgentCriteria criteria, int max) {
	// List<Agent> result = null;
	// try {
	// result = agentDAO.findByCriteria(criteria, max);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find a Agent
	// (criteriaValue : " + criteria.getCriteriaValue() + ")", e);
	// }
	// return result;
	// }

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<AGP002> findAgentByCriteria(AgentCriteria criteria, int max) {
		List<AGP002> result = null;
		try {
			result = agentDAO.findByCriteria(criteria, max);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Agent (criteriaValue : " + criteria.getCriteriaValue() + ")", e);
		}
		return result;
	}

	public String getPolicyNo(AgentCommission comm) {
		String policyNo = null;
		IPolicy policy = null;
		switch (comm.getReferenceType()) {

			// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
			case PA_POLICY:
			case FARMER_POLICY:
				policy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				policyNo = policy.getPolicyNo();
				break;
			case HEALTH_POLICY_BILL_COLLECTION:
			case HEALTH_POLICY:
				policy = medicalPolicyService.findMedicalPolicyById(comm.getReferenceNo());
				policyNo = policy.getPolicyNo();
				break;

			default:
				break;
		}
		return policyNo;
	}

	public String getCustomerName(AgentCommission comm) {
		String customerName = null;
		IPolicy policy = null;
		switch (comm.getReferenceType()) {

			case HEALTH_POLICY_BILL_COLLECTION:
			case HEALTH_POLICY:
				policy = medicalPolicyService.findMedicalPolicyById(comm.getReferenceNo());
				break;

			// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
			case LIFE_BILL_COLLECTION:
			case PA_POLICY:
			case FARMER_POLICY:
				policy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				break;

			default:
				break;
		}
		customerName = policy.getCustomerName();
		return customerName;
	}

	public double getPremium(AgentCommission comm, Payment payment, boolean isEnquiry) {
		double premium = 0.0;
		switch (comm.getReferenceType()) {

			case HEALTH_POLICY_BILL_COLLECTION:
			case HEALTH_POLICY:
				MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyById(comm.getReferenceNo());
				premium = ProposalType.RENEWAL.equals(medicalPolicy.getMedicalProposal().getProposalType()) ? 0.0 : comm.getPremium();
				break;

			// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
			case PA_POLICY:
			case FARMER_POLICY:
				LifePolicy lPolicy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				premium = lPolicy.getTotalPremium();
				break;
			case LIFE_BILL_COLLECTION:
				LifePolicy lifePolicy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				if (isEnquiry) {
					int toTerm = payment.getToTerm();
					int paymentMonth = lifePolicy.getPaymentType().getMonth();
					if (toTerm * paymentMonth <= 12) {
						premium = lifePolicy.getTotalTermPremium();
					} else {
						premium = 0.0;
					}
				} else {
					int lastPaymentTerm = lifePolicy.getLastPaymentTerm();
					int paymentCountForYear = 12 / lifePolicy.getPaymentType().getMonth();
					if (lastPaymentTerm <= paymentCountForYear) {
						premium = lifePolicy.getTotalTermPremium();
					} else {
						premium = 0.0;
					}
				}
				break;

			default:
				break;
		}
		return premium;
	}

	public double getRenewalPremium(AgentCommission comm, Payment payment, boolean isEnquiry) {
		double renewalPremium = 0.0;
		switch (comm.getReferenceType()) {

			case HEALTH_POLICY_BILL_COLLECTION:
			case HEALTH_POLICY:
				MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyById(comm.getReferenceNo());
				renewalPremium = ProposalType.RENEWAL.equals(medicalPolicy.getMedicalProposal().getProposalType()) ? comm.getPremium() : 0.0;
				break;
			case PA_POLICY:
				// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
			case FARMER_POLICY:
				renewalPremium = 0.0;
				break;
			case LIFE_BILL_COLLECTION:
				LifePolicy lPolicy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				if (isEnquiry) {
					int toTerm = payment.getToTerm();
					int paymentMonth = lPolicy.getPaymentType().getMonth();
					if (toTerm * paymentMonth <= 12) {
						renewalPremium = 0.0;
					} else {
						renewalPremium = lPolicy.getTotalTermPremium();
					}
				} else {
					int lastPaymentTerm = lPolicy.getLastPaymentTerm();
					int paymentCountForYear = 12 / lPolicy.getPaymentType().getMonth();
					// within one year payment
					if (lastPaymentTerm <= paymentCountForYear) {
						renewalPremium = 0.0;
					} else {
						renewalPremium = lPolicy.getTotalTermPremium();
					}
				}
				break;

			default:
				break;
		}
		return renewalPremium;
	}

	public double getSumInsured(AgentCommission comm) {
		double sumInsured = 0.0;
		IPolicy policy = null;
		switch (comm.getReferenceType()) {

			case HEALTH_POLICY_BILL_COLLECTION:
			case HEALTH_POLICY:
				policy = medicalPolicyService.findMedicalPolicyById(comm.getReferenceNo());
				break;
			// FIXME CHECK REFTYPE
			case ENDOWNMENT_LIFE_POLICY:
			case LIFE_BILL_COLLECTION:
			case PA_POLICY:
			case FARMER_POLICY:
				policy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
				break;

			default:
				break;
		}
		sumInsured = policy.getTotalSumInsured();
		return sumInsured;
	}

	public double getCommissionPercentage(AgentCommission comm, Payment payment, boolean isEnquiry) {
		double commissionPercentage = 0.0;
		PolicyReferenceType rType = comm.getReferenceType();

		// FIXME CHECK REFTYPE
		if (rType.equals(PolicyReferenceType.ENDOWNMENT_LIFE_POLICY)) {
			LifePolicy lPolicy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
			commissionPercentage = lPolicy.getPolicyInsuredPersonList().get(0).getProduct().getFirstCommission();
		} else if (rType.equals(PolicyReferenceType.LIFE_BILL_COLLECTION)) {
			LifePolicy lPolicy = lPolicyService.findLifePolicyById(comm.getReferenceNo());
			if (isEnquiry) {
				int toTerm = payment.getToTerm();
				int paymentMonth = lPolicy.getPaymentType().getMonth();
				if (toTerm * paymentMonth <= 12) {
					commissionPercentage = lPolicy.getPolicyInsuredPersonList().get(0).getProduct().getFirstCommission();
				} else {
					commissionPercentage = lPolicy.getPolicyInsuredPersonList().get(0).getProduct().getRenewalCommission();
				}
			} else {
				int lastPaymentTerm = lPolicy.getLastPaymentTerm();
				int paymentCountForYear = 12 / lPolicy.getPaymentType().getMonth();
				// within one year payment
				if (lastPaymentTerm <= paymentCountForYear) {
					commissionPercentage = lPolicy.getPolicyInsuredPersonList().get(0).getProduct().getFirstCommission();
				} else {
					commissionPercentage = lPolicy.getPolicyInsuredPersonList().get(0).getProduct().getRenewalCommission();
				}
			}
		}
		return commissionPercentage;
	}

	public Date getPaymentDate(AgentCommission comm) {
		Date paymentDate = null;
		List<Payment> paymentList = paymentService.findPaymentByReferenceNoAndMaxDateForAgentInvoice(comm.getReferenceNo());
		if (paymentList != null) {
			return paymentDate = paymentList.get(0).getPaymentDate();
		}
		return paymentDate;
	}

	public String getCashReceipt(AgentCommission comm) {
		String receiptNo = null;
		List<Payment> paymentList = paymentService.findPaymentByReferenceNoAndMaxDateForAgentInvoice(comm.getReferenceNo());
		if (paymentList != null) {
			return receiptNo = paymentList.get(0).getReceiptNo();
		}
		return receiptNo;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean checkExistingAgent(String liscenseNo) {
		boolean result = false;
		try {
			result = agentDAO.checkExistingAgent(liscenseNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Agent", e);
		}
		return result;
	}

}