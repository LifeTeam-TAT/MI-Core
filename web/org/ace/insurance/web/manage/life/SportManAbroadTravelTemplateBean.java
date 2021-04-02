package org.ace.insurance.web.manage.life;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.java.web.common.BaseBean;

@RequestScoped
@ManagedBean(name = "SportManAbroadTravelTemplateBean")
public class SportManAbroadTravelTemplateBean extends BaseBean {
	private List<SportManTravelAbroad> sportManAbroadTravelList;

	private List<WorkFlowHistory> workFlowList;

	@SuppressWarnings("unchecked")
	private void initializeInjection() {
		sportManAbroadTravelList = (List<SportManTravelAbroad>) getParam("sportManAbroadTravelList");
		workFlowList = (List<WorkFlowHistory>) getParam("workFlowList");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
	}

	public List<SportManTravelAbroad> getSportManAbroadTravelList() {
		return sportManAbroadTravelList;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

}
