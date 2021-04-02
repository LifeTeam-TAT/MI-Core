package org.ace.insurance.web.dialog;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.MedicalPolicyCriteriaItems;
import org.ace.insurance.medical.claim.MedicalPolicyCriteria;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "MedicalPolicyNoDialogActionBean")
@ViewScoped
public class MedicalPolicyNoDialogActionBean extends BaseBean {

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	// smt
	// private LCL001 criteria;
	private MedicalPolicyCriteria medicalPolicyCriteria;
	private LazyDataModel<MedicalPolicy> lazyModel;
	private List<MedicalPolicy> medicalPolicyList;

	@PostConstruct
	public void init() {
		resetCriteria();
		// TODO FIXME PSH
		// medicalPolicyList =
		// medicalPolicyService.findMedicalPolicyForClaimByCriteria(medicalPolicyCriteria);
		lazyModel = new LazyDataModelUtil(medicalPolicyList);
	}

	public void searchPolicyCriteria() {
		String formId = "policyNoForm";
		if ((medicalPolicyCriteria.getMedicalPolicyCriteriaItems() == null) || (medicalPolicyCriteria.getMedicalPolicyCriteriaItems().toString().equals("Select Criteria"))) {
			addErrorMessage(formId + ":selectMedicalPolicyCriteria", MessageId.PLEASE_SELECT_POLICY_CRITERIA);
		} else {
			// TODO FIXME PSH
			// medicalPolicyList =
			// medicalPolicyService.findMedicalPolicyForClaimByCriteria(medicalPolicyCriteria);
			lazyModel = new LazyDataModelUtil(medicalPolicyList);
		}
	}

	public void resetPolicyCriteria() {
		medicalPolicyCriteria.setCriteriaValue(null);
		medicalPolicyCriteria.setMedicalPolicyCriteriaItems(null);
		// TODO FIXME PSH
		// medicalPolicyList =
		// medicalPolicyService.findMedicalPolicyForClaimByCriteria(null);
	}

	public MedicalPolicyCriteriaItems[] getMedicalPolicyCriteriaList() {
		return MedicalPolicyCriteriaItems.values();
	}

	public void resetCriteria() {
		// smt
		// criteria = new LCL001();
		medicalPolicyCriteria = new MedicalPolicyCriteria();
	}

	public void selectMedicalPolicyNo(MedicalPolicy medicalPolicy) {
		putParam("medicalPolicy", medicalPolicy);
		RequestContext.getCurrentInstance().closeDialog(medicalPolicy);
	}

	public MedicalPolicyCriteria getMedicalPolicyCriteria() {
		return medicalPolicyCriteria;
	}

	// smt
	// public LCL001 getCriteria() {
	// return criteria;
	// }
	//
	// public void setCriteria(LCL001 criteria) {
	// this.criteria = criteria;
	// }

	public LazyDataModel<MedicalPolicy> getLazyModel() {
		return lazyModel;
	}

}
