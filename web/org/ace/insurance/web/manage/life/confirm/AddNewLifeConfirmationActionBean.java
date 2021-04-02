
package org.ace.insurance.web.manage.life.confirm;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.utils.DateUtils;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.lifePolicySummary.LifePolicySummary;
import org.ace.insurance.life.lifePolicySummary.Service.Interfaces.ILifePolicySummaryService;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.bmiChart.service.interfaces.IBMIService;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
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
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "AddNewLifeConfirmationActionBean")
public class AddNewLifeConfirmationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	@ManagedProperty(value = "#{BMIService}")
	private IBMIService bmiService;

	public void setBmiService(IBMIService bmiService) {
		this.bmiService = bmiService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private WorkFlowDTO workFlowDTO;
	private PaymentDTO paymentDTO;
	private boolean approvedProposal;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSnakeBite;
	private boolean isShortTermEndownment;
	private boolean isGovernmentShortTermEndownment;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isPublicTermLife;
	private boolean isSportMan;
	private boolean isSeaman;
	private String remark;
	private String fileName;
	private User responsiblePerson;
	private boolean sportManSI;
	private boolean isUserDefinedDate;
	private boolean showPrintPreview;
	private Date effectiveDate;
	private ReferenceType referenceType;
	private LifeEndorseInfo lifeEndorseInfo;
	private boolean isStudentLife;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
		workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO) getParam("workFlowDTO") : workFlowDTO;
	}

	@PreDestroy
	public void destroy() {
		removeParam("workFlowDTO");
	}

	private boolean isFinished() {
		if (lifeProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(lifeProposal.getId(), WorkflowTask.CONFIRMATION) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		InitializeProduct(product);
		loadReferenceType();
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			if (!pv.isApproved()) {
				approvedProposal = false;
				break;
			}
		}
		lifeProposal = lifeProposalService.calculatePremium(lifeProposal);

		AcceptedInfo acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(lifeProposal.getId());
		isUserDefinedDate = isEndownmentLife || isShortTermEndownment || isGovernmentShortTermEndownment || isSportMan || isPublicTermLife ? true : false;
		if (isUserDefinedDate) {
			effectiveDate = lifeProposal.getStartDate();
		}
		paymentDTO = new PaymentDTO();
		paymentDTO.setBasicPremium(lifeProposal.getTotalTermPremium());
		paymentDTO.setAddOnPremium(lifeProposal.getTotalAddOnTermPremium());
		if (acceptedInfo != null) {
			paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
			paymentDTO.setStampFees(acceptedInfo.getStampFeesAmount());
			paymentDTO.setDiscountPercent(acceptedInfo.getDiscountPercent());
			paymentDTO.setPaymentType(acceptedInfo.getPaymentType());
		}
		paymentDTO.setDiscountPercent(lifeProposal.getSpecialDiscount());
		fileName = isPersonalAccident ? "PersonalAccidentPolicyInvoice.pdf"
				: isFarmer ? "FarmerPolicyInvoice.pdf" : isSnakeBite ? "SnakeBitePolicyInvoice.pdf" : isSportMan ? "SportManPolicyInvoice.pdf" : "LifePolicyInvoice.pdf";
		if (isFinished()) {
			showPrintPreview = true;
		}
	}

	public void InitializeProduct(Product product) {
		isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGovernmentShortTermEndownment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
		isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
		isSportMan = KeyFactorChecker.isSportMan(product);
		isSeaman = KeyFactorChecker.isSeaMenLife(product.getId());
		approvedProposal = true;
		showPrintPreview = false;
	}

	public boolean isApprovedProposal() {
		return approvedProposal;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	private void loadReferenceType() {
		referenceType = isPersonalAccident ? ReferenceType.PA
				: isFarmer ? ReferenceType.FARMER
						: isSnakeBite ? ReferenceType.SNAKE_BITE
								: isShortTermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
										:isGovernmentShortTermEndownment ? ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE
												: isGroupLife ? ReferenceType.GROUP_LIFE
														: isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE 
																: isPublicTermLife ? ReferenceType.PUBLIC_TERM_LIFE 
																		:isSeaman ? ReferenceType.SEAMEN_LIFE : ReferenceType.SPORT_MAN;
	}

	public void addNewLifeProposalReceiptInfo() {
		try {
			WorkflowTask workflowTask = WorkflowTask.PAYMENT;
			List<Payment> paymentList = null;
			lifeProposal.setStartDate(effectiveDate);
			if (effectiveDate == null) {
				lifeProposal.setEndDate(null);
			}
			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			paymentList = lifeProposalService.confirmLifeProposal(lifeProposal, workFlowDTO, paymentDTO, user.getBranch(), null);
			paymentDTO = new PaymentDTO(paymentList);
			showPrintPreview = true;
			addInfoMessage(null, MessageId.CONFIRMATION_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public String editLifeProposal() {
		putParam("InsuranceType", InsuranceType.LIFE);
		putParam("lifeProposal", lifeProposal);
		return "lifeProposal";
	}

	public String denyLifeProposal() {
		String result = null;
		try {
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, WorkflowTask.REJECT, referenceType,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			workFlowDTO.setTransactionType(TransactionType.UNDERWRITING);
			lifeProposalService.rejectLifeProposal(lifeProposal, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.DENY_PROCESS_OK);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void selectUser() {
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isFarmer ? WorkFlowType.FARMER
						: isShortTermEndownment ? WorkFlowType.SHORT_ENDOWMENT
								:isGovernmentShortTermEndownment ? WorkFlowType.GOVERNMENT_SHORT_ENDOWMENT
										: isSnakeBite ? WorkFlowType.SNAKE_BITE 
												: isPublicTermLife ? WorkFlowType.PUBLIC_TERM_LIFE 
														:isSeaman ? WorkFlowType.SEAMEN : WorkFlowType.LIFE;
		selectUser(WorkflowTask.PAYMENT, workFlowType, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	/**
	 * 
	 * This method is used to retrieve the available SI amount for an insured
	 * person.
	 * 
	 * @param insuredPersonInfo
	 * @return double
	 *//*
		 * public double getAvailableSISportMan() { double availableSI = 0.0;
		 * double maxSi =
		 * lifeProposal.getProposalInsuredPersonList().get(0).getProduct().
		 * getMaxValue(); availableSI = maxSi - (getTotalSISportMan());
		 * 
		 * return availableSI; }
		 */
	/**
	 * 
	 */
	/*
	
	*//**
		 * 
		 * This method is used to retrieve the total SI amount of an insured
		 * person's active policies.
		 * 
		 * @param insuredPersonInfo
		 * @return double
		 * 
		 */
	/*
	 * public double getTotalSISportMan() { double sumInsured = 0.0; Date
	 * endDate; Map<Date, Double> endDateMap = new HashMap<Date, Double>();
	 * 
	 * if (sportManInsuredPeople != null && !sportManInsuredPeople.isEmpty()) {
	 * for (PolicyInsuredPerson p : sportManInsuredPeople) { if
	 * (p.getLifePolicy() != null) { endDate =
	 * p.getLifePolicy().getActivedPolicyEndDate();
	 * 
	 * if (endDate != null && endDate.after(new Date()) &&
	 * KeyFactorChecker.isSportMan(p.getProduct())) { sumInsured +=
	 * p.getSumInsured(); endDateMap.put(endDate, p.getSumInsured()); } }
	 * 
	 * } }
	 * 
	 * int count = 0; if(!endDateMap.isEmpty()) { for (Date i :
	 * endDateMap.keySet()) { if (count == 0) { minEndDate = i; availableSI =
	 * endDateMap.get(i); }
	 * 
	 * if (minEndDate.after(i)) { minEndDate = i; availableSI =
	 * endDateMap.get(i); } count = 1; } }else { minEndDate = new Date(); }
	 * 
	 * return sumInsured; }
	 * 
	 *//**
		 * 
		 * This method is used to decide whether an sport man insuredPerson's
		 * sumInsured amount is over sport man product's maximum SI or not in
		 * all of his/her active policies.
		 * 
		 * @param insuredPersonInfo
		 * @return boolean true if SI is over.
		 */

	/*
	 * public boolean isExcessSISportMan() { double sumInsured =
	 * getTotalSISportMan() +
	 * lifeProposal.getProposalInsuredPersonList().get(0).getProposedSumInsured(
	 * ); boolean flag = false; if (sumInsured >
	 * lifeProposal.getProposalInsuredPersonList().get(0).getProduct().
	 * getMaxValue()) { flag = true; }
	 * 
	 * // return sumInsured > insuredPersonInfo.getProduct().getMaxSumInsured()
	 * // ? true: false; return flag; }
	 * 
	 *//**
		 * 
		 * This method is used to give message to user when user's proposed sum
		 * insured is not valid in sport man.
		 * 
		 * @return String
		 *//*
			 * public String getSportManMessage() { Double available =
			 * availableSI + getAvailableSISportMan(); DateFormat df = new
			 * SimpleDateFormat("dd-MMM-yyyy"); return
			 * "You will have to wait till " + df.format(minEndDate) +
			 * " to allow the sum insured amount at " + available + "."; }
			 */

	public boolean isSportManSI() {
		return sportManSI;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PaymentDTO getPaymentDTO() {
		return paymentDTO;
	}

	public void setPaymentDTO(PaymentDTO paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isUserDefinedDate() {
		return isUserDefinedDate;
	}

	public void setUserDefinedDate(boolean isUserDefinedDate) {
		this.isUserDefinedDate = isUserDefinedDate;
	}

	public boolean getIsShowPrintPreview() {
		return showPrintPreview;
	}

	public void onDateSelect(SelectEvent event) {
		if (!DateUtils.resetStartDate(effectiveDate).equals(DateUtils.resetStartDate(lifeProposal.getStartDate()))) {
			int age;
			Date startDate = lifeProposal.getStartDate();
			for (ProposalInsuredPerson insuredPerson : lifeProposal.getProposalInsuredPersonList()) {
				Date dateOfBirth = insuredPerson.getCustomer().getDateOfBirth();
				age = Utils.getAgeForNextYear(dateOfBirth, startDate);
				insuredPerson.setAge(age);
				for (InsuredPersonKeyFactorValue vehKF : insuredPerson.getKeyFactorValueList()) {
					KeyFactor kf = vehKF.getKeyFactor();
					if (KeyFactorChecker.isAge(kf) || KeyFactorChecker.isMedicalAge(kf)) {
						vehKF.setValue(age + "");
					}
				}
			}
			lifeProposalService.calculatePremium(lifeProposal);
			lifeProposalService.calculateTermPremium(lifeProposal);
			paymentDTO.setBasicPremium(lifeProposal.getTotalTermPremium());
			paymentDTO.setAddOnPremium(lifeProposal.getTotalAddOnTermPremium());
		}
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	/* For Template */
	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{LifePolicyHistoryService}")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	public void setLifePolicyHistoryService(ILifePolicyHistoryService lifePolicyHistoryService) {
		this.lifePolicyHistoryService = lifePolicyHistoryService;
	}

	@ManagedProperty(value = "#{LifePolicySummaryService}")
	private ILifePolicySummaryService lifePolicySummaryService;

	public void setLifePolicySummaryService(ILifePolicySummaryService lifePolicySummaryService) {
		this.lifePolicySummaryService = lifePolicySummaryService;
	}

	private List<LifePolicyHistory> lifePolicyHistoryList;

	public List<LifePolicyHistory> getLifePolicyHistoryList() {
		if (lifePolicyHistoryList == null) {
			if (lifeProposal.getLifePolicy() != null) {
				lifePolicyHistoryList = lifePolicyHistoryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getPolicyNo());
			}
		}
		return lifePolicyHistoryList;
	}

	public LifePolicySummary getLifePolicySummary() {
		return lifePolicySummaryService.findLifePolicyByPolicyNo(lifeProposal.getLifePolicy().getId());
	}

	/** Generate Report */
	private String reportName = "LifePaymentInvoice";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		if (isShortTermEndownment || isGovernmentShortTermEndownment || isEndownmentLife || isStudentLife || isPublicTermLife) {
			reportName = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductContent().getName();
			DocumentBuilder.generateLifeInvoice(lifeProposal, paymentDTO, dirPath, fileName);
		} else {
			DocumentBuilder.generateLifePaymentInvoice(lifeProposal, paymentDTO, dirPath, fileName);
		}
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPageHeader() {
		return (isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : "Life") + " Proposal Confirmation";
	}

	public boolean isShortTermEndownment() {
		return isShortTermEndownment;
	}

	public boolean isGovernmentShortTermEndownment() {
		return isGovernmentShortTermEndownment;
	}
	
	public boolean isEndownmentLife() {
		return isEndownmentLife;
	}

	public boolean isSeaman() {
		return isSeaman;
	}

	public void setSeaman(boolean isSeaman) {
		this.isSeaman = isSeaman;
	}
	
}
