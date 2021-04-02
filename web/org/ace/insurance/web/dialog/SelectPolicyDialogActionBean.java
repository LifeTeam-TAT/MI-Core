package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyCriteriaItems;
import org.ace.insurance.filter.policy.AllPolicyCriteria;
import org.ace.insurance.filter.policy.POLICY001;
import org.ace.insurance.filter.policy.interfaces.IPOLICY_Filter;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "SelectPolicyDialogActionBean")
@ViewScoped
public class SelectPolicyDialogActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{POLICY_Filter}")
	private IPOLICY_Filter policyFilterService;

	public void setPolicyFilterService(IPOLICY_Filter policyFilterService) {
		this.policyFilterService = policyFilterService;
	}

	private AllPolicyCriteria policyCriteria;
	private List<POLICY001> policySearchList;

	@PostConstruct
	public void init() {
		resetCriteria();
	}

	public void search() {
		policySearchList = policyFilterService.findPolicyByCriteria(policyCriteria);
	}

	public void resetCriteria() {
		policyCriteria = new AllPolicyCriteria();
		policySearchList = new ArrayList<>();
	}

	public void selectPolicyNo(POLICY001 policyDTO) {
		PrimeFaces.current().dialog().closeDynamic(policyDTO);
	}

	public PolicyCriteriaItems[] getPolicyCriteriaList() {
		return new PolicyCriteriaItems[] { PolicyCriteriaItems.POLICYNO, PolicyCriteriaItems.CUSTOMERNAME, PolicyCriteriaItems.ORGANIZATIONNAME };
	}

	public InsuranceType[] getInsuranceTypeList() {
		return new InsuranceType[] { InsuranceType.LIFE };
	}

	public AllPolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(AllPolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public List<POLICY001> getPolicySearchList() {
		return policySearchList;
	}

}
