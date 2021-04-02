package org.ace.insurance.system.common.surveyMember.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyMember.persistence.interfaces.ISurveyMemberDAO;
import org.ace.insurance.system.common.surveyMember.service.interfaces.ISurveyMemberService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "SurveyMemberService")
public class SurveyMemberService extends BaseService implements ISurveyMemberService {

	@Resource(name = "SurveyMemberDAO")
	private ISurveyMemberDAO surveyMemberDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertSurveyMember(SurveyMember surveyMember) {
		try {
			surveyMemberDAO.insert(surveyMember);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new SurveyMember", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyMember> findAllSurveyMember() {
		List<SurveyMember> result = new ArrayList<>();
		try {
			result = surveyMemberDAO.findAllSurveyMember();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyMember", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSurveyMember(SurveyMember surveyMember) {
		try {
			surveyMemberDAO.updateSurveyMember(surveyMember);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyMember", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSurveyMember(SurveyMember surveyMember) {
		try {
			surveyMemberDAO.deleteSurveyMember(surveyMember);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find All SurveyMember", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SurveyMember> findByCriteria(String criteria) {
		List<SurveyMember> result = null;
		try {
			result = surveyMemberDAO.findByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find SurveyMember by criteria " + criteria, e);
		}
		return result;
	}

}
