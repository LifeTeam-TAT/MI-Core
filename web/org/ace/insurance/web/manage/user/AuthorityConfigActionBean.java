package org.ace.insurance.web.manage.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.Authority;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.model.DualListModel;

@ViewScoped
@ManagedBean(name = "AuthorityConfigActionBean")
public class AuthorityConfigActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	private User configUser;
	private Map<String, Authority> authorityMap;
	private Authority authority;
	private boolean isCreatNew;
	private DualListModel<WorkflowTask> dualListModel;

	private void initializeInjection() {
		configUser = (User) getParam("configUser");
	}

	@PostConstruct
	public void init() {
		initializeInjection();
		loadAuthority();
		createNewAuthority();

	}

	public void createNewAuthority() {
		isCreatNew = true;
		authority = new Authority();
		dualListModel = new DualListModel<WorkflowTask>(Arrays.asList(WorkflowTask.values()), new ArrayList<WorkflowTask>());
	}

	private void loadAuthority() {
		authorityMap = new HashMap<>();
		List<Authority> authorityList = configUser.getAuthorityList();
		String key = null;
		if (authorityList != null && !authorityList.isEmpty()) {
			for (Authority authority : authorityList) {
			//	key = authority.getWorkFlowType().getLabel() + authority.getTransactionType().getLabel();
				authorityMap.put(authority.getId(), authority);
			}
		}
	}

	public void addAuthority() {
		authority.setPermissionList(dualListModel.getTarget());
	String key = authority.getWorkFlowType().getLabel() + authority.getTransactionType().getLabel();
	    authorityMap.put(key, authority);
		createNewAuthority();

	}

	public void prepareEditAuthority(Authority authority) {
		this.isCreatNew = false;
		this.authority = authority;
		List<WorkflowTask> source = new ArrayList<>();
		for (WorkflowTask task : Arrays.asList(WorkflowTask.values())) {
			if (!authority.getPermissionList().contains(task))
				source.add(task);
		}
		dualListModel = new DualListModel<WorkflowTask>(source, authority.getPermissionList());

	}

	public void deleteAuthority(Authority authority) {
	//	String key = authority.getWorkFlowType().getLabel() + authority.getTransactionType().getLabel();
		authorityMap.remove(authority.getId());
		createNewAuthority();
	}

	public String applyAuthority() {
		String result = null;
		try {
			List<Authority> authorityList = new ArrayList<>(authorityMap.values());
			configUser.setAuthorityList(authorityList);
			userService.updateAuthority(configUser);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, configUser.getUsercode());
			result = "manageUser";
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return result;
	}

	public User getUser() {
		return configUser;
	}

	public Authority getAuthority() {
		return authority;
	}

	public boolean isCreatNew() {
		return isCreatNew;
	}

	public DualListModel<WorkflowTask> getDualListModel() {
		return dualListModel;
	}

	public void setDualListModel(DualListModel<WorkflowTask> dualListModel) {
		this.dualListModel = dualListModel;
	}

	public List<Authority> getAuthorityList() {
		return new ArrayList<>(authorityMap.values());
	}

	public WorkFlowType[] getWorkFlowTypes() {
		return WorkFlowType.values();
	}

	public List<WorkflowTask> getWorkflowTasks() {
		return Arrays.asList(WorkflowTask.values());
	}

	public TransactionType[] getTransactionTypes() {
		return TransactionType.values();
	}

}
