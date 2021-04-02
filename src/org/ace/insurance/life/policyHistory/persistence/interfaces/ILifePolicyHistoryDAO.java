package org.ace.insurance.life.policyHistory.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonBeneficiariesHistory;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonHistory;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyHistoryDAO {
	public void insert(LifePolicyHistory lifePolicy) throws DAOException;

	public void update(LifePolicyHistory lifePolicy) throws DAOException;

	public void delete(LifePolicyHistory lifePolicy) throws DAOException;

	public LifePolicyHistory findById(String id) throws DAOException;

	public LifePolicyHistory findByProposalId(String proposalId) throws DAOException;

	public List<LifePolicyHistory> findByReceiptNo(String receiptNo) throws DAOException;

	public List<LifePolicyHistory> findAll() throws DAOException;

	public List<LifePolicyHistory> findAllActiveLifePolicy() throws DAOException;

	public List<LifePolicyHistory> findByDate(Date startDate, Date endDate) throws DAOException;

	public void increasePrintCount(String id) throws DAOException;

	public void updateCommenmanceDate(Date date, String id) throws DAOException;

	public List<LifePolicyHistory> findByEnquiryCriteria(EnquiryCriteria criteria) throws DAOException;

	public List<LifePolicyHistory> findByPolicyNo(String policyNo) throws DAOException;

	public PolicyInsuredPersonHistory findInsuredPersonByCodeNo(String codeNo, String policyId) throws DAOException;

	public PolicyInsuredPersonBeneficiariesHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonID) throws DAOException;

	/**
	 * Retrieve from the repository all life policies which are required for the
	 * Co-insurance.
	 * 
	 * @return {@link List} of {@link LifePolicyHistory} instances
	 */
	public List<LifePolicyHistory> findLifePoliciesRequiredForCoinsurance();

	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType) throws DAOException;

	public LifePolicyHistory findByPolicyReferenceNo(String referenceNo) throws DAOException;
}
