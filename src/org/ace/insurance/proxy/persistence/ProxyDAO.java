package org.ace.insurance.proxy.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.BC001;
import org.ace.insurance.common.CoinsuranceType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.RegNoSorter;
import org.ace.insurance.proxy.AGT001;
import org.ace.insurance.proxy.CGO001;
import org.ace.insurance.proxy.CIN001;
import org.ace.insurance.proxy.COCL001;
import org.ace.insurance.proxy.LCB001;
import org.ace.insurance.proxy.LCLD001;
import org.ace.insurance.proxy.LCP001;
import org.ace.insurance.proxy.LIF001;
import org.ace.insurance.proxy.LPP001;
import org.ace.insurance.proxy.LSP001;
import org.ace.insurance.proxy.MED001;
import org.ace.insurance.proxy.MEDCLM002;
import org.ace.insurance.proxy.MEDFEES001;
import org.ace.insurance.proxy.OMCGO001;
import org.ace.insurance.proxy.SPMA001;
import org.ace.insurance.proxy.TRA001;
import org.ace.insurance.proxy.WorkflowCriteria;
import org.ace.insurance.proxy.persistence.interfaces.IProxyDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Repository("ProxyDAO")
public class ProxyDAO extends BasicDAO implements IProxyDAO {
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CGO001> find_CGO001(WorkflowCriteria wfc) throws DAOException {
		List<CGO001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + CGO001.class.getName());
			buffer.append(
					"(f.id, f.proposalNo, c.initialId, c.name, o.name, f.submittedDate, w.recorder.createdDate, f.currency.currencyCode, SUM(fp.proposedSumInsured), SUM(fp.approvedSumInsured), fp.approved)");
			buffer.append(" FROM CargoProposal  f");
			buffer.append(" LEFT OUTER JOIN f.customer c ");
			buffer.append(" LEFT OUTER JOIN f.organization o ");
			buffer.append(" JOIN f.shipVehicleInfoProposalList fb JOIN fb.cargoProposalProductInfoList fp ");
			buffer.append(", WorkFlow w ");
			buffer.append(" WHERE f.id = w.referenceNo AND  ");
			buffer.append(" w.responsiblePerson.id = :responsiblePersonId AND ");
			buffer.append(" w.workflowTask = :workflowTask AND w.referenceType = :referenceType AND w.transactionType = :transactionType ");
			buffer.append(" GROUP BY f.id, f.proposalNo, c.initialId, c.name, o.name,f.submittedDate, w.recorder.createdDate ,f.currency.currencyCode,fp.approved ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find CGO001 by WorkflowCriteria", pe);
		}

		RegNoSorter<CGO001> regNoSorter = new RegNoSorter<CGO001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<OMCGO001> find_OMCGO001(WorkflowCriteria wfc) throws DAOException {
		List<OMCGO001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW org.ace.insurance.proxy.OMCGO001");
			buffer.append("(f.id, f.proposalNo, c.initialId, c.name, o.name, f.submittedDate, w.recorder.createdDate, f.currency.currencyCode, SUM(fb.sumInsured))");
			buffer.append(" FROM OverseasProposal  f");
			buffer.append(" LEFT OUTER JOIN f.customer c ");
			buffer.append(" LEFT OUTER JOIN f.organization o ");
			buffer.append(" JOIN f.overseasVoyageInfoList fv JOIN fv.overseasProductInfoList fb");
			buffer.append(", WorkFlow w ");
			buffer.append(" WHERE f.id = w.referenceNo AND  ");
			buffer.append(" w.responsiblePerson.id = :responsiblePersonId AND ");
			buffer.append(" w.workflowTask = :workflowTask AND w.referenceType = :referenceType AND w.transactionType = :transactionType ");
			buffer.append(" GROUP BY f.id, f.proposalNo, c.initialId, c.name, o.name,f.submittedDate, w.recorder.createdDate ,f.currency.currencyCode ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find CMCGO001 by WorkflowCriteria", pe);
		}

		RegNoSorter<OMCGO001> regNoSorter = new RegNoSorter<OMCGO001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LIF001> find_LIF001(WorkflowCriteria wfc) throws DAOException {
		List<LIF001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT l.id, l.proposalNo, c.initialId, c.name, o.name, l.submittedDate, w.recorder.createdDate, SUM(ip.proposedSumInsured), SUM(ip.unit), sp.name");
			buffer.append(" FROM LifeProposal l ");
			buffer.append(" LEFT OUTER JOIN l.salesPoints sp ");
			buffer.append(" LEFT OUTER JOIN l.customer c ");
			buffer.append(" LEFT OUTER JOIN l.organization o ");
			buffer.append(" JOIN l.proposalInsuredPersonList ip ");
			buffer.append(", WorkFlow w ");
			buffer.append(" WHERE l.id = w.referenceNo AND ");
			buffer.append(" w.workflowTask = :workflowTask AND w.referenceType = :referenceType AND");
			if (wfc.getTransactionType() != null) {
				buffer.append(" w.transactionType = :transactionType AND");
			}
			if (wfc.getResponsiblePerson() != null) {
				buffer.append(" w.responsiblePerson.id = :responsiblePersonId AND ");
				buffer.append(" w.branchId = :branchId ");
			}
			if (wfc.getBranchId() != null) {
				buffer.append(" w.branchId = :branchId ");
			}
			buffer.append(" GROUP BY l.id, l.proposalNo, c.initialId, c.name, o.name, l.submittedDate, w.recorder.createdDate, sp.name ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			if (wfc.getTransactionType() != null) {
				query.setParameter("transactionType", wfc.getTransactionType());
			}
			if (wfc.getResponsiblePerson() != null) {
				query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
				query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			}
			if (wfc.getBranchId() != null) {
				query.setParameter("branchId", wfc.getBranchId());
			}

			List<Object> objList = query.getResultList();

			result = new ArrayList<LIF001>();
			String id = null;
			String proposalNo = null;
			String initialId = "";
			String salePointName = "";
			Name customerName = null;
			String orgName = null;
			Date submittedDate = null;
			Date createdDate = null;
			double sumInsured = 0.0;
			int unit = 0;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				proposalNo = (String) objArray[1];
				initialId = (String) objArray[2];
				customerName = (Name) objArray[3];
				orgName = (String) objArray[4];
				submittedDate = (Date) objArray[5];
				createdDate = (Date) objArray[6];
				sumInsured = (double) objArray[7];
				unit = ((Long) objArray[8]).intValue();
				salePointName = (String) objArray[9];
				result.add(new LIF001(id, proposalNo, salePointName, initialId, customerName, orgName, submittedDate, createdDate, sumInsured, unit));
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LIF001 by WorkflowCriteria", pe);
		}

		RegNoSorter<LIF001> regNoSorter = new RegNoSorter<LIF001>(result);
		return regNoSorter.getSortedList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MED001> find_MED001(WorkflowCriteria wfc) throws DAOException {
		List<MED001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + MED001.class.getName() + "(m.id, m.proposalNo, c.initialId, c.name, o.name,");
			buffer.append(" m.submittedDate,w.recorder.createdDate,SUM(COALESCE(mp.sumInsured,0)),SUM(COALESCE(mp.unit,0)),sp.name)");
			buffer.append(" FROM MedicalProposal m");
			buffer.append(" LEFT OUTER JOIN m.salesPoints sp");
			buffer.append(" LEFT OUTER JOIN m.customer c");
			buffer.append(" LEFT OUTER JOIN m.organization o");
			buffer.append(" JOIN m.medicalProposalInsuredPersonList mp");
			buffer.append(" ,WorkFlow w WHERE m.id = w.referenceNo AND ");
			if (wfc.getTransactionType() != null) {
				buffer.append(" w.transactionType = :transactionType AND ");
			}
			if (wfc.getResponsiblePerson() != null) {
				buffer.append(" w.responsiblePerson.id = :responsiblePersonId AND w.branchId = :branchId AND");
			}
			if (wfc.getBranchId() != null) {
				buffer.append(" w.branchId = :branchId AND");
			}
			buffer.append(" w.workflowTask = :workflowTask AND w.referenceType = :referenceType");
			buffer.append(" GROUP BY m.id,m.proposalNo,c.initialId, c.name, o.name,m.submittedDate,w.recorder.createdDate,sp.name");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			if (wfc.getTransactionType() != null) {
				query.setParameter("transactionType", wfc.getTransactionType());
			}
			if (wfc.getResponsiblePerson() != null) {
				query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
				query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			}
			if (wfc.getBranchId() != null) {
				query.setParameter("branchId", wfc.getBranchId());
			}

			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LIF001 by WorkflowCriteria", pe);
		}
		// RegNoSorter<LIF001> regNoSorter = new RegNoSorter<LIF001>(result);
		return result;
	}

	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<MED001> find_MED002(WorkflowCriteria wfc) throws DAOException {
		List<MED001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + MED001.class.getName() + "(m.id, m.proposalNo, c.initialId, c.name, o.name,");
			buffer.append(" m.submittedDate,w.recorder.createdDate,SUM(COALESCE(mp.sumInsured,0)),SUM(COALESCE(mp.unit,0)),sp.name)");
			buffer.append(" FROM MedicalProposal m");
			buffer.append(" LEFT OUTER JOIN m.salesPoints sp");
			buffer.append(" LEFT OUTER JOIN m.customer c");
			buffer.append(" LEFT OUTER JOIN m.organization o");
			buffer.append(" JOIN m.medicalProposalInsuredPersonList mp");
			buffer.append(" ,WorkFlow w WHERE m.id = w.referenceNo AND ");
			if (wfc.getTransactionType() != null) {
				buffer.append(" w.transactionType = :transactionType AND ");
			}
			if (wfc.getResponsiblePerson() != null) {
				buffer.append(" w.responsiblePerson.id = :responsiblePersonId AND w.branchId = :branchId AND");
			}
			if (wfc.getBranchId() != null) {
				buffer.append(" w.branchId = :branchId AND");
			}
			buffer.append(" w.workflowTask = :workflowTask AND w.referenceType = :referenceType");
			buffer.append(" GROUP BY m.id,m.proposalNo,c.initialId, c.name, o.name,m.submittedDate,w.recorder.createdDate,sp.name");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			if (wfc.getTransactionType() != null) {
				query.setParameter("transactionType", wfc.getTransactionType());
			}
			if (wfc.getResponsiblePerson() != null) {
				query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
				query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			}
			if (wfc.getBranchId() != null) {
				query.setParameter("branchId", wfc.getBranchId());
			}

			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LIF001 by WorkflowCriteria", pe);
		}
		// RegNoSorter<LIF001> regNoSorter = new RegNoSorter<LIF001>(result);
		return result;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCLD001> find_LCLD001(WorkflowCriteria wfc) throws DAOException {
		List<LCLD001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT c.id, c.claimRequestId, c.submittedDate, w.recorder.createdDate, cus.initialId, cus.name, org.name, SUM(person.sumInsured)");
			if (wfc.getReferenceType().equals(ReferenceType.LIFE_DEALTH_CLAIM))
				buffer.append(" FROM LifeClaim c");
			else
				buffer.append(" FROM LifeDisabilityClaim c");
			buffer.append(" JOIN c.lifePolicy p JOIN p.policyInsuredPersonList person");
			buffer.append(" LEFT OUTER JOIN p.customer cus LEFT OUTER JOIN p.organization org, WorkFlow w");
			buffer.append(" WHERE c.claimRequestId = w.referenceNo AND w.workflowTask = :workflowTask AND w.referenceType = :referenceType");
			buffer.append(" AND w.transactionType = :transactionType AND w.responsiblePerson.id = :responsiblePersonId");
			buffer.append(" GROUP BY c.id, c.claimRequestId, c.submittedDate, w.recorder.createdDate, cus.initialId, cus.name, org.name");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = query.getResultList();
			em.flush();
			result = new ArrayList<LCLD001>();
			String id = null;
			String claimRequestId = null;
			Date submittedDate = null;
			Date pendingSince = null;
			String initialId = null;
			Name customerName = null;
			String organizationName = null;
			String holderName = null;
			Double sumInsured = null;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				claimRequestId = (String) objArray[1];
				submittedDate = (Date) objArray[2];
				pendingSince = (Date) objArray[3];
				initialId = (String) objArray[4];
				customerName = (Name) objArray[5];
				organizationName = (String) objArray[6];
				holderName = customerName == null ? organizationName : initialId + " " + customerName.getFullName();
				sumInsured = (Double) objArray[7];
				result.add(new LCLD001(id, claimRequestId, holderName, submittedDate, pendingSince, sumInsured));
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find DamagedOther for LCLD001 by WorkflowCriteria", pe);
		}
		RegNoSorter<LCLD001> regNoSorter = new RegNoSorter<LCLD001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCB001> find_LCB001(WorkflowCriteria wfc) throws DAOException {
		List<LCB001> result = null;
		try {
			String beneficiary = null;
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT lc.id, lc.refundNo , lc.claimAmount, w.recorder.createdDate,");
			if (wfc.getReferenceType().equals(ReferenceType.LIFE_DEALTH_CLAIM)) {
				// beneficiary =
				// "CONCAT(lc.policyInsuredPersonBeneficiaries.initialId ,
				// lc.policyInsuredPersonBeneficiaries.name.firstName
				// ,lc.policyInsuredPersonBeneficiaries.name.middleName ,
				// lc.policyInsuredPersonBeneficiaries.name.lastName),
				// lc.policyInsuredPersonBeneficiaries.policyInsuredPerson.sumInsured
				// From LifeClaimInsuredPersonBeneficiary lc";
				beneficiary = "lc.beneficiaryRole, lc.claimAmount  From LifeClaimBeneficiary lc";
			} else {
				beneficiary = "CONCAT(lc.policyInsuredPerson.initialId , lc.policyInsuredPerson.name.firstName ,lc.policyInsuredPerson.name.middleName , lc.policyInsuredPerson.name.lastName) ,lc.policyInsuredPerson.sumInsured From LifeClaimInsuredPerson lc";
			}
			buffer.append(beneficiary + ", WorkFlow w WHERE lc.refundNo = w.referenceNo AND  w.workflowTask = :workflowTask AND w.referenceType = :referenceType"
					+ " AND w.responsiblePerson.id = :responsiblePersonId");

			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = query.getResultList();
			em.flush();
			result = new ArrayList<LCB001>();
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				String id = (String) objArray[0];
				String refundNo = (String) objArray[1];
				double claimAmount = (Double) objArray[2];
				Date pendingSince = (Date) objArray[3];
				String beneficiaryName = (String) objArray[4].toString();
				double sumInsured = (Double) objArray[5];

				result.add(new LCB001(id, refundNo, beneficiaryName, pendingSince, claimAmount, sumInsured));
			}
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find DamagedOther for LCB001 by WorkflowCriteria", pe);
		}
		RegNoSorter<LCB001> regNoSorter = new RegNoSorter<LCB001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<COCL001> find_COCL001(WorkflowCriteria wfc, CoinsuranceType type) {
		List<COCL001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + COCL001.class.getName());
			buffer.append("( cc.invoiceNo, c.name, CAST(cc.invoiceDate AS date), SUM(cc.claimAmount) ) ");
			buffer.append("FROM CoinsuranceClaim cc ");
			buffer.append("LEFT OUTER JOIN cc.coinsuranceCompany c ");
			buffer.append(",WorkFlow w");
			buffer.append(" WHERE cc.invoiceNo = w.referenceNo AND cc.coinsuranceType = :coinsuranceType AND w.workflowTask = :workflowTask ");
			buffer.append(" AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId ");
			buffer.append(" GROUP BY cc.invoiceNo, c.name, CAST(cc.invoiceDate AS date) ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("coinsuranceType", type);
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			result = query.getResultList();
			em.flush();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find CIN001 for DashBoar By WorkflowCriteria", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CIN001> find_CIN001(WorkflowCriteria wfc, CoinsuranceType coinsuranceType) throws DAOException {
		List<CIN001> result = new ArrayList<CIN001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT c.invoiceNo, co.name,cast(CAST(c.invoiceDate AS date) as datetime), SUM(c.sumInsuranced),");
			buffer.append(" SUM(c.premium), SUM(c.receivedSumInsured) ");
			buffer.append(" FROM Coinsurance c LEFT JOIN c.coinsuranceCompany co, WorkFlow w ");
			buffer.append(" WHERE c.invoiceNo = w.referenceNo AND c.coinsuranceType = :coinsuranceType AND w.workflowTask = :workflowTask ");
			buffer.append(" AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId group by c.invoiceNo,co.name,CAST(c.invoiceDate AS date)");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("coinsuranceType", coinsuranceType);
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = query.getResultList();
			String invoiceNo;
			String companyName;
			Date invoiceDate;
			double sumInsuranced;
			double premium;
			double receivedSumInsured;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				invoiceNo = (String) objArray[0];
				companyName = (String) objArray[1];
				invoiceDate = (Date) objArray[2];
				sumInsuranced = (Double) objArray[3];
				premium = (Double) objArray[4];
				// netPremium = premium - Utils.getPercentOf(commissionPercent,
				// premium);
				receivedSumInsured = (Double) objArray[5];
				result.add(new CIN001(invoiceNo, companyName, invoiceDate, sumInsuranced, premium, receivedSumInsured));
			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find CIN001 by WorkflowCriteria", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AGT001> find_AGT001(WorkflowCriteria wfc) throws DAOException {
		List<AGT001> result = new ArrayList<AGT001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT NEW " + AGT001.class.getName() + "(a.id, a.initialId, a.name, a.liscenseNo, ac.invoiceNo, SUM(ac.premium), SUM(ac.commission),");
			buffer.append(" ac.invoiceDate, p.bankAccountNo, p.paymentChannel, b.name) ");
			buffer.append(" FROM AgentCommission ac JOIN Payment p ON p.referenceNo = ac.id LEFT OUTER JOIN p.bank b");
			buffer.append(" JOIN ac.agent a JOIN WorkFlow w ON ac.invoiceNo = w.referenceNo ");
			buffer.append(" WHERE p.complete = 0 AND ac.invoiceDate IS NOT NULL AND w.referenceType = :referenceType");
			buffer.append(" AND w.responsiblePerson.id = :responsiblePersonId AND w.workflowTask = :workflowTask");
			buffer.append(" AND w.transactionType = :transactionType AND w.branchId = :branchId");
			buffer.append(" GROUP BY a.id, a.initialId, a.name, a.liscenseNo, ac.invoiceNo, ac.invoiceDate, p.bankAccountNo, p.paymentChannel, b.name ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find AgentCommissionDTO by WorkflowCriteria", pe);
		}

		RegNoSorter<AGT001> regNoSorter = new RegNoSorter<AGT001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TRA001> findTravelProposalForDashboard(WorkflowCriteria wfc) throws DAOException {
		List<Object> objList = new ArrayList<Object>();
		List<TRA001> result = new ArrayList<TRA001>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT t.id, t.proposalNo, t.submittedDate, w.recorder.createdDate, SUM(e.noOfUnit) ");
			buffer.append(" FROM TravelProposal t JOIN t.expressList e,  WorkFlow w ");
			buffer.append(" WHERE t.id = w.referenceNo AND w.branchId = :branchId AND w.workflowTask = :workflowTask ");
			buffer.append(" AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId");
			buffer.append(" GROUP BY t.id, t.proposalNo, t.submittedDate, w.recorder.createdDate ");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("workflowTask", wfc.getWorkflowTask());
			q.setParameter("referenceType", wfc.getReferenceType());
			q.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			q.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			objList = q.getResultList();
			em.flush();
			String id;
			String proposalNo;
			Date submittedDate;
			Date createdDate;
			double netPremium = 0.0;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				proposalNo = (String) objArray[1];
				submittedDate = (Date) objArray[2];
				createdDate = (Date) objArray[3];
				netPremium = (Double) objArray[4];
				TRA001 dto = new TRA001(id, proposalNo, submittedDate, createdDate, netPremium);
				result.add(dto);
			}

		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find TravelProposals by WorkflowCriteria", pe);
		}
		RegNoSorter<TRA001> regNoSorter = new RegNoSorter<TRA001>(result);
		return regNoSorter.getSortedList();
	}

	public static void main(String[] arg) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		em.getTransaction().commit();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MEDCLM002> find_MEDCLM002(WorkflowCriteria wfc) throws DAOException {
		List<MEDCLM002> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(
					" SELECT mc.id, mc.claimRequestId, c.initialId, c.name,  mc.submittedDate, w.recorder.createdDate, SUM(ip.unit) as UNIT , SUM (ip.basicPlusUnit) as BASICPLUSUNIT ");
			buffer.append(" FROM MedicalClaimProposal mc LEFT JOIN mc.policyInsuredPerson ip ");
			buffer.append(" LEFT OUTER JOIN ip.customer c , WorkFlow w ");
			buffer.append(" WHERE mc.id = w.referenceNo AND w.workflowTask = :workflowTask ");
			buffer.append(" AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId");
			buffer.append(" GROUP BY mc.id, mc.claimRequestId,c.initialId, c.name,  mc.submittedDate, w.recorder.createdDate ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = query.getResultList();
			result = new ArrayList<MEDCLM002>();
			String id;
			String claimRequestId;
			String customerName;
			String initialId = "";
			Name name;
			Date submittedDate;
			Date createdDate;
			int unit = 0;
			int basicPlusUnit = 0;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				claimRequestId = (String) objArray[1];
				customerName = null;
				initialId = (String) objArray[2];
				if (objArray[2] == null) {
					initialId = "";
				}

				name = (Name) objArray[3];
				submittedDate = (Date) objArray[4];
				createdDate = (Date) objArray[5];
				unit = ((Long) objArray[6]).intValue();
				basicPlusUnit = objArray[7] == null ? 0 : ((Long) objArray[7]).intValue();
				customerName = initialId + name.getFullName();

				result.add(new MEDCLM002(id, claimRequestId, customerName, submittedDate, createdDate, unit + basicPlusUnit));
			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find MEDCLM002 by WorkflowCriteria", pe);
		}

		RegNoSorter<MEDCLM002> regNoSorter = new RegNoSorter<MEDCLM002>(result);
		return regNoSorter.getSortedList();

	}

	/* For Life Surrender */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LSP001> find_LSP001(WorkflowCriteria wfc) throws DAOException {
		List<LSP001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT ls.id, ls.proposalNo, ls.policyNo, c.initialId, c.name , ls.submittedDate, w.recorder.createdDate, ls.surrenderAmount");
			buffer.append(" FROM LifeSurrenderProposal ls LEFT OUTER JOIN ls.lifePolicy l LEFT OUTER JOIN l.customer c,WorkFlow w ");
			buffer.append(" WHERE w.workflowTask=:workflowTask AND w.referenceType=:referenceType ");
			buffer.append(" AND w.responsiblePerson.id = :responsiblePersonId AND ls.id = w.referenceNo ");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("workflowTask", wfc.getWorkflowTask());
			q.setParameter("referenceType", wfc.getReferenceType());
			q.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = q.getResultList();
			result = new ArrayList<LSP001>();
			String id;
			String proposalNo;
			String policyNo;
			String customerName;
			String initialId = "";
			Name name;
			Date submittedDate;
			Date pendingSince;
			Double surrenderAmount;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				proposalNo = (String) objArray[1];
				policyNo = (String) objArray[2];
				customerName = null;
				initialId = (String) objArray[3];
				name = (Name) objArray[4];
				submittedDate = (Date) objArray[5];
				pendingSince = (Date) objArray[6];
				surrenderAmount = (Double) objArray[7];
				if (name != null) {
					customerName = initialId + name.getFullName();
				}
				result.add(new LSP001(id, proposalNo, policyNo, customerName, submittedDate, pendingSince, surrenderAmount));

			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LSP001 by WorkflowCriteria", pe);
		}
		RegNoSorter<LSP001> regNoSorter = new RegNoSorter<LSP001>(result);
		return regNoSorter.getSortedList();

	}

	/* Life PaidUp */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LPP001> find_LPP001(WorkflowCriteria wfc) throws DAOException {
		List<LPP001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT lp.id, lp.proposalNo, lp.policyNo, c.initialId, c.name, lp.submittedDate, w.recorder.createdDate, lp.realPaidUpAmount");
			buffer.append(" FROM LifePaidUpProposal lp LEFT OUTER JOIN lp.lifePolicy l LEFT OUTER JOIN l.customer c, WorkFlow w ");
			buffer.append(" WHERE w.workflowTask=:workflowTask AND w.referenceType=:referenceType ");
			buffer.append(" AND w.responsiblePerson.id = :responsiblePersonId AND lp.id = w.referenceNo ");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("workflowTask", wfc.getWorkflowTask());
			q.setParameter("referenceType", wfc.getReferenceType());
			q.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			List<Object> objList = q.getResultList();
			result = new ArrayList<LPP001>();
			String id;
			String proposalNo;
			String policyNo;
			String customerName;
			String initialId = "";
			Name name;
			Date submittedDate;
			Date pendingSince;
			Double paidUpAmount;
			for (Object obj : objList) {
				Object[] objArray = (Object[]) obj;
				id = (String) objArray[0];
				proposalNo = (String) objArray[1];
				policyNo = (String) objArray[2];
				customerName = null;
				initialId = (String) objArray[3];
				name = (Name) objArray[4];
				submittedDate = (Date) objArray[5];
				pendingSince = (Date) objArray[6];
				paidUpAmount = (Double) objArray[7];
				if (name != null) {
					customerName = initialId + name.getFullName();
				}
				result.add(new LPP001(id, proposalNo, policyNo, customerName, submittedDate, pendingSince, paidUpAmount));

			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LPP001 by WorkflowCriteria", pe);
		}
		RegNoSorter<LPP001> regNoSorter = new RegNoSorter<LPP001>(result);
		return regNoSorter.getSortedList();

	}

	/* Person Travel */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TRA001> find_TRA001(WorkflowCriteria wfc) throws DAOException {
		List<TRA001> result = new ArrayList<>();
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT p.id,p.proposalNo,c.initialId,c.name,o.name,p.submittedDate,i.totalUnit,i.premium,w.recorder.createdDate");
			buffer.append(" FROM PersonTravelProposal p ");
			buffer.append(" LEFT OUTER JOIN p.customer c ");
			buffer.append(" LEFT OUTER JOIN p.organization o ");
			buffer.append(" JOIN p.personTravelInfo i ");
			buffer.append(",WorkFlow w ");
			buffer.append(" WHERE w.workflowTask = :workflowTask AND w.referenceType = :referenceType ");
			buffer.append(" AND w.transactionType = :transactionType");
			buffer.append(" AND w.responsiblePerson.id = :responsiblePersonId AND p.id = w.referenceNo AND w.branchId = :branchId");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("workflowTask", wfc.getWorkflowTask());
			q.setParameter("referenceType", wfc.getReferenceType());
			q.setParameter("transactionType", wfc.getTransactionType());
			q.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			q.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			List<Object[]> objList = q.getResultList();
			for (Object obj[] : objList) {
				result.add(new TRA001((String) obj[0], (String) obj[1], (String) obj[2], (Name) obj[3], (String) obj[4], (Date) obj[5], (double) obj[6], (double) obj[7],
						(Date) obj[8]));
			}
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find LPP001 by WorkflowCriteria", pe);
		}
		// RegNoSorter<TRA001> regNoSorter = new RegNoSorter<TRA001>(result);
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BC001> find_BC001(WorkflowCriteria wfc) throws DAOException {
		List<BC001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + BC001.class.getName());
			buffer.append("(p.invoiceNo,p.confirmDate,COALESCE(policy.policyNo,policyH.policyNo),COALESCE(c.id,cH.id),COALESCE(c.initialId,cH.initialId)");
			buffer.append(",c.name,cH.name,COALESCE(o.name,oH.name),p.basicPremium");
			buffer.append("+ p.addOnPremium + p.penaltyPremium - p.specialDiscount - p.afpDiscount - p.fleetDiscount - p.ncbPremium ");
			buffer.append("+ SUM(p.reinstatementPremium) + SUM(p.servicesCharges) + SUM(p.administrationFees) + SUM(p.stampFees)");

			buffer.append(",COALESCE(SUM(product.sumInsured),SUM(productH.sumInsured)))");

			buffer.append(" FROM Payment p");

			buffer.append(" LEFT JOIN LifePolicy policy ON p.referenceNo = policy.id LEFT JOIN policy.policyInsuredPersonList product");
			buffer.append(" LEFT JOIN policy.customer c");
			buffer.append(" LEFT JOIN policy.organization o");

			buffer.append(" LEFT JOIN LifePolicyHistory policyH ON p.referenceNo = policyH.policyReferenceNo LEFT JOIN policyH.policyInsuredPersonList productH");
			buffer.append(" LEFT JOIN policyH.customer cH");
			buffer.append(" LEFT JOIN policyH.organization oH");

			buffer.append(" LEFT JOIN WorkFlow w ON w.referenceNo = p.invoiceNo");
			buffer.append(" WHERE w.responsiblePerson.id = :responsiblePersonId AND w.workflowTask = :workflowTask");
			buffer.append(" AND w.referenceType = :referenceType AND w.transactionType = :transactionType AND w.branchId = :branchId");
			buffer.append(" GROUP BY p.invoiceNo,p.confirmDate");
			buffer.append(",policy.policyNo,c.id,c.initialId,c.name,o.name");
			buffer.append(",policyH.policyNo,cH.id,cH.initialId,cH.name,oH.name,p.basicPremium");
			buffer.append(",p.addOnPremium,p.penaltyPremium,p.specialDiscount,p.afpDiscount,p.fleetDiscount,p.ncbPremium ");

			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());

			result = query.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find BC001 by WorkflowCriteria", pe);
		}
		RegNoSorter<BC001> regNoSorter = new RegNoSorter<BC001>(result);
		return regNoSorter.getSortedList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<BC001> find_Health_BC001(WorkflowCriteria wfc) throws DAOException {
		List<BC001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + BC001.class.getName());
			buffer.append("(p.invoiceNo,p.confirmDate,policy.policyNo,c.id,c.initialId,c.name,o.name");
			buffer.append(",p.basicPremium+ p.addOnPremium + p.penaltyPremium - p.specialDiscount - p.afpDiscount - p.fleetDiscount - p.ncbPremium ");
			buffer.append("+ SUM(p.reinstatementPremium) + SUM(p.servicesCharges) + SUM(p.administrationFees) + SUM(p.stampFees),SUM(product.sumInsured))");
			buffer.append(" FROM Payment p");
			buffer.append(" LEFT JOIN MedicalPolicy policy ON p.referenceNo = policy.id LEFT JOIN policy.policyInsuredPersonList product");
			buffer.append(" LEFT JOIN policy.customer c");
			buffer.append(" LEFT JOIN policy.organization o");
			buffer.append(" LEFT JOIN WorkFlow w ON w.referenceNo = p.invoiceNo");
			buffer.append(" WHERE w.responsiblePerson.id = :responsiblePersonId AND w.workflowTask = :workflowTask");
			buffer.append(" AND w.referenceType = :referenceType AND w.transactionType = :transactionType AND w.branchId = :branchId");
			buffer.append(" GROUP BY p.invoiceNo,p.confirmDate");
			buffer.append(",policy.policyNo,c.id,c.initialId,c.name,o.name");
			buffer.append(",p.basicPremium,p.addOnPremium,p.penaltyPremium,p.specialDiscount,p.afpDiscount,p.fleetDiscount,p.ncbPremium ");

			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());

			result = query.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find BC001 by WorkflowCriteria", pe);
		}
		RegNoSorter<BC001> regNoSorter = new RegNoSorter<BC001>(result);
		return regNoSorter.getSortedList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<SPMA001> findSoprtManAbroad_SPMA001(WorkflowCriteria wfc) throws DAOException {
		List<SPMA001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + SPMA001.class.getName());
			buffer.append("(p.invoiceNo,policy.policyNo,c.id,c.initialId,c.name,o.name,p.confirmDate,p.basicPremium+ p.addOnPremium)");
			buffer.append(" FROM Payment p");
			buffer.append(" LEFT JOIN LifePolicy policy ON p.referenceNo = policy.id");
			buffer.append(" LEFT JOIN policy.customer c");
			buffer.append(" LEFT JOIN policy.organization o");
			buffer.append(" LEFT JOIN WorkFlow w ON w.referenceNo = p.invoiceNo");
			buffer.append(" WHERE w.responsiblePerson.id = :responsiblePersonId AND w.workflowTask = :workflowTask");
			buffer.append(" AND w.referenceType = :referenceType AND w.transactionType = :transactionType AND w.branchId = :branchId");
			// buffer.append(" GROUP BY p.invoiceNo,p.confirmDate");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			result = query.getResultList();
			em.flush();
		} catch (NoResultException e) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find SPMA001 by WorkflowCriteria", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<LCP001> find_LCP001(WorkflowCriteria wfc) throws DAOException {
		List<LCP001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + LCP001.class.getName());
			buffer.append("( lcp.id, lcp.claimProposalNo, cp.initialId, cp.name, lcp.submittedDate, w.recorder.createdDate, cp.sumInsured )");
			buffer.append("FROM LifeClaimProposal lcp ");
			buffer.append("LEFT JOIN lcp.claimPerson cp ");
			buffer.append(",WorkFlow w ");
			buffer.append("WHERE lcp.id = w.referenceNo AND w.workflowTask = :workflowTask ");
			buffer.append("AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId ");
			buffer.append("GROUP BY lcp.id, lcp.claimProposalNo, cp.initialId, cp.name, lcp.submittedDate, w.recorder.createdDate, cp.sumInsured ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LCP001 by WorkflowCriteria", pe);
		}
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MEDFEES001> find_MEDFEES001(WorkflowCriteria wfc) throws DAOException {
		List<MEDFEES001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" SELECT NEW " + MEDFEES001.class.getName()
					+ "(h.id,h.name,ac.invoiceNo,ac.invoiceDate,p.bankAccountNo,p.paymentChannel,b.name,sum(ac.amount),ac.sanctionNo,ac.claimNo)");
			buffer.append(" FROM ClaimMedicalFees ac  JOIN  Payment p ON p.referenceNo = ac.id LEFT OUTER JOIN p.accountBank b");
			buffer.append(" JOIN  ac.hospital h JOIN  WorkFlow w ON ac.invoiceNo = w.referenceNo ");
			buffer.append(" WHERE p.complete = 0 AND ac.invoiceDate IS NOT NULL AND w.referenceType = :referenceType");
			buffer.append(" AND w.responsiblePerson.id = :responsiblePersonId AND w.workflowTask = :workflowTask");
			buffer.append(" AND w.transactionType = :transactionType AND w.branchId = :branchId");
			buffer.append(" GROUP BY h.id,h.name,ac.invoiceNo,ac.invoiceDate,p.bankAccountNo,p.paymentChannel,b.name,ac.sanctionNo,ac.claimNo");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", wfc.getWorkflowTask());
			query.setParameter("referenceType", wfc.getReferenceType());
			query.setParameter("transactionType", wfc.getTransactionType());
			query.setParameter("responsiblePersonId", wfc.getResponsiblePerson().getId());
			query.setParameter("branchId", wfc.getResponsiblePerson().getLoginBranch().getId());
			result = query.getResultList();
		} catch (PersistenceException pe) {
			throw translate("Failed to find MEDFEES001 by WorkflowCriteria", pe);
		}

		RegNoSorter<MEDFEES001> regNoSorter = new RegNoSorter<MEDFEES001>(result);
		return regNoSorter.getSortedList();
	}
}
