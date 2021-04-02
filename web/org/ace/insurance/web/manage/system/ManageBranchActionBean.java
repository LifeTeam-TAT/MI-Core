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

import org.ace.insurance.system.common.branch.BRA002;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageBranchActionBean")
public class ManageBranchActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean createNew;
	private Branch branch;
	@ManagedProperty(value = "#{BranchService}")
	private IBranchService branchService;
	private List<BRA002> branchList;

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	@PostConstruct
	public void init() {
		createNewBranch();
		loadBranch();
	}

	public void loadBranch() {
		branchList = branchService.findAll_BRA002();
	}

	public void createNewBranch() {
		createNew = true;
		branch = new Branch();
	}

	public void prepareUpdateBranch(BRA002 bra002) {
		this.branch = branchService.findBranchById(bra002.getId());
		createNew = false;
	}

	public void addNewBranch() {
		try {
			branchService.addNewBranch(branch);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, branch.getName());
			branchList.add(new BRA002(branch));
			createNewBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateBranch() {
		try {
			branchService.updateBranch(branch);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, branch.getName());
			createNewBranch();
			loadBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteBranch(BRA002 bra002) {
		try {
			this.branch = branchService.findBranchById(bra002.getId());
			branchService.deleteBranch(branch);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, branch.getName());
			branchList.remove(bra002);
			createNewBranch();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<BRA002> getBranchList() {
		return branchList;
	}

	public Branch getBranch() {
		return branch;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		branch.setTownship(township);
	}
}
