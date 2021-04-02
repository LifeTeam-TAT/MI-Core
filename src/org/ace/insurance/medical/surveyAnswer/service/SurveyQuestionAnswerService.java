/***************************************************************************************
 * @author <<John Htet>>
 * @Date 2014-07-25
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.surveyAnswer.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.medical.surveyAnswer.persistence.interfaces.ISurveyQuestionAnswerDAO;
import org.ace.insurance.medical.surveyAnswer.service.interfaces.ISurveyQuestionAnswerService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SurveyQuestionAnswerService")
public class SurveyQuestionAnswerService extends BaseService implements ISurveyQuestionAnswerService {

	@Resource(name = "SurveyQuestionAnswerDAO")
	private ISurveyQuestionAnswerDAO surveyQuestionAnswerDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) {
		try {
			surveyQuestionAnswerDAO.insert(surveyQuestionAnswer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SurveyQuestionAnswer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) {
		try {
			surveyQuestionAnswerDAO.update(surveyQuestionAnswer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a SurveyQuestionAnswer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer) {
		try {
			surveyQuestionAnswerDAO.delete(surveyQuestionAnswer);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a SurveyQuestionAnswer", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestionAnswer> findAllSurveyQuestionAnswer() {
		List<SurveyQuestionAnswer> result = null;
		try {
			result = surveyQuestionAnswerDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of SurveyQuestionAnswer)", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SurveyQuestionAnswer findSurveyQuestionAnswerById(String id) {
		SurveyQuestionAnswer result = null;
		try {
			result = surveyQuestionAnswerDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a SurveyQuestionAnswer (ID : " + id + ")", e);
		}
		return result;
	}

}
