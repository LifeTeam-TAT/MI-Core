package org.ace.insurance.life.endorsement.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.endorsement.LifeEndorseInfo;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.LifeSurvey;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeEndorsementDAO {

	/** used in Endorsement Service */
	public LifeEndorseInfo insert(LifeEndorseInfo lifeEndorseInfo) throws DAOException;

	/** used in Endorsement Service */
	public void deleteLifeEndorseInfo(LifeEndorseInfo lifeEndorseInfo) throws DAOException;

	/** used in Endorsement Service */
	public void updateEndorsePolicyReferenceNo(String oldPolicyId, String newPolicyId) throws DAOException;

	/** used in Endorsement Service */
	public LifeEndorseInfo findLastLifeEndorseInfoByPolicyId(String policyId) throws DAOException;

	/** used in Endorsement Service */
	public LifeEndorseInfo findBySourcePolicyReferenceNo(String policyId) throws DAOException;

	/** used in Endorsement Service */
	public LifeEndorseInfo findByEndorsePolicyReferenceNo(String policyId) throws DAOException;

	/** used in Endorsement Service */
	public LifeProposal insert(LifeProposal lifeProposal) throws DAOException;

	/** used in Endorsement Service */
	public void update(LifeProposal lifeProposal) throws DAOException;

	/** used in Endorsement Service */
	public void addAttachment(LifeProposal lifeProposal) throws DAOException;

	/** used in Endorsement Service */
	public void updateInsuredPersonApprovalInfo(List<ProposalInsuredPerson> proposalInsuredPersonList) throws DAOException;

	/** used in Endorsement Service */
	public void insertSurvey(LifeSurvey lifeSurvey) throws DAOException;

	/** used in Endorsement Service */
	public void updateInsuPersonMedicalStatus(List<ProposalInsuredPerson> proposalInsuredPersonList) throws DAOException;

	/** used in Endorsement Service */
	public void updateCompleteStatus(boolean status, String proposalId) throws DAOException;

}
