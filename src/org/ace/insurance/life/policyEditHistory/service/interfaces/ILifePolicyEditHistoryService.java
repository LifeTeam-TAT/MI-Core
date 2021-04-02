package org.ace.insurance.life.policyEditHistory.service.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policyEditHistory.LifePolicyEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonBeneficiariesEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;

public interface ILifePolicyEditHistoryService {
	public void addNewLifePolicy(LifePolicy lifePolicy, PolicyStatus status, PolicyHistoryEntryType entryType);

	public List<LifePolicyEditHistory> activateLifePolicy(String lifeProposalId);

	public void updateLifePolicy(LifePolicyEditHistory lifePolicy);

	public LifePolicyEditHistory findLifePolicyById(String id);

	public List<LifePolicyEditHistory> findLifePolicyByProposalNo(String proposalId);

	public List<LifePolicyEditHistory> findLifePolicyByReceiptNo(String receiptNo);

	public List<LifePolicyEditHistory> findAllLifePolicy();

	public List<LifePolicyEditHistory> findAllActiveLifePolicy();

	public List<LifePolicyEditHistory> findByDate(Date startDate, Date endDate);

	public void increaseLifePolicyPrintCount(String lifeProposalId);

	public void updateLifePolicyCommenmanceDate(Date date, String lifeProposalId);

	public List<LifePolicyEditHistory> findLifePolicyByEnquiryCriteria(EnquiryCriteria criteria);

	public List<LifePolicyEditHistory> findLifePolicyByPolicyNo(String policyNo);

	public PolicyInsuredPersonEditHistory findInsuredPersonByCodeNo(String codeNo, String policyId);

	public PolicyInsuredPersonBeneficiariesEditHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonId);

	/**
	 * Retrieve from the repository all life policies which are required for the
	 * Co-insurance.
	 * 
	 * @return {@link List} of {@link LifePolicyEditHistory} instances
	 */
	public List<LifePolicyEditHistory> findLifePoliciesRequiredForCoinsurance();

	/**
	 * Retrieve from the repository all life policies which policyNo is equal
	 * parameter..
	 * 
	 * @return {@link List} of {@link LifePolicyEditHistory} instances
	 */
	public List<LifePolicyEditHistory> findLifePolicyEditHistoryByPolicyNo(String policyNo);

	public List<LifePolicyEditHistory> findLifePolicyEditHistoryByProposalNo(String proposalId);

	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType);

}
