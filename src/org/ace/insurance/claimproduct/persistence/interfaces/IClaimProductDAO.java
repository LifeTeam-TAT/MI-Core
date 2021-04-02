/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct.persistence.interfaces;

import java.util.List;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.java.component.persistence.exception.DAOException;

public interface IClaimProductDAO {
	public void insert(ClaimProduct claimProduct) throws DAOException;

	public void update(ClaimProduct claimProduct) throws DAOException;

	public void delete(ClaimProduct claimProduct) throws DAOException;

	public ClaimProduct findById(String id) throws DAOException;

	public List<ClaimProduct> findByInsuranceType(InsuranceType insuranceType) throws DAOException;

	public List<ClaimProduct> findAll() throws DAOException;

	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws DAOException;
	
	public List<ClaimProduct> findAllClaimProduct(List<String> claimProducts);

}
