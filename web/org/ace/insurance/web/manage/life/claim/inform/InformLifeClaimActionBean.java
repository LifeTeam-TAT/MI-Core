package org.ace.insurance.web.manage.life.claim.inform;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.HospitalCase;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.DisabilityLifeClaimPartLink;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeDeathClaim;
import org.ace.insurance.life.claim.LifeHospitalizedClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.system.common.paymenttype.service.interfaces.IPaymentTypeService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "InformLifeClaimActionBean")
public class InformLifeClaimActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{PaymentTypeService}")
	private IPaymentTypeService paymentTypeService;

	public void setPaymentTypeService(IPaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	private User user;
	private LifeClaimProposal lifeClaimProposal;
	private ClaimAcceptedInfo claimAcceptedInfo;
	private User responsiblePerson;
	private String remark;
	private boolean isDisabilityClaim;
	private boolean isDeathClaim;
	private boolean isHospitalClaim;
	private DisabilityLifeClaim disabiliyClaim;
	private LifeDeathClaim lifeDeathClaim;
	private LifeHospitalizedClaim hospitalClaim;
	private boolean isPrint;
	private String medicalCase;
	private boolean isHospital;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeClaimProposal = (lifeClaimProposal == null) ? (LifeClaimProposal) getParam("lifeClaimProposal") : lifeClaimProposal;
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		checkApprove();
		claimAcceptedInfo = new ClaimAcceptedInfo();
		claimAcceptedInfo.setReferenceNo(lifeClaimProposal.getId());
		claimAcceptedInfo.setClaimAmount(lifeClaimProposal.getTotalClaimAmont());
		claimAcceptedInfo.setInformDate(new Date());
		claimAcceptedInfo.setReferenceType(ReferenceType.LIFE_CLAIM);
	}

	private void checkApprove() {
		if (lifeClaimProposal.getLifePolicyClaim() instanceof DisabilityLifeClaim) {
			isDisabilityClaim = true;
			disabiliyClaim = (DisabilityLifeClaim) lifeClaimProposal.getLifePolicyClaim();
			disabiliyClaim.setClaimStatus(ClaimStatus.PAID);
			disabiliyClaim.setPaymentType(paymentTypeService.findPaymentTypeById(KeyFactorChecker.getPaymentTypeLumpsumId()));
		} else if (lifeClaimProposal.getLifePolicyClaim() instanceof LifeDeathClaim) {
			isDeathClaim = true;
			lifeDeathClaim = (LifeDeathClaim) lifeClaimProposal.getLifePolicyClaim();
		} else {
			isHospitalClaim = true;
			hospitalClaim = (LifeHospitalizedClaim) lifeClaimProposal.getLifePolicyClaim();
		}
		isHospital = lifeClaimProposal.getHospital() != null;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeClaimProposal");
	}

	public void changePaymentStatus(AjaxBehaviorEvent event) {
		if (disabiliyClaim.getClaimStatus().equals(ClaimStatus.WAITING)) {
			double totalClaimAmt = claimAcceptedInfo.getClaimAmount() - disabiliyClaim.getDisabilityClaimAmount();
			claimAcceptedInfo.setClaimAmount(totalClaimAmt);
		}
	}

	public void informLifeClaim() {
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeClaimProposal.getId(), user.getLoginBranch().getId(), remark, WorkflowTask.CONFIRMATION, ReferenceType.LIFE_CLAIM,
					TransactionType.CLAIM, user, responsiblePerson);
			lifeClaimProposalService.informLifeClaim(claimAcceptedInfo, lifeClaimProposal, workFlowDTO);
			isPrint = true;
			addInfoMessage(null, MessageId.LIFE_ClAIM_INFORM_SUCCESS, lifeClaimProposal.getClaimProposalNo(), lifeClaimProposal.getClaimProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	public void changeMedicalCaseEvent(AjaxBehaviorEvent event) {
		if (medicalCase.equals(HospitalCase.GP.getLabel())) {
			lifeClaimProposal.setHospitalCase(HospitalCase.GP);
		} else {
			lifeClaimProposal.setHospitalCase(HospitalCase.SP);
		}
	}

	public ClaimStatus[] getClaimStatus() {
		ClaimStatus[] claimStatus = { ClaimStatus.WAITING, ClaimStatus.PAID };
		return claimStatus;
	}

	public void openTemplateDialog() {
		putParam("lifeClaimProposal", lifeClaimProposal);
		putParam("workFlowList", getWorkflowList());
		openLifeClaimInfoTemplate();
	}

	public List<WorkFlowHistory> getWorkflowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeClaimProposal.getId());
	}

	public LifeClaimProposal getLifeClaimProposal() {
		return lifeClaimProposal;
	}

	public ClaimAcceptedInfo getClaimAcceptedInfo() {
		return claimAcceptedInfo;
	}

	public void setLifeClaimProposal(LifeClaimProposal lifeClaimProposal) {
		this.lifeClaimProposal = lifeClaimProposal;
	}

	public void setClaimAcceptedInfo(ClaimAcceptedInfo claimAcceptedInfo) {
		this.claimAcceptedInfo = claimAcceptedInfo;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public void selectUser() {
		selectUser(WorkflowTask.SURVEY, WorkFlowType.LIFE);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void returnPaymentType(SelectEvent event) {
		PaymentType paymentType = (PaymentType) event.getObject();
		this.disabiliyClaim.setPaymentType(paymentType);

		double termdisabilityAmt = 0.00;
		int month = paymentType.getMonth();
		for (DisabilityLifeClaimPartLink link : disabiliyClaim.getDisabilityLifeClaimList()) {
			if (month == 0) {
				termdisabilityAmt = link.getDisabilityAmount();
				disabiliyClaim.setPaymentterm(1);
			} else {
				int term = 12 / month;
				termdisabilityAmt = link.getDisabilityAmount() / term;
				disabiliyClaim.setPaymentterm(term);
			}
			link.setTermDisabilityAmount(termdisabilityAmt);
		}
		disabiliyClaim.setPaidterm(1);
		claimAcceptedInfo.setClaimAmount(termdisabilityAmt);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsDisabilityClaim() {
		return isDisabilityClaim;
	}

	public boolean getIsDeathClaim() {
		return isDeathClaim;
	}

	public boolean getIsHospitalClaim() {
		return isHospitalClaim;
	}

	public DisabilityLifeClaim getDisabiliyClaim() {
		return disabiliyClaim;
	}

	public void setDisabiliyClaim(DisabilityLifeClaim disabiliyClaim) {
		this.disabiliyClaim = disabiliyClaim;
	}

	public boolean getIsPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

	public String getMedicalCase() {
		return medicalCase;
	}

	public void setMedicalCase(String medicalCase) {
		this.medicalCase = medicalCase;
	}

	public boolean isHospital() {
		return isHospital;
	}

}
