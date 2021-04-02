package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.policy.BeneficiaryStatus;
import org.ace.java.component.persistence.exception.DAOException;
/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

public interface ILifeClaimBeneficiaryDAO {
	public void insert(LifeClaimBeneficiary ClaimBeneficiary) throws DAOException;
	public void update(LifeClaimBeneficiary ClaimBeneficiary) throws DAOException;
	public void delete(LifeClaimBeneficiary ClaimBeneficiary) throws DAOException;
	public LifeClaimBeneficiary findById(String id) throws DAOException;
	public List<LifeClaimBeneficiary> findAll() throws DAOException;
	public void addAttachment(LifeClaimInsuredPerson claimInsuredPerson) throws DAOException;
	public void updateApprovalStatus(boolean approvalStatus, boolean needSomeDocument, String id) throws DAOException;
	public void updateBeneficiaryStatus(BeneficiaryStatus beneficiaryStatus, String id) throws DAOException;
	public void updateClaimAmount(int claimAmount, String id) throws DAOException;
	public LifeClaimBeneficiary findByRefundNo(String refundNo) throws DAOException;
	public List<LifeClaimBeneficiary> findBySuccessorId(String successorId) throws DAOException;
	public List<LifeClaimInsuredPersonBeneficiary> findByInsuredRelationshipId(String relationshipId) throws DAOException;
}
