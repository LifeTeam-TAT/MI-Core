package org.ace.insurance.medical.surveyquestion.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.system.common.buildingOccupation.BuildingOccupationType;

/***************************************************************************************
 * @author HS
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose This interface serves as the Service Layer to manipulate the
 *          <code>Process</code> object.
 * 
 * 
 ***************************************************************************************/

public interface ISurveyQuestionService {
	/**
	 * 
	 * @param {@link
	 *            SurveyQuestion} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Add new SurveyQuestion data.
	 */
	public void addNewSurveyQuestion(SurveyQuestion surveyQuestion);

	/**
	 * 
	 * @param {@link
	 *            SurveyQuestion} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Edit SurveyQuestion data.
	 */
	public void updateSurveyQuestion(SurveyQuestion surveyQuestion);

	/**
	 * 
	 * @param {@link
	 *            SurveyQuestion} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose delete SurveyQuestion for delete status.
	 */
	public void deleteSurveyQuestion(SurveyQuestion surveyQuestion);

	/**
	 * 
	 * @param {@link
	 *            String}
	 * @return {@link SurveyQuestion} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find SurveyQuestion data by Id.
	 * @return SurveyQuestion
	 */
	public SurveyQuestion findSurveyQuestionById(String surveyQuestionId);

	/**
	 * 
	 * @return {@link SurveyQuestion} instance
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose Find all SurveyQuestion data.
	 * 
	 */
	public List<SurveyQuestion> findAllSurveyQuestion();

	/**
	 * @param {@link
	 *            String}
	 * @return {@link List} of {@link SurveyQuestion} instances
	 * @throws SystemException
	 *             An exception occurs during the DB operation
	 * @Purpose find SurveyQuestionByproductprocess data.
	 */
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId);

	/***
	 * 
	 * @param productId
	 * @param prcessId
	 * @param buildingOccupationType
	 * @return {@link List} of {@link ProductProcessQuestionLink} instances
	 * @Purpose find ProductProcessQuestionLink list from DB
	 */
	public List<ProductProcessQuestionLink> findProductProcessQuestionLinkList(String productId, String processId, BuildingOccupationType buildingOccupationType);

	public List<ProductProcessQuestionLink> findPProQueByPPId(String publicTermLifeId, String proposalProcessId, ProductProcessCriteriaDTO dto);

}
