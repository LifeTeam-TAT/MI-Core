/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.keyfactor.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.persistence.exception.DAOException;

public interface IKeyFactorDAO {
	public void insert(KeyFactor KeyFactor) throws DAOException;

	public void update(KeyFactor KeyFactor) throws DAOException;

	public void delete(KeyFactor KeyFactor) throws DAOException;

	public KeyFactor findById(String id) throws DAOException;

	public List<KeyFactor> findAll() throws DAOException;
}
