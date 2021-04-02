package org.ace.insurance.web.manage.life.paidUp.proposal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.service.interfaces.ILifePaidUpProposalService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifePaidUpProposalActionBean")
public class LifePaidUpProposalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePaidUpProposalService}")
	private ILifePaidUpProposalService lifePaidUpProposalService;

	public void setLifePaidUpProposalService(ILifePaidUpProposalService lifePaidUpProposalService) {
		this.lifePaidUpProposalService = lifePaidUpProposalService;
	}

	@ManagedProperty(value = "#{ClaimProductService}")
	private IClaimProductService claimProductService;

	public void setClaimProductService(IClaimProductService claimProductService) {
		this.claimProductService = claimProductService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private User user;
	private LifePaidUpProposal lifePaidUpProposal;
	private List<PaymentTrackDTO> paymentList;
	private String remark;
	private User responsiblePerson;
	private int paymentYear;
	private int paymentMonth;
	private Period paymentPeriod;
	private ClaimProduct claimProduct;
	private double paidUpAmount;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		claimProduct = claimProductService.findClaimProductById(KeyFactorChecker.getLifePaidUpProductId());
		createNewLifePaidUpProposal();
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifePolicy");
		removeParam("claimProduct");
	}

	public void createNewLifePaidUpProposal() {
		lifePaidUpProposal = new LifePaidUpProposal();
		lifePaidUpProposal.setSubmittedDate(new Date());
		lifePaidUpProposal.setClaimProduct(claimProduct);
	}

	public void returnLifePolicy(SelectEvent event) {
		double receivedPremium = 0.0;
		LifePolicySearch searchPolicy = (LifePolicySearch) event.getObject();
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(searchPolicy.getPolicyNo());
		if (checkLifePolicy(lifePolicy)) {
			lifePaidUpProposal.setLifePolicy(lifePolicy);
			lifePaidUpProposal.setPolicyNo(lifePolicy.getPolicyNo());
			lifePaidUpProposal.setSumInsured(lifePolicy.getSumInsured());
			paymentPeriod = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getCoverageDate(), false, true);
			paymentList = paymentService.findPaymentTrack(lifePolicy.getPolicyNo());
			for (PaymentTrackDTO paymentTrack : paymentList) {
				receivedPremium += paymentTrack.getPremium().doubleValue();
			}
			lifePaidUpProposal.setReceivedPremium(receivedPremium);
			loadPaymentConfig();
		}
	}

	private boolean checkLifePolicy(LifePolicy lifePolicy) {
		boolean result = true;
		List<PolicyInsuredPerson> persons = lifePolicy.getPolicyInsuredPersonList();
		if (persons.size() > 1) {
			addInfoMessage(null, MessageId.INVALID_LIFE_PAIDUP_POLICY, lifePolicy.getPolicyNo());
			return false;
		}
		PolicyInsuredPerson person = persons.get(0);
		Period period = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getActivedPolicyEndDate(), false, true);
		int policyYear = period.getYears();
		period = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getActivedPolicyEndDate(), false, true);
		int paymentYear = period.getYears();
		if (policyYear >= 13) {
			result = paymentYear >= claimProduct.getLongTermActivatedYear() ? true : false;
			if (!result) {
				addInfoMessage(null, MessageId.INVALID_LIFE_PAIDUP_PERIOD, lifePolicy.getPolicyNo(), policyYear, claimProduct.getLongTermActivatedYear());
			}
		} else {
			result = paymentYear >= claimProduct.getShortTermActivatedYear() ? true : false;
			if (!result) {
				addInfoMessage(null, MessageId.INVALID_LIFE_PAIDUP_PERIOD, lifePolicy.getPolicyNo(), policyYear, claimProduct.getShortTermActivatedYear());
			}
		}
		if (result & !paymentService.findPaymentByReferenceNoAndIsNotComplete(lifePolicy.getId())) {
			result = false;
			addInfoMessage(null, MessageId.PAYMENT_PROCESS_NOTCOMPLETE);
		}
		return result;
	}

	public String addNewLifePaidUpProposal() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(null, getLoginBranchId(), remark, WorkflowTask.APPROVAL, ReferenceType.LIFE_PAIDUP_PROPOSAL, TransactionType.UNDERWRITING,
					user, responsiblePerson);
			lifePaidUpProposalService.addNewLifePaidUpProposal(lifePaidUpProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifePaidUpProposal.getProposalNo());
			createNewLifePaidUpProposal();
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public String getPaymentTerm() {
		paymentMonth = paymentPeriod.getMonths();
		paymentYear = paymentPeriod.getYears();
		StringBuffer paymentTerm = new StringBuffer();
		paymentTerm.append(paymentYear + " YEARS ");
		if (paymentMonth > 0) {
			paymentTerm.append(paymentMonth == 1 ? " AND" + paymentMonth + " MONTH" : paymentMonth + " MONTHS");
		}
		return paymentTerm.toString();
	}

	public void loadPaymentConfig() {
		double reqAmount = 0.0;
		paymentMonth = paymentPeriod.getMonths();
		paymentMonth = 12 - paymentMonth;
		paymentYear = paymentPeriod.getYears();
		int paymentType = lifePaidUpProposal.getLifePolicy().getPaymentType().getMonth();
		double basicPremium = (Double) paymentList.get(0).getPremium().doubleValue();
		double premium = lifePaidUpProposal.getLifePolicy().getPolicyInsuredPersonList().get(0).getPremium();
		switch (paymentType) {
			case 1: {
				reqAmount = basicPremium * paymentMonth;
			}
				break;
			case 3: {
				reqAmount = basicPremium * (paymentMonth / paymentType);
			}
				break;
			case 6: {
				reqAmount = basicPremium * (paymentMonth / paymentType);
			}
				break;
			// case 12: {
			// reqAmount = basicPremium;
			// }
			// break;
			default:
				break;
		}
		if (paymentMonth >= 6) {
			paymentYear = paymentYear + 1;
			reqAmount = premium - reqAmount;
			lifePaidUpProposal.setReqAmount(reqAmount);
		} else {
			lifePaidUpProposal.setReqAmount(reqAmount);
		}
		double realAmount = basicPremium * ((paymentYear * 12) / paymentType);

		lifePaidUpProposal.setRealPremium(realAmount);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		lifePaidUpProposal.setBranch(branch);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void selectUser() {
		selectUser(WorkflowTask.APPROVAL, WorkFlowType.LIFE_PAIDUP, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	public ILifePaidUpProposalService getLifePaidUpProposalService() {
		return lifePaidUpProposalService;
	}

	public User getUser() {
		return user;
	}

	public LifePaidUpProposal getLifePaidUpProposal() {
		return lifePaidUpProposal;
	}

	public List<PaymentTrackDTO> getPaymentList() {
		return paymentList;
	}

	public String getRemark() {
		return remark;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public int getPaymentYear() {
		return paymentYear;
	}

	public int getPaymentMonth() {
		return paymentMonth;
	}

	public Period getPaymentPeriod() {
		return paymentPeriod;
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public double getPaidUpAmount() {
		return paidUpAmount;
	}

	public void selectActiveLifePolicyNo() {
		selectLifePolicyNo("Actived");
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}
