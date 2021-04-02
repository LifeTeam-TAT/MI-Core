package org.ace.insurance.medical.productprocess.frontservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.medical.productprocess.ActiveStatus;
import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.web.manage.product.ProductProcessDTO;
import org.ace.insurance.web.manage.surveyquestion.factory.ProductProcessFactory;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ProductProcessConfigFrontService")
public class ProductProcessConfigFrontService extends BaseService implements IProductProcessConfigFrontService {

	@Resource(name = "ProductProcessService")
	private IProductProcessService productProcessService;

	@Transactional(propagation = Propagation.REQUIRED)
	public String createQuestionSetNo(String prefix) {
		String result;
		try {
			result = prefix + "/" + "0000000001";
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to create QuestionSetNo", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String updateQuestionSetNo(String prefix, String oldNo) {
		String id = null;
		try {
			id = String.valueOf(Integer.parseInt(oldNo) + 1);
			int idLength = id.length();
			for (; (10 - idLength) > 0; idLength++) {
				id = '0' + id;
			}

			id = prefix + "/" + id;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update QuestionSetNo", e);
		}
		return id;
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #addNewProductProcessConfig(org.ace.insurance.medical.productprocess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewProductProcessConfig(ProductProcess productProcess) {
		try {
			findAndDeleteOldConfPP(productProcess);
			productProcess.setActiveStatus(ActiveStatus.CONFIGURE);
			productProcessService.addNewProductProcess(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to add a new ProductProcessConfig", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #updateProductProcess(org.ace.insurance.medical.productprocess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductProcessConfig(ProductProcess productProcess) {
		try {
			productProcess.setId(null);
			for (ProductProcessQuestionLink ppq : productProcess.getProductProcessQuestionLinkList()) {
				ppq.setId(null);
			}
			addNewProductProcessConfig(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a ProductProcessConfig", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #deleteProductProcessConfig(org.ace.insurance.medical.productprocess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteProductProcessConfig(ProductProcess productProcess) {
		try {
			productProcessService.deleteProductProcess(productProcess);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to delete a SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #findAndDeleteOldConfPP(org.ace.insurance.medical.productprocess.ProductProcess)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void findAndDeleteOldConfPP(ProductProcess productProcess) {
		try {
			// ProductProcess oldProductProcess =
			// productProcessService.findOldConfPP(productProcess.getProduct().getId(),
			// productProcess.getProcess().getId(),
			// productProcess.getBuildingOccupationType());
			// if (oldProductProcess != null) {
			// productProcessService.deleteProductProcess(oldProductProcess);
			// }
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find and  delete a old configure productprocess ", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #findAndDeleteOldConfPP(String productId, String processId)
	 * @return List of ProductProcess
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ProductProcess> findConfigAndDeactivatePP(String productId, String processId) {
		List<ProductProcess> result = null;
		try {
			result = productProcessService.findConfigAndDeactivatePP(productId, processId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find  configure and deactivate productprocess ", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.productprocess.frontservice.interfaces.IProductProcessConfigFrontService
	 *      #updateProductProcess(org.ace.insurance.medical.productprocess.ProductProcess)
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public String findGrateQuestionSetNo(String productId, String processId) {
		String productProcessNo = null;
		List<Integer> integerList = new ArrayList<Integer>();
		Map<String, Integer> bigMap = new HashMap<String, Integer>();
		try {
			List<String> questionSetNoList = productProcessService.findPNoByProductAndProcess(productId, processId);
			for (String setNo : questionSetNoList) {
				if (setNo != null)
					integerList.add(Integer.parseInt((String) setNo.subSequence(setNo.indexOf("/") + 1, setNo.length())));
			}
			for (int i = 0; i < integerList.size(); i++) {
				if (i == 0) {
					bigMap.put("big", integerList.get(i));
				} else {
					if (bigMap.get("big") < integerList.get(i)) {
						bigMap.put("big", integerList.get(i));
					}
				}
			}
			if (!integerList.isEmpty())
				productProcessNo = String.valueOf(bigMap.get("big"));
			return productProcessNo;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a ProductProcessConfig", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateProductProcessConfigActivateDeactivate(ProductProcessDTO productProcess, String prefix, String ppNo, boolean flage) {
		try {
			if (flage) {
				if (ppNo != null && !ppNo.isEmpty()) {
					productProcess.setQuestionSetNo(updateQuestionSetNo(prefix, ppNo));
				} else {
					productProcess.setQuestionSetNo(createQuestionSetNo(prefix));
				}
			}

			productProcessService.updateProductProcess(ProductProcessFactory.getProductProcess(productProcess));
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to update a ProductProcessConfig", e);
		}
	}

}
