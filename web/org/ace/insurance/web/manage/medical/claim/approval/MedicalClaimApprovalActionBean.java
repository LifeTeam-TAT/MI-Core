package org.ace.insurance.web.manage.medical.claim.approval;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

/***************************************************************************************
 * @author TZA
 * @Date 2015-01-12
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalClaim</code> approved process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "MedicalClaimApprovalActionBean")
public class MedicalClaimApprovalActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH Claim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @ManagedProperty(value = "#{ApprovedMedicalClaimFrontService}") private
	 * IApprovedMedicalClaimFrontService approvedMedicalClaimFrontService;
	 * 
	 * public void setApprovedMedicalClaimFrontService(
	 * IApprovedMedicalClaimFrontService approvedMedicalClaimFrontService) {
	 * this.approvedMedicalClaimFrontService = approvedMedicalClaimFrontService;
	 * }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * private User user; private MedicalClaimProposalDTO
	 * medicalClaimProposalDTO; private HospitalizedClaimDTO
	 * hospitalizedClaimDTO; private List<WorkFlowHistory> workflowList; private
	 * boolean hospitalized; private boolean medication; private boolean
	 * operation; private boolean death; private boolean existhosp; private
	 * boolean existoper; private boolean existmedi; private boolean existdeath;
	 * private boolean permitHospitalization; private boolean permitOperation;
	 * private boolean permitMedication; private int totalDaysOfHospitalization;
	 * private double beneTotalClaimAmount; private double hospAmount; private
	 * double operAmount; private double mediAmount; private double deathAmount;
	 * private String remark; private User responsiblePerson; private boolean
	 * submitFlage; private List<MedicalClaimDTO> medicalClaimList; private
	 * List<HospitalizedClaimICD10> hospitalizedReasonList;
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalClaimProposalDTO =
	 * (medicalClaimProposalDTO == null) ? (MedicalClaimProposalDTO)
	 * getParam("medicalClaimProposal") : medicalClaimProposalDTO; }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalClaimProposal");
	 * }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * @PostConstruct public void init() { initializeInjection();
	 * loadSurveyQuestionAnswer(medicalClaimProposalDTO); prepareForConfirm();
	 * medicalClaimList = new ArrayList<MedicalClaimDTO>(); if
	 * (medicalClaimProposalDTO.isHospitalized()) {
	 * medicalClaimList.add(medicalClaimProposalDTO. getHospitalizedClaimDTO());
	 * } if (medicalClaimProposalDTO.isDeath()) {
	 * medicalClaimList.add(medicalClaimProposalDTO. getDeathClaimDTO()); } if
	 * (medicalClaimProposalDTO.isOperation()) {
	 * medicalClaimList.add(medicalClaimProposalDTO. getOperationClaimDTO()); }
	 * if (medicalClaimProposalDTO.isMedication()) {
	 * medicalClaimList.add(medicalClaimProposalDTO. getMedicationClaimDTO()); }
	 * if (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null &&
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getHospitalizedClaimICD10List() != null) { if (hospitalizedReasonList ==
	 * null) { hospitalizedReasonList = new ArrayList<HospitalizedClaimICD10>();
	 * }
	 * 
	 * for (HospitalizedClaimICD10 temp :
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getHospitalizedClaimICD10List()) { hospitalizedReasonList.add(temp); } }
	 * prepareForApprove(); totalDaysOfHospitalization =
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO(). getTotalHosDays(); }
	 * 
	 * public void clearRejectReason() {
	 * 
	 * if (medicalClaimProposalDTO.isHospitalized()) {
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO(). setRejectReason(""); }
	 * 
	 * if (medicalClaimProposalDTO.isMedication()) {
	 * medicalClaimProposalDTO.getMedicationClaimDTO(). setRejectReason(""); }
	 * 
	 * if (medicalClaimProposalDTO.isOperation()) {
	 * medicalClaimProposalDTO.getOperationClaimDTO(). setRejectReason(""); }
	 * 
	 * if (medicalClaimProposalDTO.isDeath()) {
	 * medicalClaimProposalDTO.getDeathClaimDTO(). setRejectReason(""); } }
	 * 
	 * public double getTotalClaimAmount() { return
	 * Utils.getTotalClaimAmount(medicalClaimProposalDTO); }
	 * 
	 * public void prepareForApprove() { if (totalDaysOfHospitalization >
	 * medicalClaimProposalDTO.getPolicyInsuredPersonDTO().
	 * getProduct().getMaxHospDays()) { permitHospitalization = true; } }
	 * 
	 * public void checkRejectReason() { String formID = "medicalApprovalForm";
	 * if (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null) { if
	 * ((medicalClaimProposalDTO.getHospitalizedClaimDTO(). isApproved() ==
	 * false) && (medicalClaimProposalDTO.getHospitalizedClaimDTO().
	 * getRejectReason().isEmpty())) { addErrorMessage(formID + ":rejectReason",
	 * MessageId.NEED_REJECT_REASON); } } if
	 * (medicalClaimProposalDTO.getOperationClaimDTO() != null) { if
	 * ((medicalClaimProposalDTO.getOperationClaimDTO(). isApproved() == false)
	 * && (medicalClaimProposalDTO.getOperationClaimDTO().
	 * getRejectReason().isEmpty())) { addErrorMessage(formID +
	 * ":rejectReasonOperation", MessageId.NEED_REJECT_REASON); } } if
	 * (medicalClaimProposalDTO.getMedicationClaimDTO() != null) { if
	 * ((medicalClaimProposalDTO.getMedicationClaimDTO(). isApproved() == false)
	 * && (medicalClaimProposalDTO.getMedicationClaimDTO().
	 * getRejectReason().isEmpty())) { addErrorMessage(formID +
	 * ":rejectReasonMedication", MessageId.NEED_REJECT_REASON); } } if
	 * (medicalClaimProposalDTO.getDeathClaimDTO() != null) { if
	 * ((medicalClaimProposalDTO.getDeathClaimDTO(). isApproved() == false) &&
	 * (medicalClaimProposalDTO.getDeathClaimDTO().
	 * getRejectReason().isEmpty())) { addErrorMessage(formID +
	 * ":rejectReasonDeath", MessageId.NEED_REJECT_REASON); } }
	 * 
	 * RequestContext.getCurrentInstance().execute(
	 * "medicalApprovalConfirmationDialog.show();"); }
	 * 
	 * public void prepareForConfirm() { if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO() != null &&
	 * medicalClaimProposalDTO.getDeathClaimDTO() == null ||
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO() != null &&
	 * medicalClaimProposalDTO.getDeathClaimDTO() != null) { existhosp = true; }
	 * if (medicalClaimProposalDTO.getDeathClaimDTO() != null) { existdeath =
	 * true; } if (medicalClaimProposalDTO.getOperationClaimDTO() != null) {
	 * existoper = true; } if (medicalClaimProposalDTO.getMedicationClaimDTO()
	 * != null) { existmedi = true; } }
	 * 
	 * public String addNewMedicalClaimApproval() { String result = null;
	 * WorkFlowDTO workFlowDTO = new
	 * WorkFlowDTO(medicalClaimProposalDTO.getId(), remark,
	 * WorkflowTask.MEDICAL_CLAIM_INFORM, ReferenceType.MEDICAL_CLAIM, user,
	 * responsiblePerson); try {
	 * approvedMedicalClaimFrontService.approveMedicalClaim(
	 * medicalClaimProposalDTO, workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.MEDICAL_ClAIM_APPROVAL_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalClaimProposalDTO.getClaimReportNo()); result = "dashboard"; }
	 * catch (SystemException ex) { handelSysException(ex); } return result; }
	 * 
	 * public String onFlowProcess(FlowEvent event) { boolean valid = true; if
	 * ("hospitalizedClaimApproval".equals(event.getOldStep( ))) { if
	 * (existdeath || existmedi || existoper || existhosp) { existoper = true; }
	 * } if ("hospClaimApproval".equals(event.getOldStep())) { if (existdeath ||
	 * existmedi || existoper) { existoper = true; } } else if
	 * ("operationClaimApproval".equals(event.getOldStep())) { if (existdeath ||
	 * existmedi) { existmedi = true; } } else if
	 * ("medicationClaimApproval".equals(event.getOldStep()) ) { if (existdeath)
	 * { existdeath = true; } } else {
	 * 
	 * } return valid ? event.getNewStep() : event.getOldStep(); }
	 * 
	 * public void loadWorkflow(boolean claimflag) { workflowList =
	 * workFlowService.findWorkFlowHistoryByRefNo(
	 * medicalClaimProposalDTO.getId());
	 * 
	 * }
	 * 
	 * public HospitalizedClaimDTO getHospitalizedClaimDTO() { return
	 * hospitalizedClaimDTO; }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return workflowList; }
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
	 * public int getTotalDaysOfHospitalization() { return
	 * totalDaysOfHospitalization; }
	 * 
	 * public void setTotalDaysOfHospitalization(int totalDaysOfHospitalization)
	 * { this.totalDaysOfHospitalization = totalDaysOfHospitalization; }
	 * 
	 * public double getHospAmount() { return hospAmount; }
	 * 
	 * public void setHospAmount(double hospAmount) { this.hospAmount =
	 * hospAmount; }
	 * 
	 * public double getOperAmount() { return operAmount; }
	 * 
	 * public void setOperAmount(double operAmount) { this.operAmount =
	 * operAmount; }
	 * 
	 * public double getMediAmount() { return mediAmount; }
	 * 
	 * public void setMediAmount(double mediAmount) { this.mediAmount =
	 * mediAmount; }
	 * 
	 * public double getDeathAmount() { return deathAmount; }
	 * 
	 * public void setDeathAmount(double deathAmount) { this.deathAmount =
	 * deathAmount; }
	 * 
	 * public boolean isSubmitFlage() { return submitFlage; }
	 * 
	 * public boolean isExisthosp() { return existhosp; }
	 * 
	 * public void setExisthosp(boolean existhosp) { this.existhosp = existhosp;
	 * }
	 * 
	 * public boolean isExistoper() { return existoper; }
	 * 
	 * public void setExistoper(boolean existoper) { this.existoper = existoper;
	 * }
	 * 
	 * public boolean isExistmedi() { return existmedi; }
	 * 
	 * public void setExistmedi(boolean existmedi) { this.existmedi = existmedi;
	 * }
	 * 
	 * public boolean isExistdeath() { return existdeath; }
	 * 
	 * public void setExistdeath(boolean existdeath) { this.existdeath =
	 * existdeath; }
	 * 
	 * public double getBeneTotalClaimAmount() { return beneTotalClaimAmount; }
	 * 
	 * public void setBeneTotalClaimAmount(double beneTotalClaimAmount) {
	 * this.beneTotalClaimAmount = beneTotalClaimAmount; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * public List<MedicalClaimDTO> getMedicalClaimList() { return
	 * medicalClaimList; }
	 * 
	 * public void setMedicalClaimList(List<MedicalClaimDTO> medicalClaimList) {
	 * this.medicalClaimList = medicalClaimList; }
	 * 
	 * public List<HospitalizedClaimICD10> getHospitalizedReasonList() { return
	 * hospitalizedReasonList; }
	 * 
	 * public void setHospitalizedReasonList(List< HospitalizedClaimICD10>
	 * hospitalizedReasonList) { this.hospitalizedReasonList =
	 * hospitalizedReasonList; }
	 * 
	 * public boolean isPermitHospitalization() { return permitHospitalization;
	 * }
	 * 
	 * public void setPermitHospitalization(boolean permitHospitalization) {
	 * this.permitHospitalization = permitHospitalization; }
	 * 
	 * public boolean isPermitOperation() { return permitOperation; }
	 * 
	 * public void setPermitOperation(boolean permitOperation) {
	 * this.permitOperation = permitOperation; }
	 * 
	 * public boolean isPermitMedication() { return permitMedication; }
	 * 
	 * public void setPermitMedication(boolean permitMedication) {
	 * this.permitMedication = permitMedication; }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_CLAIM_INFORM,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 */
}