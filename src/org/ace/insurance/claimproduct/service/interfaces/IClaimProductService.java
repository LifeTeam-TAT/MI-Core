/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimProductRate;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.manage.claimProduct.ClaimProductRateDto;
import org.ace.java.component.SystemException;

public interface IClaimProductService {
	public void addNewClaimProduct(ClaimProduct claimProduct) throws SystemException;

	public void updateClaimProduct(ClaimProduct claimProduct) throws SystemException;

	public void deleteClaimProduct(ClaimProduct claimProduct) throws SystemException;

	public void addNewClaimProductRate(ClaimProductRate claimProductRate) throws SystemException;

	public void updateClaimProductRate(ClaimProductRate claimProductRate) throws SystemException;

	public void deleteClaimProductRate(ClaimProductRate claimProductRate) throws SystemException;

	public ClaimProduct findClaimProductById(String id) throws SystemException;

	public ClaimProductRate findClaimProductRateById(String id) throws SystemException;

	public List<ClaimProductRate> findClaimProductRateByClaimProduct(String claimProductId) throws SystemException;

	public List<ClaimProductRateDto> findClaimProductRateDtoByClaimProductId(String claimProductId) throws SystemException;

	public List<ClaimProduct> findAllClaimProduct() throws SystemException;

	public Double findClaimProductRate(Map<KeyFactor, String> keyfatorValueMap, ClaimProduct claimProduct, double sumInsured) throws SystemException;

	public List<ClaimProduct> findClaimProductByInsuranceType(InsuranceType insuranceType) throws SystemException;

	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws SystemException;
	
	public List<ClaimProduct> findAllClaimProduct(List<String> claimProducts) throws SystemException;
} 
