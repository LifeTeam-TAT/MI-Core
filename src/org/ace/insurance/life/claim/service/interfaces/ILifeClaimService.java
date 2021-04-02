package org.ace.insurance.life.claim.service.interfaces;

import java.util.List;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimAmount;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.payment.Payment;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifeClaimService {
	public LifeClaimBeneficiary findLifeClaimBeneficaryByRefundNo(String refundNo);

	public LifeClaim findLifeClaimByRequestId(String claimRequestId);

	public void addNewLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO);

	public LifeClaimAmount calculateClaimAmount(PolicyInsuredPerson policyInsuredPerson);

	public void approveLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO);

	public void informLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO, ClaimAcceptedInfo claimAcceptedInfo);

	public List<Payment> confirmLifeClaim(LifeClaim lifeClaim, WorkFlowDTO workflowDTO, PaymentDTO paymentDTO);

	public void payLifeClaim(LifeClaimBeneficiary claimBeneficiary, Payment payment);

	public LifeClaimInsuredPerson findLifeClaimInsuredPersonById(String policyInsuredPersonId);

	// public List<LCL001> findLifeClaimByEnquiryCriteria(LCL001 criteria);
	public String findStatusById(String id);

	public LifeClaim findLifeClaimPortalById(String id);
}
