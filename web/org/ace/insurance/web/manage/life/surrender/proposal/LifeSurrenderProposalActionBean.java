package org.ace.insurance.web.manage.life.surrender.proposal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.surrender.LifeSurrenderKeyFactor;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.joda.time.Period;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeSurrenderProposalActionBean")
public class LifeSurrenderProposalActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifeSurrenderProposalService}")
	private ILifeSurrenderProposalService lifeSurrenderProposalService;

	public void setLifeSurrenderProposalService(ILifeSurrenderProposalService lifeSurrenderProposalService) {
		this.lifeSurrenderProposalService = lifeSurrenderProposalService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{ClaimProductService}")
	private IClaimProductService claimProductService;

	public void setClaimProductService(IClaimProductService claimProductService) {
		this.claimProductService = claimProductService;
	}

	private User user;
	private LifeSurrenderProposal lifeSurrenderProposal;
	private List<PaymentTrackDTO> paymentList;
	private String remark;
	private User responsiblePerson;
	private boolean isExtendPayment;
	private int paymentYear;
	private int paymentMonth;
	private Period paymentPeriod;
	private ClaimProduct claimProduct;
	private List<ClaimProduct> selectclaimProductlist;

	private double surrenderAmount;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		claimProduct = new ClaimProduct();
		selectclaimProductlist = claimProductService.findAllClaimProduct(KeyFactorChecker.getSurrenderProductId());
		createNewLifeSurrenderProposal();
	}

	public void createNewLifeSurrenderProposal() {
		lifeSurrenderProposal = new LifeSurrenderProposal();
		lifeSurrenderProposal.setSubmittedDate(new Date());
		lifeSurrenderProposal.setClaimProduct(claimProduct);
	}

	public void changeProduct() {
		lifeSurrenderProposal.setClaimProduct(claimProduct);
	}

	public void returnLifePolicy(SelectEvent event) {
		double receivedPremium = 0.0;
		LifePolicySearch searchPolicy = (LifePolicySearch) event.getObject();
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(searchPolicy.getPolicyNo());
		if (checkLifePolicy(lifePolicy)) {
			lifeSurrenderProposal.setLifePolicy(lifePolicy);
			lifeSurrenderProposal.setPolicyNo(lifePolicy.getPolicyNo());
			lifeSurrenderProposal.setSumInsured(lifePolicy.getSumInsured());
			paymentPeriod = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getCoverageDate(), false, true);
			paymentList = paymentService.findPaymentTrack(lifePolicy.getPolicyNo());
			for (PaymentTrackDTO paymentTrack : paymentList) {
				receivedPremium += paymentTrack.getPremium().doubleValue();
			}
			lifeSurrenderProposal.setClaimProduct(claimProduct);
			lifeSurrenderProposal.setReceivedPremium(receivedPremium);
			loadPaymentConfig();
		}

	}

	public String addNewLifeSurrenderProposal() {
		String result = null;
		try {
			PolicyInsuredPerson insuredPerson = lifeSurrenderProposal.getLifePolicy().getInsuredPersonInfo().get(0);
			String age = Integer.toString(insuredPerson.getAge());
			String policyPeriod = Integer.toString(insuredPerson.getLifePolicy().getPeriodOfInsurance());
			LifeSurrenderKeyFactor lsKeyFactor = null;
			List<LifeSurrenderKeyFactor> lsKeyFactorList = new ArrayList<LifeSurrenderKeyFactor>();
			for (KeyFactor keyFactor : claimProduct.getKeyFactors()) {
				if (KeyFactorChecker.isMedicalAge(keyFactor)) {
					lsKeyFactor = new LifeSurrenderKeyFactor(age, keyFactor);
					lsKeyFactorList.add(lsKeyFactor);
				}
				if (KeyFactorChecker.isPolicyPeriod(keyFactor)) {
					lsKeyFactor = new LifeSurrenderKeyFactor(policyPeriod, keyFactor);
					lsKeyFactorList.add(lsKeyFactor);
				}

				if (KeyFactorChecker.isPaymentYear(keyFactor)) {
					lsKeyFactor = new LifeSurrenderKeyFactor(paymentYear + "", keyFactor);
					lsKeyFactorList.add(lsKeyFactor);
				}
			}
			lifeSurrenderProposal.setLifeSurrenderKeyFactors(lsKeyFactorList);
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(null, getLoginBranchId(), remark, WorkflowTask.APPROVAL, ReferenceType.LIFESURRENDER, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			lifeSurrenderProposalService.addNewLifeSurrenderProposal(lifeSurrenderProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeSurrenderProposal.getProposalNo());
			createNewLifeSurrenderProposal();
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void loadPaymentConfig() {
		int paymentType = 0;
		double basicPremium = 0;
		double lifePremium = 0.0;
		paymentMonth = paymentPeriod.getMonths();
		paymentYear = paymentPeriod.getYears();
		if (isExtendPayment) {
			paymentType = lifeSurrenderProposal.getLifePolicy().getPaymentType().getMonth();
			basicPremium = (Double) paymentList.get(0).getPremium().doubleValue();
			switch (paymentType) {
				case 1: {
					lifePremium = basicPremium * (12 - paymentMonth);
				}
					break;
				case 3: {
					lifePremium = basicPremium * (12 - paymentMonth) / paymentType;
				}
					break;
				case 6: {
					lifePremium = basicPremium * (12 - paymentMonth) / paymentType;
				}
					break;
				case 12: {
					lifePremium = basicPremium;
				}
					break;
				default:
					break;
			}

			paymentYear = paymentYear + 1;
			paymentMonth = 0;
		} else {
			lifePremium = 0;
		}

		lifeSurrenderProposal.setLifePremium(lifePremium);
	}

	public void selectUser() {
		selectUser(WorkflowTask.APPROVAL, WorkFlowType.LIFESURRENDER, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	private boolean checkLifePolicy(LifePolicy lifePolicy) {
		boolean result = true;
		List<PolicyInsuredPerson> persons = lifePolicy.getPolicyInsuredPersonList();
		if (persons.size() > 1) {
			addInfoMessage(null, MessageId.INVALID_LIFE_SURRENDER_POLICY, lifePolicy.getPolicyNo());
			return false;
		}

		Period period = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getActivedPolicyEndDate(), false, true);
		int policyYear = period.getYears();
		period = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getActivedPolicyEndDate(), false, true);
		int paymentYear = period.getYears();
		if (policyYear >= 13) {
			result = paymentYear >= claimProduct.getLongTermActivatedYear() ? true : false;
			if (!result) {
				addInfoMessage(null, MessageId.INVALID_LIFE_SURRENDER_PERIOD, lifePolicy.getPolicyNo(), policyYear, claimProduct.getLongTermActivatedYear());
			}

		} else {
			result = paymentYear >= claimProduct.getShortTermActivatedYear() ? true : false;
			if (!result) {
				addInfoMessage(null, MessageId.INVALID_LIFE_SURRENDER_PERIOD, lifePolicy.getPolicyNo(), policyYear, claimProduct.getShortTermActivatedYear());
			}
		}
		return result;
	}

	public LifeSurrenderProposal getLifeSurrenderProposal() {
		return lifeSurrenderProposal;
	}

	public List<PaymentTrackDTO> getPaymentList() {
		return paymentList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public double getSurrenderAmount() {
		return surrenderAmount;
	}

	public boolean isExtendPayment() {
		return isExtendPayment;
	}

	public void setExtendPayment(boolean isExtendPayment) {
		this.isExtendPayment = isExtendPayment;
	}

	public void selectActiveLifePolicyNo() {
		selectLifePolicyNo("Actived");
	}

	public String getPaymentTerm() {
		StringBuffer paymentTerm = new StringBuffer();
		paymentTerm.append(paymentYear + " YEARS ");
		if (paymentMonth > 0) {
			paymentTerm.append(paymentMonth == 1 ? " AND" + paymentMonth + " MONTH" : paymentMonth + " MONTHS");
		}
		return paymentTerm.toString();
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		lifeSurrenderProposal.setBranch(branch);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void setClaimProduct(ClaimProduct claimProduct) {
		this.claimProduct = claimProduct;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}

	public List<ClaimProduct> getSelectclaimProductlist() {
		return selectclaimProductlist;
	}

	public void setSelectclaimProductlist(List<ClaimProduct> selectclaimProductlist) {
		this.selectclaimProductlist = selectclaimProductlist;
	}

}
