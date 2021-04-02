package org.ace.insurance.life.proposalhistory.service.interfaces;

import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposalhistory.LifeProposalHistory;

public interface ILifeProposalHistoryService {
	public LifeProposalHistory addNewLifeProposalHistory(LifeProposalHistory lifeProposalHistory, WorkFlowDTO workFlowDTO, String status);
	public void addNewLifeProposalHistory(LifeProposal lifeProposal);
	public LifeProposalHistory updateLifeProposalHistory(LifeProposalHistory lifeProposalHistory,WorkFlowDTO workFlowDTO);
	public void deleteLifeProposalHistory(LifeProposalHistory lifeProposalHistory);
}
