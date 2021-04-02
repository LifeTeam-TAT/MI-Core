/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.process.interfaces.IUserProcessService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.persistence.interfaces.IBranchDAO;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.insurance.user.persistence.interfaces.IUserDAO;
import org.ace.insurance.user.service.interfaces.IUserService;
import org.ace.insurance.userlog.UserLog;
import org.ace.insurance.userlog.persistences.interfaces.IUserLogDAO;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.ace.java.component.service.PasswordCodeHandler;
import org.ace.java.web.common.MessageId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class UserService extends BaseService implements IUserService {

	@Resource(name = "UserDAO")
	private IUserDAO userDAO;

	@Resource(name = "BranchDAO")
	private IBranchDAO branchDAO;

	@Resource(name = "UserLogDAO")
	private IUserLogDAO userlogDAO;

	@Resource(name = "PasswordCodeHandler")
	private PasswordCodeHandler codecHandler;

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	@Transactional(propagation = Propagation.REQUIRED)
	public User addNewUser(User user) {
		try {
			user.setPassword(codecHandler.encode(user.getPassword()));
			user = userDAO.insert(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a User", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(User user) {
		try {
			user.setPassword(codecHandler.encode(user.getPassword()));
			user = userDAO.update(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update user", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateAuthority(User user) {
		try {
			user = userDAO.update(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update user", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) {
		try {
			userDAO.delete(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete user", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<USR001> findUserByPermission(WorkflowTask workflowTask, WorkFlowType workFlowType, TransactionType transactionType, String branchId, String accessBranchId) {
		List<USR001> result = null;
		try {
			result = userDAO.findByPermission(workflowTask, workFlowType, transactionType, branchId, accessBranchId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to user by permission : " + workflowTask + " : " + workFlowType, e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<USR001> findAllUser() throws SystemException {
		List<USR001> result = null;
		try {
			result = userDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all user", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findUser(String userCode) {
		User user = null;
		try {
			user = userDAO.find(userCode);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find user by UserCode : " + userCode, e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findUserById(String id) {
		User user = null;
		try {
			user = userDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find user by Id : " + id, e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(String usercode, String newPassword, String oldPassword) {
		try {
			User user = userDAO.find(usercode);
			String encodedPassword = codecHandler.encode(oldPassword);
			if (user != null && user.getPassword().equals(encodedPassword)) {
				String encryptedPassword = codecHandler.encode(newPassword);
				userDAO.changePassword(usercode, encryptedPassword);
			} else {
				throw new SystemException(ErrorCode.OLD_PASSWORD_DOES_NOT_MATCH, "Old password does not match.");
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to change passowrd", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(String usercode) {
		try {
			String encryptedPassword = codecHandler.encode(User.DEFAULT_PASSWORD);
			userDAO.resetPassword(usercode, encryptedPassword);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to reset passowrd", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User findUserAndCreateLog(String userCode) {
		User user = userDAO.find(userCode);
		UserLog userLog = new UserLog();
		userLog.setUserId(user.getId());
		userLog.setLogInDate(new Date());
		userlogDAO.insertUserLost(userLog);
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User authenticate(String usercode, String password, String loginBranchId) throws SystemException {
		User user = null;
		try {
			user = userDAO.find(usercode);
			if (user != null) {
				String encodedPassword = codecHandler.encode(password);
				if (user.getPassword().equals(encodedPassword)) {
					Branch branch = user.getAccessBranchList().stream().filter(b -> loginBranchId.equals(b.getId())).findAny().orElse(null);
					if (branch != null) {
						user.setLoginBranch(branch);
					} else {
						throw new SystemException(MessageId.USER_BRANCH_FAIL, "Failed to Login with branch : " + loginBranchId);
					}
					userProcessService.registerUser(user);
					// UserLog userLog = new UserLog();
					// userLog.setLogInDate(new Date());
					// userLog.setUserId(user.getId());
					// userLog.setPassword(encodedPassword);
					// userlogDAO.insertUserLost(userLog);
					// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UserLog",
					// userLog);
				} else {
					throw new SystemException(MessageId.USER_PASSWORD_FAIL, "Failed to Login with password : " + password);
				}
			} else {
				throw new SystemException(MessageId.USER_NAME_FAIL, "Failed to Login with usercode : " + usercode);
			}
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to login authenticate", e);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateAuthorityPermission(User user) {
		try {
			user = userDAO.update(user);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update user", e);
		}
		return user;
	}
}
