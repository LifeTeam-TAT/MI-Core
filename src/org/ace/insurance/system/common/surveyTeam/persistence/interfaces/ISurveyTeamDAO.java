package org.ace.insurance.system.common.surveyTeam.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.surveyTeam.SurveyTeam;

public interface ISurveyTeamDAO {

	void insert(SurveyTeam surveyTeam);

	List<SurveyTeam> findAllSurveyTeam();

	// List<SurveyTeam> findAllSurveyTeamMember(String id);

	void updateSurveyTeam(SurveyTeam surveyTeam);

	void deleteSurveyTeam(SurveyTeam surveyTeam);

	List<SurveyTeam> findByCriteria(String criteria);

}
