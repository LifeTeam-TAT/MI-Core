package org.ace.insurance.medical.surveyquestion.frontservice;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.SystemConstants;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.medical.surveyquestion.SurveyQuestion;
import org.ace.insurance.medical.surveyquestion.frontservice.interfaces.ISurveyQuestionFrontService;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SurveyQuestionFrontService")
public class SurveyQuestionFrontService extends BaseService implements ISurveyQuestionFrontService {

	@Resource(name = "SurveyQuestionService")
	private ISurveyQuestionService surveyQuestionService;

	@Resource(name = "ProductProcessService")
	private IProductProcessService productProcessService;

	/**
	 * @see org.ace.insurance.medical.surveyquestion.delegate.interfaces.ISurveyQuestionFrontService
	 *      #addNewSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurveyQuestion(SurveyQuestion surveyQuestion) {
		try {
			surveyQuestion.setQuestionNo(customIDGenerator.getNextId(SystemConstants.SURVEY_QUESTION_NO, null));
			surveyQuestionService.addNewSurveyQuestion(surveyQuestion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.delegate.interfaces.ISurveyQuestionFrontService
	 *      #updateSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyQuestion(SurveyQuestion surveyQuestion) {
		try {
			surveyQuestion.setDeleteFlag(false);
			surveyQuestionService.updateSurveyQuestion(surveyQuestion);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a SurveyQuestion", e);
		}
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.delegate.interfaces.ISurveyQuestionFrontService
	 *      #deleteSurveyQuestion(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 * @return boolean
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean deleteSurveyQuestion(SurveyQuestion surveyQuestion) {
		boolean result = false;
		try {
			if (checkUsingSQ(surveyQuestion)) {
				surveyQuestion.setDeleteFlag(true);
				surveyQuestionService.updateSurveyQuestion(surveyQuestion);
				result = true;
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a SurveyQuestion", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.delegate.interfaces.ISurveyQuestionFrontService
	 *      #checkUsingSQ(org.ace.insurance.medical.surveyquestion.SurveyQuestion)
	 * @return boolean
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkUsingSQ(SurveyQuestion surveyQuestion) {
		boolean result = false;
		try {
			if (productProcessService.findPPCountByQuId(surveyQuestion.getId()) == 0) {
				result = true;
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to check using survey question", e);
		}
		return result;
	}

	/**
	 * @see org.ace.insurance.medical.surveyquestion.delegate.interfaces.ISurveyQuestionFrontService
	 *      #findSurveyQuestionBypp(String productProcessId)
	 * @return list of surveyQuestion
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId) {
		List<SurveyQuestion> result = null;
		try {
			result = surveyQuestionService.findSurveyQuestionBypp(productProcessId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find survey questions by productprocess'id", e);
		}
		return result;
	}
}
