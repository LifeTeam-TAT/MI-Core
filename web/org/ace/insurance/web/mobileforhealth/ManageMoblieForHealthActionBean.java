package org.ace.insurance.web.mobileforhealth;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.ProposalType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.service.MedicalProposalService;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.proxy.LIF001;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name="ManageMoblieForHealthActionBean")
public class ManageMoblieForHealthActionBean extends BaseBean{

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}


	private User user;
	private User responsiblePerson;
	private List<MedicalProposalDTO> medicalProposalList;
	private MedicalProposalDTO selectedMedicalProposal;
	public MedicalProposalDTO getSelectedMedicalProposal() {
		return selectedMedicalProposal;
	}

	public void setSelectedMedicalProposal(MedicalProposalDTO selectedMedicalProposal) {
		this.selectedMedicalProposal = selectedMedicalProposal;
	}


	private MedicalProposal medicalProposal;
	private boolean isSportMan;
	private boolean isPersonalAccident;
	private boolean isFarmer;
	private boolean isSnakeBite;
	private boolean isPublicTermLife;
	private boolean isEndorse;
	private boolean isShortTermEndowment;
	private boolean isConfirmEdit;
	private boolean isSurveyAgain;
	private boolean surveyAgain;
	private String loginBranchId;
	private String remark;
	private boolean isEditProposal;
	private boolean isEnquiryEdit;
	
	
	private void initializeInjection() {
		user = (User) getParam(Constants.LOGIN_USER);
		loginBranchId = user.getLoginBranch().getId();
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		loadMedicalPropoposalList();
	}
	
	private void loadMedicalPropoposalList() {
		medicalProposalList = medicalProposalService.findMedicalProposal();
	}
	
	public void convertTempToProposal(String proposalNo) {
		medicalProposal=medicalProposalService.findLifeProposalByProposalNoFromTerm(proposalNo);
		medicalProposal.setProposalType(ProposalType.UNDERWRITING);
		ExternalContext extContext = getFacesContext().getExternalContext();
		ReferenceType referenceType = getReferenceType(medicalProposal.getHealthType());
		WorkflowTask workFlowTask = surveyAgain ? WorkflowTask.SURVEY : WorkflowTask.APPROVAL;

		WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, workFlowTask, referenceType, TransactionType.UNDERWRITING, user,
				responsiblePerson);
		if (isEditProposal) {
			medicalProposal = medicalProposalService.updateMedicalProposal(medicalProposal, workFlowDTO);
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_PROPOSAL_PROCESS_SUCCESS);
		} else if (isEnquiryEdit) {
			medicalProposal = medicalProposalService.updateMedicalProposal(medicalProposal, null);
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.EDIT_PROPOSAL_PROCESS_SUCCESS);
		} else {
			if (medicalProposal.getHealthType().equals(HealthType.MICROHEALTH)) {
				workFlowTask = WorkflowTask.APPROVAL;
			} else {
				workFlowTask = WorkflowTask.SURVEY;
			}
			workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, workFlowTask, referenceType, TransactionType.UNDERWRITING, user, responsiblePerson);
			medicalProposal = medicalProposalService.addNewMedicalProposal(medicalProposal, workFlowDTO);
		}
	 
	}

	protected Object getParam(String key) {
		return getSessionMap().get(key);
	}
	
	protected Map<String, Object> getSessionMap() {
		return getFacesContext().getExternalContext().getSessionMap();
	}

	protected static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public List<MedicalProposalDTO> getMedicalProposalList() {
		return medicalProposalList;
	}

	public void setMedicalProposalList(List<MedicalProposalDTO> medicalProposalList) {
		this.medicalProposalList = medicalProposalList;
	}

	protected void putParam(String key, Object obj) {
		getSessionMap().put(key, obj);
	}
	
	
	private List<LIF001> lifeTasks;

	public List<LIF001> getLifeTasks() {
		return lifeTasks;
	}
	

	public void selectUser() {
		WorkFlowType workFlowType = isPersonalAccident ? WorkFlowType.PERSONAL_ACCIDENT
				: isSnakeBite ? WorkFlowType.SNAKE_BITE
						: isFarmer ? WorkFlowType.FARMER
								: isShortTermEndowment ? WorkFlowType.SHORT_ENDOWMENT : isPublicTermLife ? WorkFlowType.PUBLIC_TERM_LIFE : WorkFlowType.LIFE;
		WorkflowTask workflowTask = isEndorse ? WorkflowTask.SURVEY
				: (isSportMan || isSnakeBite || isPersonalAccident) ? WorkflowTask.APPROVAL : (isConfirmEdit && !isSurveyAgain) ? WorkflowTask.APPROVAL : WorkflowTask.SURVEY;
		selectUser(workflowTask, workFlowType, isEndorse ? TransactionType.ENDORSEMENT : TransactionType.UNDERWRITING, user.getLoginBranch().getId(), null);
	}


	private ReferenceType getReferenceType(HealthType healthType) {
		ReferenceType referenceType = null;
		switch (healthType) {
			case HEALTH:
				referenceType = ReferenceType.HEALTH;
				break;
			case CRITICALILLNESS:
				referenceType = ReferenceType.CRITICAL_ILLNESS;
				break;
			case MICROHEALTH:
				referenceType = ReferenceType.MICRO_HEALTH;
				break;
			default:
				break;
		}
		return referenceType;
	}
	
	
	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public void setMedicalProposal(MedicalProposal medicalProposal) {
		this.medicalProposal = medicalProposal;
	}

	public boolean isSportMan() {
		return isSportMan;
	}

	public void setSportMan(boolean isSportMan) {
		this.isSportMan = isSportMan;
	}

	public boolean isPersonalAccident() {
		return isPersonalAccident;
	}

	public void setPersonalAccident(boolean isPersonalAccident) {
		this.isPersonalAccident = isPersonalAccident;
	}

	public boolean isFarmer() {
		return isFarmer;
	}

	public void setFarmer(boolean isFarmer) {
		this.isFarmer = isFarmer;
	}

	public boolean isSnakeBite() {
		return isSnakeBite;
	}

	public void setSnakeBite(boolean isSnakeBite) {
		this.isSnakeBite = isSnakeBite;
	}

	public boolean isPublicTermLife() {
		return isPublicTermLife;
	}

	public void setPublicTermLife(boolean isPublicTermLife) {
		this.isPublicTermLife = isPublicTermLife;
	}

	public boolean isEndorse() {
		return isEndorse;
	}

	public void setEndorse(boolean isEndorse) {
		this.isEndorse = isEndorse;
	}

	public boolean isShortTermEndowment() {
		return isShortTermEndowment;
	}

	public void setShortTermEndowment(boolean isShortTermEndowment) {
		this.isShortTermEndowment = isShortTermEndowment;
	}

	public boolean isConfirmEdit() {
		return isConfirmEdit;
	}

	public void setConfirmEdit(boolean isConfirmEdit) {
		this.isConfirmEdit = isConfirmEdit;
	}

	public boolean isSurveyAgain() {
		return isSurveyAgain;
	}

	public void setSurveyAgain(boolean isSurveyAgain) {
		this.isSurveyAgain = isSurveyAgain;
	}

	

	public IMedicalProposalService getMedicalProposalService() {
		return medicalProposalService;
	}

	public void setLifeTasks(List<LIF001> lifeTasks) {
		this.lifeTasks = lifeTasks;
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public String getLoginBranchId() {
		return loginBranchId;
	}

	public void setLoginBranchId(String loginBranchId) {
		this.loginBranchId = loginBranchId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEditProposal() {
		return isEditProposal;
	}

	public void setEditProposal(boolean isEditProposal) {
		this.isEditProposal = isEditProposal;
	}

	public boolean isEnquiryEdit() {
		return isEnquiryEdit;
	}

	public void setEnquiryEdit(boolean isEnquiryEdit) {
		this.isEnquiryEdit = isEnquiryEdit;
	}
	
	
}
