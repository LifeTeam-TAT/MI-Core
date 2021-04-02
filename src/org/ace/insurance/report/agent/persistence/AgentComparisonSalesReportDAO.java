package org.ace.insurance.report.agent.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentComparisonSalesReportDAO;
import org.ace.insurance.report.common.AgentComparisonSalesReport;
import org.ace.insurance.report.common.AgentSaleComparisonCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "AgentComparisonSalesReportDAO")
public class AgentComparisonSalesReportDAO extends BasicDAO implements IAgentComparisonSalesReportDAO {
	/**
	 * Find AgentComparisonSalesReport by given criteria
	 * 
	 * @param AgentSaleComparisonCriteria
	 * 
	 * @return List[AgentComparisonSalesReport List]
	 */
	@SuppressWarnings({ "unchecked" })
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AgentComparisonSalesReport> findAgentComparisonSalesReport(AgentSaleComparisonCriteria criteria) throws DAOException {
		List<AgentComparisonSalesReport> result = new ArrayList<AgentComparisonSalesReport>();
		List<Object[]> tempResults = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT PROPOSALTYPE, ");
			query.append(" CASE WHEN SUM(FIRE) IS NULL THEN 0 ELSE SUM(FIRE) END AS FIRE, ");
			query.append(" CASE WHEN SUM(MOTOR) IS NULL THEN 0 ELSE SUM(MOTOR) END AS MOTOR, ");
			query.append(" CASE WHEN SUM(MARINECARGO) IS NULL THEN 0 ELSE SUM(MARINECARGO) END AS MARINECARGO, ");
			query.append(" ((CASE WHEN SUM(FIRE) IS NULL THEN 0 ELSE SUM(FIRE) END )+ ");
			query.append(" (CASE WHEN SUM(MOTOR) IS NULL THEN 0 ELSE SUM(MOTOR) END )+ ");
			query.append(" (CASE WHEN SUM(MARINECARGO) IS NULL THEN 0 ELSE SUM(MARINECARGO) END )) AS POLICYTOTAL, ");
			query.append(" CASE WHEN SUM(FIREPREMIUM) IS NULL THEN 0 ELSE SUM(FIREPREMIUM) END AS FIREPREMIUM, ");
			query.append(" CASE WHEN SUM(MOTORPREMIUM) IS NULL THEN 0 ELSE SUM(MOTORPREMIUM) END AS MOTORPREMIUM, ");
			query.append(" CASE WHEN SUM(MARINECARGOPREMIUM)IS NULL THEN 0 ELSE SUM(MARINECARGOPREMIUM) END AS MARINECARGOPREMIUM, ");
			query.append(" ((CASE WHEN SUM(FIREPREMIUM) IS NULL THEN 0 ELSE SUM(FIREPREMIUM) END )+ ");
			query.append(" (CASE WHEN SUM(MOTORPREMIUM) IS NULL THEN 0 ELSE SUM(MOTORPREMIUM) END )+ ");
			query.append(" (CASE WHEN SUM(MARINECARGOPREMIUM)IS NULL THEN 0 ELSE SUM(MARINECARGOPREMIUM) END )) AS PREMIUMTOTAL ");
			query.append(" FROM(SELECT ");
			query.append(" PROPOSALTYPE,[FIRE_POLICY] AS FIRE,[MOTOR_POLICY] AS MOTOR,[CARGO_POLICY] AS MARINECARGO, ");
			query.append(" 0 AS FIREPREMIUM,0 AS MOTORPREMIUM,0 AS MARINECARGOPREMIUM ");
			query.append(" FROM(SELECT ");
			query.append(" PROPOSALTYPE,REFERENCETYPE, ");
			query.append(" SUM(NEWPOLICY +RENEWALPOLICY) AS Policies ");
			query.append(" FROM VWT_AGENTSALE");
			query.append(" WHERE(ACTIVEDPOLICYSTARTDATE >= ?1) AND ");
			query.append(" (ACTIVEDPOLICYSTARTDATE <= ?2)");
			query.append(" AND (PROPOSALTYPE IS NOT NULL) ");
			if (criteria.getCurrencyType() != null) {
				query.append(" AND CURRENCY = ?3");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND BRANCHID = ?4");
			}
			query.append(" GROUP BY REFERENCETYPE,PROPOSALTYPE) ACSRPY ");
			query.append(" PIVOT(SUM(Policies) for REFERENCETYPE IN ([FIRE_POLICY],[MOTOR_POLICY],[CARGO_POLICY]))PIV ");
			query.append(" UNION ALL ");
			query.append(" SELECT PROPOSALTYPE,0 AS FIRE,0 AS MOTOR,0 AS MARINECARGO, ");
			query.append(" [FIRE_POLICY] AS FIREPREMIUM,[MOTOR_POLICY] AS MOTORPREMIUM,[CARGO_POLICY] AS MARINECARGOPREMIUM ");
			query.append(" FROM(SELECT ");
			query.append(" PROPOSALTYPE,REFERENCETYPE, ");
			if (criteria.getCurrencyType() != null) {
				query.append(" SUM(TOTALPREMIUM) AS Premium ");
			} else {
				query.append(" SUM(HOMETOTALPREMIUM) AS Premium ");
			}
			query.append(" FROM VWT_AGENTSALE WHERE ");
			query.append("(ACTIVEDPOLICYSTARTDATE >=?1) AND ");
			query.append(" (ACTIVEDPOLICYSTARTDATE <=?2)");
			query.append(" AND (PROPOSALTYPE IS NOT NULL) ");
			if (criteria.getCurrencyType() != null) {
				query.append(" AND CURRENCY = ?3");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND BRANCHID = ?4");
			}
			query.append(" GROUP BY REFERENCETYPE,PROPOSALTYPE) ACSRPM ");
			query.append(" PIVOT(SUM(Premium) for REFERENCETYPE IN ([FIRE_POLICY],[MOTOR_POLICY],[CARGO_POLICY]) ");
			query.append(" )PIV) AS TPP ");
			query.append(" GROUP BY PROPOSALTYPE ");
			Query q = em.createNativeQuery(query.toString());

			q.setParameter("1", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			q.setParameter("2", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			if (criteria.getCurrencyType() != null) {
				q.setParameter("3", criteria.getCurrencyType().getCurrencyCode());
			}
			if (criteria.getBranch() != null) {
				q.setParameter("4", criteria.getBranch().getId());
			}
			tempResults = q.getResultList();
			em.flush();
			for (Object obj[] : tempResults) {

				String proposalType = obj[0].toString();
				//To FIXME by THK
				Long firePolicy = new Long(obj[1].toString());
				Long motorPolicy = new Long(obj[2].toString());
				Long cargoPolicy = new Long(obj[3].toString());
				Long noOfTotalpolicy = new Long(obj[4].toString());
				Double firePremium = new Double(obj[5].toString());
				Double motorPremium = new Double(obj[6].toString());
				Double cargoPremium = new Double(obj[7].toString());
				Double totalPremium = new Double(obj[8].toString());
				AgentComparisonSalesReport agentComparisonSalesReport = new AgentComparisonSalesReport(proposalType, firePolicy, motorPolicy, cargoPolicy, noOfTotalpolicy,
						firePremium, motorPremium, cargoPremium, totalPremium);
				result.add(agentComparisonSalesReport);
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Agent Comparison Sales Report by criteria.", pe);
		}
		return result;

	}
}
