package org.ace.insurance.life.renewal.service.interfaces;

import java.util.Date;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.proposal.LifeProposal;

public interface IRenewalGroupLifePolicyService {
	public PolicyInsuredPerson findInsuredPersonByCodeNo(String codeNo);

	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate);

	public LifePolicy findLifePolicyByProposalId(String proposalId);

	public void increaseLifePolicyPrintCount(String lifeProposalId);

	public void addNewLifePolicy(LifePolicy lifePolicy);

	public LifePolicy activateLifePolicy(LifeProposal lifeProposal);
}
