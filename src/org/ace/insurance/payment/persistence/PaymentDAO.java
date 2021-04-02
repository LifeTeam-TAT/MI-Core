package org.ace.insurance.payment.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PaymentReferenceType;
import org.ace.insurance.common.PolicyCriteria;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.surrender.PaymentTrackDTO;
import org.ace.insurance.medical.claim.service.interfaces.IMedicalClaimProposalService;
import org.ace.insurance.payment.AgentCommission;
import org.ace.insurance.payment.CashDeno;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.TLF;
import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.ace.insurance.report.TLF.TLFVoucherDTO;
import org.ace.insurance.web.common.PaymentTableDTO;
import org.ace.insurance.web.manage.life.billcollection.BC0001;
import org.ace.java.component.idgen.service.interfaces.ICustomIDGenerator;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("PaymentDAO")
public class PaymentDAO extends BasicDAO implements IPaymentDAO {
	@Resource(name = "IDConfigLoader")
	private IDConfigLoader idConfigLoader;

	@Resource(name = "CustomIDGenerator")
	private ICustomIDGenerator customIDGenerator;

	@Resource(name = "PaymentDAO")
	private IPaymentDAO paymentDAO;

	@Resource(name = "MedicalClaimProposalService")
	private IMedicalClaimProposalService medicalClaimProposalService;

