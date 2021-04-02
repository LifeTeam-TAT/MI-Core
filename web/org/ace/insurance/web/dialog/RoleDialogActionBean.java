package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.role.ROL001;
import org.ace.insurance.role.Role;
import org.ace.insurance.role.service.interfaces.IRoleService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "RoleDialogActionBean")
@ViewScoped
public class RoleDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{RoleService}")
	private IRoleService roleService;

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	private List<ROL001> roleList;

	@PostConstruct
	public void init() {
		roleList = roleService.findAllRole();
	}

	public List<ROL001> getRoleList() {
		return roleList;
	}

	public void selectRole(ROL001 rol001) {
		Role role = roleService.findRoleById(rol001.getId());
		PrimeFaces.current().dialog().closeDynamic(role);
	}
}
