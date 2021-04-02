package org.ace.insurance.web.manage.endorsement.life.proposal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.apache.commons.lang.StringUtils;

@ViewScoped
@ManagedBean(name = "LifePolicyEndorsementActionBean")
public class LifePolicyEndorsementActionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PolicyCriteria policyCriteria;
	private List<LPC001> lifePolicyList;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@PostConstruct
	public void init() {
		lifePolicyList = new ArrayList<LPC001>();
		policyCriteria = new PolicyCriteria();
	}

	public void search() {
		if (inputCheck(policyCriteria)) {
			lifePolicyList = lifePolicyService.findByPolicyCriteria(policyCriteria, 30);
		}
	}

	public void reset() {
		policyCriteria.setCriteriaValue(null);
		policyCriteria.setPolicyCriteria(null);
		policyCriteria.setProduct(null);
		lifePolicyList = new ArrayList<LPC001>();
	}

	private boolean inputCheck(PolicyCriteria policyCriteria) {
		boolean result = true;
		String formId = "LifePolicyEndorsementForm";
		if ((policyCriteria.getPolicyCriteria() == null)) {
			addErrorMessage(formId + ":selectPolicyCriteria", MessageId.PLEASE_SELECT_POLICY_CRITERIA);
			result = false;
		}
		if (StringUtils.isBlank(policyCriteria.getCriteriaValue())) {
			addErrorMessage(formId + ":policyCriteria", MessageId.CRITERIA_VALUE_REQUIRED);
			result = false;
		}
		if (policyCriteria.getProduct() == null) {
			addErrorMessage(formId + ":selectProductCriteria", MessageId.PLEASE_SELECT_PRODUCT);
			result = false;
		}
		return result;
	}

	public String editLifePolicy(LPC001 lifePolicy) {
		LifePolicy policy = lifePolicyService.findLifePolicyById(lifePolicy.getId());
		putParam("lifePolicy", policy);
		putParam("InsuranceType", InsuranceType.LIFE);
		return "endorselifeProposal";
	}

	public PolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public List<LPC001> getLifePolicyList() {
		return lifePolicyList;
	}
}
