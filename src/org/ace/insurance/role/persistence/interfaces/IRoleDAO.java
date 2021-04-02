/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.role.persistence.interfaces;

import java.util.List;

import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;
import org.ace.java.component.persistence.exception.DAOException;

public interface IRoleDAO {
	public Role insert(Role role) throws DAOException;

	public Role update(Role role) throws DAOException;

	public void delete(Role role) throws DAOException;

	public Role findById(String id) throws DAOException;

	public List<ROL001> findAll() throws DAOException;

}
