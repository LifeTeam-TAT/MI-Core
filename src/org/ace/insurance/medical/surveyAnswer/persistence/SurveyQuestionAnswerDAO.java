/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.surveyAnswer.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.medical.surveyAnswer.persistence.interfaces.ISurveyQuestionAnswerDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SurveyQuestionAnswerDAO")
public class SurveyQuestionAnswerDAO extends BasicDAO implements ISurveyQuestionAnswerDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException {
		try {
			em.persist(surveyQuestionAnswer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert SurveyQuestionAnswer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException {
		try {
			em.merge(surveyQuestionAnswer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SurveyQuestionAnswer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(SurveyQuestionAnswer surveyQuestionAnswer) throws DAOException {
		try {
			surveyQuestionAnswer = em.merge(surveyQuestionAnswer);
			em.remove(surveyQuestionAnswer);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete SurveyQuestionAnswer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SurveyQuestionAnswer findById(String id) throws DAOException {
		SurveyQuestionAnswer result = null;
		try {
			result = em.find(SurveyQuestionAnswer.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find SurveyQuestionAnswer", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyQuestionAnswer> findAll() throws DAOException {
		List<SurveyQuestionAnswer> result = null;
		try {
			Query q = em.createNamedQuery("SurveyQuestionAnswer.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of SurveyQuestionAnswer", pe);
		}
		return result;
	}
}
