package org.ace.insurance.report.agent.persistence.interfaces;

import java.util.List;

import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.report.agent.AgentInvoiceCriteria;
import org.ace.insurance.report.agent.AgentInvoiceDTO;
import org.ace.insurance.web.manage.agent.payment.AgentCommissionReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface IAgentInvoiceReportDAO {
	public List<AgentInvoiceDTO> find(AgentInvoiceCriteria criteria) throws DAOException;

	public List<AgentCommissionReportDTO> getAgentCommissionReportDTOForLife(List<AgentCommission> agentCommissionList) throws DAOException;
}
