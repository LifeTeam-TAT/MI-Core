/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.country.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.country.CTY001;
import org.ace.insurance.system.common.country.Country;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICountryDAO {
	public void insert(Country Country) throws DAOException;

	public void update(Country Country) throws DAOException;

	public void delete(Country Country) throws DAOException;

	public Country findById(String id) throws DAOException;

	public List<Country> findAll() throws DAOException;

	public List<CTY001> findAll_CTY001() throws DAOException;

	public String findNameById(String id) throws DAOException;
	
	public Country findCountryByName(String name);
}
