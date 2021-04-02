/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.role.Role;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.java.component.SystemException;
import org.ace.java.component.service.PasswordCodeHandler;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageUserActionBean")
public class ManageUserActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{PasswordCodeHandler}")
	private PasswordCodeHandler passwordHandler;

	public void setPasswordHandler(PasswordCodeHandler passwordHandler) {
		this.passwordHandler = passwordHandler;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private User user;
	private boolean createNew;
	private List<USR001> userList;
	private List<Branch> branchList;

	@PostConstruct
	public void init() {
		branchList = branchService.findAllBranch();
		createNewUser();
		loadUser();
	}

	public void loadUser() {
		userList = userService.findAllUser();
	}

	public void createNewUser() {
		createNew = true;
		user = new User();
	}

	public void prepareUpdateUser(USR001 usr001) {
		createNew = false;
		user = userService.findUserById(usr001.getId());
		user.setPassword(passwordHandler.decode(user.getPassword()));
	}

	public void addNewUser() {
		try {
			userService.addNewUser(user);
			userList.add(new USR001(user));
			addInfoMessage(null, MessageId.INSERT_SUCCESS, user.getName());
			createNewUser();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateUser() {
		try {
			userService.updateUser(user);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, user.getName());
			createNewUser();
			loadUser();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteUser(USR001 usr001) {
		try {
			user = userService.findUserById(usr001.getId());
			user.setDisabled(true);
			user.setDisabledDate(new Date());
			userService.updateAuthority(user);
			usr001.setDisabled(true);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, user.getName());
			createNewUser();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<USR001> getUserList() {
		return userList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public User getUser() {
		return user;
	}

	public String prepareEditUserAuthority(USR001 usr001) {
		this.user = userService.findUserById(usr001.getId());
		putParam("configUser", user);
		return "authorityConfig";
	}

	public String prepareEditUserAuthorityPermission(USR001 usr001) {
		this.user = userService.findUserById(usr001.getId());
		putParam("configPermissionUser", user);
		return "authorityPermissionConfig";
	}

	public void returnRole(SelectEvent event) {
		Role role = (Role) event.getObject();
		user.setRole(role);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		user.setBranch(branch);
	}

}
