package org.ace.insurance.life.surrender.service.interfaces;

import java.util.List;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.surrender.LifeSurrenderProposal;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;

/***************************************************************************************
 * @author PPA-00136
 * @Date 2016-03-03
 * @Version 1.0
 * @Purpose This class serves as the Data Access Service Interface For Life
 *          Surrender Proposal
 * 
 ***************************************************************************************/

public interface ILifeSurrenderProposalService {
	public LifeSurrenderProposal addNewLifeSurrenderProposal(LifeSurrenderProposal lifeSurrenderProposal, WorkFlowDTO workFlowDTO);

	public LifeSurrenderProposal updateLifeSurrenderProposal(LifeSurrenderProposal lifeSurrenderProposal, WorkFlowDTO workFlowDTO);

	public void updateLifeSurrenderProposal(LifeSurrenderProposal lifeSurrenderProposal);

	public void deleteLifeSurrenderProposal(LifeSurrenderProposal lifeSurrenderProposal);

	public LifeSurrenderProposal findLifeSurrenderProposalById(String id);

	public List<LifeSurrenderProposal> findAllLifeSurrenderProposal();

	public LifeSurrenderProposal findByProposalNo(String proposalNo);

	public void informLifeSurrender(LifeSurrenderProposal surrenderProposal, WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo);

	public void approveLifeSurrenderProposal(LifeSurrenderProposal surrenderProposal, WorkFlowDTO workFlowDTO);

	public void rejectLifeSurrenderProposal(LifeSurrenderProposal surrenderProposal, WorkFlowDTO workFlowDTO);

	public List<Payment> confirmLifeSurrenderProposal(LifeSurrenderProposal surrenderProposal, WorkFlowDTO workFlowDTO, PaymentDTO payment, Branch branch, PolicyStatus status);

	public void payLifeSurrender(List<Payment> paymentList, WorkFlowDTO workFlowDTO, Branch branch, String status);

	public void issueLifeSurrenderProposal(LifeSurrenderProposal proposal);

}
