package org.ace.insurance.web.manage.life.studentLife;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.java.web.common.BaseBean;

@RequestScoped
@ManagedBean(name = "StudentLifePolicyInfoTemplateActionBean")
public class StudentLifePolicyInfoTemplateActionBean extends BaseBean {

	private LifePolicy lifePolicy;
	private List<WorkFlowHistory> workFlowList;

	private void initializeInjection() {
		lifePolicy = (LifePolicy) getParam("lifePolicy");
		workFlowList = (List<WorkFlowHistory>) getParam("workFlowList");
	}

	@PreDestroy
	public void destroy() {
		// removeParam("lifeProposal");
		// removeParam("workFlowList");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

}
