package org.ace.insurance.medical.claim.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.claim.DeathClaim;
import org.ace.insurance.medical.claim.HospitalizedClaim;
import org.ace.insurance.medical.claim.MC001;
import org.ace.insurance.medical.claim.MedicalClaim;
import org.ace.insurance.medical.claim.MedicalClaimProposal;
import org.ace.insurance.medical.claim.MedicalClaimRole;
import org.ace.insurance.medical.claim.MedicationClaim;
import org.ace.insurance.medical.claim.OperationClaim;
import org.ace.insurance.medical.claim.persistence.interfaces.IMedicalClaimProposalDAO;
import org.ace.insurance.report.claim.MedicalClaimMonthlyReport;
import org.ace.insurance.report.common.MonthlyReportCriteria;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.manage.enquires.ClaimEnquiryCriteria;
import org.ace.insurance.web.manage.life.claim.request.DeathClaimDTO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalClaimProposalDAO")
public class MedicalClaimProposalDAO extends BasicDAO implements IMedicalClaimProposalDAO {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalClaimProposal insert(MedicalClaimProposal medicalClaimProposal) throws DAOException {
		try {
			em.persist(medicalClaimProposal);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to insert medicalClaim", pe);
		}
		return medicalClaimProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalClaimProposal update(MedicalClaimProposal medicalClaimProposal) throws DAOException {
		try {
			em.merge(medicalClaimProposal);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("failed to update medicalClaim", pe);
		}
		return medicalClaimProposal;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MedicalClaimProposal medicalClaim) throws DAOException {
		try {
			medicalClaim = em.merge(medicalClaim);
			em.remove(medicalClaim);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update medicalClaim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalClaimProposal findById(String id) throws DAOException {
		MedicalClaimProposal result = null;
		try {
			result = em.find(MedicalClaimProposal.class, id);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalClaimProposal> findAll() throws DAOException {
		List<MedicalClaimProposal> result = null;
		try {
			Query q = em.createNamedQuery("MedicalClaim.findAll");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of medicalClaim", pe);
		}
		return result;
	}

	public HospitalizedClaim findHospitalizedClaimById(String id) throws DAOException {
		HospitalizedClaim result = null;
		try {
			Query q = em.createNamedQuery("HospitalizedClaim.findById");
			q.setParameter("id", id);
			result = (HospitalizedClaim) q.getSingleResult();

		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return result;
	}

	public DeathClaim findDeathClaimById(String id) throws DAOException {
		DeathClaim result = null;
		try {
			Query q = em.createNamedQuery("DeathClaim.findById");
			q.setParameter("id", id);
			result = (DeathClaim) q.getSingleResult();

		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return result;
	}

	public OperationClaim findOperationClaimById(String id) throws DAOException {
		OperationClaim result = null;
		try {
			Query q = em.createNamedQuery("OperationClaim.findById");
			q.setParameter("id", id);
			result = (OperationClaim) q.getSingleResult();

		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return result;
	}

	public MedicationClaim findMedicationClaimById(String id) throws DAOException {
		MedicationClaim result = null;
		try {
			Query q = em.createNamedQuery("MedicationClaim.findById");
			q.setParameter("id", id);
			result = (MedicationClaim) q.getSingleResult();

		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MC001> findByEnquiryCriteria(ClaimEnquiryCriteria criteria) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, MC001> resultMap = new HashMap<String, MC001>();
		MedicalClaimProposal claimProposal = new MedicalClaimProposal();
		// MZP
		// MedicalClaimProposalDTO claimProposalDTO = new
		// MedicalClaimProposalDTO();
		String policyNo;
		String saleMan;
		String agent;
		String branch;
		String unit = "";
		double totalClaimAmount = 0.0;
		Date submittedDate;
		// String chellenNo = "";
		String id;
		String claimRequestNo = null;
		String receivedNo = "";
		try {
			logger.debug("findByEnquiryCriteria() method has been started.");
			StringBuffer queryString = new StringBuffer();

			queryString.append("SELECT l , p.receiptNo" + " FROM  MedicalClaimProposal l  Left Join Payment p on l.id = p.referenceNo  where  l.policyNo is not null ");
			if (criteria.getAgent() != null) {
				queryString.append(" AND l.agent.id = :agentId");
			}
			if (criteria.getStartDate() != null) {
				queryString.append(" AND l.submittedDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				queryString.append(" AND l.submittedDate <= :endDate");
			}
			if (criteria.getBranch() != null) {
				queryString.append(" AND l.branch.id = :branchId");
			}
			if (criteria.getClaimRequestNo() != null && !criteria.getClaimRequestNo().isEmpty()) {
				queryString.append(" AND l.claimRequestId = :claimRequestId");
			}
			if (criteria.getPolicyNumber() != null) {
				queryString.append(" AND l.policyNo = :policyNo");
			}
			/* Executed query */
			Query query = em.createQuery(queryString.toString());
			if (criteria.getAgent() != null) {
				query.setParameter("agentId", criteria.getAgent().getId());
			}
			if (criteria.getStartDate() != null) {
				criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
				query.setParameter("startDate", criteria.getStartDate());
			}
			if (criteria.getEndDate() != null) {
				criteria.setEndDate(Utils.resetEndDate(criteria.getEndDate()));
				query.setParameter("endDate", criteria.getEndDate());
			}
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getClaimRequestNo() != null && !criteria.getClaimRequestNo().isEmpty()) {
				query.setParameter("claimRequestId", criteria.getClaimRequestNo());
			}
			if (criteria.getPolicyNumber() != null) {
				query.setParameter("policyNo", criteria.getPolicyNumber().getPolicyNo());
			}
			objectList = query.getResultList();
			for (Object[] b : objectList) {
				claimProposal = (MedicalClaimProposal) b[0];
				// chellenNo = (String) b[1];
				receivedNo = (String) b[1];
				// MZP
				// if (claimProposal.getId() != null) {
				// claimProposalDTO =
				// MedicalClaimProposalFactory.createMedicalClaimProposalDTO(claimProposal);
				//
				// for (MedicalClaim mc : claimProposal.getMedicalClaimList()) {
				//
				// if
				// (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM)
				// && mc.isApproved()) {
				// claimProposalDTO.setHospitalizedClaimDTO(HospitalizedClaimFactory.createHospitalizedClaimDTO(findHospitalizedClaimById(mc.getId())));
				// totalClaimAmount +=
				// claimProposalDTO.getHospitalizedClaimDTO().getHospitalizedAmount();
				// }
				// if
				// (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM)
				// && mc.isApproved()) {
				// claimProposalDTO.setOperationClaimDTO(OperationClaimFactory.createOperationClaimDTO(findOperationClaimById(mc.getId())));
				// totalClaimAmount +=
				// claimProposalDTO.getOperationClaimDTO().getOperationFee();
				// }
				// if
				// (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM)
				// && mc.isApproved()) {
				// claimProposalDTO.setMedicationClaimDTO(MedicationClaimFactory.createMedicationClaimDTO(findMedicationClaimById(mc.getId())));
				// totalClaimAmount +=
				// claimProposalDTO.getMedicationClaimDTO().getMedicationFee();
				// }
				// if (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM) &&
				// mc.isApproved()) {
				// claimProposalDTO.setDeathClaimDTO(DeathClaimFactory.createDeathClaimDTO(findDeathClaimById(mc.getId())));
				// totalClaimAmount +=
				// claimProposalDTO.getDeathClaimDTO().getDeathClaimAmount();
				// }
				// }
				//
				// }
				if (claimProposal.getAgent() == null) {
					agent = "";
				} else {
					Agent a = (Agent) claimProposal.getAgent();
					agent = a.getFullName();
				}
				if (claimProposal.getBranch() == null) {
					branch = "";
				} else {
					Branch br = (Branch) claimProposal.getBranch();
					branch = br.getName();
				}
				submittedDate = (Date) claimProposal.getSubmittedDate();
				id = (String) claimProposal.getId();
				policyNo = (String) claimProposal.getClaimRequestId();

				resultMap.put(id, new MC001(id, policyNo, agent, branch, unit, totalClaimAmount, submittedDate, claimRequestNo, receivedNo));
			}
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalClaimProposal by EnquiryCriteria : ", pe);
		}
		return new ArrayList<MC001>(resultMap.values());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MC001> findByPolicyId(String id) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, MC001> resultMap = new HashMap<String, MC001>();
		MedicalClaimProposal claimProposal = new MedicalClaimProposal();
		// MZP
		// MedicalClaimProposalDTO claimProposalDTO = new
		// MedicalClaimProposalDTO();
		// HospitalizedClaimDTO hospitalizedClaimDTO = new
		// HospitalizedClaimDTO();
		DeathClaimDTO deathClaimDTO = new DeathClaimDTO();
		String claimProposalId;
		String policyNo;
		String saleMan;
		String agent;
		String branch;
		String unit = "";
		double totalClaimAmount = 0.0;
		Date submittedDate;
		String claimRequestNo = null;
		String receivedNo = "";
		try {
			logger.debug("findByEnquiryCriteria() method has been started.");
			StringBuffer queryString = new StringBuffer();

			queryString.append("SELECT l , p.chalanNo , p.receiptNo  FROM  MedicalClaimProposal l Left Join Payment p on l.id = p.referenceNo "
					+ " where l.medicalPolicy.policyNo is not null " + " AND l.medicalPolicy.id = :id");

			/* Executed query */
			Query query = em.createQuery(queryString.toString());
			query.setParameter("id", id);
			objectList = query.getResultList();
			for (Object[] b : objectList) {
				claimProposal = (MedicalClaimProposal) b[0];
				receivedNo = (String) b[1];
				if (claimProposal.getId() != null) {
					// MZP
					// claimProposalDTO =
					// MedicalClaimProposalFactory.createMedicalClaimProposalDTO(claimProposal);
					for (MedicalClaim mc : claimProposal.getMedicalClaimList()) {
						if (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM) && mc.isApproved()) {
							// MZP
							// hospitalizedClaimDTO =
							// HospitalizedClaimFactory.createHospitalizedClaimDTO(findHospitalizedClaimById(mc.getId()));
							// totalClaimAmount +=
							// hospitalizedClaimDTO.getHospitalizedAmount();
						}
						if (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM) && mc.isApproved()) {
							// MZP
							// deathClaimDTO =
							// DeathClaimFactory.createDeathClaimDTO(findDeathClaimById(mc.getId()));
							// totalClaimAmount +=
							// deathClaimDTO.getDeathClaimAmount();
						}
					}

				}
				if (claimProposal.getAgent() == null) {
					agent = "";
				} else {
					Agent a = (Agent) claimProposal.getAgent();
					agent = a.getFullName();
				}
				if (claimProposal.getBranch() == null) {
					branch = "";
				} else {
					Branch br = (Branch) claimProposal.getBranch();
					branch = br.getName();
				}
				submittedDate = (Date) claimProposal.getSubmittedDate();
				policyNo = (String) claimProposal.getClaimRequestId();
				claimProposalId = (String) claimProposal.getId();
				resultMap.put(claimProposalId, new MC001(claimProposalId, policyNo, agent, branch, unit, totalClaimAmount, submittedDate, claimRequestNo, receivedNo));
			}
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return new ArrayList<MC001>(resultMap.values());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalClaimMonthlyReport> findMedicalClaimMonthlyReport(MonthlyReportCriteria criteria) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		List<MedicalClaimMonthlyReport> medicalClaimMonthlyList = new ArrayList<MedicalClaimMonthlyReport>();
		MedicalClaimMonthlyReport medicalClaimMonthlyReport = new MedicalClaimMonthlyReport();

		try {
			logger.debug("findByEnquiryCriteria() method has been started.");
			StringBuffer queryString = new StringBuffer();
			queryString.append(
					"SELECT mc.submittedDate ,mc.claimRequestId , CONCAT(mc.medicalPolicy.policyInsuredPerson.name.firstName , ' ' ,mc.medicalPolicy.policyInsuredPerson.name.firstName, ' ' ,mc.medicalPolicy.policyInsuredPerson.name.firstName), "
							+ " mc.medicalPolicy.policyNo , p.receiptNo , p.paymentDate , p.claimAmount FROM MedicalClaimProposal mc INNER JOIN   Payment p on p.referenceNo = mc.id  WHERE    "
							+ " p.receiptNo IS NOT NULL AND p.paymentDate IS NOT NULL AND p.paymentDate >= :startDate AND p.paymentDate <= :endDate");

			if (criteria.getBranch() != null) {
				queryString.append(" AND mc.medicalClaimProposal.branch.id = :branchId");
			}
			Query query = em.createQuery(queryString.toString());

			query.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth()));
			query.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth()));
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			objectList = query.getResultList();
			for (Object[] b : objectList) {
				Date submittedDate = (Date) b[0];
				String claimRequestNo = (String) b[1];
				String insuredPersonName = (String) b[2];
				String policyNo = (String) b[3];
				String receiptNo = (String) b[4];
				Date paymentDate = (Date) b[5];
				Double claimAmount = (Double) b[6];
				medicalClaimMonthlyReport = new MedicalClaimMonthlyReport(submittedDate, claimRequestNo, insuredPersonName, policyNo, receiptNo, paymentDate, claimAmount);
				medicalClaimMonthlyList.add(medicalClaimMonthlyReport);
			}
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find medicalClaim", pe);
		}
		return medicalClaimMonthlyList;
	}

}
