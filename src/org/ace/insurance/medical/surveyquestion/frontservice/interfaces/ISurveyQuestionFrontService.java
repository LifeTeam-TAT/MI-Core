package org.ace.insurance.medical.surveyquestion.frontservice.interfaces;

import java.util.List;

import org.ace.insurance.medical.surveyquestion.SurveyQuestion;

public interface ISurveyQuestionFrontService {
	/**
	 * 
	 * @param {@link SurveyQuestion} instance
	 * @Purpose Add new SurveyQuestion.
	 */
	public void addNewSurveyQuestion(SurveyQuestion surveyQuestion);

	/**
	 * 
	 * @param {@link SurveyQuestion} instance
	 * @Purpose update SurveyQuestion data, insert SurveyQuestion History for
	 *          edit process.
	 */
	public void updateSurveyQuestion(SurveyQuestion surveyQuestion);

	/**
	 * 
	 * @param {@link SurveyQuestion} instance
	 * @Purpose update SurveyQuestion for delete status, insert SurveyQuestion
	 */
	public boolean deleteSurveyQuestion(SurveyQuestion surveyQuestion);
	
	/**
	 * 
	 * @param {@link SurveyQuestion} instance
	 * @Purpose check SurveyQuestion
	 * @return boolean
	 */
	public boolean checkUsingSQ(SurveyQuestion surveyQuestion);
	/**
	 * 
	 * @param {@link SurveyQuestion} instance
	 * @Purpose findSurveyQuestionBypp
	 * @return boolean
	 */
	
	public List<SurveyQuestion> findSurveyQuestionBypp(String productProcessId);
}
