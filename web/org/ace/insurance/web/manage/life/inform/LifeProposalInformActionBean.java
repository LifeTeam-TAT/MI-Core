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
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RequestStatus;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.product.PremiumCalData;
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
@ManagedBean(name = "LifeProposalInformActionBean")
public class LifeProposalInformActionBean extends BaseBean implements Serializable {
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

	private User user;
	private LifeProposal lifeProposal;
	private boolean approvedProposal;
	private boolean isAllowedPrint;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSportMan;
	private boolean isSnakeBite;
	private boolean isShortermEndownment;
	private boolean isGovernmentShortermEndownment;
	private boolean isPublicTermLife;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isSeaman;
	private String remark;
	private User responsiblePerson;
	private AcceptedInfo acceptedInfo;
	// for reject letter
	private String reportName = "";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private String fileName = "";

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
		lifeProposal = lifeProposalService.calculatePremium(lifeProposal);
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
		isPersonalAccident = (KeyFactorChecker.isPersonalAccident(product));
		isFarmer = KeyFactorChecker.isFarmer(product);
		isSportMan = KeyFactorChecker.isSportMan(product);
		isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
		isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
		isGovernmentShortermEndownment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
		isSeaman = KeyFactorChecker.isSeaMenLife(product.getId());
		isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
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
		} else if (isShortermEndownment) {
			acceptedInfo.setReferenceType(ReferenceType.SHORT_ENDOWMENT_LIFE);
		} else if (isGovernmentShortermEndownment) {
			acceptedInfo.setReferenceType(ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE);
		} else if (isSeaman) {
			acceptedInfo.setReferenceType(ReferenceType.SEAMEN_LIFE);
		} else if (isGroupLife) {
			acceptedInfo.setReferenceType(ReferenceType.GROUP_LIFE);
		} else if (isEndownmentLife) {
			acceptedInfo.setReferenceType(ReferenceType.ENDOWMENT_LIFE);
		} else if (isSportMan) {
			acceptedInfo.setReferenceType(ReferenceType.SPORT_MAN);
		} else if (isPublicTermLife) {
			acceptedInfo.setReferenceType(ReferenceType.PUBLIC_TERM_LIFE);
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
		calculateStampFees();
		if (isFinished()) {
			isAllowedPrint = true;
		}
	}

	private void calculateStampFees() {
		double stamFees = premiumCalculatorService.calculateStampFee(lifeProposal.getProposalInsuredPersonList().get(0).getProduct(),
				new PremiumCalData(null, lifeProposal.getTotalSumInsured(), null, lifeProposal.getTotalUntis()));
		acceptedInfo.setStampFeesAmount(stamFees);
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
				: isFarmer ? WorkFlowType.FARMER
						: isShortermEndownment ? WorkFlowType.SHORT_ENDOWMENT
								:isGovernmentShortermEndownment ? WorkFlowType.GOVERNMENT_SHORT_ENDOWMENT
										: isSnakeBite ? WorkFlowType.SNAKE_BITE 
												: isPublicTermLife ? WorkFlowType.PUBLIC_TERM_LIFE 
														:isSeaman ? WorkFlowType.SEAMEN : WorkFlowType.LIFE;
		selectUser(workflowTask, workFlowType, TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}

	public AcceptedInfo getAcceptedInfo() {
		return acceptedInfo;
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
		String result = "";
		try {
			WorkflowTask workflowTask = WorkflowTask.CONFIRMATION;
			ReferenceType referenceType = isPersonalAccident ? ReferenceType.PA
					: isFarmer ? ReferenceType.FARMER
							: isSnakeBite ? ReferenceType.SNAKE_BITE
									: isShortermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											:isGovernmentShortermEndownment ? ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE
													: isGroupLife ? ReferenceType.GROUP_LIFE
															: isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE 
																	: isPublicTermLife ? ReferenceType.PUBLIC_TERM_LIFE 
																			:isSeaman ? ReferenceType.SEAMEN_LIFE : ReferenceType.SPORT_MAN;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeProposal.getId(), lifeProposal.getBranch().getId(), remark, workflowTask, referenceType, TransactionType.UNDERWRITING,
					user, responsiblePerson);
			lifeProposalService.informProposal(lifeProposal, workFlowDTO, acceptedInfo, RequestStatus.APPROVED.name());
			addInfoMessage(null, MessageId.INFORM_PROCESS_SUCCESS_PARAM, lifeProposal.getProposalNo());
			if (isSnakeBite) {
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.INFORM_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeProposal.getProposalNo());
			}
			isAllowedPrint = true;
			result = "";
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
									: isShortermEndownment ? ReferenceType.SHORT_ENDOWMENT_LIFE
											:isGovernmentShortermEndownment ? ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE
													: isGroupLife ? ReferenceType.GROUP_LIFE
															: isEndownmentLife ? ReferenceType.ENDOWMENT_LIFE 
																	: isPublicTermLife ? ReferenceType.PUBLIC_TERM_LIFE 
																			:isSeaman ? ReferenceType.SEAMEN_LIFE : ReferenceType.SPORT_MAN;
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
				reportName = "PersonalAccidentacceptanceletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generatePersonalAccidentAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isFarmer) {
				reportName = "Farmeracceptanceletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generateFarmerAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isSportMan) {
				reportName = "SportManacceptanceletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generateSportManAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isSnakeBite) {
				reportName = "SnakeBiteacceptanceletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generateSnakeBikeAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isSeaman) {
				reportName = "SeaMenacceptanceletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generateSeaMenAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			}else {
				if (isEndownmentLife) {
					reportName = "Endownmentlifeacceptanceletter";
					fileName = reportName + ".pdf";
				} else if (isGroupLife) {
					reportName = "Grouplifeacceptanceletter";
					fileName = reportName + ".pdf";
				} else if (isPublicTermLife) {
					reportName = "PublicTermLifeacceptanceletter";
					fileName = reportName + ".pdf";
				} else if (isGovernmentShortermEndownment) {
					reportName = "GovernmentShortermEndownmentLifeacceptanceletter";
					fileName = reportName + ".pdf";
				}   else {
					reportName = "ShortermEndownmentlifeacceptanceletter";
					fileName = reportName + ".pdf";
				}
				DocumentBuilder.generateLifeAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			}
		} else {
			if (isPersonalAccident) {
				reportName = "PersonalAccidentRejectletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generatePARejectLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else if (isFarmer) {
				reportName = "FarmerRejecteletter";
				fileName = reportName + ".pdf";
				DocumentBuilder.generateFarmerRejectLetter(lifeProposal, acceptedInfo, dirPath, fileName);
			} else {
				if (isEndownmentLife) {
					reportName = "EndownmentlifeRejectletter";
					fileName = reportName + ".pdf";
				} else if (isGroupLife) {
					reportName = "GrouplifeRejectletter";
					fileName = reportName + ".pdf";
				} else if (isPublicTermLife) {
					reportName = "PublicTermRejectletter";
					fileName = reportName + ".pdf";
				} else if (isGovernmentShortermEndownment) {
					reportName = "GovernmentShortermEndownmentlifeRejectletter";
					fileName = reportName + ".pdf";
				} else {
					reportName = "ShortermEndownmentlifeRejectletter";
					fileName = reportName + ".pdf";
				}
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
		return (isFarmer ? "Farmer" : isPersonalAccident ? "Personal Accident" : "Life") + " Proposal Inform";
	}
}
