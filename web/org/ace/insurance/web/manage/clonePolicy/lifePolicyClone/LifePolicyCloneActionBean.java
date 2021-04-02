package org.ace.insurance.web.manage.clonePolicy.lifePolicyClone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.apache.commons.lang.StringUtils;

@ViewScoped
@ManagedBean(name = "LifePolicyCloneActionBean")
public class LifePolicyCloneActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private PolicyCriteria policyCriteria;
	private List<LPC001> lifePolicyList;
	private LifePolicy lifePolicy;
	private User user;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		lifePolicyList = new ArrayList<LPC001>();
		policyCriteria = new PolicyCriteria();
	}

	public void search() {
		if (inputCheck(policyCriteria)) {
			lifePolicyList = lifePolicyService.findLifePolicyCloneByPolicyCriteria(policyCriteria, 30);
		}
	}

	public void reset() {
		policyCriteria.setCriteriaValue(null);
		policyCriteria.setPolicyCriteria(null);
		if (lifePolicyList != null) {
			lifePolicyList.clear();
		}

	}

	private boolean inputCheck(PolicyCriteria policyCriteria) {
		boolean result = true;
		String formId = "LifePolicyCloneForm";
		if ((policyCriteria.getPolicyCriteria() == null)) {
			addErrorMessage(formId + ":selectPolicyCriteria", MessageId.PLEASE_SELECT_POLICY_CRITERIA);
			result = false;
		}
		if (StringUtils.isBlank(policyCriteria.getCriteriaValue())) {
			addErrorMessage(formId + ":policyCriteria", MessageId.CRITERIA_VALUE_REQUIRED);
			result = false;
		}
		return result;
	}

	public void cloneLifePolicy(LPC001 policy) {
		lifePolicy = lifePolicyService.findLifePolicyById(policy.getId());

	}

	public List<LPC001> getLifePolicyList() {
		return lifePolicyList;
	}

	public PolicyCriteria getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteria policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public User getUser() {
		return user;
	}

	public String outjectLProposal() {
		putParam("lifePolicy", lifePolicy);
		putParam("clonePolicy", true);
		return "lifeProposal";
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

}
