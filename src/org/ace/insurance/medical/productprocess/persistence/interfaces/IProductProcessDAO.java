package org.ace.insurance.medical.productprocess.persistence.interfaces;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the <code>ProductProcess</code>
 *          object.
 * 
 ***************************************************************************************/
import java.util.List;

import org.ace.insurance.medical.productprocess.ProductProcess;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.java.component.persistence.exception.DAOException;

public interface IProductProcessDAO {
	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Add new ProductProcess data into DB.
	 */
	public void insert(ProductProcess productProcess) throws DAOException;

	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Edit ProductProcess data into DB.
	 * 
	 */
	public void update(ProductProcess productProcess) throws DAOException;

	/**
	 * 
	 * @param {@link
	 * 			ProductProcess} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose Delete ProductProcess data from DB.
	 */
	public void delete(ProductProcess productProcess) throws DAOException;

	/**
	 * 
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find All ProductProcess data from DB
	 */
	public List<ProductProcess> findAll() throws DAOException;

	/**
	 * @param String
	 * @return {@link ProductProcess} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 */
	public ProductProcess findById(String productProcessId) throws DAOException;

	/**
	 * @param String
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose Find ProductProcesses data from DB.
	 */
	public List<ProductProcess> findProductProcessBySurveyQuestionId(String surveyQuestionId) throws DAOException;

	/**
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose find ProductProcesses data by ActiveStatus from DB.
	 */
	public List<ProductProcess> findProPByActiveStatus() throws DAOException;

	/**
	 * @return {@link Integer}
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose find ProductProcesses's Count by surveyQuestionId from DB.
	 */
	public int findPPCountByQuId(String surveyQuestionId) throws DAOException;

	/**
	 * @return {@link ProductProcess}
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose find old configure ProductProcesses by productId, processId and
	 *          buildingOccupationType from DB.
	 */
	public ProductProcess findOldConfPP(String productId, String processId, BuildingOccupationType buildingOccupationType) throws DAOException;

	/**
	 * @return {@link List} of {@link ProductProcess} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation.
	 * @Purpose find configure and deactivate ProductProcesses by productId and
	 *          processId from DB.
	 */
	public List<ProductProcess> findConfigAndDeactivatePP(String productId, String processId) throws DAOException;

	public List<ProductProcess> findProPActivateConfigure() throws DAOException;

	public List<String> findPNoByProductAndProcess(String productId, String processId) throws DAOException;
}
