package org.ace.insurance.system.common.coinsuranceRatio.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.ace.insurance.system.common.coinsuranceRatio.CoinsuranceRatio;
import org.ace.insurance.system.common.coinsuranceRatio.persistence.interfaces.ICoinsuranceRatioDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("CoinsuranceRatioDAO")
public class CoinsuranceRatioDAO extends BasicDAO implements ICoinsuranceRatioDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public List<CoinsuranceRatio> findCoinsuranceRatioListByProductGroupId(String ProductGroupId) {
		List<CoinsuranceRatio> coinsuranceRatioList = new ArrayList<>();
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("Select c from CoinsuranceRatio c where c.productGroup.id=:id order by c.setNo");
			Query query = em.createQuery(queryString.toString());
			query.setParameter("id", ProductGroupId);
			coinsuranceRatioList = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find CoinsuranceRatioList By ProductGroup Id", pe);
		}
		return coinsuranceRatioList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		try {
			em.persist(coinsuranceRatio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to add New CoinsuranceRatio", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCoinsuranceRatio(CoinsuranceRatio coinsuranceRatio) {
		try {
			em.merge(coinsuranceRatio);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update CoinsuranceRatio", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndateByCoInRatioId(CoinsuranceRatio coInRatio) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("Update CoinsuranceRatio c set c.endDate=:endDate where c.id=:id ");
			Query q = em.createQuery(query.toString());
			q.setParameter("endDate", coInRatio.getEndDate());
			q.setParameter("id", coInRatio.getId());
			q.executeUpdate();
		} catch (PersistenceException pe) {
			throw translate("Failed to update EnDate By CoInRatio Id", pe);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CoinsuranceRatio findCoinsuranceRatio(String productGroupId, Date date) {
		CoinsuranceRatio result = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT cr FROM CoinsuranceRatio cr WHERE cr.productGroup.id = :productGroupId AND cr.startDate <= :date AND COALESCE(cr.endDate,:date) >= :date");
			Query q = em.createQuery(query.toString());
			q.setParameter("productGroupId", productGroupId);
			q.setParameter("date", date, TemporalType.DATE);
			result = (CoinsuranceRatio) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find CoinsuranceRatio by ProductGroup and date", pe);
		}
		return result;
	}
}
