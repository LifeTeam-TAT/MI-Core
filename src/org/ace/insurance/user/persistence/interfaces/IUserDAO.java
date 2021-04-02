/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.persistence.interfaces;

import java.util.List;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.java.component.persistence.exception.DAOException;

public interface IUserDAO {
	public User insert(User user) throws DAOException;

	public void changePassword(String username, String newPassword) throws DAOException;

	public void resetPassword(String user, String defaultPassword) throws DAOException;

	public User update(User user) throws DAOException;

	public void delete(User user) throws DAOException;

	public User find(String username) throws DAOException;

	public List<USR001> findAll() throws DAOException;

	public List<USR001> findByPermission(WorkflowTask workflowTask, WorkFlowType workFlowType, TransactionType transactionType, String branchId, String accessBranchId)
			throws DAOException;

	public User findById(String id) throws DAOException;
}
