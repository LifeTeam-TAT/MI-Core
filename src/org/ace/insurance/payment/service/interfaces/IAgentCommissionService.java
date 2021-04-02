package org.ace.insurance.payment.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.payment.AC001;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.web.manage.agent.AgentEnquiryCriteria;
import org.ace.java.component.SystemException;

public interface IAgentCommissionService {

	public void addNewAgentCommisssion(List<AgentCommission> agentCommissions) throws SystemException;

	public void addNewAgentCommisssion(MedicalPolicy medicalPolicy, String chalanNo) throws SystemException;

	public void updateAgentCommisssion(MedicalPolicy medicalPolicy, AgentCommission agentComission) throws SystemException;

	public List<Agent> findAgentByCommissionCriteria(AgentEnquiryCriteria agentEnquiryCriteria) throws SystemException;

	public List<AC001> findAgentCommissionByAgent(AgentEnquiryCriteria agentEnquiryCriteria) throws SystemException;

	public AgentCommission findAgentCommissionByChallanNo(String challanNo) throws SystemException;

	public AgentCommission findAgentCommissionByPolicyId(String id) throws SystemException;

	public List<AgentCommission> findAgentCommissionBySanctionNo(String sanctionNo) throws SystemException;

	public void updateAgentCommisssion(AgentCommission agentCommission) throws SystemException;

	public List<AgentCommission> findAgentCommissionByInvoiceNo(String invoiceNo) throws SystemException;

	public List<AgentCommission> paymentAgentCommission(List<AgentCommission> commissionList, SalesPoints salePoint, Branch branch) throws SystemException;

}
