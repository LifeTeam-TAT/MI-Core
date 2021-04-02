package org.ace.insurance.report.agent.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.payment.service.interfaces.IAgentCommissionService;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSanctionDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSanctionService;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "AgentSanctionService")
public class AgentSanctionService implements IAgentSanctionService {

	@Resource(name = "AgentSanctionDAO")
	private IAgentSanctionDAO agentSanctionDAO;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "AgentCommissionService")
	private IAgentCommissionService agentCommissionService;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowService;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void sanctionAgent(List<AgentSanctionInfo> sanctionInfoList) {
		try {
			String sanctionNo = customIDGenerator.getCustomNextId(SystemConstants.AGENT_SANCTION_NO, null);
			for (AgentSanctionInfo sanctionInfo : sanctionInfoList) {
				sanctionInfo.setSanctionNo(sanctionNo);
				sanctionInfo.setSanctionDate(new Date());
			}

			agentSanctionDAO.updateAgentSanctionStaus(sanctionInfoList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to sanctionAgent.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionInfo> findAgents(AgentSanctionCriteria criteria) throws SystemException {
		List<AgentSanctionInfo> results = null;
		try {
			results = agentSanctionDAO.findAgents(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Agents.", e);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionDTO> findAgentSanctionDTO(AgentSanctionCriteria criteria) throws SystemException {
		List<AgentSanctionDTO> results = null;
		try {
			results = agentSanctionDAO.findAgentSanctionDTO(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Agents.", e);
		}

		return results;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentSanctionDTO> invoicedAgentSanction(List<AgentSanctionDTO> sanctionList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo)
			throws SystemException {
		try {
			String invoiceNo = null;
			List<AgentCommission> agentCommissions = null;
			Payment payment = null;
			for (AgentSanctionDTO sanction : sanctionList) {
				invoiceNo = customIDGenerator.getCustomNextId(SystemConstants.AGENT_COMMISSION_INVOICE_NO, null);
				agentCommissions = agentCommissionService.findAgentCommissionBySanctionNo(sanction.getSanctionNo());
				for (AgentCommission agentCommission : agentCommissions) {
					agentCommission.setInvoiceNo(invoiceNo);
					agentCommission.setInvoiceDate(new Date());
					agentCommissionService.updateAgentCommisssion(agentCommission);

					payment = new Payment();
					payment.setPaymentChannel(paymentChannel);
					payment.setAccountBank(bank);
					payment.setBankAccountNo(bankAccountNo);
					payment.setInvoiceNo(invoiceNo);
					payment.setConfirmDate(new Date());
					payment.setPolicyNo(agentCommission.getPolicyNo());
					payment.setReceiptNo(agentCommission.getReceiptNo());
					payment.setReferenceNo(agentCommission.getId());
					payment.setReferenceType(PolicyReferenceType.AGENT_COMMISSION);
					payment.setCurrency(agentCommission.getCurrency());
					payment.setAgentCommission(agentCommission.getCommission());
					payment.setRate(agentCommission.getRate());
					payment.setAmount(payment.getAgentCommission());
					paymentDAO.insert(payment);
				}

				workFlow.setReferenceNo(invoiceNo);
				workFlow.setBranchId(sanction.getBranchId());
				workFlowService.addNewWorkFlow(workFlow);
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Agents.", e);
		}

		return sanctionList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionInfo> findAgentCommissionBySanctionNo(String sanctionNo) throws SystemException {
		List<AgentSanctionInfo> results = null;
		try {
			results = agentSanctionDAO.findAgentCommissionBySanctionNo(sanctionNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Agents.", e);
		}

		return results;
	}

}
