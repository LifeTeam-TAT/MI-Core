/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.qualification.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.java.component.persistence.exception.DAOException;

public interface IQualificationDAO {
	public void insert(Qualification qualification) throws DAOException;

	public void update(Qualification qualification) throws DAOException;

	public void delete(Qualification qualification) throws DAOException;

	public Qualification findById(String id) throws DAOException;

	public List<Qualification> findAll() throws DAOException;

	public List<Qualification> findByCriteria(String criteria) throws DAOException;
}
