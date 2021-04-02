package org.ace.insurance.web.manage.medical.survey.action;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.ProductProcessIDConfig;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.medical.productprocess.service.interfaces.IProductProcessService;
import org.ace.insurance.medical.proposal.MedicalHistory;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.medical.proposal.MedicalProposalAttachment;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPerson;
import org.ace.insurance.medical.proposal.MedicalProposalInsuredPersonAttachment;
import org.ace.insurance.medical.proposal.MedicalSurvey;
import org.ace.insurance.medical.proposal.SurveyType;
import org.ace.insurance.medical.proposal.service.interfaces.IMedicalProposalService;
import org.ace.insurance.medical.surveyAnswer.ResourceQuestionAnswer;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.surveyMember.SurveyMember;
import org.ace.insurance.system.common.surveyTeam.SurveyTeam;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.web.common.DTOValidator;
import org.ace.insurance.web.common.ErrorMessage;
import org.ace.insurance.web.common.ValidationResult;
import org.ace.insurance.web.manage.medical.survey.MedicalHistoryDTO;
import org.ace.insurance.web.manage.medical.survey.MedicalSurveyDTO;
import org.ace.insurance.web.manage.medical.survey.factory.MedicalHistoryDTOFactory;
import org.ace.insurance.web.manage.medical.survey.factory.MedicalSurveyDTOFactory;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/***************************************************************************************
 * @author JH
 * @Date 2014-08-14
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalProposal</code> survey process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "AddNewMedicalSurveyActionBean")
public class AddNewMedicalSurveyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String temporyDir;
	private String medicalProposalId;
	private String remark;
	private String loginBranchId;
	private User user;
	private User responsiblePerson;
	private MedicalProposal medicalProposal;
	// private MedProInsuDTO insuredPersonDTO;
	private MedicalProposalInsuredPerson insuredPerson;
	private MedicalSurvey medicalSurvey;
	private Map<String, String> proposalUploadedFileMap;
	private Map<String, MedicalHistoryDTO> medicalHistoryDTOMap;
	private MedicalHistoryDTO medicalHistoryDTO;
	private boolean isNewMedicalHistory;
	private boolean disablePrintBtn = true;
	private boolean showEntry;
	private Map<String, String> vehFileMap = new HashMap<String, String>();

	private final String PROPOSAL_DIR = "/upload/medical-proposal/";
	private final String reportName = "MedicalSanction";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";
	private List<SurveyMember> surveyMemberList;

	@ManagedProperty(value = "#{ProductProcessService}")
	private IProductProcessService productProcessService;

	public void setProductProcessService(IProductProcessService productProcessService) {
		this.productProcessService = productProcessService;
	}

	@ManagedProperty(value = "#{SurveyQuestionService}")
	private ISurveyQuestionService surveyQuestionService;

	public void setSurveyQuestionService(ISurveyQuestionService surveyQuestionService) {
		this.surveyQuestionService = surveyQuestionService;
	}

	@ManagedProperty(value = "#{MedicalProposalService}")
	private IMedicalProposalService medicalProposalService;

	public void setMedicalProposalService(IMedicalProposalService medicalProposalService) {
		this.medicalProposalService = medicalProposalService;
	}

	@ManagedProperty(value = "#{MedicalSurveyValidator}")
	private DTOValidator<MedicalSurveyDTO> medicalSurveyValidator;

	public void setMedicalSurveyValidator(DTOValidator<MedicalSurveyDTO> medicalSurveyValidator) {
		this.medicalSurveyValidator = medicalSurveyValidator;
	}

	@ManagedProperty(value = "#{MedicalHistoryValidator}")
	private DTOValidator<MedicalHistoryDTO> medicalHistoryValidator;

	public void setMedicalHistoryValidator(DTOValidator<MedicalHistoryDTO> medicalHistoryValidator) {
		this.medicalHistoryValidator = medicalHistoryValidator;
	}

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		loginBranchId = user.getLoginBranch().getId();
		medicalSurvey = (MedicalSurvey) getParam("medicalSurvey");
	}

	@PreDestroy
	public void destroy() {
		removeParam("medicalSurvey");
	}

	private boolean isFinished() {
		if (medicalSurvey == null) {
			return true;
		} else {

			if (workFlowService.findWorkFlowByRefNo(medicalProposalId, WorkflowTask.SURVEY) == null)
				return true;
			else
				return false;
		}
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		/* Create temp directory for upload */
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";
		medicalSurvey.setSurveyDate(new Date());
		creatNewMedicalHistory();
		medicalHistoryDTOMap = new LinkedHashMap<String, MedicalHistoryDTO>();
		medicalProposal = medicalSurvey.getMedicalProposal();
		proposalUploadedFileMap = new HashMap<String, String>();
		medicalProposalId = medicalSurvey.getMedicalProposal().getId();
		String srcPath = getUploadPath() + PROPOSAL_DIR + medicalProposalId;
		String destPath = getUploadPath() + temporyDir + medicalProposalId;
		try {
			FileHandler.copyDirectory(srcPath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String filePath = null;
		for (MedicalProposalAttachment attach : medicalProposal.getAttachmentList()) {
			filePath = attach.getFilePath();
			filePath = filePath.replaceAll("/upload/cargo-proposal/", temporyDir);
			attach.setFilePath(filePath);
		}
		for (MedicalProposalInsuredPerson medicalProposalInsu : medicalProposal.getMedicalProposalInsuredPersonList()) {
			for (MedicalProposalInsuredPersonAttachment attach : medicalProposalInsu.getAttachmentList()) {
				filePath = attach.getFilePath();
				filePath = filePath.replaceAll("/upload/cargo-proposal/", temporyDir);
				attach.setFilePath(filePath);
			}
		}
		for (MedicalProposalAttachment att : medicalProposal.getAttachmentList()) {
			proposalUploadedFileMap.put(att.getName(), att.getFilePath());
		}
		for (MedicalProposalInsuredPerson insuPerson : medicalProposal.getMedicalProposalInsuredPersonList()) {
			for (MedicalProposalInsuredPersonAttachment insuAtt : insuPerson.getAttachmentList()) {
				vehFileMap.put(insuAtt.getName(), insuAtt.getFilePath());
			}
			insuPerson.getSurveyQuestionAnswerList().clear();
			List<ProductProcessQuestionLink> ppQueLinkList = surveyQuestionService.findProductProcessQuestionLinkList(insuPerson.getProduct().getId(),
					ProductProcessIDConfig.getProposalProcessId(), null);
			for (ProductProcessQuestionLink ppQuesLink : ppQueLinkList) {
				SurveyQuestionAnswer surveyAnswerDTO = new SurveyQuestionAnswer(ppQuesLink);
				surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_UNDERWRITING_SURVEY);
				insuPerson.getSurveyQuestionAnswerList().add(surveyAnswerDTO);
			}

			if (isFinished()) {
				disablePrintBtn = false;
			}
		}
	}

	/***************** Proposal Attachment ********************/
	public void handleProposalAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!proposalUploadedFileMap.containsKey(fileName)) {
			String filePath = temporyDir + medicalProposalId + "/" + fileName;
			proposalUploadedFileMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void removeProposalUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			proposalUploadedFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (proposalUploadedFileMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***************** Proposal Attachment End ********************/

	/***************** InsuredPerson Attachment ********************/
	public void prepareInsuPersonAttachment(MedicalProposalInsuredPerson insuredPerson) {
		this.insuredPerson = insuredPerson;
		this.showEntry = true;
	}

	public void handleInsurePersonAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!vehFileMap.containsKey(fileName)) {
			String filePath = temporyDir + medicalProposalId + "/" + insuredPerson.getId() + "/" + fileName;
			vehFileMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void removeInsuPersonUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			vehFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String path = temporyDir + medicalProposalId + "/" + insuredPerson.getId();
			if (vehFileMap.isEmpty() && proposalUploadedFileMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***************** InsuredPerson Attachment End ********************/
	/***************** Survey Answer ***********************/
	public void changeResourceQuestion(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = this.insuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswer resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}
		int index = resourceQuestionAnswerList.indexOf(surveyQuestionAnswer.getSelectResQuesAns());
		ResourceQuestionAnswer selectedResourceQAns = resourceQuestionAnswerList.get(index);
		selectedResourceQAns.setValue(1);
		surveyQuestionAnswer.setSelectResQuesAns(selectedResourceQAns);
	}

	public void changeResourceQuestionList(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = this.insuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswer resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}
		for (ResourceQuestionAnswer resourceQuestionAnswer : surveyQuestionAnswer.getSelectResQuesAnsList()) {
			int index = resourceQuestionAnswerList.indexOf(resourceQuestionAnswer);
			ResourceQuestionAnswer selectedResourceQAns = resourceQuestionAnswerList.get(index);
			selectedResourceQAns.setValue(1);
			surveyQuestionAnswer.setSelectResQuesAns(selectedResourceQAns);
		}
	}

	public void changeBooleanValue(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = this.insuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		ResourceQuestionAnswer resourceQuestionAnswer = resourceQuestionAnswerList.get(0);
		resourceQuestionAnswer.setName(surveyQuestionAnswer.getTureLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswer.setValue(1);
		} else {
			resourceQuestionAnswer.setValue(0);
		}
		resourceQuestionAnswer = resourceQuestionAnswerList.get(1);
		resourceQuestionAnswer.setName(surveyQuestionAnswer.getFalseLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswer.setValue(0);
		} else {
			resourceQuestionAnswer.setValue(1);
		}
	}

	public void changeDate(SelectEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswer surveyQuestionAnswer = this.insuredPerson.getSurveyQuestionAnswerList().get(rowIndex);
		List<ResourceQuestionAnswer> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(surveyQuestionAnswer.getAnswerDate()));
	}

	public void resetDate(SurveyQuestionAnswer surveyQuestionAnswer) {
		surveyQuestionAnswer.setAnswerDate(null);
		surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null);
	}

	public void addSurveyQuestion() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('questionDialog').hide();");
	}

	/***************** Survey Answer End ***********************/
	public String onFlowProcess(FlowEvent event) {
		boolean valid = true;
		return valid ? event.getNewStep() : event.getOldStep();
	}

	public void openTemplateDialog() {
		putParam("medicalProposal", medicalProposal);
		putParam("workFlowList", getWorkFlowList());
		openMedicalProposalInfoTemplate();
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.responsiblePerson = user;
	}

	private ReferenceType getReferenceType() {
		ReferenceType referenceType = null;
		switch (medicalProposal.getHealthType()) {
			case CRITICALILLNESS:
				referenceType = ReferenceType.CRITICAL_ILLNESS;
				break;
			case HEALTH:
				referenceType = ReferenceType.HEALTH;
				break;
			case MICROHEALTH:
				referenceType = ReferenceType.MICRO_HEALTH;
				break;
			default:
				break;
		}
		return referenceType;
	}

	private boolean validMedicalSurvey() {
		boolean valid = true;
		String formID = "surveyEntryForm";
		if (medicalSurvey.getSurveyMemberList() == null || medicalSurvey.getSurveyMemberList().size() <= 0) {
			addErrorMessage(formID + ":surveyTeamMember", UIInput.REQUIRED_MESSAGE_ID);
			valid = false;
		}
		return valid;
	}

	public String addNewSurvey() {
		String res = null;
		try {
			loadAllSurveyMember();
			medicalSurvey.setRemark(remark);
			loadAttachment();
			loadMedicalHistory();
			ReferenceType referenceType = getReferenceType();
			WorkflowTask workflowTask = WorkflowTask.APPROVAL;
			WorkFlowDTO workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), loginBranchId, remark, workflowTask, referenceType, TransactionType.UNDERWRITING, user,
					responsiblePerson);
			medicalProposalService.addNewSurvey(medicalSurvey, workFlowDTO);
			ExternalContext extContext = getFacesContext().getExternalContext();
			extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.SURVEY_PROCESS_SUCCESS);
			extContext.getSessionMap().put(Constants.PROPOSAL_NO, medicalProposal.getProposalNo());
			res = "dashboard";
			moveUploadedFiles();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return res;
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + medicalProposalId, PROPOSAL_DIR + medicalProposalId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadAttachment() {
		medicalProposal.getAttachmentList().clear();
		for (String fileName : proposalUploadedFileMap.keySet()) {
			String filePath = PROPOSAL_DIR + medicalProposalId + "/" + fileName;
			medicalProposal.addAttachment(new MedicalProposalAttachment(fileName, filePath));
		}
		for (MedicalProposalInsuredPerson insuDTO : medicalProposal.getMedicalProposalInsuredPersonList()) {
			insuDTO.getAttachmentList().clear();
			for (String fileName : vehFileMap.keySet()) {
				String filePath = PROPOSAL_DIR + medicalProposalId + "/" + insuDTO.getId() + "/" + fileName;
				insuDTO.addAttachment(new MedicalProposalInsuredPersonAttachment(fileName, filePath));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void returnSurveyTeamList(SelectEvent selectEvent) {
		surveyMemberList = new ArrayList<>();
		List<SurveyTeam> surveyTeamList = (List<SurveyTeam>) selectEvent.getObject();
		for (SurveyTeam surveyTeam : surveyTeamList) {
			for (SurveyMember surveyMember : surveyTeam.getSurveyMemberList()) {
				surveyMember.setInclude(true);
				surveyMemberList.add(surveyMember);
			}
		}
	}

	private void loadAllSurveyMember() {
		List<SurveyMember> surMemberList = new ArrayList<>();
		if (surveyMemberList != null)
			for (SurveyMember surveyMember : surveyMemberList) {
				if (surveyMember.isInclude()) {
					surMemberList.add(surveyMember);
				}
			}
		medicalSurvey.setSurveyMemberList(surMemberList);
	}

	public void saveMedicalHistory() {
		ValidationResult result = medicalHistoryValidator.validate(medicalHistoryDTO);
		if (result.isVerified()) {
			medicalHistoryDTOMap.put(medicalHistoryDTO.getTempId(), medicalHistoryDTO);
			creatNewMedicalHistory();
		} else {
			for (ErrorMessage message : result.getErrorMeesages()) {
				addErrorMessage(message.getId(), message.getErrorcode(), message.getParams());
			}
		}
	}

	public void prepareEditMedicalHistory(MedicalHistoryDTO medicalHistoryDTO) {
		this.medicalHistoryDTO = new MedicalHistoryDTO(medicalHistoryDTO);
		isNewMedicalHistory = false;
	}

	public void deleteMedicalHistory(MedicalHistoryDTO medicalHistoryDTO) {
		medicalHistoryDTOMap.remove(medicalHistoryDTO.getTempId());
		creatNewMedicalHistory();
	}

	public void creatNewMedicalHistory() {
		medicalHistoryDTO = new MedicalHistoryDTO();
		isNewMedicalHistory = true;
	}

	public void selectUser() {
		selectUser(WorkflowTask.APPROVAL, WorkFlowType.MEDICAL_INSURANCE, TransactionType.UNDERWRITING, loginBranchId, null);
	}

	public MedicalSurvey changeDTODataToInstance() {
		return MedicalSurveyDTOFactory.getMedicalSurvey(null);
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

	public List<String> getProposalUploadedFileList() {
		return new ArrayList<String>(proposalUploadedFileMap.values());
	}

	public List<String> getPersonUploadedFileList() {
		return new ArrayList<String>(vehFileMap.values());
	}

	public MedicalProposal getMedicalProposal() {
		return medicalProposal;
	}

	public MedicalSurvey getMedicalSurvey() {
		return medicalSurvey;
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId());
	}

	public MedicalProposalInsuredPerson getInsuredPerson() {
		return insuredPerson;
	}

	public void setInsuredPerson(MedicalProposalInsuredPerson insuredPerson) {
		this.insuredPerson = insuredPerson;
	}

	public MedicalHistoryDTO getMedicalHistoryDTO() {
		return medicalHistoryDTO;
	}

	public void setMedicalHistoryDTO(MedicalHistoryDTO medicalHistoryDTO) {
		this.medicalHistoryDTO = medicalHistoryDTO;
	}

	public void returnICD10(SelectEvent event) {
		ICD10 icd10 = (ICD10) event.getObject();
		medicalHistoryDTO.setIcd10(icd10);
	}

	public void returnHospital(SelectEvent event) {
		Hospital hospital = (Hospital) event.getObject();
		medicalHistoryDTO.setHospital(hospital);
	}

	public void loadMedicalHistory() {
		for (MedicalHistoryDTO medicalHistoryDTO : medicalHistoryDTOMap.values()) {
			MedicalHistory medicalHistory = MedicalHistoryDTOFactory.getMedicalHistory(medicalHistoryDTO);
			medicalSurvey.addMedicalHistory(medicalHistory);
			medicalProposal.addMedicalHistory(medicalHistory);
		}
	}

	public List<MedicalHistoryDTO> getMedicalHistoryDTOList() {
		return new ArrayList<MedicalHistoryDTO>(medicalHistoryDTOMap.values());
	}

	public boolean isNewMedicalHistory() {
		return isNewMedicalHistory;
	}

	public boolean isDisablePrintBtn() {
		return disablePrintBtn;
	}

	public void setDisablePrintBtn(boolean disablePrintBtn) {
		this.disablePrintBtn = disablePrintBtn;
	}

	public int getPaymentTerm() {
		return medicalProposal.getPaymentTerm();
	}

	public List<SurveyMember> getSurveyMemberList() {
		return surveyMemberList;
	}

	public boolean isShowEntry() {
		return showEntry;
	}
}