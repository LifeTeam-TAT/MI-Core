package org.ace.insurance.web.manage.medical.claim.action;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

/***************************************************************************************
 * @author KMH
 * @Date 2014-08-14
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalProposal</code> underWriting process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "ClaimSurveyActionBean")
public class ClaimSurveyActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH Claim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @ManagedProperty(value = "#{MedicalProposalService}") private
	 * IMedicalProposalService medicalProposalService;
	 * 
	 * public void setMedicalProposalService(IMedicalProposalService
	 * medicalProposalService) { this.medicalProposalService =
	 * medicalProposalService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimSurveyValidator}") private
	 * DTOValidator<MedicalClaimProposalDTO> medicalClaimSurveyValidator;
	 * 
	 * public void
	 * setMedicalClaimSurveyValidator(DTOValidator<MedicalClaimProposalDTO>
	 * medicalClaimSurveyValidator) { this.medicalClaimSurveyValidator =
	 * medicalClaimSurveyValidator; }
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimProposalService}") private
	 * IMedicalClaimProposalService medicalClaimProposalService;
	 * 
	 * public void setMedicalClaimProposalService(IMedicalClaimProposalService
	 * medicalClaimProposalService) { this.medicalClaimProposalService =
	 * medicalClaimProposalService; }
	 * 
	 * @ManagedProperty(value = "#{ProductProcessService}") private
	 * IProductProcessService productProcessService;
	 * 
	 * public void setProductProcessService(IProductProcessService
	 * productProcessService) { this.productProcessService =
	 * productProcessService; }
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
	 * private boolean hospitalized; private boolean medication; private boolean
	 * operation; private boolean death; private double beneTotalClaimAmount;
	 * private double hospAmount; private double operAmount; private double
	 * mediAmount; private double deathAmount; private boolean showEntry;
	 * private String temporyDir; private String medicalProposalId; private
	 * String remark; private User user; private User responsiblePerson; private
	 * List<User> responsiblePersonList; private MedicalClaimSurveyDTO
	 * medicalClaimSurveyDTO; private MedicalClaimProposalDTO
	 * medicalClaimProposalDTO; private final String PROPOSAL_DIR =
	 * "/upload/medical-proposal/"; private List<SurveyQuestionAnswerDTO>
	 * medicationQuestionAnswerDTOList; private List<SurveyQuestionAnswerDTO>
	 * operationQuestionAnswerDTOList; private List<SurveyQuestionAnswerDTO>
	 * deathQuestionAnswerDTOList; private List<SurveyQuestionAnswerDTO>
	 * hospitalizedQuestionAnswerDTOList; private List<WorkFlowHistory>
	 * workflowList; private Map<String, String> proposalUploadedFileMap;
	 * private Map<String, String> medicationproposalUploadedFileMap; private
	 * Map<String, String> operationproposalUploadedFileMap; private Map<String,
	 * String> deathproposalUploadedFileMap; private Map<String, String>
	 * hospitalizedproposalUploadedFileMap; private
	 * List<ShowSurveyQuestionAnswerDTO> showSurveyQuestionAnswerDTOList;
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalClaimProposalDTO =
	 * (medicalClaimProposalDTO == null) ? (MedicalClaimProposalDTO)
	 * getParam("medicalClaimProposal") : medicalClaimProposalDTO; }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalClaimProposal");
	 * }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostConstruct public void init() { initializeInjection();
	 * 
	 * temporyDir = "/temp/" + System.currentTimeMillis() + "/";
	 * medicalProposalId = medicalClaimProposalDTO.getId();
	 * medicalClaimSurveyDTO = new MedicalClaimSurveyDTO();
	 * medicalClaimSurveyDTO.setSurveyDate(new Date());
	 * medicalClaimSurveyDTO.setSurveyQuestionAnswerDTOList(null);
	 * medicalClaimProposalDTO.setMedicalClaimSurvey(medicalClaimSurveyDTO);
	 * medicationQuestionAnswerDTOList = new
	 * ArrayList<SurveyQuestionAnswerDTO>(); operationQuestionAnswerDTOList =
	 * new ArrayList<SurveyQuestionAnswerDTO>(); deathQuestionAnswerDTOList =
	 * new ArrayList<SurveyQuestionAnswerDTO>();
	 * hospitalizedQuestionAnswerDTOList = new
	 * ArrayList<SurveyQuestionAnswerDTO>(); proposalUploadedFileMap = new
	 * HashMap<String, String>(); medicationproposalUploadedFileMap = new
	 * HashMap<String, String>(); operationproposalUploadedFileMap = new
	 * HashMap<String, String>(); deathproposalUploadedFileMap = new
	 * HashMap<String, String>(); hospitalizedproposalUploadedFileMap = new
	 * HashMap<String, String>();
	 * 
	 * if (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null) {
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * setSurveyQuestionAnswersList(null); if
	 * (ProductProcessIDConfig.getHospitalizationClaimId() != null) {
	 * List<ProductProcessQuestionLink> ppQueLinkHCList =
	 * medicalProposalService.findPProQueByPPId(ProductProcessIDConfig.
	 * getMedicalProductId(), ProductProcessIDConfig
	 * .getHospitalizationClaimId().toString()); for (ProductProcessQuestionLink
	 * ppQuesLink : ppQueLinkHCList) { SurveyQuestionAnswerDTO surveyAnswerDTO =
	 * new SurveyQuestionAnswerDTO(ppQuesLink);
	 * surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().addSurveyQuestionList(
	 * surveyAnswerDTO); } hospitalizedQuestionAnswerDTOList =
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getSurveyQuestionAnswersList(); if (hospitalizedQuestionAnswerDTOList !=
	 * null && !hospitalizedQuestionAnswerDTOList.isEmpty()) {
	 * Collections.sort(hospitalizedQuestionAnswerDTOList); } } } if
	 * (medicalClaimProposalDTO.getDeathClaimDTO() != null) {
	 * medicalClaimProposalDTO.getDeathClaimDTO().setSurveyQuestionAnswersList(
	 * null); if (ProductProcessIDConfig.getDeathClaimId() != null) {
	 * List<ProductProcessQuestionLink> ppQueLinkDCList =
	 * medicalProposalService.findPProQueByPPId(ProductProcessIDConfig.
	 * getMedicalProductId(), ProductProcessIDConfig
	 * .getDeathClaimId().toString()); for (ProductProcessQuestionLink
	 * ppQuesLink : ppQueLinkDCList) { SurveyQuestionAnswerDTO surveyAnswerDTO =
	 * new SurveyQuestionAnswerDTO(ppQuesLink);
	 * surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimProposalDTO.getDeathClaimDTO().addSurveyQuestionList(
	 * surveyAnswerDTO); } deathQuestionAnswerDTOList =
	 * medicalClaimProposalDTO.getDeathClaimDTO().getSurveyQuestionAnswersList()
	 * ; if (deathQuestionAnswerDTOList != null &&
	 * !deathQuestionAnswerDTOList.isEmpty()) {
	 * Collections.sort(deathQuestionAnswerDTOList); } } }
	 * 
	 * if (medicalClaimProposalDTO.getOperationClaimDTO() != null) {
	 * medicalClaimProposalDTO.getOperationClaimDTO().
	 * setSurveyQuestionAnswersList(null); if
	 * (ProductProcessIDConfig.getOperationClaimId() != null) {
	 * List<ProductProcessQuestionLink> ppQueLinkDCList =
	 * medicalProposalService.findPProQueByPPId(ProductProcessIDConfig.
	 * getMedicalProductId(), ProductProcessIDConfig
	 * 
	 * .getOperationClaimId().toString()); for (ProductProcessQuestionLink
	 * ppQuesLink : ppQueLinkDCList) { SurveyQuestionAnswerDTO surveyAnswerDTO =
	 * new SurveyQuestionAnswerDTO(ppQuesLink);
	 * surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimProposalDTO.getOperationClaimDTO().addSurveyQuestionList(
	 * surveyAnswerDTO); } operationQuestionAnswerDTOList =
	 * medicalClaimProposalDTO.getOperationClaimDTO().
	 * getSurveyQuestionAnswersList(); if (operationQuestionAnswerDTOList !=
	 * null && !operationQuestionAnswerDTOList.isEmpty()) {
	 * Collections.sort(operationQuestionAnswerDTOList); } } } if
	 * (medicalClaimProposalDTO.getMedicationClaimDTO() != null) {
	 * medicalClaimProposalDTO.getMedicationClaimDTO().
	 * setSurveyQuestionAnswersList(null); if
	 * (ProductProcessIDConfig.getOperationClaimId() != null) {
	 * List<ProductProcessQuestionLink> ppQueLinkDCList =
	 * medicalProposalService.findPProQueByPPId(ProductProcessIDConfig.
	 * getMedicalProductId(), ProductProcessIDConfig
	 * .getDeathClaimId().toString()); for (ProductProcessQuestionLink
	 * ppQuesLink : ppQueLinkDCList) { SurveyQuestionAnswerDTO surveyAnswerDTO =
	 * new SurveyQuestionAnswerDTO(ppQuesLink);
	 * surveyAnswerDTO.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimProposalDTO.getMedicationClaimDTO().addSurveyQuestionList(
	 * surveyAnswerDTO); } medicationQuestionAnswerDTOList =
	 * medicalClaimProposalDTO.getMedicationClaimDTO().
	 * getSurveyQuestionAnswersList(); if (medicationQuestionAnswerDTOList !=
	 * null && !medicationQuestionAnswerDTOList.isEmpty()) {
	 * Collections.sort(medicationQuestionAnswerDTOList); } } }
	 * 
	 * if (medicalClaimProposalDTO.getMedicalClaimBeneficiariesList() != null &&
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList().size() != 0) {
	 * prepareBeneficiaryClaimAmount(); }
	 * 
	 * }
	 * 
	 * public void prepareBeneficiaryClaimAmount() { double total = 0.0; for
	 * (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) { hospAmount
	 * += mcb.getHospClaimAmount(); operAmount += mcb.getHospClaimAmount();
	 * mediAmount += mcb.getMediClaimAmount(); deathAmount +=
	 * mcb.getDeathClaimAmount(); total += mcb.getClaimAmount(); }
	 * 
	 * medicalClaimProposalDTO.setTotalHospAmt(hospAmount);
	 * medicalClaimProposalDTO.setTotalOperAmt(operAmount);
	 * medicalClaimProposalDTO.setTotalMediAmt(mediAmount);
	 * medicalClaimProposalDTO.setTotalDeathAmt(deathAmount);
	 * medicalClaimProposalDTO.setTotalAllBeneAmt(total); }
	 * 
	 * public boolean isHospitalized() { if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null) {
	 * hospitalized = true; } return hospitalized; }
	 * 
	 * public void setHospitalized(boolean hospitalized) { this.hospitalized =
	 * hospitalized; }
	 * 
	 * public boolean isMedication() { if
	 * (medicalClaimProposalDTO.getMedicationClaimDTO() != null) { medication =
	 * true; } return medication; }
	 * 
	 * public void setMedication(boolean medication) { this.medication =
	 * medication; }
	 * 
	 * public boolean isOperation() { if
	 * (medicalClaimProposalDTO.getOperationClaimDTO() != null) { operation =
	 * true; } return operation; }
	 * 
	 * public void setOperation(boolean operation) { this.operation = operation;
	 * }
	 * 
	 * public boolean isDeath() { if (medicalClaimProposalDTO.getDeathClaimDTO()
	 * != null) { death = true; } return death; }
	 * 
	 * public void setDeath(boolean death) { this.death = death; }
	 * 
	 * public String addNewSurvey() { String res = null; try {
	 * medicalClaimSurveyDTO.setRemark(remark);
	 * medicalClaimProposalDTO.setMedicalClaimSurvey(medicalClaimSurveyDTO); if
	 * (proposalUploadedFileMap != null && proposalUploadedFileMap.size() != 0)
	 * { for (String fileName : proposalUploadedFileMap.keySet()) { String
	 * filePath = PROPOSAL_DIR + medicalProposalId + "/" + fileName;
	 * medicalClaimProposalDTO.addClaimAttachment(new
	 * MedicalClaimAttachmentDTO(fileName, filePath)); } } if
	 * (hospitalizedproposalUploadedFileMap != null &&
	 * hospitalizedproposalUploadedFileMap.size() != 0) { for (String fileName :
	 * hospitalizedproposalUploadedFileMap.keySet()) { String filePath =
	 * PROPOSAL_DIR + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().getId() + "/" +
	 * fileName;
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().addAttachment(new
	 * AttachmentDTO(fileName, filePath)); } } if
	 * (operationproposalUploadedFileMap != null &&
	 * operationproposalUploadedFileMap.size() != 0) { for (String fileName :
	 * operationproposalUploadedFileMap.keySet()) { String filePath =
	 * PROPOSAL_DIR + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getOperationClaimDTO().getId() + "/" + fileName;
	 * medicalClaimProposalDTO.getOperationClaimDTO().addAttachment(new
	 * AttachmentDTO(fileName, filePath)); } } if (deathproposalUploadedFileMap
	 * != null && deathproposalUploadedFileMap.size() != 0) { for (String
	 * fileName : deathproposalUploadedFileMap.keySet()) { String filePath =
	 * PROPOSAL_DIR + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getDeathClaimDTO().getId() + "/" + fileName;
	 * medicalClaimProposalDTO.getDeathClaimDTO().addAttachment(new
	 * AttachmentDTO(fileName, filePath)); } }
	 * 
	 * if (medicationproposalUploadedFileMap != null &&
	 * medicationproposalUploadedFileMap.size() != 0) { for (String fileName :
	 * medicationproposalUploadedFileMap.keySet()) { String filePath =
	 * PROPOSAL_DIR + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getMedicationClaimDTO().getId() + "/" + fileName;
	 * medicalClaimProposalDTO.getMedicationClaimDTO().addAttachment(new
	 * AttachmentDTO(fileName, filePath)); } } if
	 * (medicationQuestionAnswerDTOList != null &&
	 * medicationQuestionAnswerDTOList.size() != 0) { for
	 * (SurveyQuestionAnswerDTO sqa : medicationQuestionAnswerDTOList) {
	 * sqa.setClaimType(ClaimType.MEDICATION_CLAIM);
	 * sqa.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimSurveyDTO.addsurveyQuestionAnswer(sqa); } } if
	 * (operationQuestionAnswerDTOList != null &&
	 * operationQuestionAnswerDTOList.size() != 0) { for
	 * (SurveyQuestionAnswerDTO sqa : operationQuestionAnswerDTOList) {
	 * sqa.setClaimType(ClaimType.OPERATION_CLAIM);
	 * sqa.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimSurveyDTO.addsurveyQuestionAnswer(sqa); } } if
	 * (deathQuestionAnswerDTOList != null && deathQuestionAnswerDTOList.size()
	 * != 0) { for (SurveyQuestionAnswerDTO sqa : deathQuestionAnswerDTOList) {
	 * sqa.setClaimType(ClaimType.DEATH_CLAIM);
	 * sqa.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimSurveyDTO.addsurveyQuestionAnswer(sqa); } } if
	 * (hospitalizedQuestionAnswerDTOList != null &&
	 * hospitalizedQuestionAnswerDTOList.size() != 0) { for
	 * (SurveyQuestionAnswerDTO sqa : hospitalizedQuestionAnswerDTOList) {
	 * sqa.setClaimType(ClaimType.HOSPITALIZED_CLAIM);
	 * sqa.setSurveyType(SurveyType.MEDICAL_CLAIM_SURVEY);
	 * medicalClaimSurveyDTO.addsurveyQuestionAnswer(sqa); } }
	 * medicalClaimProposalDTO.setMedicalClaimSurvey(medicalClaimSurveyDTO);
	 * WorkflowTask workflowTask = WorkflowTask.MEDICAL_CLAIM_APPROVAL;
	 * WorkFlowDTO workFlowDTO = new
	 * WorkFlowDTO(medicalClaimProposalDTO.getId(), remark, workflowTask,
	 * ReferenceType.MEDICAL_CLAIM, user, responsiblePerson);
	 * medicalClaimProposalService.updateMedicalClaimProposal(
	 * MedicalClaimProposalFactory.createMedicalClaimProposal(
	 * medicalClaimProposalDTO), workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.SURVEY_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalClaimProposalDTO.getClaimRequestId()); moveUploadedFiles(); res =
	 * "dashboard"; } catch (SystemException ex) { handelSysException(ex); }
	 * return res; }
	 * 
	 * public double getBeneTotalClaimAmount() { return beneTotalClaimAmount; }
	 * 
	 * public double getHospAmount() { return hospAmount; }
	 * 
	 * public double getOperAmount() { return operAmount; }
	 * 
	 * public double getMediAmount() { return mediAmount; }
	 * 
	 * public double getDeathAmount() { return deathAmount; }
	 * 
	 * public void setBeneTotalClaimAmount(double beneTotalClaimAmount) {
	 * this.beneTotalClaimAmount = beneTotalClaimAmount; }
	 * 
	 * public void setHospAmount(double hospAmount) { this.hospAmount =
	 * hospAmount; }
	 * 
	 * public void setOperAmount(double operAmount) { this.operAmount =
	 * operAmount; }
	 * 
	 * public void setMediAmount(double mediAmount) { this.mediAmount =
	 * mediAmount; }
	 * 
	 * public void setDeathAmount(double deathAmount) { this.deathAmount =
	 * deathAmount; }
	 * 
	 * public List<ShowSurveyQuestionAnswerDTO>
	 * getShowSurveyQuestionAnswerDTOList() { return
	 * showSurveyQuestionAnswerDTOList; }
	 * 
	 * public void
	 * setShowSurveyQuestionAnswerDTOList(List<ShowSurveyQuestionAnswerDTO>
	 * showSurveyQuestionAnswerDTOList) { this.showSurveyQuestionAnswerDTOList =
	 * showSurveyQuestionAnswerDTOList; }
	 * 
	 * public void returnTownship(SelectEvent event) { Township township =
	 * (Township) event.getObject();
	 * medicalClaimSurveyDTO.setTownship(township); }
	 * 
	 * public void selectUser() {
	 * selectUser(WorkflowTask.MEDICAL_CLAIM_APPROVAL,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public String onFlowProcess(FlowEvent event) { boolean valid = true;
	 * ValidationResult result =
	 * medicalClaimSurveyValidator.validate(medicalClaimProposalDTO); if
	 * ("proposalInfo".equals(event.getOldStep())) { if (!result.isVerified()) {
	 * for (ErrorMessage message : result.getErrorMeesages()) {
	 * addErrorMessage(message); } valid = false; } } if
	 * ("hospitalizedSurveyInfo".equals(event.getOldStep()) &&
	 * "operationSurveyInfo".equals(event.getNewStep())) { } if
	 * ("operationSurveyInfo".equals(event.getOldStep()) &&
	 * "medicationSurveyInfo".equals(event.getNewStep())) { } if
	 * ("medicationSurveyInfo".equals(event.getOldStep()) &&
	 * "deathSurveyInfo".equals(event.getNewStep())) { } if
	 * ("deathSurveyInfo".equals(event.getOldStep()) &&
	 * "workflow".equals(event.getNewStep())) { } return valid ?
	 * event.getNewStep() : event.getOldStep(); }
	 * 
	 * public void handleProposalAttachment(FileUploadEvent event) {
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!proposalUploadedFileMap.containsKey(fileName)) { String filePath =
	 * temporyDir + medicalProposalId + "/" + fileName;
	 * proposalUploadedFileMap.put(fileName, filePath); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); } }
	 * 
	 * public List<String> getProposalUploadedFileList() { if
	 * (proposalUploadedFileMap != null && proposalUploadedFileMap.size() != 0)
	 * { return new ArrayList<String>(proposalUploadedFileMap.values()); }
	 * return new ArrayList<String>(); }
	 * 
	 * public void handleHospitalAttachment(FileUploadEvent event) {
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!hospitalizedproposalUploadedFileMap.containsKey(fileName)) { String
	 * filePath = temporyDir + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().getId() + "/" +
	 * fileName; hospitalizedproposalUploadedFileMap.put(fileName, filePath);
	 * createFile(new File(getUploadPath() + filePath),
	 * uploadedFile.getContents()); } }
	 * 
	 * public List<String> getHospitalAttachmentList() { if
	 * (hospitalizedproposalUploadedFileMap != null &&
	 * hospitalizedproposalUploadedFileMap.size() != 0) { return new
	 * ArrayList<String>(hospitalizedproposalUploadedFileMap.values()); } return
	 * new ArrayList<String>(); }
	 * 
	 * public void handleMedicationAttachment(FileUploadEvent event) {
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!medicationproposalUploadedFileMap.containsKey(fileName)) { String
	 * filePath = temporyDir + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getMedicationClaimDTO().getId() + "/" + fileName;
	 * medicationproposalUploadedFileMap.put(fileName, filePath); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); } }
	 * 
	 * public List<String> getMedicationAttachmentList() { if
	 * (medicationproposalUploadedFileMap.size() != 0 &&
	 * medicationproposalUploadedFileMap != null) { return new
	 * ArrayList<String>(medicationproposalUploadedFileMap.values()); } return
	 * new ArrayList<String>(); }
	 * 
	 * public void handleOperationAttachment(FileUploadEvent event) {
	 * UploadedFile uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!operationproposalUploadedFileMap.containsKey(fileName)) { String
	 * filePath = temporyDir + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getOperationClaimDTO().getId() + "/" + fileName;
	 * operationproposalUploadedFileMap.put(fileName, filePath); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); } }
	 * 
	 * public List<String> getOperationAttachmentList() { if
	 * (operationproposalUploadedFileMap.size() != 0 &&
	 * operationproposalUploadedFileMap != null) { return new
	 * ArrayList<String>(operationproposalUploadedFileMap.values()); } return
	 * new ArrayList<String>(); }
	 * 
	 * public void handleDeathAttachment(FileUploadEvent event) { UploadedFile
	 * uploadedFile = event.getFile(); String fileName =
	 * uploadedFile.getFileName().replaceAll("\\s", "_"); if
	 * (!deathproposalUploadedFileMap.containsKey(fileName)) { String filePath =
	 * temporyDir + medicalProposalId + "/" +
	 * medicalClaimProposalDTO.getDeathClaimDTO().getId() + "/" + fileName;
	 * deathproposalUploadedFileMap.put(fileName, filePath); createFile(new
	 * File(getUploadPath() + filePath), uploadedFile.getContents()); } }
	 * 
	 * public List<String> getDeathAttachmentList() { if
	 * (deathproposalUploadedFileMap != null &&
	 * deathproposalUploadedFileMap.size() != 0) { return new
	 * ArrayList<String>(deathproposalUploadedFileMap.values()); } return new
	 * ArrayList<String>(); }
	 * 
	 * public void changeMedicationResourceQuestion(AjaxBehaviorEvent e) {
	 * UIData data = (UIData)
	 * e.getComponent().findComponent("medicationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * medicationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeMedicationResourceQuestionList(AjaxBehaviorEvent e) {
	 * UIData data = (UIData)
	 * e.getComponent().findComponent("medicationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * medicationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeMedicationBooleanValue(AjaxBehaviorEvent e) { UIData
	 * data = (UIData)
	 * e.getComponent().findComponent("medicationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * medicationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeMedicationDate(SelectEvent e) { UIData data = (UIData)
	 * e.getComponent().findComponent("medicationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * medicationQuestionAnswerDTOList.get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList();
	 * resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(
	 * surveyQuestionAnswer.getAnswerDate())); }
	 * 
	 * public void resetMedicationDate(SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer) { surveyQuestionAnswer.setAnswerDate(null);
	 * surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null); }
	 * 
	 * public void changeOperationResourceQuestion(AjaxBehaviorEvent e) { UIData
	 * data = (UIData) e.getComponent().findComponent("operationQuestionTable");
	 * int rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = operationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeOperationResourceQuestionList(AjaxBehaviorEvent e) {
	 * UIData data = (UIData)
	 * e.getComponent().findComponent("operationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * operationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeOperationBooleanValue(AjaxBehaviorEvent e) { UIData
	 * data = (UIData) e.getComponent().findComponent("operationQuestionTable");
	 * int rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = operationQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeOperationDate(SelectEvent e) { UIData data = (UIData)
	 * e.getComponent().findComponent("operationQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * operationQuestionAnswerDTOList.get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList();
	 * resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(
	 * surveyQuestionAnswer.getAnswerDate())); }
	 * 
	 * public void resetOperationDate(SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer) { surveyQuestionAnswer.setAnswerDate(null);
	 * surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null); }
	 * 
	 * public void changeDeathResourceQuestion(AjaxBehaviorEvent e) { UIData
	 * data = (UIData) e.getComponent().findComponent("deathQuestionTable"); int
	 * rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = deathQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeDeathResourceQuestionList(AjaxBehaviorEvent e) { UIData
	 * data = (UIData) e.getComponent().findComponent("deathQuestionTable"); int
	 * rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = deathQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeDeathBooleanValue(AjaxBehaviorEvent e) { UIData data =
	 * (UIData) e.getComponent().findComponent("deathQuestionTable"); int
	 * rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = deathQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeDeathDate(SelectEvent e) { UIData data = (UIData)
	 * e.getComponent().findComponent("deathQuestionTable"); int rowIndex =
	 * data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * deathQuestionAnswerDTOList.get(rowIndex); List<ResourceQuestionAnswerDTO>
	 * resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList();
	 * resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(
	 * surveyQuestionAnswer.getAnswerDate())); }
	 * 
	 * public void resetDeathDate(SurveyQuestionAnswerDTO surveyQuestionAnswer)
	 * { surveyQuestionAnswer.setAnswerDate(null);
	 * surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null); }
	 * 
	 * public void changeHospitalizedResourceQuestion(AjaxBehaviorEvent e) {
	 * UIData data = (UIData)
	 * e.getComponent().findComponent("hospitalizedQuestionTable"); int rowIndex
	 * = data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * hospitalizedQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeHospitalizedResourceQuestionList(AjaxBehaviorEvent e) {
	 * UIData data = (UIData)
	 * e.getComponent().findComponent("hospitalizedQuestionTable"); int rowIndex
	 * = data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * hospitalizedQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeHospitalizedBooleanValue(AjaxBehaviorEvent e) { UIData
	 * data = (UIData)
	 * e.getComponent().findComponent("hospitalizedQuestionTable"); int rowIndex
	 * = data.getRowIndex(); SurveyQuestionAnswerDTO surveyQuestionAnswer =
	 * hospitalizedQuestionAnswerDTOList.get(rowIndex);
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
	 * public void changeHospitalizedDate(SelectEvent e) { UIData data =
	 * (UIData) e.getComponent().findComponent("hospitalizedQuestionTable"); int
	 * rowIndex = data.getRowIndex(); SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer = hospitalizedQuestionAnswerDTOList.get(rowIndex);
	 * List<ResourceQuestionAnswerDTO> resourceQuestionAnswerList =
	 * surveyQuestionAnswer.getResourceQuestionList();
	 * resourceQuestionAnswerList.get(0).setResult(Utils.getDateFormatString(
	 * surveyQuestionAnswer.getAnswerDate())); }
	 * 
	 * public void resetHospitalizedDate(SurveyQuestionAnswerDTO
	 * surveyQuestionAnswer) { surveyQuestionAnswer.setAnswerDate(null);
	 * surveyQuestionAnswer.getResourceQuestionList().get(0).setResult(null); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public void returnMedicalPlaceDialog(SelectEvent event) { Hospital
	 * medicalPlace = (Hospital) event.getObject();
	 * medicalClaimSurveyDTO.setMedicalPlace(medicalPlace);
	 * medicalClaimSurveyDTO.setAddress(medicalPlace.getAddress().
	 * getPermanentAddress());
	 * medicalClaimSurveyDTO.setTownship(medicalPlace.getAddress().getTownship()
	 * ); }
	 * 
	 * public void removeProposalUploadedFile(String filePath) { try {
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * fileName = getFileName(filePath);
	 * proposalUploadedFileMap.remove(fileName); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void removeHospitalClaimUploadedFile(String filePath) { try {
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * fileName = getFileName(filePath);
	 * hospitalizedproposalUploadedFileMap.remove(fileName); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * public void removeOperationClaimUploadedFile(String filePath) { try {
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * fileName = getFileName(filePath);
	 * operationproposalUploadedFileMap.remove(fileName); } catch (IOException
	 * e) { e.printStackTrace(); } }
	 * 
	 * public void removeDeathClaimUploadedFile(String filePath) { try {
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * fileName = getFileName(filePath);
	 * deathproposalUploadedFileMap.remove(fileName); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 * public void removeMedicationClaimUploadedFile(String filePath) { try {
	 * FileHandler.forceDelete(new File(getUploadPath() + filePath)); String
	 * fileName = getFileName(filePath);
	 * medicationproposalUploadedFileMap.remove(fileName); } catch (IOException
	 * e) { e.printStackTrace(); } }
	 * 
	 * public List<ShowSurveyQuestionAnswerDTO>
	 * prepareSurveyQuestionAnswer(List<SurveyQuestionAnswerDTO>
	 * surveyQuestionAnswerList) { List<ShowSurveyQuestionAnswerDTO>
	 * showSQueAnsList = new ArrayList<ShowSurveyQuestionAnswerDTO>(); for
	 * (SurveyQuestionAnswerDTO surveyQuestionAnswer : surveyQuestionAnswerList)
	 * { System.out.println("SMT " + surveyQuestionAnswer.getDescription()); if
	 * (surveyQuestionAnswer.getInputType().equals(InputType.
	 * SELECT_MANY_CHECKBOX) ||
	 * surveyQuestionAnswer.getInputType().equals(InputType.SELECT_MANY_MENU) ||
	 * surveyQuestionAnswer.getInputType().equals(InputType.SELECT_ONE_MENU) ||
	 * surveyQuestionAnswer.getInputType().equals(InputType.SELECT_ONE_RADIO) ||
	 * surveyQuestionAnswer.getInputType().equals(InputType.BOOLEAN)) {
	 * StringBuffer sb = new StringBuffer(); int i = 0; for
	 * (ResourceQuestionAnswerDTO resourceQuestionAnswer :
	 * surveyQuestionAnswer.getResourceQuestionList()) { if
	 * (resourceQuestionAnswer.getValue() == 1) { if (i != 0) { sb.append(",");
	 * } sb.append(resourceQuestionAnswer.getName()); i++; } }
	 * ShowSurveyQuestionAnswerDTO showSQADTO = new
	 * ShowSurveyQuestionAnswerDTO(surveyQuestionAnswer.getPriority(),
	 * surveyQuestionAnswer.getDescription(), sb.toString());
	 * showSQueAnsList.add(showSQADTO); } else { ShowSurveyQuestionAnswerDTO
	 * showSQADTO = new
	 * ShowSurveyQuestionAnswerDTO(surveyQuestionAnswer.getPriority(),
	 * surveyQuestionAnswer.getDescription(),
	 * (surveyQuestionAnswer.getResourceQuestionList() == null ||
	 * surveyQuestionAnswer.getResourceQuestionList().size() == 0 ? "" :
	 * surveyQuestionAnswer .getResourceQuestionList().get(0).getResult()));
	 * showSQueAnsList.add(showSQADTO); } } return showSQueAnsList; }
	 * 
	 * public List<SurveyQuestionAnswerDTO> getMedicationQuestionAnswerDTOList()
	 * { return medicationQuestionAnswerDTOList; }
	 * 
	 * public List<SurveyQuestionAnswerDTO> getOperationQuestionAnswerDTOList()
	 * { return operationQuestionAnswerDTOList; }
	 * 
	 * public List<SurveyQuestionAnswerDTO> getDeathQuestionAnswerDTOList() {
	 * return deathQuestionAnswerDTOList; }
	 * 
	 * public List<SurveyQuestionAnswerDTO>
	 * getHospitalizedQuestionAnswerDTOList() { return
	 * hospitalizedQuestionAnswerDTOList; }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void
	 * setMedicationQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO>
	 * medicationQuestionAnswerDTOList) { this.medicationQuestionAnswerDTOList =
	 * medicationQuestionAnswerDTOList; }
	 * 
	 * public void
	 * setOperationQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO>
	 * operationQuestionAnswerDTOList) { this.operationQuestionAnswerDTOList =
	 * operationQuestionAnswerDTOList; }
	 * 
	 * public void setDeathQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO>
	 * deathQuestionAnswerDTOList) { this.deathQuestionAnswerDTOList =
	 * deathQuestionAnswerDTOList; }
	 * 
	 * public void
	 * setHospitalizedQuestionAnswerDTOList(List<SurveyQuestionAnswerDTO>
	 * hospitalizedQuestionAnswerDTOList) {
	 * this.hospitalizedQuestionAnswerDTOList =
	 * hospitalizedQuestionAnswerDTOList; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public void loadWorkflow(boolean claimflag) { workflowList =
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalClaimProposalDTO.getId(
	 * )); }
	 * 
	 * public boolean isShowEntry() { return showEntry; }
	 * 
	 * public String getTemporyDir() { return temporyDir; }
	 * 
	 * public String getMedicalProposalId() { return medicalProposalId; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public List<WorkFlowHistory> getWorkflowList() { return workflowList; }
	 * 
	 * public MedicalClaimSurveyDTO getMedicalClaimSurveyDTO() { return
	 * medicalClaimSurveyDTO; }
	 * 
	 * public void setShowEntry(boolean showEntry) { this.showEntry = showEntry;
	 * }
	 * 
	 * public void setTemporyDir(String temporyDir) { this.temporyDir =
	 * temporyDir; }
	 * 
	 * public void setMedicalProposalId(String medicalProposalId) {
	 * this.medicalProposalId = medicalProposalId; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * public void setWorkflowList(List<WorkFlowHistory> workflowList) {
	 * this.workflowList = workflowList; }
	 * 
	 * public void setMedicalClaimSurveyDTO(MedicalClaimSurveyDTO
	 * medicalClaimSurveyDTO) { this.medicalClaimSurveyDTO =
	 * medicalClaimSurveyDTO; }
	 * 
	 * private void moveUploadedFiles() { try {
	 * FileHandler.moveFiles(getUploadPath(), temporyDir + medicalProposalId,
	 * PROPOSAL_DIR + medicalProposalId); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 */}
