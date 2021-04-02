package org.ace.insurance.medical.process.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.medical.process.Process;
import org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO;
import org.ace.insurance.medical.process.service.interfaces.IProcessService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the
 *          <code>Process</code> object.
 * 
 * 
 ***************************************************************************************/
@Service(value = "ProcessService")
public class ProcessService extends BaseService implements IProcessService {

	@Resource(name = "ProcessDAO")
	private IProcessDAO processDAO;

	/**
	 * @see org.ace.insurance.medical.process.service.interfaces.IProcessService
	 *      #addNewProcess(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProcess(Process process) {
		try {
			processDAO.insert(process);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Process", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.service.interfaces.IProcessService
	 *      #updateProcess(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProcess(Process process) {
		try {
			processDAO.update(process);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Process", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.service.interfaces.IProcessService
	 *      #deleteProcess(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProcess(Process process) {
		try {
			processDAO.delete(process);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Process", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.service.interfaces.IProcessService
	 *      #findAllProcess(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Process> findAllProcess() {
		List<Process> result = null;
		try {
			result = processDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Process)", e);
		}
		return result;
	}

}
