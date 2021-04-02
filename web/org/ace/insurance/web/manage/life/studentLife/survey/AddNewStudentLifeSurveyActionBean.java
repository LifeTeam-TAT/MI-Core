package org.ace.insurance.web.manage.life.studentLife.survey;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.context.ExternalContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.ProductProcessIDConfig;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.proposal.ClassificationOfHealth;
import org.ace.insurance.life.proposal.InsuredPersonAttachment;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeProposalAttachment;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.medical.productprocess.ProductProcessCriteriaDTO;
import org.ace.insurance.medical.productprocess.StudentAgeType;
import org.ace.insurance.medical.proposal.SurveyType;
import org.ace.insurance.medical.surveyAnswer.SurveyQuestionAnswer;
import org.ace.insurance.medical.surveyquestion.ProductProcessQuestionLink;
import org.ace.insurance.medical.surveyquestion.service.interfaces.ISurveyQuestionService;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.township.Township;
import org.ace.insurance.user.User;
import org.ace.insurance.web.common.DocumentBuilder;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.manage.medical.survey.ResourceQuestionAnswerDTO;
import org.ace.insurance.web.manage.medical.survey.SurveyQuestionAnswerDTO;
import org.ace.insurance.web.manage.medical.survey.factory.SurveyQuestionAnswerDTOFactroy;
import org.ace.insurance.web.util.FileHandler;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@ViewScoped
@ManagedBean(name = "AddNewStudentLifeSurveyActionBean")
public class AddNewStudentLifeSurveyActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workFlowService;

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@ManagedProperty(value = "#{SurveyQuestionService}")
	private ISurveyQuestionService surveyQuestionService;

	public void setSurveyQuestionService(ISurveyQuestionService surveyQuestionService) {
		this.surveyQuestionService = surveyQuestionService;
	}

	@ManagedProperty(value = "#{LifeProposalService}")
	private ILifeProposalService lifeProposalService;

	public void setLifeProposalService(ILifeProposalService lifeProposalService) {
		this.lifeProposalService = lifeProposalService;
	}

	private User user;
	private LifeSurvey lifeSurvey;
	private Map<String, String> proposalUploadedFileMap;
	private Map<String, String> customerMedChkUpAttMap;
	private Map<String, String> personAttchmentMap;
	private String remark;
	private User responsiblePerson;
	private final String PROPOSAL_DIR = "/upload/life-proposal/";
	private String temporyDir;
	private String lifeProposalId;
	private ProposalInsuredPerson proposalInsuredPerson;
	private List<SurveyQuestionAnswerDTO> surveyQuestionAnswerDTOList;
	private boolean isMedicalCheckUpAtt;
	private boolean isInsuAtt;
	private boolean isInsuPerson;
	private boolean isPrintPreview;

	private void initializeInjection() {
		user = (user == null) ? (User) getParam(Constants.LOGIN_USER) : user;
		lifeSurvey = (lifeSurvey == null) ? (LifeSurvey) getParam("lifeSurvey") : lifeSurvey;
	}

	@PreDestroy
	public void destroy() {
		removeParam("lifeSurvey");
		removeParam("lifeProposal");
	}

	@PostConstruct
	public void init() {
		isPrintPreview = false;
		initializeInjection();
		temporyDir = "/temp/" + System.currentTimeMillis() + "/";
		LifeProposal lifeProposal = lifeSurvey.getLifeProposal();
		this.lifeProposalId = lifeProposal.getId();
		this.proposalInsuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
		createNewAttachMap();
		changedTempFile(lifeProposal);
		saveIntoTempFolder(lifeProposal);
	}

	private void createNewAttachMap() {
		proposalUploadedFileMap = new HashMap<String, String>();
		personAttchmentMap = new HashMap<String, String>();
		customerMedChkUpAttMap = new HashMap<String, String>();
		surveyQuestionAnswerDTOList = new ArrayList<SurveyQuestionAnswerDTO>();
	}

	// when file remove and refresh page
	private void changedTempFile(LifeProposal lifeProposal) {
		List<LifeProposalAttachment> listProposalAttList = new ArrayList<LifeProposalAttachment>();
		List<Attachment> attList = new ArrayList<Attachment>();
		List<InsuredPersonAttachment> insuAttList = new ArrayList<InsuredPersonAttachment>();
		for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
			if (attach.getFilePath().indexOf("temp") != -1) {
				String fileName = attach.getName();
				String filePath = PROPOSAL_DIR + lifeProposalId + "/" + fileName;
				listProposalAttList.add(new LifeProposalAttachment(fileName, filePath));
			}
		}
		if (listProposalAttList.size() > 0) {
			lifeProposal.setAttachmentList(new ArrayList<LifeProposalAttachment>());
			lifeProposal.getAttachmentList().addAll(listProposalAttList);
		}
		for (Attachment attach : lifeProposal.getCustomerMedicalCheckUpAttachmentList()) {
			if (attach.getFilePath().indexOf("temp") != -1) {
				String fileName = attach.getName();
				String filePath = PROPOSAL_DIR + lifeProposalId + "/" + fileName;
				attList.add(new Attachment(fileName, filePath));
			}
		}
		if (attList.size() > 0) {
			lifeProposal.setCustomerMedicalCheckUpAttachmentList(new ArrayList<Attachment>());
			lifeProposal.getCustomerMedicalCheckUpAttachmentList().addAll(attList);
		}
		for (InsuredPersonAttachment attach : lifeProposal.getProposalInsuredPersonList().get(0).getAttachmentList()) {
			if (attach.getFilePath().indexOf("temp") != -1) {
				String fileName = attach.getName();
				String filePath = PROPOSAL_DIR + lifeProposalId + "/" + proposalInsuredPerson.getId() + "/" + fileName;
				insuAttList.add(new InsuredPersonAttachment(fileName, filePath));
			}
		}
		if (insuAttList.size() > 0) {
			lifeProposal.getProposalInsuredPersonList().get(0).setAttachmentList(new ArrayList<InsuredPersonAttachment>());
			lifeProposal.getProposalInsuredPersonList().get(0).getAttachmentList().addAll(insuAttList);
		}
	}

	private void saveIntoTempFolder(LifeProposal lifeProposal) {
		if (lifeProposal.getAttachmentList().size() > 0 || lifeProposal.getCustomerMedicalCheckUpAttachmentList().size() > 0
				|| lifeProposal.getProposalInsuredPersonList().get(0).getAttachmentList().size() > 0
				|| lifeProposal.getProposalInsuredPersonList().get(0).getBirthCertificateAttachment().size() > 0) {
			String srcPath = getUploadPath() + PROPOSAL_DIR + lifeProposalId;
			String destPath = getUploadPath() + temporyDir + lifeProposalId;
			try {
				FileHandler.copyDirectory(srcPath, destPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String filePath = null;
			for (LifeProposalAttachment attach : lifeProposal.getAttachmentList()) {
				filePath = attach.getFilePath();
				filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
				attach.setFilePath(filePath);
				proposalUploadedFileMap.put(attach.getName(), attach.getFilePath());
			}
			for (InsuredPersonAttachment attach : proposalInsuredPerson.getAttachmentList()) {
				filePath = attach.getFilePath();
				filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
				attach.setFilePath(filePath);
				personAttchmentMap.put(attach.getName(), attach.getFilePath());
			}
			for (Attachment att : lifeProposal.getCustomerMedicalCheckUpAttachmentList()) {
				filePath = att.getFilePath();
				filePath = filePath.replaceAll("/upload/life-proposal/", temporyDir);
				att.setFilePath(filePath);
				customerMedChkUpAttMap.put(att.getName(), att.getFilePath());
			}
		}
	}

	public boolean isMedicalCheckUpAtt() {
		return isMedicalCheckUpAtt;
	}

	public boolean isInsuAtt() {
		return isInsuAtt;
	}

	public LifeSurvey getSurvey() {
		return lifeSurvey;
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

	public List<String> getCustomerMedicalCheckUpUploadedFileList() {
		return new ArrayList<String>(customerMedChkUpAttMap.values());
	}

	public List<String> getPersonUploadedFileList() {
		return new ArrayList<String>(personAttchmentMap.values());
	}

	public void handleProposalAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!proposalUploadedFileMap.containsKey(fileName)) {
			String filePath = temporyDir + lifeProposalId + "/" + fileName;
			proposalUploadedFileMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void handleInsurePersonAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!personAttchmentMap.containsKey(fileName)) {
			String filePath = temporyDir + lifeProposalId + "/" + proposalInsuredPerson.getId() + "/" + fileName;
			personAttchmentMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void handleCustomerAttachment(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		String fileName = uploadedFile.getFileName().replaceAll("\\s", "_");
		if (!customerMedChkUpAttMap.containsKey(fileName)) {
			String filePath = temporyDir + lifeProposalId + "/" + fileName;
			customerMedChkUpAttMap.put(fileName, filePath);
			createFile(new File(getUploadPath() + filePath), uploadedFile.getContents());
		}
	}

	public void returnHospital(SelectEvent event) {
		Hospital hospital = (Hospital) event.getObject();
		lifeSurvey.setHospital(hospital);
		lifeSurvey.setAddress(hospital.getAddress().getPermanentAddress());
		lifeSurvey.setTownship(hospital.getAddress().getTownship());
	}

	public void preparePersonAttachment() {
		isInsuAtt = true;
	}

	public void prepareMedChkUpAttachment() {
		isMedicalCheckUpAtt = true;
	}

	public void preparePersonAttachmentDetails(ProposalInsuredPerson proposalPerson) {
		this.proposalInsuredPerson = proposalPerson;
	}

	private void moveUploadedFiles() {
		try {
			FileHandler.moveFiles(getUploadPath(), temporyDir + lifeProposalId, PROPOSAL_DIR + lifeProposalId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<WorkFlowHistory> getWorkFlowList() {
		return workFlowService.findWorkFlowHistoryByRefNo(lifeSurvey.getLifeProposal().getId());
	}

	private void loadAttachment() {
		if (lifeSurvey.getLifeProposal().getAttachmentList().size() > 0) {
			lifeSurvey.getLifeProposal().setAttachmentList(new ArrayList<LifeProposalAttachment>());
		}
		if (lifeSurvey.getLifeProposal().getCustomerMedicalCheckUpAttachmentList().size() > 0) {
			lifeSurvey.getLifeProposal().setCustomerMedicalCheckUpAttachmentList(new ArrayList<Attachment>());
		}
		if (lifeSurvey.getLifeProposal().getProposalInsuredPersonList().get(0).getAttachmentList().size() > 0) {
			lifeSurvey.getLifeProposal().getProposalInsuredPersonList().get(0).setAttachmentList(new ArrayList<InsuredPersonAttachment>());
		}
		LifeProposal lifeProposal = lifeSurvey.getLifeProposal();
		for (String fileName : proposalUploadedFileMap.keySet()) {
			String filePath = PROPOSAL_DIR + lifeProposalId + "/" + fileName;
			lifeProposal.addAttachment(new LifeProposalAttachment(fileName, filePath));
		}
		for (String fileName : customerMedChkUpAttMap.keySet()) {
			String filePath = PROPOSAL_DIR + lifeProposalId + "/" + fileName;
			lifeProposal.addCustomerMedicalChkUpAttachment(new Attachment(fileName, filePath));
		}
		for (String fileName : personAttchmentMap.keySet()) {
			String filePath = PROPOSAL_DIR + lifeProposalId + "/" + proposalInsuredPerson.getId() + "/" + fileName;
			lifeProposal.getProposalInsuredPersonList().get(0).addAttachment(new InsuredPersonAttachment(fileName, filePath));
		}
	}

	public void addNewSurvey() {
		loadAttachment();
		try {
			if (validateMedicalCheckUpAttach()) {
				WorkflowTask workflowTask = WorkflowTask.APPROVAL;
				ReferenceType referenceType = ReferenceType.STUDENT_LIFE;
				WorkFlowDTO workFlowDTO = new WorkFlowDTO(lifeSurvey.getLifeProposal().getId(), lifeSurvey.getLifeProposal().getBranch().getId(), remark, workflowTask,
						referenceType, TransactionType.UNDERWRITING, user, responsiblePerson);
				lifeProposalService.addNewSurvey(lifeSurvey, workFlowDTO);
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.SURVEY_PROCESS_SUCCESS);
				extContext.getSessionMap().put(Constants.PROPOSAL_NO, lifeSurvey.getLifeProposal().getProposalNo());
				moveUploadedFiles();
				isPrintPreview = true;
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean validateMedicalCheckUpAttach() {
		boolean valid = true;
		if (lifeSurvey.getLifeProposal().getCustomerClsOfHealth() == null) {
			valid = false;
			addErrorMessage("surveyEntryForm:parentTable", MessageId.CLS_OF_HEALTH_REQUIRED);
		}
		return valid;
	}

	public ClassificationOfHealth[] getClassificationHealthList() {
		return ClassificationOfHealth.values();
	}

	public boolean isEmptyInsuPerAtt() {
		if (personAttchmentMap == null || personAttchmentMap.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isEmptyAtt() {
		if (customerMedChkUpAttMap == null || customerMedChkUpAttMap.isEmpty()) {
			return true;
		}
		return false;
	}

	public void removeInsuPersonUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			personAttchmentMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			String path = temporyDir + lifeProposalId + "/" + proposalInsuredPerson.getId();
			if (personAttchmentMap.isEmpty() && proposalUploadedFileMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeProposalUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			proposalUploadedFileMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (proposalUploadedFileMap.isEmpty() && personAttchmentMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeCustomerMedChkUpUploadedFile(String filePath) {
		try {
			String fileName = getFileName(filePath);
			customerMedChkUpAttMap.remove(fileName);
			FileHandler.forceDelete(new File(getUploadPath() + filePath));
			if (customerMedChkUpAttMap.isEmpty() && personAttchmentMap.isEmpty()) {
				FileHandler.forceDelete(new File(getUploadPath() + temporyDir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final String reportName = "StudentLifeSanction";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getWebRootPath() + pdfDirPath;
	private String fileName = null;

	public String getReportStream() {
		return pdfDirPath + fileName;
	}

	public void generateReport() {
		String customerName = lifeSurvey.getLifeProposal().getCustomerName();
		if (customerName.contains("\\")) {
			customerName = customerName.replace("\\", "");
		}
		if (customerName.contains("/")) {
			customerName = customerName.replace("/", "");
		}
		fileName = "StudentLife_" + customerName + "_Sanction" + ".pdf";
		LifeProposal proposal = lifeSurvey.getLifeProposal();
		DocumentBuilder.generateStudentLifeSanction(proposal, dirPath, fileName);
		PrimeFaces.current().executeScript("PF('pdfDialog').show()");

	}

	public void selectUser() {
		WorkflowTask workflowTask = WorkflowTask.APPROVAL;
		WorkFlowType workFlowType = WorkFlowType.STUDENT_LIFE;
		selectUser(workflowTask, workFlowType);
	}

	public void addSurveyQuestion() {
		if (isInsuPerson) {
			proposalInsuredPerson.getSurveyQuestionAnswerList().clear();
		} else {
			lifeSurvey.getLifeProposal().getCustomerSurveyQuestionAnswerList().clear();
		}
		for (SurveyQuestionAnswerDTO questionDTO : surveyQuestionAnswerDTOList) {
			SurveyQuestionAnswer question = SurveyQuestionAnswerDTOFactroy.getSurveyQuestionAnswer(questionDTO);
			if (isInsuPerson) {
				proposalInsuredPerson.addSurveyQuestionAnswer(question);
			} else {
				lifeSurvey.getLifeProposal().addCustomerSurveyQuestionAnswerList(question);
			}
		}
		surveyQuestionAnswerDTOList = new ArrayList<SurveyQuestionAnswerDTO>();
		PrimeFaces.current().executeScript("PF('questionDialog').hide();");
	}

	public void setProposalInsuredPerson(ProposalInsuredPerson insuredPerson) {
		StudentAgeType studentAgeType = null;
		/* load survey question */
		surveyQuestionAnswerDTOList.clear();
		if (insuredPerson == null) {
			if (lifeSurvey.getLifeProposal().getCustomerSurveyQuestionAnswerList() != null && !lifeSurvey.getLifeProposal().getCustomerSurveyQuestionAnswerList().isEmpty()) {
				for (SurveyQuestionAnswer sqanswer : lifeSurvey.getLifeProposal().getCustomerSurveyQuestionAnswerList()) {
					SurveyQuestionAnswerDTO surveyAnswerDTO = SurveyQuestionAnswerDTOFactroy.getSurveyQuestionAnswerDTO(sqanswer);
					surveyAnswerDTO.setSurveyType(SurveyType.STUDENT_LIFE_CHILD_SURVEY);
					surveyQuestionAnswerDTOList.add(surveyAnswerDTO);
				}
			} else {
				ProductProcessCriteriaDTO dto = new ProductProcessCriteriaDTO();
				dto.setStudentAgeType(StudentAgeType.PARENT_SQ);
				List<ProductProcessQuestionLink> ppQueLinkList = surveyQuestionService.findPProQueByPPId(KeyFactorChecker.getStudentLifeID(),
						ProductProcessIDConfig.getProposalProcessId(), dto);
				for (ProductProcessQuestionLink ppQuesLink : ppQueLinkList) {
					SurveyQuestionAnswerDTO surveyAnswerDTO = new SurveyQuestionAnswerDTO(ppQuesLink);
					surveyAnswerDTO.setSurveyType(SurveyType.STUDENT_LIFE_CHILD_SURVEY);
					surveyQuestionAnswerDTOList.add(surveyAnswerDTO);
				}
			}
			isInsuPerson = false;
		} else {
			this.proposalInsuredPerson = insuredPerson;
			if (proposalInsuredPerson.getSurveyQuestionAnswerList() != null && !proposalInsuredPerson.getSurveyQuestionAnswerList().isEmpty()) {
				for (SurveyQuestionAnswer sqanswer : proposalInsuredPerson.getSurveyQuestionAnswerList()) {
					SurveyQuestionAnswerDTO surveyAnswerDTO = SurveyQuestionAnswerDTOFactroy.getSurveyQuestionAnswerDTO(sqanswer);
					if (surveyAnswerDTO.getResourceQuestionList().size() > 0) {
						surveyAnswerDTO.setTureLabelValue(surveyAnswerDTO.getResourceQuestionList().get(0).getValue() == 1 ? true : false);
					}
					surveyAnswerDTO.setSurveyType(SurveyType.STUDENT_LIFE_CHILD_SURVEY);
					surveyQuestionAnswerDTOList.add(surveyAnswerDTO);
				}
			} else {
				if (insuredPerson.getAgeForNextYear() > 3) {
					studentAgeType = StudentAgeType.ABOVE_3_YEARS;
				} else {
					studentAgeType = StudentAgeType.UNDER_3_YEARS;
				}
				ProductProcessCriteriaDTO dto = new ProductProcessCriteriaDTO();
				dto.setStudentAgeType(studentAgeType);
				List<ProductProcessQuestionLink> ppQueLinkList = surveyQuestionService.findPProQueByPPId(KeyFactorChecker.getStudentLifeID(),
						ProductProcessIDConfig.getProposalProcessId(), dto);
				for (ProductProcessQuestionLink ppQuesLink : ppQueLinkList) {
					SurveyQuestionAnswerDTO surveyAnswerDTO = new SurveyQuestionAnswerDTO(ppQuesLink);
					surveyAnswerDTO.setSurveyType(SurveyType.STUDENT_LIFE_CHILD_SURVEY);
					surveyQuestionAnswerDTOList.add(surveyAnswerDTO);
				}
			}
			isInsuPerson = true;
		}

	}

	public ProposalInsuredPerson getProposalInsuredPerson() {
		return proposalInsuredPerson;
	}

	public List<SurveyQuestionAnswerDTO> getSurveyQuestionAnswerDTOList() {
		return surveyQuestionAnswerDTOList;
	}

	public void changeResourceQuestion(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswerDTO surveyQuestionAnswer = getSurveyQuestionAnswerDTOList().get(rowIndex);
		List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswerDTO resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}
		int index = resourceQuestionAnswerList.indexOf(surveyQuestionAnswer.getSelectedResourceQAnsDTO());
		ResourceQuestionAnswerDTO selectedResourceQAnsDTO = resourceQuestionAnswerList.get(index);
		selectedResourceQAnsDTO.setValue(1);
		surveyQuestionAnswer.setSelectedResourceQAnsDTO(selectedResourceQAnsDTO);
	}

	public void changeResourceQuestionList(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswerDTO surveyQuestionAnswer = getSurveyQuestionAnswerDTOList().get(rowIndex);
		List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		for (ResourceQuestionAnswerDTO resourceQuestionAnswer : resourceQuestionAnswerList) {
			resourceQuestionAnswer.setValue(0);
		}

		for (ResourceQuestionAnswerDTO resourceQuestionAnswer : surveyQuestionAnswer.getSelectedResourceQAnsDTOList()) {
			int index = resourceQuestionAnswerList.indexOf(resourceQuestionAnswer);
			ResourceQuestionAnswerDTO selectedResourceQAnsDTO = resourceQuestionAnswerList.get(index);
			selectedResourceQAnsDTO.setValue(1);
			surveyQuestionAnswer.setSelectedResourceQAnsDTO(selectedResourceQAnsDTO);
		}
	}

	public void changeBooleanValue(AjaxBehaviorEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswerDTO surveyQuestionAnswer = getSurveyQuestionAnswerDTOList().get(rowIndex);
		List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		resourceQuestionAnswerList.get(0).setName(surveyQuestionAnswer.getTureLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswerList.get(0).setValue(1);
		} else {
			resourceQuestionAnswerList.get(0).setValue(0);
		}
		resourceQuestionAnswerList.get(1).setName(surveyQuestionAnswer.getFalseLabel());
		if (surveyQuestionAnswer.isTureLabelValue()) {
			resourceQuestionAnswerList.get(1).setValue(0);
		} else {
			resourceQuestionAnswerList.get(1).setValue(1);
		}
	}

	public void changeDate(SelectEvent e) {
		UIData data = (UIData) e.getComponent().findComponent("questionTable");
		int rowIndex = data.getRowIndex();
		SurveyQuestionAnswerDTO surveyQuestionAnswer = getSurveyQuestionAnswerDTOList().get(rowIndex);
		List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList = surveyQuestionAnswer.getResourceQuestionList();
		resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(surveyQuestionAnswer.getAnswerDate()));
	}

	public void resetDate(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
		surveyQuestionAnswer.setAnswerDate(null);
		surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null);
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		lifeSurvey.setTownship(township);
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

	public void removeHospital() {
		lifeSurvey.setHospital(null);
		lifeSurvey.setAddress(null);
		lifeSurvey.setTownship(null);
	}

	public void openTemplateDialog() {
		putParam("lifeProposal", lifeSurvey.getLifeProposal());
		putParam("workFlowList", getWorkFlowList());
		openStudentLifeInfoTemplate();
	}

	public boolean getIsPrintPreview() {
		return isPrintPreview;
	}

}
