package org.ace.insurance.workflow.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.CoinsuranceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.LifeClaimInsuredPerson;
import org.ace.insurance.proxy.WF001;
import org.ace.insurance.proxy.WF002;
import org.ace.insurance.user.User;
import org.ace.insurance.web.util.UserChangerCriteria;
import org.ace.insurance.workflow.WorkFlow;
import org.ace.insurance.workflow.WorkFlowHistory;
import org.ace.java.component.persistence.exception.DAOException;

public interface IWorkFlowDAO {
	public long findCountForDashBoard(ReferenceType referenceType, String responsiblePersonId) throws DAOException;

	public void insert(List<WorkFlowHistory> wrokflowList) throws DAOException;

	public void insertWorkFlowList(List<WorkFlow> wrokflowList) throws DAOException;

	public void insert(WorkFlow workflow) throws DAOException;

	public void update(WorkFlow workflow) throws DAOException;

	public void delete(WorkFlow workflow) throws DAOException;

	public void insert(WorkFlowHistory WorkFlowHistory) throws DAOException;

	public List<WorkFlow> findByUser(User user) throws DAOException;

	public long findRequestCountByUser(User user) throws DAOException;

	public WorkFlow findByRefNo(String refNo, WorkflowTask... workflowTasks) throws DAOException;

	public WorkFlow findByCashInTransitRefNo(String refNo, WorkflowTask... workflowTasks) throws DAOException;

	public List<WorkFlowHistory> findWorkFlowHistoryByRefNo(String refNo, WorkflowTask... workflowTask) throws DAOException;

	public List<WF001> find_WF001ByUser(String responsibleUserId) throws DAOException;

	public long findCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsibleUserId) throws DAOException;

	public long findCoinsuranceCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId, CoinsuranceType coinsuranceType)
			throws DAOException;

	public List<LifeClaim> findLifeClaimForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) throws DAOException;

	public List<LifeClaimBeneficiary> findLifeClaimBeneficiaryForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) throws DAOException;

	public List<LifeClaimInsuredPerson> findLifeClaimInsuredPersonForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId)
			throws DAOException;

	public List<WF001> findWorkflowCountByUser(String userId, String branchId) throws DAOException;

	public List<WorkFlowHistory> findApprovalPersonByRefNo(String refNo) throws DAOException;

	public List<WF002> findWorkflowByCriteria(UserChangerCriteria criteria);

}
