package org.ace.insurance.web.manage.medical.claim.confirm;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

/***************************************************************************************
 * @author KMH
 * @Date 2015-07-09
 * @Version 1.0
 * @Purpose This class serves as the Presentation Layer to manipulate the
 *          <code>MedicalClaim</code> approved process.
 * 
 ***************************************************************************************/
@ViewScoped
@ManagedBean(name = "MedicalClaimConfirmationActionBean")
public class MedicalClaimConfirmationActionBean extends BaseBean implements Serializable {
	// TODO FIXME PSH Claim Case
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @ManagedProperty(value = "#{ConfirmedMedicalClaimFrontService}") private
	 * IConfirmedMedicalClaimFrontService confirmedMedicalClaimFrontService;
	 * 
	 * public void
	 * setConfirmedMedicalClaimFrontService(IConfirmedMedicalClaimFrontService
	 * confirmedMedicalClaimFrontService) {
	 * this.confirmedMedicalClaimFrontService =
	 * confirmedMedicalClaimFrontService; }
	 * 
	 * @ManagedProperty(value = "#{ClaimAcceptedInfoService}") private
	 * IClaimAcceptedInfoService claimAcceptedInfoService;
	 * 
	 * public void setClaimAcceptedInfoService(IClaimAcceptedInfoService
	 * claimAcceptedInfoService) { this.claimAcceptedInfoService =
	 * claimAcceptedInfoService; }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * private User user; private MedicalClaimProposalDTO
	 * medicalClaimProposalDTO; private PaymentDTO paymentDTO; private
	 * ClaimAcceptedInfo acceptedInfo; private Payment payment; private User
	 * responsiblePerson; private boolean showFormFlg = true; private boolean
	 * isCheque; private boolean btnOkFlag; private boolean approveProposal;
	 * private boolean showPreview; private String remark; private double
	 * hospAmount; private double deathAmount;
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalClaimProposalDTO =
	 * (medicalClaimProposalDTO == null) ? (MedicalClaimProposalDTO)
	 * getParam("medicalClaimProposal") : medicalClaimProposalDTO; }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalClaimProposal");
	 * }
	 * 
	 * @PostConstruct public void init() { initializeInjection();
	 * approveProposal = getApprovedStatus(); paymentDTO = new PaymentDTO();
	 * acceptedInfo =
	 * claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo(
	 * medicalClaimProposalDTO.getId(), ReferenceType.MEDICAL_CLAIM);
	 * paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
	 * paymentDTO.setClaimAmount(acceptedInfo.getClaimAmount()); }
	 * 
	 * private boolean getApprovedStatus() { if
	 * (medicalClaimProposalDTO.isHospitalized()) { if
	 * (medicalClaimProposalDTO.getHospitalizedClaimDTO().isApproved()) { return
	 * true; } }
	 * 
	 * if (medicalClaimProposalDTO.isOperation()) { if
	 * (medicalClaimProposalDTO.getOperationClaimDTO().isApproved()) { return
	 * true; } }
	 * 
	 * if (medicalClaimProposalDTO.isMedication()) { if
	 * (medicalClaimProposalDTO.getMedicationClaimDTO().isApproved()) { return
	 * true; } }
	 * 
	 * if (medicalClaimProposalDTO.isDeath()) { if
	 * (medicalClaimProposalDTO.getDeathClaimDTO().isApproved()) { return true;
	 * } }
	 * 
	 * return false; }
	 * 
	 * public String editMedicalClaim() { putParam("medicalClaimProposalDTO",
	 * medicalClaimProposalDTO); return "editHospitalizedClaimRequestForm"; }
	 * 
	 * public void confirm() { String formID =
	 * "confirmaitonMedicalClaimProposalForm"; Boolean valid = true; if
	 * (responsiblePerson == null) { addErrorMessage(formID +
	 * ":responsiblePerson", UIInput.REQUIRED_MESSAGE_ID); valid = false; } if
	 * (!valid) { showFormFlg = true; } else { showFormFlg = false; } }
	 * 
	 * public void confirmClaimBeneficiary() { btnOkFlag = true; try { if
	 * (isCheque) { paymentDTO.setPaymentChannel(PaymentChannel.CHEQUE); } else
	 * { paymentDTO.setPaymentChannel(PaymentChannel.CASHED); } WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO(medicalClaimProposalDTO.getId(), remark,
	 * WorkflowTask.MEDICAL_CLAIM_PAYMENT, ReferenceType.MEDICAL_CLAIM, user,
	 * responsiblePerson); payment =
	 * confirmedMedicalClaimFrontService.confirmMedicalClaimProposal(
	 * medicalClaimProposalDTO, workFlowDTO, paymentDTO); addInfoMessage(null,
	 * "CONFIRMATION_PROCESS_SUCCESS_REFUND_PARAM",
	 * medicalClaimProposalDTO.getClaimRequestId()); showPreview = true; } catch
	 * (SystemException ex) { handelSysException(ex); } }
	 * 
	 * public void changePaymentChannel(AjaxBehaviorEvent event) { if
	 * (!isCheque) { paymentDTO.setBank(null); paymentDTO.setChequeNo(null); } }
	 * 
	 * public String getReportStream() { return pdfDirPathCashReceive +
	 * fileNameCashReceive; }
	 * 
	 * private final String reportNameCashReceive =
	 * "MedicalCashReceiveClaimEnquiry"; private final String
	 * pdfDirPathCashReceive = "/pdf-report/" + reportNameCashReceive + "/" +
	 * System.currentTimeMillis() + "/"; private final String dirPathCashReceive
	 * = getSystemPath() + pdfDirPathCashReceive; private final String
	 * fileNameCashReceive = reportNameCashReceive + ".pdf";
	 * 
	 * public void generateCashReceiveReport() {
	 * MedicalUnderwritingDocFactory.generateMedicalClaimCashReceipt(
	 * medicalClaimProposalDTO, payment, dirPathCashReceive,
	 * fileNameCashReceive); }
	 * 
	 * public Payment getPayment() { return payment; }
	 * 
	 * public void setPayment(Payment payment) { this.payment = payment; }
	 * 
	 * public boolean isShowPreview() { return showPreview; }
	 * 
	 * public void setShowPreview(boolean showPreview) { this.showPreview =
	 * showPreview; }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public void returnBank(SelectEvent event) { Bank bank = (Bank)
	 * event.getObject(); paymentDTO.setBank(bank); }
	 * 
	 * public ClaimAcceptedInfo getAcceptedInfo() { return acceptedInfo; }
	 * 
	 * public boolean isBtnOkFlag() { return btnOkFlag; }
	 * 
	 * public boolean isShowFormFlg() { return showFormFlg; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setAcceptedInfo(ClaimAcceptedInfo acceptedInfo) {
	 * this.acceptedInfo = acceptedInfo; }
	 * 
	 * public void setBtnOkFlag(boolean btnOkFlag) { this.btnOkFlag = btnOkFlag;
	 * }
	 * 
	 * public void setShowFormFlg(boolean showFormFlg) { this.showFormFlg =
	 * showFormFlg; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public boolean isCheque() { return isCheque; }
	 * 
	 * public double getHospAmount() { return hospAmount; }
	 * 
	 * public double getDeathAmount() { return deathAmount; }
	 * 
	 * public void setCheque(boolean isCheque) { this.isCheque = isCheque; }
	 * 
	 * public void setHospAmount(double hospAmount) { this.hospAmount =
	 * hospAmount; }
	 * 
	 * public void setDeathAmount(double deathAmount) { this.deathAmount =
	 * deathAmount; }
	 * 
	 * public boolean isApproveProposal() { return approveProposal; }
	 * 
	 * public void setApproveProposal(boolean approveProposal) {
	 * this.approveProposal = approveProposal; }
	 * 
	 * public MedicalClaimProposalDTO getMedicalClaimProposalDTO() { return
	 * medicalClaimProposalDTO; }
	 * 
	 * public PaymentDTO getPaymentDTO() { return paymentDTO; }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowHistoryList() { return
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalClaimProposalDTO.getId(
	 * )); }
	 * 
	 * public void setMedicalClaimProposalDTO(MedicalClaimProposalDTO
	 * medicalClaimProposalDTO) { this.medicalClaimProposalDTO =
	 * medicalClaimProposalDTO; }
	 * 
	 * public void setPaymentDTO(PaymentDTO paymentDTO) { this.paymentDTO =
	 * paymentDTO; }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_CLAIM_PAYMENT,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void handleClose(CloseEvent event) { try {
	 * org.ace.insurance.web.util.FileHandler.forceDelete(dirPathCashReceive); }
	 * catch (IOException e) { e.printStackTrace(); } }
	 */}
