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
import org.ace.insurance.system.common.department.DEP001;
import org.ace.insurance.system.common.department.Department;
import org.ace.insurance.system.common.department.service.interfaces.IDepartmentService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageDepartmentActionBean")
public class ManageDepartmentActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{DepartmentService}")
	private IDepartmentService departmentService;

	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	private boolean createNew;
	private Department department;
	private List<DEP001> departmentList;

	@PostConstruct
	public void init() {
		createNewDepartment();
		loadDepartments();
	}

	public void loadDepartments() {
		departmentList = departmentService.findAll_DEP001();
	}

	public void createNewDepartment() {
		createNew = true;
		department = new Department();
	}

	public void prepareUpdateDepartment(DEP001 dep001) {
		createNew = false;
		this.department = departmentService.findDepartmentById(dep001.getId());
	}

	public void addNewDepartment() {
		try {
			departmentService.addNewDepartment(department);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, department.getName());
			departmentList.add(new DEP001(department));
			createNewDepartment();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateDepartment() {
		try {
			departmentService.updateDepartment(department);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, department.getName());
			createNewDepartment();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadDepartments();
	}

	public String deleteDepartment(DEP001 dep001) {
		try {
			department = departmentService.findDepartmentById(dep001.getId());
			departmentService.deleteDepartment(department);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, department.getName());
			departmentList.remove(dep001);
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<DEP001> getDepartmentList() {
		return departmentList;
	}

	public Department getDepartment() {
		return department;
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		department.getAddress().setTownship(township);
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		department.setBranch(branch);
	}

}
