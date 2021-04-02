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
import java.util.Map;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimProductRate;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.manage.claimProduct.ClaimProductRateDto;
import org.ace.java.component.persistence.exception.DAOException;

public interface IClaimProductRateDAO {
	public ClaimProductRate insert(ClaimProductRate motorPremiumRate) throws DAOException;

	public void update(ClaimProductRate claimProductRate) throws DAOException;

	public void delete(ClaimProductRate claimProductRate) throws DAOException;

	public ClaimProductRate findById(String id) throws DAOException;

	public List<ClaimProductRate> findByClaimProduct(String product) throws DAOException;
	
	public List<ClaimProductRateDto> findDtoByClaimProduct(String product) throws DAOException;

	public Double findClaimProductRate(Map<KeyFactor, String> keyfatorValueMap, ClaimProduct claimProduct) throws DAOException;

}
