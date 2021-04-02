package org.ace.insurance.medical.process.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.process.Process;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the
 *          <code>Process</code> object.
 * 
 * 
 ***************************************************************************************/
public interface IProcessService {
	/**
	 * 
	 * @param {@link Process} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Add new Process .
	 */
	public void addNewProcess(Process process);

	/**
	 * 
	 * @param {@link Process} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Edit Process data.
	 */
	public void updateProcess(Process process);

	/**
	 * 
	 * @param {@link Process} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Delete Process data.
	 */
	public void deleteProcess(Process process);

	/**
	 * 
	 * @return {@link List} of {@link Process} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find all Process data.
	 */
	public List<Process> findAllProcess();
}
