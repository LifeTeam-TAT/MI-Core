package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyMember.service.interfaces.ISurveyMemberService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageSurveyMemberActionBean")
public class ManageSurveyMemberActionBean extends BaseBean {

	@ManagedProperty(value = "#{SurveyMemberService}")
	private ISurveyMemberService surveyMemberService;

	public void setSurveyMemberService(ISurveyMemberService surveyMemberService) {
		this.surveyMemberService = surveyMemberService;
	}

	private SurveyMember surveyMember;
	private List<SurveyMember> surveyMemberList;
	private boolean createNew;
	private String criteria;

	@PostConstruct
	public void init() {
		createNewInstances();
		loadSurveyMember();
	}

	public void createNewInstances() {
		criteria = null;
		createNew = true;
		surveyMember = new SurveyMember();
	}

	public void loadSurveyMember() {
		surveyMemberList = surveyMemberService.findAllSurveyMember();
	}

	public void addNewSurveyMember() {
		try {
			surveyMemberService.insertSurveyMember(surveyMember);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, surveyMember.getName());
			createNewInstances();
			loadSurveyMember();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateSurveyMember() {
		try {
			surveyMemberService.updateSurveyMember(surveyMember);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, surveyMember.getName());
			createNewInstances();
			loadSurveyMember();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteSurveyMember() {
		try {
			surveyMemberService.deleteSurveyMember(surveyMember);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, surveyMember.getName());
			createNewInstances();
			loadSurveyMember();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void prepareUpdateSurveyMember(SurveyMember surveyMember) {
		createNew = false;
		this.surveyMember = surveyMember;
	}

	public SurveyMember getSurveyMember() {
		return surveyMember;
	}

	public void setSurveyMember(SurveyMember surveyMember) {
		this.surveyMember = surveyMember;
	}

	public List<SurveyMember> getSurveyMemberList() {
		return surveyMemberList;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

}
