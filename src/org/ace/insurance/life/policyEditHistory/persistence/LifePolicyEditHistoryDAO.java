package org.ace.insurance.life.policyEditHistory.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.PolicyHistoryEntryType;
import org.ace.insurance.common.PolicyUtils;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policyEditHistory.LifePolicyEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonBeneficiariesEditHistory;
import org.ace.insurance.life.policyEditHistory.PolicyInsuredPersonEditHistory;
import org.ace.insurance.life.policyEditHistory.persistence.interfaces.ILifePolicyEditHistoryDAO;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicyEditHistoryDAO")
public class LifePolicyEditHistoryDAO extends BasicDAO implements ILifePolicyEditHistoryDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicyEditHistory lifePolicy) throws DAOException {
		try {
			em.persist(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicyEditHistory", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicyEditHistory lifePolicy) throws DAOException {
		try {
			em.merge(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicyEditHistory", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePolicyEditHistory lifePolicy) throws DAOException {
		try {
			lifePolicy = em.merge(lifePolicy);
			em.remove(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicyEditHistory", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicyEditHistory findById(String id) throws DAOException {
		LifePolicyEditHistory result = null;
		try {
			result = em.find(LifePolicyEditHistory.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyEditHistory", pe);
		}
		return result;
	}

	@Override
	public List<LifePolicyEditHistory> findByProposalId(String proposalId) throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findByProposalId");
			q.setParameter("lifeProposalId", proposalId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyEditHistory by ProposalID : " + proposalId, pe);
		}
		return result;
	}

	@Override
	public List<LifePolicyEditHistory> findByReceiptNo(String receiptNo) throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findByReceiptNo");
			q.setParameter("receiptNo", receiptNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyEditHistory by ReceiptNo : " + receiptNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findAll() throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyEditHistory", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findAllActiveLifePolicy() throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findAllActiveLifePolicy");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ActiveLifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findByDate(Date startDate, Date endDate) throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findByDate");
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyEditHistory by Date: ", pe);
		}
		return result;
	}

	@Override
	public void increasePrintCount(String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.increasePrintCount");
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to increase print count : ", pe);
		}
	}

	@Override
	public void updateCommenmanceDate(Date date, String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.updateCommenmanceDate");
			q.setParameter("commenceDate", date);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update commenmance date : ", pe);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findByEnquiryCriteria(EnquiryCriteria criteria) throws DAOException {
		List<LifePolicyEditHistory> result = new ArrayList<LifePolicyEditHistory>();
		try {
			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT DISTINCT l FROM LifePolicyEditHistory l");
			if ((criteria.getProduct() != null) ) {
				queryString.append(" INNER JOIN l.policyInsuredPersonList pi ");
			}
			queryString.append(" WHERE l.policyNo IS NOT NULL");

			if (criteria.getAgent() != null) {
				queryString.append(" AND l.agent.id = :agentId");
			}
			if (criteria.getStartDate() != null) {
				queryString.append(" AND l.commenmanceDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				queryString.append(" AND l.commenmanceDate <= :endDate");
			}
			if (criteria.getCustomer() != null) {
				queryString.append(" AND l.customer.id = :customerId");
			}
			if (criteria.getOrganization() != null) {
				queryString.append(" AND l.organization.id = :organizationId");
			}
			if (criteria.getBranch() != null) {
				queryString.append(" AND l.branch.id = :branchId");
			}
			if (criteria.getProduct() != null) {
				queryString.append(" AND pi.product.id = :productId");
			}
			if (!criteria.getPolicyNo().isEmpty()) {
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
			if (!criteria.getPolicyNo().isEmpty()) {
				query.setParameter("policyNo", criteria.getPolicyNo());
			}
			result = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicyEditHistory by EnquiryCriteria : ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	/**
	 * @see org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO#findLifePoliciesRequiredForCoinsurance()
	 */
	//TODO FIXME THK WRONG QUERY
	public List<LifePolicyEditHistory> findLifePoliciesRequiredForCoinsurance() {
		List<LifePolicyEditHistory> ret = null;
		final String query = "SELECT l FROM MotorPolicy l WHERE l.commenmanceDate IS NOT NULL AND l.isCoinsuranceApplied = FALSE";
		try {
			Query q = em.createQuery(query);
			ret = q.getResultList();
			ret = PolicyUtils.filterPoliciesByInsuredAmount(ret);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Faield to retrieve life policies required for the Co-insurance", pe);
		}
		return ret;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyEditHistory> findByPolicyNo(String policyNo) throws DAOException {
		List<LifePolicyEditHistory> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicyEditHistory.findByPolicyNo");
			q.setParameter("policyNo", policyNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicyEditHistory by PolicyNo : " + policyNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonEditHistory findInsuredPersonByCodeNo(String codeNo, String policyId) throws DAOException {
		PolicyInsuredPersonEditHistory result = null;
		try {
			Query q = em.createQuery("SELECT i FROM PolicyInsuredPersonEditHistory i WHERE i.inPersonCodeNo = :codeNo AND i.lifePolicy.id= :policyId");
			q.setParameter("codeNo", codeNo);
			q.setParameter("policyId", policyId);
			result = (PolicyInsuredPersonEditHistory) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of find Insured Person By CodeNo : " + codeNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPersonBeneficiariesEditHistory findBeneficiaryByCodeNo(String codeNo, String insuPersonID) throws DAOException {
		PolicyInsuredPersonBeneficiariesEditHistory result = null;
		try {
			Query q = em.createQuery("SELECT b FROM PolicyInsuredPersonBeneficiariesEditHistory b WHERE b.beneficiaryNo = :codeNo AND b.policyInsuredPerson.id= :insuPersonID");
			q.setParameter("codeNo", codeNo);
			q.setParameter("insuPersonID", insuPersonID);
			result = (PolicyInsuredPersonBeneficiariesEditHistory) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of find Beneficiary By Code No : " + codeNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findMaximunEntryCount(String policyNo, PolicyHistoryEntryType entryType) throws DAOException {
		int result = 0;
		try {
			Query q = em.createQuery("SELECT MAX(m.entryCount) FROM LifePolicyEditHistory m  WHERE  m.entryType = :entryType AND m.policyNo = :policyNo");
			q.setParameter("entryType", entryType);
			q.setParameter("policyNo", policyNo);
			result = (Integer) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return 0;
		} catch (NullPointerException e) {
			return 0;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Maximun EntryCount by policyNo : " + policyNo, pe);
		}

		return result;
	}

}
