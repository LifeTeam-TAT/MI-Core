/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageOrganizationActionBean")
public class ManageOrganizationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OrganizationService}")
	private IOrganizationService organizationService;

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	private boolean createNew;
	private Organization organization;
	private List<ORG001> organizationList;

	@PostConstruct
	public void init() {
		createNewOrganization();
		loadOrganizations();
	}

	public void loadOrganizations() {
		organizationList = organizationService.findAll_ORG001();
	}

	public void createNewOrganization() {
		createNew = true;
		organization = new Organization();
	}

	public void prepareUpdateOrganization(ORG001 org001) {
		createNew = false;
		this.organization = organizationService.findOrganizationById(org001.getId());
	}

	public void addNewOrganization() {
		try {
			organizationService.addNewOrganization(organization);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, organization.getName());
			organizationList.add(new ORG001(organization));
			createNewOrganization();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateOrganization() {
		try {
			organizationService.updateOrganization(organization);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, organization.getName());
			createNewOrganization();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadOrganizations();
	}

	public String deleteOrganization(ORG001 org001) {
		try {
			organization = organizationService.findOrganizationById(org001.getId());
			organizationService.deleteOrganization(organization);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, organization.getName());
			organizationList.remove(org001);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<ORG001> getOrganizationList() {
		return organizationList;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		organization.getAddress().setTownship(township);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		organization.setBranch(branch);
	}

}
