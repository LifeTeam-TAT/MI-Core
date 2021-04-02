/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.role.service.interfaces;

import java.util.List;

import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;

public interface IRoleService {
	public Role addNewRole(Role role);

	public Role updateRole(Role role);

	public void deleteRole(Role role);

	public Role findRoleById(String id);

	public List<ROL001> findAllRole();

}
