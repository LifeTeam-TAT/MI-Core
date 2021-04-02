package org.ace.insurance.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.ace.insurance.common.BC001;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimProposalService;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.claim.service.interfaces.ILifeDisabilityClaimService;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;
import org.ace.insurance.life.paidUp.service.interfaces.ILifePaidUpProposalService;
import org.ace.insurance.life.premium.service.interfaces.ILifePolicyBillingService;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.life.surrender.service.interfaces.ILifeSurrenderProposalService;
import org.ace.insurance.medical.claim.service.interfaces.IMedicalClaimProposalService;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.proxy.AGT001;
import org.ace.insurance.proxy.CCL001;
import org.ace.insurance.proxy.LCB001;
import org.ace.insurance.proxy.LCLD001;
import org.ace.insurance.proxy.LCP001;
import org.ace.insurance.proxy.LIF001;
import org.ace.insurance.proxy.LPP001;
import org.ace.insurance.proxy.LSP001;
import org.ace.insurance.proxy.MED001;
import org.ace.insurance.proxy.MEDCLM002;
import org.ace.insurance.proxy.MEDFEES001;
import org.ace.insurance.proxy.MTR001;
import org.ace.insurance.proxy.SPMA001;
import org.ace.insurance.proxy.WF001;
import org.ace.insurance.proxy.WorkflowCriteria;
import org.ace.insurance.proxy.service.interfaces.IProxyService;
import org.ace.insurance.user.User;
//import org.ace.insurance.web.manage.medical.survey.factory.MedicalSurveyDTOFactory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.event.ToggleEvent;

/**
 * @author PPA
 *
 */
@ViewScoped
@ManagedBean(name = "DashBoard")
public class DashBoard extends BaseBean {

	@ManagedProperty(value = "#{ProxyService}")
	private IProxyService proxyService;

