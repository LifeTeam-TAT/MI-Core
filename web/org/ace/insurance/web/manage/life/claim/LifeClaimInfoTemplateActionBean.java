package org.ace.insurance.web.manage.life.claim;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "LifeClaimInfoTemplateActionBean")
public class LifeClaimInfoTemplateActionBean extends BaseBean {
	private boolean death;
	private boolean disbility;
	private boolean hospital;
	private LifeClaimProposal lifeClaimProposal;
	private List<WorkFlowHistory> workflowList;
	private DisabilityLifeClaim disabiliyClaim;
	private LifeDeathClaim lifeDeathClaim;
	private LifeHospitalizedClaim hospitalClaim;

	@PostConstruct
	public void init() {
		initializeInjection();
		if (lifeClaimProposal.getLifePolicyClaim() instanceof DisabilityLifeClaim) {
			disbility = true;
			disabiliyClaim = (DisabilityLifeClaim) lifeClaimProposal.getLifePolicyClaim();
		} else if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeDeathClaim) {
			death = true;
			lifeDeathClaim = (LifeDeathClaim) lifeClaimProposal.getLifePolicyClaim();
		} else {
			hospital = true;
			hospitalClaim = (LifeHospitalizedClaim) lifeClaimProposal.getLifePolicyClaim();
		}
	}

	@SuppressWarnings("unchecked")
	private void initializeInjection() {
		lifeClaimProposal = (lifeClaimProposal == null) ? (LifeClaimProposal) getParam("lifeClaimProposal") : lifeClaimProposal;
		workflowList = (List<WorkFlowHistory>) getParam("workFlowList");
	}

	public LifeClaimProposal getLifeClaimProposal() {
		return lifeClaimProposal;
	}

	public List<WorkFlowHistory> getWorkflowList() {
		return workflowList;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public boolean isDisbility() {
		return disbility;
	}

	public void setDisbility(boolean disbility) {
		this.disbility = disbility;
	}

	public boolean isHospital() {
		return hospital;
	}

	public void setHospital(boolean hospital) {
		this.hospital = hospital;
	}

	public DisabilityLifeClaim getDisabiliyClaim() {
		return disabiliyClaim;
	}

	public LifeDeathClaim getLifeDeathClaim() {
		return lifeDeathClaim;
	}

	public LifeHospitalizedClaim getHospitalClaim() {
		return hospitalClaim;
	}

}
