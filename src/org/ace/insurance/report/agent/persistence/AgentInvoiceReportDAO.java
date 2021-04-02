package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.AgentCommissionEntryType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.report.agent.AgentInvoiceCriteria;
import org.ace.insurance.report.agent.AgentInvoiceDTO;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentInvoiceReportDAO;
import org.ace.insurance.web.manage.agent.payment.AgentCommissionReportDTO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("AgentInvoiceReportDAO")
public class AgentInvoiceReportDAO extends BasicDAO implements IAgentInvoiceReportDAO {

	@Resource(name = "PaymentDAO")
	protected IPaymentDAO paymentDAO;

	public List<AgentInvoiceDTO> find(AgentInvoiceCriteria criteria) throws DAOException {
		List<AgentInvoiceDTO> result = new ArrayList<AgentInvoiceDTO>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + AgentInvoiceDTO.class.getName());
			buffer.append("(ac.invoiceNo, a.initialId, a.name, a.liscenseNo, ");
			buffer.append("SUM(ac.premium), SUM(ac.commission), ac.currency.currencyCode, ac.invoiceDate, ac.branch.id) ");
			buffer.append("FROM AgentCommission ac JOIN ac.agent a ");
			buffer.append("WHERE ac.sanctionNo IS NOT NULL AND ac.branch.id = :branchId AND ac.currency.currencyCode = :currencyCode ");
			if (criteria.getAgentId() != null && !criteria.getAgentId().isEmpty())
				buffer.append("AND a.id = :agentId ");
			if (criteria.getInvoiceNo() != null && !criteria.getInvoiceNo().isEmpty())
				buffer.append("AND ac.invoiceNo = :invoiceNo ");
			if (criteria.getStartDate() != null)
				buffer.append("AND ac.invoiceDate >= :startDate ");
			if (criteria.getEndDate() != null)
				buffer.append("AND ac.invoiceDate <= :endDate ");
			buffer.append("AND ac.invoiceNo IS NOT NULL ");
			buffer.append("GROUP BY ac.invoiceNo, ac.invoiceDate, a.initialId, a.name, a.liscenseNo, ac.currency.currencyCode, ac.sanctionDate, ac.branch.id ");
			buffer.append("ORDER BY ac.invoiceNo");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("branchId", criteria.getBranchId());
			query.setParameter("currencyCode", criteria.getCurrencyCode());
			if (criteria.getAgentId() != null && !criteria.getAgentId().isEmpty())
				query.setParameter("agentId", criteria.getAgentId());
			if (criteria.getInvoiceNo() != null && !criteria.getInvoiceNo().isEmpty())
				query.setParameter("invoiceNo", criteria.getInvoiceNo());
			if (criteria.getStartDate() != null)
				query.setParameter("startDate", criteria.getStartDate());
			if (criteria.getEndDate() != null)
				query.setParameter("endDate", criteria.getEndDate());
			result = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of agent sanction", pe);
		}
		return result;
	}

	public List<AgentCommissionReportDTO> getAgentCommissionReportDTOForLife(List<AgentCommission> agentCommissionList) throws DAOException {
		List<AgentCommissionReportDTO> dtoList = new ArrayList<AgentCommissionReportDTO>();

		for (AgentCommission agentCommission : agentCommissionList) {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l FROM LifePolicy l WHERE l.id = :policyId");

			Query q = em.createQuery(query.toString());
			q.setParameter("policyId", agentCommission.getReferenceNo());

			LifePolicy policy = (LifePolicy) q.getSingleResult();

			// Search Payment with receiptNo of agent commission
			List<Payment> paymentList = paymentDAO.findPaymentByReceiptNo(agentCommission.getReceiptNo());
			Payment payment = paymentList.get(0);

			// determine within one year or not
			double commissionPercentage = 0.0;
			double premium = 0.0;
			double renewalPremium = 0.0;
			// within one year payment
			if (agentCommission.getEntryType() == AgentCommissionEntryType.UNDERWRITING) {
				commissionPercentage = agentCommission.getPercentage();
				premium = policy.getTotalTermPremium();
				renewalPremium = 0.0;
			} else if (agentCommission.getEntryType() == AgentCommissionEntryType.RENEWAL || agentCommission.getEntryType() == AgentCommissionEntryType.RENEWAL_FIRST) {
				commissionPercentage = agentCommission.getPercentage();
				premium = 0.0;
				renewalPremium = policy.getTotalTermPremium();
			}

			AgentCommissionReportDTO dto = new AgentCommissionReportDTO(payment.getReceiptNo(), payment.getPaymentDate(), policy.getPolicyNo(), agentCommission.getCommission(),
					agentCommission.getWithHoldingTax(), commissionPercentage, premium, renewalPremium, policy.getTotalSumInsured(), policy.getCustomerName());
			dtoList.add(dto);
		}
		return dtoList;
	}
}
