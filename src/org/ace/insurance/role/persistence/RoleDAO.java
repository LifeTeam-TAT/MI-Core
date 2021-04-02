/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.role.persistence;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;
import org.ace.insurance.role.persistence.interfaces.IRoleDAO;
import org.ace.java.component.persistence.BasicDAO;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("RoleDAO")
public class RoleDAO extends BasicDAO implements IRoleDAO {

	@Transactional(propagation = Propagation.REQUIRED)
	public Role insert(Role role) throws DAOException {
		try {
			em.persist(role);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to insert Role(Rolename = " + role.getName() + ")", pe);
		}
		return role;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role update(Role role) throws DAOException {
		try {
			role = em.merge(role);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update Role(Rolename = " + role.getName() + ")", pe);
		}
		return role;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Role role) throws DAOException {
		try {
			role = em.merge(role);
			em.remove(role);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to delete Role(Rolename = " + role.getName() + ")", pe);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ROL001> findAll() {
		List<ROL001> result = null;
		try {
			StringBuffer buffer = new StringBuffer("SELECT NEW " + ROL001.class.getName() + "(r.id, r.name) FROM Role r ORDER BY r.name ASC");
			Query query = em.createQuery(buffer.toString());
			result = query.getResultList();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find all of Role.", pe);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role findById(String id) throws DAOException {
		Role result = null;
		try {
			result = em.find(Role.class, id);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Role(ID = " + id + ")", pe);
		}
		return result;
	}

}
