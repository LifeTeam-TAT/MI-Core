/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.product.persistence.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.product.Product;
import org.ace.insurance.product.ProductPremiumRate;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProductPremiumRateDAO {
	public ProductPremiumRate insert(ProductPremiumRate motorPremiumRate) throws DAOException;

	public void update(ProductPremiumRate productPremiumRate) throws DAOException;

	public void delete(ProductPremiumRate productPremiumRate) throws DAOException;

	public ProductPremiumRate findById(String id) throws DAOException;

	public List<ProductPremiumRate> findByProduct(String product) throws DAOException;

	public List<ProductPremiumRate> findByAddOnId(String addOnId) throws DAOException;

	public Double findProductPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product Product) throws DAOException;

	public Double findThirdPartyPremiumRate(Map<KeyFactor, String> keyfatorValueMap, Product Product) throws DAOException;
}
