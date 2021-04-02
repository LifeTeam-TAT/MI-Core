package org.ace.insurance.web.dialog;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.branch.BRA001;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.insurance.system.common.salesPoints.service.interfaces.ISalesPointsService;
import org.ace.insurance.user.User;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.Constants;
import org.primefaces.PrimeFaces;
@ViewScoped
@ManagedBean(name = "SalesPointsDialogActionBean")
public class SalesPointsDialogActionBean extends BaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{SalesPointsService}")
	private ISalesPointsService salesPointsService;
	
	public void setSalesPointsService(ISalesPointsService salesPointsService) {
		this.salesPointsService = salesPointsService;
	}

	User user;
	private List<SalesPoints> salesPointsList;
	
	@PostConstruct
	public void init() {
		user = (User) getParam(Constants.LOGIN_USER);
		salesPointsList = salesPointsService.findSalesPointsByBranch(user.getLoginBranch().getId());
	}
	
	public List<SalesPoints> getSalesPointsList() {
		return salesPointsList;
	}

	public void selectSalesPoints(SalesPoints salesPoints) {
		PrimeFaces.current().dialog().closeDynamic(salesPoints);
	}

}
