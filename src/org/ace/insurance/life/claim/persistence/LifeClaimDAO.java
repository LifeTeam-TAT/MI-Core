package org.ace.insurance.life.claim.persistence;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimAttachment;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.life.claim.LifeClaimInsuredPersonBeneficiary;
import org.ace.insurance.life.claim.LifeClaimSuccessor;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimBeneficiaryDAO;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimDAO;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimInsuredPersonDAO;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyInsuredPersonInfoDAO;
import org.ace.insurance.life.proposal.persistence.interfaces.ILifeProposalDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Repository("LifeClaimDAO")
public class LifeClaimDAO extends BasicDAO implements ILifeClaimDAO {

	@Resource(name = "LifePolicyDAO")
	private ILifePolicyDAO lifePolicyDAO;

	@Resource(name = "ClaimInsuredPersonDAO")
	private ILifeClaimInsuredPersonDAO claimInsuredPersonDAO;

	@Resource(name = "LifeClaimBeneficiaryDAO")
	private ILifeClaimBeneficiaryDAO lifeClaimBeneficiaryDAO;

	@Resource(name = "LifeProposalDAO")
	private ILifeProposalDAO lifeProposalDAO;

	@Resource(name = "PolicyInsuredPersonInfoDAO")
	private ILifePolicyInsuredPersonInfoDAO lifePolicyInsuredPersonDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifeClaim lifeClaim) throws DAOException {
		try {
			em.persist(lifeClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert Claim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifeClaim lifeClaim) throws DAOException {
		try {
			em.merge(lifeClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update Claim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifeClaim lifeClaim) throws DAOException {
		try {
			em.merge(lifeClaim);
			em.remove(lifeClaim);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Claim", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaim findById(String id) throws DAOException {
		LifeClaim result = null;
		try {
			result = em.find(LifeClaim.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Claim", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaim> findAll() throws DAOException {
		List<LifeClaim> result = null;
		try {
			Query q = em.createNamedQuery("Claim.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Claim", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addAttachment(LifeClaim lifeClaim) throws DAOException {
		try {
			for (LifeClaimAttachment ca : lifeClaim.getClaimAttachmentList()) {
				em.persist(ca);
				em.flush();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to add Attachment", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateServiceCharges(int serviceCharges, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("Claim.updateServiceCharges");
			q.setParameter("serviceCharges", serviceCharges);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update complete status", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaim findByClaimRequestId(String id) throws DAOException {
		LifeClaim result = null;
		try {
			Query q = em.createNamedQuery("Claim.findByClaimRequestId");
			q.setParameter("id", id);
			result = (LifeClaim) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Claim", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaimInsuredPerson findLifeClaimInsuredPersonByPolicyInsuredPersonId(String policyInsuredPersonId) throws DAOException {
		LifeClaimInsuredPerson result = null;
		try {
			Query q = em.createNamedQuery("ClaimInsuredPerson.findByPolicyInsuredPersonId");
			q.setParameter("id", policyInsuredPersonId);
			result = (LifeClaimInsuredPerson) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			result = null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimInsuredPerson", pe);
		}
		return result;
	}

	// 2013/07/09 TDS Add for retrieving DisabilityClaim by defined
	// ClaimRequestNo(Used at Claim) Start
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeDisabilityClaim findDisabilityByRequestId(String claimRequestId) throws DAOException {
		LifeDisabilityClaim result = null;
		try {
			Query q = em.createNamedQuery("DisabilityClaim.findByRequestId");
			q.setParameter("claimRequestId", claimRequestId);
			result = (LifeDisabilityClaim) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to search DisabilityClaim by claimRequestId : " + claimRequestId, pe);
		}
		return result;
	}

	// 2013/07/09 TDS Add for retrieving DisabilityClaim by defined
	// ClaimRequestNo(Used at Claim) End

	// @Transactional(propagation = Propagation.REQUIRED)
	// public List<LCL001> findByEnquiryCriteria(LCL001 criteria) throws
	// DAOException {
	// List<Object[]> objectList = new ArrayList<Object[]>();
	// Map<String, LCL001> resultMap = new HashMap<String, LCL001>();
	// String policyNo;
	// String claimRole;
	// String insuredPerson = null;
	// double sumInsured = 0;
	// Date submittedDate;
	// String id;
	// try {
	// /* create query */
	// StringBuffer queryString = new StringBuffer();
	// queryString.append("SELECT DISTINCT l.POLICYID, l.CLAIMROLE, " +
	// "l.CLAIMINSUREPERSONID, l.SUBMITTEDDATE, l.ID "
	// + "FROM LIFECLAIM_REQUEST l INNER JOIN LIFECLAIM_REQUEST_BENEFICIARY b "
	// + "ON l.ID = b.LIFECLAIMREQUESTID ");
	// if (criteria.getPolicyId() != null) {
	// queryString.append(" AND l.POLICYID = '" + criteria.getPolicyId() + "'");
	// }
	// if (criteria.getClaimDate() != null) {
	// queryString.append(" AND l.CLAIMDATE = '" + new
	// java.sql.Timestamp(criteria.getClaimDate().getTime()) + "'");
	// }
	// if (criteria.getSubmittedDate() != null) {
	// queryString.append(" AND l.SUBMITTEDDATE = '" + new
	// java.sql.Timestamp(criteria.getSubmittedDate().getTime()) + "'");
	// }
	// if (criteria.getClaimRole() != null) {
	// queryString.append(" AND l.CLAIMROLE = '" + criteria.getClaimRole() +
	// "'");
	// }
	// /* Executed query */
	// Query query = em.createNativeQuery(queryString.toString());
	//
	// objectList = query.getResultList();
	// for (Object[] b : objectList) {
	// if (b[0] == null) {
	// policyNo = "";
	// } else {
	// policyNo = lifePolicyDAO.findPolicyNoById((String) b[0]);
	// }
	// claimRole = (String) b[1];
	// if (b[2] == null) {
	//
	// } else {
	//
	// if (claimRole.equals("DISABILITY")) {
	// ProposalInsuredPerson proposalInsuredPerson =
	// lifeProposalDAO.findProposalInsuredPersonById((String) b[2]);
	// if (proposalInsuredPerson != null) {
	// insuredPerson = proposalInsuredPerson.getFullName();
	// sumInsured = proposalInsuredPerson.getProposedSumInsured();
	// }
	// } else if (claimRole.equals("DEATH")) {
	// PolicyInsuredPerson policyInsuredPerson =
	// lifePolicyInsuredPersonDAO.findById((String) b[2]);
	// if (policyInsuredPerson != null) {
	// insuredPerson = policyInsuredPerson.getFullName();
	// sumInsured = policyInsuredPerson.getSumInsured();
	// }
	// }
	// }
	// submittedDate = (Date) b[3];
	// id = (String) b[4];
	// resultMap.put(id, new LCL001(id, policyNo, claimRole, insuredPerson,
	// sumInsured, null, submittedDate));
	// }
	// em.flush();
	//
	// } catch (PersistenceException pe) {
	// throw translate("Failed to find LifeClaim by EnquiryCriteria : ", pe);
	// }
	// return new ArrayList<LCL001>(resultMap.values());
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public String findStatusById(String id) throws DAOException {
		String result = null;
		try {
			Query q = em.createNativeQuery("SELECT STATUS FROM LIFECLAIM_REQUEST WHERE ID = '" + id + "' ");
			result = (String) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeProposal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifeClaim findLifeClaimPortalById(String id) throws DAOException {
		LifeClaim lifeClaim = null;
		try {
			String queryStr = "SELECT DISTINCT l.POLICYID, l.CLAIMROLE, l.CLAIMINSUREPERSONID, " + "l.CLAIMDATE, l.SUBMITTEDDATE, b.BENEFICIARYROLE, b.RELATIONSHIPID "
					+ "FROM LIFECLAIM_REQUEST l INNER JOIN LIFECLAIM_REQUEST_BENEFICIARY b " + "on l.ID = b.LIFECLAIMREQUESTID WHERE l.ID = '" + id + "' ";
			Query q = em.createNativeQuery(queryStr);
			List<Object[]> results = q.getResultList();
			if (results.size() > 0) {

				lifeClaim = new LifeClaim();
				lifeClaim.setPortalId(id);

				String policyId = (String) results.get(0)[0];
				if (!StringUtils.isBlank(policyId)) {
					LifePolicy lifePolicy = lifePolicyDAO.findById(policyId);
					lifeClaim.setLifePolicy(lifePolicy);
				}

				lifeClaim.setClaimRole((String) results.get(0)[1]);

				String insuredPersonId = (String) results.get(0)[2];
				if (!StringUtils.isBlank(insuredPersonId)) {
					LifeClaimInsuredPerson claimInsuredPerson = claimInsuredPersonDAO.findById(insuredPersonId);
					lifeClaim.setClaimInsuredPerson(claimInsuredPerson);

					String beneficiaryRole = (String) results.get(0)[5];
					if (beneficiaryRole.equals("SUCCESSOR")) {

						// TODO get successor by relationship of beneficiary.
						LifeClaimSuccessor succesor = null;
						lifeClaim.setSuccessor(succesor);
					} else if (beneficiaryRole.equals("DEATH") || beneficiaryRole.equals("DISABILITY")) {

						String relationshipId = (String) results.get(0)[6];
						List<LifeClaimInsuredPersonBeneficiary> claimInsuredPersonBeneficiaryList = lifeClaimBeneficiaryDAO.findByInsuredRelationshipId(relationshipId);
						lifeClaim.setClaimInsuredPersonBeneficiaryList(claimInsuredPersonBeneficiaryList);
					}
				}

				// lifeClaim.setClaimDate((Date) results.get(0)[3]);
				lifeClaim.setSubmittedDate((Date) results.get(0)[4]);

			}

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaim", pe);
		}
		return lifeClaim;
	}
}
