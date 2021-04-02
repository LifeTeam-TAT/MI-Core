package org.ace.insurance.web.manage.life.inform;

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
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IPremiumCalculatorService;
import org.ace.insurance.product.service.interfaces.IProductService;
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
@ManagedBean(name = "EndorsementLifeProposalInformActionBean")
public class EndorsementLifeProposalInformActionBean extends BaseBean implements Serializable {
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

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	@ManagedProperty(value = "#{PremiumCalculatorService}")
	private IPremiumCalculatorService premiumCalculatorService;

	public void setPremiumCalculatorService(IPremiumCalculatorService premiumCalculatorService) {
		this.premiumCalculatorService = premiumCalculatorService;
	}

	@ManagedProperty(value = "#{LifeEndorsementService}")
	private ILifeEndorsementService lifeEndorsementService;

	public void setLifeEndorsementService(ILifeEndorsementService lifeEndorsementService) {
		this.lifeEndorsementService = lifeEndorsementService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private User user;
	private LifeProposal lifeProposal;
	private boolean approvedProposal;
	private boolean isAllowedPrint;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSportMan;
	private boolean isSnakeBite;
	private boolean isShortTermEndownment;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private String remark;
	private User responsiblePerson;
	private AcceptedInfo acceptedInfo;
	private boolean isEndorse;
	// for reject letter
	private final String reportName = "acceptanceLetter";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeProposal = (lifeProposal == null) ? (LifeProposal) getParam("lifeProposal") : lifeProposal;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeProposal");
	}

	private boolean isFinished() {
		if (lifeProposal == null) {
			return true;
		} else {
			if (workFlowService.findWorkFlowByRefNo(lifeProposal.getId(), WorkflowTask.INFORM) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		// lifeProposal = lifeProposalService.calculatePremium(lifeProposal);
		isEndorse = lifeProposal.getProposalType().equals(ProposalType.ENDORSEMENT);
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		isPersonalAccident = (KeyFactorChecker.isPersonalAccident(product));
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortTermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGroupLife = KeyFactorChecker.isGroupLife(product);
		isEndownmentLife = KeyFactorChecker.isPublicLife(product);
		acceptedInfo = new AcceptedInfo();
		acceptedInfo.setReferenceNo(lifeProposal.getId());
		if (isPersonalAccident) {
			acceptedInfo.setReferenceType(ReferenceType.PA);
		} else if (isFarmer) {
			acceptedInfo.setReferenceType(ReferenceType.FARMER);
		} else if (isSnakeBite) {
			acceptedInfo.setReferenceType(ReferenceType.SNAKE_BITE);
		} else if (isShortTermEndownment) {
			acceptedInfo.setReferenceType(ReferenceType.SHORT_ENDOWMENT_LIFE);
		} else if (isGroupLife) {
			acceptedInfo.setReferenceType(ReferenceType.GROUP_LIFE);
		} else if (isEndownmentLife) {
			acceptedInfo.setReferenceType(ReferenceType.ENDOWMENT_LIFE);
		} else if (isSportMan) {
			acceptedInfo.setReferenceType(ReferenceType.SPORT_MAN);
		}
		acceptedInfo.setBasicPremium(lifeProposal.getProposedPremium());
		acceptedInfo.setAddOnPremium(lifeProposal.getAddOnPremium());
		acceptedInfo.setBasicTermPremium(lifeProposal.getTotalBasicTermPremium());
		acceptedInfo.setAddOnTermPremium(lifeProposal.getTotalAddOnTermPremium());
		acceptedInfo.setPaymentType(lifeProposal.getPaymentType());
		acceptedInfo.setEndorsementNetPremium(lifeProposal.getEndorsementNetPremium());
		acceptedInfo.setEndorsementAddOnPremium(lifeProposal.getEndorsementAddOnPremium());
		approvedProposal = true;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			if (!pv.isApproved()) {
				approvedProposal = false;
				break;
			}
		}
		if (isFinished()) {
			isAllowedPrint = true;
		}
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

	public void selectUser() {
		WorkflowTask workflowTask = WorkflowTask.CONFIRMATION;
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isFarmer ? WorkFlowType.FARMER : isShortTermEndownment ? WorkFlowType.SHORT_ENDOWMENT : isSnakeBite ? WorkFlowType.SNAKE_BITE : WorkFlowType.LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.ENDORSEMENT, user.getLoginBranch().getId(), null);
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

	public boolean getIsAllowedPrint() {
		return isAllowedPrint;
	}

	public String informApprovedLifeProposal() {
		String result = null;
		try {
			WorkflowTask workflowTask = WorkflowTask.CONFIRMATION;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = null;
			workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.ENDORSEMENT, user,
					responsiblePerson);
			lifeEndorsementService.informLifeEndorse(lifeProposal, workFlowDTO, acceptedInfo, RequestStatus.APPROVED.name());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INFORM_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			isAllowedPrint = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public void informRejectLifeProposal() {
		try {
			WorkflowTask workflowTask = WorkflowTask.CONFIRMATION;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortTermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											: isGroupLife ? ReferenceType.GROUP_LIFE : isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING,
					user, responsiblePerson);
			lifeProposalService.informProposal(lifeProposal, workFlowDTO, null, RequestStatus.REJECTED.name());
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INFORM_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			isAllowedPrint = true;
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void openTemplateDialog() {
		putParam("lifeProposalDetail", lifeProposal);
		putParam("workFlowList", getWorkFlowList());
		openLifeProposalInfoTemplate();
	}

	public Date getCurrentDate() {
		return new Date();
	}

	public boolean isVisibleEndorseNetPremium() {
		if (lifeProposal.getLifePolicy() != null && lifeProposal.getEndorsementNetPremium() > 0) {
			return true;
		}
		return false;
	}

	public String getStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		if (approvedProposal) {
			if (isPersonalAccident) {
				DocumentBuilder.generatePersonalAccidentAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isFarmer) {
				DocumentBuilder.generateFarmerAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isSportMan) {
				DocumentBuilder.generateSportManAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isSnakeBite) {
				DocumentBuilder.generateSnakeBikeAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else {
				DocumentBuilder.generateLifeAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			}
		} else {
			if (isPersonalAccident) {
				DocumentBuilder.generatePARejectLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isFarmer) {
				DocumentBuilder.generateFarmerRejectLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else {
				DocumentBuilder.generateLifeRejectLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			}
		}
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPageHeader() {
		return (isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : "Life") + " Endorsement Inform";
	}

	public boolean isEndorse() {
		return isEndorse;
	}

	public AcceptedInfo getAcceptedInfo() {
		return acceptedInfo;
	}

	public void setAcceptedInfo(AcceptedInfo acceptedInfo) {
		this.acceptedInfo = acceptedInfo;
	}

}
