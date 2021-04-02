package org.ace.insurance.web.manage.medical.renewal.inform;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "MedicalRenewalInformActionBean")
public class MedicalRenewalInformActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case
	/*
	 * @ManagedProperty(value = "#{MedicalProposalService}") private
	 * IMedicalProposalService medicalproposalService;
	 * 
	 * public void setMedicalproposalService(IMedicalProposalService
	 * medicalproposalService) { this.medicalproposalService =
	 * medicalproposalService; }
	 * 
	 * @ManagedProperty(value = "#{UserService}") private IUserService
	 * userService;
	 * 
	 * public void setUserService(IUserService userService) { this.userService =
	 * userService; }
	 * 
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * private User user; private MedicalProposal medicalProposal; private
	 * WorkFlowDTO workFlowDTO;
	 * 
	 * private boolean approvedProposal = true; private boolean disablePrintBtn
	 * = true; private String remark; private AcceptedInfo acceptedInfo; private
	 * MedProDTO medProDTO; private HealthType healthType;
	 * 
	 *//**
		 * for inform letter
		 */
	/*
	 * private final String reportNameAcceptedLetter =
	 * "MedicalProposalAcceptedLetter"; private final String
	 * pdfDirPathAcceptedLetter = "/pdf-report/" + reportNameAcceptedLetter +
	 * "/" + System.currentTimeMillis() + "/"; private final String
	 * dirPathAcceptedLetter = getSystemPath() + pdfDirPathAcceptedLetter;
	 * private final String fileNameAcceptedLetter = reportNameAcceptedLetter +
	 * ".pdf";
	 * 
	 * private final String reportNameRejectLetter =
	 * "MedicalProposalRejectLetter"; private final String
	 * pdfDirPathRejectLetter = "/pdf-report/" + reportNameRejectLetter + "/" +
	 * System.currentTimeMillis() + "/"; private final String
	 * dirPathRejectLetter = getSystemPath() + pdfDirPathRejectLetter; private
	 * final String fileNameRejectLetter = reportNameRejectLetter + ".pdf";
	 * 
	 * private User responsiblePerson;
	 * 
	 * public Date getCurrentDate() { return new Date(); }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalProposal = (medicalProposal
	 * == null) ? (MedicalProposal) getParam("medicalProposal") :
	 * medicalProposal; workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO)
	 * getParam("workFlowDTO") : workFlowDTO; healthType = (HealthType)
	 * getParam("WORKFLOWHEALTHTYPE"); }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalproposal");
	 * removeParam("WorkFlowDTO"); }
	 * 
	 * @PostConstruct public void init() { initializeInjection(); medProDTO =
	 * MedicalProposalFactory.getMedProDTO(medicalProposal); for
	 * (MedicalProposalInsuredPerson person :
	 * medicalProposal.getMedicalProposalInsuredPersonList()) { if
	 * (!person.isApproved()) { approvedProposal = false; break; } }
	 * 
	 * acceptedInfo = new AcceptedInfo();
	 * acceptedInfo.setReferenceNo(medicalProposal.getId());
	 * acceptedInfo.setReferenceType(ReferenceType.MEDICAL_RENEWAL_PROPOSAL);
	 * acceptedInfo.setBasicPremium(medicalProposal.getTotalApprovedPremium() +
	 * medicalProposal.getTotalBasicPlusPremium());
	 * acceptedInfo.setAddOnPremium(medicalProposal.getTotalAddOnPremium());
	 * acceptedInfo.setPaymentType(medicalProposal.getPaymentType());
	 * acceptedInfo.getNetPremium(); }
	 * 
	 * public void informApprovedMedicalProposal() { try { String formID =
	 * "informMedicalProposal"; if (responsiblePerson == null) {
	 * addErrorMessage(formID + ":responsiblePerson",
	 * UIInput.REQUIRED_MESSAGE_ID); return; } if
	 * (acceptedInfo.getServicesCharges() < 0) { addErrorMessage(formID +
	 * ":additionalCharges", UIInput.REQUIRED_MESSAGE_ID); return; } WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), remark,
	 * WorkflowTask.RENEWAL_CONFIRMATION,
	 * ReferenceType.MEDICAL_RENEWAL_PROPOSAL, user, responsiblePerson);
	 * medicalproposalService.informProposal(medicalProposal, workFlowDTO,
	 * acceptedInfo, RequestStatus.APPROVED.name());
	 * outjectMedicalProposal(medicalProposal); outjectWorkFlowDTO(workFlowDTO);
	 * addInfoMessage(null, MessageId.INFORM_PROCESS_SUCCESS_PARAM,
	 * medicalProposal.getProposalNo()); disablePrintBtn = false;
	 * 
	 * } catch (SystemException ex) { handelSysException(ex); } }
	 * 
	 * public void informRejectMedicalProposal() { try { String formID =
	 * "informMedicalProposal"; if (responsiblePerson == null) {
	 * addErrorMessage(formID + ":responsiblePerson",
	 * UIInput.REQUIRED_MESSAGE_ID); } WorkFlowDTO workFlowDTO = new
	 * WorkFlowDTO(medicalProposal.getId(), remark,
	 * WorkflowTask.RENEWAL_CONFIRMATION,
	 * ReferenceType.MEDICAL_RENEWAL_PROPOSAL, user, responsiblePerson);
	 * medicalproposalService.informProposal(medicalProposal, workFlowDTO, null,
	 * RequestStatus.REJECTED.name()); outjectMedicalProposal(medicalProposal);
	 * outjectWorkFlowDTO(workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.INFORM_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalProposal.getProposalNo()); disablePrintBtn = false; } catch
	 * (SystemException ex) { handelSysException(ex); } }
	 * 
	 * public String getReportStream() { if (approvedProposal) { return
	 * pdfDirPathAcceptedLetter + fileNameAcceptedLetter; } else { return
	 * pdfDirPathRejectLetter + fileNameRejectLetter; } }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId()); }
	 * 
	 * public void generateReport() { if (checkApproved()) {
	 * MedicalUnderwritingDocFactory.generateMedicalAcceptanceLetter(
	 * medicalProposal, acceptedInfo, dirPathAcceptedLetter,
	 * fileNameAcceptedLetter, healthType); } else {
	 * MedicalUnderwritingDocFactory.generateMedicalRejectLetter(
	 * medicalProposal, dirPathRejectLetter, fileNameRejectLetter, healthType);
	 * }
	 * 
	 * }
	 * 
	 * public boolean checkApproved() { for (MedicalProposalInsuredPerson person
	 * : medicalProposal.getMedicalProposalInsuredPersonList()) { if
	 * (person.isApproved()) { approvedProposal = true; } } return
	 * approvedProposal; }
	 * 
	 * public MedProDTO getMedProDTO() { return medProDTO; }
	 * 
	 * public void setMedProDTO(MedProDTO medProDTO) { this.medProDTO =
	 * medProDTO; }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public MedicalProposal getMedicalProposal() { return medicalProposal; }
	 * 
	 * public void setMedicalProposal(MedicalProposal medicalProposal) {
	 * this.medicalProposal = medicalProposal; }
	 * 
	 * public WorkFlowDTO getWorkFlowDTO() { return workFlowDTO; }
	 * 
	 * public void setWorkFlowDTO(WorkFlowDTO workFlowDTO) { this.workFlowDTO =
	 * workFlowDTO; }
	 * 
	 * public boolean isApprovedProposal() { return approvedProposal; }
	 * 
	 * public void setApprovedProposal(boolean approvedProposal) {
	 * this.approvedProposal = approvedProposal; }
	 * 
	 * public boolean isDisablePrintBtn() { return disablePrintBtn; }
	 * 
	 * public void setDisablePrintBtn(boolean disablePrintBtn) {
	 * this.disablePrintBtn = disablePrintBtn; }
	 * 
	 * public AcceptedInfo getAcceptedInfo() { return acceptedInfo; }
	 * 
	 * public void setAcceptedInfo(AcceptedInfo acceptedInfo) {
	 * this.acceptedInfo = acceptedInfo; }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * private void outjectMedicalProposal(MedicalProposal medicalProposal) {
	 * putParam("medicalProposal", medicalProposal); }
	 * 
	 * private void outjectWorkFlowDTO(WorkFlowDTO workFlowDTO) {
	 * putParam("workFlowDTO", workFlowDTO); }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_CONFIRMATION,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 * public void handleClose(CloseEvent event) { try {
	 * org.ace.insurance.web.util.FileHandler.forceDelete(getSystemPath() +
	 * "/pdf-report/MedicalProposalAcceptedLetter"); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 * 
	 */}
