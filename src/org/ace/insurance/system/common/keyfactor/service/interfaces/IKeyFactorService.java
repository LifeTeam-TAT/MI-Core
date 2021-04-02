/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.keyfactor.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.keyfactor.KeyFactor;

public interface IKeyFactorService {
	public void addNewKeyFactor(KeyFactor KeyFactor);

	public void updateKeyFactor(KeyFactor KeyFactor);

	public void deleteKeyFactor(KeyFactor KeyFactor);
	
	public KeyFactor findKeyFactorById(String id);

	public List<KeyFactor> findAllKeyFactor();
}
