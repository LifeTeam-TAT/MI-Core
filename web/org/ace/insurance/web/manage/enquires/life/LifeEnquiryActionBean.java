package org.ace.insurance.web.manage.enquires.life;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.endorsement.service.interfaces.ILifeEndorsementService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
import org.ace.insurance.life.proposal.LPL001;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.product.Product;
import org.ace.insurance.product.service.interfaces.IProductService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.common.document.DocumentBuilder;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "LifeEnquiryActionBean")
public class LifeEnquiryActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
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

	@ManagedProperty(value = "#{AcceptedInfoService}")
	private IAcceptedInfoService acceptedInfoService;

	public void setAcceptedInfoService(IAcceptedInfoService acceptedInfoService) {
		this.acceptedInfoService = acceptedInfoService;
	}

	@ManagedProperty(value = "#{ProductService}")
	private IProductService productService;

	public void setProductService(IProductService productService) {
		this.productService = productService;
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

	@ManagedProperty(value = "#{LifePolicyHistoryService}")
	private ILifePolicyHistoryService lifePolicyHistoryService;

	public void setLifePolicyHistoryService(ILifePolicyHistoryService lifePolicyHistoryService) {
		this.lifePolicyHistoryService = lifePolicyHistoryService;
	}

	private LifeProposal lifeProposal;
	private User user;
	private List<LPL001> lifeProposalList;
	private List<Product> productList;
	private WorkFlow workFlow;
	private EnquiryCriteria criteria;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		resetCriteria();
		loadProductList();
		criteria.setBranch(user.getLoginBranch());
	}

	public void loadProductList() {
		productList = productService.findProductByInsuranceType(InsuranceType.LIFE);
		String farmerId = KeyFactorIDConfig.getFarmerId();
		for (Iterator<Product> iterator = productList.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			if (product.getId().equals(farmerId)) {
				iterator.remove();
			}
		}
	}

	public List<LPL001> getLifeProposalList() {
		RegNoSorter<LPL001> regNoSorter = new RegNoSorter<LPL001>(lifeProposalList);
		return regNoSorter.getSortedList();
	}

	public LifeProposal getLifeProposal() {
		return lifeProposal;
	}

	public void setLifeProposal(LifeProposal lifeProposal) {
		this.lifeProposal = lifeProposal;
	}

	public void showDetailLifeProposal(LPL001 lifeProposalDTO) {
		this.lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
		if (KeyFactorChecker.isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId())) {
			putParam("lifeProposal", lifeProposal);
			putParam("workFlowList", getWorkFlowList());
			openStudentLifeProposalInfoTemplate();
		} else {
			putParam("lifeProposalDetail", lifeProposal);
			putParam("workFlowList", getWorkFlowList());
			openLifeProposalInfoTemplate();
		}
	}

	public WorkFlow getWorkFlow() {
		return workFlow;
	}

	public void setWorkFlow(WorkFlow workFlow) {
		this.workFlow = workFlow;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId());
	}

	public List<Product> getProductList() {
		return productList;
	}

	public EnquiryCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(EnquiryCriteria criteria) {
		this.criteria = criteria;
	}

	public void findLifeProposalListByEnquiryCriteria() {
		criteria.setInsuranceType(InsuranceType.LIFE);
		lifeProposalList = lifeProposalService.findLifeProposalByEnquiryCriteria(criteria, productList);
	}

	public void resetCriteria() {
		criteria = new EnquiryCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setBranch(user.getBranch());
		criteria.setEndDate(endDate);
		criteria.setPolicyNo("");
		criteria.setProposalType(ProposalType.UNDERWRITING);
		lifeProposalList = new ArrayList<LPL001>();
	}

	public String editLifeProposal(LPL001 lifeProposalDTO) {
		if (allowToEdit(lifeProposalDTO.getProposalId())) {
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			if (KeyFactorChecker.isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId())) {
				putParam("lifeProposal", lifeProposal);
				putParam("enquiryEditLifeProposal", true);
				return "addNewStudentLifeProposal";
			} else {
				putParam("InsuranceType", InsuranceType.LIFE);
				putParam("lifeProposal", lifeProposal);
				putParam("isEnquiryEdit", true);
				if (ProposalType.RENEWAL.equals(lifeProposal.getProposalType())) {
					return "editRenewalGroupLifeProposal";
				} else {
					putParam("proposalType", ProposalType.UNDERWRITING);
					return "editLifeProposal";
				}
			}
		} else {
			return null;
		}
	}

	public String addAttachment(LPL001 lifeProposalDTO) {
		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
		putParam("InsuranceType", InsuranceType.LIFE);
		putParam("lifeProposal", lifeProposal);
		return "addAttachmentLifeProposal";
	}

	private String message;

	public String getMessage() {
		return message;
	}

	private LifePolicy lifePolicy;
	private PaymentDTO paymentDTO;
	private boolean isShortermEndownment;
	private boolean isFarmer;
	private boolean isSportMan;
	private boolean isSnakeBite;
	private boolean isGroupLife;
	private boolean isEndownmentLife;
	private boolean isPersonalAccident;
	private boolean isStudentLife;
	private boolean isPublicTermLife;
	private boolean isSeamanLife;
	private boolean isGovernmentShortTermEndowment;

	public String printIssuePolicy(LPL001 lifeProposalDTO) {
		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
		if (lifeProposal.getComplete() && allowToPrint(lifeProposal, WorkflowTask.ISSUING)) {
			lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
			isSeamanLife = KeyFactorChecker.isSeaMenLife(product.getId());
			isGovernmentShortTermEndowment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_POLICY
																	: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																			:isSportMan ?  PolicyReferenceType.SPORT_MAN_POLICY
																					:isSeamanLife ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY;;
			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, true);
			paymentDTO = new PaymentDTO(paymentList);
			if (isEndownmentLife || isPublicTermLife) {
				DocumentBuilder.generatePublicLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isGroupLife) {
				DocumentBuilder.generateGroupLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isSportMan) {
				DocumentBuilder.generateSportManPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isSeamanLife) {
				DocumentBuilder.generateSeaManPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isFarmer) {
				DocumentBuilder.generateFarmerPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isSnakeBite) {
				DocumentBuilder.generateSnakeBitePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isShortermEndownment) {
				DocumentBuilder.generateShortTermLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isGovernmentShortTermEndowment) {
				DocumentBuilder.generateGovernmentShortTermLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isPersonalAccident) {
				DocumentBuilder.generatePAPolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			} else if (isStudentLife) {
				DocumentBuilder.generateStudentLifePolicyLetter(lifePolicy, paymentDTO, dirPath, fileName);
			}
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.ISSUING.getLabel().toLowerCase());
			showInformationDialog();
		}
		return null;
	}

	public String printEndorseIssuePolicy(LPL001 lifeProposalDTO) {
		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
		if (lifeProposal.getComplete() && allowToPrint(lifeProposal, WorkflowTask.ISSUING)) {
			lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY : PolicyReferenceType.SPORT_MAN_POLICY;
			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, true);
			paymentDTO = new PaymentDTO(paymentList);
			LifeEndorseInfo endorseInfo = lifeEndorsementService.findByEndorsePolicyReferenceNo(lifePolicy.getId());
			LifePolicyHistory policyHistory = lifePolicyHistoryService.findByPolicyReferenceNo(endorseInfo.getSourcePolicyReferenceNo());
			if (isEndownmentLife) {
				// DocumentBuilder.generateEndorseChangesLetters(lifePolicy,
				// endorseInfo, dirPath, fileName);
				// DocumentBuilder.generatePublicLifePolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			} else if (isGroupLife) {
				DocumentBuilder.generateEndorseChangesLetters(lifePolicy, policyHistory, endorseInfo, dirPath, fileName);
			} else if (isSportMan) {
				// DocumentBuilder.generateSportManPolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			} else if (isFarmer) {
				// DocumentBuilder.generateFarmerPolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			} else if (isSnakeBite) {
				// DocumentBuilder.generateSnakeBitePolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			} else if (isShortermEndownment) {
				// DocumentBuilder.generateShortTermLifePolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			} else if (isPersonalAccident) {
				// DocumentBuilder.generatePAPolicyLetter(lifePolicy,
				// paymentDTO, dirPath, fileName);
			}
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.ISSUING.getLabel().toLowerCase());
			showInformationDialog();
		}
		return null;
	}

	public String printAcceptedLetter(LPL001 lifeProposalDTO) {
		if (allowToPrint(lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId()), WorkflowTask.CONFIRMATION, WorkflowTask.PAYMENT, WorkflowTask.ISSUING)) {
			AcceptedInfo acceptedInfo = null;
			boolean isApprovedProposal = true;
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
				if (!pv.isApproved()) {
					isApprovedProposal = false;
					break;
				}
			}
			isSportMan = KeyFactorChecker.isSportMan(lifeProposal.getProposalInsuredPersonList().get(0).getProduct());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId());
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(lifeProposal.getProposalInsuredPersonList().get(0).getProduct());
			isSeamanLife = KeyFactorChecker.isSeaMenLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId());
			acceptedInfo = acceptedInfoService.findAcceptedInfoByReferenceNo(lifeProposal.getId());
			if (isApprovedProposal) {
				if (isFarmer) {
					DocumentBuilder.generateFarmerAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
				} else if (isSportMan) {
					DocumentBuilder.generateSportManAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
				} else if (isSnakeBite) {
					DocumentBuilder.generateSnakeBikeAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
				} else if (isPersonalAccident) {
					DocumentBuilder.generatePersonalAccidentAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
				} else if (isSeamanLife) {
					DocumentBuilder.generateSeaMenAcceptanceLetter(lifeProposal, acceptedInfo, dirPath, fileName);
				}else {
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
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.INFORM.getLabel().toLowerCase());
			showInformationDialog();
		}
		return null;
	}

	public String printPaymentInvoiceLetter(LPL001 lifeProposalDTO) {
		if (allowToPrint(lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId()), WorkflowTask.ISSUING, WorkflowTask.PAYMENT)) {
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			isPublicTermLife = KeyFactorChecker.isPublicTermLife(product.getId());
			isSeamanLife = KeyFactorChecker.isSeaMenLife(product.getId());
			isGovernmentShortTermEndowment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_POLICY
																	: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																		:isSportMan ? PolicyReferenceType.SPORT_MAN_POLICY
																			:isSeamanLife ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY;

			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, null);
			PaymentDTO paymentDTO = new PaymentDTO(paymentList);
			String dirPath = getWebRootPath() + pdfDirPath;
			if (isShortermEndownment || isEndownmentLife || isStudentLife || isPublicTermLife || isGovernmentShortTermEndowment) {
				DocumentBuilder.generateLifeInvoice(lifeProposal, paymentDTO, dirPath, fileName);
			} else {
				DocumentBuilder.generateLifePaymentInvoice(lifeProposal, paymentDTO, dirPath, fileName);
			}
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.CONFIRMATION.getLabel().toLowerCase());
			showInformationDialog();
		}
		return null;
	}

	public String printEndorsePaymentInvoiceLetter(LPL001 lifeProposalDTO) {
		if (allowToPrint(lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId()), WorkflowTask.ISSUING, WorkflowTask.PAYMENT)) {
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY : PolicyReferenceType.SPORT_MAN_POLICY;

			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, null);
			PaymentDTO paymentDTO = new PaymentDTO(paymentList);
			String dirPath = getWebRootPath() + pdfDirPath;
			if (isShortermEndownment || isEndownmentLife) {
				DocumentBuilder.generateLifeInvoice(lifeProposal, paymentDTO, dirPath, fileName);
			} else {
				DocumentBuilder.generateLifePaymentInvoice(lifeProposal, paymentDTO, dirPath, fileName);
			}
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.CONFIRMATION.getLabel().toLowerCase());
			showInformationDialog();
		}
		return null;
	}

	private void showPdfDialog() {
		PrimeFaces.current().executeScript("PF('pdfPreviewDialog').show();");
	}

	public void outjectLifeProposal(LifeProposal lifeProposal) {
		putParam("lifeProposal", lifeProposal);
	}

	public ProposalType[] getProposalTypes() {
		ProposalType[] types = { ProposalType.UNDERWRITING, ProposalType.ENDORSEMENT, ProposalType.RENEWAL };
		return types;
	}

	private boolean allowToPrint(LifeProposal lifeProposal, WorkflowTask... workflowTasks) {
		List<WorkFlowHistory> wfhList = workFlowService.findWorkFlowHistoryByRefNo(lifeProposal.getId(), workflowTasks);
		if (wfhList == null || wfhList.isEmpty()) {
			return false;
		} else {
			this.lifeProposal = lifeProposal;
			return true;
		}
	}

	long currentTime = System.currentTimeMillis();

	/********************************************
	 * PDF Document Generation
	 ***************************************/
	String reportName = "LifePolicyLedger";
	String pdfDirPath = "/pdf-report/" + reportName + "/" + currentTime + "/";
	String dirPath = getWebRootPath() + pdfDirPath;
	String fileName = reportName + ".pdf";

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	/********************************************
	 * PDF Document Generation
	 ***************************************/
	String reportNameLedger = "LifePolicyLedger";
	String pdfDirPathLedger = "/pdf-report/" + reportNameLedger + "/" + currentTime + "/";
	String dirPathLedger = getWebRootPath() + pdfDirPathLedger;
	String fileNameLedger = reportNameLedger + ".pdf";

	public String getReportStreamLedger() {
		return pdfDirPathLedger + fileNameLedger;
	}

	public String getStream() {
		return pdfDirPath + fileName;
	}

	public void handleClose(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkGroupLife(LifePolicy lifePolicy) {
		boolean ans = false;
		for (PolicyInsuredPerson person : lifePolicy.getInsuredPersonInfo()) {
			if (person.getProduct().getId().equals(KeyFactorIDConfig.getGroupLifeId())) {
				ans = true;
			}
		}
		return ans;
	}

	public void generateCashReceipt(LPL001 lifeProposalDTO) {
		if (allowToPrint(lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId()), WorkflowTask.ISSUING)) {
			// FIXME CHECK REFTYPE
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			isStudentLife = KeyFactorChecker.isStudentLife(product.getId());
			isSeamanLife = KeyFactorChecker.isSeaMenLife(product.getId());
			isGovernmentShortTermEndowment = KeyFactorChecker.isGovernmentShortTermEndowment(product.getId());
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isStudentLife ? PolicyReferenceType.STUDENT_LIFE_POLICY
																	: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY 
																			: isSportMan ?  PolicyReferenceType.SPORT_MAN_POLICY
																					:isSeamanLife ? PolicyReferenceType.SEAMAN_LIFE_POLICY : PolicyReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY;
			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, null);
			Payment payment = new Payment(paymentList);

			pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
			dirPath = getWebRootPath() + pdfDirPath;

			fileName = isFarmer ? "FarmerCashReceipt.pdf"
					: isSnakeBite ? "SnakeBiteCashReceipt.pdf"
							: isSportMan ? "SportManCashReceipt.pdf" 
									: isPersonalAccident ? "PersonalAccidentCashReceipt.pdf" 
											:isSeamanLife ? "SeamanCashReceipt.pdf" : "LifeCashReceipt.pdf";
			DocumentBuilder.generateLifeReceiptLetter(lifeProposal, payment, false, dirPath, fileName);
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.PAYMENT.getLabel().toLowerCase());
			showInformationDialog();
		}
	}

	public void generateEndorseCashReceipt(LPL001 lifeProposalDTO) {
		if (allowToPrint(lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId()), WorkflowTask.ISSUING)) {
			// FIXME CHECK REFTYPE
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(lifeProposalDTO.getProposalId());
			Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();
			isFarmer = KeyFactorChecker.isFarmer(product);
			isSportMan = KeyFactorChecker.isSportMan(product);
			isSnakeBite = KeyFactorChecker.isSnakeBite(product.getId());
			isShortermEndownment = KeyFactorChecker.isShortTermEndowment(product.getId());
			isGroupLife = KeyFactorChecker.isGroupLife(product);
			isEndownmentLife = KeyFactorChecker.isPublicLife(product);
			isPersonalAccident = KeyFactorChecker.isPersonalAccident(product);
			PolicyReferenceType policyReferenceType = isFarmer ? PolicyReferenceType.FARMER_POLICY
					: isSnakeBite ? PolicyReferenceType.SNAKE_BITE_POLICY
							: isShortermEndownment ? PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY
									: isGroupLife ? PolicyReferenceType.GROUP_LIFE_POLICY
											: isEndownmentLife ? PolicyReferenceType.ENDOWNMENT_LIFE_POLICY
													: isPersonalAccident ? PolicyReferenceType.PA_POLICY
															: isPublicTermLife ? PolicyReferenceType.PUBLIC_TERM_LIFE_POLICY : PolicyReferenceType.SPORT_MAN_POLICY;
			List<Payment> paymentList = paymentService.findByProposal(lifeProposal.getId(), policyReferenceType, null);
			Payment payment = new Payment(paymentList);

			pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
			dirPath = getWebRootPath() + pdfDirPath;

			fileName = isFarmer ? "FarmerCashReceipt.pdf"
					: isSnakeBite ? "SnakeBiteCashReceipt.pdf"
							: isSportMan ? "SportManCashReceipt.pdf" : isPersonalAccident ? "PersonalAccidentCashReceipt.pdf" : "LifeCashReceipt.pdf";
			DocumentBuilder.generateLifeReceiptLetter(lifeProposal, payment, false, dirPath, fileName);
			showPdfDialog();
		} else {
			this.message = getMessage(MessageId.WORKFLOW_INFORMATION_MESSAGE, WorkflowTask.PAYMENT.getLabel().toLowerCase());
			showInformationDialog();
		}
	}

	public void handleCloseCashReceipt(CloseEvent event) {
		try {
			org.ace.insurance.web.util.FileHandler.forceDelete(getSystemPath() + "/pdf-report/LifeCashReceipt/" + currentTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean allowToEdit(String refNo) {
		boolean flag = true;
		WorkFlow wf = workFlowService.findWorkFlowByRefNo(refNo);
		if (wf == null) {
			flag = false;
			this.message = "This proposal has been legalized as policy contract.";
			showInformationDialog();
		} else {
			if (wf.getWorkflowTask().equals(WorkflowTask.PROPOSAL) || wf.getWorkflowTask().equals(WorkflowTask.SURVEY) || wf.getWorkflowTask().equals(WorkflowTask.APPROVAL)
					|| wf.getWorkflowTask().equals(WorkflowTask.INFORM) || wf.getWorkflowTask().equals(WorkflowTask.CONFIRMATION)) {
				flag = true;
			} else {
				flag = false;
				this.message = "This proposal is not in the editable workflow phase. It's currently at " + wf.getWorkflowTask().getLabel() + " phase";
				showInformationDialog();
			}
		}
		return flag;
	}

	public LifePolicy getLifePolicy() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByLifeProposalId(lifeProposal.getId());
		return lifePolicy;
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

	public void returnProduct(SelectEvent event) {
		Product product = (Product) event.getObject();
		criteria.setProduct(product);
	}

	public void returnSalePoint(SelectEvent event) {
		SalesPoints salePoint = (SalesPoints) event.getObject();
		criteria.setSalePoint(salePoint);
	}

	private void showInformationDialog() {
		PrimeFaces.current().executeScript("PF('informationDialog').show();");
	}

	public SaleChannelType[] getSaleChannel() {
		SaleChannelType[] types = { SaleChannelType.AGENT, SaleChannelType.WALKIN, SaleChannelType.DIRECTMARKETING };
		return types;
	}

	public List<Branch> getBranches() {
		return user.getAccessBranchList();
	}
}
