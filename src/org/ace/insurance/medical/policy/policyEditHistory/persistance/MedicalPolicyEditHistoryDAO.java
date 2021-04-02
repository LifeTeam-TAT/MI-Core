package org.ace.insurance.medical.policy.policyEditHistory.persistance;

import javax.persistence.PersistenceException;

import org.ace.insurance.medical.policy.policyEditHistory.MedicalPolicyEditHistory;
import org.ace.insurance.medical.policy.policyEditHistory.persistance.interfaces.IMedicalPolicyEditHistoryDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalPolicyEditHistoryDAO")
public class MedicalPolicyEditHistoryDAO extends BasicDAO implements IMedicalPolicyEditHistoryDAO {

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MedicalPolicyEditHistory medicalPolicyHistory) throws DAOException {
		try {
			em.persist(medicalPolicyHistory);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert MedicalPolicyEditHistory", pe);
		}
	}

}
