package org.ace.insurance.life.claim.persistence.interfaces;

import java.util.List;

import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeClaimSurvey;
import org.ace.insurance.life.claim.LifeDisabilityPaymentCriteria;
import org.ace.insurance.report.agent.ClaimMedicalFeesCriteria;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
import org.ace.java.component.persistence.exception.DAOException;

public interface ILifeClaimProposalDAO {

	public void insert(LifeClaimProposal claimProposal) throws DAOException;

	public void update(LifeClaimProposal claimProposal) throws DAOException;

	public void delete(LifeClaimProposal claimProposal) throws DAOException;

	public LifeClaimProposal findById(String id) throws DAOException;

	public void insert(LifeClaimSurvey lifeClaimSurvey) throws DAOException;

	public List<DisabilityLifeClaim> findDisabilityLifeClaimByLifeClaimProposalNo(LifeDisabilityPaymentCriteria criteria) throws DAOException;

	public void update(DisabilityLifeClaim disabilityLifeClaim) throws DAOException;

	public double findTotalDisabilityClaimPercentageByClaimPersonId(String id, String policyNo);

	public void issueLifeClaimPolicy(LifeClaimProposal lifeClaimProposal);

	public List<LCL001> findLifeClaimProposalByCriteria(LCL001 criteria);

	public List<LifeClaimProposal> findByLifepolicyId(String lifePolicyId);

	public List<LifeClaimMonthlyReportDTO> findLifeClaimByCriteria(LCL001 criteria);

	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeSanction(ClaimMedicalFeesCriteria claimMedicalFeesCriteria);

	public LifeClaimProposal findByClaimNo(String claimNo) throws DAOException;

	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeInvoice(ClaimMedicalFeesCriteria claimMedicalFeesCriteria);
}
