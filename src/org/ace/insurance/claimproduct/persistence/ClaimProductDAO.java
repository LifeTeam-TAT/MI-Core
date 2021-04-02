/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct.persistence;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.persistence.interfaces.IClaimProductDAO;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.KeyFactorConfig;
import org.ace.insurance.product.service.interfaces.IKeyFactorConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ClaimProductDAO")
public class ClaimProductDAO extends BasicDAO implements IClaimProductDAO {

	@Resource(name = "KeyFactorConfigLoader")
	private IKeyFactorConfigLoader keyFactorConfigLoader;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ClaimProduct claimProduct) throws DAOException {
		try {
			em.persist(claimProduct);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ClaimProduct", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ClaimProduct claimProduct) throws DAOException {
		try {
			em.merge(claimProduct);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimProduct", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ClaimProduct claimProduct) throws DAOException {
		try {
			claimProduct = em.merge(claimProduct);
			em.remove(claimProduct);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ClaimProduct", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimProduct findById(String id) throws DAOException {
		ClaimProduct result = null;
		try {
			result = em.find(ClaimProduct.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ClaimProduct", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findAll() throws DAOException {
		List<ClaimProduct> result = null;
		try {
			Query q = em.createNamedQuery("ClaimProduct.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimProduct", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findByInsuranceType(InsuranceType insuranceType) throws DAOException {
		List<ClaimProduct> result = null;
		try {
			Query q = em.createNamedQuery("ClaimProduct.findByInsuranceType");
			q.setParameter("insuranceType", insuranceType);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimProduct by InsuranceType ", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws DAOException {
		List<KFRefValue> result = null;
		try {
			KeyFactorConfig kfConfig = keyFactorConfigLoader.getKeyFactorConfig(entityName);
			if (kfConfig != null) {
				String stQuery = kfConfig.getQuery(insuranceType);
				Query q = em.createQuery(stQuery);
				if (insuranceType != null) {
					q.setParameter("insuranceType", insuranceType);
				}
				result = q.getResultList();
				em.flush();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Claim Product reference type by InsuranceType ", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findAllClaimProduct(List<String> claimProducts) {
		List<ClaimProduct> result = null;
		try {
			Query q = em.createQuery("select s from  ClaimProduct s where s.id IN :claimProducts");
			q.setParameter("claimProducts", claimProducts);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ClaimProduct by InsuranceType ", pe);
		}
		return result;
	}
}
