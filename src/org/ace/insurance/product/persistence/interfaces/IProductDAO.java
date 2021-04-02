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

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.product.PRO001;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProductDAO {
	public void insert(Product product) throws DAOException;

	public void update(Product product) throws DAOException;

	public void delete(Product product) throws DAOException;

	public Product findById(String id) throws DAOException;

	public List<Product> findByInsuranceType(InsuranceType insuranceType) throws DAOException;

	public List<Product> findProductByCurrencyType(InsuranceType insuranceType, Currency currency) throws DAOException;

	public List<Product> findAll() throws DAOException;

	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws DAOException;

	public KFRefValue findKFRefValueById(String entityName, String id) throws DAOException;

	public List<PRO001> findProductDTOGroupById(String id) throws DAOException;

	public double findStampFeeRateByProductID(String id) throws DAOException;

	public List<PRO001> findAllProductName();
}
