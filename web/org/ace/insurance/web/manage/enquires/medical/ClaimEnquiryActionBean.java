package org.ace.insurance.web.manage.enquires.medical;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "ClaimEnquiryActionBean")
public class ClaimEnquiryActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH Claim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @ManagedProperty(value = "#{PaymentService}") private IPaymentService
	 * paymentService;
	 * 
	 * public void setPaymentService(IPaymentService paymentService) {
	 * this.paymentService = paymentService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalClaimProposalService}") private
	 * IMedicalClaimProposalService medicalClaimProposalService;
	 * 
	 * public void setMedicalClaimProposalService(IMedicalClaimProposalService
	 * medicalClaimProposalService) { this.medicalClaimProposalService =
	 * medicalClaimProposalService; }
	 * 
	 * @ManagedProperty(value = "#{MedicalPolicyService}") private
	 * IMedicalPolicyService medicalPolicyService;
	 * 
	 * public void setMedicalPolicyService(IMedicalPolicyService
	 * medicalPolicyService) { this.medicalPolicyService = medicalPolicyService;
	 * }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * @ManagedProperty(value = "#{ClaimAcceptedInfoService}") private
	 * IClaimAcceptedInfoService acceptedInfoService;
	 * 
	 * public void setAcceptedInfoService(IClaimAcceptedInfoService
	 * acceptedInfoService) { this.acceptedInfoService = acceptedInfoService; }
	 * 
	 * private ClaimEnquiryCriteria criteria; private List<MC001> proposalList;
	 * private List<Product> productList; private MedicalClaimProposalDTO
	 * medicalClaimProposalDTO; private MedicalClaimProposal
	 * medicalClaimProposal; private List<WorkFlowHistory> workflowList; private
	 * MC001 selectedProposal;
	 * 
	 * @PostConstruct public void init() { proposalList = new
	 * ArrayList<MC001>(); resetCriteria(); selectedProposal = new MC001(); }
	 * 
	 * public void resetCriteria() { criteria = new ClaimEnquiryCriteria();
	 * Calendar cal = Calendar.getInstance(); cal.add(Calendar.DAY_OF_MONTH,
	 * -7); criteria.setStartDate(cal.getTime()); Date endDate = new Date();
	 * criteria.setEndDate(endDate); proposalList =
	 * medicalClaimProposalService.findAllMedicalClaimProposal(criteria); }
	 * 
	 * public ClaimEnquiryCriteria getCriteria() { return criteria; }
	 * 
	 * public void setCriteria(ClaimEnquiryCriteria criteria) { this.criteria =
	 * criteria; }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public MC001 getSelectedProposal() { return selectedProposal; }
	 * 
	 * public void setSelectedProposal(MC001 selectedProposal) {
	 * this.selectedProposal = selectedProposal; }
	 * 
	 * private void showInformationDialog(String msg) { this.message = msg;
	 * RequestContext.getCurrentInstance().execute("informationDialog.show()");
	 * }
	 * 
	 * private void showPdfDialog() {
	 * RequestContext.getCurrentInstance().execute("pdfPreviewDialog.show()"); }
	 * 
	 *//**
		 * for inform letter
		 */
	/*
	 * private final String reportName = "MedicalClaimEnquiryDocument"; private
	 * final String pdfDirPath = "/pdf-report/" + reportName + "/" +
	 * System.currentTimeMillis() + "/"; private final String dirPath =
	 * getSystemPath() + pdfDirPath; private final String fileName = reportName
	 * + ".pdf"; Payment payment;
	 * 
	 * private boolean allowToPrint(String id, WorkflowTask... workflowTasks) {
	 * List<WorkFlowHistory> wlfList =
	 * workFlowService.findWorkFlowHistoryByRefNo(id, workflowTasks); if
	 * (wlfList.isEmpty() || wlfList == null) { return false; } else { return
	 * true; } }
	 * 
	 * public void generateReport(MC001 mClaimProposal) { if
	 * (allowToPrint(mClaimProposal.getId(), WorkflowTask.APPROVAL)) {
	 * medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(mClaimProposal.
	 * getId()); medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } } if
	 * (!((medicalClaimProposalDTO.isHospitalized() &&
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isMedication() &&
	 * medicalClaimProposalDTO.getMedicationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isOperation() &&
	 * medicalClaimProposalDTO.getOperationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isDeath() &&
	 * medicalClaimProposalDTO.getDeathClaimDTO().isApproved()))) {
	 * MedicalUnderwritingDocFactory.generateMedicalClaimRejectLetter(
	 * medicalClaimProposalDTO, dirPath, fileName); showPdfDialog(); } else {
	 * MedicalUnderwritingDocFactory.generateMedicalClaimAcceptanceLetter(
	 * medicalClaimProposalDTO, dirPath, fileName); showPdfDialog(); } } else {
	 * showInformationDialog("Cann't print accepted letter."); } }
	 * 
	 * // Cash Receipt public void generateCashReceiptReport(MC001 medicalClaim)
	 * { if (allowToPrint(medicalClaim.getId(), WorkflowTask.APPROVAL)) {
	 * medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(medicalClaim.
	 * getId()); medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } } if
	 * (!((medicalClaimProposalDTO.isHospitalized() &&
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isMedication() &&
	 * medicalClaimProposalDTO.getMedicationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isOperation() &&
	 * medicalClaimProposalDTO.getOperationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isDeath() &&
	 * medicalClaimProposalDTO.getDeathClaimDTO().isApproved()))) {
	 * showInformationDialog("The claim does not finish payment process."); }
	 * else { payment =
	 * paymentService.findClaimProposal(medicalClaimProposal.getId(),
	 * PolicyReferenceType.HEALTH_CLAIM, false);
	 * MedicalUnderwritingDocFactory.generateMedicalClaimCashReceipt(
	 * medicalClaimProposalDTO, payment, dirPath, fileName); showPdfDialog(); }
	 * } else {
	 * showInformationDialog("The claim does not finish payment process."); } }
	 * 
	 * public void generateDocument(MC001 medicalClaim) { ClaimAcceptedInfo
	 * claimAcceptedInfo = null; payment = null; medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(medicalClaim.
	 * getId()); medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } } List<WorkFlowHistory> wfhList
	 * = workFlowService.findWorkFlowHistoryByRefNo(medicalClaim.getId());
	 * Set<WorkflowTask> workflowTasks = new HashSet<WorkflowTask>(); for
	 * (WorkFlowHistory history : wfhList) {
	 * workflowTasks.add(history.getWorkflowTask()); } boolean isInform = false;
	 * boolean isCashReceipt = false;
	 * 
	 * if (workflowTasks.contains(WorkflowTask.PAYMENT)) { isInform = true;
	 * isCashReceipt = true; } else if
	 * (workflowTasks.contains(WorkflowTask.CONFIRMATION)) { isInform = true;
	 * isCashReceipt = false; }
	 * 
	 * if (isInform) { claimAcceptedInfo =
	 * acceptedInfoService.findClaimAcceptedInfoByReferenceNo(
	 * medicalClaimProposalDTO.getId(), ReferenceType.HEALTH); if
	 * (isCashReceipt) { payment =
	 * paymentService.findClaimProposal(medicalClaimProposalDTO.getId(),
	 * PolicyReferenceType.HEALTH_CLAIM, false); } }
	 * MedicalUnderwritingDocFactory.generateAllMedicalClaimDocument(
	 * medicalClaimProposalDTO, claimAcceptedInfo, payment, isInform,
	 * isCashReceipt, dirPath, fileName); showPdfDialog(); }
	 * 
	 * public String getReportStream() { return pdfDirPath + fileName; }
	 * 
	 * public void handleClose(CloseEvent event) { try {
	 * org.ace.insurance.web.util.FileHandler.forceDelete(dirPath); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 *//**
		 * for proposal details
		 */
	/*
	 * 
	 * public List<WorkFlowHistory> getWorkflowList() { return workflowList; }
	 * 
	 * public void setWorkflowList(List<WorkFlowHistory> workflowList) {
	 * this.workflowList = workflowList; }
	 * 
	 *//**
		 * for inform accepted letter
		 */
	/*
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalClaimProposalDTO.getId(
	 * )); }
	 * 
	 * private String message;
	 * 
	 * public String getMessage() { return message; }
	 * 
	 * public void outjectMedicalProposal(MedicalProposal medicalProposal) {
	 * putParam("medicalProposal", medicalProposal); }
	 * 
	 * public void outjectMedicalClaimProposal(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { putParam("medicalClaimProposalDTO",
	 * medicalClaimProposalDTO); }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaim() {
	 * MedicalClaimProposalDTO medicalClaimProposalDTO = null;
	 * medicalClaimProposalDTO = MedicalClaimProposalFactory
	 * .createMedicalClaimProposalDTO(medicalClaimProposalService.
	 * findMedicalClaimProposalById(medicalClaimProposalDTO.getId())); return
	 * medicalClaimProposalDTO; }
	 * 
	 * public String medicalClaimEdit(MC001 claimProposal) {
	 * medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(claimProposal.
	 * getId()); medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); WorkFlow wf =
	 * workFlowService.findWorkFlowByRefNo(medicalClaimProposal.getId());
	 * 
	 * for (MedicalClaim mc : medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); } if
	 * (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } }
	 * outjectMedicalClaimProposal(medicalClaimProposalDTO); if (wf != null ||
	 * (medicalClaimProposalDTO.isHospitalized() &&
	 * !medicalClaimProposalDTO.getHospitalizedClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isOperation() &&
	 * !medicalClaimProposalDTO.getOperationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isMedication() &&
	 * !medicalClaimProposalDTO.getMedicationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isDeath() &&
	 * !medicalClaimProposalDTO.getDeathClaimDTO().isApproved())) {
	 * 
	 * return "medicalClaimEdit"; } else {
	 * showInformationDialog("The claim has been approved.");
	 * 
	 * return null; }
	 * 
	 * }
	 * 
	 * public List<MC001> getProposalList() { // RegNoSorter<MC001> regNoSorter
	 * = new // RegNoSorter<MC001>(proposalList); // List<MC001> proposalList =
	 * regNoSorter.getSortedList(); return proposalList; }
	 * 
	 *//**
		 * for edit medical claim proposal
		 * 
		 * @param medicalProposal
		 */
	/*
	 * 
	 * public void ShowDetailLifeProposal(MC001 medicalProposal) {
	 * MedicalClaimProposal medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(medicalProposal.
	 * getId()); this.medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); }
	 * else if (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } } }
	 * 
	 * public void findMedicalClaimProposalListByEnquiryCriteria() {
	 * this.proposalList = new ArrayList<MC001>(); if
	 * (criteria.getStartDate().before(criteria.getEndDate())) {
	 * this.proposalList =
	 * medicalClaimProposalService.findAllMedicalClaimProposal(criteria); if
	 * (proposalList.size() == 0) {
	 * addErrorMessage("claimEnquiryActionBeanDataTable" + ":claimEnquiryPanel",
	 * MessageId.NO_RECORDS_FOUND); } } }
	 * 
	 * public void setProposalList(List<MC001> proposalList) { this.proposalList
	 * = proposalList; }
	 * 
	 * public void returnAgent(SelectEvent event) { Agent agent = (Agent)
	 * event.getObject(); criteria.setAgent(agent); }
	 * 
	 * public void returnPolicyNo(SelectEvent event) { MedicalPolicy medPolicy =
	 * (MedicalPolicy) event.getObject(); criteria.setPolicyNumber(medPolicy); }
	 * 
	 * public void returnSaleMan(SelectEvent event) { SaleMan saleMan =
	 * (SaleMan) event.getObject(); criteria.setSaleMan(saleMan); }
	 * 
	 * public void returnBranch(SelectEvent event) { Branch branch = (Branch)
	 * event.getObject(); criteria.setBranch(branch); }
	 * 
	 * public List<Product> getProductList() { return productList; }
	 * 
	 * public void setProductList(List<Product> productList) { this.productList
	 * = productList; }
	 * 
	 * public MedicalClaimProposal getMedicalClaimProposal() { return
	 * medicalClaimProposal; }
	 * 
	 * public void setMedicalClaimProposal(MedicalClaimProposal
	 * medicalClaimProposal) { this.medicalClaimProposal = medicalClaimProposal;
	 * }
	 * 
	 * public void claimDetail(MC001 mc001) { workflowList =
	 * workFlowService.findWorkFlowHistoryByRefNo(mc001.getId());
	 * MedicalClaimProposal medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(mc001.getId());
	 * medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); }
	 * else if (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } }
	 * loadSurveyQuestionAnswer(medicalClaimProposalDTO); if
	 * (medicalClaimProposalDTO.getMedicalClaimBeneficiariesList() != null) {
	 * prepareBeneficiaryClaimAmount(); }
	 * 
	 * }
	 * 
	 * public boolean checkForApprovalPrint(MC001 mc001) { boolean approve =
	 * false; MedicalClaimProposal medicalClaimProposal =
	 * medicalClaimProposalService.findMedicalClaimProposalById(mc001.getId());
	 * medicalClaimProposalDTO =
	 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
	 * medicalClaimProposal); for (MedicalClaim mc :
	 * medicalClaimProposal.getMedicalClaimList()) { if
	 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)) {
	 * medicalClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
	 * findOperationClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM)) {
	 * medicalClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
	 * findDeathClaimById(mc.getId())); } else if
	 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)) {
	 * medicalClaimProposalDTO.setHospitalizedClaimDTO(
	 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); }
	 * else if (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)) {
	 * medicalClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
	 * .findMedicationClaimById(mc.getId())); } } if
	 * ((medicalClaimProposalDTO.isHospitalized() &&
	 * medicalClaimProposalDTO.getHospitalizedClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isMedication() &&
	 * medicalClaimProposalDTO.getMedicationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isOperation() &&
	 * medicalClaimProposalDTO.getOperationClaimDTO().isApproved()) ||
	 * (medicalClaimProposalDTO.isDeath() &&
	 * medicalClaimProposalDTO.getDeathClaimDTO().isApproved())) { approve =
	 * true;
	 * 
	 * } return approve; }
	 * 
	 * public void prepareBeneficiaryClaimAmount() { double total = 0.0; double
	 * hospAmount = 0.0; double operAmount = 0.0; double mediAmount = 0.0;
	 * double deathAmount = 0.0; for (MedicalClaimBeneficiaryDTO mcb :
	 * medicalClaimProposalDTO.getMedicalClaimBeneficiariesList()) { hospAmount
	 * += mcb.getHospClaimAmount(); operAmount += mcb.getOperClaimAmount();
	 * mediAmount += mcb.getMediClaimAmount(); deathAmount +=
	 * mcb.getDeathClaimAmount(); total += mcb.getClaimAmount(); }
	 * medicalClaimProposalDTO.setTotalHospAmt(hospAmount);
	 * medicalClaimProposalDTO.setTotalOperAmt(operAmount);
	 * medicalClaimProposalDTO.setTotalMediAmt(mediAmount);
	 * medicalClaimProposalDTO.setTotalDeathAmt(deathAmount);
	 * medicalClaimProposalDTO.setTotalAllBeneAmt(total); }
	 */}
