package org.ace.insurance.medical.claim.frontService.approved;

/***************************************************************************************
 * @author <<Thazin Aye>>
 * @Date 2014-8-14
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
import javax.annotation.Resource;

import org.ace.insurance.medical.claim.frontService.approved.interfaces.IApprovedMedicalClaimFrontService;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimProposalDAO;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;

@Service(value = "ApprovedMedicalClaimFrontService")
public class ApprovedMedicalClaimFrontService extends BaseService implements IApprovedMedicalClaimFrontService {
	@Resource(name = "MedicalClaimProposalDAO")
	private IMedicalClaimProposalDAO medicalClaimProposalDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Resource(name = "PaymentService")
	private IPaymentService paymentService;

	/**
	 * @see org.ace.insurance.medical.claim.service.interfaces.IMedicalClaimService
	 *      #informClaim(org.ace.insurance.medical.claim.MedicalClaim,org.ace.insurance.common.WorkFlowDTO,org.ace.insurance.accept.AcceptedInfo,String)
	 */
	//MZP
	// @Transactional(propagation = Propagation.REQUIRED)
	// public void approveMedicalClaim(MedicalClaimProposalDTO claimProposalDTO,
	// WorkFlowDTO workflowDTO) {
	// try {
	// workFlowDTOService.updateWorkFlow(workflowDTO);
	// medicalClaimProposalDAO.update(MedicalClaimProposalFactory.createMedicalClaimProposal(claimProposalDTO));
	// } catch (DAOException e) {
	// throw new SystemException(e.getErrorCode(), "Faield to update a
	// MedicalClaim", e);
	// }
	// }
}
