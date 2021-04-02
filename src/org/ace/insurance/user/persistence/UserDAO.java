/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.user.persistence;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.USR001;
import org.ace.insurance.user.User;
import org.ace.insurance.user.persistence.interfaces.IUserDAO;
import org.ace.java.component.idgen.service.interfaces.IDConfigLoader;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("UserDAO")
public class UserDAO extends BasicDAO implements IUserDAO {

	@Resource(name = "IDConfigLoader")
	private IDConfigLoader configLoader;

	@Transactional(propagation = Propagation.REQUIRED)
	public User insert(User user) throws DAOException {
		try {
			em.persist(user);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert User(Username = " + user.getUsercode() + ")", pe);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User update(User user) throws DAOException {
		try {
			user = em.merge(user);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update User(Username = " + user.getUsercode() + ")", pe);
		}
		return user;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(User user) throws DAOException {
		try {
			user = em.merge(user);
			em.remove(user);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete User(Username = " + user.getUsercode() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<USR001> findAll() {
		List<USR001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + USR001.class.getName() + " (u.id, u.usercode, u.name, u.disabled, u.authority, u.branch.name)");
			buffer.append(" FROM User u ORDER BY u.name ASC");
			Query query = em.createQuery(buffer.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of User.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User find(String usercode) throws DAOException {
		User result = null;
		try {
			Query q = em.createNamedQuery("User.findByUsercode");
			q.setParameter("usercode", usercode);
			result = (User) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find User(Username = " + usercode + ")", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User findById(String id) throws DAOException {
		User result = null;
		try {
			Query q = em.createNamedQuery("User.findById");
			q.setParameter("id", id);
			result = (User) q.getSingleResult();
			em.flush();
		} catch (NoResultException pe) {
			return null;
		} catch (PersistenceException pe) {
			throw translate("Failed to find User(Username = " + id + ")", pe);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<USR001> findByPermission(WorkflowTask workflowTask, WorkFlowType workFlowType, TransactionType transactionType, String branchId, String accessBranchId)
			throws DAOException {
		List<USR001> result = null;
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT NEW " + USR001.class.getName() + "(u.id, u.usercode, u.name, u.disabled, u.authority , u.branch.preFix) ");
			buffer.append("FROM User u LEFT JOIN u.authorityList a ");
			if (accessBranchId != null)
				buffer.append("LEFT JOIN u.accessBranchList b ");
			buffer.append("WHERE a.workFlowType = :workFlowType AND u.disabled = FALSE ");
			buffer.append("AND :permission MEMBER OF a.permissionList AND a.transactionType = :transactionType ");
			if (branchId != null)
				buffer.append("AND u.branch.id = :branchId ");
			if (accessBranchId != null)
				buffer.append("AND b.id = :accessBranchId ");
			Query query = em.createQuery(buffer.toString());
			query.setParameter("permission", workflowTask);
			query.setParameter("workFlowType", workFlowType);
			query.setParameter("transactionType", transactionType);
			if (branchId != null)
				query.setParameter("branchId", branchId);
			if (accessBranchId != null)
				query.setParameter("accessBranchId", accessBranchId);
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find user by permission", pe);
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void changePassword(String usercode, String newPassword) throws DAOException {
		try {
			Query q = em.createNamedQuery("User.changePassword");
			q.setParameter("usercode", usercode);
			q.setParameter("newPassword", newPassword);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to change Password (Username = " + usercode + ")", pe);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void resetPassword(String usercode, String defaultPassword) throws DAOException {
		try {
			Query q = em.createNamedQuery("User.resetPassword");
			q.setParameter("usercode", usercode);
			q.setParameter("defaultPassowrd", defaultPassword);
			q.executeUpdate();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to reset Password (Username = " + usercode + ")", pe);
		}
	}

}
