package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.organization.ORG001;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.insurance.system.common.organization.service.interfaces.IOrganizationService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "OrganizationDialogActionBean")
@ViewScoped
public class OrganizationDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{OrganizationService}")
	private IOrganizationService organizationService;

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	private List<ORG001> organizationList;

	@PostConstruct
	public void init() {
		organizationList = organizationService.findAll_ORG001();
	}

	public List<ORG001> getOrganizationList() {
		return organizationList;
	}

	public void selectOrganization(ORG001 org001) {
		Organization organization = organizationService.findOrganizationById(org001.getId());
		PrimeFaces.current().dialog().closeDynamic(organization);
	}

}
