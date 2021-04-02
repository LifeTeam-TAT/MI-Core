package org.ace.insurance.system.common.surveyMember.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.surveyMember.SurveyMember;

public interface ISurveyMemberDAO {

	void insert(SurveyMember surveyMember);

	List<SurveyMember> findAllSurveyMember();

	void updateSurveyMember(SurveyMember surveyMember);

	void deleteSurveyMember(SurveyMember surveyMember);

	List<SurveyMember> findByCriteria(String criteria);

}
