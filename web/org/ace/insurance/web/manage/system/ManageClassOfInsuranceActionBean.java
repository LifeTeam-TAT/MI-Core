package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.classofinsurance.ClassOfInsurance;
import org.ace.insurance.system.common.classofinsurance.service.interfaces.IClassOfInsuranceService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageClassOfInsuranceActionBean")
public class ManageClassOfInsuranceActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean createNew;
	private ClassOfInsurance classofinsurance;
	private String criteria;
	private List<ClassOfInsurance> classofinsuranceList;

	@ManagedProperty(value = "#{ClassOfInsuranceService}")
	private IClassOfInsuranceService classofinsuranceService;

	public void setClassofinsuranceService(IClassOfInsuranceService classofinsuranceService) {
		this.classofinsuranceService = classofinsuranceService;
	}

	@PostConstruct
	public void init() {
		createNewClassOfInsurance();
		loadClassOfInsurance();
	}

	public void loadClassOfInsurance() {
		classofinsuranceList = classofinsuranceService.findAllClassOfInsurance();
	}

	public void createNewClassOfInsurance() {
		createNew = true;
		classofinsurance = new ClassOfInsurance();
	}

	public void prepareUpdateClassOfInsurance(ClassOfInsurance classofinsurance) {
		createNew = false;
		this.classofinsurance = classofinsurance;
	}

	public void addNewClassOfInsurance() {
		try {
			if (classofinsurance.getName() == null) {
				addInfoMessage(null, MessageId.REQUIRED_CLASSOFINSURANCE);
				return;
			}
			classofinsuranceService.addNewClassOfInsurance(classofinsurance);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, classofinsurance.getName());
			createNewClassOfInsurance();
			loadClassOfInsurance();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateClassOfInsurance() {
		try {
			classofinsuranceService.updateClassOfInsurance(classofinsurance);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, classofinsurance.getName());
			createNewClassOfInsurance();
			loadClassOfInsurance();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteClassOfInsurance() {
		try {
			classofinsuranceService.deleteClassOfInsurance(classofinsurance);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, classofinsurance.getName());
			createNewClassOfInsurance();
			loadClassOfInsurance();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public ClassOfInsurance getClassofinsurance() {
		return classofinsurance;
	}

	public void setClassofinsurance(ClassOfInsurance classofinsurance) {
		this.classofinsurance = classofinsurance;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public List<ClassOfInsurance> getClassofinsuranceList() {
		return classofinsuranceList;
	}

	public void setClassofinsuranceList(List<ClassOfInsurance> classofinsuranceList) {
		this.classofinsuranceList = classofinsuranceList;
	}
}
