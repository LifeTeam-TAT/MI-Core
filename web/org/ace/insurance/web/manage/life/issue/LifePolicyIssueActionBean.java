package org.ace.insurance.web.manage.life.issue;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.ListSplitor;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.CloseEvent;

@ViewScoped
@ManagedBean(name = "LifePolicyIssueActionBean")
public class LifePolicyIssueActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private Date todaydate = new Date();
	private LifePolicyHistory lifePolicyHistory;
	private List<LifePolicy> lifePolicyList;
	private LifePolicy lifePolicy;
	private PaymentDTO paymentDTO;
	private boolean showPreview;
	private boolean disableIssueBtn;
	private boolean disableSetUPBtn;
	private String AddDispline;
	private String Reasons;
	private boolean isPublicLife;
	private boolean isGroupLife;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isShortEndowLife;
	private boolean isGovernmentShortEndowLife;
	private boolean isSnakeBite;
	private boolean isSportMan;
	private boolean isPublicTermLife;
	private boolean isSeaman;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		lifePolicyList = new ArrayList<LifePolicy>();
		disableSetUPBtn = true;
		lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
		lifePolicyList.add(lifePolicy);
		Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
		isPublicLife = KeyFactorChecker.isPublicLife(product);
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isShortEndowLife = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGovernmentShortEndowLife = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
		isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
		isSeaman = KeyFactorChecker.isSeaMenLife(product.getId());
		PolicyReferenceType referenceType = isPersonalAccident ? PolicyReferenceType.PA_POLICY
				: isFarmer ? PolicyReferenceType.FARMER_POLICY
						: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
								: isShortEndowLife ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
										:isGovernmentShortEndowLife ? PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY
										: isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
												: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
														: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																:isSeaman ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;
		List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), referenceType, true);
		paymentDTO = new PaymentDTO(paymentList);

		if (lifeProposal.getComplete()) {
			this.disableIssueBtn = true;
		}

		fileName = isPublicLife || isGroupLife ? "LifePolicyIssue.pdf"
				: isPersonalAccident ? "PersonalAccidentIssue.pdf"
						: isFarmer ? "FarmerIssue.pdf"
								: isShortEndowLife ? "ShortTermEndowmentLifeIssue.pdf"
										 :isGovernmentShortEndowLife ? "GovernmentShortEndowLifeIssue.pdf"
										: isSnakeBite ? "SnakeBiteLifeIssue.pdf" : isPublicTermLife ? "PublicTermLifeIssue.pdf" : "SportManIssue.pdf";
	}

	public void issuePolicy() {
		try {
			lifeProposalService.issueLifeProposal(lifeProposal);
			lifeProposal = lifeProposalService.findLifeProposalById(lifeProposal.getId());
			this.showPreview = true;
			this.disableIssueBtn = true;
			addInfoMessage(null, MessageId.ISSUING_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}

	}

	private String reportName = "";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private String fileName;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport(LifePolicy lifePolicy) {
		if (isPublicLife) {
			reportName = "PublicLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generatePublicLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isGroupLife) {
			reportName = "GroupLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateGroupLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isPersonalAccident) {
			reportName = "PersonalAccidentPolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generatePAPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isFarmer) {
			reportName = "FarmerPolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateFarmerPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isShortEndowLife) {
			reportName = "ShortEndowmentLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateShortTermLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isGovernmentShortEndowLife) {
			reportName = "GovernmentShortEndowLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateGovernmentShortTermLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isSnakeBite) {
			reportName = "SnakeBiteLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateSnakeBitePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isPublicTermLife) {
			reportName = "PublicTermLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generatePublicLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else if (isSeaman) {
			reportName = "SeaManPolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateSeaManPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		} else {
			reportName = "SportManLifePolicyletter";
			fileName = reportName + ".pdf";
			DocumentBuilder.generateSportManPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
		}
	}

	public void increasePrintCount() {
		lifePolicyService.increaseLifePolicyPrintCount(lifePolicy.getId());
		lifePolicy.setPrintCount(lifePolicy.getPrintCount() + 1);
	}

	public Date getPresentDate() {
		return new Date();
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public List<LifePolicy> getLifePolicyList() {
		return lifePolicyList;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public boolean getShowPreview() {
		return showPreview;
	}

	public boolean getDisableIssueBtn() {
		return disableIssueBtn;
	}

	public LifePolicyHistory getLifePolicyHistory() {
		return lifePolicyHistory;
	}

	public Date getTodaydate() {
		return todaydate;
	}

	public void setTodaydate(Date todaydate) {
		this.todaydate = todaydate;
	}

	public String getAddDispline() {
		return AddDispline;
	}

	public void setAddDispline(String addDispline) {
		AddDispline = addDispline;
	}

	public String getReasons() {
		return Reasons;
	}

	public void setReasons(String reasons) {
		Reasons = reasons;
	}

	public PaymentDTO getPayment() {
		return paymentDTO;
	}

	public boolean isLicenseNoNull() {
		if (lifeProposal.getAgent() != null) {
			return true;
		} else
			return false;
	}

	public List<List<PolicyInsuredPerson>> getPolicyInsuredList() {
		if (lifePolicy != null) {
			return ListSplitor.split(lifePolicy.getInsuredPersonInfo(), 8);
		}
		return null;
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public boolean isDisableSetUPBtn() {
		return disableSetUPBtn;
	}

	public void setDisableSetUPBtn(boolean disableSetUPBtn) {
		this.disableSetUPBtn = disableSetUPBtn;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPageHeader() {
		return (isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : isShortEndowLife ? "Short Term Endowment Life" : isPublicTermLife ? "Public Term Life" : "Life")
				+ " Policy Issue";
	}
}
