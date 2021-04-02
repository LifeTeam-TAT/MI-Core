/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.organization.service.interfaces;

import java.util.List;

import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;

public interface IOrganizationService {
	public void addNewOrganization(Organization Organization);

	public void updateOrganization(Organization Organization);

	public void deleteOrganization(Organization Organization);

	public Organization findOrganizationById(String id);

	public List<Organization> findAllOrganization();

	public List<ORG001> findAll_ORG001();

	public void updateActivePolicy(int policyCount, String organizationId);
}
