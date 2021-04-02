/***************************************************************************************
 * @author YYK
 * @Date 2016-May-6
 * @Version 1.0
 * @Purpose This interface serves as the DAO Layer to manipulate the <code>District</code> object.
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.district.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.district.DIS001;
import org.ace.insurance.system.common.district.District;
import org.ace.insurance.system.common.district.DistrictCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface IDistrictDAO {

	/**
	 * This method serves as the DAO method to insert district instance into
	 * database.
	 * 
	 * @param District
	 * @throws DAOException
	 */
	public void insert(District District) throws DAOException;

	/**
	 * This method serves as the DAO method to update district instance into
	 * database.
	 * 
	 * @param District
	 * @throws DAOException
	 */
	public void update(District District) throws DAOException;

	/**
	 * This method serves as the DAO method to delete district instance into
	 * database.
	 * 
	 * @param District
	 * @throws DAOException
	 */
	public void delete(District District) throws DAOException;

	/**
	 * This method serves as the DAO method to find district instance by ID from
	 * database.
	 * 
	 * @param id
	 * @return District
	 * @throws DAOException
	 */
	public District findById(String id) throws DAOException;

	/**
	 * This method serves as the DAO method to find all district instances from
	 * database.
	 * 
	 * @return List<District>
	 * @throws DAOException
	 */
	public List<District> findAll() throws DAOException;

	/**
	 * This method serves as the DAO method to find District001 instance by
	 * criteria from database.
	 * 
	 * @param criteria
	 * @return List<District001>
	 * @throws DAOException
	 */
	public List<DIS001> findByCriteria(DistrictCriteria criteria) throws DAOException;

	/**
	 * This method serves as the DAO method to find all District001 from
	 * database.
	 * 
	 * @param
	 * @return List<District001>
	 * @throws DAOException
	 */
	public List<DIS001> findAll_DIS001() throws DAOException;

	/**
	 * This method serves as the DAO method to delete district instance by id
	 * from database.
	 * 
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public void deleteDistrictById(String districtId) throws DAOException;
}
