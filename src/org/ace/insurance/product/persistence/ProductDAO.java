/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.product.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.KeyFactorConfig;
import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductGroup;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.product.persistence.interfaces.IProductDAO;
import org.ace.insurance.product.service.interfaces.IKeyFactorConfigLoader;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProductDAO")
public class ProductDAO extends BasicDAO implements IProductDAO {

	@Resource(name = "KeyFactorConfigLoader")
	private IKeyFactorConfigLoader keyFactorConfigLoader;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(Product product) throws DAOException {
		try {
			em.persist(product);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Product", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Product product) throws DAOException {
		try {
			em.merge(product);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Product", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Product product) throws DAOException {
		try {
			product = em.merge(product);
			em.remove(product);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Product", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(ProductPremiumRate productPremiumRate) throws DAOException {
		try {
			em.persist(productPremiumRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ProductPremiumRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Product findById(String id) throws DAOException {
		Product result = null;
		try {
			result = em.find(Product.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Product", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findAll() throws DAOException {
		List<Product> result = null;
		try {
			Query q = em.createNamedQuery("Product.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Product", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findAllByNotEmptyProductPremiumRate() throws DAOException {
		List<Product> result = null;
		try {
			Query q = em.createNamedQuery("Product.findAllByNotEmptyProductPremiumRate");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Product by not empty ProductPremiumRate", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findAllByEmptyProductPremiumRate() throws DAOException {
		List<Product> result = null;
		try {
			Query q = em.createNamedQuery("Product.findAllByEmptyProductPremiumRate");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Product by empty ProductPremiumRate", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findByInsuranceType(InsuranceType insuranceType) throws DAOException {
		List<Product> result = null;
		try {
			Query q = em.createNamedQuery("Product.findByInsuranceType");
			q.setParameter("insuranceType", insuranceType);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Product by InsuranceType ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findProductByCurrencyType(InsuranceType insuranceType, Currency currency) throws DAOException {
		List<Product> result = null;
		try {
			Query q = em.createNamedQuery("Product.findProductByCurrencyType");
			q.setParameter("insuranceType", insuranceType);
			q.setParameter("currency", currency);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Product by InsuranceType ", pe);
		}
		return result;
	}

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
			throw translate("Failed to find all of Product by InsuranceType ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public KFRefValue findKFRefValueById(String entityName, String id) throws DAOException {
		KFRefValue result = null;
		try {
			KeyFactorConfig kfConfig = keyFactorConfigLoader.getKeyFactorConfig(entityName);
			String stQuery = kfConfig.getQuery(id);
			Query q = em.createQuery(stQuery);
			result = (KFRefValue) q.getSingleResult();
			em.flush();
		} catch (PersistenceException e) {
			throw translate("Faield to find KFRefValue by id. ( ID : " + id + " , Entity Name : " + entityName + ")", e);
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

	/** find productDTO by productGroupID **/
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PRO001> findProductDTOGroupById(String id) throws DAOException {
		List<PRO001> result = new ArrayList<PRO001>();
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT new org.ace.insurance.product.PRO001(");
			builder.append("p.id, p.productContent.name,p.maxValue,p.minValue,p.maxTerm,p.minTerm,p.premiumRateType)");
			builder.append(" FROM Product p WHERE p.productGroup.id=:id ORDER BY p.productContent.name ASC");
			Query q = em.createQuery(builder.toString());
			q.setParameter("id", id);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductDTO by GroupId ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public double findStampFeeRateByProductID(String id) throws DAOException {
		double result = 0.0;
		try {
			Query q = em.createQuery("SELECT p.stampFee FROM Product p WHERE p.id=:id");
			q.setParameter("id", id);
			result = (double) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductDTO by GroupId ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<PRO001> findAllProductName() throws DAOException {
		List<PRO001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + PRO001.class.getName());
			buffer.append("(p.id, c.name)");
			buffer.append("FROM Product p");
			buffer.append(" JOIN p.productContent c order by c.name");
			Query query = em.createQuery(buffer.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductDTO by GroupId ", pe);
		}
		return result;
	}
}
