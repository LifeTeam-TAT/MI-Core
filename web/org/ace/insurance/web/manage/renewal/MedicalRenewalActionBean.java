package org.ace.insurance.web.manage.renewal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.MedicalPolicyCriteriaItems;
import org.ace.insurance.medical.claim.MedicalPolicyCriteria;
import org.ace.insurance.medical.policy.MPL001;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.commons.lang.StringUtils;



@ViewScoped
@ManagedBean(name = "MedicalRenewalActionBean")
public class MedicalRenewalActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService policyService;

	public void setPolicyService(IMedicalPolicyService policyService) {
		this.policyService = policyService;
	}

	private MedicalPolicyCriteria criteria;
	private List<MPL001> policyList;
	private MPL001 policyDTO;

	@PostConstruct
	public void init() {
		criteria = new MedicalPolicyCriteria();
		policyList = new ArrayList<MPL001>();
	}

	public void search() {
		if (inputCheck(criteria)) {
			policyList = policyService.findMedicalPolicyByCriteria(criteria);
		}
	}

	public void reset() {
		criteria.setCriteriaValue(null);
		criteria.setMedicalPolicyCriteriaItems(null);
		policyList = new ArrayList<MPL001>();
	}

	private boolean inputCheck(MedicalPolicyCriteria policyCriteria) {
		boolean result = true;
		String formId = "medicalRenewalForm";
		if ((policyCriteria.getMedicalPolicyCriteriaItems() == null)) {
			addErrorMessage(formId + ":selectMedicalPolicyCriteria", MessageId.PLEASE_SELECT_POLICY_CRITERIA);
			result = false;
		}
		if (StringUtils.isBlank(policyCriteria.getCriteriaValue())) {
			addErrorMessage(formId + ":policyCriteria", MessageId.CRITERIA_VALUE_REQUIRED);
			result = false;
		}
		return result;
	}

	public void prepareToRenewPolicy(MPL001 policyDTO) {
		this.policyDTO = policyDTO;
	}

	public String renewPolicy() {
		MedicalPolicy medicalPolicy = policyService.findMedicalPolicyById(this.policyDTO.getId());
		putParam("medicalPolicy", medicalPolicy);
		return "renewalMedicalProposal";
	}

	public MedicalPolicyCriteriaItems[] getMedicalPolicyCriteriaList() {
		return MedicalPolicyCriteriaItems.values();
	}

	public MedicalPolicyCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(MedicalPolicyCriteria criteria) {
		this.criteria = criteria;
	}

	public List<MPL001> getPolicyList() {
		return policyList;
	}

	public MPL001 getPolicyDTO() {
		return policyDTO;
	}

	public void setPolicyDTO(MPL001 policyDTO) {
		this.policyDTO = policyDTO;
	}

}
