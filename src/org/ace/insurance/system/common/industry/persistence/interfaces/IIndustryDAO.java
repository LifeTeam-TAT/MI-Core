/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.industry.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.industry.Industry;
import org.ace.java.component.persistence.exception.DAOException;

public interface IIndustryDAO {
	public void insert(Industry Industry) throws DAOException;

	public void update(Industry Industry) throws DAOException;

	public void delete(Industry Industry) throws DAOException;

	public Industry findById(String id) throws DAOException;

	public List<Industry> findAll() throws DAOException;

	public List<Industry> findByCriteria(String criteria) throws DAOException;
}
