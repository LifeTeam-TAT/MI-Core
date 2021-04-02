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
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "BranchDialogActionBean")
@ViewScoped
public class BranchDialogActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	private List<BRA001> branchList;

	@PostConstruct
	public void init() {
		branchList = branchService.findAll_BRA001();
	}

	public List<BRA001> getBranchList() {
		return branchList;
	}

	public void selectBranch(BRA001 bra001) {
		Branch branch = branchService.findBranchById(bra001.getId());
		PrimeFaces.current().dialog().closeDynamic(branch);
	}
}
