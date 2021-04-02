package org.ace.insurance.system.common.surveyTeam.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.surveyTeam.SurveyTeam;

public interface ISurveyTeamService {

	public void insertSurveyTeam(SurveyTeam surveyTeam);

	public List<SurveyTeam> findAllSurveyTeam();

	// public List<SurveyTeam> findAllSurveyTeamMember(String id);

	public void updateSurveyTeam(SurveyTeam surveyTeam);

	public void deleteSurveyTeam(SurveyTeam surveyTeam);

	public List<SurveyTeam> findByCriteria(String criteria);
}
