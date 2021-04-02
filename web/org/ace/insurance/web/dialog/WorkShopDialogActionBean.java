package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.workshop.WorkShop;
import org.ace.insurance.system.common.workshop.service.interfaces.IWorkShopService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "WorkShopDialogActionBean")
@ViewScoped
public class WorkShopDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{WorkShopService}")
	private IWorkShopService workShopService;

	public void setWorkShopService(IWorkShopService workShopService) {
		this.workShopService = workShopService;
	}

	private List<WorkShop> workShopList;

	@PostConstruct
	public void init() {
		workShopList = workShopService.findAll();
	}

	public List<WorkShop> getWorkShopList() {
		return workShopList;
	}

	public void selectWorkShop(WorkShop workShop) {
		RequestContext.getCurrentInstance().closeDialog(workShop);
	}
}
