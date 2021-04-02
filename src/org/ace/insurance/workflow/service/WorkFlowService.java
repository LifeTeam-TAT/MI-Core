package org.ace.insurance.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import org.ace.insurance.workflow.persistence.interfaces.IWorkFlowDAO;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("WorkFlowService")
public class WorkFlowService extends BaseService implements IWorkFlowService {

	@Resource(name = "WorkFlowDAO")
	private IWorkFlowDAO workFlowDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addNewWorkFlow(WorkFlowDTO workFlowDTO) {
		try {
			WorkFlow workflow = new WorkFlow(workFlowDTO);
			workFlowDAO.insert(workflow);
			WorkFlowHistory wfHistory = new WorkFlowHistory(workFlowDTO);
			workFlowDAO.insert(wfHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new WorkFlow", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateWorkFlow(WorkFlowDTO workFlowDTO, WorkflowTask... flowTasks) {
		try {
			WorkFlowHistory wfHistory = new WorkFlowHistory(workFlowDTO);
			workFlowDAO.insert(wfHistory);
			WorkFlow workflow = null;
			if (ArrayUtils.indexOf(flowTasks, WorkflowTask.PAYMENT) == 0) {
				workflow = findWorkFlowByChitRefNo(workFlowDTO.getReferenceNo(), WorkflowTask.PAYMENT);
			} else {
				workflow = findWorkFlowByRefNo(workFlowDTO.getReferenceNo());
			}
			dataTransfer(workflow, workFlowDTO);
			workFlowDAO.update(workflow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update WorkFlow by RefNO : " + workFlowDTO.getReferenceNo(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addWorkFlowHistory(WorkFlowDTO workFlowDTO) {
		try {
			WorkFlowHistory wfHistory = new WorkFlowHistory(workFlowDTO);
			workFlowDAO.insert(wfHistory);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add WorkFlow History" + workFlowDTO.getReferenceNo(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addWorkFlowHistory(WorkFlow workflow) {
		try {
			workFlowDAO.insert(workflow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add WorkFlow History" + workflow.getReferenceNo(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteWorkFlowByRefNo(String refNo) {
		try {
			WorkFlow workflow = findWorkFlowByRefNo(refNo);
			workFlowDAO.delete(workflow);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete WorkFlow by RefNO " + refNo, e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlow> findWorkFlowByUser(User user) {
		List<WorkFlow> result = null;
		try {
			result = workFlowDAO.findByUser(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlow by user : " + user.getUsercode(), e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findRequestCountByUser(User user) {
		long result = 0;
		try {
			result = workFlowDAO.findRequestCountByUser(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Request Count by user : " + user.getUsercode(), e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlowHistory> findWorkFlowHistoryByRefNo(String refNo, WorkflowTask... workflowTasks) {
		List<WorkFlowHistory> result = null;
		try {
			result = workFlowDAO.findWorkFlowHistoryByRefNo(refNo, workflowTasks);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlowHistory by RefNo : " + refNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkFlow findWorkFlowByRefNo(String refNo, WorkflowTask... workflowTasks) {
		WorkFlow result = null;
		try {
			result = workFlowDAO.findByRefNo(refNo, workflowTasks);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlow by reference No : " + refNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public WorkFlow findWorkFlowByChitRefNo(String refNo, WorkflowTask... flowTasks) {
		WorkFlow result = null;
		try {
			result = workFlowDAO.findByCashInTransitRefNo(refNo, flowTasks);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlow by reference No : " + refNo, e);
		}
		return result;
	}

	public void dataTransfer(WorkFlow wf, WorkFlowDTO wfDTO) {
		wf.setTransactionType(wfDTO.getTransactionType());
		wf.setWorkflowTask(wfDTO.getWorkflowTask());
		wf.setRemark(wfDTO.getRemark());
		wf.setResponsiblePerson(wfDTO.getResponsiblePerson());
		wf.setCreatedUser(wfDTO.getCreatedUser());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) {
		long result = 0;
		try {
			result = workFlowDAO.findCountForDashBoard(workflowTask, referenceType, responsiblePersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Count for dashBoard", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCountForDashBoard(ReferenceType referenceType, String responsiblePersonId) {
		long result = 0;
		try {
			result = workFlowDAO.findCountForDashBoard(referenceType, responsiblePersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Count for dashBoard", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WF001> find_WF001ByUser(String responsibleUserId) {
		List<WF001> result = null;
		try {
			result = workFlowDAO.find_WF001ByUser(responsibleUserId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find proxy WF001 by user for dashBoard", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long findCoinsuranceCountForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId, CoinsuranceType coinsuranceType) {
		long result = 0;
		try {
			result = workFlowDAO.findCoinsuranceCountForDashBoard(workflowTask, referenceType, responsiblePersonId, coinsuranceType);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Coinsurance Count for dashBoard", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaim> findLifeClaimForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) {
		List<LifeClaim> resultList = null;
		try {
			resultList = workFlowDAO.findLifeClaimForDashBoard(workflowTask, referenceType, responsiblePersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaim for dashBoard", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimInsuredPerson> findLifeClaimInsuredPersonForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) {
		List<LifeClaimInsuredPerson> resultList = null;
		try {
			resultList = workFlowDAO.findLifeClaimInsuredPersonForDashBoard(workflowTask, referenceType, responsiblePersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimInsuredPerson for dashBoard", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeClaimBeneficiary> findLifeClaimBeneficiaryForDashBoard(WorkflowTask workflowTask, ReferenceType referenceType, String responsiblePersonId) {
		List<LifeClaimBeneficiary> resultList = null;
		try {
			resultList = workFlowDAO.findLifeClaimBeneficiaryForDashBoard(workflowTask, referenceType, responsiblePersonId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find LifeClaimBeneficiary for dashBoard", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Map<WorkflowTask, User> findUserByWorkFlow(String proposalId) {
		Map<WorkflowTask, User> resultMap = new HashMap<WorkflowTask, User>();
		try {
			List<WorkFlowHistory> workFlowHisList = workFlowDAO.findWorkFlowHistoryByRefNo(proposalId, null);
			for (WorkFlowHistory his : workFlowHisList) {
				if (his.getWorkflowTask().equals(WorkflowTask.SURVEY)) {
					resultMap.put(WorkflowTask.SURVEY, his.getCreatedUser());
				} else if (his.getWorkflowTask().equals(WorkflowTask.SURVEY)) {
					resultMap.put(WorkflowTask.APPROVAL, his.getCreatedUser());
				}
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find user by workflow ", e);
		}
		return resultMap;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<WF001> findWorkflowCountByUser(String userId, String branchId) {
		List<WF001> resultList = null;
		try {
			resultList = workFlowDAO.findWorkflowCountByUser(userId, branchId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlow Count By User", e);
		}
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void denyWorkFlow(WorkFlowDTO workFlowDTO) {
		try {
			WorkFlowHistory wfHistory = new WorkFlowHistory(workFlowDTO);
			workFlowDAO.insert(wfHistory);
			deleteWorkFlowByRefNo(workFlowDTO.getReferenceNo());
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update WorkFlow by RefNO : " + workFlowDTO.getReferenceNo(), e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WorkFlowHistory> findApprovalPersonByRefNo(String refNo) {
		List<WorkFlowHistory> result = null;
		try {
			result = workFlowDAO.findApprovalPersonByRefNo(refNo);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlowHistory by RefNo : " + refNo, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<WF002> findWorkflowByCriteria(UserChangerCriteria criteria) {
		List<WF002> result = null;
		try {
			result = workFlowDAO.findWorkflowByCriteria(criteria);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find WorkFlow by Criteria : " + criteria, e);
		}
		return result;
	}

}
