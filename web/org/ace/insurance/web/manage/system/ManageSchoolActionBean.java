package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.school.School;
import org.ace.insurance.system.common.school.service.interfaces.ISchoolService;
import org.ace.insurance.system.common.township.Township;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.event.SelectEvent;

@ViewScoped
@ManagedBean(name = "ManageSchoolActionBean")
public class ManageSchoolActionBean extends BaseBean {

	@ManagedProperty(value = "#{SchoolService}")
	private ISchoolService schoolService;

	public void setSchoolService(ISchoolService schoolService) {
		this.schoolService = schoolService;
	}

	private School school;
	private List<School> schoolList;
	private boolean createNew;

	private List<School> filterschoolList;

	@PostConstruct
	public void init() {
		createNewSchool();
		loadSchool();
	}

	public void loadSchool() {
		schoolList = schoolService.findAllSchool();
	}

	public void createNewSchool() {
		createNew = true;
		school = new School();
	}

	public void prepareUpdateSchool(School school) {
		createNew = false;
		this.school = school;
	}

	public void addNewSchool() {
		try {
			if (!isAreadyExist()) {
				schoolService.addNewSchool(school);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, school.getName());
				createNewSchool();
				loadSchool();
			} else {
				addWranningMessage(null, MessageId.ALREADY_ADD_SCHOOL, school.getName());
				loadSchool();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadSchool();
	}

	private boolean isAreadyExist() {
		return schoolService.checkExistingSchool(school);
	}

	public void updateSchool() {
		try {
			if (!isAreadyExist()) {
				schoolService.updateSchool(school);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, school.getName());
				createNewSchool();
				loadSchool();
			} else {
				addInfoMessage(null, MessageId.ALREADY_ADD_SCHOOL, school.getName());
				loadSchool();
			}
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadSchool();
	}

	public void deleteSchool(School school) {
		try {
			schoolService.deleteSchool(school);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, school.getName());
			createNewSchool();
			loadSchool();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadSchool();
	}

	public void returnTownship(SelectEvent event) {
		Township township = (Township) event.getObject();
		school.setTownship(township);
	}

	public School getSchool() {
		return school;
	}

	public List<School> getSchoolList() {
		return schoolList;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public List<School> getFilterschoolList() {
		return filterschoolList;
	}

	public void setFilterschoolList(List<School> filterschoolList) {
		this.filterschoolList = filterschoolList;
	}

}
