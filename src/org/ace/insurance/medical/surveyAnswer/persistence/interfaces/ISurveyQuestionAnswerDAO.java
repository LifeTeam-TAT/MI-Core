/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.surveyAnswer.persistence.interfaces;

import java.util.List;

import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.java.component.persistence.exception.DAOException;

public interface ISurveyQuestionAnswerDAO {
	public void insert(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException;

	public void update(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException;

	public void delete(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException;

	public SurveyQuestionAnswer findById(String id) throws DAOException;

	public List<SurveyQuestionAnswer> findAll() throws DAOException;
}
