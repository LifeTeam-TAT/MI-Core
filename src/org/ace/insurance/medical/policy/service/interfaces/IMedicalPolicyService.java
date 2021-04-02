/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.policy.service.interfaces;

import java.util.List;

import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.medical.claim.MedicalPolicyCriteria;
import org.ace.insurance.medical.policy.MED002;
import org.ace.insurance.medical.policy.MPL001;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.proposal.MedicalProposal;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;

public interface IMedicalPolicyService {
	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria);

	public void addNewMedicalPolicy(MedicalPolicy medicalPolicy);

	public void updateMedicalPolicy(MedicalPolicy medicalPolicy);

	public void deleteMedicalPolicy(MedicalPolicy medicalPolicy);

	public int findMedicalPolicyInsuredPersonById(String id);

	public MedicalPolicy findMedicalPolicyById(String id);

	public List<MedicalPolicy> findAllMedicalPolicy();

	public MedicalPolicy findMedicalPolicyByProposalId(String proposalId);

	public MedicalPolicy findMedicalPolicyByPolicyNo(String policyNo);
	// TODO FIXME PSH Claim case
	// public List<MedicalPolicy>
	// findMedicalPolicyForClaimByCriteria(MedicalPolicyCriteria criteria);

	public List<MED002> findMedicalPolicyForPolicyEnquiry(EnquiryCriteria criteria);

	// public void updateMedicalPolicy(MedicalPolicy medicalPolicy, Payment
	// payment);

	public List<MedicalPolicy> findByCustomer(Customer customer);

	public void activateBillCollection(MedicalPolicy medicalPolicy);

	public List<MPL001> findMedicalPolicyByCriteria(MedicalPolicyCriteria criteria);

	// public List<PolicyNotificationDTO>
	// findPolicyNotification(NotificationCriteria criteria);

	public MedicalPolicy activateMedicalPolicy(MedicalProposal medicalProposal, double rate);

	public List<MedicalPolicy> findMedicalPolicyPOByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType);

	public void overwriteMedicalPolicy(MedicalPolicy medicalPolicy);

	public void updatePolicyAttachment(MedicalPolicy medicalPolicy);
}
