/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.organization.persistence.interfaces;

import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.java.component.persistence.exception.DAOException;

public interface IOrganizationDAO {
	public void insert(Organization Company) throws DAOException;

	public void update(Organization Company) throws DAOException;

	public void delete(Organization Company) throws DAOException;

	public Organization findById(String id) throws DAOException;

	public List<Organization> findAll() throws DAOException;

	public List<ORG001> findAll_ORG001() throws DAOException;

	public void updateActivePolicy(int policyCount, String organizationId) throws DAOException;

	public void updateActivedPolicyDate(Date activedDate, String organizationId) throws DAOException;
}
