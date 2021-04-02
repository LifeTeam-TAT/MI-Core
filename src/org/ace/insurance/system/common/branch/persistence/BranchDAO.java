/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.branch.persistence;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.BRA002;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.persistence.interfaces.IBranchDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BranchDAO")
@SuppressWarnings("unchecked")
public class BranchDAO extends BasicDAO implements IBranchDAO {

	@Resource(name = "CSC_BRANCH_CONFIG")
	private Properties properties;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Branch branch) throws DAOException {
		try {
			em.persist(branch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Branch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Branch branch) throws DAOException {
		try {
			em.merge(branch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Branch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Branch branch) throws DAOException {
		try {
			branch = em.merge(branch);
			em.remove(branch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Branch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Branch findById(String id) throws DAOException {
		Branch result = null;
		try {
			result = em.find(Branch.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Branch", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Branch findByCode(String code) throws DAOException {
		Branch result = null;
		try {
			Query q = em.createNamedQuery("Branch.findByCode");
			q.setParameter("branchCode", code);
			result = (Branch) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Branch", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Branch> findAll() throws DAOException {
		List<Branch> result = null;
		try {
			Query q = em.createNamedQuery("Branch.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Branch", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BRA002> findAll_BRA002() throws DAOException {
		List<BRA002> result = null;
		try {
			StringBuffer buffer = new StringBuffer("Select NEW " + BRA002.class.getName());
			buffer.append("(b.id, b.name, b.branchCode, b.address, b.township.name, b.isCoInsuAccess) from Branch b ORDER BY b.name ASC");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all BRA002.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BRA001> findAll_BRA001() throws DAOException {
		List<BRA001> result = null;
		try {
			Query q = em.createQuery("Select NEW " + BRA001.class.getName() + "(b.id, b.name, b.branchCode, b.description) from Branch b ORDER BY b.name ASC");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all BRA001.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Branch findByCSCBranchCode(String cscBranchCode) throws DAOException {
		Branch result = null;
		try {
			String branchCode = properties.getProperty(cscBranchCode);
			result = findByCode(branchCode);
		} catch (PersistenceException pe) {
			throw translate("Failed to find Branch", pe);
		}
		return result;
	}

}
