package org.ace.insurance.system.configuration.interfaces;

import org.ace.insurance.system.configuration.SettingVariable;
import org.ace.java.component.persistence.exception.DAOException;

public interface ISettingVariableDAO {
	public void insert(SettingVariable variable) throws DAOException;
	public void update(SettingVariable variable) throws DAOException;
	public SettingVariable findByKey(String key) throws DAOException;	
}
