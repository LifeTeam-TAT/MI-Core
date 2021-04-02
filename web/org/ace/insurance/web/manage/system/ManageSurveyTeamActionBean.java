package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyMember.service.interfaces.ISurveyMemberService;
import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.system.common.surveyTeam.service.interfaces.ISurveyTeamService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageSurveyTeamActionBean")
public class ManageSurveyTeamActionBean extends BaseBean {

	@ManagedProperty(value = "#{SurveyTeamService}")
	private ISurveyTeamService surveyTeamService;

	public void setSurveyTeamService(ISurveyTeamService surveyTeamService) {
		this.surveyTeamService = surveyTeamService;
	}

	@ManagedProperty(value = "#{SurveyMemberService}")
	private ISurveyMemberService surveyMemberService;

	public void setSurveyMemberService(ISurveyMemberService surveyMemberService) {
		this.surveyMemberService = surveyMemberService;
	}

	private SurveyTeam surveyTeam;

	private List<SurveyTeam> surveyTeamList;
	private List<SurveyMember> surveyMemberList;
	private List<SurveyMember> selectedSurveyMemberList;

	private boolean createNew;
	private String criteria;

	@PostConstruct
	public void init() {
		createNewInstances();
		loadList();
	}

	public void createNewInstances() {
		criteria = null;
		createNew = true;
		surveyTeam = new SurveyTeam();
	}

	public void loadList() {
		surveyTeamList = surveyTeamService.findAllSurveyTeam();
		surveyMemberList = surveyMemberService.findAllSurveyMember();
	}

	public void addNewSurveyTeam() {
		try {
			surveyTeamService.insertSurveyTeam(surveyTeam);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, surveyTeam.getName());
			createNewInstances();
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateSurveyTeam() {
		try {
			surveyTeamService.updateSurveyTeam(surveyTeam);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, surveyTeam.getName());
			createNewInstances();
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteSurveyTeam() {
		try {
			surveyTeamService.deleteSurveyTeam(surveyTeam);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, surveyTeam.getName());
			createNewInstances();
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void removeSurveyMemberList(SurveyMember surveyMember) {
		surveyTeam.getSurveyMemberList().remove(surveyMember);
	}

	public void saveSurveyMemberList() {
		surveyTeam.setSurveyMemberList(selectedSurveyMemberList);
	}

	public void preparedUpdateSurveyMemberList() {
		selectedSurveyMemberList = surveyTeam.getSurveyMemberList();
	}

	public void prepareUpdateSurveyTeam(SurveyTeam surveyTeam) {
		createNew = false;
		this.surveyTeam = surveyTeam;
	}

	public SurveyTeam getSurveyTeam() {
		return surveyTeam;
	}

	public void setSurveyTeam(SurveyTeam surveyTeam) {
		this.surveyTeam = surveyTeam;
	}

	public List<SurveyTeam> getSurveyTeamList() {
		return surveyTeamList;
	}

	public List<SurveyMember> getSurveyMemberList() {
		return surveyMemberList;
	}

	public List<SurveyMember> getSelectedSurveyMemberList() {
		return selectedSurveyMemberList;
	}

	public void setSelectedSurveyMemberList(List<SurveyMember> selectedSurveyMemberList) {
		this.selectedSurveyMemberList = selectedSurveyMemberList;
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
