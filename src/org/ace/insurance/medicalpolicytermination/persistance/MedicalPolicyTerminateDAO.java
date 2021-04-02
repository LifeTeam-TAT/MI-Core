package org.ace.insurance.medicalpolicytermination.persistance;

import org.ace.insurance.medicalpolicytermination.persistance.interfaces.IMedicalPolicyTerminateDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("MedicalPolicyTerminateDAO")
public class MedicalPolicyTerminateDAO extends BasicDAO implements IMedicalPolicyTerminateDAO {

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void insert(MedicalPolicyTerminate policyTermination) {
	// try {
	// em.persist(policyTermination);
	// em.flush();
	// } catch (PersistenceException pe) {
	// throw translate("Failed to insert MedicalPolicyTerminate ", pe);
	// }
	//
	// }

}
