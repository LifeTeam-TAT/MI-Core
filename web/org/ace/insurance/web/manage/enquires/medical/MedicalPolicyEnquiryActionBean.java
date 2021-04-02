package org.ace.insurance.web.manage.enquires.medical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.medical.policy.MED002;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.service.interfaces.IMedicalPolicyService;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.SelectEvent;

/**
 * @author PPA
 *
 */
@ViewScoped
@ManagedBean(name = "MedicalPolicyEnquiryActionBean")
public class MedicalPolicyEnquiryActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{MedicalPolicyService}")
	private IMedicalPolicyService medicalPolicyService;

	public void setMedicalPolicyService(IMedicalPolicyService medicalPolicyService) {
		this.medicalPolicyService = medicalPolicyService;
	}

	private User user;
	private EnquiryCriteria criteria;
	private List<MED002> policyList;
	private MedicalPolicy policy;
	private HealthType healthType;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		resetCriteria();
	}

	public void resetCriteria() {
		policyList = new ArrayList<MED002>();
		criteria = new EnquiryCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		criteria.setEndDate(new Date());
		this.healthType = null;
		policyList = new ArrayList<>();
		criteria.setBranch(user.getLoginBranch());
	}

	public void findPolicyListByEnquiryCriteria() {
		policyList = medicalPolicyService.findMedicalPolicyForPolicyEnquiry(criteria);
		sortLists(policyList);
	}

	public void sortLists(List<MED002> farmerPolicyList) {
		Collections.sort(farmerPolicyList, new Comparator<MED002>() {
			@Override
			public int compare(MED002 obj1, MED002 obj2) {
				if (obj1.getPolicyNo().equals(obj2.getPolicyNo()))
					return -1;
				else
					return obj1.getPolicyNo().compareTo(obj2.getPolicyNo());
			}
		});
	}

	public String editMedicalPolicy(String id) {
		policy = medicalPolicyService.findMedicalPolicyById(id);
		putParam("medicalPolicy", policy);
		outjectHealthType(healthType);
		return "editMedicalPolicy";

	}

	public List<WorkFlowHistory> getWorkFlowList() {
		if (policy != null) {
			return workFlowService.findWorkFlowHistoryByRefNo(policy.getId());
		} else {
			return null;
		}
	}

	public void openTemplateDialog(MED002 dto) {
		policy = medicalPolicyService.findMedicalPolicyById(dto.getId());
		putParam("medicalProposal", policy.getMedicalProposal());
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	public String addAttachment(String policyId) {
		MedicalPolicy medicalPolicy = medicalPolicyService.findMedicalPolicyById(policyId);
		putParam("InsuranceType", InsuranceType.HEALTH);
		putParam("medicalPolicy", medicalPolicy);
		return "medicalPolicyAttachment";
	}

	private void outjectHealthType(HealthType healthType) {
		putParam("healthType", healthType);
	}

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProduct(product);
	}

	public void returnAgent(SelectEvent event) {
		Agent agent = (Agent) event.getObject();
		criteria.setAgent(agent);
	}

	public void returnCustomer(SelectEvent event) {
		Customer customer = (Customer) event.getObject();
		criteria.setCustomer(customer);
	}

	public void returnOrganization(SelectEvent event) {
		Organization organization = (Organization) event.getObject();
		criteria.setOrganization(organization);
	}

	public EnquiryCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(EnquiryCriteria criteria) {
		this.criteria = criteria;
	}

	public List<SaleChannelType> getSaleChannel() {
		return Arrays.asList(SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING);
	}

	public ProposalType[] getProposalTypes() {
		ProposalType[] types = { ProposalType.UNDERWRITING, ProposalType.ENDORSEMENT, ProposalType.RENEWAL };
		return types;
	}

	public List<MED002> getPolicyList() {
		return policyList;
	}

	public List<Branch> getBranches() {
		return user.getAccessBranchList();
	}
}
