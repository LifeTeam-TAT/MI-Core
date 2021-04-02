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

import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.system.common.religion.service.interfaces.IReligionService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageReligionActionBean")
public class ManageReligionActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ReligionService}")
	private IReligionService religionService;

	public void setReligionService(IReligionService religionService) {
		this.religionService = religionService;
	}

	private boolean createNew;
	private Religion religion;
	private String criteria;
	private List<Religion> religionList;

	@PostConstruct
	public void init() {
		createNewReligion();
		loadReligion();
	}


	public void loadReligion() {
		religionList = religionService.findAllReligion();
	}

	public void createNewReligion() {
		createNew = true;
		religion = new Religion();
	}

	public void prepareUpdateReligion(Religion religion) {
		createNew = false;
		this.religion = religion;
	}

	public void addNewReligion() {
		try {
			religionService.addNewReligion(religion);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, religion.getName());
			createNewReligion();
			loadReligion();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateReligion() {
		try {
			religionService.updateReligion(religion);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, religion.getName());
			createNewReligion();
			loadReligion();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteReligion() {
		try {
			religionService.deleteReligion(religion);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, religion.getName());
			createNewReligion();
			loadReligion();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<Religion> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<Religion> religionList) {
		this.religionList = religionList;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
}
