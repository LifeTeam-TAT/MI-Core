package org.ace.insurance.medical.process.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.TableName;
import org.ace.insurance.medical.process.Process;
import org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the <code>Process</code>
 *          object.
 * 
 ***************************************************************************************/

@Repository("ProcessDAO")
public class ProcessDAO extends BasicDAO implements IProcessDAO {

	/**
	 * @see org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO
	 *      #insert(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Process process) throws DAOException {
		try {
			em.persist(process);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Process", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO
	 *      #update(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Process process) throws DAOException {
		try {
			em.merge(process);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Process", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO
	 *      #delete(org.ace.insurance.medical.process.Process)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Process process) throws DAOException {
		try {
			process = em.merge(process);
			em.remove(process);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Process", pe);
		}
	}

	/**
	 * @see org.ace.insurance.medical.process.persistence.interfaces.IProcessDAO
	 *      #findAll()
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Process> findAll() throws DAOException {
		List<Process> result = null;
		try {
			Query q = em.createNamedQuery("Process.findAll");
			Query qq = em.createNamedQuery("Process.findByName");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Process", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findByName(String name) throws DAOException {
		String result = "";
		try {
			Query qq = em.createNamedQuery("Process.findByName");
			qq.setParameter("processName", name);
			result = (String) qq.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Process", pe);
		}
		return result;
	}
}
