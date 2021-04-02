/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.hospital.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.java.component.persistence.exception.DAOException;

public interface IHospitalDAO {
	public void insert(Hospital Hospital) throws DAOException;

	public void update(Hospital Hospital) throws DAOException;

	public void delete(Hospital Hospital) throws DAOException;

	public Hospital findById(String id) throws DAOException;

	public List<Hospital> findAll() throws DAOException;

	public List<Hospital> findByCriteria(String criteria) throws DAOException;
}
