package org.ace.insurance.policytermination.persistance;

import org.ace.insurance.policytermination.persistance.interfaces.IPolicyTerminationDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;

@Repository("PolicyTerminationDAO")
public class PolicyTerminationDAO extends BasicDAO implements IPolicyTerminationDAO {

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void delete(PolicyTermination policyTermination) {
	//
	// try {
	// em.remove(policyTermination);
	// em.flush();
	// } catch (PersistenceException pe) {
	// throw translate("Failed to delete PolicyTermination", pe);
	// }
	//
	// }

	// @Override
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void insert(PolicyTermination policyTermination) {
	// try {
	// em.persist(policyTermination);
	// em.flush();
	// } catch (PersistenceException pe) {
	// throw translate("Failed to insert PolicyTermination ", pe);
	// }
	//
	// }

}
