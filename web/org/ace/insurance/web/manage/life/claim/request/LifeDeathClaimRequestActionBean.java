package org.ace.insurance.web.manage.life.claim.request;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.LifePolicyCriteriaItems;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimAttachment;
import org.ace.insurance.life.claim.LifeClaimDeathPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonAttachment;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.LifeClaimSuccessor;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.system.common.relationship.service.interfaces.IRelationShipService;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
@ViewScoped
@ManagedBean(name = "LifeDeathClaimRequestActionBean")
public class LifeDeathClaimRequestActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean disablelinkBtn;
	private boolean showEntry;
	private boolean fromDashBoard;
	private boolean showUploadedClaimAttachment;
	private boolean showUploadedDeathPersonAttachment;
	private boolean insuredPersonSelect;
	private boolean pending;
	private String temporyDir;
	private String remark;
	private List<String> uploadedClaimAttachmentList;
	private List<String> uploadedDeathPersonAttachmentList;
	private Map<String, String> claimUploadedFileMap;
	private Map<String, String> deathPersonUploadedFileMap;
	private User responsiblePerson;
	private LifePolicy lifePolicy;
	private DeathClaimDTO deathClaimDTO;
	private PolicyInsuredPerson selectedPolicyInsuredPerson;
	private LifePolicyCriteria lifePolicyCriteria;
	private List<LifePolicySearch> policySearchList;
	private List<RelationShip> relationShipList;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowDTOService;

	public void setWorkFlowDTOService(IWorkFlowService workFlowDTOService) {
		this.workFlowDTOService = workFlowDTOService;
	}

	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService lifeClaimService;

	public void setLifeClaimService(ILifeClaimService lifeClaimService) {
		this.lifeClaimService = lifeClaimService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{RelationShipService}")
	private IRelationShipService relationShipService;

	public void setRelationShipService(IRelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	private String transition;

	private User user;
	private LifeClaim lifeClaim;

	private final String LIFECLAIM_DIR = "/upload/life-claim/";

	@PostConstruct
	public void init() {
		user = (User) getParam("LoginUser");
		lifeClaim = (LifeClaim) getParam("lifeClaim");
		fromDashBoard = (lifeClaim != null) ? true : false;
		disablelinkBtn = false;
		lifePolicyCriteria = new LifePolicyCriteria();
		claimUploadedFileMap = new HashMap<String, String>();
		deathPersonUploadedFileMap = new HashMap<String, String>();
		uploadedClaimAttachmentList = new ArrayList<String>();
		uploadedDeathPersonAttachmentList = new ArrayList<String>();
		relationShipList = relationShipService.findAllRelationShip();
		if (fromDashBoard) {
			disablelinkBtn = true;
			deathClaimDTO = populateClaimInfoDTO();
		} else {
			deathClaimDTO = new DeathClaimDTO();
		}
		/* Create temp directory for upload */
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";
		if (fromDashBoard) {
			copyAttachmentToTemp(deathClaimDTO);
		}
		if (lifeClaim != null) {
			WorkFlow workFlow = workFlowDTOService.findWorkFlowByRefNo(lifeClaim.getClaimRequestId(), WorkflowTask.APPROVAL);
			remark = workFlow == null ? "" : workFlow.getRemark();
		}
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	/** Claim Info **/

	// Insured Person PopUp Click Event
	public void findInsuredPersonListByPolicyNo() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(deathClaimDTO.getPolicyNo());
		if (Utils.isNull(lifePolicy)) {
			addErrorMessage("lifeDeathClaimRequestForm:policyNo", MessageId.POLICY_NUMBER_NOT_EXIST);
		} else {
			deathClaimDTO.setLifePolicy(lifePolicy);
		}
	}

	// Select Insured Person Event
	public void selectPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		// populate DeathClaimInsuredPersonDTO
		DeathClaimInsuredPersonDTO claimInsuredPersonDTO = new DeathClaimInsuredPersonDTO();
		claimInsuredPersonDTO.setPolicyInsuredPerson(policyInsuredPerson);
		deathClaimDTO.setClaimInsuredPersonInfoDTO(claimInsuredPersonDTO);

		// populate DeathClaimInsuredPersonBeneficiary
		List<DeathClaimInsuredPersonBeneficiaryDTO> claimInsuredPersonBeneficiaryInfoList = new ArrayList<DeathClaimInsuredPersonBeneficiaryDTO>();
		for (PolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries : policyInsuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
			DeathClaimInsuredPersonBeneficiaryDTO claimInsuredPersonBeneficiaryInfoDTO = new DeathClaimInsuredPersonBeneficiaryDTO();
			claimInsuredPersonBeneficiaryInfoDTO.setPolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries);
			claimInsuredPersonBeneficiaryInfoDTO.setClaimStatus(insuredPersonBeneficiaries.getClaimStatus());
			// claimInsuredPersonBeneficiaryInfoDTO.setBeneficiaryStatus(insuredPersonBeneficiaries.getBeneficiaryStatus());
			claimInsuredPersonBeneficiaryInfoList.add(claimInsuredPersonBeneficiaryInfoDTO);
		}
		deathClaimDTO.setClaimInsuredPersonBeneficiaryInfoList(claimInsuredPersonBeneficiaryInfoList);
		disablelinkBtn = true;
	}

	/** End Claim Info **/

	/** Insured Person Info **/

	// Claim Attachment Upload Listener
	public void handleClaimAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = Long.toString(System.currentTimeMillis()) + "_" + uploadedFile.getFileName().replaceAll("\\s", "_");
		String filePath = temporyDir + deathClaimDTO.getClaimAttachmentRootPath() + "/" + fileName;
		try {
			FileHandler.createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		} catch (IOException e) {
			e.printStackTrace();
		}
		claimUploadedFileMap.put(fileName, filePath);
	}

	// Insured Person Attachment Upload Listener
	public void handleDeathPersonAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = Long.toString(System.currentTimeMillis()) + "_" + uploadedFile.getFileName().replaceAll("\\s", "_");
		String filePath = temporyDir + deathClaimDTO.getClaimInsuredPersonAttachmentRootPath() + "/" + fileName;
		try {
			FileHandler.createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		} catch (IOException e) {
			e.printStackTrace();
		}
		deathPersonUploadedFileMap.put(fileName, filePath);
	}

	// Remove Claim Attachment
	public void removeClaimUploadedFile(String filePath) {
		String fileName = "";
		for (Entry<String, String> es : claimUploadedFileMap.entrySet()) {
			if (es.getValue().equals(filePath)) {
				fileName = es.getKey();
				deleteFile(claimUploadedFileMap.get(fileName));
				claimUploadedFileMap.remove(fileName);
			}
		}
	}

	// Remove Insured Person Attachment
	public void removeDeathPersonUploadedFile(String filePath) {
		String fileName = "";
		for (Entry<String, String> es : deathPersonUploadedFileMap.entrySet()) {
			if (es.getValue().equals(filePath)) {
				fileName = es.getKey();
				deleteFile(deathPersonUploadedFileMap.get(fileName));
				deathPersonUploadedFileMap.remove(fileName);
			}
		}
	}

	// Death Info Update Button Click Event
	public void updatePolicyInsuredPerson() {
		String formID = "updatedeathPersonInfoForm";
		boolean valid = true;
		if (deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathDate() == null) {
			addErrorMessage(formID + ":deathDate", MessageId.REQUIRED_DEATH_PERSONINFO);
			valid = false;
		}
		if (deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathReason() == null || deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathReason().isEmpty()) {
			addErrorMessage(formID + ":deathReason", MessageId.REQUIRED_DEATH_PERSONINFO);
			valid = false;
		}
		if (valid) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('updatedeathPersonInfoDialog').hide();");

		}
	}

	/** End Insured Person Info **/

	public void selectUser() {
		selectUser(WorkflowTask.APPROVAL, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	/** Wizard Flow Process **/

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		String formID = "lifeDeathClaimRequestForm";
		if ("claimInfo".equals(event.getOldStep())) {
			boolean selected = false;
			for (DeathClaimInsuredPersonBeneficiaryDTO claiminsuredPersonBeneficiaryInfoDTO : deathClaimDTO.getClaimInsuredPersonBeneficiaryInfoList()) {
				if (claiminsuredPersonBeneficiaryInfoDTO.isClaimBeneficiary())
					selected = true;
			}
			if (!selected) {
				addErrorMessage(formID + ":policyInsuredPersonBeneficiariesTable", MessageId.REQUIRED_BENEFICIARY_PERSON_CHECKBOX);
				valid = false;
			}
			if (deathClaimDTO.getBranch() == null) {
				addErrorMessage(formID + ":branch", MessageId.REQUIRED_BRANCH);
				valid = false;
			}
			if (deathClaimDTO.getSubmittedDate() == null) {
				addErrorMessage(formID + ":submittedDate", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
			if (!deathClaimDTO.getNRCNo().isEmpty() || deathClaimDTO.getRelationShip() != null) {
				if (deathClaimDTO.getCustomerName().isEmpty()) {
					addErrorMessage(formID + ":customerName", UIInput.REQUIRED_MESSAGE_ID);
					valid = false;
				}
			}
			// if (!deathClaimDTO.getCustomerName().isEmpty() ||
			// deathClaimDTO.getRelationShip() != null) {
			// if (deathClaimDTO.getNRCNo().isEmpty()) {
			// addErrorMessage(formID + ":nrcNo", UIInput.REQUIRED_MESSAGE_ID);
			// valid = false;
			// }
			// }
			// if (!deathClaimDTO.getCustomerName().isEmpty() ||
			// !deathClaimDTO.getNRCNo().isEmpty()) {
			// if (deathClaimDTO.getRelationShip() == null) {
			// addErrorMessage(formID + ":relationship",
			// UIInput.REQUIRED_MESSAGE_ID);
			// valid = false;
			// }
			// }
		}
		if ("insuredPersonInfo".equals(event.getOldStep()) && "workFlow".equals(event.getNewStep())) {
			if (deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathDate() == null || deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathReason().isEmpty()) {
				addErrorMessage(formID + ":insuredPersonTable", MessageId.REQUIRED_DEATH_PERSONINFO);
				valid = false;
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	/** End Wizard Flow Process **/

	public void policyChangeOrgEvent(LifePolicySearch policySearch) {
		String policyNo = policySearch.getPolicyNo();
		Branch branch = policySearch.getBranch();
		deathClaimDTO.setPolicyNo(policyNo);
		deathClaimDTO.setBranch(branch);
	}

	public void search() {
		policySearchList = lifePolicyService.findLifePolicyForClaimByCriteria(lifePolicyCriteria);
	}

	public void resetCriteria() {
		lifePolicyCriteria.setCriteriaValue(null);
		lifePolicyCriteria.setLifePolicyCriteriaItems(null);
		policySearchList = lifePolicyService.findLifePolicyForClaimByCriteria(null);
	}

	public String submitClaimInfo() {
		String result = null;
		try {
			String formID = "lifeDeathClaimRequestForm";
			if (responsiblePerson == null) {
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				return null;
			}
			WorkflowTask workflowTask = pending ? WorkflowTask.REQUEST : WorkflowTask.APPROVAL;
			LifeClaim lifeClaim = getLifeClaim();
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeClaim.getClaimRequestId(), getLoginBranchId(), remark, workflowTask, ReferenceType.LIFE_DEALTH_CLAIM,
					TransactionType.UNDERWRITING, user, responsiblePerson);
			if (fromDashBoard) {
				lifeClaimService.approveLifeClaim(lifeClaim, workFlowDTO);
			} else {
				lifeClaimService.addNewLifeClaim(lifeClaim, workFlowDTO);
			}
			/* file upload */
			if (!claimUploadedFileMap.isEmpty() || !deathPersonUploadedFileMap.isEmpty()) {
				moveUploadedFiles();
			}
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.REQUEST_NO, workFlowDTO.getReferenceNo());
			result = "dashboard";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	/********************************************
	 * End Action Controller
	 *****************************************/

	/***********************************************
	 * Helper
	 *****************************************************/

	private void copyAttachmentToTemp(DeathClaimDTO deathClaimDTO) {
		String srcPath = getUploadPath() + LIFECLAIM_DIR + deathClaimDTO.getClaimAttachmentRootPath();
		String destPath = getUploadPath() + temporyDir + deathClaimDTO.getClaimAttachmentRootPath();
		try {
			FileHandler.copyDirectory(srcPath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + deathClaimDTO.getClaimAttachmentRootPath(), LIFECLAIM_DIR + deathClaimDTO.getClaimAttachmentRootPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteFile(String filePath) {
		File file = new File(getUploadPath() + temporyDir + filePath);
		try {
			FileHandler.forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void holdDeathClaimRequest() {
		if (pending) {
			responsiblePerson = user;
		} else {
			responsiblePerson = null;
		}
	}

	public DeathClaimDTO populateClaimInfoDTO() {
		DeathClaimDTO claimInfoDTO = new DeathClaimDTO();
		claimInfoDTO.setLifePolicy(lifeClaim.getLifePolicy());
		claimInfoDTO.setSubmittedDate(lifeClaim.getSubmittedDate());
		claimInfoDTO.setBranch(lifeClaim.getBranch());
		if (lifeClaim.getSuccessor() != null) {
			claimInfoDTO.setSuccessorId(lifeClaim.getSuccessor().getId());
			claimInfoDTO.setCustomerName(lifeClaim.getSuccessor().getName());
			claimInfoDTO.setNRCNo(lifeClaim.getSuccessor().getNrcNo());
			claimInfoDTO.setRelationShip(lifeClaim.getSuccessor().getRelationShip());
		}
		claimInfoDTO.setPolicyNo(lifeClaim.getLifePolicy().getPolicyNo());
		DeathClaimInsuredPersonDTO claimInsuredPersonInfoDTO = new DeathClaimInsuredPersonDTO();
		LifeClaimDeathPerson deathPerson = (LifeClaimDeathPerson) lifeClaim.getClaimInsuredPerson();
		claimInsuredPersonInfoDTO.setPolicyInsuredPerson(deathPerson.getPolicyInsuredPerson());
		claimInsuredPersonInfoDTO.setDeathDate(deathPerson.getDeathDate());
		claimInsuredPersonInfoDTO.setDeathReason(deathPerson.getDeathReason());
		claimInfoDTO.setClaimInsuredPersonInfoDTO(claimInsuredPersonInfoDTO);
		Map<String, DeathClaimInsuredPersonBeneficiaryDTO> beneficiaryMap = new HashMap<String, DeathClaimInsuredPersonBeneficiaryDTO>();
		DeathClaimInsuredPersonBeneficiaryDTO claimInsuredPersonBeneficiaryInfoDTO = null;
		for (PolicyInsuredPersonBeneficiaries beneficiary : lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getPolicyInsuredPersonBeneficiariesList()) {
			for (LifeClaimInsuredPersonBeneficiary claimBeneficiary : lifeClaim.getClaimInsuredPersonBeneficiaryList()) {
				if (beneficiary.getId().equals(claimBeneficiary.getPolicyInsuredPersonBeneficiaries().getId())) {
					claimInsuredPersonBeneficiaryInfoDTO = new DeathClaimInsuredPersonBeneficiaryDTO(claimBeneficiary);
					claimInsuredPersonBeneficiaryInfoDTO.setClaimBeneficiary(true);
					beneficiaryMap.put(claimInsuredPersonBeneficiaryInfoDTO.getPolicyInsuredPersonBeneficiaries().getId(), claimInsuredPersonBeneficiaryInfoDTO);
				}
			}
		}
		for (PolicyInsuredPersonBeneficiaries beneficiary : lifeClaim.getClaimInsuredPerson().getPolicyInsuredPerson().getPolicyInsuredPersonBeneficiariesList()) {
			if (!beneficiaryMap.containsKey(beneficiary.getId())) {
				claimInsuredPersonBeneficiaryInfoDTO = new DeathClaimInsuredPersonBeneficiaryDTO();
				claimInsuredPersonBeneficiaryInfoDTO.setPolicyInsuredPersonBeneficiaries(beneficiary);
				beneficiaryMap.put(claimInsuredPersonBeneficiaryInfoDTO.getPolicyInsuredPersonBeneficiaries().getId(), claimInsuredPersonBeneficiaryInfoDTO);
			}
		}
		claimInfoDTO.setClaimInsuredPersonBeneficiaryInfoList(new ArrayList<DeathClaimInsuredPersonBeneficiaryDTO>(beneficiaryMap.values()));
		for (LifeClaimAttachment claimAttachment : lifeClaim.getClaimAttachmentList()) {
			uploadedClaimAttachmentList.add(claimAttachment.getFilePath());
		}
		showUploadedClaimAttachment = !uploadedClaimAttachmentList.isEmpty();
		for (LifeClaimInsuredPersonAttachment claimInsuredPersonAttachment : lifeClaim.getClaimInsuredPerson().getClaimInsuredPersonAttachmentList()) {
			uploadedDeathPersonAttachmentList.add(claimInsuredPersonAttachment.getFilePath());
		}
		showUploadedDeathPersonAttachment = !uploadedDeathPersonAttachmentList.isEmpty();
		return claimInfoDTO;
	}

	private LifeClaim getLifeClaim() {
		LifeClaim claim = null;
		LifeClaimDeathPerson deathPerson = null;

		if (fromDashBoard) {
			claim = lifeClaim;
		} else {
			claim = new LifeClaim();
		}
		claim = loadClaimAttachment(claim);

		claim.setBranch(deathClaimDTO.getBranch());
		claim.setSubmittedDate(deathClaimDTO.getSubmittedDate());
		claim.setLifePolicy(deathClaimDTO.getLifePolicy());
		/* death person */
		if (fromDashBoard) {
			deathPerson = (LifeClaimDeathPerson) claim.getClaimInsuredPerson();
		} else {
			deathPerson = new LifeClaimDeathPerson();
			deathPerson.setId(claim.getId());
			deathPerson.setVersion(claim.getVersion());
		}
		deathPerson = loadDeathPersonAttachment(deathPerson);
		deathPerson.setDeathDate(deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathDate());
		deathPerson.setDeathReason(deathClaimDTO.getClaimInsuredPersonInfoDTO().getDeathReason());
		deathPerson.setPolicyInsuredPerson(deathClaimDTO.getClaimInsuredPersonInfoDTO().getPolicyInsuredPerson());
		deathPerson.setNeedSomeDocument(false);
		claim.addClaimInsuredPerson(deathPerson);
		/* claim Successor */
		LifeClaimSuccessor claimSuccessor = null;
		if (deathClaimDTO.hasSuccessor()) {
			if (deathClaimDTO.isNewSuccessor()) {
				claimSuccessor = new LifeClaimSuccessor();
			} else {
				claimSuccessor = claim.getSuccessor();
			}
			claimSuccessor.setName(deathClaimDTO.getCustomerName());
			claimSuccessor.setNrcNo(deathClaimDTO.getNRCNo());
			claimSuccessor.setRelationShip(deathClaimDTO.getRelationShip());
			if (deathClaimDTO.isNewSuccessor()) {
				claim.addClaimSuccessor(claimSuccessor);
			}
		}
		List<LifeClaimInsuredPersonBeneficiary> beneficiaryList = new ArrayList<LifeClaimInsuredPersonBeneficiary>();
		for (DeathClaimInsuredPersonBeneficiaryDTO claimInsuredPersonBeneficiaryInfoDTO : deathClaimDTO.getClaimInsuredPersonBeneficiaryInfoList()) {
			if (claimInsuredPersonBeneficiaryInfoDTO.isClaimBeneficiary()) {
				LifeClaimInsuredPersonBeneficiary claimInsuredPersonBeneficiary = new LifeClaimInsuredPersonBeneficiary();
				// claimInsuredPersonBeneficiary.setClaimBeneficiary(true);
				claimInsuredPersonBeneficiary.setLifeClaim(claim);
				claimInsuredPersonBeneficiary.setPolicyInsuredPersonBeneficiaries(claimInsuredPersonBeneficiaryInfoDTO.getPolicyInsuredPersonBeneficiaries());
				deathPerson.addClaimInsuredPersonBeneficiary(claimInsuredPersonBeneficiary);
				claimInsuredPersonBeneficiary.setClaimInsuredPerson(deathPerson);

				if (claimInsuredPersonBeneficiaryInfoDTO.getId() != null) {
					claimInsuredPersonBeneficiary.setId(claimInsuredPersonBeneficiaryInfoDTO.getId());
					claimInsuredPersonBeneficiary.setVersion(claimInsuredPersonBeneficiaryInfoDTO.getVersion());
					// claimInsuredPersonBeneficiary =
					// claim.mergeClaimInsuredPersonBeneficiary(claimInsuredPersonBeneficiary);
				}
				beneficiaryList.add(claimInsuredPersonBeneficiary);
				if (deathClaimDTO.hasSuccessor()) {
					claimInsuredPersonBeneficiary.setClaimSuccessor(claimSuccessor);
					claim.getSuccessor().addClaimInsuredPersonBeneficiary(claimInsuredPersonBeneficiary);
				}
			}
		}
		if (deathClaimDTO.hasSuccessor()) {
			claim.getSuccessor().setClaimInsuredPersonBeneficiaryList(beneficiaryList);
		}
		claim.setClaimInsuredPersonBeneficiaryList(beneficiaryList);
		return claim;
	}

	private LifeClaim loadClaimAttachment(LifeClaim claim) {
		for (String fileName : claimUploadedFileMap.keySet()) {
			String filePath = LIFECLAIM_DIR + deathClaimDTO.getClaimAttachmentRootPath() + "/" + fileName;
			claim.addClaimAttachment(new LifeClaimAttachment(fileName, filePath, claim));
		}
		return claim;
	}

	private LifeClaimDeathPerson loadDeathPersonAttachment(LifeClaimDeathPerson deathPerson) {
		for (String fileName : deathPersonUploadedFileMap.keySet()) {
			String filePath = LIFECLAIM_DIR + deathClaimDTO.getClaimInsuredPersonAttachmentRootPath() + "/" + fileName;
			deathPerson.addClaimInsuredPersonAttachment(new LifeClaimInsuredPersonAttachment(fileName, filePath, deathPerson));
		}
		return deathPerson;
	}

	public void ShowPersonAttachment(PolicyInsuredPerson policyInsuredPerson) {
		this.selectedPolicyInsuredPerson = policyInsuredPerson;
		showEntry = true;
	}

	public void ShowUploadArea() {
		showEntry = true;
	}

	/********************************************
	 * End Helper
	 ***************************************************/

	/********************************************
	 * Getter/Setter
	 *************************************************/

	public boolean isSameInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		return deathClaimDTO.getClaimInsuredPersonInfoDTO().getPolicyInsuredPerson().getId().equals(policyInsuredPerson.getId());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTemporyDir() {
		return temporyDir;
	}

	public void setTemporyDir(String temporyDir) {
		this.temporyDir = temporyDir;
	}

	public boolean isInsuredPersonSelect() {
		return insuredPersonSelect;
	}

	public void setInsuredPersonSelect(boolean insuredPersonSelect) {
		this.insuredPersonSelect = insuredPersonSelect;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public PolicyInsuredPerson getSelectedPolicyInsuredPerson() {
		return selectedPolicyInsuredPerson;
	}

	public void setSelectedPolicyInsuredPerson(PolicyInsuredPerson selectedPolicyInsuredPerson) {
		this.selectedPolicyInsuredPerson = selectedPolicyInsuredPerson;
	}

	public boolean isdisablelinkBtn() {
		return disablelinkBtn;
	}

	public void setdisablelinkBtn(boolean disablelinkBtn) {
		this.disablelinkBtn = disablelinkBtn;
	}

	public DeathClaimDTO getDeathClaimDTO() {
		return deathClaimDTO;
	}

	public void setDeathClaimDTO(DeathClaimDTO claimInfoDTO) {
		this.deathClaimDTO = claimInfoDTO;
	}

	/* attachment */
	public List<String> getClaimAttachmentList() {
		return new ArrayList<String>(claimUploadedFileMap.values());
	}

	public List<String> getDeathPersonAttachmentList() {
		return new ArrayList<String>(deathPersonUploadedFileMap.values());
	}

	public boolean isShowEntry() {
		return showEntry;
	}

	public void setShowEntry(boolean showEntry) {
		this.showEntry = showEntry;
	}

	public boolean isfromDashBoard() {
		return fromDashBoard;
	}

	public void setfromDashBoard(boolean fromDashBoard) {
		this.fromDashBoard = fromDashBoard;
	}

	public boolean isShowUploadedClaimAttachment() {
		return showUploadedClaimAttachment;
	}

	public void setShowUploadedClaimAttachment(boolean showUploadedClaimAttachment) {
		this.showUploadedClaimAttachment = showUploadedClaimAttachment;
	}

	public boolean isShowUploadedDeathPersonAttachment() {
		return showUploadedDeathPersonAttachment;
	}

	public void setShowUploadedDeathPersonAttachment(boolean showUploadedDeathPersonAttachment) {
		this.showUploadedDeathPersonAttachment = showUploadedDeathPersonAttachment;
	}

	public List<String> getUploadedClaimAttachmentList() {
		return uploadedClaimAttachmentList;
	}

	public List<String> getUploadedDeathPersonAttachmentList() {
		return uploadedDeathPersonAttachmentList;
	}

	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public LifePolicyCriteria getLifePolicyCriteria() {
		return lifePolicyCriteria;
	}

	public void setLifePolicyCriteria(LifePolicyCriteria lifePolicyCriteria) {
		this.lifePolicyCriteria = lifePolicyCriteria;
	}

	public LifePolicyCriteriaItems[] getLifePolicyCriteriaList() {
		return LifePolicyCriteriaItems.values();
	}

	public List<LifePolicySearch> getPolicySearchList() {
		return policySearchList;
	}

	public List<RelationShip> getRelationShipList() {
		return relationShipList;
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicySearch = (LifePolicySearch) event.getObject();
		deathClaimDTO.setPolicyNo(lifePolicySearch.getPolicyNo());
		deathClaimDTO.setBranch(lifePolicySearch.getBranch());
	}

	public void returnPolicyInsuredPerson(SelectEvent event) {
		PolicyInsuredPerson policyInsuredPerson = (PolicyInsuredPerson) event.getObject();
		DeathClaimInsuredPersonDTO dcInsuPerson = new DeathClaimInsuredPersonDTO();
		dcInsuPerson.setPolicyInsuredPerson(policyInsuredPerson);
		deathClaimDTO.setClaimInsuredPersonInfoDTO(dcInsuPerson);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		deathClaimDTO.setBranch(branch);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}
