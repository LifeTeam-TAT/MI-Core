package org.ace.insurance.system.common.salesPoints.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.city.City;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.salesPoints.persistence.interfaces.ISalesPointsDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository("SalesPointsDAO")
public class SalesPointsDAO extends BasicDAO implements ISalesPointsDAO{
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SalesPoints salesPoints) throws DAOException{
		try {
			em.persist(salesPoints);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert SalesPoints", pe);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(SalesPoints salesPoints) throws DAOException{
		try {
			em.merge(salesPoints);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SalesPoints", pe);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(SalesPoints salesPoints) throws DAOException{
		try {
			salesPoints = em.merge(salesPoints);
			em.remove(salesPoints);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SalesPoints", pe);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public SalesPoints findById(String id) throws DAOException {
		SalesPoints result = null;
		try {
			result = em.find(SalesPoints.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find SalesPoints", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalesPoints> findAll() throws DAOException {
		List<SalesPoints> result = null;
		try {
			Query q = em.createNamedQuery("SalesPoints.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SalesPoints", pe);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public SalesPoints findByName(String name) throws DAOException {
		List<SalesPoints> result = null;
		try {
			Query q = em.createQuery("Select s from SalesPoints s where s.name = :name ");
			q.setParameter("name", name);
			result = q.getResultList();
			if (result.size() > 0) {
				return result.get(0);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by name of SalesPoints.", pe);
		}
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SalesPoints> findSalesPointsByBranch(String id) throws DAOException{
		List<SalesPoints> result = null;
		try {
			Query q = em.createQuery("Select s from SalesPoints s where s.branch.id = :id");
			q.setParameter("id", id);
			result = q.getResultList();
		em.flush();
			} catch (PersistenceException pe) {
				throw translate("Failed to find by branch of SalesPoints.", pe);
			}
		return result;
			
	}
		
	}

