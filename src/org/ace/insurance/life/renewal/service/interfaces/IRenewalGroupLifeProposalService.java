package org.ace.insurance.life.renewal.service.interfaces;

import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;

public interface IRenewalGroupLifeProposalService {
	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO);

	public LifeProposal renewalGroupLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String status);

	public LifeProposal updateRenewalGroupLifeProposalEnquiry(LifeProposal lifeProposal);

	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO);

	public void informProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status);

	public void rejectLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO);

	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status);

	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> paymentList, Branch branch, String status);

	public LifeProposal findLifeProposalById(String id);

	public void issueLifeProposal(LifeProposal lifeProposal);

	public LifeProposal calculatePremium(LifeProposal lifeProposal);

}
