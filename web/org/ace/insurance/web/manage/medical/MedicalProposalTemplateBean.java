package org.ace.insurance.web.manage.medical;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.java.web.common.BaseBean;

@RequestScoped
@ManagedBean(name = "MedicalProposalTemplateBean")
public class MedicalProposalTemplateBean extends BaseBean {

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	private MedicalProposal medicalProposal;
	private MedicalPolicy medicalPolicy;
	private List<WorkFlowHistory> workFlowList;
	private boolean isShowPolicy;

	@SuppressWarnings("unchecked")
	private void initialization() {
		medicalProposal = (MedicalProposal) getParam("medicalProposal");
		workFlowList = (List<WorkFlowHistory>) getParam("workFlowList");
	}

	@PostConstruct
	public void init() {
		initialization();
	}

	public MedicalPolicy getMedicalPolicy() {
		return medicalPolicy;
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowList;
	}

	public boolean isShowPolicy() {
		return isShowPolicy;
	}
}
