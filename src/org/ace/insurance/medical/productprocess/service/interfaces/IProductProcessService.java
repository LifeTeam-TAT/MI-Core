package org.ace.insurance.medical.productprocess.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the
 *          <code>ProductProcess</code> object.
 * 
 * 
 ***************************************************************************************/
public interface IProductProcessService {
	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Add new ProductProcess.
	 */
	public void addNewProductProcess(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Edit ProductProcess, Add new ProductProcessQuestionHistory for
	 *          update process.
	 * 
	 */
	public void updateProductProcess(ProductProcess productProcess);

	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 */
	public void deleteProductProcess(ProductProcess productProcess);

	/**
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find All ProductProcess data from DB
	 */
	public List<ProductProcess> findAllProductProcess();

	/**
	 * @param {@link
	 * 			String}
	 * @return {@link ProductProcess} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find ProductProcess by id.
	 */
	public ProductProcess findByProductProcessId(String productProcessId);

	/**
	 * @param String
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find ProductProcesses data from DB.
	 */
	public List<ProductProcess> findProductProcessBySurveyQuestionId(String surveyQuestionId);

	/**
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find ProductProcesses data by activestatus from DB.
	 */
	public List<ProductProcess> findProPByActiveStatus();

	/**
	 * @return {@link Integer}
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find ProductProcesses data by activestatus from DB.
	 */
	public int findPPCountByQuId(String surveyQuestionId);

	/**
	 * @return {@link productProcess} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find old configure ProductProcesses by productId, processId and
	 *          buildingOccupationType from DB.
	 */
	public ProductProcess findOldConfPP(String productId, String processId, BuildingOccupationType buildingOccupationType);

	/**
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find old configure and deativate ProductProcesses by productId
	 *          and processId from DB.
	 */
	public List<ProductProcess> findConfigAndDeactivatePP(String productId, String processId);

	public List<ProductProcess> findProPActivateConfigure();

	public List<String> findPNoByProductAndProcess(String productId, String processId);
}
