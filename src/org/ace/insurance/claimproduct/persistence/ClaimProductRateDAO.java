/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimProductRate;
import org.ace.insurance.claimproduct.persistence.interfaces.IClaimProductRateDAO;
import org.ace.insurance.common.KeyFactorType;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.manage.claimProduct.ClaimProductRateDto;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ClaimProductRateDAO")
public class ClaimProductRateDAO extends BasicDAO implements IClaimProductRateDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimProductRate insert(ClaimProductRate claimProductRate) throws DAOException {
		try {
			em.persist(claimProductRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClaimProductRate", pe);
		}
		return claimProductRate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ClaimProductRate claimProductRate) throws DAOException {
		try {
			claimProductRate = em.merge(claimProductRate);
			em.remove(claimProductRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete ClaimProductRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClaimProductRate claimProductRate) throws DAOException {
		try {
			em.merge(claimProductRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimProductRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimProductRate findById(String id) throws DAOException {
		ClaimProductRate result = null;
		try {
			result = em.find(ClaimProductRate.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimProductRate", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProductRate> findByClaimProduct(String productId) throws DAOException {
		List<ClaimProductRate> result = null;
		try {
			Query q = em.createNamedQuery("ClaimProductRate.findByClaimProductId");
			q.setParameter("productId", productId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimProductRate by ClaimProduct : ID " + productId, pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProductRateDto> findDtoByClaimProduct(String productId) throws DAOException {
		List<ClaimProductRateDto> result = null;
		try {
			StringBuffer query = new StringBuffer(" SELECT NEW org.ace.insurance.web.manage.claimProduct.ClaimProductRateDto(c.id,c.rate,c.claimProductRateKeyFactors"
					+ ") FROM ClaimProductRate c WHERE c.claimProduct.id = :productId");
			Query q = em.createQuery(query.toString());
			q.setParameter("productId", productId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimProductRateDto by ClaimProduct : ID " + productId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double findClaimProductRate(Map<KeyFactor, String> keyfatorValueMap, ClaimProduct product) throws DAOException {
		Double result = null;
		try {
			Iterator<KeyFactor> iterator = keyfatorValueMap.keySet().iterator();
			KeyFactor keyFactor = null;
			KeyFactorType keyFactorType = null;
			int kfCount = 0;
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT cp.rate FROM ClaimProductRate cp");
			while (iterator.hasNext()) {
				kfCount++;
				iterator.next();
				if (iterator.hasNext()) {
					queryString.append(" ,ClaimProductRateKeyFactor key" + kfCount);
				} else {
					queryString.append(" ,ClaimProductRateKeyFactor key" + kfCount + " ");
				}
			}

			queryString.append(" WHERE cp.claimProduct.id = :claimProductId");

			kfCount = 0;
			iterator = keyfatorValueMap.keySet().iterator();
			while (iterator.hasNext()) {
				kfCount++;
				iterator.next();
				queryString.append(" AND key" + kfCount + ".rateId = cp.id");
			}

			kfCount = 0;
			iterator = keyfatorValueMap.keySet().iterator();
			while (iterator.hasNext()) {
				kfCount++;
				keyFactor = iterator.next();
				keyFactorType = keyFactor.getKeyFactorType();
				switch (keyFactorType) {
					case FIXED:
					case REFERENCE: {
						queryString.append(" AND key" + kfCount + ".value = :value" + kfCount);
					}
						break;

					case FROM_TO: {
						queryString.append(" AND key" + kfCount + ".from <= :value" + kfCount + " AND key" + kfCount + ".to >= :value" + kfCount);
					}
						break;
				}

			}

			Query query = em.createQuery(queryString.toString());
			query.setParameter("claimProductId", product.getId());
			kfCount = 0;
			iterator = keyfatorValueMap.keySet().iterator();
			while (iterator.hasNext()) {
				kfCount++;
				keyFactor = iterator.next();
				keyFactorType = keyFactor.getKeyFactorType();
				switch (keyFactorType) {
					case FIXED:
					case REFERENCE: {
						query.setParameter("value" + kfCount, keyfatorValueMap.get(keyFactor));
					}
						break;

					case FROM_TO: {
						query.setParameter("value" + kfCount, Double.parseDouble(keyfatorValueMap.get(keyFactor)));
					}
						break;
				}
			}

			result = (Double) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimProductRate", pe);
		}

		return result;
	}
}
