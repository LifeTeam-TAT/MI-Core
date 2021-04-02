package org.ace.insurance.life.policyEditHistory.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.life.policyEditHistory.LifePolicyEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonBeneficiariesEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifePolicyEditHistoryDAO {
	public void insert(LifePolicyEditHistory lifePolicy) throws DAOException;

	public void update(LifePolicyEditHistory lifePolicy) throws DAOException;

	public void delete(LifePolicyEditHistory lifePolicy) throws DAOException;

	public LifePolicyEditHistory findById(String id) throws DAOException;

	public List<LifePolicyEditHistory> findByProposalId(String proposalId) throws DAOException;

	public List<LifePolicyEditHistory> findByReceiptNo(String receiptNo) throws DAOException;

	public List<LifePolicyEditHistory> findAll() throws DAOException;

	public List<LifePolicyEditHistory> findAllActiveLifePolicy() throws DAOException;

	public List<LifePolicyEditHistory> findByDate(Date startDate, Date endDate) throws DAOException;

	public void increasePrintCount(String id) throws DAOException;

	public void updateCommenmanceDate(Date date, String id) throws DAOException;

	public List<LifePolicyEditHistory> findByEnquiryCriteria(EnquiryCriteria criteria) throws DAOException;

	public List<LifePolicyEditHistory> findByPolicyNo(String policyNo) throws DAOException;

	public PolicyInsuredPersonEditHistory findInsuredPersonByCodeNo(String codeNo, String policyId) throws DAOException;

	public PolicyInsuredPersonBeneficiariesEditHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonID) throws DAOException;

	/**
	 * Retrieve from the repository all life policies which are required for the
	 * Co-insurance.
	 * 
	 * @return {@link List} of {@link LifePolicyEditHistory} instances
	 */
	public List<LifePolicyEditHistory> findLifePoliciesRequiredForCoinsurance();

	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType) throws DAOException;
}
