package org.ace.insurance.web.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.common.interfaces.IDataModel;
import org.ace.insurance.proxy.WF002;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.workflow.service.interfaces.IWorkFlowService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "UserChangerActionBean")
public class UserChangerActionBean<T extends IDataModel> extends BaseBean {

	@ManagedProperty(value = "#{WorkFlowService}")
	private IWorkFlowService workflowService;

	public void setWorkflowService(IWorkFlowService workflowService) {
		this.workflowService = workflowService;

	}

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private WorkflowTask workflowTask;
	private User user;
	private List<WF002> workflowList;
	private String loginBranchId;
	private User responsiblePerson;
	private UserChangerCriteria criteria;
	private WF002[] selectedWorkflowUser;
	private ReferenceType referenceType;
	private TransactionType transactionType;

	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		loginBranchId = user.getLoginBranch().getId();
		resetCriteria();
	}

	public void resetCriteria() {
		criteria = new UserChangerCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		criteria.setReferenceNo("");
		criteria.setWorkflowTask(null);

	}

	public WorkflowTask[] getWorkfolwTask() {
		return WorkflowTask.values();
	}

	public ReferenceType[] getReferenceTypes() {
		return ReferenceType.values();
	}

	public TransactionType[] getTransactionTypes() {
		return TransactionType.values();
	}

	public void changeWorkflowTask(SelectEvent event) {
		WorkflowTask workflow = (WorkflowTask) event.getObject();
		this.workflowTask = workflow;
	}

	public void changeReferenceType(SelectEvent event) {
		ReferenceType ref = (ReferenceType) event.getObject();
		this.referenceType = ref;
	}

	public void changeTransactionType(SelectEvent event) {
		TransactionType tran = (TransactionType) event.getObject();
		this.transactionType = tran;
	}

	public void selectUser() {
		WorkFlowType wfType = null;
		TransactionType tranType = null;
		switch (referenceType) {
			case ENDOWMENT_LIFE:
				wfType = WorkFlowType.LIFE;
				break;
			case GROUP_LIFE:
				wfType = WorkFlowType.LIFE;
				break;
			case SPORT_MAN:
				wfType = WorkFlowType.LIFE;
				break;
			case SHORT_ENDOWMENT_LIFE:
				wfType = WorkFlowType.SHORT_ENDOWMENT;
				break;
			case FARMER:
				wfType = WorkFlowType.FARMER;
				break;
			case PA:
				wfType = WorkFlowType.PERSONAL_ACCIDENT;
				break;
			case SNAKE_BITE:
				wfType = WorkFlowType.SNAKE_BITE;
				break;
			case HEALTH:
				wfType = WorkFlowType.MEDICAL_INSURANCE;
				break;
			case CRITICAL_ILLNESS:
				wfType = WorkFlowType.MEDICAL_INSURANCE;
				break;
			case MICRO_HEALTH:
				wfType = WorkFlowType.MEDICAL_INSURANCE;
				break;
			case AGENT_COMMISSION:
				wfType = WorkFlowType.AGENT_COMMISSION;
				break;

			case CRITICAL_ILLNESS_POLICY_BILL_COLLECTION:
				break;
			case HEALTH_CLAIM:
				break;
			case HEALTH_POLICY_BILL_COLLECTION:
				break;
			case LIFE_DEALTH_CLAIM:
				break;
			case LIFE_DIS_CLAIM:
				break;
			case LIFE_PAIDUP_PROPOSAL:
				break;
			case LIFESURRENDER:
				break;
			case MICRO_HEALTH_POLICY_BILL_COLLECTION:
				break;

			case SPORT_MAN_ABROAD:
				wfType = WorkFlowType.LIFE;
				break;

			default:
				break;
		}

		switch (transactionType) {
			case UNDERWRITING:
				tranType = TransactionType.UNDERWRITING;
				break;

			case ENDORSEMENT:
				tranType = TransactionType.ENDORSEMENT;
				break;
			case RENEWAL:
				tranType = TransactionType.RENEWAL;
				break;

			case BILL_COLLECTION:
				tranType = TransactionType.BILL_COLLECTION;
				break;

			default:
				break;
		}

		selectUser(workflowTask, wfType, tranType, loginBranchId, null);
	}

	public void returnUser(SelectEvent event) {
		User user = (User) event.getObject();
		this.setResponsiblePerson(user);
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public void search() {

		if (criteria.getReferenceType() != null) {
			workflowList = workflowService.findWorkflowByCriteria(criteria);
		} else {
			workflowList = workflowService.findWorkflowByCriteria(criteria);
		}

	}

	public void updateWorkflow() {
		try {
			if (responsiblePerson != null) {
				for (WF002 workflow : selectedWorkflowUser) {
					WorkFlowDTO workFlowDTO = new WorkFlowDTO(workflow.getReferenceNo(), loginBranchId, "", workflowTask, referenceType, transactionType, user, responsiblePerson);
					workflowService.updateWorkFlow(workFlowDTO, workflowTask);
					workflowList.remove(workflow);

				}
				ExternalContext extContext = getFacesContext().getExternalContext();
				extContext.getSessionMap().put(Constants.MESSAGE_ID, MessageId.WORKFLOW_CHANGER_PROCESS_SUCCESS);
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public WF002[] getSelectedWorkflowUser() {
		return selectedWorkflowUser;
	}

	public void setSelectedWorkflowUser(WF002[] selectedWorkflowUser) {
		this.selectedWorkflowUser = selectedWorkflowUser;
	}

	public List<WF002> getWorkflowList() {
		return workflowList;
	}

	public void setWorkflowList(List<WF002> workflowList) {
		this.workflowList = workflowList;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public User getUser() {
		return user;
	}

	public String getLoginBranchId() {
		return loginBranchId;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setWorkflowTask(WorkflowTask workflowTask) {
		this.workflowTask = workflowTask;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLoginBranchId(String loginBranchId) {
		this.loginBranchId = loginBranchId;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public UserChangerCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(UserChangerCriteria criteria) {
		this.criteria = criteria;
	}

}
