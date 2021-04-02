package org.ace.insurance.medical.surveyquestion.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;
import org.ace.java.component.persistence.exception.DAOException;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This class serves as the DAO to manipulate the
 *          <code>SurveyQuestion</code> object.
 * 
 ***************************************************************************************/

public interface ISurveyQuestionDAO {
	/**
	 * @param SurveyQuestion
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Insert SurveyQuestion data into DB.
	 */
	public void insert(SurveyQuestion surveyQuestion) throws DAOException;

	/**
	 * @param SurveyQuestion
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Update SurveyQuestion data into DB.
	 */
	public void update(SurveyQuestion surveyQuestion) throws DAOException;

	/**
	 * @param SurveyQuestion
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Delete SurveyQuestion data from DB.
	 */
	public void delete(SurveyQuestion surveyQuestion) throws DAOException;

	/**
	 * 
	 * @param {@link
	 *            String}
	 * @return {@link SurveyQuestion} instance
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find SurveyQuestion data by Id from DB.
	 */
	public SurveyQuestion findSurveyQuestionById(String surveyQuestionId) throws DAOException;

	/**
	 * 
	 * @return {@link List} of {@link SurveyQuestion} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find all SurveyQuestion data from DB.
	 */
	public List<SurveyQuestion> findAll() throws DAOException;

	/**
	 * @param {@link
	 *            String}{@link BuildingOccupationType}
	 * @return {@link List} of {@link SurveyQuestion} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find productprocessQuestionLink from DB.
	 */
	public List<ProductProcessQuestionLink> findPProQueByPPId(String productId, String processId, BuildingOccupationType buildingOccupationType) throws DAOException;

	/**
	 * @param {@link
	 *            String}
	 * @return {@link List} of {@link SproductProcessId} instances
	 * @throws DAOException
	 *             An exception occurs during the DB operation
	 * @Purpose Find SurveyQuestion by product process's id from DB.
	 */
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId) throws DAOException;

	List<ProductProcessQuestionLink> findPProQueByPPId(String productName, String processName, ProductProcessCriteriaDTO dto) throws DAOException;

}
