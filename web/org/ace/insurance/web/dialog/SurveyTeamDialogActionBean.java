package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.system.common.surveyTeam.service.interfaces.ISurveyTeamService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "SurveyTeamDialogActionBean")
@ViewScoped
public class SurveyTeamDialogActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{SurveyTeamService}")
	private ISurveyTeamService surveyTeamService;

	public void setSurveyTeamService(ISurveyTeamService surveyTeamService) {
		this.surveyTeamService = surveyTeamService;
	}

	private List<SurveyTeam> surveyTeamList;
	private List<SurveyTeam> selectedSurveyTeamList;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (isExistParam("SURVEYTEAMLIST")) {
			selectedSurveyTeamList = (List<SurveyTeam>) getParam("SURVEYTEAMLIST");
		} else {
			selectedSurveyTeamList = new ArrayList<SurveyTeam>();
		}
		surveyTeamList = surveyTeamService.findAllSurveyTeam();
	}

	public List<SurveyTeam> getSurveyTeamList() {
		return surveyTeamList;
	}

	public List<SurveyTeam> getSelectedSurveyTeamList() {
		return selectedSurveyTeamList;
	}

	public void setSelectedSurveyTeamList(List<SurveyTeam> selectedSurveyTeamList) {
		this.selectedSurveyTeamList = selectedSurveyTeamList;
	}

	public void selectSurveyTeam() {
		PrimeFaces.current().dialog().closeDynamic(selectedSurveyTeamList);
	}
}
