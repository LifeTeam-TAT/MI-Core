package org.ace.insurance.medical.policy.policyEditHistory.persistance.interfaces;

import org.ace.insurance.medical.policy.policyEditHistory.MedicalPolicyEditHistory;
import org.ace.java.component.persistence.exception.DAOException;

public interface IMedicalPolicyEditHistoryDAO {

	public void insert(MedicalPolicyEditHistory medicalPolicyHistory) throws DAOException;

}
