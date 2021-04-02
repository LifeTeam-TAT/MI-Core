package org.ace.insurance.medical.process.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.process.Process;
import org.ace.java.component.persistence.exception.DAOException;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the <code>Process</code>
 *          object.
 * 
 ***************************************************************************************/
public interface IProcessDAO {
	/**
	 * 
	 * @param {@link Process} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Insert Process data into DB.
	 */
	public void insert(Process process) throws DAOException;

	/**
	 * 
	 * @param {@link Process} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Update Process data into DB.
	 */
	public void update(Process process) throws DAOException;

	/**
	 * 
	 * @param {@link Process} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Delete Process data from DB.
	 */
	public void delete(Process process) throws DAOException;

	/**
	 * 
	 * @returnA {@link List} of {@link Process} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find all Process data from DB.
	 */
	public List<Process> findAll() throws DAOException;

	public String findByName(String name) throws DAOException;
}
