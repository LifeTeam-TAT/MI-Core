package org.ace.insurance.system.common.surveyMember.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyMember.persistence.interfaces.ISurveyMemberDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SurveyMemberDAO")
public class SurveyMemberDAO extends BasicDAO implements ISurveyMemberDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SurveyMember surveyMember) throws DAOException {
		try {
			em.persist(surveyMember);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert SurveyMember", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyMember> findAllSurveyMember() {
		List<SurveyMember> result = new ArrayList<>();
		try {
			Query q = em.createNamedQuery("SurveyMember.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find All SurveyMember", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyMember(SurveyMember surveyMember) {
		try {
			em.merge(surveyMember);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SurveyMember", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyMember(SurveyMember surveyMember) {
		try {
			surveyMember = em.merge(surveyMember);
			em.remove(surveyMember);
		} catch (PersistenceException pe) {
			throw translate("Failed to delete SurveyMember", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyMember> findByCriteria(String criteria) throws DAOException {
		List<SurveyMember> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select s from SurveyMember s where s.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of SurveyMember.", pe);
		}
		return result;
	}

}
