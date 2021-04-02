/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.company.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.company.CMY001;
import org.ace.insurance.system.common.company.Company;
import org.ace.insurance.system.common.company.persistence.interfaces.ICompanyDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CompanyDAO")
@SuppressWarnings("unchecked")
public class CompanyDAO extends BasicDAO implements ICompanyDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Company company) throws DAOException {
		try {
			em.persist(company);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Company", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Company company) throws DAOException {
		try {
			em.merge(company);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Company", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Company company) throws DAOException {
		try {
			company = em.merge(company);
			em.remove(company);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Company", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Company findById(String id) throws DAOException {
		Company result = null;
		try {
			result = em.find(Company.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Company", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Company> findAll() throws DAOException {
		List<Company> result = null;
		try {
			Query q = em.createNamedQuery("Company.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Company", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CMY001> findAll_CMY001() throws DAOException {
		List<CMY001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + CMY001.class.getName() + "(");
			buffer.append("c.id, c.name, c.address.permanentAddress, c.address.township.name, ");
			buffer.append("c.contentInfo.phone, c.contentInfo.mobile, c.contentInfo.email) FROM Company c");
			Query q = em.createQuery(buffer.toString());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Faield to find all CMY001.", pe);
		}
		return result;
	}
}
