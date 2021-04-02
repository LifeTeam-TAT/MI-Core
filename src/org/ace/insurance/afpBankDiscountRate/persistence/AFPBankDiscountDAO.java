package org.ace.insurance.afpBankDiscountRate.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.afpBankDiscountRate.AFPBankDiscountRate;
import org.ace.insurance.afpBankDiscountRate.AFPR001;
import org.ace.insurance.afpBankDiscountRate.persistence.interfaces.IAFPBankDiscountDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("AFPBankDiscountDAO")
public class AFPBankDiscountDAO extends BasicDAO implements IAFPBankDiscountDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<AFPR001> findAFPBankDiscountRateDTOByProductGroupId(String productGroupId) {
		List<AFPR001> afpBankDiscountRateDTOList = new ArrayList<>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("Select New " + AFPR001.class.getName() + "(a.id,a.productGroup.name,a.bank.name,a.discountRate)");
			query.append(" From AFPBankDiscountRate a where a.productGroup.id=:productGroupId");
			Query q = em.createQuery(query.toString());
			q.setParameter("productGroupId", productGroupId);
			afpBankDiscountRateDTOList = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AFPBankDiscountRateDTO By ProductGroupId", pe);
		}
		return afpBankDiscountRateDTOList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAllAFPBankDiscountRateByProductGroup(String id) {
		try {
			StringBuffer query = new StringBuffer();
			query.append("Delete from AFPBankDiscountRate a where a.productGroup.id=:productGroupId");
			Query q = em.createQuery(query.toString());
			q.setParameter("productGroupId", id);
			q.executeUpdate();
		} catch (PersistenceException pe) {
			throw translate("Failed to Delete AFPBankDiscountRateDTO By ProductGroupId", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate) {
		try {
			em.persist(afpBankDiscountRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to Insert AFPBankDiscountRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateAFPBankDiscountRate(AFPBankDiscountRate afpBankDiscountRate) {
		try {
			em.merge(afpBankDiscountRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to Update AFPBankDiscountRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAFPBankDiscountRateById(String id) {
		try {
			StringBuffer query = new StringBuffer();
			query.append("Delete from AFPBankDiscountRate a where a.id=:id");
			Query q = em.createQuery(query.toString());
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to Delete AFPBankDiscountRate By Id", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AFPBankDiscountRate findAFPBankDiscountRateById(String id) {
		AFPBankDiscountRate afpBankDiscountRate = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("Select a from AFPBankDiscountRate a where a.id=:id");
			Query q = em.createQuery(query.toString());
			q.setParameter("id", id);
			afpBankDiscountRate = (AFPBankDiscountRate) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AFPBankDiscountRate By Id", pe);
		}
		return afpBankDiscountRate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findAFPDiscountRate(String bankId, String productGroupId) {
		double afpBankDiscountRate = 0.0;
		try {
			StringBuilder query = new StringBuilder();
			query.append("Select a.discountRate from AFPBankDiscountRate a where a.bank.id=:bankId AND a.productGroup.id=:productGroupId");
			Query q = em.createQuery(query.toString());
			q.setParameter("bankId", bankId);
			q.setParameter("productGroupId", productGroupId);
			afpBankDiscountRate = (double) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return afpBankDiscountRate;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AFPBankDiscountRate By Id", pe);
		}
		return afpBankDiscountRate;
	}

}
