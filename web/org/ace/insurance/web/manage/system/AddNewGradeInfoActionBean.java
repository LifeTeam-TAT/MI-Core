package org.ace.insurance.web.manage.system;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.gradeinfo.GradeInfo;
import org.ace.insurance.system.common.gradeinfo.service.interfaces.IGradeInfoService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "AddNewGradeInfoActionBean")
public class AddNewGradeInfoActionBean extends BaseBean {

	@ManagedProperty(value = "#{GradeInfoService}")
	private IGradeInfoService gradeInfoService;

	public void setGradeInfoService(IGradeInfoService gradeInfoService) {
		this.gradeInfoService = gradeInfoService;
	}

	private GradeInfo gradeInfo;
	private boolean createNew;
	private List<GradeInfo> gradeInfoList;
	private List<GradeInfo> filteredgradeInfoList;

	@PostConstruct
	public void init() {
		createNewGradeInfo();
		loadGradeInfo();
	}

	public void loadGradeInfo() {
		gradeInfoList = gradeInfoService.findAllGradeInfo();
	}

	public void createNewGradeInfo() {
		createNew = true;
		gradeInfo = new GradeInfo();
	}

	public void prepareUpdateGradeInfo(GradeInfo gradeInfo) {
		createNew = false;
		this.gradeInfo = gradeInfo;
	}

	public void addNewGradeInfo() {
		try {
			if (!isAreadyExistGradeInfo()) {
				gradeInfoService.addNewGradeInfo(gradeInfo);
				addInfoMessage(null, MessageId.INSERT_SUCCESS, gradeInfo.getName());
				createNewGradeInfo();
				loadGradeInfo();
			} else {
				addWranningMessage(null, MessageId.ALREADY_ADD_GRADE, gradeInfo.getName());
				loadGradeInfo();
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadGradeInfo();
	}

	public void updateGradeInfo() {
		try {
			if (!isAreadyExistGradeInfo()) {
				gradeInfoService.updateGradeInfo(gradeInfo);
				addInfoMessage(null, MessageId.UPDATE_SUCCESS, gradeInfo.getName());
				createNewGradeInfo();
				loadGradeInfo();
			} else {
				addInfoMessage(null, MessageId.ALREADY_ADD_GRADE, gradeInfo.getName());
				loadGradeInfo();
			}

		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadGradeInfo();
	}

	public void deleteGradeInfo(GradeInfo gradeInfo) {
		try {
			gradeInfoService.deleteGradeInfo(gradeInfo);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, gradeInfo.getName());
			createNewGradeInfo();
			loadGradeInfo();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadGradeInfo();
	}

	private boolean isAreadyExistGradeInfo() {

		return gradeInfoService.checkExistGradeInfo(gradeInfo);
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<GradeInfo> getGradeInfoList() {
		return gradeInfoList;
	}

	public List<GradeInfo> getFilteredgradeInfoList() {
		return filteredgradeInfoList;
	}

	public void setFilteredgradeInfoList(List<GradeInfo> filteredgradeInfoList) {
		this.filteredgradeInfoList = filteredgradeInfoList;
	}

}
