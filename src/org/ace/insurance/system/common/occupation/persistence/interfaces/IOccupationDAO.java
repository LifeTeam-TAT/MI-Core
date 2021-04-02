/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.occupation.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.java.component.persistence.exception.DAOException;

public interface IOccupationDAO {
	public void insert(Occupation Occupation) throws DAOException;

	public void update(Occupation Occupation) throws DAOException;

	public void delete(Occupation Occupation) throws DAOException;

	public Occupation findById(String id) throws DAOException;

	public List<Occupation> findByInsuranceType(InsuranceType insuranceType) throws DAOException;

	public List<Occupation> findAll() throws DAOException;

	public List<Occupation> findByCriteria(String criteria) throws DAOException;
	
	public Occupation findOccupationByName(String name);
}
