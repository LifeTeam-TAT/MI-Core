package org.ace.insurance.life.policy.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.LifePolicyCriteriaItems;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.NotificationCriteria;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.PolicyStatus;
import org.ace.insurance.common.ReceiptNoCriteria;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.claim.LifePolicyCriteria;
import org.ace.insurance.life.claim.LifePolicySearch;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LPC001;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.LifePolicyAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.SportManTravelAbroad;
import org.ace.insurance.life.policy.persistence.interfaces.ILifePolicyDAO;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.persistence.interfaces.ILifePolicyHistoryDAO;
import org.ace.insurance.life.proposal.LPL002;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.product.Product;
import org.ace.insurance.report.life.UPRReportCriteria;
import org.ace.insurance.report.life.view.UPRReportView;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.insurance.web.common.SaleChannelType;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
import org.ace.insurance.web.manage.life.billcollection.LifePolicyNotificationDTO;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("LifePolicyDAO")
public class LifePolicyDAO extends BasicDAO implements ILifePolicyDAO {

	@Resource(name = "LifePolicyHistoryDAO")
	private ILifePolicyHistoryDAO lifePolicyHistoryDAO;

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	@Resource(name = "IDConfigLoader")
	private IDConfigLoader idConfigLoader;

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(LifePolicy lifePolicy) throws DAOException {
		try {
			em.persist(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertSportManTravelAbroad(List<SportManTravelAbroad> sportManTravelList) throws DAOException {
		try {
			for (SportManTravelAbroad sportMan : sportManTravelList) {
				em.persist(sportMan);
				em.flush();
			}
		} catch (PersistenceException pe) {
			throw translate("failed to insert LifePolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(LifePolicy lifePolicy) throws DAOException {
		try {
			em.merge(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicy", pe);
		}

	}

	/*
	 * @Transactional(propagation = Propagation.REQUIRED) public void
	 * updateActivePolicyEndDate(Date activePolicyEndDate, String policyId)
	 * throws DAOException { try{ logger.debug(
	 * "update active policy end date method has been started."); String query =
	 * "Update LifePolicy Set activedPolicyEndDate = :endDate Where id = :policyId"
	 * ; Query q = em.createQuery(query); q.setParameter("endDate",
	 * activePolicyEndDate); q.setParameter("policyId", policyId);
	 * q.executeUpdate(); em.flush(); logger.debug(
	 * "update active policy end date method has been successfully completed.");
	 * }catch(PersistenceException pe){ logger.error(
	 * "update active policy end date method has been failed.", pe); throw
	 * translate("failed to update LifePolicy", pe); }
	 * 
	 * }
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(LifePolicy lifePolicy) throws DAOException {
		try {
			lifePolicy = em.merge(lifePolicy);
			em.remove(lifePolicy);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update LifePolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findById(String id) throws DAOException {
		LifePolicy result = null;
		try {
			result = em.find(LifePolicy.class, id);
			// if does not exist in LifePolicy, then find in History
			if (result == null) {
				LifePolicyHistory lifePolicyHistory = lifePolicyHistoryDAO.findByPolicyReferenceNo(id);
				if (lifePolicyHistory != null) {
					result = new LifePolicy(lifePolicyHistory);
				}
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findByProposalId(String proposalId) throws DAOException {
		LifePolicy result = null;
		LifePolicyHistory history = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByProposalId");
			q.setParameter("lifeProposalId", proposalId);
			result = (LifePolicy) q.getSingleResult();
			em.flush();

			/**
			 * If it's Policy have been finished Endorse and Renewal Case,
			 * Cann't find Policy Table. So find in History Table.
			 */
			if (result == null) {
				history = lifePolicyHistoryDAO.findByProposalId(proposalId);
				if (history != null) {
					result = new LifePolicy(history);
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy by ProposalID : " + proposalId, pe);
		}
		return result;
	}

	@Override
	public List<LifePolicy> findByPolicyId(String policyId) throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByPolicyId");
			q.setParameter("lifePolicyId", policyId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy by Policy ID : " + policyId, pe);
		}
		return result;
	}

	@Override
	public List<LifePolicy> findByReceiptNo(String receiptNo) throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByReceiptNo");
			q.setParameter("receiptNo", receiptNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy by ReceiptNo : " + receiptNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findAll() throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findAll");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of LifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findAllActiveLifePolicy() throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findAllActiveLifePolicy");
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of ActiveLifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findByDate(Date startDate, Date endDate) throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByDate");
			q.setParameter("startDate", startDate);
			q.setParameter("endDate", endDate);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy by Date: ", pe);
		}
		return result;
	}

	@Override
	public void increasePrintCount(String id) throws DAOException {
		try {
			Query q = em.createNamedQuery("LifePolicy.increasePrintCount");
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
			Query q = em.createNamedQuery("LifePolicy.updateCommenmanceDate");
			q.setParameter("commenceDate", date);
			q.setParameter("id", id);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update commenmance date : ", pe);
		}

	}

	/* used for Life Policy Enquire (lifePolicyEnquiry.xhtml) */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPL002> findByEnquiryCriteria(EnquiryCriteria criteria, List<Product> productList) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, LPL002> resultMap = new HashMap<String, LPL002>();
		try {
			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append(" SELECT l.id,l.policyNo, pro.proposalNo, ");
			queryString.append(" CONCAT(TRIM(a.initialId),' ', TRIM(a.name.firstName), ' ', TRIM(a.name.middleName), ' ', TRIM(a.name.lastName)),");
			queryString.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			queryString.append(" o.name, l.branch.name, COALESCE(SUM(pi.basicTermPremium + pi.addOnTermPremium),0.0) , COALESCE(SUM(pi.sumInsured),0.0),pi.unit, ");
			queryString.append(" l.paymentType.name, l.commenmanceDate, ");
			queryString.append(" l.saleChannelType,b.name");
			queryString.append(" FROM LifePolicy l INNER JOIN l.policyInsuredPersonList pi ");
			queryString.append(" JOIN l.lifeProposal pro");
			queryString.append(" LEFT OUTER JOIN l.agent a ");
			queryString.append(" LEFT OUTER JOIN l.customer c ");
			queryString.append(" LEFT OUTER JOIN l.organization o ");
			queryString.append(" LEFT OUTER JOIN l.saleBank b");
			queryString.append(" WHERE l.policyNo IS NOT NULL  ");
			if (criteria.getInsuranceType() != null) {
				queryString.append(" AND pi.product.insuranceType = :insuranceType");
			}
			if (criteria.getAgent() != null) {
				queryString.append(" AND l.agent.id = :agentId");
			}
			if (criteria.getStartDate() != null) {
				queryString.append(" AND l.activedPolicyStartDate >= :startDate");
			}
			if (criteria.getEndDate() != null) {
				queryString.append(" AND l.activedPolicyStartDate <= :endDate");
			}
			if (criteria.getCustomer() != null) {
				queryString.append(" AND l.customer.id = :customerId");
			}
			if (criteria.getOrganization() != null) {
				queryString.append(" AND l.organization.id = :organizationId");
			}
			if (criteria.getSaleBank() != null) {
				queryString.append(" AND l.saleBank.id = :saleBankId");
			}
			if (criteria.getBranch() != null) {
				queryString.append(" AND l.branch.id = :branchId");
			}
			if (criteria.getProduct() != null) {
				queryString.append(" AND pi.product.id = :productId");
			} else if (productList != null && !productList.isEmpty()) {
				queryString.append(" AND pi.product IN :productList");
			}
			if (!criteria.getPolicyNo().isEmpty()) {
				queryString.append(" AND l.policyNo LIKE :policyNo");
			}
			if (!criteria.getProposalNo().isEmpty()) {
				queryString.append(" AND pro.proposalNo LIKE :proposalNo");
			}
			if (criteria.getProposalType() != null) {
				queryString.append(" AND pro.proposalType = :proposalType");
			}
			if (criteria.getSaleChannelType() != null) {
				queryString.append(" AND l.saleChannelType =:saleChannelType");
			}
			if (criteria.getInsuredPersonName() != null) {
				queryString.append(" AND CONCAT(FUNCTION('REPLACE',pi.name.firstName,' ',''),FUNCTION('REPLACE',pi.name.middleName,' ','')");
				queryString.append(",FUNCTION('REPLACE',pi.name.lastName,' ','')) LIKE :insuredPersonName");
			}
			queryString.append(" GROUP BY l.id, l.policyNo, pro.proposalNo, a.initialId, a.name,c.initialId,c.name.firstName,c.name.middleName,");
			queryString.append("c.name.lastName, o.name, l.branch.name,b, l.branch.name, l.paymentType.name, l.commenmanceDate,l.saleChannelType,b.name,pi.unit");

			/* Executed query */
			Query query = em.createQuery(queryString.toString());
			if (criteria.getProposalType() != null) {
				query.setParameter("proposalType", criteria.getProposalType());
			}
			if (criteria.getInsuranceType() != null) {
				query.setParameter("insuranceType", criteria.getInsuranceType());
			}
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
			if (criteria.getSaleBank() != null) {
				query.setParameter("saleBankId", criteria.getSaleBank().getId());
			}
			if (criteria.getBranch() != null) {
				query.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getProduct() != null) {
				query.setParameter("productId", criteria.getProduct().getId());
			} else if (productList != null && !productList.isEmpty()) {
				query.setParameter("productList", productList);
			}
			if (!criteria.getPolicyNo().isEmpty()) {
				query.setParameter("policyNo", "%" + criteria.getPolicyNo() + "%");
			}
			if (!criteria.getProposalNo().isEmpty()) {
				query.setParameter("proposalNo", "%" + criteria.getProposalNo() + "%");
			}
			if (criteria.getSaleChannelType() != null) {
				query.setParameter("saleChannelType", criteria.getSaleChannelType());
			}
			if (criteria.getInsuredPersonName() != null) {
				query.setParameter("insuredPersonName", "%" + criteria.getInsuredPersonName().replace(" ", "") + "%");
			}
			objectList = query.getResultList();
			String id;
			String policyNo;
			String proposalNo;
			String agent = null;
			String customer = null;
			String organization;
			String branch;
			double premium;
			double sumInsured;
			int unit;
			String paymentType;
			String paymenttype;
			Date commenmanceDate;
			String saleBank;
			SaleChannelType saleChannelType;
			for (Object[] b : objectList) {
				id = (String) b[0];
				policyNo = (String) b[1];
				proposalNo = (String) b[2];
				agent = (String) b[3];
				customer = (String) b[4];
				organization = (String) b[5];
				branch = (String) b[6];
				premium = (double) b[7];
				sumInsured = (double) b[8];
				unit = (int) b[9];
				paymentType = (String) b[10];
				commenmanceDate = (Date) b[11];
				saleChannelType = (SaleChannelType) b[12];
				if (resultMap.containsKey(policyNo)) {
					premium += resultMap.get(policyNo).getPremium();
					sumInsured += resultMap.get(policyNo).getSumInsured();
					resultMap.put(policyNo,
							new LPL002(id, policyNo, proposalNo, agent, customer, organization, branch, premium, sumInsured, unit, paymentType, commenmanceDate, saleChannelType));
				} else {
					resultMap.put(policyNo,
							new LPL002(id, policyNo, proposalNo, agent, customer, organization, branch, premium, sumInsured, unit, paymentType, commenmanceDate, saleChannelType));
				}
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy by EnquiryCriteria : ", pe);
		}
		return new ArrayList<LPL002>(resultMap.values());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePaymentDate(String lifePolicyId, Date paymentDate, Date paymentValidDate) throws DAOException {
		try {
			Query q = em.createNamedQuery("LifePolicy.updatePaymentDate");
			q.setParameter("id", lifePolicyId);
			q.setParameter("paymentDate", paymentDate);
			q.setParameter("paymentValidDate", paymentValidDate);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update payment date : ", pe);
		}
	}

	// public List<LifePolicy> findLifePoliciesRequiredForCoinsurance() {
	// List<LifePolicy> ret = null;
	// final String query =
	// "SELECT l FROM LifePolicy l WHERE l.commenmanceDate IS NOT NULL AND
	// l.isCoinsuranceApplied = FALSE";
	// try {
	// logger.debug("Retrieving all life policies required for the
	// Co-insurance...");
	// Query q = em.createQuery(query);
	// ret = q.getResultList();
	// ret = PolicyUtils.filterPoliciesByInsuredAmount(ret);
	// em.flush();
	// logger.debug("All life policies required for the Co-insurance
	// retrieved.");
	// } catch (PersistenceException pe) {
	// throw
	// translate("Faield to retrieve life policies required for the
	// Co-insurance"
	// , pe);
	// }
	// return ret;
	// }

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findByCustomer(Customer customer) throws DAOException {
		List<LifePolicy> result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByCustomer");
			q.setParameter("customerId", customer.getId());
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy by Date: ", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndorsementStatus(boolean status, String policylId) throws DAOException {
		try {
			Query q = em.createNamedQuery("LifePolicy.updateEndorsementStatus");
			q.setParameter("isEndorsementApplied", status);
			q.setParameter("id", policylId);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update endorsement status", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEndorsementStatusAndIssueDate(LifePolicy lifePolicy) {
		try {
			Query q = em.createNamedQuery("LifePolicy.updateEndorsementStatusAndIssueDate");
			q.setParameter("isEndorsementApplied", lifePolicy.isEndorsementApplied());
			q.setParameter("issueDate", lifePolicy.getIssueDate());
			q.setParameter("id", lifePolicy.getId());
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update endorsement status", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public PolicyInsuredPerson findInsuredPersonByCodeNo(String codeNo) throws DAOException {
		PolicyInsuredPerson result = null;
		try {
			Query q = em.createQuery("SELECT i FROM PolicyInsuredPerson i WHERE i.insPersonCodeNo = :codeNo ORDER BY i.recorder.createdDate desc");
			q.setParameter("codeNo", codeNo);
			q.setMaxResults(1);
			result = (PolicyInsuredPerson) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of find Insured Person By CodeNo : " + codeNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsuredPersonStatusByCodeNo(String codeNo, EndorsementStatus status) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE PolicyInsuredPerson i SET i.endorsementStatus = :status WHERE i.insPersonCodeNo = :codeNo");
			q.setParameter("codeNo", codeNo);
			q.setParameter("status", status);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Insured Person Status By CodeNo : ", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyStatusById(String id, PolicyStatus status) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE LifePolicy p SET p.policyStatus = :status WHERE p.id = :id");
			q.setParameter("id", id);
			q.setParameter("status", status);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Policy Status By Id : ", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyAttachment(LifePolicy lifePolicy) throws DAOException {
		try {
			Query delQuery = em.createQuery("DELETE FROM LifePolicyAttachment a WHERE a.lifePolicy.id = :lifePolicyId ");
			delQuery.setParameter("lifePolicyId", lifePolicy.getId());
			delQuery.executeUpdate();
			for (LifePolicyAttachment att : lifePolicy.getAttachmentList()) {
				if (att.getId() == null) {
					em.persist(att);
				}
			}

			Query del = em.createQuery("DELETE FROM PolicyInsuredPersonAttachment a WHERE a.policyInsuredPerson.id = :insuredPersonId ");
			for (PolicyInsuredPerson pv : lifePolicy.getInsuredPersonInfo()) {
				del.setParameter("insuredPersonId", pv.getId());
			}
			del.executeUpdate();
			for (PolicyInsuredPerson pv : lifePolicy.getInsuredPersonInfo()) {
				for (PolicyInsuredPersonAttachment att : pv.getAttachmentList()) {
					if (att.getId() == null) {
						em.persist(att);
					}
				}
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update MotorPolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findLifePolicyByPolicyNo(String policyNo) throws DAOException {
		LifePolicy result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByPolicyNo");
			q.setParameter("policyNo", policyNo);
			result = (LifePolicy) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBeneficiaryClaimStatusById(String id, ClaimStatus status) throws DAOException {
		try {
			Query q = em.createNamedQuery("PolicyInsuredPersonBeneficiaries.updateClaimStatus");
			q.setParameter("id", id);
			q.setParameter("claimStatus", status);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Claim Status By Id : ", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInsuredPersonClaimStatusById(String id, ClaimStatus status) throws DAOException {
		try {
			Query q = em.createNamedQuery("PolicyInsuredPerson.updateClaimStatus");
			q.setParameter("id", id);
			q.setParameter("claimStatus", status);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Claim Status By Id : ", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findLifePolicyForClaimByCriteria(LifePolicyCriteria criteria) throws DAOException {
		List<LifePolicySearch> result = null;

		try {
			StringBuffer query = new StringBuffer();
			query.append("Select New " + LifePolicySearch.class.getName() + "(l.policyNo,");
			query.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			query.append("o.name, l.branch.name,lp.idNo,lp.fatherName) From LifePolicy l INNER JOIN l.policyInsuredPersonList lp");
			query.append(" LEFT OUTER JOIN l.customer c");
			query.append(" LEFT OUTER JOIN l.organization o");
			query.append("  WHERE l.policyNo IS NOT NULL");
			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					query.append(" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) like :customerName OR "
							+ "CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName,  l.customer.name.lastName) like :customerName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					query.append(" AND l.policyNo like :policyNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					query.append(" AND l.organization.name like :organizationName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					query.append(" AND lp.idNo like :nrcNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					query.append(" AND CONCAT(lp.name.firstName, ' ', lp.name.middleName, ' ', lp.name.lastName) like :insuredPersonName OR "
							+ "CONCAT(lp.name.firstName, ' ', lp.name.middleName,  lp.name.lastName) like :insuredPersonName");
				}
			}

			Query q = em.createQuery(query.toString());
			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					q.setParameter("nrcNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					q.setParameter("insuredPersonName", "%" + criteria.getCriteriaValue() + "%");
				}
			}
			q.setMaxResults(30);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve LifePolicy for Claim By Criteria", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findActiveLifePolicy(LifePolicyCriteria criteria, List<Product> productList) throws DAOException {
		List<LifePolicySearch> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("Select New " + LifePolicySearch.class.getName() + "(l.policyNo,");
			query.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			query.append("o.name, l.branch.name,lp.idNo,lp.fatherName) From LifePolicy l INNER JOIN l.policyInsuredPersonList lp");
			query.append(" LEFT OUTER JOIN l.customer c");
			query.append(" LEFT OUTER JOIN l.organization o  WHERE l.policyNo IS NOT NULL AND");
			query.append(
					" (l.policyStatus = org.ace.insurance.common.PolicyStatus.UPDATE  OR l.policyStatus = org.ace.insurance.common.PolicyStatus.LOAN OR l.policyStatus is NULL ) ");
			if (productList != null && !productList.isEmpty()) {
				query.append(" AND lp.product IN :productList");
			}

			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					query.append(" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) like :customerName OR "
							+ "CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName,  l.customer.name.lastName) like :customerName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					query.append(" AND l.policyNo like :policyNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					query.append(" AND l.organization.name like :organizationName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					query.append(" AND lp.idNo like :nrcNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					query.append(" AND CONCAT(lp.name.firstName, ' ', lp.name.middleName, ' ', lp.name.lastName) like :insuredPersonName OR "
							+ "CONCAT(lp.name.firstName, ' ', lp.name.middleName,  lp.name.lastName) like :insuredPersonName");
				}
			}

			Query q = em.createQuery(query.toString());
			if (productList != null && !productList.isEmpty()) {
				q.setParameter("productList", productList);
			}
			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					q.setParameter("nrcNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					q.setParameter("insuredPersonName", "%" + criteria.getCriteriaValue() + "%");
				}
			}
			q.setMaxResults(30);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve LifePolicy for Claim By Criteria", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findActiveSportManLifePolicy(LifePolicyCriteria criteria) throws DAOException {
		List<LifePolicySearch> result = null;
		String productId = KeyFactorChecker.getSportManId();

		try {
			StringBuffer query = new StringBuffer();
			query.append("Select New " + LifePolicySearch.class.getName() + "(l.policyNo,");
			query.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			query.append("o.name, l.branch.name,lp.idNo,lp.fatherName) From LifePolicy l INNER JOIN l.policyInsuredPersonList lp");
			query.append(" LEFT JOIN lp.product p");
			query.append(" LEFT OUTER JOIN l.customer c");
			query.append(" LEFT OUTER JOIN l.organization o");
			query.append(" WHERE l.policyNo IS NOT NULL AND p.id = :productId AND (l.policyStatus = org.ace.insurance.common.PolicyStatus.UPDATE");
			query.append(" OR l.policyStatus = org.ace.insurance.common.PolicyStatus.LOAN OR l.policyStatus is NULL ) ");

			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					query.append(" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) like :customerName OR "
							+ "CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName,  l.customer.name.lastName) like :customerName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					query.append(" AND l.policyNo like :policyNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					query.append(" AND l.organization.name like :organizationName");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					query.append(" AND lp.idNo like :nrcNo");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					query.append(" AND CONCAT(lp.name.firstName, ' ', lp.name.middleName, ' ', lp.name.lastName) like :insuredPersonName OR "
							+ "CONCAT(lp.name.firstName, ' ', lp.name.middleName,  lp.name.lastName) like :insuredPersonName");
				}
			}
			Query q = em.createQuery(query.toString());

			q.setParameter("productId", productId);
			if (criteria != null && criteria.getCriteriaValue() != null) {
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					q.setParameter("nrcNo", "%" + criteria.getCriteriaValue() + "%");
				}
				if (criteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					q.setParameter("insuredPersonName", "%" + criteria.getCriteriaValue() + "%");
				}
			}
			q.setMaxResults(30);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve LifePolicy for Claim By Criteria", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findByCriteria(PolicyCriteria criteria, int max) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, LPC001> resultMap = new HashMap<String, LPC001>();
		String branchId = userProcessService.getLoginUser().getLoginBranch().getId();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l.id, l.policyNo, a.initialId, a.name, c.initialId, c.name, o.name, b.name, pi.premium, pi.sumInsured, ");
			query.append(" t.name,sp.name FROM LifePolicy l INNER JOIN l.policyInsuredPersonList pi LEFT OUTER JOIN pi.product p ");
			query.append(" LEFT OUTER JOIN l.agent a LEFT OUTER JOIN l.customer c LEFT OUTER JOIN ");
			query.append(" l.organization o LEFT OUTER JOIN l.branch b LEFT OUTER JOIN l.salesPoints sp LEFT OUTER JOIN l.paymentType t ");
			if (criteria.getProduct() != null) {
				query.append(" WHERE p.name = :product AND ");
			} else {
				query.append(" WHERE ");
			}

			query.append(" (l.isEndorsementApplied = FALSE OR l.isEndorsementApplied IS NULL) ");
			query.append(" AND b.id = :branchId");

			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						query.append(" AND l.policyNo like :policyNo");
						break;
					}
					case CUSTOMERNAME: {
						query.append(" AND (c.name.firstName like :customerName" + " OR c.name.middleName like :customerName" + " OR c.name.lastName like :customerName)");
						break;
					}
					case ORGANIZATIONNAME: {
						query.append(" AND o.name like :organizationName ");
						break;
					}
				}
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("branchId", branchId);

			if (criteria.getProduct() != null) {
				q.setParameter("product", criteria.getProduct());
			}
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case CUSTOMERNAME: {
						q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case ORGANIZATIONNAME: {
						q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
				}
			}
			objectList = q.getResultList();
			em.flush();
			String id;
			String policyNo;
			String agentInitialId;
			Name agentName;
			String agent = null;
			String customerInitialId;
			Name customerName;
			String customer;
			String organization;
			String branch;
			double premium;
			double sumInsured;
			String paymenttype;
			String salePoint;

			for (Object[] b : objectList) {
				id = (String) b[0];
				policyNo = (String) b[1];
				agentInitialId = (String) b[2];
				agentName = (Name) b[3];
				customerInitialId = (String) b[4];
				customerName = (Name) b[5];
				organization = (String) b[6];
				branch = (String) b[7];
				premium = (Double) b[8];
				sumInsured = (Double) b[9];
				paymenttype = (String) b[10];
				salePoint = (String) b[11];
				if (agentName != null) {
					agent = agentInitialId + agentName.getFullName();
				}
				if (customerName != null) {
					customer = customerInitialId + customerName.getFullName();
				} else {
					customer = organization;
				}

				if (resultMap.containsKey(policyNo)) {
					premium += resultMap.get(policyNo).getPremium();
					sumInsured += resultMap.get(policyNo).getSumInsured();
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, true));
				} else {
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, false));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}
		RegNoSorter<LPC001> sorter = new RegNoSorter<LPC001>(new ArrayList<LPC001>(resultMap.values()));
		return sorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findPolicyByCriteria(PolicyCriteria criteria, int max) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, LPC001> resultMap = new HashMap<String, LPC001>();
		String branchId = userProcessService.getLoginUser().getLoginBranch().getId();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l.id, l.policyNo, a.initialId, a.name, c.initialId, c.name, o.name, b.name, pi.premium, pi.sumInsured, ");
			query.append(" t.name,sp.name FROM LifePolicy l INNER JOIN l.policyInsuredPersonList pi LEFT OUTER JOIN pi.product p ");
			query.append(" LEFT OUTER JOIN l.agent a LEFT OUTER JOIN l.customer c LEFT OUTER JOIN ");
			query.append(" l.organization o LEFT OUTER JOIN l.branch b LEFT OUTER JOIN l.salesPoints sp LEFT OUTER JOIN l.paymentType t ");
			if (criteria.getProduct() != null) {
				query.append(" WHERE p.name = :product AND ");
			} else {
				query.append(" WHERE ");
			}

			query.append(" b.id = :branchId");

			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						query.append(" AND l.policyNo like :policyNo");
						break;
					}
					case CUSTOMERNAME: {
						query.append(" AND (c.name.firstName like :customerName" + " OR c.name.middleName like :customerName" + " OR c.name.lastName like :customerName)");
						break;
					}
					case ORGANIZATIONNAME: {
						query.append(" AND o.name like :organizationName ");
						break;
					}
					default:
						break;
				}
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("branchId", branchId);

			if (criteria.getProduct() != null) {
				q.setParameter("product", criteria.getProduct());
			}
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case CUSTOMERNAME: {
						q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case ORGANIZATIONNAME: {
						q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					default:
						break;
				}
			}
			objectList = q.getResultList();
			em.flush();
			String id;
			String policyNo;
			String agentInitialId;
			Name agentName;
			String agent = null;
			String customerInitialId;
			Name customerName;
			String customer;
			String organization;
			String branch;
			double premium;
			double sumInsured;
			String paymenttype;
			String salePoint;

			for (Object[] b : objectList) {
				id = (String) b[0];
				policyNo = (String) b[1];
				agentInitialId = (String) b[2];
				agentName = (Name) b[3];
				customerInitialId = (String) b[4];
				customerName = (Name) b[5];
				organization = (String) b[6];
				branch = (String) b[7];
				premium = (Double) b[8];
				sumInsured = (Double) b[9];
				paymenttype = (String) b[10];
				salePoint = (String) b[11];
				if (agentName != null) {
					agent = agentInitialId + agentName.getFullName();
				}
				if (customerName != null) {
					customer = customerInitialId + customerName.getFullName();
				} else {
					customer = organization;
				}

				if (resultMap.containsKey(policyNo)) {
					premium += resultMap.get(policyNo).getPremium();
					sumInsured += resultMap.get(policyNo).getSumInsured();
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, true));
				} else {
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, false));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		RegNoSorter<LPC001> sorter = new RegNoSorter<LPC001>(new ArrayList<LPC001>(resultMap.values()));
		return sorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findByPageCriteria(PolicyCriteria criteria) throws DAOException {
		List<LifePolicy> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT l FROM LifePolicy l INNER JOIN l.policyInsuredPersonList p, Payment pay "
					+ " WHERE pay.referenceNo = l.id AND pay.referenceType IN :referenceTypeList AND pay.isReverse = 0 AND pay.complete = 1");
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO:
						query.append(" AND l.policyNo LIKE :policyNo ");
						break;
					case CUSTOMERNAME:
						query.append(
								" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) LIKE :name OR CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, l.customer.name.lastName) like :name ");
						break;
					case ORGANIZATIONNAME:
						query.append(" AND l.organization.name LIKE :name ");
						break;

					default:
						break;
				}
			}

			if (criteria.getFromDate() != null) {
				query.append(" AND l.activedPolicyEndDate >= :fromDate ");
			}

			if (criteria.getToDate() != null) {
				query.append(" AND l.activedPolicyEndDate <= :toDate ");
			}

			query.append(" AND pay.referenceNo NOT IN ( SELECT p.referenceNo FROM Payment p WHERE p.complete = '0' )");
			query.append(" ORDER BY l.policyNo ASC ");
			Query q = em.createQuery(query.toString());
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO:
						q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					case ORGANIZATIONNAME:
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
					case CUSTOMERNAME:
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
				}
			}

			if (criteria.getFromDate() != null) {
				q.setParameter("fromDate", Utils.resetStartDate(criteria.getFromDate()));
			}

			if (criteria.getToDate() != null) {
				q.setParameter("toDate", Utils.resetEndDate(criteria.getToDate()));
			}
			// FIXME CHECK REFTYPE
			q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.LIFE_BILL_COLLECTION, PolicyReferenceType.ENDOWNMENT_LIFE_POLICY));
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BillCollectionDTO> findBillCollectionByCriteria(PolicyCriteria criteria) throws DAOException {
		List<BillCollectionDTO> result = new ArrayList<BillCollectionDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT NEW " + BillCollectionDTO.class.getName() + "(l.id,");
			query.append(" l.policyNo,c.initialId, c.name,o.name, c.fullIdNo, l.coverageDate,l.activedPolicyEndDate, l.paymentType,");
			query.append(" l.lastPaymentTerm,SUM(COALESCE(p.sumInsured,0)), SUM(COALESCE(p.basicTermPremium,0)), SUM(COALESCE(p.addOnTermPremium,0)),");
			query.append(" 0.0, l.specialDiscount)");
			query.append(" FROM LifePolicy l LEFT JOIN l.customer c LEFT JOIN l.organization o");
			query.append(" LEFT JOIN l.policyInsuredPersonList p LEFT JOIN Payment pay ON pay.referenceNo = l.id");
			query.append(" WHERE l.coverageDate < l.activedPolicyEndDate ");
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
					case BANKCUSTOMER:
						query.append(" AND l.bank.name LIKE :value ");
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
			if (criteria.getBranch() != null) {
				query.append(" AND l.branch.id = :branchId");
			}

			query.append(" AND pay.referenceNo NOT IN ( SELECT p.referenceNo FROM Payment p WHERE p.complete = '0' )");
			query.append(" GROUP BY l.id,l.policyNo,c.initialId, c.name,o.name, c.fullIdNo, l.coverageDate,l.activedPolicyEndDate, l.paymentType");
			query.append(",l.lastPaymentTerm,l.specialDiscount,pay.id ORDER BY l.policyNo ASC ");
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
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (ReferenceType.ENDOWMENT_LIFE.equals(criteria.getReferenceType())) {
				q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.ENDOWNMENT_LIFE_POLICY, PolicyReferenceType.LIFE_BILL_COLLECTION));
			} else if (ReferenceType.SHORT_ENDOWMENT_LIFE.equals(criteria.getReferenceType())) {
				q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.SHORT_ENDOWMENT_LIFE_POLICY, PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION));
			} else if (ReferenceType.STUDENT_LIFE.equals(criteria.getReferenceType())) {
				q.setParameter("referenceTypeList", Arrays.asList(PolicyReferenceType.STUDENT_LIFE_POLICY, PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION));
			}

			result.addAll(q.getResultList());

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BillCollectionDTO> findPaidUpPolicyByCriteria(PolicyCriteria criteria) throws DAOException {
		List<BillCollectionDTO> result = new ArrayList<BillCollectionDTO>();
		try {
			String policyId = null;
			String policyNo = null;
			PaymentType paymentType = null;
			Date startDate = null;
			Date endDate = null;
			int lastPaymentTerm = 0;
			Double basicTermPremium = null;
			Double addOnTermPremium = null;
			Double serviceCharges = null;
			Double paidUpAmount = null;
			Double realPaidUpAmount = null;
			Double receivedPremium = null;
			Date paidUpDate = null;
			String initialId = null;
			Name name = null;
			String idNo = null;
			StringBuffer query = new StringBuffer();
			query.append("SELECT Distinct l.id, l.policyNo, l.paymentType, l.activedPolicyStartDate, l.activedPolicyEndDate, ");
			query.append(" l.lastPaymentTerm,   p.basicTermPremium, p.addOnTermPremium , ");
			query.append(" p.initialId, p.name, p.idNo , pro.paidUpAmount , pro.realPaidUpAmount ,pro.receivedPremium , pro.serviceCharges , pro.submittedDate ");
			query.append(" FROM LifePolicy l JOIN l.policyInsuredPersonList p LEFT JOIN LifePaidUpProposal pro  ");
			query.append(" WHERE pro.complete = 0 AND pro.lifePolicy.id = l.id ");
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO:
						query.append(" AND l.policyNo LIKE :policyNo ");
						break;
					case CUSTOMERNAME:
						query.append(
								" AND CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, ' ', l.customer.name.lastName) LIKE :name OR CONCAT(l.customer.name.firstName, ' ', l.customer.name.middleName, l.customer.name.lastName) like :name ");
						break;
					case ORGANIZATIONNAME:
						query.append(" AND l.organization.name LIKE :name ");
						break;
					case BANKCUSTOMER:
						query.append(" AND l.bank.name LIKE :name ");
						break;
				}
			}
			if (criteria.getFromDate() != null) {
				query.append(" AND pro.activedPolicyEndDate >= :fromDate ");
			}

			if (criteria.getToDate() != null) {
				query.append(" AND pro.activedPolicyEndDate <= :toDate ");
			}

			query.append(" ORDER BY l.policyNo ASC ");
			Query q = em.createQuery(query.toString());
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO:
						q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					case ORGANIZATIONNAME:
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
					case CUSTOMERNAME:
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
					case BANKCUSTOMER:
						q.setParameter("name", "%" + criteria.getCriteriaValue() + "%");
						break;
				}
			}

			if (criteria.getFromDate() != null) {
				q.setParameter("fromDate", Utils.resetStartDate(criteria.getFromDate()));
			}

			if (criteria.getToDate() != null) {
				q.setParameter("toDate", Utils.resetEndDate(criteria.getToDate()));
			}

			List<Object[]> objectList = q.getResultList();

			for (Object[] arr : objectList) {
				policyId = (String) arr[0];
				policyNo = (String) arr[1];
				paymentType = (PaymentType) arr[2];
				startDate = (Date) arr[3];
				endDate = (Date) arr[4];
				lastPaymentTerm = (Integer) arr[5];
				lastPaymentTerm = lastPaymentTerm == 0 ? 2 : lastPaymentTerm + 1;
				basicTermPremium = (Double) arr[6];
				addOnTermPremium = (Double) arr[7];
				basicTermPremium = basicTermPremium == null ? 0 : basicTermPremium;
				addOnTermPremium = addOnTermPremium == null ? 0 : addOnTermPremium;
				initialId = (String) arr[8];
				name = (Name) arr[9];
				idNo = (String) arr[10];
				paidUpAmount = (Double) arr[11];
				realPaidUpAmount = (Double) arr[12];
				receivedPremium = (Double) arr[13];
				serviceCharges = (Double) arr[14];
				paidUpDate = (Date) arr[15];
				result.add(new BillCollectionDTO(policyId, policyNo, initialId + name.getFullName(), idNo, startDate, endDate, paymentType, 1, lastPaymentTerm,
						basicTermPremium.doubleValue() + addOnTermPremium.doubleValue(), 0, 0, serviceCharges, paidUpAmount, realPaidUpAmount, receivedPremium, paidUpDate));

			}

			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBillCollection(LifePolicy lifePolicy) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE LifePolicy p SET p.lastPaymentTerm = :lastPaymentTerm, p.coverageDate = :coverageDate WHERE p.id = :id ");
			q.setParameter("lastPaymentTerm", lifePolicy.getLastPaymentTerm());
			q.setParameter("coverageDate", lifePolicy.getCoverageDate());
			q.setParameter("id", lifePolicy.getId());
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Bill Collection after life policy", pe);
		}
	}

	/** to generate Bill Collection Noti Letter */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicyNotificationDTO> findNotificationLifePolicy(NotificationCriteria criteria, List<String> productList) throws DAOException {
		List<LifePolicyNotificationDTO> result = new ArrayList<LifePolicyNotificationDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW " + LifePolicyNotificationDTO.class.getName() + "(l.id, l.policyNo,");
			query.append("CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			query.append("c.fullIdNo, l.paymentType.name, l.lastPaymentTerm,");
			query.append("SUM(pi.basicTermPremium),l.activedPolicyStartDate, l.activedPolicyEndDate,l.coverageDate, CASE WHEN (s.refund IS NULL ) THEN 0.0 ELSE s.refund END )");
			if (ReferenceType.ENDOWMENT_LIFE.equals(criteria.getProduct()) || ReferenceType.SHORT_ENDOWMENT_LIFE.equals(criteria.getProduct())
					|| ReferenceType.STUDENT_LIFE.equals(criteria.getProduct()) || ReferenceType.GOVERNMENT_SHORT_ENDOWMENT_LIFE.equals(criteria.getProduct())) {
				query.append(" FROM LifePolicy l INNER JOIN l.customer c INNER JOIN l.policyInsuredPersonList pi");
			} 
			if (ReferenceType.HEALTH.equals(criteria.getProduct()) || ReferenceType.CRITICAL_ILLNESS.equals(criteria.getProduct())) {
				query.append(" FROM MedicalPolicy l INNER JOIN l.customer c INNER JOIN l.policyInsuredPersonList pi");
			}
			query.append(" LEFT JOIN LifePolicySummary s ON s.policyNo = l.policyNo");

			if (criteria.getReportType().equalsIgnoreCase("Monthly")) {
				query.append(" WHERE l.coverageDate != l.activedPolicyEndDate AND l.coverageDate >= :startDate AND l.coverageDate <= :endDate");
				query.append(" AND pi.product.id IN :productList");
			} else {
				query.append(" WHERE l.coverageDate != l.activedPolicyEndDate");
				query.append(" AND pi.product.id IN :productList");
				if (criteria.getStartDate() != null) {
					criteria.setStartDate(Utils.resetStartDate(criteria.getStartDate()));
					query.append(" AND l.coverageDate >= :startDate");
				}
				if (criteria.getEndDate() != null) {
					criteria.setEndDate(Utils.resetStartDate(criteria.getEndDate()));
					query.append(" AND l.coverageDate <= :endDate");
				}
			}
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				query.append(" AND l.policyNo LIKE :policyNo");
			}
			if (criteria.getBranch() != null) {
				query.append(" AND l.branch.id = :branchId");
			}
			if (criteria.getSalePoint() != null) {
				query.append(" AND l.salesPoints.id <= :salePointId");
			}
			query.append(" GROUP BY l.id,l.policyNo,c.initialId,c.name,c.fullIdNo,l.paymentType.name,l.lastPaymentTerm,");
			query.append("l.activedPolicyStartDate,l.activedPolicyEndDate,l.coverageDate,s.refund");

			Query q = em.createQuery(query.toString());

			q.setParameter("productList", productList);
			if (criteria.getPolicyNo() != null && !criteria.getPolicyNo().isEmpty()) {
				q.setParameter("policyNo", "%" + criteria.getPolicyNo() + "%");
			}
			if (criteria.getBranch() != null) {
				q.setParameter("branchId", criteria.getBranch().getId());
			}
			if (criteria.getSalePoint() != null) {
				q.setParameter("salePointId", criteria.getSalePoint().getId());
			}
			if (criteria.getReportType().equalsIgnoreCase("Monthly")) {
				q.setParameter("startDate", Utils.getStartDate(criteria.getYear(), criteria.getMonth().getValue()));
				q.setParameter("endDate", Utils.getEndDate(criteria.getYear(), criteria.getMonth().getValue()));
			} else {
				if (criteria.getStartDate() != null) {
					q.setParameter("startDate", criteria.getStartDate());
				}
				if (criteria.getEndDate() != null) {
					q.setParameter("endDate", criteria.getEndDate());
				}
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}
		return result;
	}

	public boolean isBetweenNotificationDays(Date startDate, Date endDate, Date policyDate) {
		if (policyDate.getTime() >= startDate.getTime() && policyDate.getTime() <= endDate.getTime()) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(EndorsementLifePolicyPrint endorsementPolicyPrint) throws DAOException {
		try {
			em.persist(endorsementPolicyPrint);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to insert FirePolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(EndorsementLifePolicyPrint endorsementPolicyPrint) throws DAOException {
		try {
			em.merge(endorsementPolicyPrint);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("failed to update LifePolicy", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public EndorsementLifePolicyPrint findEndorsementLifePolicyPrintById(String id) throws DAOException {
		EndorsementLifePolicyPrint result = null;
		try {
			result = em.find(EndorsementLifePolicyPrint.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find EndorsementPolicyPrint", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<EndorsementLifePolicyPrint> findEndorsementPolicyPrintByLifePolicyNo(String lifePolicyNo) throws DAOException {
		List<EndorsementLifePolicyPrint> result = null;
		try {
			Query q = em.createQuery("SELECT e FROM EndorsementLifePolicyPrint e WHERE e.lifePolicy.policyNo = :lifePolicyNo ");
			q.setParameter("lifePolicyNo", lifePolicyNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find EndorsementPolicyPrint by firePolicyNo", pe);
		}
		return result;
	}

	@Override
	public String findPolicyNoById(String policyId) throws DAOException {
		String result = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findPolicyNoById");
			q.setParameter("id", policyId);
			result = (String) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find PolicyNo by PolicyId : " + policyId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public LifePolicy findByLifeProposalId(String lifeProposalId) throws DAOException {
		LifePolicy result = null;
		LifePolicyHistory history = null;
		try {
			Query q = em.createNamedQuery("LifePolicy.findByLifeProposalId");
			q.setParameter("lifeProposalId", lifeProposalId);
			result = (LifePolicy) q.getSingleResult();
			em.flush();
		} catch (NoResultException nre) {
			history = lifePolicyHistoryDAO.findByProposalId(lifeProposalId);
			if (history != null)
				return new LifePolicy(history);
			else
				return result;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifePolicy", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByReceiptNoCriteria(ReceiptNoCriteria criteria, int max) throws DAOException {
		List<Payment> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT p FROM LifePolicy l, Payment p, TLF t WHERE (l.isEndorsementApplied = FALSE OR l.isEndorsementApplied IS NULL) ");
			if (criteria.getReceiptNoCriteria() != null) {
				switch (criteria.getReceiptNoCriteria()) {
					case RECEIPTNO: {
						query.append("AND p.receiptNo like :receiptNo AND l.id = p.referenceNo AND p.isPO = TRUE ");
						query.append("AND l.id = t.referenceNo AND t.clearing = TRUE AND t.paid = FALSE ");
						break;
					}
				}
			}
			query.append("Order By p.receiptNo DESC ");
			Query q = em.createQuery(query.toString());
			q.setMaxResults(max);
			if (criteria.getReceiptNoCriteria() != null) {
				switch (criteria.getReceiptNoCriteria()) {
					case RECEIPTNO: {
						q.setParameter("receiptNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
				}
			}
			result = q.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> findPaymentOrderByReceiptNo(String receiptNo, PolicyReferenceType policyReferenceType) throws DAOException {
		List<LifePolicy> result = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT m FROM LifePolicy m, Payment p, TLF t WHERE m.id = p.referenceNo ");
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

	// used for migration
	// delete after migration
	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyPersonCode(List<PolicyInsuredPerson> personList) throws DAOException {
		try {
			for (PolicyInsuredPerson person : personList) {
				Query query = em.createQuery("UPDATE PolicyInsuredPerson p SET p.insPersonCodeNo = :personCodeNo WHERE p.id = :id");
				query.setParameter("id", person.getId());
				query.setParameter("personCodeNo", person.getInsPersonCodeNo());
				query.executeUpdate();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to update insured person code no", pe);
		}
	}

	// used for migration
	// delete after migration
	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePolicyBeneficiaryCode(List<PolicyInsuredPersonBeneficiaries> beneficiaryList) throws DAOException {
		try {
			for (PolicyInsuredPersonBeneficiaries beneficiary : beneficiaryList) {
				Query query = em.createQuery("UPDATE PolicyInsuredPersonBeneficiaries p SET p.beneficiaryNo = :beneficiaryNo WHERE p.id = :id");
				query.setParameter("id", beneficiary.getId());
				query.setParameter("beneficiaryNo", beneficiary.getBeneficiaryNo());
				query.executeUpdate();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to update beneficiary code no", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LPC001> findEndorsementByCriteria(PolicyCriteria criteria, int max) throws DAOException {
		List<Object[]> objectList = new ArrayList<Object[]>();
		Map<String, LPC001> resultMap = new HashMap<String, LPC001>();
		String branchId = idConfigLoader.getBranchId();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT l.id, l.policyNo, a.initialId, a.name, c.initialId, c.name, o.name, b.name, pi.premium, pi.sumInsured, ");
			query.append(" t.name,sp.name FROM LifePolicy l INNER JOIN l.policyInsuredPersonList pi LEFT OUTER JOIN pi.product p ");
			query.append(" LEFT OUTER JOIN l.agent a LEFT OUTER JOIN l.customer c LEFT OUTER JOIN ");
			query.append(" l.organization o LEFT OUTER JOIN l.branch b LEFT OUTER JOIN l.salesPoints sp LEFT OUTER JOIN l.paymentType t ");
			if (criteria.getProduct() != null) {
				query.append(" WHERE p.productContent.name = :product AND ");
			} else {
				query.append(" WHERE ");
			}
			query.append(" (l.isEndorsementApplied = FALSE OR l.isEndorsementApplied IS NULL) ");
			query.append(" AND p.insuranceType = :insuranceType AND p.id != :productId");
			query.append(" AND l.policyNo IS NOT NULL");
			query.append(" AND b.id = :branchId");

			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						query.append(" AND l.policyNo like :policyNo");
						break;
					}
					case CUSTOMERNAME: {
						query.append(" AND (c.name.firstName like :customerName" + " OR c.name.middleName like :customerName" + " OR c.name.lastName like :customerName)");
						break;
					}
					case ORGANIZATIONNAME: {
						query.append(" AND o.name like :organizationName ");
						break;
					}
					case INSUREDPERSON_NAME: {
						query.append(" AND (pi.name.firstName like :insuredPersonName" + " OR pi.name.middleName like :insuredPersonName"
								+ " OR pi.name.lastName like :insuredPersonName)");
						break;
					}
					case NRC_NO: {
						query.append(" AND pi.idNo like :nrcNo ");
						break;
					}
				}
			}

			Query q = em.createQuery(query.toString());
			q.setMaxResults(max);
			q.setParameter("branchId", branchId);

			if (criteria.getProduct() != null) {
				q.setParameter("product", criteria.getProduct());
			}
			q.setParameter("insuranceType", InsuranceType.LIFE);
			q.setParameter("productId", KeyFactorIDConfig.getFarmerId());
			if (criteria.getPolicyCriteria() != null) {
				switch (criteria.getPolicyCriteria()) {
					case POLICYNO: {
						q.setParameter("policyNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case CUSTOMERNAME: {
						q.setParameter("customerName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case ORGANIZATIONNAME: {
						q.setParameter("organizationName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case INSUREDPERSON_NAME: {
						q.setParameter("insuredPersonName", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
					case NRC_NO: {
						q.setParameter("nrcNo", "%" + criteria.getCriteriaValue() + "%");
						break;
					}
				}
			}
			objectList = q.getResultList();
			em.flush();
			String id;
			String policyNo;
			String agentInitialId;
			Name agentName;
			String agent = null;
			String customerInitialId;
			Name customerName;
			String customer;
			String organization;
			String branch;
			double premium;
			double sumInsured;
			String paymenttype;
			String salePoint;

			for (Object[] b : objectList) {
				id = (String) b[0];
				policyNo = (String) b[1];
				agentInitialId = (String) b[2];
				agentName = (Name) b[3];
				customerInitialId = (String) b[4];
				customerName = (Name) b[5];
				organization = (String) b[6];
				branch = (String) b[7];
				premium = (Double) b[8];
				sumInsured = (Double) b[9];
				paymenttype = (String) b[10];
				salePoint = (String) b[11];
				if (agentName != null) {
					agent = agentInitialId + agentName.getFullName();
				}
				if (customerName != null) {
					customer = customerInitialId + customerName.getFullName();
				} else {
					customer = organization;
				}

				if (resultMap.containsKey(policyNo)) {
					premium += resultMap.get(policyNo).getPremium();
					sumInsured += resultMap.get(policyNo).getSumInsured();
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, true));
				} else {
					resultMap.put(policyNo, new LPC001(id, policyNo, agent, customer, branch, salePoint, premium, sumInsured, paymenttype, false));
				}
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		RegNoSorter<LPC001> sorter = new RegNoSorter<LPC001>(new ArrayList<LPC001>(resultMap.values()));
		return sorter.getSortedList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<SportManTravelAbroad> findSportManAbroadListByInvoiceNo(String invoiceNo) {
		List<SportManTravelAbroad> result = null;
		try {
			Query q = em.createQuery("SELECT s FROM SportManTravelAbroad s WHERE s.invoiceNo = :invoiceNo");
			q.setParameter("invoiceNo", invoiceNo);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find SportManTravelAbroad List by InvoiceNo " + invoiceNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<UPRReportView> findUPRReport(UPRReportCriteria criteria) throws DAOException {
		List<UPRReportView> resultList = new ArrayList<UPRReportView>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT u FROM UPRReportView u WHERE u.policyNo IS NOT NULL");

			if (criteria.getStartDate() != null)
				query.append(" AND u.activedPolicyStartDate >= :startDate");

			if (criteria.getEndDate() != null)
				query.append(" AND u.activedPolicyStartDate <= :endDate");

			if (criteria.getSalePointId() != null)
				query.append(" AND u.salePointId = :salePointId");

			if (criteria.getBranchId() != null)
				query.append(" AND u.branchId = :branchId");

			if (criteria.getBranchId() != null)
				query.append(" AND u.branchId = :branchId");

			if (criteria.getProductId() != null)
				query.append(" AND u.productId = :productId");

			query.append(" ORDER BY u.activedPolicyStartDate");

			Query q = em.createQuery(query.toString());

			if (criteria.getStartDate() != null)
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));

			if (criteria.getEndDate() != null)
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));

			if (criteria.getSalePointId() != null)
				q.setParameter("salePointId", criteria.getSalePointId());

			if (criteria.getBranchId() != null)
				q.setParameter("branchId", criteria.getBranchId());

			if (criteria.getProductId() != null)
				q.setParameter("productId", criteria.getProductId());

			resultList = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find UPR Report", pe);
		}
		return resultList;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicySearch> findNonFullPaidDisClaimPolicy(LifePolicyCriteria lifePolicyCriteria) {
		List<LifePolicySearch> result = null;
		try {
			// informed death Claim
			// 100% disability Claim
			// does not complete
			// already submitted notification
			StringBuffer query = new StringBuffer();
			query.append("Select New " + LifePolicySearch.class.getName() + "(l.policyNo,");
			query.append(" CONCAT(TRIM(c.initialId),' ', TRIM(c.name.firstName), ' ', TRIM(c.name.middleName), ' ', TRIM(c.name.lastName)),");
			query.append("o.name, l.branch.name,lp.idNo,lp.fatherName) From LifePolicy l INNER JOIN l.policyInsuredPersonList lp");
			query.append(" LEFT OUTER JOIN l.customer c");
			query.append(" LEFT OUTER JOIN l.organization o");
			query.append(" WHERE l.policyNo IS NOT NULL and lp.id not in (select n.claimPerson.id from LifeClaimNotification n where n.claimStatus=:claimStatus)");
			query.append(
					" AND l.id not in (Select case when sum(dl.percentage)=100 then l.lifePolicy.id else '' end from LifeClaimProposal l left join l.lifePolicyClaim p,DisabilityLifeClaim d join d.disabilityLifeClaimList dl");
			query.append(" where p.id=d.id group by l.lifePolicy.id)");
			query.append(" AND l.id not in (Select l.lifePolicy.id from LifeClaimProposal l where l.complete=false)");
			query.append(" AND l.id not in (Select l.lifePolicy.id from LifeClaimProposal l left join l.lifePolicyClaim p,");
			query.append("LifeDeathClaim ld where p.id=ld.id)");
			query.append(" AND l.id not in (Select l.lifePolicy.id from LifeClaimProposal l where l.lifePolicy.policyStatus=:policyStatus)");
			if (lifePolicyCriteria != null && lifePolicyCriteria.getCriteriaValue() != null) {
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					query.append(" AND CONCAT(c.name.firstName, ' ', c.name.middleName, ' ', c.name.lastName) like :customerName");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					query.append(" AND l.policyNo like :policyNo");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					query.append(" AND l.organization.name like :organizationName");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					query.append(" AND lp.idNo like :nrcNo");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					query.append(" AND CONCAT(lp.name.firstName, ' ', lp.name.middleName, ' ', lp.name.lastName) like :insuredPersonName OR "
							+ "CONCAT(lp.name.firstName, ' ', lp.name.middleName,  lp.name.lastName) like :insuredPersonName");
				}
			}

			Query q = em.createQuery(query.toString());
			q.setParameter("claimStatus", org.ace.insurance.medical.claim.ClaimStatus.INITIAL_INFORM);
			q.setParameter("policyStatus", PolicyStatus.TERMINATE);
			if (lifePolicyCriteria != null && lifePolicyCriteria.getCriteriaValue() != null) {
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.CUSTOMER_NAME) {
					q.setParameter("customerName", "%" + lifePolicyCriteria.getCriteriaValue() + "%");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.POLICY_NO) {
					q.setParameter("policyNo", "%" + lifePolicyCriteria.getCriteriaValue() + "%");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.ORGANIZATION_NAME) {
					q.setParameter("organizationName", "%" + lifePolicyCriteria.getCriteriaValue() + "%");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.NRC) {
					q.setParameter("nrcNo", "%" + lifePolicyCriteria.getCriteriaValue() + "%");
				}
				if (lifePolicyCriteria.getLifePolicyCriteriaItems() == LifePolicyCriteriaItems.INSURED_PERSON_NAME) {
					q.setParameter("insuredPersonName", "%" + lifePolicyCriteria.getCriteriaValue() + "%");
				}
			}
			q.setMaxResults(30);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to retrieve LifePolicy for Claim By Criteria", pe);
		}

		return result;
	}

}
