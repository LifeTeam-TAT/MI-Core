/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.role.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;
import org.ace.insurance.role.persistence.interfaces.IRoleDAO;
import org.ace.insurance.role.service.interfaces.IRoleService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("RoleService")
public class RoleService extends BaseService implements IRoleService {

	@Resource(name = "RoleDAO")
	private IRoleDAO roleDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public Role addNewRole(Role role) {
		try {
			role = roleDAO.insert(role);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a Role", e);
		}
		return role;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role updateRole(Role role) {
		try {
			role = roleDAO.update(role);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update role", e);
		}
		return role;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Role role) {
		try {
			roleDAO.delete(role);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete role", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ROL001> findAllRole() {
		List<ROL001> result = null;
		try {
			result = roleDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to fine all Role", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Role findRoleById(String id) {
		Role result = null;
		try {
			result = roleDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to fine all Role", e);
		}
		return result;
	}

}
