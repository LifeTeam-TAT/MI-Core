/**
 *
 */
package org.ace.insurance.web.manage.life.claim.request;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import org.ace.insurance.filter.life.LPLC001;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimAttachment;
import org.ace.insurance.life.claim.LifeClaimDisabilityPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonAttachment;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
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
@ManagedBean(name = "LifeDisabilityClaimRequestActionBean")
public class LifeDisabilityClaimRequestActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean fromDashBoard = true;
	private boolean disablelinkBtn;
	private boolean showGallery;
	private boolean chkFlag = true;
	private boolean isUpdate;
	private boolean showEntry;
	private boolean showUploadedClaimAttachment = false;
	private boolean showUploadedDisabilityPersonAttachment = false;
	private boolean insuredPersonSelect;
	private boolean pending;
	private boolean checkedpolicyInsuredPersonBeneficiaries;
	private String strPolicyNo;
	private String beneficiary;
	private String temporyDir;
	private String remark;
	private String strDisabilityReason;
	private Agent agent;
	private Branch branch;
	private Date submittedDate;
	private Date disabledDate;
	private User responsiblePerson;
	private DisabilityClaimDTO disabilityClaimDTO;
	private LifeDisabilityClaim disabilityClaim;
	private LifeClaimDisabilityPerson disabilityPerson;
	private PolicyInsuredPerson insuredPerson;
	private LifePolicy lifePolicy;
	private UploadedFile uploadedFile;
	private PolicyInsuredPerson policyInsuredPerson;
	private List<PolicyInsuredPerson> insuredPersonInfoList;
	private List<String> uploadedClaimAttachmentList;
	private List<String> uploadedDisabilityPersonAttachmentList;
	private List<PolicyInsuredPerson> policyInsuredPersonList;
	private LifePolicyCriteria lifePolicyCriteria;
	private List<LifePolicySearch> policySearchList;
	private Map<String, String> claimUploadedFileMap;
	private Map<String, String> disabilityUploadedFileMap;
	private LifeClaim claim;
	private User user;
	@ManagedProperty(value = "#{LifeClaimService}")
	private ILifeClaimService claimService;

	public void setClaimService(ILifeClaimService claimService) {
		this.claimService = claimService;
	}

	@ManagedProperty(value = "#{LifePolicyService}")
	private ILifePolicyService lifePolicyService;

	public void setLifePolicyService(ILifePolicyService lifePolicyService) {
		this.lifePolicyService = lifePolicyService;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowDTOService;

	public void setWorkFlowDTOService(IWorkFlowService workFlowDTOService) {
		this.workFlowDTOService = workFlowDTOService;
	}

	private final String CLAIM_DIR = "/upload/life-disable/";
	// private List<LifeClaimDisabilityPartRate> productDisabilityRateList;
	// private LifeClaimDisabilityPartRate productDisabilityRate;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		showGallery = false;
		lifePolicyCriteria = new LifePolicyCriteria();
		disabilityClaimDTO = new DisabilityClaimDTO();
		claimUploadedFileMap = new HashMap<String, String>();
		disabilityUploadedFileMap = new HashMap<String, String>();
		uploadedClaimAttachmentList = new ArrayList<String>();
		uploadedDisabilityPersonAttachmentList = new ArrayList<String>();

		if (claim == null) {
			isUpdate = false;
			disablelinkBtn = false;
			fromDashBoard = false;
			claim = new LifeClaim();
			policyInsuredPerson = new PolicyInsuredPerson();
		} else {
			disablelinkBtn = true;
			isUpdate = true;

			List<DisabilityClaimInsuredPersonDTO> disabilityClaimInsuredPersonDTOList = new ArrayList<DisabilityClaimInsuredPersonDTO>();
			DisabilityClaimInsuredPersonDTO disabilityClaimInsuredPersonDTO = new DisabilityClaimInsuredPersonDTO();

			disabilityClaim = (LifeDisabilityClaim) claim;
			disabilityPerson = (LifeClaimDisabilityPerson) claim.getClaimInsuredPerson();

			disabilityClaimDTO.setLifePolicy(disabilityClaim.getLifePolicy());
			disabilityClaimDTO.setPolicyNo(disabilityClaim.getLifePolicy().getPolicyNo());
			disabilityClaimDTO.setBranch(disabilityClaim.getBranch());
			disabilityClaimDTO.setSubmittedDate(disabilityClaim.getSubmittedDate());
			disabilityClaimDTO.setDisabilityClaimInsuredPersonDTO(disabilityClaimInsuredPersonDTO);

			disabilityClaimInsuredPersonDTO.setDisabilityDate(disabilityPerson.getDisabilityDate());
			disabilityClaimInsuredPersonDTO.setDisabilityReason(disabilityPerson.getDisabilityReason());
			disabilityClaimInsuredPersonDTO.setPolicyInsuredPerson(disabilityPerson.getPolicyInsuredPerson());
			disabilityClaimInsuredPersonDTO.setClaimStatus(disabilityPerson.getClaimStatus());
			disabilityClaimInsuredPersonDTO.setDisabilityClaimDTO(disabilityClaimDTO);

			disabilityClaimInsuredPersonDTOList.add(disabilityClaimInsuredPersonDTO);
			disabilityClaimDTO.setDisabilityClaimInsuredPersonDTOList(disabilityClaimInsuredPersonDTOList);

			if (disabilityClaim.getClaimAttachmentList().size() > 0) {
				for (LifeClaimAttachment claimAttachment : disabilityClaim.getClaimAttachmentList()) {
					uploadedClaimAttachmentList.add(claimAttachment.getFilePath());
				}
				showUploadedClaimAttachment = !uploadedClaimAttachmentList.isEmpty();
			}
			if (disabilityClaim.getClaimInsuredPerson().getClaimInsuredPersonAttachmentList().size() > 0) {
				for (LifeClaimInsuredPersonAttachment claimInsuredPersonAttachment : disabilityClaim.getClaimInsuredPerson().getClaimInsuredPersonAttachmentList()) {
					uploadedDisabilityPersonAttachmentList.add(claimInsuredPersonAttachment.getFilePath());
				}
				showUploadedDisabilityPersonAttachment = !uploadedDisabilityPersonAttachmentList.isEmpty();
			}
		}

		temporyDir = "/temp/" + System.currentTimeMillis() + "/";

		if (isUpdate) {
			copyAttachmentToTemp(disabilityClaimDTO);
		}
		WorkFlow workFlow = workFlowDTOService.findWorkFlowByRefNo(claim.getClaimRequestId(), WorkflowTask.PROPOSAL);
		remark = workFlow == null ? "" : workFlow.getRemark();
	}

	/********************************************
	 * Action Controller
	 ********************************************/

	// Policy PopUp Click Event
	public void search() {
		policySearchList = lifePolicyService.findLifePolicyForClaimByCriteria(lifePolicyCriteria);
	}

	public void resetCriteria() {
		lifePolicyCriteria.setCriteriaValue(null);
		lifePolicyCriteria.setLifePolicyCriteriaItems(null);
		policySearchList = lifePolicyService.findLifePolicyForClaimByCriteria(null);
	}

	// Select Insured Person Event
	public void prepareInsuredPersonDTO(DisabilityClaimInsuredPersonDTO insuredPersonDTO) {
		if (!isUpdate) {
			disabilityClaimDTO.setDisabilityClaimInsuredPersonDTO(insuredPersonDTO);
		}
	}

	public void handleClaimAttachment(FileUploadEvent event) {
		uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		String filePath = temporyDir + disabilityClaimDTO.getClaimAttachmentRootPath() + "/" + fileName;
		claimUploadedFileMap.put(fileName, filePath);
		createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
	}

	// Insured Person Attachment Listener
	public void handleDisabilityAttachment(FileUploadEvent event) {
		uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		String filePath = temporyDir + disabilityClaimDTO.getClaimInsuredPersonAttachmentRootPath() + "/" + fileName;
		try {
			disabilityUploadedFileMap.put(fileName, filePath);
			FileHandler.createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Remove Claim Attachment
	public void removeClaimUploadedFile(String filePath) {
		String fileName = "";
		mapLoop: for (Entry<String, String> es : claimUploadedFileMap.entrySet()) {
			if (es.getValue().equals(filePath)) {
				fileName = es.getKey();
				deleteFile(claimUploadedFileMap.get(fileName));
				claimUploadedFileMap.remove(fileName);
				break mapLoop;
			}
		}
	}

	// Remove Insured Person Attachment
	public void removeDeathPersontUploadedFile(String filePath) {
		String fileName = "";
		mapLoop: for (Entry<String, String> es : disabilityUploadedFileMap.entrySet()) {
			if (es.getValue().equals(filePath)) {
				fileName = es.getKey();
				deleteFile(disabilityUploadedFileMap.get(fileName));
				disabilityUploadedFileMap.remove(fileName);
				break mapLoop;
			}
		}
	}

	/** End Insured Person Info **/

	/** WorkFlow **/

	// Pending CheckBox Checked Event
	public void changePendingValue() {
		if (pending) {
			this.responsiblePerson = this.user;
			showEntry = true;
		} else {
			this.responsiblePerson = new User();
			showEntry = false;
		}
	}

	// Responsible User PopUp Click Event
	public void selectUser() {
		selectUser(WorkflowTask.APPROVAL, WorkFlowType.LIFE, TransactionType.UNDERWRITING, getLoginBranchId(), null);
	}

	// Submit button click event
	public String submitClaimInfo() {
		String result = null;
		try {
			String formID = "lifeDisabilityClaimRequestForm";
			WorkFlowDTO workFlowDTO = null;
			if (responsiblePerson == null) {
				addErrorMessage(formID + ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID);
				return null;
			}
			WorkflowTask workflowTask = pending ? workflowTask = WorkflowTask.PROPOSAL : WorkflowTask.APPROVAL;
			workFlowDTO = new WorkFlowDTO(claim.getClaimRequestId(), getLoginBranchId(), remark, workflowTask, ReferenceType.LIFE_DIS_CLAIM, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			if (!isUpdate) {
				claimService.addNewLifeClaim(getLifeClaim(), workFlowDTO);
			} else {
				claimService.approveLifeClaim(getLifeClaim(), workFlowDTO);
			}
			if (!claimUploadedFileMap.isEmpty() || !disabilityUploadedFileMap.isEmpty()) {
				moveUploadedFilesClaim();
			}
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.UNDERWRITING_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.REQUEST_NO, workFlowDTO.getReferenceNo());
			result = "dashboard";
		} catch (SystemException exception) {
			handelSysException(exception);
		}

		return result;
	}

	/** End WorkFlow **/

	/** Wizard Flow Process **/

	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		String formID = "lifeDisabilityClaimRequestForm";
		if ("claimInfo".equals(event.getOldStep())) {
			if (disabilityClaimDTO.getPolicyNo() == null) {
				addErrorMessage(formID + ":policyNo", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		if ("insuredPersonInfo".equals(event.getOldStep()) && "workFlow".equals(event.getNewStep())) {
			if (disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityDate() == null) {
				addErrorMessage(formID + ":disabledDate", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}

			if (disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityReason() == null
					|| disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityReason().isEmpty()) {
				addErrorMessage(formID + ":strDisabilityReason", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		return valid ? event.getNewStep() : event.getOldStep();
	}

	/** End Wizard Flow Process **/

	/******************************************
	 * End Action Controller
	 ******************************************/

	/***********************************************
	 * Helper
	 *****************************************************/

	private void copyAttachmentToTemp(DisabilityClaimDTO disabilityClaimDTO) {
		String srcPath = getUploadPath() + CLAIM_DIR + disabilityClaimDTO.getClaimAttachmentRootPath();
		String destPath = getUploadPath() + temporyDir + disabilityClaimDTO.getClaimAttachmentRootPath();
		try {
			FileHandler.copyDirectory(srcPath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Populate into DisabilityClaimInsuredPersonDTO
	private List<DisabilityClaimInsuredPersonDTO> getPopulatedInsuredPersonDTOList(LifePolicy lifePolicy) {
		List<DisabilityClaimInsuredPersonDTO> dtoList = new ArrayList<DisabilityClaimInsuredPersonDTO>();
		if (Utils.isNotNull(lifePolicy.getInsuredPersonInfo())) {
			for (PolicyInsuredPerson insuredPerson : lifePolicy.getInsuredPersonInfo()) {
				DisabilityClaimInsuredPersonDTO dto = new DisabilityClaimInsuredPersonDTO();
				if (Utils.isNull(insuredPerson.getClaimStatus())) {
					dto.setClaimStatus(ClaimStatus.WAITING);
				} else {
					dto.setClaimStatus(insuredPerson.getClaimStatus());
				}
				dto.setPolicyInsuredPerson(insuredPerson);
				dto.setDisabilityClaimDTO(disabilityClaimDTO);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	private LifeClaim getLifeClaim() {
		LifeDisabilityClaim disClaim;
		LifeClaimDisabilityPerson disabilityPerson = null;
		if (!isUpdate) {
			disClaim = new LifeDisabilityClaim();
		} else {
			disClaim = (LifeDisabilityClaim) claim;
			disClaim.setClaimRequestId(claim.getClaimRequestId());
		}
		disClaim = loadClaimAttachment(disClaim);
		disClaim.setBranch(disabilityClaimDTO.getBranch());
		disClaim.setSubmittedDate(disabilityClaimDTO.getSubmittedDate());
		disClaim.setLifePolicy(disabilityClaimDTO.getLifePolicy());
		/* disability person */
		if (isUpdate) {
			disabilityPerson = (LifeClaimDisabilityPerson) disClaim.getClaimInsuredPerson();
			disabilityPerson.setNeedSomeDocument(false);
		} else {
			disabilityPerson = new LifeClaimDisabilityPerson();
		}
		disabilityPerson = loadDisabilityPersonAttachment(disabilityPerson);
		disabilityPerson.setDisabilityDate(disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityDate());
		disabilityPerson.setDisabilityReason(disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityReason());
		disabilityPerson.setPolicyInsuredPerson(disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getPolicyInsuredPerson());
		disabilityPerson.setClaimStatus(disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getClaimStatus());
		disabilityPerson.setClaimInsuredPersonLinkClaim(disClaim);
		disClaim.addClaimInsuredPerson(disabilityPerson);
		return (LifeClaim) disClaim;
	}

	private LifeDisabilityClaim loadClaimAttachment(LifeDisabilityClaim dclaim) {
		for (String fileName : claimUploadedFileMap.keySet()) {
			String filePath = CLAIM_DIR + disabilityClaimDTO.getClaimAttachmentRootPath() + "/" + fileName;
			dclaim.addClaimAttachment(new LifeClaimAttachment(fileName, filePath, (LifeClaim) dclaim));
		}
		return dclaim;
	}

	private LifeClaimDisabilityPerson loadDisabilityPersonAttachment(LifeClaimDisabilityPerson disabilityPerson) {
		for (String fileName : disabilityUploadedFileMap.keySet()) {
			String filePath = CLAIM_DIR + disabilityClaimDTO.getClaimInsuredPersonAttachmentRootPath() + "/" + fileName;
			disabilityPerson.addClaimInsuredPersonAttachment(new LifeClaimInsuredPersonAttachment(fileName, filePath, disabilityPerson));
		}
		return disabilityPerson;
	}

	public List<String> getUploadedClaimAttachmentList() {
		return uploadedClaimAttachmentList;
	}

	public List<String> getUploadedDisabilityPersonAttachmentList() {
		return uploadedDisabilityPersonAttachmentList;
	}

	public List<String> getClaimAttachmentList() {
		return new ArrayList<String>(claimUploadedFileMap.values());
	}

	public List<String> getDisabilityAttachmentList() {
		return new ArrayList<String>(disabilityUploadedFileMap.values());
	}

	/******************************************
	 * End Getter/Setter
	 ***********************************************/
	public void changepolicyInsuredPersonBeneficiariesValue(PolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
	}

	public void inputInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public void prepareinsuredPersonAttachment(PolicyInsuredPerson insuredPersonInfo) {
	}

	public void ShowPersonInfoAttachment(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public void DisplayInfoAttachment(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStrPolicyNo() {
		return strPolicyNo;
	}

	public void setStrPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getTemporyDir() {
		return temporyDir;
	}

	public void setTemporyDir(String temporyDir) {
		this.temporyDir = temporyDir;
	}

	public List<PolicyInsuredPerson> getInsuredPersonInfoList() {
		return insuredPersonInfoList;
	}

	public void setInsuredPersonInfoList(List<PolicyInsuredPerson> insuredPersonInfoList) {
		this.insuredPersonInfoList = insuredPersonInfoList;
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

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public List<PolicyInsuredPerson> getPolicyInsuredPersonList() {
		return policyInsuredPersonList;
	}

	public void setPolicyInsuredPersonList(List<PolicyInsuredPerson> policyInsuredPersonList) {
		this.policyInsuredPersonList = policyInsuredPersonList;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public boolean isCheckedpolicyInsuredPersonBeneficiaries() {
		return checkedpolicyInsuredPersonBeneficiaries;
	}

	public void setCheckedpolicyInsuredPersonBeneficiaries(boolean checkedpolicyInsuredPersonBeneficiaries) {
		this.checkedpolicyInsuredPersonBeneficiaries = checkedpolicyInsuredPersonBeneficiaries;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public boolean isdisablelinkBtn() {
		return disablelinkBtn;
	}

	public void setdisablelinkBtn(boolean disablelinkBtn) {
		this.disablelinkBtn = disablelinkBtn;
	}

	public String getStrDisabilityReason() {
		return strDisabilityReason;
	}

	public void setStrDisabilityReason(String strDisabilityReason) {
		this.strDisabilityReason = strDisabilityReason;
	}

	public LifeClaim getClaim() {
		return claim;
	}

	public void setClaim(LifeClaim claim) {
		this.claim = claim;
	}

	public boolean isShowEntry() {
		return showEntry;
	}

	public void setShowEntry(boolean showEntry) {
		this.showEntry = showEntry;
	}

	public Date getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(Date disabledDate) {
		this.disabledDate = disabledDate;
	}

	private void moveUploadedFilesClaim() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + disabilityClaimDTO.getClaimAttachmentRootPath(), CLAIM_DIR + disabilityClaimDTO.getClaimAttachmentRootPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteFile(String filePath) {
		File file = new File(getUploadPath() + filePath);
		try {
			FileHandler.forceDelete(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public boolean isShowGallery() {
		return showGallery;
	}

	public void setShowGallery(boolean showGallery) {
		this.showGallery = showGallery;
	}

	public boolean isShowUploadedClaimAttachment() {
		return showUploadedClaimAttachment;
	}

	public void setShowUploadedClaimAttachment(boolean showUploadedClaimAttachment) {
		this.showUploadedClaimAttachment = showUploadedClaimAttachment;
	}

	public boolean isShowUploadedDisabilityPersonAttachment() {
		return showUploadedDisabilityPersonAttachment;
	}

	public void setShowUploadedDisabilityPersonAttachment(boolean showUploadedDisabilityPersonAttachment) {
		this.showUploadedDisabilityPersonAttachment = showUploadedDisabilityPersonAttachment;
	}

	public void ShowUploadArea() {
		showGallery = true;
	}

	public boolean isChkFlag() {
		return chkFlag;
	}

	public void setChkFlag(boolean chkFlag) {
		this.chkFlag = chkFlag;
	}

	public PolicyInsuredPerson getInsuredPerson() {
		return insuredPerson;
	}

	public void setInsuredPerson(PolicyInsuredPerson insuredPerson) {
		this.insuredPerson = insuredPerson;
	}

	public void prepareInsuredPerson(PolicyInsuredPerson insuredPerson) {
		this.insuredPerson = insuredPerson;
	}

	public DisabilityClaimDTO getDisabilityClaimDTO() {
		return disabilityClaimDTO;
	}

	public void setDisabilityClaimDTO(DisabilityClaimDTO disabilityClaimDTO) {
		this.disabilityClaimDTO = disabilityClaimDTO;
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

	public boolean isfromDashBoard() {
		return fromDashBoard;
	}

	public void setfromDashBoard(boolean fromDashBoard) {
		this.fromDashBoard = fromDashBoard;
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		disabilityClaimDTO.setBranch(branch);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	public void setLifePolicy() {
		LPLC001 lplcDTO001 = (LPLC001) getParam("LplcDTO001");
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lplcDTO001.getPolicyNo());
		disabilityClaimDTO.setPolicyNo(lifePolicy.getPolicyNo());
		disabilityClaimDTO.setBranch(lifePolicy.getBranch());
	}

	public void returnLifePolicyNo(SelectEvent event) {
		LifePolicySearch lifePolicySearch = (LifePolicySearch) event.getObject();
		String formID = "lifeDisabilityClaimRequestForm";
		lifePolicy = lifePolicyService.findLifePolicyByPolicyNo(lifePolicySearch.getPolicyNo());
		if (Utils.isNotNull(lifePolicy)) {
			disabilityClaimDTO.setLifePolicy(lifePolicy);
			disabilityClaimDTO.setPolicyNo(lifePolicy.getPolicyNo());
			policyInsuredPersonList = lifePolicy.getInsuredPersonInfo();
			disabilityClaimDTO.setDisabilityClaimInsuredPersonDTOList(getPopulatedInsuredPersonDTOList(lifePolicy));
			disablelinkBtn = true;
		} else {
			addErrorMessage(formID + ":policyNo", MessageId.POLICY_NUMBER_NOT_EXIST);
		}
		disabilityClaimDTO.setPolicyNo(lifePolicySearch.getPolicyNo());
		disabilityClaimDTO.setBranch(lifePolicySearch.getBranch());
	}

	// public void returnDisabilityPart(SelectEvent event) {
	// productDisabilityRateList = new ArrayList<>();
	// List<DisabilityPart> disabilityPartList = new
	// ArrayList<DisabilityPart>();
	// List<DisabilityPart> selectedDisabilityPartList = (List<DisabilityPart>)
	// event.getObject();
	// for (DisabilityPart part : selectedDisabilityPartList) {
	// if (!disabilityPartList.contains(part)) {
	// productDisabilityRate = new LifeClaimDisabilityPartRate();
	// productDisabilityRate.setDisabilityPart(part);
	// productDisabilityRateList.add(productDisabilityRate);
	// }
	// }
	// disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().setDisabilityPartRateList(productDisabilityRateList);
	// }
	//
	// public void selectDisabilityPart() {
	// List<DisabilityPart> disabilityPartList = new
	// ArrayList<DisabilityPart>();
	// for (LifeClaimDisabilityPartRate rate :
	// disabilityClaimDTO.getDisabilityClaimInsuredPersonDTO().getDisabilityPartRateList())
	// {
	// disabilityPartList.add(rate.getDisabilityPart());
	// }
	// selectDisabilityPart(disabilityPartList);
	// }

	private String getLoginBranchId() {
		return user.getLoginBranch().getId();
	}
}
