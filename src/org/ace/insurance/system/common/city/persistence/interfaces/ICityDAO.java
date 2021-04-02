/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.city.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.city.City;
import org.ace.java.component.persistence.exception.DAOException;

public interface ICityDAO {

	public void insert(City City) throws DAOException;

	public void update(City City) throws DAOException;

	public void delete(City City) throws DAOException;

	public City findById(String id) throws DAOException;

	public List<City> findAll() throws DAOException;

	public List<City> findByCriteria(String criteria) throws DAOException;

	public City findByName(String name) throws DAOException;

	public String findNameById(String id) throws DAOException;
}
