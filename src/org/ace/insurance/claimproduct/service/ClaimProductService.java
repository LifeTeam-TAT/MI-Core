/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.claimproduct.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.ClaimProductRate;
import org.ace.insurance.claimproduct.persistence.interfaces.IClaimProductDAO;
import org.ace.insurance.claimproduct.persistence.interfaces.IClaimProductRateDAO;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.product.KFRefValue;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.web.manage.claimProduct.ClaimProductRateDto;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ClaimProductService")
public class ClaimProductService extends BaseService implements IClaimProductService {

	@Resource(name = "ClaimProductDAO")
	private IClaimProductDAO claimProductDAO;

	@Resource(name = "ClaimProductRateDAO")
	private IClaimProductRateDAO claimProductRateDAO;




	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewClaimProduct(ClaimProduct claimProduct) {
		try {
			claimProductDAO.insert(claimProduct);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimProduct", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateClaimProduct(ClaimProduct claimProduct) {
		try {
			claimProductDAO.update(claimProduct);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a ClaimProduct", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteClaimProduct(ClaimProduct claimProduct) {
		try {
			claimProductDAO.delete(claimProduct);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a ClaimProduct", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewClaimProductRate(ClaimProductRate claimProductRate) {
		try {
			claimProductRateDAO.insert(claimProductRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new ClaimProductRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateClaimProductRate(ClaimProductRate claimProductRate) {
		try {
			claimProductRateDAO.update(claimProductRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a ClaimProductRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteClaimProductRate(ClaimProductRate claimProductRate) {
		try {
			claimProductRateDAO.delete(claimProductRate);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a ClaimProductRate", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findAllClaimProduct() {
		List<ClaimProduct> result = null;
		try {
			result = claimProductDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of claimProduct)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimProduct findClaimProductById(String id) {
		ClaimProduct result = null;
		try {
			result = claimProductDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClaimProduct (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClaimProductRate findClaimProductRateById(String id) {
		ClaimProductRate result = null;
		try {
			result = claimProductRateDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClaimProductRate (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProductRate> findClaimProductRateByClaimProduct(String claimProductId) {
		List<ClaimProductRate> result = null;
		try {
			result = claimProductRateDAO.findByClaimProduct(claimProductId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClaimProductRate by ClaimProduct (ID : " + claimProductId + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProductRateDto> findClaimProductRateDtoByClaimProductId(String claimProductId) {
		List<ClaimProductRateDto> result = null;
		try {
			result = claimProductRateDAO.findDtoByClaimProduct(claimProductId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClaimProductRate by ClaimProduct (ID : " + claimProductId + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double findClaimProductRate(Map<KeyFactor, String> keyfatorValueMap, ClaimProduct claimProduct, double sumInsured) {
		Double result;
		try {
			result = claimProductRateDAO.findClaimProductRate(keyfatorValueMap, claimProduct);
			if (result != null && result > 0) {
				switch (claimProduct.getRateType()) {
					case USER_DEFINED:
						break;
					case BASED_ON_SUMINSURED: {
						result = Utils.divide(result * sumInsured, claimProduct.getBaseSumInsured());
					}
						break;
					case PERCENT_OF_SUMINSURED:
						result = Utils.divide(result * sumInsured, 100);
						break;
				}
			}

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a ClaimProductRate", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findClaimProductByInsuranceType(InsuranceType insuranceType) throws SystemException {
		List<ClaimProduct> result = null;
		try {
			result = claimProductDAO.findByInsuranceType(insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of claimProduct by InsuranceType)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<KFRefValue> findReferenceValue(String entityName, InsuranceType insuranceType) throws SystemException {
		List<KFRefValue> result = null;
		try {
			result = claimProductDAO.findReferenceValue(entityName, insuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of KFRefValue)", e);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimProduct> findAllClaimProduct(List<String> claimProducts) throws SystemException {
		List<ClaimProduct> result = null;
		try {
			result = claimProductDAO.findAllClaimProduct(claimProducts);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of claimProduct by id)", e);
		}
		return result;
	}

}