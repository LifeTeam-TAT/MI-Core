package org.ace.insurance.web.dialog;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.java.web.common.BaseBean;

@ManagedBean(name = "LifeProposalEquireActionBean")
@ViewScoped
public class LifeProposalEquireActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private LifeProposal lifeProposal;
	private ProposalInsuredPerson insuredPerson;
	private InsuredPersonBeneficiaries beneficiaries;

	@PostConstruct
	public void init() {
		lifeProposal = (LifeProposal) getParam("LifeProposal");
		insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
	}

	public void showBeneficiaries(InsuredPersonBeneficiaries beneficiaries) {
		this.beneficiaries = beneficiaries;
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public ProposalInsuredPerson getInsuredPerson() {
		return insuredPerson;
	}

	public InsuredPersonBeneficiaries getBeneficiaries() {
		return beneficiaries;
	}
}
