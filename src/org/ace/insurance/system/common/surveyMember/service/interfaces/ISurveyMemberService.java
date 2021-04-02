package org.ace.insurance.system.common.surveyMember.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.surveyMember.SurveyMember;

public interface ISurveyMemberService {

	public void insertSurveyMember(SurveyMember surveyMember);

	public List<SurveyMember> findAllSurveyMember();

	public void updateSurveyMember(SurveyMember surveyMember);

	public void deleteSurveyMember(SurveyMember surveyMember);

	public List<SurveyMember> findByCriteria(String criteria);

}
