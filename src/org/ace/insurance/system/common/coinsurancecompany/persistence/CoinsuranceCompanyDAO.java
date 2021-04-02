package org.ace.insurance.system.common.coinsurancecompany.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuredProductGroup;
import org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This serves as the DAO implementation of the {@link ICoinsuranceCompanyDAO}
 * to manipulate the Co-insurance Company objects.
 * 
 * @author ACN
 * @version 1.0.0
 * @Date 2013/05/07
 */
@Repository("CoinsuranceCompanyDAO")
public class CoinsuranceCompanyDAO extends BasicDAO implements ICoinsuranceCompanyDAO {

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#insert(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(CoinsuranceCompany coinsuranceCompany) throws DAOException {
		try {
			em.persist(coinsuranceCompany);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Co-insurance Company (ID : " + coinsuranceCompany.getId() + ")", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#update(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CoinsuranceCompany coinsuranceCompany) throws DAOException {
		try {
			em.merge(coinsuranceCompany);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Co-insurance Company (ID : " + coinsuranceCompany.getId() + ")", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#delete(org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(CoinsuranceCompany coinsuranceCompany) throws DAOException {
		String id = coinsuranceCompany.getId();
		try {
			coinsuranceCompany = em.merge(coinsuranceCompany);
			em.remove(coinsuranceCompany);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete Co-insurance Company (ID : " + id + ")", pe);
		}
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#findById(java.lang.String)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CoinsuranceCompany findById(String id) throws DAOException {
		CoinsuranceCompany ret = null;
		try {
			ret = em.find(CoinsuranceCompany.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve Co-insurance Company (ID : " + id + ")", pe);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findAll() throws DAOException {
		List<CoinsuranceCompany> ret = null;
		try {
			Query q = em.createNamedQuery("CoinsuranceCompany.findAll");
			ret = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve Co-insurance Companies", pe);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#isPercentageAvailable(org.ace.insurance.product.ProductGroup,
	 *      boolean)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isPercentageAvailable(ProductGroup productGroup, double percentage, CoinsuranceCompany company) throws DAOException {
		boolean ret = true;
		try {
			if (percentage > 100)
				throw new PersistenceException("The entered percentage value is more than 100.");

			StringBuffer query = new StringBuffer();
			query.append("SELECT SUM(c.precentage) FROM CoinsuredProductGroup c WHERE c.productGroup = :productGroup");
			Query q = em.createQuery(query.toString()).setParameter("productGroup", productGroup);
			Object result = q.getSingleResult();

			Query q1 = em.createQuery("SELECT SUM(c.precentage) FROM CoinsuredProductGroup c WHERE c.productGroup = :productGroup AND c.coinsuranceCompany = :company");
			q1.setParameter("productGroup", productGroup);
			q1.setParameter("company", company);
			Object oldResult = q1.getSingleResult();
			em.flush();

			if (result != null) {
				double retPercent = Double.parseDouble(result.toString());
				if (retPercent > 100)
					throw new PersistenceException("The assigned percentage value in DB is more than 100.");
				if (oldResult != null) {
					double oldPercent = Double.parseDouble(oldResult.toString());
					retPercent = retPercent - oldPercent;
				}
				if (percentage + retPercent > 100) {
					ret = false;
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to check percentage availability.", pe);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#findByProductGroupId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findByProductGroupId(String productGroupId) throws DAOException {
		List<CoinsuranceCompany> ret = null;
		try {
			Query q = em.createNamedQuery("CoinsuranceCompany.findByGroup");
			ret = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve Co-insurance Companies by Product Group", pe);
		}
		return ret;
	}

	/**
	 * @see org.ace.insurance.system.common.coinsurancecompany.persistence.interfaces.ICoinsuranceCompanyDAO#findCoinsuredProductGroupsByProductGroupId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuredProductGroup> findCoinsuredProductGroupsByProductGroupId(String productGroupId) throws DAOException {
		List<CoinsuredProductGroup> ret = null;
		try {
			Query q = em.createNamedQuery("CoinsuredProductGroup.findByGroup");
			q.setParameter("groupId", productGroupId);
			ret = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve Coinsured Product Groups by Product Group", pe);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceCompany> findByCriteria(String criteria) throws DAOException {
		List<CoinsuranceCompany> result = null;
		try {
			Query q = em.createQuery("Select c from CoinsuranceCompany c where c.name Like '" + criteria + "%'");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of CoinsuranceCompany.", pe);
		}
		return result;
	}
}
