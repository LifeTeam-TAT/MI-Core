package org.ace.insurance.system.configuration.interfaces;

public interface ISettingVariableService {	
	/**
	 * To get a setting value with given key. 
	 * If setting variable with given key has already defined in system, the return value will be get from the repository.
	 * Unless, a new setting variable will be defined in system with given key and default value, and return given default value.
	 * 
	 * @param key - the setting variable's key to be get with 
	 * @param defaultValue - the default value of setting variable (type can be String, int, double, float, long, short)
	 * @return - the setting variable's value and type can be as given dafault value 
	 */
	public <T> T getSettingVariable(String key, T defaultValue);
		
	/**
	 * To set the setting variable (key & value) in the system.
	 * If the setting variable with given key is new in the system, a new one will be defined in the system with given key and value.
	 * Unless, the setting variable value will be updated.
	 * 
	 * @param key - setting variable's key to be set with
	 * @param value - setting variable's value to be set with (type can be String, int, double, float, long, short)
	 */
	public <T> void setSettingVariable(String key, T value);
}
