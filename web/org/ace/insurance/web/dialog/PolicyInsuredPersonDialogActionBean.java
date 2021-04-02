package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "PolicyInsuredPersonDialogActionBean")
@ViewScoped
public class PolicyInsuredPersonDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<PolicyInsuredPerson> policyInsuredPersonList;

	@PostConstruct
	public void init() {
		if (isExistParam("LIFEPOLICY")) {
			LifePolicy lifepolicy = (LifePolicy) getParam("LIFEPOLICY");
			policyInsuredPersonList = lifepolicy.getPolicyInsuredPersonList();
		}

	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public void selectPolicyInsuredPerson(PolicyInsuredPerson insuredPerson) {
		PrimeFaces.current().dialog().closeDynamic(insuredPerson);
	}

}
