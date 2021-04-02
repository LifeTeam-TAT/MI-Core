package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.report.agent.AgentSanctionCriteria;
import org.ace.insurance.report.agent.AgentSanctionDTO;
import org.ace.insurance.report.agent.AgentSanctionInfo;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSanctionDAO;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AgentSanctionDAO")
public class AgentSanctionDAO extends BasicDAO implements IAgentSanctionDAO {

	@Resource(name = "IDConfigLoader")
	protected IDConfigLoader configLoader;

	@Resource(name = "UserProcessService")
	protected IUserProcessService userProcessService;

	@Resource(name = "PaymentDAO")
	protected IPaymentDAO paymentDAO;

	List<AgentCommission> resultCommission = new ArrayList<AgentCommission>();

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionInfo> findAgents(AgentSanctionCriteria criteria) throws DAOException {
		List<AgentSanctionInfo> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + AgentSanctionInfo.class.getName());
			buffer.append("(ac.id, ac.policyNo, ac.receiptNo, a.initialId,  a.name, a.liscenseNo, c.initialId, c.name, o.name, ac.premium, ");
			buffer.append("ac.commission, ac.currency.currencyCode, ac.referenceType, ac.sanctionNo, ac.sanctionDate, ac.commissionStartDate) ");
			buffer.append("FROM AgentCommission ac JOIN ac.agent a LEFT OUTER JOIN ac.customer c ");
			buffer.append("LEFT OUTER JOIN ac.organization o ");
			buffer.append("WHERE ac.sanctionNo IS NULl AND ac.status = 0 ");
			buffer.append("AND ac.branch.id = :branchId AND ac.currency.currencyCode = :currencyCode AND ac.agent.id = :agentId ");
			buffer.append("AND ac.referenceType IN :referenceTypeList ");
			if (criteria.getStartDate() != null)
				buffer.append("AND ac.commissionStartDate >= :startDate ");
			if (criteria.getEndDate() != null)
				buffer.append("AND ac.commissionStartDate <= :endDate ");
			buffer.append("ORDER BY ac.referenceType, ac.receiptNo");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("branchId", criteria.getBranchId());
			query.setParameter("currencyCode", criteria.getCurrencyCode());
			query.setParameter("agentId", criteria.getAgentId());
			query.setParameter("referenceTypeList", criteria.getReferenceTypeList());
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

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionDTO> findAgentSanctionDTO(AgentSanctionCriteria criteria) throws DAOException {
		List<AgentSanctionDTO> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + AgentSanctionDTO.class.getName());
			buffer.append("(ac.sanctionNo, a.initialId, a.name, a.liscenseNo, ");
			buffer.append("SUM(ac.premium), SUM(ac.commission), ac.currency.currencyCode, ac.sanctionDate, ac.branch.id) ");
			buffer.append("FROM AgentCommission ac JOIN ac.agent a ");
			buffer.append("WHERE ac.sanctionNo IS NOT NULl AND ac.branch.id = :branchId AND ac.currency.currencyCode = :currencyCode ");
			if (!criteria.isEnquiry()) {
				buffer.append("AND ac.invoiceNo IS NULL ");
			}
			if (criteria.getAgentId() != null && !criteria.getAgentId().isEmpty())
				buffer.append("AND a.id = :agentId ");
			if (criteria.getSanctionNo() != null && !criteria.getSanctionNo().isEmpty())
				buffer.append("AND ac.sanctionNo = :sanctionNo ");
			if (criteria.getStartDate() != null)
				buffer.append("AND ac.sanctionDate >= :startDate ");
			if (criteria.getEndDate() != null)
				buffer.append("AND ac.sanctionDate <= :endDate ");
			buffer.append("GROUP BY ac.sanctionNo, ac.sanctionDate, a.initialId, a.name, a.liscenseNo, ac.currency.currencyCode, ac.sanctionDate, ac.branch.id ");
			buffer.append("ORDER BY ac.sanctionNo");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("branchId", criteria.getBranchId());
			query.setParameter("currencyCode", criteria.getCurrencyCode());
			if (criteria.getAgentId() != null && !criteria.getAgentId().isEmpty())
				query.setParameter("agentId", criteria.getAgentId());
			if (criteria.getSanctionNo() != null && !criteria.getSanctionNo().isEmpty())
				query.setParameter("sanctionNo", criteria.getSanctionNo());
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

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAgentSanctionStaus(List<AgentSanctionInfo> sanctionInfoList) throws DAOException {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE AgentCommission a SET a.status = :status , a.sanctionDate = :sanctionDate , a.sanctionNo = :sanctionNo ");
			buffer.append("WHERE a.id = :id");
			Query query = em.createQuery(buffer.toString());
			for (AgentSanctionInfo sanctionInfo : sanctionInfoList) {
				query.setParameter("id", sanctionInfo.getId());
				query.setParameter("status", true);
				query.setParameter("sanctionDate", sanctionInfo.getSanctionDate());
				query.setParameter("sanctionNo", sanctionInfo.getSanctionNo());
				query.executeUpdate();
				em.flush();
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to update AgentCommission", pe);
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AgentSanctionInfo> findAgentCommissionBySanctionNo(String sanctionNo) throws DAOException {
		List<AgentSanctionInfo> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + AgentSanctionInfo.class.getName());
			buffer.append("(a.id, ac.policyNo, ac.receiptNo, a.initialId,  a.name, a.liscenseNo, c.initialId, c.name, o.name, ac.premium, ");
			buffer.append("ac.commission, ac.currency.currencyCode, ac.referenceType, ac.sanctionNo, ac.sanctionDate, ac.commissionStartDate) ");
			buffer.append("FROM AgentCommission ac JOIN ac.agent a LEFT OUTER JOIN ac.customer c ");
			buffer.append("LEFT OUTER JOIN ac.organization o ");
			buffer.append("WHERE ac.sanctionNo = :sanctionNo ");
			buffer.append("ORDER BY ac.referenceType, ac.receiptNo");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("sanctionNo", sanctionNo);
			result = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of agent sanction", pe);
		}
		return result;
	}
}
