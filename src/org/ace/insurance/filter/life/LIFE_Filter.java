package org.ace.insurance.filter.life;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.filter.cirteria.CRIA002;
import org.ace.insurance.filter.life.interfaces.ILIFE_Filter;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("LIFE_Filter")
public class LIFE_Filter extends BasicDAO implements ILIFE_Filter {
	private Logger logger = LogManager.getLogger(getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPLC001> find(CRIA002 criteria) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, LPLC001> resultMap = new HashMap<String, LPLC001>();
		String id;
		String policyNo;
		String proposalNo;
		String saleMan;
		String agent;
		String customer;
		String branch;
		double premium;
		double sumInsured;
		PaymentType paymenttype;
		Date submittedDate;
		try {
			logger.debug("find() method has been started.");
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT l.id, l.policyNo, l.lifeProposal.proposalNo, l.agent, l.customer, l.organization, "
					+ "l.branch.name, pi.premium, pi.sumInsured, l.paymentType, l.commenmanceDate "
					+ "FROM LifePolicy l INNER Join l.policyInsuredPersonList pi WHERE l.policyNo IS NOT NULL");
			if (!isEmpty(criteria.getAgentId())) {
				queryString.append(" AND l.agent.id = '" + criteria.getAgentId() + "'");
			}
			if (!isEmpty(criteria.getStartDate())) {
				queryString.append(" AND l.commenmanceDate >= '" + criteria.getSQLStartDate() + "'");
			}
			if (!isEmpty(criteria.getEndDate())) {
				queryString.append(" AND l.commenmanceDate <= '" + criteria.getSQLEndDate() + "'");
			}
			if (!isEmpty(criteria.getCustomerId())) {
				queryString.append(" AND l.customer.id = '" + criteria.getCustomerId() + "'");
			}
			if (!isEmpty(criteria.getOrganizationId())) {
				queryString.append(" AND l.organization.id = '" + criteria.getOrganizationId() + "'");
			}
			if (!isEmpty(criteria.getSaleManId())) {
				queryString.append(" AND l.saleMan.id = '" + criteria.getSaleManId() + "'");
			}
			if (!isEmpty(criteria.getBranchId())) {
				queryString.append(" AND l.branch.id = '" + criteria.getBranchId() + "'");
			}
			if (!isEmpty(criteria.getProductId())) {
				queryString.append(" AND pi.product.id = '" + criteria.getProductId() + "'");
			}
			if (!isEmpty(criteria.getReferenceNo())) {
				queryString.append(" AND l.policyNo = '" + criteria.getReferenceNo() + "'");
			}
			/* Executed query */
			Query query = em.createQuery(queryString.toString());

			objectList = query.getResultList();
			for (Object[] b : objectList) {
				id = (String) b[0];
				policyNo = (String) b[1];
				proposalNo = (String) b[2];

				if (b[3] == null) {
					agent = "";
				} else {
					Agent a = (Agent) b[3];
					agent = a.getFullName();
				}
				if (b[5] == null) {
					Customer c = (Customer) b[4];
					customer = c.getFullName();
				} else {
					Organization org = (Organization) b[5];
					customer = org.getName();
				}
				branch = (String) b[6];
				premium = (Double) b[7];
				sumInsured = (Double) b[8];
				paymenttype = (PaymentType) b[9];
				submittedDate = (Date) b[10];
				if (resultMap.containsKey(policyNo)) {
					premium += resultMap.get(policyNo).getPremium();
					sumInsured += resultMap.get(policyNo).getSumInsured();
					resultMap.put(policyNo, new LPLC001(id, policyNo, proposalNo, agent, customer, branch, premium, sumInsured, paymenttype, submittedDate));
				} else {
					resultMap.put(policyNo, new LPLC001(id, policyNo, proposalNo, agent, customer, branch, premium, sumInsured, paymenttype, submittedDate));
				}
			}
			em.flush();

			logger.debug("find() method has been successfully finished.");
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy by LPLC001 : ", pe);
		}
		RegNoSorter<LPLC001> regNoSorter = new RegNoSorter<LPLC001>(new ArrayList<LPLC001>(resultMap.values()));
		return regNoSorter.getSortedList();
	}
}
