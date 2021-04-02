package org.ace.insurance.medical.claim.frontService.confirmed;

import javax.annotation.Resource;

import org.ace.insurance.medical.claim.frontService.confirmed.interfaces.IConfirmedMedicalClaimFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimProposalDAO;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "ConfirmedMedicalClaimFrontService")
public class ConfirmedMedicalClaimFrontService extends BaseService implements IConfirmedMedicalClaimFrontService {
	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	@Resource(name = "MedicalClaimProposalDAO")
	private IMedicalClaimProposalDAO medicalClaimProposalDAO;

	@Resource(name = "MedicalPolicyDAO")
	private IMedicalPolicyDAO medicalPolicyDAO;
//MZP
	// @Transactional(propagation = Propagation.REQUIRED)
	// public Payment confirmMedicalClaimProposal(MedicalClaimProposalDTO
	// medicalClaimProposalDTO, WorkFlowDTO workflowDTO, PaymentDTO paymentDTO)
	// {
	//
	// Payment payment = null;
	// try {
	//
	// payment = new Payment();
	// payment.setConfirmDate(new Date());
	// payment.setBank(paymentDTO.getBank());
	// payment.setBankAccountNo(paymentDTO.getBankAccountNo());
	// payment.setChequeNo(paymentDTO.getChequeNo());
	// payment.setPaymentChannel(paymentDTO.getPaymentChannel());
	// payment.setServicesCharges(paymentDTO.getServicesCharges());
	// payment.setClaimAmount(paymentDTO.getClaimAmount());
	// payment.setReferenceNo(medicalClaimProposalDTO.getId());
	// payment.setReferenceType(PolicyReferenceType.HEALTH_CLAIM);
	// medicalClaimProposalDTO.setTotalAllBeneAmt(paymentDTO.getTotalClaimAmount());
	// medicalClaimProposalDAO.update(MedicalClaimProposalFactory.createMedicalClaimProposal(medicalClaimProposalDTO));
	// paymentService.prePaymentAndTLFMedical(payment, medicalClaimProposalDTO);
	// workFlowDTOService.updateWorkFlow(workflowDTO);
	//
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to confirm Life
	// Claim", e);
	// }
	// return payment;
	// }
}
