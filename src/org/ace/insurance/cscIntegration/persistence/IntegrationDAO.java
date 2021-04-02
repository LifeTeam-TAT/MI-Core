package org.ace.insurance.cscIntegration.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.cscIntegration.Integration;
import org.ace.insurance.cscIntegration.persistence.interfaces.IIntegrationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("IntegrationDAO")
public class IntegrationDAO extends BasicDAO implements IIntegrationDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Integration> findByAcePolicyNo(String policyNo, InsuranceType type) throws DAOException {
		List<Integration> result = new ArrayList<>();
		List<Object[]> objectList = new ArrayList<Object[]>();
		try {
			String queryString = "SELECT POLICYTYPE, ACEPOLICYNO, CSCPOLICYNO, ACECUSTOMERNAME, CSCCUSTOMERKEY, ACEAGENTNAME, CSCAGENTKEY, BRANCH FROM INTEGRATION WHERE ACEPOLICYNO LIKE '%"
					+ policyNo + "%' AND POLICYTYPE ='" + type.getLabel() + "' ORDER BY ACEPOLICYNO, CSCPOLICYNO";
			Query q = em.createNativeQuery(queryString);
			q.setMaxResults(30);
			objectList = q.getResultList();
			for (Object[] b : objectList) {
				result.add(new Integration((String) b[0], (String) b[1], (String) b[2], (String) b[3], (String) b[4],
						(String) b[5], (String) b[6], (String) b[7]));
			}
			em.flush();
		} catch (NoResultException pe) {
			result = null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find CSC policy by Ace policyNo : " + policyNo, pe);
		}
		return result;
	}

}
