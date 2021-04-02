package org.ace.insurance.life.paidUp.service.interfaces;

import java.util.List;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.paidUp.LifePaidUpProposal;

public interface ILifePaidUpProposalService {
	public LifePaidUpProposal addNewLifePaidUpProposal(LifePaidUpProposal proposal, WorkFlowDTO workFlowDTO);

	public LifePaidUpProposal updateLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workFlowDTO);

	public void deleteLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal);

	public List<LifePaidUpProposal> findAllLifePaidUpProposal();

	public LifePaidUpProposal findLifePaidUpProposalById(String id);

	public LifePaidUpProposal findByProposalNo(String proposalNo);

	public void approveLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workFlowDTO);

	public void informLifePaidUpProposal(LifePaidUpProposal lifePaidUpProposal, WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo);

	public void confirmLifePaidUpProposal(LifePaidUpProposal paidUpProposal, PolicyStatus status);

	public void rejectLifePaidUpProposal(LifePaidUpProposal paidUpProposal);

	public LifePaidUpProposal findByPolicyNo(String policyNo);

	public void updateCompleteStatus(String policyNo);
}
