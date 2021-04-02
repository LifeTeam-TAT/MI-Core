/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.medical.policy.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.medical.claim.MedicalPolicyCriteria;
import org.ace.insurance.medical.policy.MED002;
import org.ace.insurance.medical.policy.MPL001;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonGuardian;
import org.ace.insurance.medical.policy.persistence.interfaces.IMedicalPolicyDAO;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.medical.claim.MedicalPolicyInsuredPersonDTO;
import org.ace.insurance.web.manage.medical.claim.factory.MedicalPolicyInsuredPersonFactory;
import org.ace.insurance.web.manage.medical.proposal.PolicyGuardianDTO;
import org.ace.insurance.web.manage.medical.proposal.factory.GuardianFactory;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("MedicalPolicyDAO")
public class MedicalPolicyDAO extends BasicDAO implements IMedicalPolicyDAO {
	private Logger logger = LogManager.getLogger(this.getClass());

	// TODO FIXME PSH claim
	/*
	 * @Resource(name = "MedicalClaimProposalService") private
	 * IMedicalClaimProposalService medicalClaimProposalService;
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MedicalPolicy medicalPolicy) throws DAOException {
		try {
			em.persist(medicalPolicy);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(MedicalPolicy medicalPolicy) throws DAOException {
		try {
			medicalPolicy = em.merge(medicalPolicy);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(MedicalPolicy medicalPolicy) throws DAOException {
		try {
			medicalPolicy = em.merge(medicalPolicy);
			em.remove(medicalPolicy);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> findMedPolicyInsuredPersonById(String id) throws DAOException {
		List<String> result = new ArrayList<String>();
		try {
			Query query = em.createQuery("SELECT m.unit FROM MedicalPolicyInsuredPerson m WHERE m.id = :id AND m.endDate >=:newDate ");
			query.setParameter("id", id);
			query.setParameter("newDate", new Date());
			result = query.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalProposal", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalPolicy findById(String id) throws DAOException {
		MedicalPolicy result = null;
		try {
			result = em.find(MedicalPolicy.class, id);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalPolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPolicy> findAll() throws DAOException {
		List<MedicalPolicy> result = null;
		try {
			Query q = em.createNamedQuery("MedicalPolicy.findAll");
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalPolicy", pe);
		}
		return result;
	}
	// TODO FIXME PSH
	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * updateBeneficiaryClaimStatusByBeneficiaryNo(String beneficiaryNo,
	 * ClaimStatus status) throws DAOException { try { Query q =
	 * em.createNamedQuery(
	 * "MedicalPolicyInsuredPersonBeneficiaries.updateClaimStatus");
	 * q.setParameter("beneficiaryNo", beneficiaryNo);
	 * q.setParameter("claimStatus", status); //
	 * updateProcessLog(TableName.MEDICALPOLICYINSUREDPERSONBENEFICIARIES, //
	 * id); q.executeUpdate(); em.flush(); System.gc(); } catch
	 * (PersistenceException pe) { throw
	 * translate("Failed to update Claim Status By Id : ", pe); } }
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyInsuBeneByBeneficiaryNo(MedicalPolicyInsuredPersonBeneficiaries medicalPolicyInsuredPersonBeneficiaries) throws DAOException {
		try {
			em.merge(medicalPolicyInsuredPersonBeneficiaries);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalPolicy findByProposalId(String proposalId) throws DAOException {
		MedicalPolicy result = null;
		try {
			Query q = em.createNamedQuery("MedicalPolicy.findByProposalId");
			q.setParameter("medicalProposalId", proposalId);
			result = (MedicalPolicy) q.getSingleResult();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalPolicy by ProposalID : " + proposalId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalPolicy findMedicalPolicyByPolicyNo(String policyNo) throws DAOException {
		MedicalPolicy result = null;
		try {
			Query q = em.createNamedQuery("MedicalPolicy.findByPolicyNo");
			q.setParameter("policyNo", policyNo);
			result = (MedicalPolicy) q.getSingleResult();
			em.flush();
			System.gc();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find MedicalPolicy", pe);
		}
		return result;
	}

	/* used for Medical Policy Enquiry (medicalPolicyEnquiry.xhtml) */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MED002> findMedicalPolicyForPolicyEnquiry(EnquiryCriteria criteria) throws DAOException {
		List<MED002> result = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT new org.ace.insurance.medical.policy.MED002(m.id, m.policyNo,m.medicalProposal.proposalNo,m.saleChannelType, ");
			buffer.append(" CONCAT(TRIM(a.initialId),' ', TRIM(a.name.firstName), ' ', TRIM(a.name.middleName), ' ', TRIM(a.name.lastName)), ");
			buffer.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)), ");
			buffer.append(" o.name,b.name, SUM(cast(mp.unit as Integer)),");
			buffer.append(" SUM(mp.premium),");
			buffer.append(" m.activedPolicyStartDate,m.activedPolicyEndDate)");
			buffer.append(" FROM MedicalPolicy m JOIN m.policyInsuredPersonList mp");
			buffer.append(" LEFT OUTER JOIN mp.customer ip");
			buffer.append(" LEFT OUTER JOIN m.agent a");
			buffer.append(" LEFT OUTER JOIN m.customer c LEFT OUTER JOIN m.branch b LEFT OUTER JOIN m.organization o");
			buffer.append(" WHERE m.policyNo IS NOT NULL");
			if (criteria.getAgent() != null) {
				buffer.append(" AND m.agent.id = :agentId");
			}
			if (criteria.getStartDate() != null) {
				buffer.append(" AND m.activedPolicyStartDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				buffer.append(" AND m.activedPolicyStartDate <= :endDate");
			}
			if (criteria.getCustomer() != null) {
				buffer.append(" AND m.customer.id = :customerId");
			}
			if (criteria.getOrganization() != null) {
				buffer.append(" AND m.organization.id = :organizationId");
			}
			if (criteria.getBranch() != null) {
				buffer.append(" AND m.branch.id = :branchId");
			}
			if (criteria.getProduct() != null) {
				buffer.append(" AND mp.product.id = :productId");
			}
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				buffer.append(" AND m.policyNo like :policyNo");
			}
			if (criteria.getProposalNo() != null && !criteria.getProposalNo().isEmpty()) {
				buffer.append(" AND m.medicalProposal.proposalNo like :proposalNo");
			}
			if (criteria.getProposalType() != null) {
				buffer.append(" AND m.medicalProposal.proposalType = :proposalType");
			}
			if (criteria.getSaleChannelType() != null) {
				buffer.append(" AND m.saleChannelType = :saleChannelType");
			}
			if (criteria.getInsuredPersonName() != null) {
				buffer.append(" AND CONCAT(FUNCTION('REPLACE',ip.name.firstName,' ',''),FUNCTION('REPLACE',ip.name.middleName,' ','')");
				buffer.append(",FUNCTION('REPLACE',ip.name.lastName,' ','')) LIKE :insuredPersonName");
			}
			buffer.append(" GROUP BY m.id, m.policyNo,m.medicalProposal.proposalNo,m.saleChannelType,");
			buffer.append("  a.initialId, a.name,c.initialId, c.name,o.name,");
			buffer.append(" b.name,m.activedPolicyStartDate,m.activedPolicyEndDate");

