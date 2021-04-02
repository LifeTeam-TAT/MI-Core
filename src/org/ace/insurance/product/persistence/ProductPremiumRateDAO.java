/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.product.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.KeyFactorType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.product.ProductPremiumRateKeyFactor;
import org.ace.insurance.product.persistence.interfaces.IProductPremiumRateDAO;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("ProductPremiumRateDAO")
public class ProductPremiumRateDAO extends BasicDAO implements IProductPremiumRateDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductPremiumRate insert(ProductPremiumRate productPremiumRate) throws DAOException {
		try {
			em.persist(productPremiumRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert ProductPremiumRate", pe);
		}
		return productPremiumRate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(ProductPremiumRate productPremiumRate) throws DAOException {
		Query query = null;
		try {
			for (ProductPremiumRateKeyFactor keyFactor : productPremiumRate.getPremiumRateKeyFactor()) {
				query = em.createNamedQuery("ProductPremiumRateKeyFactor.deleteById");
				query.setParameter("id", keyFactor.getId());
				query.executeUpdate();
				em.flush();
			}
			query = em.createNamedQuery("ProductPremiumRate.deleteById");
			query.setParameter("id", productPremiumRate.getId());
			query.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete ProductPremiumRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ProductPremiumRate productPremiumRate) throws DAOException {
		try {
			em.merge(productPremiumRate);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ProductPremiumRate", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductPremiumRate findById(String id) throws DAOException {
		ProductPremiumRate result = null;
		try {
			result = em.find(ProductPremiumRate.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ProductPremiumRate", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductPremiumRate> findByProduct(String productId) throws DAOException {
		List<ProductPremiumRate> result = null;
		try {
			Query q = em.createNamedQuery("ProductPremiumRate.findByProductId");
			q.setParameter("productId", productId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductPremiumRate by Product : ID " + productId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductPremiumRate> findByAddOnId(String addOnId) throws DAOException {
		List<ProductPremiumRate> result = null;
		try {
			Query q = em.createNamedQuery("ProductPremiumRate.findByAddOnId");
			q.setParameter("addonId", addOnId);
			result = q.getResultList();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ProductPremiumRate by Product : ID " + addOnId, pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public Double findProductPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) throws DAOException {
		Double result = null;
		try {
			double sumInsuredAmount = 0.0;
			switch (product.getPremiumRateType()) {
				case BASED_ON_MAINCOVER_SUMINSURED: {
					for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
						if (KeyFactorIDConfig.isSumInsured(keyFactor)) {
							sumInsuredAmount = Double.parseDouble(keyfatorValueMap.get(keyFactor));
							if (sumInsuredAmount <= 0.0) {
								throw new SystemException(ErrorCode.NO_SUM_INSURED_KEYFACTOR, "There is SumInsured KeyFactor to calculate premium.");
							}
							keyfatorValueMap.remove(keyFactor);
							break;
						}
					}
				}
					break;
				case BASED_ON_OWN_SUMINSURED: {
					for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
						if (KeyFactorIDConfig.isSumInsured(keyFactor)) {
							sumInsuredAmount = Double.parseDouble(keyfatorValueMap.get(keyFactor));
							if (sumInsuredAmount <= 0.0) {
								throw new SystemException(ErrorCode.NO_SUM_INSURED_KEYFACTOR, "There is SumInsured KeyFactor to calculate premium.");
							}
							keyfatorValueMap.remove(keyFactor);
							break;
						}
					}
				}
					break;
				case USER_DEFINED_PREMIUM:
					break;
				default:
					break;
			}

			int i = 1;
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT p.premiumRate FROM ProductPremiumRate p, ");
			for (int index = 0; index < keyfatorValueMap.size(); index++) {
				queryString.append("ProductPremiumRateKeyFactor pr" + i++);
				if ((index + 1) != keyfatorValueMap.size()) {
					queryString.append(", ");
				} else {
					queryString.append(" ");
				}
			}

			queryString.append("WHERE ");
			i = 1;
			for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
				if (i > 1) {
					queryString.append(" AND ");
				}
				KeyFactorType kfValue = keyFactor.getKeyFactorType();
				if (kfValue.equals(KeyFactorType.FIXED) || kfValue.equals(KeyFactorType.REFERENCE) || kfValue.equals(KeyFactorType.ENUM)) {
					if (!isFireProduct(product.getId())) {
						queryString.append("pr" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					}
					queryString.append("pr" + i + ".value = :fixedValue_" + i);
				} else if (kfValue.equals(KeyFactorType.FROM_TO)) {
					if (!isFireProduct(product.getId())) {
						queryString.append("pr" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					}
					queryString.append("(pr" + i + ".from <= :value_" + i + " AND pr" + i + ".to >= :value_" + i + ")");
				}
				i++;
			}
			if (keyfatorValueMap.size() > 1) {
				queryString.append(" AND ");
			}
			i = 1;
			for (int index = 1; index <= keyfatorValueMap.size(); index++) {
				if (i > 1) {
					if (keyfatorValueMap.size() > 2) {
						queryString.append(" AND ");
					}
				}
				if (index != keyfatorValueMap.size()) {
					queryString.append("pr" + index + ".productPremiumRate.id");
					queryString.append(" = ");
					queryString.append("pr" + (index + 1) + ".productPremiumRate.id");
				} else {
					if (keyfatorValueMap.size() > 2) {
						queryString.append("pr1.productPremiumRate.id");
						queryString.append(" = ");
						queryString.append("pr" + keyfatorValueMap.size() + ".productPremiumRate.id");
					}
				}
				i++;
			}
			queryString.append(" AND ");
			queryString.append("pr1.productPremiumRate.id = p.id");
			queryString.append(" AND ");
			queryString.append("p.product.id = :productID");
			Query query = em.createQuery(queryString.toString());
			i = 1;
			for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
				KeyFactorType kfValue = keyFactor.getKeyFactorType();
				if (kfValue.equals(KeyFactorType.FIXED) || kfValue.equals(KeyFactorType.REFERENCE) || kfValue.equals(KeyFactorType.ENUM)) {
					if (!isFireProduct(product.getId())) {
						query.setParameter("keyFactorName_" + i, keyFactor.getValue());
					}
					query.setParameter("fixedValue_" + i, keyfatorValueMap.get(keyFactor));
				} else if (kfValue.equals(KeyFactorType.FROM_TO)) {
					if (!isFireProduct(product.getId())) {
						query.setParameter("keyFactorName_" + i, keyFactor.getValue());
					}
					query.setParameter("value_" + i, Double.parseDouble(keyfatorValueMap.get(keyFactor)));
				}
				i++;
			}
			query.setParameter("productID", product.getId());

			// SELECT t0.PREMIUMRATE
			// FROM PRODUCT_PREMIUMRATE_LINK t6, PRODUCT_PREMIUMRATE_LINK t5,
			// PRODUCT_PREMIUMRATE_LINK t4, PRODUCT_RATEKEYFACTOR t3,
			// PRODUCT_RATEKEYFACTOR t2,
			// PRODUCT_RATEKEYFACTOR t1, PRODUCT_PREMIUMRATE_LINK t0
			// WHERE (((((((((t1.VALUE = 'ISSYS0110001000000005031032013') AND
			// (t2.VALUE = 'ISSYS0300001000000000431032013')) AND
			// (t3.VALUE ='ISSYS0210001000000000129032013')) AND (t4.ID =
			// t5.ID)) AND (t5.ID = t6.ID)) AND (t4.ID = t6.ID)) AND (t4.ID =
			// t0.ID)) AND
			// (t0.PRODUCTID = 'ISPRD0030001000000001731032013')) AND (((t4.ID =
			// t1.PRODUCTPREMIUMRATEID) AND (t5.ID = t2.PRODUCTPREMIUMRATEID))
			// AND
			// (t6.ID = t3.PRODUCTPREMIUMRATEID)))
			//

			List<Double> premiumList = query.getResultList();
			if (premiumList != null && !premiumList.isEmpty()) {
				result = (Double) premiumList.get(0);
			}
			if (result == null) {
				return null;
			}
			switch (product.getPremiumRateType()) {
				case BASED_ON_MAINCOVER_SUMINSURED: {
					result = Utils.getTwoDecimalPoint(Utils.divide(sumInsuredAmount, 100) * result);

				}
					break;
				case BASED_ON_OWN_SUMINSURED: {
					// result = Utils.divide(Utils.getTwoDecimalPoint(result *
					// sumInsuredAmount), product.getBaseSumInsured());
				}
					break;
				case USER_DEFINED_PREMIUM:
					break;
				default:
					break;
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find ProductPremiumRate", pe);
		}
		return result;
	}

	/*
	 * Used former.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private Double findProductPremiumRate_Old(Map<KeyFactor, String> keyfatorValueMap, Product product) throws DAOException {
		Double result = null;
		try {
			double sumInsuredAmount = 0.0;
			switch (product.getPremiumRateType()) {
				case BASED_ON_MAINCOVER_SUMINSURED: {
					for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
						if (KeyFactorIDConfig.isSumInsured(keyFactor)) {
							sumInsuredAmount = Double.parseDouble(keyfatorValueMap.get(keyFactor));
							if (sumInsuredAmount <= 0.0) {
								throw new SystemException(ErrorCode.NO_SUM_INSURED_KEYFACTOR, "There is SumInsured KeyFactor to calculate premium.");
							}
							keyfatorValueMap.remove(keyFactor);
							break;
						}
					}
				}
					;
					break;
				case BASED_ON_OWN_SUMINSURED: {
					for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
						if (KeyFactorIDConfig.isSumInsured(keyFactor)) {
							sumInsuredAmount = Double.parseDouble(keyfatorValueMap.get(keyFactor));
							if (sumInsuredAmount <= 0.0) {
								throw new SystemException(ErrorCode.NO_SUM_INSURED_KEYFACTOR, "There is SumInsured KeyFactor to calculate premium.");
							}
							keyfatorValueMap.remove(keyFactor);
							break;
						}
					}
				}
					;
					break;
				case USER_DEFINED_PREMIUM:
					break;
				default:
					break;
			}
			int i = 1;
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT mpr.premiumRate FROM ProductPremiumRate mpr ");
			for (int index = 0; index < keyfatorValueMap.size(); index++) {
				queryString.append("JOIN mpr.premiumRateKeyFactor prkf_" + (i++) + " ");
			}
			queryString.append("WHERE ");
			i = 1;
			for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
				if (i > 1) {
					queryString.append(" AND ");
				}
				KeyFactorType kfValue = keyFactor.getKeyFactorType();
				if (kfValue.equals(KeyFactorType.FIXED) || kfValue.equals(KeyFactorType.REFERENCE)) {
					queryString.append("prkf_" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					queryString.append("prkf_" + i + ".value = :fixedValue_" + i);
				} else if (kfValue.equals(KeyFactorType.FROM_TO)) {
					queryString.append("prkf_" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					queryString.append("(prkf_" + i + ".from <= :value_" + i + " AND prkf_" + i + ".to >= :value_" + i + ")");
				}
				i++;
			}
			queryString.append(" AND mpr.product.id = :productID");
			System.out.println("@" + queryString.toString());
			Query query = em.createQuery(queryString.toString());
			i = 1;
			for (KeyFactor keyFactor : keyfatorValueMap.keySet()) {
				KeyFactorType kfValue = keyFactor.getKeyFactorType();
				if (kfValue.equals(KeyFactorType.FIXED) || kfValue.equals(KeyFactorType.REFERENCE)) {
					query.setParameter("keyFactorName_" + i, keyFactor.getValue());
					query.setParameter("fixedValue_" + i, keyfatorValueMap.get(keyFactor));
				} else if (kfValue.equals(KeyFactorType.FROM_TO)) {
					query.setParameter("keyFactorName_" + i, keyFactor.getValue());
					query.setParameter("value_" + i, Double.parseDouble(keyfatorValueMap.get(keyFactor)));
				}
				i++;
			}
			query.setParameter("productID", product.getId());
			List<Double> premiumList = query.getResultList();
			if (premiumList != null && !premiumList.isEmpty()) {
				result = (Double) premiumList.get(0);
			}
			if (result == null) {
				return null;
			}
			switch (product.getPremiumRateType()) {
				case BASED_ON_MAINCOVER_SUMINSURED: {
					result = (sumInsuredAmount / 100) * result;

				}
					;
					break;
				case BASED_ON_OWN_SUMINSURED: {
					// result = (result * sumInsuredAmount) /
					// product.getBaseSumInsured();
				}
					;
					break;
				case USER_DEFINED_PREMIUM:
					break;
				default:
					break;
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find ProductPremiumRate", pe);
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public Double findThirdPartyPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) throws DAOException {
		Double result = null;
		try {
			int i = 1;
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT mpr.premiumRate FROM ProductPremiumRate mpr ");
			for (int index = 0; index < keyfatorValueMap.size(); index++) {
				queryString.append("JOIN mpr.premiumRateKeyFactor prkf_" + (i++) + " ");
			}
			queryString.append("WHERE ");
			i = 1;
			for (Map.Entry<KeyFactor, String> entry : keyfatorValueMap.entrySet()) {
				if (i > 1) {
					queryString.append(" AND ");
				}
				if (entry.getKey().getKeyFactorType().equals(KeyFactorType.FIXED)) {
					queryString.append("prkf_" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					queryString.append("prkf_" + i + ".value = :fixedValue_" + i);
				} else if (entry.getKey().getKeyFactorType().equals(KeyFactorType.FROM_TO)) {
					queryString.append("prkf_" + i + ".keyFactor.value = :keyFactorName_" + i + " AND ");
					if (entry.getKey().getId().equals(KeyFactorIDConfig.getSumInsuredId())) {
						queryString.append("(prkf_" + i + ".from = :value_" + i + " AND prkf_" + i + ".to = :value_" + i + ")");
					} else {
						queryString.append("(prkf_" + i + ".from <= :value_" + i + " AND prkf_" + i + ".to >= :value_" + i + ")");
					}
				}
				i++;
			}
			queryString.append(" AND mpr.product.id = :productID");
			System.out.println("QueryString : " + queryString.toString());
			Query query = em.createQuery(queryString.toString());
			i = 1;
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<KeyFactor, String> entry : keyfatorValueMap.entrySet()) {
				if (entry.getKey().getKeyFactorType().equals(KeyFactorType.FIXED)) {
					query.setParameter("keyFactorName_" + i, entry.getKey().getValue());
					buffer.append("keyFactorName_" + i + entry.getKey().getValue());

					query.setParameter("fixedValue_" + i, entry.getValue());
					buffer.append("fixedValue_" + i + entry.getValue());
				} else if (entry.getKey().getKeyFactorType().equals(KeyFactorType.FROM_TO)) {
					query.setParameter("keyFactorName_" + i, entry.getKey().getValue());
					buffer.append("keyFactorName_" + i + entry.getKey().getValue());
					query.setParameter("value_" + i, Double.parseDouble(entry.getValue()));
					buffer.append("value_" + i + entry.getValue());
				}
				i++;
			}
			query.setParameter("productID", product.getId());
			List<Double> premiumList = query.getResultList();
			if (premiumList != null && !premiumList.isEmpty()) {
				result = (Double) premiumList.get(0);
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find ProductPremiumRate", pe);
		}
		return result;
	}
}
