package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "UserDialogActionBean")
@ViewScoped
public class UserDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private List<USR001> userList;

	@PreDestroy
	public void destroy() {
		removeParam("workflowTask");
		removeParam("workFlowType");
		removeParam("transactionType");
		removeParam("branchId");
		removeParam("accessBranchId");
	}

	@PostConstruct
	public void init() {
		WorkflowTask workFlowTask = (WorkflowTask) getParam("workflowTask");
		WorkFlowType workFlowType = (WorkFlowType) getParam("workFlowType");
		TransactionType transactionType = (TransactionType) getParam("transactionType");
		String branchId = (String) getParam("branchId");
		String accessBranchId = (String) getParam("accessBranchId");
		if (workFlowTask != null) {
			userList = userService.findUserByPermission(workFlowTask, workFlowType, transactionType, branchId, accessBranchId);
		} else {
			userList = userService.findAllUser();
		}
		destroy();
	}

	public void selectUser(USR001 usr001) {
		User user = userService.findUserById(usr001.getId());
		PrimeFaces.current().dialog().closeDynamic(user);
	}

	public List<USR001> getUserList() {
		return userList;
	}
}
