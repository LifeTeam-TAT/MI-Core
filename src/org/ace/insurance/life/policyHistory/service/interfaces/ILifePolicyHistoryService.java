package org.ace.insurance.life.policyHistory.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;

public interface ILifePolicyHistoryService {
	public void addNewLifePolicy(LifePolicy lifePolicy, PolicyStatus status, PolicyHistoryEntryType entryType);

	public void updateLifePolicy(LifePolicyHistory lifePolicy);

	public LifePolicyHistory findLifePolicyById(String id);

	public List<LifePolicyHistory> findLifePolicyByReceiptNo(String receiptNo);

	public List<LifePolicyHistory> findAllLifePolicy();

	public List<LifePolicyHistory> findAllActiveLifePolicy();

	public List<LifePolicyHistory> findByDate(Date startDate, Date endDate);

	public void increaseLifePolicyPrintCount(String lifeProposalId);

	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId);

	public List<LifePolicyHistory> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria);

	public List<LifePolicyHistory> findLifePolicyByPolicyNo(String policyNo);

	public PolicyInsuredPersonHistory findInsuredPersonByCodeNo(String codeNo, String policyId);

	public PolicyInsuredPersonBeneficiariesHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonId);

	/**
	 * Retrieve from the repository all life policies which are required for the
	 * Co-insurance.
	 * 
	 * @return {@link List} of {@link LifePolicyHistory} instances
	 */
	public List<LifePolicyHistory> findLifePoliciesRequiredForCoinsurance();

	/**
	 * Retrieve from the repository all life policies which policyNo is equal
	 * parameter..
	 * 
	 * @return {@link List} of {@link LifePolicyHistory} instances
	 */
	public List<LifePolicyHistory> findLifePolicyHistoryByPolicyNo(String policyNo);

	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType);

	public LifePolicyHistory findByPolicyReferenceNo(String policyID);

}
