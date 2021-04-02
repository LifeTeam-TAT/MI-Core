package org.ace.insurance.system.configuration;

import javax.annotation.Resource;

import org.ace.insurance.system.configuration.interfaces.ISettingVariableDAO;
import org.ace.insurance.system.configuration.interfaces.ISettingVariableService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SettingVariableService")
public class SettingVariableService implements ISettingVariableService {

	@Resource(name = "SettingVariableDAO")
	private ISettingVariableDAO settingVariableDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public <T> T getSettingVariable(String key, T defaultValue) {
		if (key == null)
			throw new SystemException("SETTING_VARIABLE_KEY_NULL", "GET : Setting variable key cannnot be NULL");
		if (defaultValue == null)
			throw new SystemException("SETTING_VARIABLE_DEFAULT_VALUE_NULL", "GET : Setting variable default value cannnot be NULL");

		T ret = null;
		try {
			SettingVariable retVar = settingVariableDAO.findByKey(key);
			if (retVar == null) {
				settingVariableDAO.insert(new SettingVariable(key, defaultValue.toString()));
				ret = defaultValue;
			} else {
				if (defaultValue instanceof Integer) {
					ret = (T) new Integer(retVar.getValue());
				} else if (defaultValue instanceof Double) {
					ret = (T) new Double(retVar.getValue());
				} else if (defaultValue instanceof Long) {
					ret = (T) new Long(retVar.getValue());
				} else if (defaultValue instanceof Float) {
					ret = (T) new Float(retVar.getValue());
				} else if (defaultValue instanceof String) {
					ret = (T) retVar.getValue();
				} else {
					throw new SystemException("SETTING_VARIABLE_TYPE_NOT_SUPPORT", "Setting variable type not supported for  <" + retVar.getValue().getClass() + ">");
				}
			}
		} catch (DAOException de) {
			throw new SystemException(de.getErrorCode(), "Faield to insert setting variable", de);
		} catch (Exception e) {
			throw new SystemException("FAIL_GET_SETTING_VARIABLE", "Faield to get setting variable", e);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T> void setSettingVariable(String key, T value) {
		if (key == null)
			throw new SystemException("SETTING_VARIABLE_KEY_NULL", "SET : Setting variable key cannnot be NULL");
		if (value == null)
			throw new SystemException("SETTING_VARIABLE_VALUE_NULL", "SET : Setting variable value cannnot be NULL");
		try {
			SettingVariable retVar = settingVariableDAO.findByKey(key);
			if (retVar == null) {
				settingVariableDAO.insert(new SettingVariable(key, value.toString()));
			} else {
				retVar.setValue(value.toString());
				settingVariableDAO.update(retVar);
			}
		} catch (DAOException de) {
			throw new SystemException(de.getErrorCode(), "Faield to set setting variable", de);
		}

	}
}
