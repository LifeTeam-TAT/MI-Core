package org.ace.insurance.web.manage.medical.renewal.survey;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "MedicalRenewalSurveyActionBean")
public class MedicalRenewalSurveyActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case
	/*
	 * private boolean isNewMedicalHistory; private boolean disablePrintBtn =
	 * true; private boolean showEntry; private String temporyDir; private
	 * String medicalProposalId; private String remark; private String
	 * conditionOfHealth; private User user; private User responsiblePerson;
	 * private MedProDTO medicalProposalDTO; private MedProInsuDTO
	 * insuredPersonDTO; private MedicalSurveyDTO medicalSurveyDTO; private
	 * MedicalHistoryDTO medicalHistoryDTO;
	 * 
	 * private List<SurveyQuestionAnswerDTO> questionAnswerDTOList; private
	 * Map<String, String> proposalUploadedFileMap; private Map<String,
	 * Map<String, String>> perAttMap = null; private Map<String,
	 * MedicalHistoryDTO> medicalHistoryDTOMap; private Map<String, String>
	 * vehFileMap = new HashMap<String, String>();
	 * 
	 * private final String PROPOSAL_DIR = "/upload/medical-proposal/"; private
	 * final String reportName = "MedicalSanction"; private final String
	 * pdfDirPath = "/pdf-report/" + reportName + "/" +
	 * System.currentTimeMillis() + "/"; private final String dirPath =
	 * getWebRootPath() + pdfDirPath; private final String fileName = reportName
	 * + ".pdf"; private HealthType healthType;
	 * 
	 * @ManagedProperty(value = "#{ProductProcessService}") private
	 * IProductProcessService productProcessService;
	 * 
	 * public void setProductProcessService(IProductProcessService
	 * productProcessService) { this.productProcessService =
	 * productProcessService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalProposalService}") private
	 * IMedicalProposalService medicalProposalService;
	 * 
	 * public void setMedicalProposalService(IMedicalProposalService
	 * medicalProposalService) { this.medicalProposalService =
	 * medicalProposalService; }
	 * 
	 * @ManagedProperty(value = "#{AddNewMedicalSurveyFrontService}") private
	 * IAddNewMedicalSurveyFrontService addNewMedicalSurveyFrontService;
	 * 
	 * public void
	 * setAddNewMedicalSurveyFrontService(IAddNewMedicalSurveyFrontService
	 * addNewMedicalSurveyFrontService) { this.addNewMedicalSurveyFrontService =
	 * addNewMedicalSurveyFrontService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalSurveyValidator}") private
	 * DTOValidator<MedicalSurveyDTO> medicalSurveyValidator;
	 * 
	 * public void setMedicalSurveyValidator(DTOValidator<MedicalSurveyDTO>
	 * medicalSurveyValidator) { this.medicalSurveyValidator =
	 * medicalSurveyValidator; }
	 * 
	 * @ManagedProperty(value = "#{MedicalHistoryValidator}") private
	 * DTOValidator<MedicalHistoryDTO> medicalHistoryValidator;
	 * 
	 * public void setMedicalHistoryValidator(DTOValidator<MedicalHistoryDTO>
	 * medicalHistoryValidator) { this.medicalHistoryValidator =
	 * medicalHistoryValidator; }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * @ManagedProperty(value = "#{UserService}") private IUserService
	 * userService;
	 * 
	 * public void setUserService(IUserService userService) { this.userService =
	 * userService; }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalSurveyDTO =
	 * (medicalSurveyDTO == null) ? (MedicalSurveyDTO)
	 * getParam("medicalSurveyDTO") : medicalSurveyDTO; }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalSurveyDTO");
	 * removeParam("medicalProposalDTO"); }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostConstruct public void init() { initializeInjection(); Create temp
	 * directory for upload temporyDir = "/temp/" + System.currentTimeMillis() +
	 * "/"; medicalSurveyDTO.setSurveyDate(new Date());
	 * 
	 * medicalProposalDTO = medicalSurveyDTO.getMedicalProposalDTO(); //
	 * insuredPersonDTO = medicalProposalDTO.getMedProInsuDTOList().get(0);
	 * questionAnswerDTOList = new ArrayList<SurveyQuestionAnswerDTO>();
	 * medicalHistoryDTOMap = new LinkedHashMap<String, MedicalHistoryDTO>();
	 * creatNewMedicalHistory(); proposalUploadedFileMap = new HashMap<String,
	 * String>(); perAttMap = new HashMap<String, Map<String, String>>();
	 * healthType = (HealthType) getParam("WORKFLOWHEALTHTYPE");
	 * 
	 * load surveyQuestion List<ProductProcessQuestionLink> ppQueLinkList =
	 * medicalProposalService.findPProQueByPPId(ProductProcessIDConfig.
	 * getMedicalProductId(), ProductProcessIDConfig.getProposalProcessId()); if
	 * (medicalProposalDTO != null) { for (MedProAttDTO att :
	 * medicalProposalDTO.getAttachmentList()) {
	 * proposalUploadedFileMap.put(att.getName(), att.getFilePath()); } for
	 * (MedProInsuDTO insuDTO : medicalProposalDTO.getMedProInsuDTOList()) {
	 * Map<String, String> personAttMap = new HashMap<String, String>(); for
	 * (MedProInsuAttDTO insuAtt : insuDTO.getAttachmentList()) {
	 * personAttMap.put(insuAtt.getName(), insuAtt.getFilePath()); }
	 * insuDTO.getSurveyQuestionAnsweDTOList().clear(); for
	 * (ProductProcessQuestionLink ppQuesLink : ppQueLinkList) {
	 * SurveyQuestionAnswerDTO surveyAnswerDTO = new
	 * SurveyQuestionAnswerDTO(ppQuesLink);
	 * surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_UNDERWRITING_SURVEY);
	 * insuDTO.getSurveyQuestionAnsweDTOList().add(surveyAnswerDTO); }
	 * perAttMap.put(insuDTO.getId(), personAttMap); } medicalProposalId =
	 * medicalProposalDTO.getId(); }
	 * 
	 * if (questionAnswerDTOList != null && !questionAnswerDTOList.isEmpty()) {
	 * Collections.sort(questionAnswerDTOList); } }
	 * 
	 * public boolean isEmptyAtt() { if (perAttMap.values() != null &&
	 * perAttMap.values().size() != 0) { return false; } return true; }
	 * 
	 * public ClassificationOfHealth[] getClassificationHealthList() { return
	 * ClassificationOfHealth.values(); }
	 * 
	 * public void handleProposalAttachment(FileUploadEvent event) {
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!proposalUploadedFileMap.containsKey(fileName)) { String filePath =
	 * temporyDir + medicalProposalId + "/" + fileName;
	 * proposalUploadedFileMap.put(fileName, filePath); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); } }
	 * 
	 * public String onFlowProcess(FlowEvent event) { boolean valid = true;
	 * return valid ? event.getNewStep() : event.getOldStep(); }
	 * 
	 * public void removeProposalUploadedFile(String filePath) { try { String
	 * fileName = getFileName(filePath);
	 * proposalUploadedFileMap.remove(fileName); FileHandler.forceDelete(new
	 * File(getUploadPath() + filePath)); if (proposalUploadedFileMap.isEmpty()
	 * && perAttMap.isEmpty()) { FileHandler.forceDelete(new
	 * File(getUploadPath() + temporyDir)); } } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void handleInsurePersonAttachment(FileUploadEvent event) {
	 * 
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_");
	 * 
	 * if (!perAttMap.containsKey(fileName)) { String filePath = temporyDir +
	 * medicalProposalId + "/" + insuredPersonDTO.getId() + "/" + fileName;
	 * vehFileMap.put(fileName, filePath);
	 * perAttMap.put(insuredPersonDTO.getId(), vehFileMap); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); }
	 * 
	 * }
	 * 
	 * public List<String> getPersonUploadedFileList() { Map<String, String>
	 * vehFileMap = new HashMap<String, String>(); if (insuredPersonDTO != null)
	 * { vehFileMap = perAttMap.get(insuredPersonDTO.getId()); return new
	 * ArrayList<String>(vehFileMap.values()); } return new ArrayList<String>();
	 * }
	 * 
	 * public void prepareInsuPersonAttachment(MedProInsuDTO insuredPersonDTO) {
	 * this.insuredPersonDTO = insuredPersonDTO; showEntry = true; }
	 * 
	 * public void preparePersonAttachmentDetails(MedProInsuDTO
	 * insuredPersonDTO) { this.insuredPersonDTO = insuredPersonDTO; }
	 * 
	 * public boolean isShowEntry() { return showEntry; }
	 * 
	 * public void setShowEntry(boolean showEntry) { this.showEntry = showEntry;
	 * }
	 * 
	 * public void removeInsuPersonUploadedFile(String filePath) { try { String
	 * fileName = getFileName(filePath); perAttMap.remove(fileName);
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * path = temporyDir + medicalProposalId + "/" + insuredPersonDTO.getId();
	 * if (perAttMap.isEmpty() && proposalUploadedFileMap.isEmpty()) {
	 * FileHandler.forceDelete(new File(getUploadPath() + path)); } } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * public void changeResourceQuestion(AjaxBehaviorEvent e) { UIData data =
	 * (UIData) e.getComponent().findComponent("questionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * this.insuredPersonDTO.getSurveyQuestionAnsweDTOList().get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList(); for
	 * (ResourceQuestionAnswerDTO resourceQuestionAnswer :
	 * resourceQuestionAnswerList) { resourceQuestionAnswer.setValue(0); } int
	 * index = resourceQuestionAnswerList.indexOf(surveyQuestionAnswer.
	 * getSelectedResourceQAnsDTO()); ResourceQuestionAnswerDTO
	 * selectedResourceQAnsDTO = resourceQuestionAnswerList.get(index);
	 * selectedResourceQAnsDTO.setValue(1);
	 * surveyQuestionAnswer.setSelectedResourceQAnsDTO(selectedResourceQAnsDTO);
	 * }
	 * 
	 * public void changeResourceQuestionList(AjaxBehaviorEvent e) { UIData data
	 * = (UIData) e.getComponent().findComponent("questionTable"); int rowIndex
	 * = data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * this.insuredPersonDTO.getSurveyQuestionAnsweDTOList().get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList(); for
	 * (ResourceQuestionAnswerDTO resourceQuestionAnswer :
	 * resourceQuestionAnswerList) { resourceQuestionAnswer.setValue(0); }
	 * 
	 * for (ResourceQuestionAnswerDTO resourceQuestionAnswer :
	 * surveyQuestionAnswer.getSelectedResourceQAnsDTOList()) { int index =
	 * resourceQuestionAnswerList.indexOf(resourceQuestionAnswer);
	 * ResourceQuestionAnswerDTO selectedResourceQAnsDTO =
	 * resourceQuestionAnswerList.get(index);
	 * selectedResourceQAnsDTO.setValue(1);
	 * surveyQuestionAnswer.setSelectedResourceQAnsDTO(selectedResourceQAnsDTO);
	 * } }
	 * 
	 * public void changeBooleanValue(AjaxBehaviorEvent e) { UIData data =
	 * (UIData) e.getComponent().findComponent("questionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * this.insuredPersonDTO.getSurveyQuestionAnsweDTOList().get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList(); ResourceQuestionAnswerDTO
	 * resourceQuestionAnswer = resourceQuestionAnswerList.get(0);
	 * resourceQuestionAnswer.setName(surveyQuestionAnswer.getTureLabel()); if
	 * (surveyQuestionAnswer.isTureLabelValue()) {
	 * resourceQuestionAnswer.setValue(1); } else {
	 * resourceQuestionAnswer.setValue(0); } resourceQuestionAnswer =
	 * resourceQuestionAnswerList.get(1);
	 * resourceQuestionAnswer.setName(surveyQuestionAnswer.getFalseLabel()); if
	 * (surveyQuestionAnswer.isTureLabelValue()) {
	 * resourceQuestionAnswer.setValue(0); } else {
	 * resourceQuestionAnswer.setValue(1); } }
	 * 
	 * public void changeDate(SelectEvent e) { UIData data = (UIData)
	 * e.getComponent().findComponent("questionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * this.insuredPersonDTO.getSurveyQuestionAnsweDTOList().get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList();
	 * resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(
	 * surveyQuestionAnswer.getAnswerDate())); }
	 * 
	 * public void resetDate(SurveyQuestionAnswerDTO surveyQuestionAnswer) {
	 * surveyQuestionAnswer.setAnswerDate(null);
	 * surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public String addNewSurvey() { String res = null; try {
	 * medicalSurveyDTO.setRemark(remark);
	 * medicalSurveyDTO.setConditionOfHealth(conditionOfHealth);
	 * ValidationResult result =
	 * medicalSurveyValidator.validate(medicalSurveyDTO); if
	 * (result.isVerified()) { loadAttachment(); loadMedicalHistory();
	 * WorkflowTask workflowTask = WorkflowTask.RENEWAL_APPROVAL; WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO(medicalProposalDTO.getId(), remark,
	 * workflowTask, ReferenceType.MEDICAL_RENEWAL_PROPOSAL, user,
	 * responsiblePerson);
	 * addNewMedicalSurveyFrontService.addNewSurvey(changeDTODataToInstance(),
	 * workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.SURVEY_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalSurveyDTO.getMedicalProposalDTO().getProposalNo());
	 * moveUploadedFiles(); disablePrintBtn = false; res = ""; } else { for
	 * (ErrorMessage message : result.getErrorMeesages()) {
	 * addErrorMessage(message.getId(), message.getErrorcode(),
	 * message.getParams()); } } } catch (SystemException ex) {
	 * handelSysException(ex); } return res; }
	 * 
	 * private void loadAttachment() { for (MedProInsuDTO insuDTO :
	 * medicalSurveyDTO.getMedicalProposalDTO().getMedProInsuDTOList()) { if
	 * (medicalSurveyDTO.getMedicalProposalDTO().getAttachmentList().size() > 0)
	 * { medicalSurveyDTO.getMedicalProposalDTO().setAttachmentList(new
	 * ArrayList<MedProAttDTO>()); insuDTO.setAttachmentList(new
	 * ArrayList<MedProInsuAttDTO>()); }
	 * 
	 * for (String fileName : proposalUploadedFileMap.keySet()) { String
	 * filePath = PROPOSAL_DIR + medicalProposalId + "/" + fileName;
	 * medicalProposalDTO.addAttachment(new MedProAttDTO(fileName, filePath)); }
	 * if (perAttMap.keySet() != null) { for (Map<String, String> fileName :
	 * perAttMap.values()) { for (String name : fileName.keySet()) { String
	 * filePath = PROPOSAL_DIR + medicalProposalId + "/" + insuDTO.getId() + "/"
	 * + name; insuDTO.addInsuredPersonAttachment(new MedProInsuAttDTO(name,
	 * filePath)); }
	 * 
	 * } } } }
	 * 
	 * private void moveUploadedFiles() { try {
	 * FileHandler.moveFiles(getUploadPath(), temporyDir + medicalProposalId,
	 * PROPOSAL_DIR + medicalProposalId); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public String getReportStream() { return pdfDirPath + fileName; }
	 * 
	 * public void generateReport() {
	 * MedicalUnderwritingDocFactory.generateMedicalSanction(
	 * changeDTODataToInstance(), dirPath, fileName, healthType); }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_APPROVAL,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public MedicalSurvey changeDTODataToInstance() { return
	 * MedicalSurveyDTOFactory.getMedicalSurvey(medicalSurveyDTO); }
	 * 
	 * public void addSurveyQuestion() { RequestContext context =
	 * RequestContext.getCurrentInstance();
	 * context.execute("PF('questionDialog').hide();"); }
	 * 
	 * // setter and getter
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public List<String> getProposalUploadedFileList() { return new
	 * ArrayList<String>(proposalUploadedFileMap.values()); }
	 * 
	 * public List<SurveyQuestionAnswerDTO> getQuestionAnswerDTOList() { return
	 * questionAnswerDTOList; }
	 * 
	 * public void setQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO>
	 * questionAnswerDTOList) { this.questionAnswerDTOList =
	 * questionAnswerDTOList; }
	 * 
	 * public MedicalSurveyDTO getMedicalSurveyDTO() { return medicalSurveyDTO;
	 * }
	 * 
	 * public void setMedicalSurveyDTO(MedicalSurveyDTO medicalSurveyDTO) {
	 * this.medicalSurveyDTO = medicalSurveyDTO; }
	 * 
	 * public MedProDTO getMedicalProposalDTO() { return medicalProposalDTO; }
	 * 
	 * public void setMedicalProposalDTO(MedProDTO medicalProposalDTO) {
	 * this.medicalProposalDTO = medicalProposalDTO; }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalSurveyDTO.
	 * getMedicalProposalDTO().getId()); }
	 * 
	 * public MedProInsuDTO getInsuredPersonDTO() { return insuredPersonDTO; }
	 * 
	 * public void setInsuredPersonDTO(MedProInsuDTO insuredPersonDTO) {
	 * this.insuredPersonDTO = insuredPersonDTO; }
	 * 
	 * public MedicalHistoryDTO getMedicalHistoryDTO() { return
	 * medicalHistoryDTO; }
	 * 
	 * public void setMedicalHistoryDTO(MedicalHistoryDTO medicalHistoryDTO) {
	 * this.medicalHistoryDTO = medicalHistoryDTO; }
	 * 
	 * public void returnICD10(SelectEvent event) { ICD10 icd10 = (ICD10)
	 * event.getObject(); medicalHistoryDTO.setIcd10(icd10); }
	 * 
	 * public void returnHospital(SelectEvent event) { Hospital hospital =
	 * (Hospital) event.getObject(); medicalHistoryDTO.setHospital(hospital); }
	 * 
	 * public void loadMedicalHistory() { for (MedicalHistoryDTO
	 * medicalHistoryDTO : medicalHistoryDTOMap.values()) {
	 * medicalSurveyDTO.addMedicalHistoryDTO(medicalHistoryDTO); } }
	 * 
	 * public List<MedicalHistoryDTO> getMedicalHistoryDTOList() { return new
	 * ArrayList<MedicalHistoryDTO>(medicalHistoryDTOMap.values()); }
	 * 
	 * public void saveMedicalHistory() { ValidationResult result =
	 * medicalHistoryValidator.validate(medicalHistoryDTO); if
	 * (result.isVerified()) {
	 * medicalHistoryDTOMap.put(medicalHistoryDTO.getTempId(),
	 * medicalHistoryDTO); creatNewMedicalHistory(); } else { for (ErrorMessage
	 * message : result.getErrorMeesages()) { addErrorMessage(message.getId(),
	 * message.getErrorcode(), message.getParams()); } }
	 * 
	 * }
	 * 
	 * public void prepareEditMedicalHistory(MedicalHistoryDTO
	 * medicalHistoryDTO) { this.medicalHistoryDTO = new
	 * MedicalHistoryDTO(medicalHistoryDTO); isNewMedicalHistory = false;
	 * 
	 * }
	 * 
	 * public void deleteMedicalHistory(MedicalHistoryDTO medicalHistoryDTO) {
	 * medicalHistoryDTOMap.remove(medicalHistoryDTO.getTempId());
	 * creatNewMedicalHistory(); }
	 * 
	 * public void creatNewMedicalHistory() { medicalHistoryDTO = new
	 * MedicalHistoryDTO(); isNewMedicalHistory = true;
	 * 
	 * }
	 * 
	 * public boolean isNewMedicalHistory() { return isNewMedicalHistory; }
	 * 
	 * public String getConditionOfHealth() { return conditionOfHealth; }
	 * 
	 * public void setConditionOfHealth(String conditionOfHealth) {
	 * this.conditionOfHealth = conditionOfHealth; }
	 * 
	 * public boolean isDisablePrintBtn() { return disablePrintBtn; }
	 * 
	 * public void setDisablePrintBtn(boolean disablePrintBtn) {
	 * this.disablePrintBtn = disablePrintBtn; }
	 * 
	 * public List<String> getPersonAttachmentList() { Map<String, String>
	 * vehFileMap = new HashMap<String, String>(); if (insuredPersonDTO != null)
	 * { vehFileMap = perAttMap.get(insuredPersonDTO.getId()); return new
	 * ArrayList<String>(vehFileMap.values()); } return new ArrayList<String>();
	 * }
	 * 
	 * public void preparePersonAttachment(MedProInsuDTO insuredPersonDTO) {
	 * this.insuredPersonDTO = insuredPersonDTO; showEntry = true; if
	 * (!perAttMap.containsKey(insuredPersonDTO.getId())) {
	 * perAttMap.put(insuredPersonDTO.getId(), new HashMap<String, String>()); }
	 * }
	 * 
	 * public void handleClose(CloseEvent event) { try {
	 * org.ace.insurance.web.util.FileHandler.forceDelete(dirPath); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * public void handleSurveyQuestionAnswer(ResourceQuestionAnswerDTO dto) {
	 * System.out.println(dto.getResult()); }
	 * 
	 */}
