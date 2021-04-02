package org.ace.insurance.web.manage.medical.renewal.payment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "MedicalRenewalPaymentActionBean")
public class MedicalRenewalPaymentActionBean extends BaseBean {
	// TODO FIXME PSH Renewal Case
	/*
	 * @ManagedProperty(value = "#{WorkFlowService}") private IWorkFlowService
	 * workFlowService;
	 * 
	 * public void setWorkFlowService(IWorkFlowService workFlowService) {
	 * this.workFlowService = workFlowService; }
	 * 
	 * @ManagedProperty(value = "#{PaymentService}") private IPaymentService
	 * paymentService;
	 * 
	 * public void setPaymentService(IPaymentService paymentService) {
	 * this.paymentService = paymentService; }
	 * 
	 * @ManagedProperty(value = "#{PaymentMedicalProposalFrontService}") private
	 * IPaymentMedicalProposalFrontService paymentMedicalProposalFrontService;
	 * 
	 * public void
	 * setPaymentMedicalProposalFrontService(IPaymentMedicalProposalFrontService
	 * paymentMedicalProposalFrontService) {
	 * this.paymentMedicalProposalFrontService =
	 * paymentMedicalProposalFrontService; }
	 * 
	 * private User user; private PaymentDTO paymentDTO; private boolean
	 * cashPayment = true; private String remark; private List<Payment>
	 * paymentList; private User responsiblePerson; private
	 * List<WorkFlowHistory> workflowHistoryList; private MedicalProposal
	 * medicalProposal;
	 * 
	 * private void initializeInjection() { user = (user == null) ? (User)
	 * getParam(Constants.LOGIN_USER) : user; medicalProposal = (medicalProposal
	 * == null) ? (MedicalProposal) getParam("medicalProposal") :
	 * medicalProposal; workflowHistoryList =
	 * workFlowService.findWorkFlowHistoryByRefNo(medicalProposal.getId()); }
	 * 
	 * @PreDestroy public void destroy() { removeParam("medicalProposal"); }
	 * 
	 * @PostConstruct public void init() { initializeInjection(); paymentList =
	 * paymentService.findByProposal(medicalProposal.getId(),
	 * PolicyReferenceType.MEDICAL_POLICY, false); paymentDTO = new
	 * PaymentDTO(paymentList); }
	 * 
	 * public String paymentMedicalProposal() { String result = null; try {
	 * String formID = "medicalPaymentForm"; if (responsiblePerson == null) {
	 * addErrorMessage(formID + ":responsiblePerson",
	 * UIInput.REQUIRED_MESSAGE_ID); return null; } WorkFlowDTO workFlowDTO =
	 * new WorkFlowDTO(medicalProposal.getId(), remark,
	 * WorkflowTask.RENEWAL_ISSUING, ReferenceType.MEDICAL_RENEWAL_PROPOSAL,
	 * user, responsiblePerson);
	 * paymentMedicalProposalFrontService.paymentMedicalProposal(
	 * medicalProposal, workFlowDTO, paymentList, user.getBranch(),
	 * RequestStatus.FINISHED.name()); ExternalContext extContext =
	 * getFacesContext().getExternalContext();
	 * extContext.getSessionMap().put(Constants.MESSAGE_ID,
	 * MessageId.PAYMENT_PROCESS_SUCCESS);
	 * extContext.getSessionMap().put(Constants.PROPOSAL_NO,
	 * medicalProposal.getProposalNo()); result = "dashboard"; } catch
	 * (SystemException ex) { handelSysException(ex); } return result; }
	 * 
	 * public boolean isCashPayment() { return cashPayment; }
	 * 
	 * public EnumSet<PaymentChannel> getPaymentChannels() { return
	 * EnumSet.of(PaymentChannel.CASHED, PaymentChannel.CHEQUE); }
	 * 
	 * public void changePaymentChannel(AjaxBehaviorEvent e) { PaymentChannel
	 * paymentChannel = (PaymentChannel) ((SelectOneMenu)
	 * e.getSource()).getValue(); if
	 * (paymentChannel.equals(PaymentChannel.CHEQUE)) { cashPayment = false; }
	 * else { cashPayment = true; } }
	 * 
	 * public User getResponsiblePerson() { return responsiblePerson; }
	 * 
	 * public MedicalProposal getMedicalProposal() { return medicalProposal; }
	 * 
	 * public MedProDTO getMedicalProposalDTO() { return
	 * MedicalProposalFactory.getMedProDTO(medicalProposal); }
	 * 
	 * public PaymentDTO getPayment() { return paymentDTO; }
	 * 
	 * public List<WorkFlowHistory> getWorkFlowList() { return
	 * workflowHistoryList; }
	 * 
	 * public String getRemark() { return remark; }
	 * 
	 * public void setRemark(String remark) { this.remark = remark; }
	 * 
	 * public void selectUser() { selectUser(WorkflowTask.MEDICAL_ISSUE,
	 * WorkFlowType.MEDICAL_INSURANCE); }
	 * 
	 * public void returnUser(SelectEvent event) { User user = (User)
	 * event.getObject(); this.responsiblePerson = user; }
	 * 
	 */}
