package org.ace.insurance.report.agent.service.interfaces;

import java.util.List;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.system.common.PaymentChannel;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.java.component.SystemException;

public interface IAgentSanctionService {
	public List<AgentSanctionInfo> findAgents(AgentSanctionCriteria criteria) throws SystemException;

	public void sanctionAgent(List<AgentSanctionInfo> sanctionInfoList) throws SystemException;

	public List<AgentSanctionDTO> findAgentSanctionDTO(AgentSanctionCriteria criteria) throws SystemException;

	public List<AgentSanctionDTO> invoicedAgentSanction(List<AgentSanctionDTO> sanctionList, WorkFlowDTO workFlow, PaymentChannel paymentChannel, Bank bank, String bankAccountNo)
			throws SystemException;

	public List<AgentSanctionInfo> findAgentCommissionBySanctionNo(String sanctionNo) throws SystemException;

}
