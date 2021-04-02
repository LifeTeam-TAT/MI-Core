/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.surveyAnswer.service.interfaces;

import java.util.List;

import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;

public interface ISurveyQuestionAnswerService {
	public void addNewSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

	public void updateSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

	public void deleteSurveyQuestionAnswer(SurveyQuestionAnswer surveyQuestionAnswer);

	public SurveyQuestionAnswer findSurveyQuestionAnswerById(String id);

	public List<SurveyQuestionAnswer> findAllSurveyQuestionAnswer();
}