	public void setProxyService(IProxyService proxyService) {
		this.proxyService = proxyService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	@ManagedProperty(value = "#{PaymentService}")
	private IPaymentService paymentService;

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService claimService;

	public void setClaimService(ILifeClaimService claimService) {
		this.claimService = claimService;
	}

	@ManagedProperty(value = "#{LifeDisabilityClaimService}")
	private ILifeDisabilityClaimService disabilityClaimService;

	public void setDisabilityClaimService(ILifeDisabilityClaimService disabilityClaimService) {
		this.disabilityClaimService = disabilityClaimService;
	}

	@ManagedProperty(value = "#{LifePolicyBillingService}")
	private ILifePolicyBillingService lifePolicyBillingService;

	public void setLifePolicyBillingService(ILifePolicyBillingService lifePolicyBillingService) {
		this.lifePolicyBillingService = lifePolicyBillingService;
	}

	@ManagedProperty(value = "#{MedicalClaimProposalService}")
	private IMedicalClaimProposalService medicalClaimProposalService;

	public void setMedicalClaimProposalService(IMedicalClaimProposalService medicalClaimProposalService) {
		this.medicalClaimProposalService = medicalClaimProposalService;
	}

	@ManagedProperty(value = "#{LifeSurrenderProposalService}")
	private ILifeSurrenderProposalService surrenderProposalService;

	public void setSurrenderProposalService(ILifeSurrenderProposalService surrenderProposalService) {
		this.surrenderProposalService = surrenderProposalService;
	}

	@ManagedProperty(value = "#{LifePaidUpProposalService}")
	private ILifePaidUpProposalService lifePaidUpProposalService;

	public void setLifePaidUpProposalService(ILifePaidUpProposalService lifePaidUpProposalService) {
		this.lifePaidUpProposalService = lifePaidUpProposalService;
	}

	@ManagedProperty(value = "#{UserProcessService}")
	private IUserProcessService userProcessService;

	public void setUserProcessService(IUserProcessService userProcessService) {
		this.userProcessService = userProcessService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	@ManagedProperty(value = "#{LifeClaimProposalService}")
	private ILifeClaimProposalService lifeClaimProposalService;

	public void setLifeClaimProposalService(ILifeClaimProposalService lifeClaimProposalService) {
		this.lifeClaimProposalService = lifeClaimProposalService;
	}

	private User user;
	private List<WF001> workflowTasks;
	private WF001 selectedTask;

	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		
	}
	
	public void toggleCollapsed(ToggleEvent event) {
		if(event.getVisibility().name().equals("VISIBLE")){
			loadWorkflowCountByUser();
		}
	}
	

	private void loadWorkflowCountByUser() {
		workflowTasks = workFlowService.findWorkflowCountByUser(user.getId(), user.getLoginBranch().getId());
	}

	/***********************************
	 * Dashbboard Message
	 *************************************/
	private static final String MESSAGE_PARAM_SUFFIX = "_PARAM";
	private static final String MESSAGE_REQUEST_PARAM_SUFFIX = "_REQUEST_PARAM";

	public void checkMessage(ComponentSystemEvent event) {
		ExternalContext extContext = getFacesContext().getExternalContext();
		String messageId = (String) extContext.getSessionMap().get(Constants.MESSAGE_ID);
		String proposalNo = (String) extContext.getSessionMap().get(Constants.PROPOSAL_NO);
		String requestNo = (String) extContext.getSessionMap().get(Constants.REQUEST_NO);
		String messageParam = (String) extContext.getSessionMap().get(Constants.MESSAGE_PARAM);
		if (messageId != null) {
			if (proposalNo != null) {
				addInfoMessage(null, messageId + MESSAGE_PARAM_SUFFIX, proposalNo);
				extContext.getSessionMap().remove(Constants.MESSAGE_ID);
				extContext.getSessionMap().remove(Constants.PROPOSAL_NO);
			} else {
				if (requestNo != null) {
					addInfoMessage(null, messageId + MESSAGE_REQUEST_PARAM_SUFFIX, requestNo);
					extContext.getSessionMap().remove(Constants.MESSAGE_ID);
					extContext.getSessionMap().remove(Constants.REQUEST_NO);
				} else {
					addInfoMessage(null, messageId, messageParam);
					extContext.getSessionMap().remove(Constants.MESSAGE_ID);
					extContext.getSessionMap().remove(Constants.MESSAGE_PARAM);
				}
			}
		}
	}

	private void clearList() {
		billCollectionTasks = null;
		lifeTasks = null;
		healthTasks = null;
		agentCommissionTasks = null;
		medicalFeesTasks = null;
		healthClaimTasks = null;
		lifeClaimTasks = null;
		lifeClaimProposalTasks = null;
		lifeClaimPaymentTasks = null;
		lifeSurrenderTasks = null;
		lifePaidUpTasks = null;
		sportManAbroadTasks = null;
	}

	public List<WF001> getWorkflowTasks() {
		return workflowTasks;
	}

	public WF001 getSelectedTask() {
		return selectedTask;
	}

	public void loadWorkFlowList(WF001 workflow) {
		clearList();
		this.selectedTask = workflow;
		ReferenceType refType = selectedTask.getReferenceType();
		WorkflowTask task = selectedTask.getWorkflowTask();
		TransactionType tranType = selectedTask.getTransactionType();
		loadWorkFlowListData(refType, task, tranType);
	}

	public void loadWorkFlowListData(ReferenceType refType, WorkflowTask task, TransactionType tranType) {
		WorkflowCriteria criteria = new WorkflowCriteria(refType, task, tranType, user);
		switch (tranType) {
			case BILL_COLLECTION:
				if (ReferenceType.HEALTH.equals(refType) || ReferenceType.CRITICAL_ILLNESS.equals(refType) || ReferenceType.MICRO_HEALTH.equals(refType)) {
					billCollectionTasks = proxyService.find_Health_BC001(criteria);
				} else {
					billCollectionTasks = proxyService.find_BC001(criteria);
				}
				break;
			default:
				break;
		}
		switch (refType) {

			case HEALTH:
			case MICRO_HEALTH:
			case CRITICAL_ILLNESS:
				healthTasks = proxyService.find_MED001(criteria);
				break;
			case PA:
			case FARMER:
			case GROUP_LIFE:
			case ENDOWMENT_LIFE:
			case SPORT_MAN:
			case SNAKE_BITE:
			case SHORT_ENDOWMENT_LIFE:
			case GOVERNMENT_SHORT_ENDOWMENT_LIFE:
			case SEAMEN_LIFE:
			case PUBLIC_TERM_LIFE:
			case STUDENT_LIFE:
				lifeTasks = proxyService.find_LIF001(criteria);
				break;

			case CLAIM_MEDICAL_FEES:
				medicalFeesTasks = proxyService.find_MEDFEES001(criteria);
				break;

			case AGENT_COMMISSION:
				agentCommissionTasks = proxyService.find_AGT001(criteria);
				break;

			case HEALTH_CLAIM:
				healthClaimTasks = proxyService.find_MEDCLM002(criteria);
				break;
			case LIFE_DIS_CLAIM:
			case LIFE_DEALTH_CLAIM:
				if (selectedTask.getWorkflowTask().equals(WorkflowTask.PAYMENT))
					lifeClaimPaymentTasks = proxyService.find_LCB001(criteria);
				else
					lifeClaimTasks = proxyService.find_LCLD001(criteria);
				break;
			case LIFE_CLAIM:

				lifeClaimProposalTasks = proxyService.find_LCP001(criteria);
				break;
			case LIFESURRENDER:
				lifeSurrenderTasks = proxyService.find_LSP001(criteria);
				break;

			case LIFE_PAIDUP_PROPOSAL:
				lifePaidUpTasks = proxyService.find_LPP001(criteria);
				break;

			// case SNAKE_BITE:
			// snakeBiteTasks = proxyService.find_SBP001(criteria);
			// break;

			case SPORT_MAN_ABROAD:
				sportManAbroadTasks = proxyService.findSoprtManAbroad_SPMA001(criteria);
			default:
				break;
		}
	}

	public void clearSession() {
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.LOGIN_USER);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.LOGIN_USER, user);
		userProcessService.registerUser(user);
	}

	/************************ Motor ************************************/
	private List<MTR001> motorTasks;

	public List<MTR001> getMotorTasks() {
		return motorTasks;
	}

	/************************ Life ************************************/

	private List<LIF001> lifeTasks;

	public List<LIF001> getLifeTasks() {
		return lifeTasks;
	}

	private void outjectLifeSurvey(String proposalId) {
		LifeSurvey lifeSurvey = new LifeSurvey();
		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(proposalId);
		lifeSurvey.setLifeProposal(lifeProposal);
		putParam("lifeSurvey", lifeSurvey);
	}

	private void outjectLifeProposal(String proposalId) {
		LifeProposal lifeProposal = lifeProposalService.findLifeProposalById(proposalId);
		putParam("lifeProposal", lifeProposal);
	}

	public String prepareLifeTask(String proposalId) {
		if (selectedTask.getWorkflowTask().equals(WorkflowTask.SURVEY))
			outjectLifeSurvey(proposalId);
		else
			outjectLifeProposal(proposalId);
		return navigation();
	}

	/************************
	 * Sport Man Abroad
	 ************************************/

	private List<SPMA001> sportManAbroadTasks;

	public List<SPMA001> getSportManAbroadTasks() {
		return sportManAbroadTasks;
	}

	public String prepareSportManAbroadTask(String invoiceNo) {
		Payment payment = paymentService.findByInvoiceNo(invoiceNo);
		putParam("payment", payment);
		return navigation();
	}

	/************************ Health ************************************/

	private List<MED001> healthTasks;

	public List<MED001> getHealthTasks() {
		return healthTasks;
	}

	public String prepareHealthTask(String proposalId) {
		MedicalProposal medicalProposal = medicalProposalService.findMedicalProposalById(proposalId);
		if (selectedTask.getWorkflowTask().equals(WorkflowTask.SURVEY)) {
			outjectMedicalSurvey(medicalProposal);
		} else {
			outjectMedicalProposal(medicalProposal);
		}
		return navigation();
	}

	private void outjectMedicalProposal(MedicalProposal medicalProposal) {
		putParam("medicalProposal", medicalProposal);
	}

	private void outjectMedicalSurvey(MedicalProposal medicalProposal) {
		MedicalSurvey medicalSurvey = new MedicalSurvey();
		medicalSurvey.setMedicalProposal(medicalProposal);
		putParam("medicalSurvey", medicalSurvey);
	}

	/************************
	 * AgentCommission
	 ************************************/

	private List<AGT001> agentCommissionTasks;

	public List<AGT001> getAgentCommissionTasks() {
		return agentCommissionTasks;
	}

	public String prepareAgentCommissionTask(AGT001 agentCommission) {
		putParam("AgentCommissionDTO", agentCommission);
		return navigation();
	}

	public String prepareMedicalFeesTask(MEDFEES001 medfees001) {
		putParam("MEDCALFEESDTO", medfees001);
		return navigation();
	}

	private List<MEDFEES001> medicalFeesTasks;

	public List<MEDFEES001> getMedicalFeesTasks() {
		return medicalFeesTasks;
	}

	/************************
	 * LifeSurrender
	 ************************************/
	private List<LSP001> lifeSurrenderTasks;

	public List<LSP001> getLifeSurrenderTasks() {
		return lifeSurrenderTasks;
	}

	public String prepareLifeSurrenderTask(String proposalNo) {
		LifeSurrenderProposal proposal = surrenderProposalService.findByProposalNo(proposalNo);
		putParam("surrenderProposal", proposal);
		return navigation();
	}

	/************************ CargoClaim ************************************/

	private List<CCL001> cargoClaimTasks;

	public List<CCL001> getCargoClaimTasks() {
		return cargoClaimTasks;
	}

	/************************ HealthClaim ************************************/

	private List<MEDCLM002> healthClaimTasks;

	public List<MEDCLM002> getHealthClaimTasks() {
		return healthClaimTasks;
	}

	private void outjectHealthClaim(String proposalId) {
		// TODO FIXME PSH Claim Case
		/*
		 * MedicalClaimProposal claimProposal =
		 * medicalClaimProposalService.findMedicalClaimProposalById(proposalId);
		 * MedicalClaimProposalDTO proposal =
		 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
		 * claimProposal); for (MedicalClaim mc :
		 * claimProposal.getMedicalClaimList()) { if
		 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
		 * proposal.setOperationClaimDTO(medicalClaimProposalService.
		 * findOperationClaimById(mc.getId())); } else if
		 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
		 * proposal.setDeathClaimDTO(medicalClaimProposalService.
		 * findDeathClaimById(mc.getId())); } else if
		 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
		 * proposal.setHospitalizedClaimDTO(medicalClaimProposalService.
		 * findHospitalizedClaimById(mc.getId())); } else if
		 * (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
		 * proposal.setMedicationClaimDTO(medicalClaimProposalService.
		 * findMedicationClaimById(mc.getId())); } }
		 * 
		 * putParam("medicalClaimProposal", proposal);
		 */
	}

	public String prepareHealthClaimTask(String proposalId) {
		outjectHealthClaim(proposalId);
		return navigation();
	}

	/************************
	 * LifeClaim(Disability/Death)
	 ************************************/
	private List<LCP001> lifeClaimProposalTasks;

	public List<LCP001> getLifeClaimProposalTasks() {
		return lifeClaimProposalTasks;
	}

	private List<LCLD001> lifeClaimTasks;
	private List<LCB001> lifeClaimPaymentTasks;

	public List<LCLD001> getLifeClaimTasks() {
		return lifeClaimTasks;
	}

	public List<LCB001> getLifeClaimPaymentTasks() {
		return lifeClaimPaymentTasks;
	}

	private void outjectLifeClaim(String claimRequestId) {
		LifeClaim claim = claimService.findLifeClaimByRequestId(claimRequestId);
		if (selectedTask.getReferenceType().equals(ReferenceType.LIFE_DIS_CLAIM))
			putParam("lifeDisabilityClaim", claim);
		else
			putParam("lifeClaim", claim);
		putParam("fromDashboard", true);
	}

	private void outjectLifeClaimBeneficiary(String refundNo) {
		LifeClaimBeneficiary lifeClaimBeneficiary = claimService.findLifeClaimBeneficaryByRefundNo(refundNo);
		putParam("lifeClaimBeneficiary", lifeClaimBeneficiary);
	}

	public String prepareLifeClaimTask(String claimRequestId) {
		if (selectedTask.getWorkflowTask().equals(WorkflowTask.PAYMENT))
			outjectLifeClaimBeneficiary(claimRequestId);
		else
			outjectLifeClaim(claimRequestId);
		return navigation();
	}

	public String prepareLifeClaim(String lifeClaimProposalId) {
		LifeClaimProposal lifeClaimProposal = lifeClaimProposalService.findLifeClaimProposalById(lifeClaimProposalId);
		outjectLifeClaimProposal(lifeClaimProposal);
		return navigation();
	}

	private void outjectLifeClaimProposal(LifeClaimProposal claimProposal) {
		putParam("lifeClaimProposal", claimProposal);
	}

	/************************ LifePaidUp ************************************/

	private List<LPP001> lifePaidUpTasks;

	public List<LPP001> getLifePaidUpTasks() {
		return lifePaidUpTasks;
	}

	public String prepareLifePaidUpProposal(String proposalNo) {
		LifePaidUpProposal paidUpProposal = lifePaidUpProposalService.findByProposalNo(proposalNo);
		putParam("paidUpProposal", paidUpProposal);
		return navigation();
	}

	/********************************
	 * BillCollection Payment
	 ********************************/

	private List<BC001> billCollectionTasks;

	public List<BC001> getBillCollectionTasks() {
		return billCollectionTasks;
	}

	public String prepareBillCollectionTask(String invoiceNo, String policyNo) {
		Payment payment = paymentService.findByInvoiceNo(invoiceNo);
		putParam("payment", payment);
		return navigation();
	}

	/************************ Navigation ************************************/

	private String navigation() {
		StringBuffer result = new StringBuffer();
		result.append(selectedTask.getReferenceType().getLabel());
		result.append(selectedTask.getTransactionType().getLabel());
		result.append(selectedTask.getWorkflowTask().getLabel());
		return result.toString();
	}

}
