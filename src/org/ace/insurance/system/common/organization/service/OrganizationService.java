/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.system.common.organization.service;

import java.util.List;

import javax.annotation.Resource;

import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.persistence.interfaces.IOrganizationDAO;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "OrganizationService")
public class OrganizationService extends BaseService implements IOrganizationService {

	@Resource(name = "OrganizationDAO")
	private IOrganizationDAO organizationDAO;

	public void addNewOrganization(Organization organization) {
		try {
			organizationDAO.insert(organization);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to add a new Organization", e);
		}
	}

	public void updateOrganization(Organization organization) {
		try {
			organizationDAO.update(organization);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update a Organization", e);
		}
	}

	public void deleteOrganization(Organization organization) {
		try {
			organizationDAO.delete(organization);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete a Organization", e);
		}
	}

	public List<Organization> findAllOrganization() {
		List<Organization> result = null;
		try {
			result = organizationDAO.findAll();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all of Organization)", e);
		}
		return result;
	}

	public Organization findOrganizationById(String id) {
		Organization result = null;
		try {
			result = organizationDAO.findById(id);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find a Organization (ID : " + id + ")", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ORG001> findAll_ORG001() {
		List<ORG001> result = null;
		try {
			result = organizationDAO.findAll_ORG001();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find all ORG001 ", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateActivePolicy(int policyCount, String organizationId) {
		try {
			organizationDAO.updateActivePolicy(policyCount, organizationId);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update activePolicy", e);
		}
	}

}