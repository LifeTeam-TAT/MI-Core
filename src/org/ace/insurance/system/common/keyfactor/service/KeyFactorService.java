/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.keyfactor.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.persistence.interfaces.IKeyFactorDAO;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "KeyFactorService")
public class KeyFactorService extends BaseService implements IKeyFactorService {

	@Resource(name = "KeyFactorDAO")
	private IKeyFactorDAO keyFactorDAO;

	public void addNewKeyFactor(KeyFactor keyFactor) {
		try {
			keyFactorDAO.insert(keyFactor);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new KeyFactor", e);
		}
	}

	public void updateKeyFactor(KeyFactor keyFactor) {
		try {
			keyFactorDAO.update(keyFactor);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a KeyFactor", e);
		}
	}

	public void deleteKeyFactor(KeyFactor keyFactor) {
		try {
			keyFactorDAO.delete(keyFactor);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a KeyFactor", e);
		}
	}

	public List<KeyFactor> findAllKeyFactor() {
		List<KeyFactor> result = null;
		try {
			result = keyFactorDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of KeyFactor)", e);
		}
		return result != null ? result : Collections.emptyList();
	}

	public KeyFactor findKeyFactorById(String id) {
		KeyFactor result = null;
		try {
			result = keyFactorDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a KeyFactor (ID : " + id + ")", e);
		}
		return result;
	}
}