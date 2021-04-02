package org.ace.insurance.web.manage.medical.renewal.confirm;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "MedicalRenewalConfirmActionBean")
public class MedicalRenewalConfirmActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case
	/*
	 * @ManagedProperty(value = "#{MedicalProposalService}") private
	 * IMedicalProposalService medicalProposalService;
	 * 
	 * public void setMedicalProposalService(IMedicalProposalService
	 * medicalProposalService) { this.medicalProposalService =
	 * medicalProposalService; }
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
	 * private User user; private MedProDTO medProDTO; private MedicalProposal
	 * medicalProposal; private WorkFlowDTO workFlowDTO; private boolean
	 * approvedProposal = true; private String remark; private User
	 * responsiblePerson;
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalProposal = (medicalProposal
	 * == null) ? (MedicalProposal) getParam("medicalProposal") :
	 * medicalProposal; workFlowDTO = (workFlowDTO == null) ? (WorkFlowDTO)
	 * getParam("workFlowDTO") : workFlowDTO; }
	 * 
	 * @PostConstruct public void init() { initializeInjection(); medProDTO =
	 * MedicalProposalFactory.getMedProDTO(medicalProposal); for
	 * (MedicalProposalInsuredPerson person :
	 * medicalProposal.getMedicalProposalInsuredPersonList()) { if
	 * (!person.isApproved()) { approvedProposal = false; break; } }
	 * 
	 * }
	 * 
	 * public MedProDTO getMedProDTO() { return medProDTO; }
	 * 
	 * public void setMedProDTO(MedProDTO medProDTO) { this.medProDTO =
	 * medProDTO; }
	 * 
	 * public boolean isApprovedProposal() { return approvedProposal; }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId()); }
	 * 
	 * public MedicalProposal getMedicalProposal() { return medicalProposal; }
	 * 
	 * public void setMedicalProposal(MedicalProposal medicalProposal) {
	 * this.medicalProposal = medicalProposal; }
	 * 
	 * public String confirmMedicalProposal() { if (responsiblePerson == null) {
	 * addErrorMessage("medicalConfirmaitonForm:responsiblePerson",
	 * UIInput.REQUIRED_MESSAGE_ID); return null; } WorkflowTask workflowTask =
	 * medicalProposal.isSkipPaymentTLF() ? WorkflowTask.RENEWAL_ISSUING :
	 * WorkflowTask.RENEWAL_PAYMENT; workFlowDTO = new
	 * WorkFlowDTO(medicalProposal.getId(), remark, workflowTask,
	 * ReferenceType.MEDICAL_RENEWAL_PROPOSAL, user, responsiblePerson);
	 * outjectMedicalProposal(medicalProposal); outjectWorkFlowDTO(workFlowDTO);
	 * return "medicalRenewalConfirmPrint"; }
	 * 
	 * public String editMedicalProposal() {
	 * outjectMedicalProposal(medicalProposal); return
	 * "confirmEditMedicalRenewalProposal"; }
	 * 
	 * public String denyMedicalProposal() { String result = null; try { if
	 * (responsiblePerson == null) { responsiblePerson = user; } if
	 * (responsiblePerson == null) { responsiblePerson = user; } WorkFlowDTO
	 * workFlowDTO = new WorkFlowDTO(medicalProposal.getId(), remark,
	 * WorkflowTask.PROPOSAL_REJECT, ReferenceType.MEDICAL_RENEWAL_PROPOSAL,
	 * user, responsiblePerson);
	 * medicalProposalService.rejectMedicalProposal(medicalProposal,
	 * workFlowDTO); outjectMedicalProposal(medicalProposal);
	 * outjectWorkFlowDTO(workFlowDTO); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.DENY_PROCESS_OK);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalProposal.getProposalNo()); result = "dashboard"; } catch
	 * (SystemException ex) { handelSysException(ex); } return result; }
	 * 
	 *//*************************************************
		 * Responsible Person Criteria
		 **********************************************/
	/*
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public void setResponsiblePerson(User responsiblePerson) {
	 * this.responsiblePerson = responsiblePerson; }
	 * 
	 * private void outjectMedicalProposal(MedicalProposal medicalProposal) {
	 * putParam("editMedicalRenewalProposal", medicalProposal); }
	 * 
	 * private void outjectWorkFlowDTO(WorkFlowDTO workFlowDTO) {
	 * putParam("workFlowDTO", workFlowDTO); }
	 * 
	 * public void selectUser() { WorkflowTask workflowTask =
	 * medicalProposal.isSkipPaymentTLF() ? WorkflowTask.MEDICAL_ISSUE :
	 * WorkflowTask.MEDICAL_PAYMENT; selectUser(workflowTask,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 */}
