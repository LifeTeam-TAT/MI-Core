/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.bank.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.filter.bankCustomer.BNK001;
import org.ace.insurance.system.common.bank.Bank;
import org.ace.insurance.system.common.bank.COA001;
import org.ace.insurance.system.common.bank.persistence.interfaces.IBankDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("BankDAO")
public class BankDAO extends BasicDAO implements IBankDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Bank bank) throws DAOException {
		try {
			em.persist(bank);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Bank", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Bank bank) throws DAOException {
		try {
			em.merge(bank);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Bank", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Bank bank) throws DAOException {
		try {
			bank = em.merge(bank);
			em.remove(bank);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Bank", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Bank findById(String id) throws DAOException {
		Bank result = null;
		try {
			result = em.find(Bank.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Bank", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BNK001> findAll() throws DAOException {
		List<BNK001> result = null;
		try {
			Query q = em.createQuery("SELECT NEW " + BNK001.class.getName() + "( b.id, b.name, b.description, b.acode) FROM Bank b ORDER BY b.name ASC");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Bank", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<COA001> findCAOByAType() throws DAOException {
		List<COA001> result = null;
		try {
			Query query = em.createQuery("SELECT NEW " + COA001.class.getName() + "(a.id, a.acCode, a.acName) FROM Coa a WHERE a.acType='A' AND substring(a.acCode,4,3)<>'000' ");
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find CAO By AType", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BNK001> findACodeNotNull() throws DAOException {
		List<BNK001> result = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append("SELECT NEW " + BNK001.class.getName() + "(b.id,b.name,b.description,b.acode)");
			str.append(" FROM Bank b WHERE b.acode IS NOT NULL");
			Query query = em.createQuery(str.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find All BNK001 which ACode is Not Null", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Bank findByCSCBankCode(String cscBankCode) throws DAOException {
		Bank result = null;
		try {
			Query query = em.createQuery("SELECT b FROM Bank b WHERE b.cscBankCode=:cscBankCode");
			query.setParameter("cscBankCode", cscBankCode);
			result = (Bank) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find bank by cscbankcode", pe);
		}
		return result;
	}
}
