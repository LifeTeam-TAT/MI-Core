/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.product.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.SystemException;

public interface IProductService {
	public void addNewProduct(Product product) throws SystemException;

	public void updateProduct(Product product) throws SystemException;

	public void deleteProduct(Product product) throws SystemException;

	public ProductPremiumRate addNewProductPremiumRate(ProductPremiumRate productPremiumRate) throws SystemException;

	public void updateProductPremiumRate(ProductPremiumRate productPremiumRate) throws SystemException;

	public void deleteProductPremiumRate(ProductPremiumRate productPremiumRate) throws SystemException;

	public Product findProductById(String id) throws SystemException;

	public List<ProductPremiumRate> findProductPremiumRateByAddOnId(String addOnId) throws SystemException;

	public ProductPremiumRate findProductPremiumRateById(String id) throws SystemException;

	public List<ProductPremiumRate> findProductPremiumRateByProduct(String productId) throws SystemException;

	public List<Product> findAllProduct() throws SystemException;

	public Double findProductPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) throws SystemException;

	public Double findThirdPartyPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product product) throws SystemException;

	public List<Product> findProductByInsuranceType(InsuranceType insuranceType) throws SystemException;

	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws SystemException;

	public List<Product> findProductByCurrencyType(InsuranceType insuranceType, Currency currency) throws SystemException;

	public KFRefValue findKFRefValueById(String entityName, String id) throws SystemException;

	public <T> Double findPremiumRate(Map<KeyFactor, String> keyFactorValueMap, T param) throws SystemException;

	public List<PRO001> findProductDTOGroupById(String productGroupId);

	public List<PRO001> findAllProductName();

}