			/* Executed query */
			Query query = em.createQuery(buffer.toString());
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
			if (criteria.getCustomer() != null) {
				query.setParameter("customerId", criteria.getCustomer().getId());
			}
			if (criteria.getOrganization() != null) {
				query.setParameter("organizationId", criteria.getOrganization().getId());
			}
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getProduct() != null) {
				query.setParameter("productId", criteria.getProduct().getId());
			}
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				query.setParameter("policyNo", "%" + criteria.getPolicyNo() + "%");
			}
			if (criteria.getProposalNo() != null && !criteria.getProposalNo().isEmpty()) {
				query.setParameter("proposalNo", "%" + criteria.getProposalNo() + "%");
			}
			if (criteria.getProposalType() != null) {
				query.setParameter("proposalType", criteria.getProposalType());
			}
			if (criteria.getSaleChannelType() != null) {
				query.setParameter("saleChannelType", criteria.getSaleChannelType());
			}
			if (criteria.getInsuredPersonName() != null) {
				query.setParameter("insuredPersonName", "%" + criteria.getInsuredPersonName().replace(" ", "") + "%");
			}
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeProposal by EnquiryCriteria : ", pe);
		}

		return result;
	}
	// TODO FIXME PSH

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * List<MedicalPolicy>
	 * findMedicalPolicyForClaimByCriteria(MedicalPolicyCriteria criteria)
	 * throws DAOException { List<MedicalPolicy> medicalPolicyList = null;
	 * 
	 * try { StringBuffer query = new StringBuffer();
	 * query.append("Select l From MedicalPolicy l where l.policyNo IS NOT NULL"
	 * ); if (criteria != null && criteria.getCriteriaValue() != null) { if
	 * (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.CUSTOMER_NAME) { query.
	 * append(" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) like :customerName OR "
	 * +
	 * "CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName,  l.customer.name.lastName) like :customerName"
	 * ); } if (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.POLICY_NO) {
	 * query.append(" AND l.policyNo like :policyNo"); } if
	 * (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.ORGANIZATION_NAME) {
	 * query.append(" AND l.organization.name like :organizationName"); } }
	 * 
	 * Query q = em.createQuery(query.toString()); if (criteria != null &&
	 * criteria.getCriteriaValue() != null) { if
	 * (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.CUSTOMER_NAME) {
	 * q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
	 * } if (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.POLICY_NO) { q.setParameter("policyNo", "%" +
	 * criteria.getCriteriaValue() + "%"); } if
	 * (criteria.getMedicalPolicyCriteriaItems() ==
	 * MedicalPolicyCriteriaItems.ORGANIZATION_NAME) {
	 * q.setParameter("organizationName", "%" + criteria.getCriteriaValue() +
	 * "%"); } }
	 * 
	 * q.setMaxResults(30); medicalPolicyList = q.getResultList(); em.flush();
	 * System.gc();
	 * 
	 * } catch (PersistenceException pe) { throw
	 * translate("Failed to retrieve MedicalPolicy for Claim By Criteria", pe);
	 * }
	 * 
	 * return medicalPolicyList; }
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyGuardian(PolicyGuardianDTO policyGuardianDTO) throws DAOException {
		try {
			MedicalPolicyInsuredPersonGuardian medicalPolicyInsuredPersonGuardian = GuardianFactory.getPolicyGuardian(policyGuardianDTO);
			em.merge(medicalPolicyInsuredPersonGuardian);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyInsuredPerson(MedicalPolicyInsuredPersonDTO medicalPolicyInsuredPersonDTO) throws DAOException {
		try {
			MedicalPolicyInsuredPerson medicalPolicyInsuredPerson = MedicalPolicyInsuredPersonFactory.createMedicalPolicyInsuredPerson(medicalPolicyInsuredPersonDTO);
			em.merge(medicalPolicyInsuredPerson);
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to update MedicalPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPolicy> findByCustomer(Customer customer) throws DAOException {
		List<MedicalPolicy> result = null;
		try {
			Query q = em.createNamedQuery("MedicalPolicy.findByCustomer");
			q.setParameter("customerId", customer.getId());
			result = q.getResultList();
			em.flush();
			System.gc();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of MedicalPolicy by ProposalID : ", pe);
		}
		return result;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBillCollection(MedicalPolicy medicalPolicy) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE MedicalPolicy p SET p.lastPaymentTerm = :lastPaymentTerm, p.coverageDate = :coverageDate WHERE p.id = :id ");
			q.setParameter("lastPaymentTerm", medicalPolicy.getLastPaymentTerm());
			q.setParameter("coverageDate", medicalPolicy.getCoverageDate());
			q.setParameter("id", medicalPolicy.getId());
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Bill Collection after medical policy", pe);
		}
	}

	/* used in billCollection(billCollection payment) */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria) throws DAOException {
		List<BillCollectionDTO> result = new ArrayList<BillCollectionDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT NEW " + BillCollectionDTO.class.getName() + "(l.id,");
			query.append(" l.policyNo,c.initialId, c.name,o.name, c.fullIdNo, l.coverageDate,l.activedPolicyEndDate, l.paymentType,");
			query.append(" l.lastPaymentTerm,SUM(COALESCE(p.sumInsured,0)), SUM(COALESCE(p.basicTermPremium,0)), SUM(COALESCE(p.addonTermPremium,0)),");
			query.append(" 0.0, l.specialDiscount)");
			query.append(" FROM MedicalPolicy l LEFT JOIN l.customer c LEFT JOIN l.organization o");
			query.append(" LEFT JOIN l.policyInsuredPersonList p, Payment pay");
			query.append(" WHERE pay.referenceNo = l.id AND l.coverageDate < cast(l.activedPolicyEndDate as date)");
			query.append(" AND pay.referenceType IN :referenceTypeList AND pay.complete = 1");

			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO:
						query.append(" AND l.policyNo LIKE :value ");
						break;
					case CUSTOMERNAME:
						query.append(" AND CONCAT(TRIM(c.initialId),' ',TRIM(c.name.firstName),' ',TRIM(c.name.middleName),' ',TRIM(c.name.lastName)) LIKE :value ");
						break;
					case ORGANIZATIONNAME:
						query.append(" AND o.name LIKE :value ");
						break;
					default:
						break;
				}
			}
			if (criteria.getFromDate() != null) {
				query.append(" AND l.coverageDate >= :fromDate ");
			}
			if (criteria.getToDate() != null) {
				query.append(" AND l.coverageDate <= :toDate ");
			}
			query.append(" AND l.id NOT IN ( SELECT p.referenceNo FROM Payment p WHERE p.complete = '0' )");
			query.append(" GROUP BY l.id,l.policyNo,c.initialId,c.name,o.name,c.fullIdNo,l.coverageDate,l.activedPolicyEndDate");
			query.append(",l.paymentType,l.lastPaymentTerm,l.specialDiscount,pay.id ORDER BY l.policyNo ASC ");
			Query q = em.createQuery(query.toString());

			if (criteria.getPolicyCriteria() != null) {
				q.setParameter("value", "%" + criteria.getCriteriaValue() + "%");
			}
			if (criteria.getFromDate() != null) {
				q.setParameter("fromDate", Utils.resetStartDate(criteria.getFromDate()));
			}
			if (criteria.getToDate() != null) {
				q.setParameter("toDate", Utils.resetEndDate(criteria.getToDate()));
			}
			if (ReferenceType.HEALTH.equals(criteria.getReferenceType())) {
				q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.HEALTH_POLICY, PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION));
			} else if (ReferenceType.CRITICAL_ILLNESS.equals(criteria.getReferenceType())) {
				q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.CRITICAL_ILLNESS_POLICY, PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION));
			}
			result.addAll(q.getResultList());
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MPL001> findPolicyByCriteria(MedicalPolicyCriteria criteria) throws DAOException {
		List<MPL001> policyList = new ArrayList<MPL001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT p.id, p.policyNo, SUM(person.unit),");
			buffer.append(" CONCAT(TRIM(COALESCE(a.initialId, '')),' ', TRIM(COALESCE(a.name.firstName, '')), ' ',");
			buffer.append(" TRIM(COALESCE(a.name.middleName, '')), ' ', TRIM(COALESCE(a.name.lastName, ''))), ");
			buffer.append(" CONCAT(TRIM(COALESCE(c.initialId, '')),' ', TRIM(COALESCE(c.name.firstName, '')), ' ',");
			buffer.append(" TRIM(COALESCE(c.name.middleName, '')), ' ', TRIM(COALESCE(c.name.lastName, ''))), ");
			buffer.append(" o.name, p.branch.name, p.paymentType.name, p.activedPolicyEndDate");
			buffer.append(" FROM MedicalPolicy p LEFT OUTER JOIN p.agent a LEFT OUTER JOIN p.customer c");
			buffer.append(" LEFT OUTER JOIN p.organization o ");
			buffer.append(" JOIN p.policyInsuredPersonList person");
			buffer.append(" WHERE p.policyNo IS NOT NULL");
			if (criteria.getCriteriaValue() != null) {
				switch (criteria.getMedicalPolicyCriteriaItems()) {
					case CUSTOMER_NAME:
						buffer.append(" AND CONCAT(TRIM(COALESCE(c.initialId, '')),' ', TRIM(COALESCE(c.name.firstName, '')), ' ',");
						buffer.append(" TRIM(COALESCE(c.name.middleName, '')), ' ', TRIM(COALESCE(c.name.lastName, ''))) LIKE :param");
						break;
					case POLICY_NO:
						buffer.append(" AND p.policyNo LIKE :param");
						break;
					case ORGANIZATION_NAME:
						buffer.append(" AND p.organization.name LIKE :param");
						break;
				}
			}
			buffer.append(" GROUP BY p.id, p.policyNo, a.initialId, a.name.firstName, a.name.middleName, a.name.lastName,");
			buffer.append(" c.initialId, c.name.firstName, c.name.middleName, c.name.lastName,");
			buffer.append(" o.name, p.branch.name, p.paymentType.name, p.activedPolicyEndDate");
			Query query = em.createQuery(buffer.toString());
			if (criteria.getCriteriaValue() != null) {
				query.setParameter("param", "%" + criteria.getCriteriaValue() + "%");
			}
			List<Object[]> objArr = query.getResultList();
			for (Object[] arr : objArr) {
				policyList.add(new MPL001((String) arr[0], (String) arr[1], (Long) arr[2], (String) arr[3], (String) arr[4], (String) arr[5], (String) arr[6], (String) arr[7],
						(Date) arr[8]));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve MedicalPolicy for Criteria", pe);
		}
		return policyList;
	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public
	 * List<PolicyNotificationDTO>
	 * findPolicyNotificationByCriteria(NotificationCriteria criteria) throws
	 * DAOException { List<PolicyNotificationDTO> result = new
	 * ArrayList<PolicyNotificationDTO>(); try { StringBuffer queryStr = new
	 * StringBuffer("SELECT p.policyNo"); queryStr.
	 * append(", CONCAT(TRIM(COALESCE(c.initialId, '')),' ', TRIM(COALESCE(c.name.firstName, '')), ' ',"
	 * ); queryStr.
	 * append(" TRIM(COALESCE(c.name.middleName, '')), ' ', TRIM(COALESCE(c.name.lastName, '')))"
	 * ); queryStr.
	 * append(", COALESCE(c.fullIdNo,''),pt.name,p.lastPaymentTerm,COALESCE(i.premium,0)+COALESCE(i.basicPlusPremium,0)+COALESCE(SUM(a.premium),0)"
	 * ); queryStr.append(", p.activedPolicyStartDate,p.activedPolicyEndDate");
	 * queryStr.
	 * append(" FROM MedicalPolicy p INNER JOIN p.policyInsuredPersonList i LEFT JOIN i.customer c"
	 * ); queryStr.
	 * append(" LEFT JOIN i.policyInsuredPersonAddOnList a JOIN p.paymentType pt WHERE pt.id != :lumpsum AND i.product.id IN :productIdList"
	 * ); if (criteria.getReportType().equalsIgnoreCase("Monthly") ||
	 * criteria.getStartDate() != null) {
	 * queryStr.append(" AND p.activedPolicyEndDate >= :startDate"); } if
	 * (criteria.getReportType().equalsIgnoreCase("Monthly") ||
	 * criteria.getEndDate() != null) {
	 * queryStr.append(" AND p.activedPolicyEndDate <= :endDate"); }
	 * 
	 * queryStr.
	 * append(" GROUP BY p.policyNo,c.initialId,c.name.firstName,c.name.middleName,"
	 * ); queryStr.
	 * append(" c.name.lastName,c.fullIdNo,pt.name,p.lastPaymentTerm,i.premium,i.basicPlusPremium,p.activedPolicyStartDate,p.activedPolicyEndDate"
	 * );
	 * 
	 * Query q = em.createQuery(queryStr.toString()); q.setParameter("lumpsum",
	 * KeyFactorChecker.getLumpsumId());
	 * 
	 * if (ReferenceType.MEDICAL_POLICY.equals(criteria.getReferenceType())) {
	 * q.setParameter("productIdList",
	 * ProductIDConfig.getIdByReferenceType(criteria.getReferenceType())); } if
	 * (ReferenceType.CRITICAL_ILLNESS_POLICY.equals(criteria.getReferenceType()
	 * )) { q.setParameter("productIdList",
	 * ProductIDConfig.getIdByReferenceType(criteria.getReferenceType())); } if
	 * (ReferenceType.HEALTH_POLICY.equals(criteria.getReferenceType())) {
	 * q.setParameter("productIdList",
	 * ProductIDConfig.getIdByReferenceType(criteria.getReferenceType())); }
	 * 
	 * if (criteria.getReportType().equalsIgnoreCase("Monthly")) {
	 * q.setParameter("startDate", Utils.getStartDate(criteria.getYear(),
	 * criteria.getMonth())); q.setParameter("endDate",
	 * Utils.getEndDate(criteria.getYear(), criteria.getMonth())); } else { if
	 * (criteria.getStartDate() != null) {
	 * criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
	 * q.setParameter("startDate", criteria.getStartDate()); } if
	 * (criteria.getEndDate() != null) {
	 * criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
	 * q.setParameter("endDate", criteria.getEndDate()); } } List<Object[]>
	 * objArr = q.getResultList(); for (Object[] arr : objArr) { int
	 * lastPaymentTerm = (int) arr[4]; result.add(new
	 * PolicyNotificationDTO((String) arr[0], (String) arr[1], (String) arr[2],
	 * (String) arr[3], lastPaymentTerm != 0 ? lastPaymentTerm + 1 : 2,
	 * ((BigDecimal) arr[5]).doubleValue(), (Date) arr[6], (Date) arr[7])); }
	 * em.flush(); } catch (PersistenceException pe) { throw
	 * translate("Failed to find PolicyNotificationDTO by criteria", pe); }
	 * return result; }
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalPolicy> findPaymentOrderByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType) throws DAOException {
		List<MedicalPolicy> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT m FROM MedicalPolicy m, Payment p, TLF t WHERE m.id = p.referenceNo ");
			query.append("AND m.id = t.referenceNo AND p.isPO = TRUE AND t.clearing = TRUE AND t.paid = FALSE ");
			if (!StringUtils.isBlank(receiptNo)) {
				query.append("AND p.receiptNo = :receiptNo ");
			}
			if (policyReferenceType != null) {
				query.append("AND p.referenceType = :policyReferenceType ");
			}
			Query q = em.createQuery(query.toString());
			if (!StringUtils.isBlank(receiptNo)) {
				q.setParameter("receiptNo", receiptNo);
			}
			if (policyReferenceType != null) {
				q.setParameter("policyReferenceType", policyReferenceType);
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy PaymentOrder", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyAttachment(MedicalPolicy medicalPolicy) throws DAOException {
		try {
			em.merge(medicalPolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update MedicalPolicy Attachment", pe);
		}
	}

}
