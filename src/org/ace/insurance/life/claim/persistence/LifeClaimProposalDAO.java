package org.ace.insurance.life.claim.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.claim.DisabilityLifeClaim;
import org.ace.insurance.life.claim.LCL001;
import org.ace.insurance.life.claim.LifeClaimProposal;
import org.ace.insurance.life.claim.LifeClaimSurvey;
import org.ace.insurance.life.claim.LifeDisabilityPaymentCriteria;
import org.ace.insurance.life.claim.LifePolicyClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimProposalDAO;
import org.ace.insurance.report.agent.ClaimMedicalFeesCriteria;
import org.ace.insurance.report.claim.LifeClaimMedicalFeeDTO;
import org.ace.insurance.report.claim.LifeClaimMonthlyReportDTO;
import org.ace.insurance.system.common.paymenttype.persistence.interfaces.IPaymentTypeDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifeClaimProposalDAO")
public class LifeClaimProposalDAO extends BasicDAO implements ILifeClaimProposalDAO {

	@Resource(name = "PaymentTypeDAO")
	private IPaymentTypeDAO paymentTypeDAO;

	@Resource(name = "WorkFlowService")
	private IWorkFlowService workFlowDTOService;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimProposal claimProposal) throws DAOException {
		try {
			em.persist(claimProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeClaimProposal", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaimProposal claimProposal) throws DAOException {
		try {
			em.merge(claimProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeClaimProposal", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaimProposal claimProposal) throws DAOException {
		try {
			claimProposal = em.merge(claimProposal);
			em.remove(claimProposal);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeClaimProposal", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimProposal findById(String id) throws DAOException {
		LifeClaimProposal result = null;
		try {
			result = em.find(LifeClaimProposal.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimProposal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaimSurvey lifeClaimSurvey) throws DAOException {
		try {
			em.persist(lifeClaimSurvey);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert LifeClaimSurvey", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<DisabilityLifeClaim> findDisabilityLifeClaimByLifeClaimProposalNo(LifeDisabilityPaymentCriteria criteria) throws DAOException {
		List<DisabilityLifeClaim> result = new ArrayList<DisabilityLifeClaim>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT c FROM LifeClaimProposal p JOIN p.claimList c WHERE p.claimProposalNo = :claimProposalNo");
			Query q = em.createQuery(query.toString());
			q.setParameter("claimProposalNo", criteria.getClaimProposalNo());
			List<LifePolicyClaim> results = q.getResultList();
			em.flush();

			for (LifePolicyClaim claim : results) {
				if (claim instanceof DisabilityLifeClaim) {
					DisabilityLifeClaim disabilityClaim = (DisabilityLifeClaim) claim;
					result.add(disabilityClaim);
				}
			}

		} catch (PersistenceException pe) {
			throw translate("Failed to find DisabilityLifeClaim", pe);
		}
		return result;
	}

	public void update(DisabilityLifeClaim disabilityLifeClaim) throws DAOException {
		try {
			em.merge(disabilityLifeClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update DisabilityLifeClaim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findTotalDisabilityClaimPercentageByClaimPersonId(String id, String policyID) {
		double result = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Select Case When SUM(dl.percentage) is null then 0 else SUM(dl.percentage) end from LifeClaimProposal l,LifePolicyClaim p,DisabilityLifeClaim d");
			buffer.append(
					" join d.disabilityLifeClaimList dl where p.id=l.lifePolicyClaim.id and p.id=d.id and l.lifePolicy.id=:id and l.claimPerson.id=:personId and p.claimType='DISIBILITY_CLAIM'");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("personId", id);
			q.setParameter("id", policyID);
			result = (double) q.getSingleResult();
		} catch (PersistenceException pe) {
			throw translate("Failed to Find Total Disability Claim Percentage By ClaimPerson Id", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void issueLifeClaimPolicy(LifeClaimProposal lifeClaimProposal) {
		try {
			Query q = em.createNamedQuery("LifeClaimProposal.updateCompleteStatus");
			q.setParameter("complete", true);
			q.setParameter("id", lifeClaimProposal.getId());
			q.executeUpdate();
			em.flush();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to issue a LifeProposal.", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LCL001> findLifeClaimProposalByCriteria(LCL001 criteria) {
		List<LCL001> result = new ArrayList<LCL001>();
		String select = "";
		String from = "";
		StringBuffer query2 = new StringBuffer();
		StringBuffer where = new StringBuffer();
		StringBuffer where2 = new StringBuffer();
		where.append(" where p.id=d.id and l.claimProposalNo is not null");
		where2.append(" where p.id=ld.id and l.claimProposalNo is not null");
		if (criteria.getClaimProposalNo() != null && !criteria.getClaimProposalNo().isEmpty()) {
			where.append(" And l.claimProposalNo=:claimProposalNo");
			where2.append(" And l.claimProposalNo=:claimProposalNo");
		}
		if (criteria.getStartDate() != null) {
			where.append(" And cast(l.occuranceDate as date)>=:startDate");
			where2.append(" And cast(l.occuranceDate as date)>=:startDate");
		}
		if (criteria.getEndDate() != null) {
			where.append(" And l.occuranceDate<=:endDate");
			where2.append(" And l.occuranceDate<=:endDate");
		}
		if (criteria.getInsuredPersonId() != null) {
			where.append(" And pi.id=:insuredPersonId");
			where2.append(" And pi.id=:insuredPersonId");
		}
		if (criteria.getPolicyNo() != null) {
			where.append(" And lp.policyNo=:policyNo");
			where2.append(" And lp.policyNo=:policyNo");
		}
		try {
			if (null != criteria.getClaimRole() && criteria.getClaimRole().equals("DISABILITY")) {
				select = ",sum(dl.disabilityAmount),sum(dl.percentage)";
				from = ",DisabilityLifeClaim d join d.disabilityLifeClaimList dl";
			} else if (null != criteria.getClaimRole() && criteria.getClaimRole().equals("DEATH")) {
				select = ",sum(d.deathClaimAmount),0.0";
				from = ",LifeDeathClaim d";
			} else {
				select = ",sum(dl.disabilityAmount),sum(dl.percentage)";
				from = ",DisabilityLifeClaim d join d.disabilityLifeClaimList dl";
				query2.append(" UNION ALL Select l.id,l.claimProposalNo,lp.policyNo,p.claimType,");
				query2.append(" CONCAT(TRIM(pi.initialId),' ', TRIM(pi.name.firstName), ' ', TRIM(pi.name.middleName), ' ', TRIM(pi.name.lastName)),");
				query2.append("pi.sumInsured,l.occuranceDate,sum(ld.deathClaimAmount),0.0");
				query2.append(" From LifeClaimProposal l left join l.lifePolicyClaim p left join l.lifePolicy lp left JOIN l.claimPerson pi,LifeDeathClaim ld");
				query2.append(where2.toString());
				query2.append(" group by l.id,l.claimProposalNo,lp.policyNo,p.claimType,pi.initialId,pi.name,pi.sumInsured,l.occuranceDate");
			}
			StringBuffer query = new StringBuffer();
			query.append("Select l.id,l.claimProposalNo,lp.policyNo,p.claimType,");
			query.append(" CONCAT(TRIM(pi.initialId),' ', TRIM(pi.name.firstName), ' ', TRIM(pi.name.middleName), ' ', TRIM(pi.name.lastName)),");
			query.append("pi.sumInsured,l.occuranceDate" + select);
			query.append(" From LifeClaimProposal l left join l.lifePolicyClaim p left join l.lifePolicy lp left JOIN l.claimPerson pi");
			query.append(from.toString());
			query.append(where.toString());
			query.append(" group by l.id,l.claimProposalNo,lp.policyNo,p.claimType,pi.initialId,pi.name,pi.sumInsured,l.occuranceDate");
			query.append(query2.toString());
			Query q = em.createQuery(query.toString());
			if (criteria.getClaimProposalNo() != null && !criteria.getClaimProposalNo().isEmpty()) {
				q.setParameter("claimProposalNo", criteria.getClaimProposalNo());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getInsuredPersonId() != null) {
				q.setParameter("insuredPersonId", criteria.getInsuredPersonId());
			}
			if (criteria.getPolicyNo() != null) {
				q.setParameter("policyNo", criteria.getPolicyNo());
			}
			List<Object[]> results = q.getResultList();
			for (Object[] o : results) {
				String lifeProposalId = (String) o[0];
				String claimProposalNo = (String) o[1];
				String policyNo = (String) o[2];
				String claimRole = (String) o[3];
				String insuredPerson = (String) o[4];
				double sumInsured = (double) o[5];
				Date occuranceDate = (Date) o[6];
				double claimAmount = (double) o[7];
				double claimPercentage = (double) o[8];
				result.add(new LCL001(lifeProposalId, claimProposalNo, policyNo, claimRole, insuredPerson, sumInsured, occuranceDate, claimAmount, claimPercentage));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to Find LifeClaim Proposal By Criteria", pe);
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimProposal> findByLifepolicyId(String lifePolicyId) {
		List<LifeClaimProposal> result = null;
		try {
			Query q = em.createQuery("select p from LifeClaimProposal p where p.lifePolicy.id =:lifePolicyId");
			result = q.getResultList();
			q.setParameter("lifePolicyId", lifePolicyId);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimProposal", pe);
		}
		return result;
	}

	@Override
	public List<LifeClaimMonthlyReportDTO> findLifeClaimByCriteria(LCL001 criteria) {
		List<LifeClaimMonthlyReportDTO> result = new ArrayList<LifeClaimMonthlyReportDTO>();
		StringBuffer query = new StringBuffer();

		try {
			query.append("SELECT new " + LifeClaimMonthlyReportDTO.class.getName() + "(l)" + " FROM LifeClaimMonthlyReportView l WHERE l.policyNo IS NOT NULL ");
			query.append(" AND 1=1 ");
			if (criteria.getPolicyNo() != null) {
				query.append(" And l.policyNo=:policyNo");
			}

			if (null != criteria.getClaimRole()) {
				query.append(" AND l.claimType = :claimType");
			}

			if (criteria.getStartDate() != null) {
				query.append(" And l.claimDate >=:startDate");
			}
			if (criteria.getEndDate() != null) {
				query.append(" And l.claimDate <=:endDate");
			}

			if (criteria.getLifeProdutType() != null) {
				query.append(" AND l.productId = :productId");
			}

			Query q = em.createQuery(query.toString());

			if (criteria.getPolicyNo() != null) {
				q.setParameter("policyNo", criteria.getPolicyNo());
			}

			if (null != criteria.getClaimRole() && criteria.getClaimRole().equals("DEATH")) {
				q.setParameter("claimType", "DEATH");
			}
			if (null != criteria.getClaimRole() && criteria.getClaimRole().equals("DISABILITY")) {
				q.setParameter("claimType", "DISIBILITY_CLAIM");
			}
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				q.setParameter("startDate", criteria.getStartDate());
			}

			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				q.setParameter("endDate", criteria.getEndDate());
			}

			if (criteria.getLifeProdutType().equals(LifeProductType.SNAKE_BITE)) {
				q.setParameter("productId", KeyFactorIDConfig.getSnakeBikeId());
			} else if (criteria.getLifeProdutType().equals(LifeProductType.GROUP_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getGroupLifeId());
			} else if (criteria.getLifeProdutType().equals(LifeProductType.PUBLIC_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getPublicLifeId());
			} else if (criteria.getLifeProdutType().equals(LifeProductType.PA)) {
				q.setParameter("productId", KeyFactorIDConfig.getPersonalAccidentMMKId());
			} else if (criteria.getLifeProdutType().equals(LifeProductType.SHORTERM_ENDOWNMENT_LIFE)) {
				q.setParameter("productId", KeyFactorIDConfig.getShortEndowLifeId());
			} else {
				q.setParameter("productId", KeyFactorIDConfig.getSportManId());
			}

			result = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy by criteria.", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeSanction(ClaimMedicalFeesCriteria claimMedicalFeesCriteria) {
		List<LifeClaimMedicalFeeDTO> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT new " + LifeClaimMedicalFeeDTO.class.getName()
					+ "(s.id,s.policyNo,s.medicalFeesStartDate,s.claimPerson,s.claimNo,s.amount,s.hospital.name,s.branch.id,s.hospitalCase,s.informDate,s.informDate)");
			query.append(" FROM  ClaimMedicalFees s  WHERE s.status = 0 ");

			if (claimMedicalFeesCriteria.getHospitalId() != null && !claimMedicalFeesCriteria.getHospitalId().isEmpty()) {
				query.append(" AND s.hospital.id=:hospitalId");
			}
			if (claimMedicalFeesCriteria.getClaimNo() != null && !claimMedicalFeesCriteria.getClaimNo().isEmpty()) {
				query.append(" AND s.claimNo= :claimNo");
			}
			if (claimMedicalFeesCriteria.getPolicyNo() != null && !claimMedicalFeesCriteria.getPolicyNo().isEmpty()) {
				query.append(" AND s.policyNo= :policyNo");
			}
			Query q = em.createQuery(query.toString());
			if (claimMedicalFeesCriteria.getClaimNo() != null && !claimMedicalFeesCriteria.getClaimNo().isEmpty()) {
				q.setParameter("claimNo", claimMedicalFeesCriteria.getClaimNo());
			}
			if (claimMedicalFeesCriteria.getPolicyNo() != null && !claimMedicalFeesCriteria.getPolicyNo().isEmpty()) {
				q.setParameter("policyNo", claimMedicalFeesCriteria.getPolicyNo());
			}
			if (claimMedicalFeesCriteria.getHospitalId() != null && !claimMedicalFeesCriteria.getHospitalId().isEmpty()) {
				q.setParameter("hospitalId", claimMedicalFeesCriteria.getHospitalId());
			}
			result = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of meidcalfees by criteria.", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimMedicalFeeDTO> findLifeClaimMedicalFeeInvoice(ClaimMedicalFeesCriteria claimMedicalFeesCriteria) {
		List<LifeClaimMedicalFeeDTO> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append(" SELECT new " + LifeClaimMedicalFeeDTO.class.getName() + "(s.sanctionNo,SUM(s.amount),s.hospital.name,s.branch.id,s.sanctionDate)");
			query.append(" FROM  ClaimMedicalFees s WHERE s.status = 1 AND s.invoiceDate IS NULL AND s.invoiceNo IS NULL ");

			if (claimMedicalFeesCriteria.getHospitalId() != null && !claimMedicalFeesCriteria.getHospitalId().isEmpty()) {
				query.append(" AND s.hospital.id= :hospitalId");
			}
			if (claimMedicalFeesCriteria.getClaimNo() != null && !claimMedicalFeesCriteria.getClaimNo().isEmpty()) {
				query.append(" AND s.claimNo= :claimNo");
			}
			if (claimMedicalFeesCriteria.getPolicyNo() != null && !claimMedicalFeesCriteria.getPolicyNo().isEmpty()) {
				query.append(" AND s.policyNo= :policyNo");
			}
			if (claimMedicalFeesCriteria.getSanctionNo() != null && !claimMedicalFeesCriteria.getSanctionNo().isEmpty()) {
				query.append(" AND s.sanctionNo= :sanctionNo");
			}
			query.append(" GROUP BY s.sanctionNo,s.hospital.name,s.branch.id,s.sanctionDate");
			query.append(" ORDER BY s.sanctionNo");
			Query q = em.createQuery(query.toString());

			if (claimMedicalFeesCriteria.getHospitalId() != null && !claimMedicalFeesCriteria.getHospitalId().isEmpty()) {
				q.setParameter("hospitalId", claimMedicalFeesCriteria.getHospitalId());
			}
			if (claimMedicalFeesCriteria.getClaimNo() != null && !claimMedicalFeesCriteria.getClaimNo().isEmpty()) {
				q.setParameter("claimNo", claimMedicalFeesCriteria.getClaimNo());
			}
			if (claimMedicalFeesCriteria.getPolicyNo() != null && !claimMedicalFeesCriteria.getPolicyNo().isEmpty()) {
				q.setParameter("policyNo", claimMedicalFeesCriteria.getPolicyNo());
			}
			if (claimMedicalFeesCriteria.getSanctionNo() != null && !claimMedicalFeesCriteria.getSanctionNo().isEmpty()) {
				q.setParameter("sanctionNo", claimMedicalFeesCriteria.getSanctionNo());
			}

			result = q.getResultList();
		} catch (

		PersistenceException pe) {
			throw translate("Failed to find all of meidcalfees by criteria.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimProposal findByClaimNo(String claimNo) throws DAOException {
		LifeClaimProposal result = null;
		try {
			Query q = em.createQuery("select s from LifeClaimProposal s where s.claimProposalNo =:claimNo ");
			q.setParameter("claimNo", claimNo);
			result = (LifeClaimProposal) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimProposal", pe);
		}
		return result;
	}

}
