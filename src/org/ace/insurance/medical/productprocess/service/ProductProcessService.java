package org.ace.insurance.medical.productprocess.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.medical.productprocess.persistence.interfaces.IProductProcessDAO;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the
 *          <code>ProductProcess</code> object.
 * 
 ***************************************************************************************/

@Service(value = "ProductProcessService")
public class ProductProcessService extends BaseService implements IProductProcessService {

	@Resource(name = "ProductProcessDAO")
	private IProductProcessDAO productProcessDAO;

	/**
	 * @see org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService
	 *      #addNewProductProcess(org.ace.insurance.medical.productProcess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductProcess(ProductProcess productProcess) {
		try {
			productProcessDAO.insert(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new ProductProcess", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService
	 *      #updateProductProcess(org.ace.insurance.medical.productProcess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductProcess(ProductProcess productProcess) {
		try {
			productProcessDAO.update(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a ProductProcess", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService
	 *      #deleteProductProcess(org.ace.insurance.medical.productProcess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductProcess(ProductProcess productProcess) {
		try {
			productProcessDAO.delete(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a ProductProcess", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService
	 *      #findAllProductProcess()
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findAllProductProcess() {
		List<ProductProcess> result = null;
		try {
			result = productProcessDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of ProductProcess)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findAllProductProcess(String)
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findProductProcessBySurveyQuestionId(String surveyQuestionId) {
		List<ProductProcess> result = null;
		try {
			result = productProcessDAO.findProductProcessBySurveyQuestionId(surveyQuestionId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all of ProductProcess by surveyQuestion)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findByProductProcessId(java.util.String)
	 * @return ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ProductProcess findByProductProcessId(String productProcessId) {
		ProductProcess result = null;
		try {
			result = productProcessDAO.findById(productProcessId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ProductProcess by id)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findProPByActiveStatus()
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findProPByActiveStatus() {
		List<ProductProcess> result = null;
		try {
			result = productProcessDAO.findProPByActiveStatus();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ProductProcess by activeStatus)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findProPActivateConfigure() {
		List<ProductProcess> result = null;
		try {
			result = productProcessDAO.findProPActivateConfigure();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ProductProcess by activeStatus)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findPPCountByQuId()
	 * @return int
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int findPPCountByQuId(String surveyQuestionId) {
		int count = 0;
		try {
			count = productProcessDAO.findPPCountByQuId(surveyQuestionId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ProductProcess's by surveyQuestionId)", e);
		}
		return count;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findOldConfPP()
	 * @return ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public ProductProcess findOldConfPP(String productId, String processId, BuildingOccupationType buildingOccupationType) {
		ProductProcess result = null;
		try {
			result = productProcessDAO.findOldConfPP(productId, processId, buildingOccupationType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find old configure ProductProcess by productId, processId and buildingOccupationType)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findOldConfPP()
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findConfigAndDeactivatePP(String productId, String processId) {
		List<ProductProcess> result = null;
		try {
			result = productProcessDAO.findConfigAndDeactivatePP(productId, processId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find old configure and deactivate ProductProcess by productId and processId)", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.ProductProcess.service.interfaces.IProductProcessService
	 *      #findPPByProductAndProcess()
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findPNoByProductAndProcess(String productId, String processId) {
		List<String> result = null;
		try {
			result = productProcessDAO.findPNoByProductAndProcess(productId, processId);
			if (result != null && result.contains(null)) {
				result.remove(null);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find ProductProcess's QueSetNo by productId and processId)", e);
		}
		return result;
	}
}
