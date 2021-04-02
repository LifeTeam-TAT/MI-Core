/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.typesOfSport.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.java.component.persistence.exception.DAOException;

public interface ITypesOfSportDAO {
	public void insert(TypesOfSport religion) throws DAOException;

	public void update(TypesOfSport religion) throws DAOException;

	public void delete(TypesOfSport religion) throws DAOException;

	public TypesOfSport findById(String id) throws DAOException;

	public List<TypesOfSport> findAll() throws DAOException;

	public List<TypesOfSport> findByCriteria(String criteria, int max) throws DAOException;
}
