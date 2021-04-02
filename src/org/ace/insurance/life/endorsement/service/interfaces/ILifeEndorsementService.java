package org.ace.insurance.life.endorsement.service.interfaces;

import java.util.List;

import org.ace.insurance.accept.AcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.SystemException;

public interface ILifeEndorsementService {

	/** used in proposal form and confirm stage */
	public LifeEndorseInfo preCalculatePremium(LifeProposal lifeProposal);

	/** used in proposal form */
	public LifeProposal addNewShortEndowLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, String name, LifeEndorseInfo lifeEndorseInfo) throws SystemException;

	/** used in proposal form */
	public LifeProposal addNewNonFinancialLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, String name) throws SystemException;

	/** used in proposal form */
	public LifeProposal addNewLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO, String status) throws SystemException;

	/** used in proposal form and Edit proposal form */
	public LifeProposal updateLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO);

	/** used in survey form */
	public void addNewSurvey(LifeSurvey lifeSurvey, WorkFlowDTO workFlowDTO);

	/** used in approved form */
	public void approveLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO);

	/** used in inform form */
	public void informLifeEndorse(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, AcceptedInfo acceptedInfo, String status);

	/** used in confirm form */
	public void rejectLifeProposal(LifeProposal lifeProposal, LifeEndorseInfo lifeEndorseInfo, WorkFlowDTO workFlowDTO);

	/** used in confirm form */
	public List<Payment> confirmLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, PaymentDTO paymentDTO, Branch branch, String status);

	/** used in payment form */
	public void paymentLifeProposal(LifeProposal lifeProposal, WorkFlowDTO workFlowDTO, List<Payment> payment, Branch branch, String status);

	/** used in issue form */
	public void issueLifeProposal(LifeProposal lifeProposal);

	/** used in endorse proposal detail */
	public LifeEndorseInfo findLastLifeEndorseInfoByPolicyId(String policyNo);

	/** used in endorse proposal detail */
	public LifeEndorseInfo findByEndorsePolicyReferenceNo(String policyId);

	/** used in lifeProposalService and RenewalGroupLifeProposalService */
	public LifeEndorseInfo updateLifeEndorseInfo(LifeProposal lifeProposal);

}
