/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bankBranch.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.bankBranch.BKB001;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.bankBranch.persistence.interfaces.IBankBranchDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BankBranchDAO")
public class BankBranchDAO extends BasicDAO implements IBankBranchDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(BankBranch bankBranch) throws DAOException {
		try {
			em.persist(bankBranch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert BankBranch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(BankBranch bankBranch) throws DAOException {
		try {
			em.merge(bankBranch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update BankBranch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(BankBranch bankBranch) throws DAOException {
		try {
			bankBranch = em.merge(bankBranch);
			em.remove(bankBranch);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update BankBranch", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public BankBranch findById(String id) throws DAOException {
		BankBranch result = null;
		try {
			result = em.find(BankBranch.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find BankBranch", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BankBranch> findAll() throws DAOException {
		List<BankBranch> result = null;
		try {
			Query q = em.createNamedQuery("BankBranch.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of BankBranch", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BKB001> findAll_BKB001() throws DAOException {
		List<BKB001> result = new ArrayList<BKB001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + BKB001.class.getName() + "(");
			buffer.append("b.id, b.name, b.bank.name, b.branchCode ) FROM BankBranch b ORDER BY  b.bank.name");
			Query query = em.createQuery(buffer.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of BKB001", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BKB001> findAllAfp_BKB001(String productGroupId) throws DAOException {
		List<BKB001> result = new ArrayList<BKB001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + BKB001.class.getName() + "(");
			buffer.append("b.id, b.name, b.bank.name, b.branchCode, r.discountRate ) FROM BankBranch b ");
			buffer.append("JOIN AFPBankDiscountRate r ON b.bank.id = r.bank.id ");
			buffer.append("WHERE b.id IS NOT NULL and r.productGroup.id = :productGroupId ORDER BY  b.bank.name ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("productGroupId", productGroupId);
			result = (List<BKB001>) query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of BKB001", pe);
		}
		return result;
	}
}
