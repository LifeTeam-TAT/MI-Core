package org.ace.insurance.workflow.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.CoinsuranceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.proxy.WF001;
import org.ace.insurance.proxy.WF002;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.UserChangerCriteria;
import org.ace.insurance.workflow.TaskMessage;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.insurance.workflow.persistence.interfaces.IWorkFlowDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("WorkFlowDAO")
public class WorkFlowDAO extends BasicDAO implements IWorkFlowDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(List<WorkFlowHistory> wrokflowList) throws DAOException {
		try {
			for (WorkFlowHistory workFlowHistory : wrokflowList) {
				em.persist(workFlowHistory);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertWorkFlowList(List<WorkFlow> wrokflowList) throws DAOException {
		try {
			for (WorkFlow workFlow : wrokflowList) {
				em.persist(workFlow);
			}
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlow> findByUser(User user) throws DAOException {
		List<WorkFlow> result = null;
		try {
			Query q = em.createNamedQuery("WorkFlow.findByUser");
			q.setParameter("usercode", user.getUsercode());
			result = q.getResultList();
			/* Delete Request Messages */
			Query delQuery = em.createNamedQuery("TaskMessage.deleteByUser");
			delQuery.setParameter("usercode", user.getUsercode());
			delQuery.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find workflow by user.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findRequestCountByUser(User user) throws DAOException {
		long result = 0;
		try {
			Query q = em.createNamedQuery("TaskMessage.findRequestCount");
			q.setParameter("usercode", user.getUsercode());
			result = (Long) q.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Request Count by user.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlowHistory> findWorkFlowHistoryByRefNo(String refNo, WorkflowTask... workflowTasks) throws DAOException {
		List<WorkFlowHistory> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT h FROM WorkFlowHistory h WHERE h.referenceNo = :referenceNo");
			if (workflowTasks != null && workflowTasks.length > 0) {
				buffer.append(" AND h.workflowTask IN :workflowTaskList");
			}
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", refNo);
			if (workflowTasks != null && workflowTasks.length > 0) {
				q.setParameter("workflowTaskList", Arrays.asList(workflowTasks));
			}
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find WorkFlowHistory by RefNo.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkFlow findByRefNo(String refNo, WorkflowTask... workflowTasks) throws DAOException {
		WorkFlow result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT h FROM WorkFlow h WHERE h.referenceNo = :referenceNo");
			if (workflowTasks != null && workflowTasks.length > 0) {
				buffer.append(" AND h.workflowTask IN :workflowTaskList");
			}
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", refNo);
			if (workflowTasks != null && workflowTasks.length > 0) {
				q.setParameter("workflowTaskList", Arrays.asList(workflowTasks));
			}
			result = (WorkFlow) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find workflow by user.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkFlow findByCashInTransitRefNo(String refNo, WorkflowTask... workflowTasks) throws DAOException {
		WorkFlow result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT h FROM WorkFlow h WHERE h.referenceNo = :referenceNo");
			if (workflowTasks != null && workflowTasks.length > 0) {
				buffer.append(" AND h.workflowTask IN :workflowTaskList");
			}
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", refNo);
			if (workflowTasks != null && workflowTasks.length > 0) {
				q.setParameter("workflowTaskList", Arrays.asList(workflowTasks));
			}
			result = (WorkFlow) q.getSingleResult();
			em.flush();
		} catch (NoResultException ne) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find workflow by user.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(WorkFlow workflow) throws DAOException {
		try {
			em.persist(workflow);
			em.persist(new TaskMessage(workflow));
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(WorkFlow workflow) throws DAOException {
		try {
			em.merge(workflow);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(WorkFlow workflow) throws DAOException {
		try {
			WorkFlow meargedWorkflow = em.merge(workflow);
			em.remove(meargedWorkflow);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to updates WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(WorkFlowHistory WorkFlowHistory) throws DAOException {
		try {
			em.persist(WorkFlowHistory);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert WorkFlow", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) throws DAOException {
		long count = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"SELECT COUNT(w.id) FROM WorkFlow w WHERE w.workflowTask = :workflowTask AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", workflowTask);
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			count = (Long) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find count for dashBoard", pe);
		}

		return count;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCountForDashBoard(ReferenceType referenceType, String responsiblePersonId) throws DAOException {
		long count = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(w.id) FROM WorkFlow w WHERE w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			count = (Long) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find count for dashBoard", pe);
		}

		return count;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WF001> find_WF001ByUser(String responsibleUserId) throws DAOException {
		List<WF001> result = new ArrayList<WF001>();
		List<Object[]> rawList = new ArrayList<Object[]>();
		WorkflowTask workflowTask = null;
		ReferenceType referenceType = null;

		try {
			String queryString = "SELECT w.workflowTask, w.referenceType FROM WorkFlow w WHERE w.responsiblePerson.id = :responsibleUserId GROUP BY w.workflowTask, w.referenceType ";
			Query q = em.createQuery(queryString);
			q.setParameter("responsibleUserId", responsibleUserId);
			rawList = q.getResultList();

			for (Object[] object : rawList) {
				workflowTask = (WorkflowTask) object[0];
				referenceType = (ReferenceType) object[1];
				result.add(new WF001(workflowTask, referenceType));
			}

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find proxy WF001  by user.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCoinsuranceCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId, CoinsuranceType coinsuranceType)
			throws DAOException {
		long count = 0;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT COUNT(w.id) FROM Coinsurance c, WorkFlow w WHERE c.id = w.referenceNo AND "
					+ " w.workflowTask = :workflowTask AND w.referenceType = :referenceType AND w.responsiblePerson.id = :responsiblePersonId"
					+ " AND c.coinsuranceType = :coinsuranceType");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("workflowTask", workflowTask);
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			query.setParameter("coinsuranceType", coinsuranceType);
			count = (Long) query.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find count for dashBoard", pe);
		}

		return count;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaim> findLifeClaimForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) throws DAOException {
		List<LifeClaim> resultList = null;
		try {

			String queryString = "SELECT l FROM WorkFlow w, LifeClaim l " + "WHERE w.referenceType = :referenceType and w.workflowTask = :workFlowTask and "
					+ "w.responsiblePerson.id = :responsiblePersonId and w.referenceNo = l.id";
			Query query = em.createQuery(queryString);
			query.setParameter("workFlowTask", workflowTask);
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			resultList = query.getResultList();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaim for dashBoard", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimInsuredPerson> findLifeClaimInsuredPersonForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId)
			throws DAOException {
		List<LifeClaimInsuredPerson> resultList = null;
		try {

			String queryString = "SELECT l FROM WorkFlow w, LifeClaimInsuredPerson l " + "WHERE w.referenceType = :referenceType and w.workflowTask = :workFlowTask and "
					+ "w.responsiblePerson.id = :responsiblePersonId and w.referenceNo = l.id";
			Query query = em.createQuery(queryString);
			query.setParameter("workFlowTask", workflowTask);
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			resultList = query.getResultList();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimInsuredPerson for dashBoard", pe);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimBeneficiary> findLifeClaimBeneficiaryForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) throws DAOException {
		List<LifeClaimBeneficiary> resultList = null;
		try {

			String queryString = "SELECT l FROM WorkFlow w, LifeClaimBeneficiary l " + "WHERE w.referenceType = :referenceType and w.workflowTask = :workFlowTask and "
					+ "w.responsiblePerson.id = :responsiblePersonId and w.referenceNo = l.id";
			Query query = em.createQuery(queryString);
			query.setParameter("workFlowTask", workflowTask);
			query.setParameter("referenceType", referenceType);
			query.setParameter("responsiblePersonId", responsiblePersonId);
			resultList = query.getResultList();

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find LifeClaimBeneficiary for dashBoard", pe);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<WF001> findWorkflowCountByUser(String responsibleUserId, String branchId) throws DAOException {
		List<WF001> result = null;
		try {
			StringBuilder builder = new StringBuilder(" SELECT NEW " + WF001.class.getName());
			builder.append("(w.workflowTask, w.referenceType, COUNT(w.id), w.transactionType) FROM WorkFlow w ");
			builder.append(" WHERE w.responsiblePerson.id =:responsibleUserId AND w.branchId = :branchId");
			builder.append(" GROUP BY w.referenceType, w.workflowTask, w.transactionType ORDER BY w.referenceType ASC, w.transactionType DESC");
			Query query = em.createQuery(builder.toString());
			query.setParameter("responsibleUserId", responsibleUserId);
			query.setParameter("branchId", branchId);
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Workflow Count for DashBoard Left Panel", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlowHistory> findApprovalPersonByRefNo(String refNo) throws DAOException {
		List<WorkFlowHistory> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT h FROM WorkFlowHistory h WHERE h.referenceNo = :referenceNo AND h.workflowTask = :workflowTask");
			Query q = em.createQuery(buffer.toString());
			q.setParameter("referenceNo", refNo);
			q.setParameter("workflowTask", WorkflowTask.APPROVAL);
			result = q.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find WorkFlowHistory by RefNo.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WF002> findWorkflowByCriteria(UserChangerCriteria criteria) {
		List<WF002> results = new ArrayList<WF002>();

		String proposal = null;
		String referenceno = null;
		String joinId = null;

		switch (criteria.getReferenceType()) {
			case AGENT_COMMISSION:
				proposal = " AgentCommission ";
				referenceno = " l.invoiceNo ";
				joinId = " l.invoiceNo ";
				break;

			case CRITICAL_ILLNESS:

				proposal = " MedicalProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";
				break;
			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:

				break;
			case ENDOWMENT_LIFE:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case FARMER:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case GROUP_LIFE:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case HEALTH:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case HEALTH_CLAIM:
				proposal = "LifeClaim ";
				referenceno = " l.claimRequestId ";
				joinId = " l.id ";

				break;
			case HEALTH_POLICY_BILL_COLLECTION:

				break;
			case LIFE_DEALTH_CLAIM:
				proposal = "LifeClaim ";
				referenceno = " l.claimRequestId ";
				joinId = " l.id ";

				break;
			case LIFE_DIS_CLAIM:
				proposal = "LifeClaim ";
				referenceno = " l.claimRequestId ";
				joinId = " l.id ";

				break;
			case LIFE_PAIDUP_PROPOSAL:

				break;
			case LIFESURRENDER:

				break;
			case MICRO_HEALTH:
				proposal = " MedicalProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case MICRO_HEALTH_POLICY_BILL_COLLECTION:

				break;
			case PA:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;

			case SHORT_ENDOWMENT_LIFE:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case SNAKE_BITE:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case SPORT_MAN:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;
			case SPORT_MAN_ABROAD:
				proposal = " LifeProposal ";
				referenceno = " l.proposalNo  ";
				joinId = " l.id ";

				break;

			default:
				break;
		}
		switch (criteria.getTransactionType()) {
			case BILL_COLLECTION:
				proposal = " Payment ";
				referenceno = " l.invoiceNo ";
				joinId = " l.invoiceNo ";
				break;

			default:
				break;
		}
		/* create query */
		try {

			StringBuffer query = new StringBuffer();
			query.append("SELECT DISTINCT NEW " + WF002.class.getName() + "(f.referenceNo,l.id," + referenceno + ", f.workflowTask,");
			query.append(" f.recorder.updatedDate,f.transactionType, f.referenceType)");
			query.append(" FROM " + proposal + " l ,WorkFlow f");
			query.append(" WHERE f.referenceNo = " + joinId);

			if (!criteria.getReferenceNo().isEmpty()) {
				query.append(" AND " + referenceno + " LIKE :referenceNo ");
			}
			if (criteria.getWorkflowTask() != null) {
				query.append(" AND f.workflowTask= :workflowTask");
			}
			if (criteria.getStartDate() != null) {
				query.append(" AND (f.recorder.updatedDate >= :startDate OR f.recorder.createdDate >= :startDate)");
			}
			if (criteria.getEndDate() != null) {
				query.append(" AND (f.recorder.updatedDate <= :endDate OR f.recorder.createdDate <= :endDate)");
			}
			if (criteria.getTransactionType() != null) {
				query.append(" AND f.transactionType= :transactionType");
			}

			if (criteria.getReferenceType() != null) {
				query.append(" AND f.referenceType = :referenceType");
			}

			query.append(" GROUP BY f.referenceNo,l.id," + referenceno + " ,f.workflowTask,f.recorder,f.transactionType,f.referenceType");
			query.append(" ORDER BY " + referenceno + " ASC ");
			Query q = em.createQuery(query.toString());
			if (!criteria.getReferenceNo().isEmpty()) {
				q.setParameter("referenceNo", criteria.getReferenceNo());
			}
			if (criteria.getStartDate() != null) {
				q.setParameter("startDate", Utils.resetStartDate(criteria.getStartDate()));
			}
			if (criteria.getEndDate() != null) {
				q.setParameter("endDate", Utils.resetEndDate(criteria.getEndDate()));
			}
			if (criteria.getWorkflowTask() != null) {
				q.setParameter("workflowTask", criteria.getWorkflowTask());
			}
			if (criteria.getReferenceType() != null) {
				q.setParameter("referenceType", criteria.getReferenceType());
			}
			if (criteria.getTransactionType() != null) {
				q.setParameter("transactionType", criteria.getTransactionType());
			}

			results.addAll(q.getResultList());

			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Policy", pe);
		}

		return results;

	}

	
}
