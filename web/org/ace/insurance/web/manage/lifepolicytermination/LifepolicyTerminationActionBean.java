package org.ace.insurance.web.manage.lifepolicytermination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.GenericDataModel;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.medicalpolicytermination.service.interfaces.IMedicalPolicyTerminateService;
import org.ace.insurance.policytermination.service.interfaces.IPolicyTerminationService;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "LifepolicyTerminationActionBean")
public class LifepolicyTerminationActionBean extends BaseBean {

	@ManagedProperty(value = "#{PolicyTerminationService}")
	private IPolicyTerminationService lifePolicyterminationservice;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	@ManagedProperty(value = "#{MedicalPolicyTerminateService}")
	private IMedicalPolicyTerminateService medicalPolicyterminationservice;

	public void setLifePolicyterminationservice(IPolicyTerminationService lifePolicyterminationservice) {
		this.lifePolicyterminationservice = lifePolicyterminationservice;
	}

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	public void setMedicalPolicyterminationservice(IMedicalPolicyTerminateService medicalPolicyterminationservice) {
		this.medicalPolicyterminationservice = medicalPolicyterminationservice;
	}

	public void setMedicalPolicy(MedicalPolicy medicalPolicy) {
		this.medicalPolicy = medicalPolicy;
	}

	private LifePolicy lifepolicy;

	private MedicalPolicy medicalPolicy;

	private GenericDataModel<IDataModel> policyDataModel;

	private String policyNo;
	private User user;

	private InsuranceType insuraceType;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
	}

	public void reset() {
		policyDataModel = new GenericDataModel<>();
		policyNo = "";
	}

	public void findlifepolicytermination() {
		if (InsuranceType.LIFE.equals(insuraceType)) {
			List<LifePolicy> lifepolicylist = new ArrayList<>();
			this.lifepolicy = lifePolicyService.findLifePolicyByPolicyNo(policyNo);
			if (lifepolicy != null)
				lifepolicylist.add(lifepolicy);
			policyDataModel = new GenericDataModel(lifepolicylist);
		} else if (InsuranceType.HEALTH.equals(insuraceType)) {
			List<MedicalPolicy> medicalPolicylist = new ArrayList<>();
			this.medicalPolicy = medicalPolicyService.findMedicalPolicyByPolicyNo(policyNo);
			if (medicalPolicy != null)
				medicalPolicylist.add(medicalPolicy);
			policyDataModel = new GenericDataModel(medicalPolicylist);
		}

		if (policyDataModel.getRowCount() == 0) {
			addErrorMessage("lifePolicyTableForm:lifePolicyTable", MessageId.NO_RECORDS_FOUND);
		}

	}

	public List<InsuranceType> getInsuranceTypes() {
		return Arrays.asList(new InsuranceType[] { InsuranceType.LIFE, InsuranceType.HEALTH });
	}

	public void resetlifepolicytermination() {

	}

	public void terminate() {
		try {
			if (InsuranceType.LIFE.equals(insuraceType)) {
				lifePolicyterminationservice.terminatePolicy(lifepolicy);
				addInfoMessage("Terminate process for " + lifepolicy.getPolicyNo() + " is successfully terminated");
			} else {
				medicalPolicyterminationservice.terminatePolicy(medicalPolicy);
				addInfoMessage("Terminate process for " + medicalPolicy.getPolicyNo() + " is successfully terminated");
			}
			reset();
		} catch (SystemException e) {
			addErrorMessage("Fail to terminate policy");
		}
	}

	public GenericDataModel<IDataModel> getPolicyDataModel() {
		return policyDataModel;
	}

	public InsuranceType getInsuraceType() {
		return insuraceType;
	}

	public void setInsuraceType(InsuranceType insuraceType) {
		this.insuraceType = insuraceType;
	}

	public LifePolicy getLifepolicy() {
		return lifepolicy;
	}

	public void setLifepolicy(LifePolicy lifepolicy) {
		this.lifepolicy = lifepolicy;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
