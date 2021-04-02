/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.java.web.authentication;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.user.User;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.userlog.service.interfaces.IUserLogService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.ace.java.web.common.MessageId;

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean extends BaseBean {
	@ManagedProperty(value = "#{UserService}")
	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@ManagedProperty(value = "#{UserLogService}")
	private IUserLogService userLogService;

	public void setUserLogService(IUserLogService userlogService) {
		this.userLogService = userlogService;
	}

	private String username;
	private String password;
	private String branchId;
	private List<BRA001> branchList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public List<BRA001> getBranchList() {
		return branchList;
	}

	@PostConstruct
	public void init() {
		branchList = branchService.findAll_BRA001();
	}

	public String authenticate() {
		String result = null;
		try {
			User user = userService.authenticate(username, password, branchId);
			putParam(Constants.LOGIN_USER, user);
			result = "dashboard";
		} catch (SystemException exception) {
			if (MessageId.USER_NAME_FAIL.equals(exception.getErrorCode()))
				addInfoMessage(null, exception.getErrorCode(), username);
			else if (MessageId.USER_PASSWORD_FAIL.equals(exception.getErrorCode())) {
				addInfoMessage(null, exception.getErrorCode());
			} else if (MessageId.USER_BRANCH_FAIL.equals(exception.getErrorCode())) {
				BRA001 branch = branchList.stream().filter(b -> branchId.equals(b.getId())).findAny().orElse(null);
				addInfoMessage(null, exception.getErrorCode(), branch.getName());
			}
		}
		return result;
	}

	public String logout() {
		// UserLog userLog = (UserLog) getParam("UserLog");
		// userLog.setLogOutDate(new Date());
		// userLogService.updateUserLog(userLog);
		HttpSession session = (HttpSession) getFacesContext().getExternalContext().getSession(false);
		session.invalidate();
		if (getPrimeTheme().equalsIgnoreCase("home")) {
			return "pslogin";
		} else {
			return "login";
		}
	}

	public void checkPermission(ComponentSystemEvent event) {
		FacesContext context = getFacesContext();
		ExternalContext extContext = context.getExternalContext();
		String messageId = (String) extContext.getSessionMap().remove(Constants.MESSAGE_ID);
		if (messageId != null) {
			addInfoMessage(null, messageId);
		}
		if (isExistParam(Constants.VIEW_EXPIRED)) {
			if ((boolean) getParam(Constants.VIEW_EXPIRED)) {
				addInfoMessage("Your session has expired. Please login again.");
				removeParam(Constants.VIEW_EXPIRED);
			}
		}
	}
}
