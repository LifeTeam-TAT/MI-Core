package org.ace.insurance.life.claim;

import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;

public class LCBP001 {

	private PolicyInsuredPersonBeneficiaries beneficiaryPerson;
	private boolean isDeath;

	public LCBP001() {
	}

	public LCBP001(PolicyInsuredPersonBeneficiaries beneficiaryPerson, boolean isDeath) {
		super();
		this.beneficiaryPerson = beneficiaryPerson;
		this.isDeath = isDeath;
	}

	public PolicyInsuredPersonBeneficiaries getBeneficiaryPerson() {
		return beneficiaryPerson;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void setBeneficiaryPerson(PolicyInsuredPersonBeneficiaries beneficiaryPerson) {
		this.beneficiaryPerson = beneficiaryPerson;
	}

	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}

}
