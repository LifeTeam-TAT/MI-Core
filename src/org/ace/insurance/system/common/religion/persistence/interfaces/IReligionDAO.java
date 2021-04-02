/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.religion.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.religion.Religion;
import org.ace.java.component.persistence.exception.DAOException;

public interface IReligionDAO {
	public void insert(Religion religion) throws DAOException;

	public void update(Religion religion) throws DAOException;

	public void delete(Religion religion) throws DAOException;

	public Religion findById(String id) throws DAOException;

	public List<Religion> findAll() throws DAOException;

	public List<Religion> findByCriteria(String criteria) throws DAOException;
}
