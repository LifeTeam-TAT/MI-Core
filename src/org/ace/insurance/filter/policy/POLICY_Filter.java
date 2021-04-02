package org.ace.insurance.filter.policy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.filter.policy.interfaces.IPOLICY_Filter;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("POLICY_Filter")
public class POLICY_Filter extends BasicDAO implements IPOLICY_Filter {

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<POLICY001> findPolicyByCriteria(AllPolicyCriteria policyCriteria) throws DAOException {
		List<POLICY001> result = new ArrayList<>();
		try {
			StringBuilder buffer = new StringBuilder();
			buffer.append("SELECT NEW " + POLICY001.class.getName() + "(p.policyNo, p.activedPolicyStartDate, p.activedPolicyEndDate, ");
			buffer.append("CONCAT(TRIM(p.agent.name.firstName), ' ', TRIM(p.agent.name.middleName), ' ', TRIM(p.agent.name.lastName)), p.branch.preFix) ");
			if (policyCriteria.getInsuranceType() != null) {
				if (InsuranceType.LIFE.equals(policyCriteria.getInsuranceType())) {
					buffer.append(" FROM LifePolicy p LEFT OUTER JOIN p.customer c WHERE p.policyNo IS NOT NULL");
				}
			}

			if (policyCriteria != null && policyCriteria.getCriteriaValue() != null) {
				switch (policyCriteria.getPolicyCriteriaItems()) {
					case CUSTOMERNAME:
						buffer.append(" AND CONCAT(TRIM(c.initialId),' ' ,TRIM(c.name.firstName),' ', TRIM(CONCAT(TRIM(c.name.middleName), ' ')), TRIM(c.name.lastName))");
						buffer.append(" LIKE :param");
						break;
					case POLICYNO:
						buffer.append(" AND p.policyNo LIKE :param");
						break;
					case ORGANIZATIONNAME:
						buffer.append(" AND p.organization.name LIKE :param");
						break;
					default:
						break;
				}
			}

			Query query = em.createQuery(buffer.toString());
			if (policyCriteria != null && policyCriteria.getCriteriaValue() != null)
				query.setParameter("param", "%" + policyCriteria.getCriteriaValue() + "%");

			query.setMaxResults(30);
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy by LPLC001 : ", pe);
		}
		return result;
	}

}
