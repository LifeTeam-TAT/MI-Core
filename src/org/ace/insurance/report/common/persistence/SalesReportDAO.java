package org.ace.insurance.report.common.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentSaleReportView;
import org.ace.insurance.report.common.SalesReport;
import org.ace.insurance.report.common.SalesReportCriteria;
import org.ace.insurance.report.common.persistence.interfaces.ISalesReportDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;

@Repository("SalesReportDAO")
public class SalesReportDAO extends BasicDAO implements ISalesReportDAO {

	@Override
	public List<SalesReport> find(SalesReportCriteria criteria) throws DAOException {
		List<SalesReport> result = new ArrayList<SalesReport>();
		List<AgentSaleReportView> agentSaleReportList = new ArrayList<AgentSaleReportView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT a FROM AgentSaleReportView a WHERE a.id is NOT NULL");

			if (criteria.getReferenceType() != null) {
				query.append(" AND a.insuranceType = :referenceType");
			}

			if (criteria.getProduct() != null) {
				query.append(" AND a.productId = :productId");
			}

			if (criteria.getAgent() != null) {
				query.append(" AND a.agentId = :agentId");

			}
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.append(" AND a.dateOfInsured >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.append(" AND a.dateOfInsured <= :endDate");
			}
			query.append(" ORDER BY a.insuranceType, a.productType,  a.id");
			Query q = em.createQuery(query.toString());

			if (criteria.getReferenceType() != null) {
				q.setParameter("referenceType", criteria.getReferenceType().getLabel());
			}

			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getAgent() != null) {
				q.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getProduct() != null) {
				q.setParameter("productId", criteria.getProduct().getId());
			}

			agentSaleReportList = q.getResultList();
			for (AgentSaleReportView view : agentSaleReportList) {
				result.add(new SalesReport(view));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Agent Sale by criteria.", pe);
		}
		return result;
	}
}
