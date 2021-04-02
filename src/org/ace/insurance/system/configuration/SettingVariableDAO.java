package org.ace.insurance.system.configuration;

import javax.persistence.PersistenceException;

import org.ace.insurance.system.configuration.interfaces.ISettingVariableDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SettingVariableDAO")
public class SettingVariableDAO extends BasicDAO implements ISettingVariableDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SettingVariable variable) throws DAOException {

		try {
			em.persist(variable);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert setting variable", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(SettingVariable variable) throws DAOException {
		try {
			em.merge(variable);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update setting variable", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SettingVariable findByKey(String key) throws DAOException {
		SettingVariable result = null;
		try {
			result = em.find(SettingVariable.class, key);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find setting variable", pe);
		}
		return result;
	}
}
