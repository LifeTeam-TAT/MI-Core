package org.ace.insurance.product.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.product.PROGRP002;
import org.ace.insurance.product.PROGRP003;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.persistence.interfaces.IProductGroupDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProductGroupDAO")
public class ProductGroupDAO extends BasicDAO implements IProductGroupDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ProductGroup productGroup) throws DAOException {
		try {
			em.persist(productGroup);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Product", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ProductGroup productGroup) throws DAOException {
		try {
			em.merge(productGroup);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ProductGroup", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ProductGroup productGroup) throws DAOException {
		try {
			productGroup = em.merge(productGroup);
			em.remove(productGroup);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ProductGroup", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProductGroup> findAllProductGroup() throws DAOException {
		List<ProductGroup> result = null;
		try {
			Query q = em.createNamedQuery("ProductGroup.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductGroup", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PROGRP002> findAllByGroupType(ProductGroupType groupType) throws DAOException {
		List<PROGRP002> result = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT NEW " + PROGRP002.class.getName() + "(p.id, p.name, p.groupType) FROM ProductGroup p ");
			if (groupType != null)
				builder.append(" WHERE p.groupType = :groupType ");
			Query query = em.createQuery(builder.toString());
			if (groupType != null)
				query.setParameter("groupType", groupType);
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all by group type ", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductGroup findProductGroupById(String id) throws DAOException {
		ProductGroup result = null;
		try {
			result = em.find(ProductGroup.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Product Group", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<PROGRP003> findAll_PROGRP003() throws DAOException {
		List<PROGRP003> result = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT NEW " + PROGRP003.class.getName());
			builder.append("(p.id, p.name, p.groupType, p.policyNoPrefix, p.proposalNoPrefix, p.fleetDiscount, p.underSession13, ");
			builder.append("p.underSession25, p.maxSumInsured) FROM ProductGroup p ");
			Query query = em.createQuery(builder.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all by group type ", pe);
		}

		return result;
	}
}
