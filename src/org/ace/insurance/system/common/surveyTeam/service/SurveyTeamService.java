package org.ace.insurance.system.common.surveyTeam.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.system.common.surveyTeam.persistence.interfaces.ISurveyTeamDAO;
import org.ace.insurance.system.common.surveyTeam.service.interfaces.ISurveyTeamService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SurveyTeamService")
public class SurveyTeamService extends BaseService implements ISurveyTeamService {

	@Resource(name = "SurveyTeamDAO")
	private ISurveyTeamDAO surveyTeamDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertSurveyTeam(SurveyTeam surveyTeam) {
		try {
			surveyTeamDAO.insert(surveyTeam);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SurveyTeam", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyTeam> findAllSurveyTeam() {
		List<SurveyTeam> result = new ArrayList<>();
		try {
			result = surveyTeamDAO.findAllSurveyTeam();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyTeam", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyTeam(SurveyTeam surveyTeam) {
		try {
			surveyTeamDAO.updateSurveyTeam(surveyTeam);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyTeam", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyTeam(SurveyTeam surveyTeam) {
		try {
			surveyTeamDAO.deleteSurveyTeam(surveyTeam);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyTeam", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyTeam> findByCriteria(String criteria) {
		List<SurveyTeam> result = null;
		try {
			result = surveyTeamDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SurveyMember by criteria " + criteria, e);
		}
		return result;
	}

	// @Transactional(propagation = Propagation.REQUIRED)
	// public List<SurveyTeam> findAllSurveyTeamMember(String id) {
	// List<SurveyTeam> result = new ArrayList<>();
	// try {
	// result = surveyTeamDAO.findAllSurveyTeamMember(id);
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to find All
	// SurveyTeam", e);
	// }
	// return result;
	// }

}
