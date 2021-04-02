package org.ace.insurance.web.manage.reversal;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "LifeProposalReversalActionBean")
@ViewScoped
public class LifeProposalReversalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private List<LifeProposal> lifeProposalList;
	private LifeProposal lifeproposal;
	private List<WorkFlowHistory> workflowHistoryList;

	@PostConstruct
	public void init() {
		lifeProposalList = lifeProposalService.findAllLifeProposal();
		workflowHistoryList = workFlowService.findWorkFlowHistoryByRefNo(lifeproposal.getId());
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workflowHistoryList;
	}

	public List<LifeProposal> getLifeProposalList() {
		return lifeProposalList;
	}

	public LifeProposal getLifeProposal() {
		return lifeproposal;
	}

	public String editLifeProposal(LifeProposal lifeProposal) {
		this.lifeproposal = lifeProposal;
		return "editLifeProposal";
	}
}
