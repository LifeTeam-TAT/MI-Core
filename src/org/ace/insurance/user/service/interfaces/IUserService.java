/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.service.interfaces;

import java.util.List;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.java.component.SystemException;

public interface IUserService {
	public User authenticate(String username, String password, String loginBranchId) throws SystemException;

	public User addNewUser(User user);

	public void changePassword(String username, String newPassword, String oldPassword);

	public void resetPassword(String user);

	public User updateUser(User user);

	public User updateAuthority(User user);

	public void deleteUser(User user);

	public User findUser(String userCode);

	public List<USR001> findAllUser() throws SystemException;

	public List<USR001> findUserByPermission(WorkflowTask workflowTask, WorkFlowType workFlowType, TransactionType transactionType, String branchId, String accessBranchId)
			throws SystemException;

	public User findUserById(String id);

	User findUserAndCreateLog(String userCode);

	public User updateAuthorityPermission(User configUser);
}