	@Transactional(propagation = Propagation.REQUIRED)
	public double findUSDActiveRate() throws DAOException {
		StringBuffer query = new StringBuffer();
		double rate = 0.0;
		query.append("Select rate from rateinfo where rateType = 'CS' and lastModify = 1 And Cur = 'USD'");
		Query q = em.createNativeQuery(query.toString());
		Number result = (Number) q.getSingleResult();
		rate = result.doubleValue();
		return rate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findSGDActiveRate() throws DAOException {
		StringBuffer query = new StringBuffer();
		double rate = 0.0;
		query.append("Select rate from rateinfo where rateType = 'CS' and lastModify = 1 And Cur = 'SGD'");
		Query q = em.createNativeQuery(query.toString());
		Number result = (Number) q.getSingleResult();
		rate = result.doubleValue();
		return rate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment insert(Payment payment) throws DAOException {
		try {
			em.persist(payment);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Payment", pe);
		}
		return payment;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double findActiveRate() throws DAOException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT EXCHANGERATE FROM RATEINFO WHERE RATETYPE = 'CS' AND LASTMODIFY = 1 AND CUR = '" + KeyFactorIDConfig.getUSDCurrencyId() + "'");
		Query q = em.createNativeQuery(query.toString());
		Number result = (Number) q.getSingleResult();
		double rate = result.doubleValue();
		return rate;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertAgentCommission(AgentCommission agentCommission) throws DAOException {
		try {
			em.persist(agentCommission);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Payment", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment update(Payment payment) throws DAOException {
		try {
			payment = em.merge(payment);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Payment", pe);
		}
		return payment;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TLF updateTLF(TLF tlf) throws DAOException {
		try {
			tlf = em.merge(tlf);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Payment", pe);
		}
		return tlf;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentDTO> findByReceiptNo(List<String> receiptList, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		List<PaymentDTO> result = new ArrayList<PaymentDTO>();
		try {
			StringBuffer buffer = new StringBuffer("SELECT m FROM Payment m WHERE m.receiptNo = :receiptNo AND m.referenceType = :referenceType");
			if (complete != null) {
				buffer.append(" AND m.complete = " + complete);
			}
			Query q = em.createQuery(buffer.toString());
			for (String receiptNo : receiptList) {
				q.setParameter("receiptNo", receiptNo);
				q.setParameter("referenceType", referenceType);
				List<Payment> paymentList = q.getResultList();
				if (paymentList != null && !paymentList.isEmpty()) {
					PaymentDTO paymentDTO = new PaymentDTO(paymentList);
					result.add(paymentDTO);
				}
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReceiptNo : ", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByReceiptNo(String receiptNo, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		List<Payment> paymentList = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT m FROM Payment m WHERE m.receiptNo = :receiptNo AND m.referenceType = :referenceType");
			if (complete != null) {
				buffer.append(" AND m.complete = " + complete);
			}
			Query q = em.createQuery(buffer.toString());
			q.setParameter("receiptNo", receiptNo);
			q.setParameter("referenceType", referenceType);
			paymentList = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReceiptNo : ", pe);
		}
		return paymentList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findPaymentByReferenceNo(String referenceNo) throws DAOException {
		Payment result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			q.setMaxResults(1);
			result = (Payment) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + referenceNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByReferenceNo(String referenceNo) throws DAOException {
		List<Payment> result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			result = q.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + referenceNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findPaymentByReferenceNoAndIsComplete(String referenceNo, Boolean complete) throws DAOException {
		Payment result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT m FROM Payment m WHERE m.referenceNo = :referenceNo AND m.complete = :complete");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", referenceNo);
			q.setParameter("complete", complete);
			result = (Payment) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo And Is Compelte: " + referenceNo + complete, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean findPaymentByReferenceNoAndIsNotComplete(String referenceNo) {
		Long i;
		try {
			StringBuffer buffer = new StringBuffer("SELECT count(m.id) FROM Payment m WHERE m.referenceNo = :referenceNo AND m.complete = :complete");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", referenceNo);
			q.setParameter("complete", false);
			i = (Long) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo And Is Not Compelte: " + referenceNo, pe);
		}
		return i == 0 ? true : false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public AgentCommission findAgentCommissionByReferenceNo(String referenceNo) throws DAOException {
		AgentCommission result = null;
		try {
			Query q = em.createNamedQuery("AgentCommission.findByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			result = (AgentCommission) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + referenceNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByPolicy(String policyId, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		List<Payment> result = null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT p FROM Payment p WHERE p.referenceType = :referenceType ");
		if (complete != null) {
			buffer.append(" AND p.complete = :complete ");
		}
		buffer.append(" AND p.referenceNo = :policyId ");
		Query query = em.createQuery(buffer.toString());
		query.setParameter("policyId", policyId);
		query.setParameter("referenceType", referenceType);
		if (complete != null) {
			query.setParameter("complete", complete);
		}
		result = query.getResultList();
		em.flush();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByProposal(String proposalId, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		List<Payment> result = null;
		try {
			String policy = null;
			String policyHistory = null;
			String concatQuery = null;
			StringBuffer buffer = new StringBuffer();
			// referenceNo of payment to policy is missing (because of
			// endorsement and renewal process), then find in policy history
			switch (referenceType) {

				case ENDOWNMENT_LIFE_POLICY:
				case GROUP_LIFE_POLICY:
				case SPORT_MAN_POLICY:
				case SEAMAN_LIFE_POLICY:
				case PA_POLICY:
				case FARMER_POLICY:
				case SNAKE_BITE_POLICY:
				case STUDENT_LIFE_POLICY:
				case PUBLIC_TERM_LIFE_POLICY:
				case GOVERNMENT_SHORT_ENDOWMENT_LIFE_POLICY:
				case SHORT_ENDOWMENT_LIFE_POLICY: {
					policy = "LifePolicy x";
					policyHistory = "LifePolicyHistory x";
					concatQuery = "x.lifeProposal.id = :proposalId";
				}
					break;

				case CRITICAL_ILLNESS_POLICY:
				case MICRO_HEALTH_POLICY:
				case HEALTH_POLICY: {
					policy = "MedicalPolicy x";
					concatQuery = "x.medicalProposal.id = :proposalId";
				}

				default:
					break;
			}

			buffer.append("SELECT p FROM Payment p WHERE p.referenceType = :referenceType ");
			if (complete != null) {
				buffer.append(" AND p.complete = :complete ");
			}

			buffer.append(" AND p.referenceNo IN (SELECT x.id FROM " + policy + " WHERE " + concatQuery + ")");
			// FIXME CHECK REFTYPE
			if (PolicyReferenceType.ENDOWNMENT_LIFE_POLICY.equals(referenceType)) {
				buffer.append(" UNION ");
				buffer.append("SELECT p FROM Payment p WHERE p.referenceType = :referenceType ");
				if (complete != null) {
					buffer.append(" AND p.complete = :complete ");
				}
				buffer.append(" AND p.referenceNo IN (SELECT x.policyReferenceNo FROM " + policyHistory + " WHERE " + concatQuery
						+ " AND x.entryType != org.ace.insurance.common.PolicyHistoryEntryType.UNDERWRITING)");
			}

			Query query = em.createQuery(buffer.toString());
			query.setParameter("proposalId", proposalId);
			query.setParameter("referenceType", referenceType);
			if (complete != null) {
				query.setParameter("complete", complete);
			}

			result = query.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find PaymentList by Proposal ID : " + proposalId, pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByClaimProposal(String claimId, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		List<Payment> result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNoAndReferenceType");
			q.setParameter("referenceNo", claimId);
			q.setParameter("referenceType", referenceType);
			q.setParameter("complete", complete);
			result = q.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo and ReferenceType : " + claimId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findClaimProposal(String claimId, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		Payment result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNoAndReferenceType");
			q.setParameter("referenceNo", claimId);
			q.setParameter("referenceType", referenceType);
			q.setParameter("complete", complete);
			result = (Payment) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo and ReferenceType : " + claimId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public TLF insertTLF(TLF tlf) throws DAOException {
		try {
			em.persist(tlf);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert TLF", pe);
		}
		return tlf;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertTLFList(List<TLF> tlfList) throws DAOException {
		try {
			for (TLF tlf : tlfList) {
				em.persist(tlf);
				em.flush();
			}
		} catch (PersistenceException pe) {
			throw translate("Failed to insert TLF", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByPolicy(String policyId) throws DAOException {
		List<Payment> result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNo");
			q.setParameter("referenceNo", policyId);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + policyId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public CashDeno insertCashDeno(CashDeno cashDeno) throws DAOException {
		try {
			em.persist(cashDeno);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert CashDeno", pe);
		}
		return cashDeno;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String findCheckOfAccountNameByCode(String acName, String branchId, String currencyId) throws DAOException {
		String result = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append("SELECT a.ccoaId FROM COASetup a WHERE a.acName = :acName AND a.currencyId = :currencyId AND a.branchId = :branchId");
			Query query = em.createQuery(str.toString());
			query.setParameter("acName", acName);
			query.setParameter("currencyId", currencyId);
			query.setParameter("branchId", branchId);
			result = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert TLF", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String findCCOAByCode(String acCode, String branchId, String currencyId) {
		String result = null;
		try {
			StringBuffer str = new StringBuffer();
			str.append("SELECT ccoa.id FROM CCOA ccoa JOIN Coa coa ON coa.id = ccoa.coaId ");
			str.append("WHERE coa.acCode = :acCode AND ccoa.currencyId = :currencyId AND ccoa.branchId = :branchId");
			Query query = em.createQuery(str.toString());
			query.setParameter("acCode", acCode);
			query.setParameter("currencyId", currencyId);
			query.setParameter("branchId", branchId);
			result = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find ccoaid BY acCode,currencyId,branchId", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findCOAAccountNameByCode(String groupId) throws DAOException {
		String result = null;
		try {
			Query query = em.createQuery("SELECT a.acName FROM Coa a WHERE a.id = :groupId");
			query.setParameter("groupId", groupId);
			result = (String) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find COA account name", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findByClaimProposalComplete(String claimId, PolicyReferenceType referenceType) throws DAOException {
		List<Payment> result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByReferenceNoAndReferenceTypeComplete");
			q.setParameter("referenceNo", claimId);
			q.setParameter("referenceType", referenceType);
			result = q.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo and ReferenceType : " + claimId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBillCollection(String receiptNo, boolean complete) throws DAOException {
		try {
			Query q = em.createQuery("UPDATE Payment p SET p.complete = :complete WHERE p.receiptNo = :receiptNo ");
			q.setParameter("complete", complete);
			q.setParameter("receiptNo", receiptNo);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Bill Collection after Payment", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReferenceNoAndMaxDate(String referenceNo) throws DAOException {
		List<Payment> result = new ArrayList<Payment>();
		try {
			/* create query */
			Query query = em.createNamedQuery("Payment.findPaymentByReferenceNoAndMaxDate");
			query.setParameter("referenceNo", referenceNo);
			result = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find payment List by  ReferenceNo and Max Date : ", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReferenceNoAndMaxDateForAgentInvoice(String referenceNo) throws DAOException {
		List<Payment> result = new ArrayList<Payment>();
		try {
			/* create query */
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT p from Payment p WHERE p.paymentDate=(select MAX(p1.paymentDate) from Payment p1 where  p1.referenceNo = :referenceNo1) "
					+ "AND p.referenceNo = :referenceNo");
			Query query = em.createQuery(queryString.toString());
			query.setParameter("referenceNo1", referenceNo);
			query.setParameter("referenceNo", referenceNo);
			result = query.getResultList();
			em.flush();

		} catch (PersistenceException pe) {
			throw translate("Failed to find payment List by  ReferenceNo and Max Date : ", pe);
		}
		return result;
	}

	// new
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findPaymentByReceiptNo(String receiptNo) throws DAOException {
		List<Payment> result = null;
		try {
			Query q = em.createQuery("SELECT p FROM Payment p WHERE p.receiptNo = :receiptNo");
			q.setParameter("receiptNo", receiptNo);
			result = q.getResultList();
			em.flush();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReceiptNo : " + receiptNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<BC0001> findBCPaymentByPolicyNo(PolicyCriteria policyCriteria) throws DAOException {
		List<BC0001> result = null;
		try {
			String policyType = "";
			if (ReferenceType.ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType()) || ReferenceType.SHORT_ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType())
					|| ReferenceType.STUDENT_LIFE.equals(policyCriteria.getReferenceType())) {
				policyType = "LifePolicy";
			} else if (ReferenceType.HEALTH.equals(policyCriteria.getReferenceType()) || ReferenceType.CRITICAL_ILLNESS.equals(policyCriteria.getReferenceType())) {
				policyType = "MedicalPolicy";
			}
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW " + BC0001.class.getName() + "(l.id,");
			query.append(" p.policyNo,l.paymentType,");
			query.append(" p.basicPremium,l.coverageDate,p.confirmDate,p.receiptNo,p.invoiceNo,p.fromTerm,p.toTerm,p.referenceNo,p.referenceType)");
			query.append(" FROM Payment p, " + policyType + " l WHERE p.referenceNo = l.id AND p.referenceType =:referenceType");

			if (policyCriteria.getCriteriaValue() != null && !policyCriteria.getCriteriaValue().isEmpty()) {
				query.append(" AND p.policyNo = :policyNo ");
			}

			if (policyCriteria.getFromDate() != null) {
				query.append(" AND p.confirmDate >= :startDate ");
			}

			if (policyCriteria.getToDate() != null) {
				query.append(" AND p.confirmDate <= :endDate ");
			}
			query.append(" GROUP BY l.id,p.policyNo,l.paymentType,p.basicPremium,l.coverageDate,p.confirmDate,p.receiptNo,p.invoiceNo");
			query.append(",p.fromTerm,p.toTerm,p.referenceNo,p.referenceType,p.paymentDate ORDER BY p.policyNo,p.paymentDate ASC ");

			Query q = em.createQuery(query.toString());
			if (policyCriteria.getCriteriaValue() != null && !policyCriteria.getCriteriaValue().isEmpty()) {
				q.setParameter("policyNo", policyCriteria.getCriteriaValue());
			}
			if (policyCriteria.getFromDate() != null) {
				q.setParameter("startDate", policyCriteria.getFromDate());
			}

			if (policyCriteria.getToDate() != null) {
				q.setParameter("endDate", policyCriteria.getToDate());
			}
			if (ReferenceType.HEALTH.equals(policyCriteria.getReferenceType())) {
				q.setParameter("referenceType", PolicyReferenceType.HEALTH_POLICY_BILL_COLLECTION);
			} else if (ReferenceType.CRITICAL_ILLNESS.equals(policyCriteria.getReferenceType())) {
				q.setParameter("referenceType", PolicyReferenceType.CRITICAL_ILLNESS_POLICY_BILL_COLLECTION);
			} else if ((ReferenceType.SHORT_ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType()))) {
				q.setParameter("referenceType", PolicyReferenceType.SHORT_ENDOWMENT_LIFE_BILL_COLLECTION);
			} else if ((ReferenceType.ENDOWMENT_LIFE.equals(policyCriteria.getReferenceType()))) {
				q.setParameter("referenceType", PolicyReferenceType.LIFE_BILL_COLLECTION);
			} else if ((ReferenceType.STUDENT_LIFE.equals(policyCriteria.getReferenceType()))) {
				q.setParameter("referenceType", PolicyReferenceType.STUDENT_LIFE_POLICY_BILL_COLLECTION);
			}

			result = q.getResultList();
			em.flush();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by PolicyNo : " + policyCriteria.getCriteriaValue(), pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findReceiptNoByReferenceNo(String referenceNo) throws DAOException {
		String result = null;
		try {
			Query q = em.createNamedQuery("Payment.findReceiptNoByReferenceNo");
			q.setParameter("referenceNo", referenceNo);
			result = (String) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Cash Receipt No by Reference No : " + referenceNo, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String findReceiptNoByProposalId(String proposalId, String policyObject) throws DAOException {
		String result = null;
		try {
			Query q = em.createQuery("select p.receiptNo from Payment p, " + policyObject + " fp where p.referenceNo = fp.id and fp.fireProposal.id = :proposalId ");
			q.setParameter("proposalId", proposalId);
			result = (String) q.getSingleResult();
			em.flush();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Cash Receipt No by Proposal ID : " + proposalId, pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TLF> findTLFbyTLFNo(String tlfNo, Boolean isClearing) throws DAOException {

		List<TLF> results;
		try {

			String query = "Select t From TLF t Where t.tlfNo = :tlfNo ";
			if (isClearing != null) {
				query += "AND t.clearing = :clearing ";
			}
			TypedQuery<TLF> q = em.createQuery(query, TLF.class);
			q.setParameter("tlfNo", tlfNo);
			if (isClearing != null) {
				q.setParameter("clearing", isClearing);
			}

			results = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReceiptNo : ", pe);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TLF> findTLFbyReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType policyReferenceType) throws DAOException {

		List<TLF> results;
		try {
			String query = "Select t From TLF t Where t.referenceNo = :referenceNo and t.referenceType = :referenceType";
			TypedQuery<TLF> q = em.createQuery(query, TLF.class);
			q.setParameter("referenceNo", referenceNo);
			q.setParameter("referenceType", policyReferenceType);

			results = q.getResultList();

		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by Reference No and Reference Type : ", pe);
		}

		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePayments(List<Payment> paymentList) throws DAOException {
		try {
			for (Payment payment : paymentList) {
				List<TLF> tlfList = findTLFbyTLFNo(payment.getReceiptNo(), null);
				for (TLF tlf : tlfList) {
					TLF margedTLF = em.merge(tlf);
					em.remove(margedTLF);
				}
				Payment margedPayment = em.merge(payment);
				em.remove(margedPayment);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Customer", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteTLFs(List<TLF> tlfList) throws DAOException {
		try {
			for (TLF tlf : tlfList) {
				TLF margedTLF = em.merge(tlf);
				em.remove(margedTLF);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Customer", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<TLFVoucherDTO> findTLFVoucher(String receiptNo) throws DAOException {
		List<TLFVoucherDTO> result = new ArrayList<TLFVoucherDTO>();
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW " + TLFVoucherDTO.class.getName() + "(coa.acCode, tlf.narration, ttype.status, coa.acName, c ,");
			query.append(" p.confirmDate, tlf.homeAmount, p.rate, p.amount, p.basicPremium, p.addOnPremium, tlf.localAmount,coa1.acName,coa1.acCode) ");
			query.append(" FROM Payment p, TLF tlf INNER JOIN CCOA ccoa ON tlf.ccoaId = ccoa.id ");
			query.append(" INNER JOIN Coa coa ON coa.id=ccoa.coaId INNER JOIN Coa coa1 ON coa1.id=coa.groupId");
			query.append(" INNER JOIN TranType ttype ON ttype.id = tlf.tranTypeId INNER JOIN Currency c ON c.id = tlf.currencyId ");
			query.append(" WHERE p.receiptNo = tlf.tlfNo AND p.receiptNo = :receiptNo ");
			Query q = em.createQuery(query.toString());
			q.setParameter("receiptNo", receiptNo);
			result = q.getResultList();
			//
			// // for GL Code
			// // Query from COA by account base code
			// String baseAcName = null;
			// for (TLFVoucherDTO dto : result) {
			// baseAcName =
			// paymentDAO.findCOAAccountNameByCode(dto.getGlCode());
			// dto.setBaseAcName(baseAcName);
			// }
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of CashReceiptDTO by receiptNo.", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClaimVoucherDTO> findClaimVoucher(String claimNo, String damage) throws DAOException {
		List<ClaimVoucherDTO> result = new ArrayList<ClaimVoucherDTO>();
		String tableName;
		if (damage.equals("Own Vehicle Damage") || damage.equals("Third Party Vehicle Damage")) {
			tableName = "DamagedVehicle tb, ";
		} else if (damage.equals("Damage Other")) {
			tableName = "DamagedOther tb, ";
		} else {
			tableName = "PersonCasualty tb, ";
		}
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT NEW " + ClaimVoucherDTO.class.getName() + "(tlf.coaId, tlf.narration, coa.acName, tlf.status, p.claimAmount)");
			query.append("FROM MotorClaim mc, ");
			query.append(tableName);
			query.append("TLF tlf INNER JOIN Coa coa on tlf.coaId =  coa.acode, Payment p ");
			query.append("WHERE p.referenceNo = tlf.referenceNo AND ");
			query.append("tb.claim.id = mc.id AND tlf.referenceNo = tb.id ");
			query.append("AND mc.claimReferenceNo =:claimNo");
			Query q = em.createQuery(query.toString());
			q.setParameter("claimNo", claimNo);

			result = q.getResultList();
			// for GL Code
			// Query from COA by account base code
			String baseAcName = null;
			for (ClaimVoucherDTO dto : result) {
				baseAcName = paymentDAO.findCOAAccountNameByCode(dto.getGlCode());
				dto.setBaseAcName(baseAcName);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of CashReceiptDTO by receiptNo.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<TLF> findTLFbyENONo(String enoNo) throws DAOException {
		List<TLF> results;
		try {
			String query = "Select t From TLF t Where t.enoNo = :enoNo";
			TypedQuery<TLF> q = em.createQuery(query, TLF.class);
			q.setParameter("enoNo", enoNo);
			results = q.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReceiptNo : ", pe);
		}
		return results;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findByChalanNo(String chalanNo) throws DAOException {
		Payment result = null;
		try {
			Query q = em.createNamedQuery("Payment.findByChalanNo");
			q.setParameter("chalanNoid", chalanNo);
			result = (Payment) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + chalanNo, pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentTableDTO> findByChalanNoForClaim(List<String> receiptList, PaymentReferenceType referenceType, Boolean complete) throws DAOException {
		// TODO FIXME PSH Claim Case
		/*
		 * List<PaymentTableDTO> resultList = new ArrayList<PaymentTableDTO>();
		 * try { StringBuffer buffer = new StringBuffer(
		 * "SELECT mcp FROM  MedicalClaimProposal mcp inner join Payment p on mcp.id = p.referenceNo Where p.chalanNo = :receiptNo AND p.referenceType = :referenceType"
		 * ); buffer.append(" AND p.complete = " + complete); Query q =
		 * em.createQuery(buffer.toString()); for (String receiptNo :
		 * receiptList) { q.setParameter("receiptNo", receiptNo);
		 * q.setParameter("referenceType", PaymentReferenceType.CLAIM);
		 * List<MedicalClaimProposal> medClaimProposalList = q.getResultList();
		 * 
		 * if (medClaimProposalList != null && medClaimProposalList.size() > 0)
		 * { MedicalClaimProposalDTO medClaimProposalDTO =
		 * MedicalClaimProposalFactory.createMedicalClaimProposalDTO(
		 * medClaimProposalList.get(0)); for (MedicalClaim mc :
		 * medClaimProposalList.get(0).getMedicalClaimList()) { if
		 * (mc.getClaimRole().equals(MedicalClaimRole.OPERATION_CLAIM) &&
		 * mc.isApproved()) {
		 * medClaimProposalDTO.setOperationClaimDTO(medicalClaimProposalService.
		 * findOperationClaimById(mc.getId())); } if
		 * (mc.getClaimRole().equals(MedicalClaimRole.DEATH_CLAIM) &&
		 * mc.isApproved()) {
		 * medClaimProposalDTO.setDeathClaimDTO(medicalClaimProposalService.
		 * findDeathClaimById(mc.getId())); } if
		 * (mc.getClaimRole().equals(MedicalClaimRole.HOSPITALIZED_CLAIM) &&
		 * mc.isApproved()) { medClaimProposalDTO.setHospitalizedClaimDTO(
		 * medicalClaimProposalService.findHospitalizedClaimById(mc.getId())); }
		 * if (mc.getClaimRole().equals(MedicalClaimRole.MEDICATION_CLAIM) &&
		 * mc.isApproved()) {
		 * medClaimProposalDTO.setMedicationClaimDTO(medicalClaimProposalService
		 * .findMedicationClaimById(mc.getId())); } } double hosAmt = 0.0;
		 * double optAmt = 0.0; double medAmt = 0.0; double deaAmt = 0.0;
		 * StringBuffer sb = new StringBuffer(); if
		 * (medClaimProposalDTO.getHospitalizedClaimDTO() != null) { hosAmt =
		 * medClaimProposalDTO.getHospitalizedClaimDTO().getHospitalizedAmount()
		 * ; }
		 * 
		 * if (medClaimProposalDTO.getOperationClaimDTO() != null) { optAmt =
		 * medClaimProposalDTO.getOperationClaimDTO().getOperationFee(); }
		 * 
		 * if (medClaimProposalDTO.getMedicationClaimDTO() != null) { medAmt =
		 * medClaimProposalDTO.getMedicationClaimDTO().getMedicationFee(); }
		 * 
		 * if (medClaimProposalDTO.getDeathClaimDTO() != null) { deaAmt =
		 * medClaimProposalDTO.getDeathClaimDTO().getDeathClaimAmount(); } for
		 * (MedicalClaimBeneficiaryDTO medBene :
		 * medClaimProposalDTO.getMedicalClaimBeneficiariesList()) { if (medBene
		 * != null) { if (sb.length() > 0) { sb.append(","); } if
		 * (medBene.getBeneficiaryRole().equals(MedicalBeneficiaryRole.SUCCESSOR
		 * )) { sb.append(medBene.getName()); } else if
		 * (medBene.getBeneficiaryRole().equals(MedicalBeneficiaryRole.
		 * INSURED_PERSON)) {
		 * sb.append(medBene.getMedicalPolicyInsuredPersonDTO().getFullName());
		 * } else if
		 * (medBene.getBeneficiaryRole().equals(MedicalBeneficiaryRole.GUARDIAN)
		 * ) { sb.append(medBene.getMedPolicyGuardianDTO().getCustomerDTO().
		 * getFullName()); } else if
		 * (medBene.getBeneficiaryRole().equals(MedicalBeneficiaryRole.
		 * BENEFICIARY)) {
		 * sb.append(medBene.getMedPolicyInsuBeneDTO().getFullName()); } } }
		 * PaymentTableDTO paymentTableDTO = new PaymentTableDTO(sb, receiptNo,
		 * optAmt, medAmt, hosAmt, deaAmt, 0.0);
		 * resultList.add(paymentTableDTO); } } em.flush(); } catch
		 * (PersistenceException pe) { throw
		 * translate("Failed to find Payment by ReceiptNo : ", pe); } return
		 * resultList;
		 */
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<PaymentTrackDTO> findPaymentTrack(String policyNo) throws DAOException {
		List<PaymentTrackDTO> resultList = new ArrayList<PaymentTrackDTO>();
		List<PaymentTrackDTO> policyList = null;
		List<PaymentTrackDTO> policyHistoryList = null;
		try {

			StringBuffer buffer = new StringBuffer();
			buffer.append(
					" SELECT new org.ace.insurance.life.surrender.PaymentTrackDTO( p.id, p.policyNo, p.paymentDate, p.receiptNo, p.paymentChannel, p.basicPremium, p.fromTerm, p.toTerm )");
			buffer.append(" FROM Payment p WHERE p.referenceNo IN (SELECT l.id FROM LifePolicy l WHERE l.policyNo = :policyNo)");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("policyNo", policyNo);
			policyList = query.getResultList();
			buffer = new StringBuffer();
			buffer.append(
					" SELECT new org.ace.insurance.life.surrender.PaymentTrackDTO( p.id, p.referenceNo, p.paymentDate, p.receiptNo, p.paymentChannel, p.basicPremium, p.fromTerm, p.toTerm )");
			buffer.append(" FROM Payment p WHERE p.referenceNo IN (SELECT l.policyReferenceNo FROM LifePolicyHistory l WHERE l.policyNo = :policyNo)");
			query = em.createQuery(buffer.toString());
			query.setParameter("policyNo", policyNo);
			policyHistoryList = query.getResultList();
			if (!policyList.isEmpty()) {
				resultList.addAll(policyList);
			}
			if (!policyHistoryList.isEmpty()) {
				resultList.addAll(policyHistoryList);
			}

			Collections.sort(resultList, new Comparator<PaymentTrackDTO>() {
				@Override
				public int compare(PaymentTrackDTO paymentTrack1, PaymentTrackDTO paymentTrack2) {
					return paymentTrack1.getPaymentDate().compareTo(paymentTrack2.getPaymentDate());
				}
			});

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment Track List by PolicyNo : =" + policyNo, pe);
		}

		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Double findSummaryByReferenceNo(String policyId, PolicyReferenceType referenceType, Boolean complete) throws DAOException {
		Double result = null;
		try {
			StringBuffer sf = new StringBuffer(
					"SELECT SUM(p.basicPremium) FROM Payment p WHERE p.referenceNo=:policyId AND p.referenceType=:referenceType AND p.complete=:complete");
			Query q = em.createQuery(sf.toString());
			q.setParameter("policyId", policyId);
			q.setParameter("referenceType", referenceType);
			q.setParameter("complete", complete);
			result = (Double) q.getSingleResult();
			return result;
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AcceptedInfo", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findByInvoiceNo(String invoiceNo) {
		Payment payment = null;
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("Select p from Payment p where p.invoiceNo=:invoiceNo");
			Query query = em.createQuery(stringBuffer.toString());
			query.setParameter("invoiceNo", invoiceNo);
			payment = (Payment) query.getSingleResult();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AcceptedInfo", pe);
		}
		return payment;
	}

	/* in AgentCommission Payment, one invoiceNo -> many Payment */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Payment> findListByInvoiceNo(String invoiceNo) {
		List<Payment> result = null;
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("Select p from Payment p where p.invoiceNo=:invoiceNo");
			Query query = em.createQuery(stringBuffer.toString());
			query.setParameter("invoiceNo", invoiceNo);
			result = query.getResultList();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find AcceptedInfo", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Payment findPaymentByReferenceNoAndReferenceType(String referenceNo, PolicyReferenceType policyReferenceType) throws DAOException {
		Payment result = null;
		try {
			Query q = em.createQuery("SELECT P FROM Payment p where p.referenceNo =:referenceNo and p.referenceType :referenceType");
			q.setParameter("referenceNo", referenceNo);
			q.setParameter("referenceType", policyReferenceType);
			result = (Payment) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find Payment by ReferenceNo : " + referenceNo, pe);
		}
		return result;
	}

}
