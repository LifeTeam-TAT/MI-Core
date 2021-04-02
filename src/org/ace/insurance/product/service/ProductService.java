/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.product.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.product.persistence.interfaces.IPremiumCalculatorDAO;
import org.ace.insurance.product.persistence.interfaces.IProductDAO;
import org.ace.insurance.product.persistence.interfaces.IProductPremiumRateDAO;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.system.common.keyfactor.service.interfaces.IKeyFactorService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProductService")
public class ProductService extends BaseService implements IProductService {

	@Resource(name = "ProductDAO")
	private IProductDAO productDAO;

	@Resource(name = "ProductPremiumRateDAO")
	private IProductPremiumRateDAO productPremiumRateDAO;

	@Resource(name = "PremiumCalculatorDAO")
	private IPremiumCalculatorDAO premiumCalculatorDAO;

	@Resource(name = "KeyFactorService")
	private IKeyFactorService keyFactorService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProduct(Product product) {
		// boolean addSumInsuredKey = true;
		try {
			/*
			 * for(KeyFactor kf : motorProduct.getKeyFactorList()) {
			 * if(kf.getId().equals(KeyFactor.SUM_INSU_ID)) { addSumInsuredKey =
			 * false;break; } } if(addSumInsuredKey) { KeyFactor kf =
			 * keyFactorService.findKeyFactorById(KeyFactor.SUM_INSU_ID);
			 * motorProduct.getKeyFactorList().add(kf); }
			 */
			productDAO.insert(product);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Product", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProduct(Product product) {
		try {
			productDAO.update(product);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a Product", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProduct(Product product) {
		try {
			productDAO.delete(product);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a Product", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductPremiumRate addNewProductPremiumRate(ProductPremiumRate productPremiumRate) {
		ProductPremiumRate result = null;
		try {
			result = productPremiumRateDAO.insert(productPremiumRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductPremiumRate(ProductPremiumRate productPremiumRate) {
		try {
			productPremiumRateDAO.update(productPremiumRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a ProductPremiumRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductPremiumRate(ProductPremiumRate productPremiumRate) {
		try {
			productPremiumRateDAO.delete(productPremiumRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a ProductPremiumRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findAllProduct() {
		List<Product> result = null;
		try {
			result = productDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of product)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Product findProductById(String id) {
		Product result = null;
		try {
			result = productDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a Product (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ProductPremiumRate findProductPremiumRateById(String id) {
		ProductPremiumRate result = null;
		try {
			result = productPremiumRateDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductPremiumRate> findProductPremiumRateByProduct(String productId) {
		List<ProductPremiumRate> result = null;
		try {
			result = productPremiumRateDAO.findByProduct(productId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate by Product (ID : " + productId + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductPremiumRate> findProductPremiumRateByAddOnId(String addOnId) {
		List<ProductPremiumRate> result = null;
		try {
			result = productPremiumRateDAO.findByAddOnId(addOnId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate by AddOn (ID : " + addOnId + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double findProductPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) {
		Double result;
		try {
			result = productPremiumRateDAO.findProductPremiumRate(keyfatorValueMap, product);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double findThirdPartyPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) {
		Double result = 0.0;
		try {
			result = productPremiumRateDAO.findThirdPartyPremiumRate(keyfatorValueMap, product);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findProductByInsuranceType(InsuranceType insuranceType) throws SystemException {
		List<Product> result = null;
		try {
			result = productDAO.findByInsuranceType(insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of product by InsuranceType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Product> findProductByCurrencyType(InsuranceType insuranceType, Currency currency) throws SystemException {
		List<Product> result = null;
		try {
			result = productDAO.findProductByCurrencyType(insuranceType, currency);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of product by InsuranceType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws SystemException {
		List<KFRefValue> result = null;
		try {
			result = productDAO.findReferenceValue(entityName, insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of KFRefValue)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public KFRefValue findKFRefValueById(String entityName, String id) throws SystemException {
		KFRefValue result = null;
		try {
			result = productDAO.findKFRefValueById(entityName, id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find KFRefValue by id. ( ID : " + id + " , Entity Name : " + entityName + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public <T> Double findPremiumRate(Map<KeyFactor, String> keyfatorValueMap, T param) {
		Double result;
		try {
			result = premiumCalculatorDAO.findPremiumRate(keyfatorValueMap, param);
			if (result == null || result < 0) {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find a ProductPremiumRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PRO001> findProductDTOGroupById(String id) throws SystemException {
		List<PRO001> result = null;
		try {
			result = productDAO.findProductDTOGroupById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of product)", e);
		}
		return result;
	}

	@Override
	public List<PRO001> findAllProductName() {
		List<PRO001> result = null;
		try {
			result = productDAO.findAllProductName();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of product name)", e);
		}
		return result;
	}

}