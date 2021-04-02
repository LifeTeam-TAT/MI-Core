package org.ace.insurance.workflow.service.interfaces;

import java.util.List;
import java.util.Map;

import org.ace.insurance.common.CoinsuranceType;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.WorkFlowDTO;
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

public interface IWorkFlowService {
	public long findCountForDashBoard(ReferenceType referenceType, String responsiblePersonId);

	public void addNewWorkFlow(WorkFlowDTO workFlowDTO);

	public void addWorkFlowHistory(WorkFlowDTO workFlowDTO);

	public void updateWorkFlow(WorkFlowDTO workFlowDTO, WorkflowTask... flowTasks);

	public void deleteWorkFlowByRefNo(String refNo);

	public List<WorkFlow> findWorkFlowByUser(User user);

	public long findRequestCountByUser(User user);

	public WorkFlow findWorkFlowByRefNo(String refNo, WorkflowTask... workflowTasks);

	public WorkFlow findWorkFlowByChitRefNo(String refNo, WorkflowTask... flowTasks);

	public List<WorkFlowHistory> findWorkFlowHistoryByRefNo(String refNo, WorkflowTask... workflowTask);

	public List<WF001> find_WF001ByUser(String responsibleUserId);

	public long findCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId);

	public long findCoinsuranceCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId, CoinsuranceType coinsuranceType);

	public List<LifeClaim> findLifeClaimForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId);

	public List<LifeClaimInsuredPerson> findLifeClaimInsuredPersonForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId);

	public List<LifeClaimBeneficiary> findLifeClaimBeneficiaryForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId);

	public Map<WorkflowTask, User> findUserByWorkFlow(String proposalId);

	public List<WF001> findWorkflowCountByUser(String userId, String branchId);

	public void denyWorkFlow(WorkFlowDTO workFlowDTO);

	public List<WorkFlowHistory> findApprovalPersonByRefNo(String refNo);

	public void addWorkFlowHistory(WorkFlow workflow);

	public List<WF002> findWorkflowByCriteria(UserChangerCriteria criteria);

}
