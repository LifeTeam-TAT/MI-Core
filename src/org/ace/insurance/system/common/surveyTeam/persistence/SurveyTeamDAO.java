package org.ace.insurance.system.common.surveyTeam.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.system.common.surveyTeam.persistence.interfaces.ISurveyTeamDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("SurveyTeamDAO")
public class SurveyTeamDAO extends BasicDAO implements ISurveyTeamDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(SurveyTeam surveyTeam) throws DAOException {
		try {
			em.persist(surveyTeam);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert SurveyTeam", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyTeam> findAllSurveyTeam() {
		List<SurveyTeam> result = new ArrayList<>();
		try {
			Query q = em.createNamedQuery("SurveyTeam.findAll");
			result = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find All SurveyTeam", pe);
		}
		return result;
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	// public List<SurveyTeam> findAllSurveyTeamMember(String id) {
	// List<SurveyTeam> result = null;
	// try {
	// Query q = em.createQuery("SELECT t.name,m.name From SurveyTeam t LEFT
	// JOIN t.surveyMemberList m WHERE t.id IN :s.ids");
	// q.setParameter("id", id);
	// result = q.getResultList();
	// em.flush();
	// } catch (PersistenceException pe) {
	// throw translate("Failed to find of SurveyTeam and SurveyMember.", pe);
	// }
	// return result;
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyTeam(SurveyTeam surveyTeam) {
		try {
			em.merge(surveyTeam);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update SurveyTeam", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyTeam(SurveyTeam surveyTeam) {
		try {
			surveyTeam = em.merge(surveyTeam);
			em.remove(surveyTeam);
		} catch (PersistenceException pe) {
			throw translate("Failed to delete SurveyTeam", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyTeam> findByCriteria(String criteria) {
		List<SurveyTeam> result = null;
		try {
			// Query q = em.createNamedQuery("Township.findByCriteria");
			Query q = em.createQuery("Select s from SurveyTeam s where s.name Like '" + criteria + "%'");
			// q.setParameter("criteriaValue", "%" + criteria + "%");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find by criteria of SurveyTeam.", pe);
		}
		return result;
	}
}
