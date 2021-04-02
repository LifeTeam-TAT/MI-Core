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

import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.qualification.service.interfaces.IQualificationService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageQualificationActionBean")
public class ManageQualificationActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{QualificationService}")
	private IQualificationService qualificationService;

	public void setQualificationService(IQualificationService qualificationService) {
		this.qualificationService = qualificationService;
	}

	private boolean createNew;
	private Qualification qualification;
	private String criteria;
	private List<Qualification> qualificationList;

	@PostConstruct
	public void init() {
		createNewQualification();
		loadQualification();
	}

	public void createNewQualification() {
		createNew = true;
		qualification = new Qualification();
	}

	public void prepareUpdateQualification(Qualification qualification) {
		createNew = false;
		this.qualification = qualification;
	}

	public void addNewQualification() {
		try {
			qualificationService.addNewQualification(qualification);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, qualification.getName());
			createNewQualification();
			loadQualification();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateQualification() {
		try {
			qualificationService.updateQualification(qualification);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, qualification.getName());
			createNewQualification();
			loadQualification();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public String deleteQualification() {
		try {
			qualificationService.deleteQualification(qualification);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, qualification.getName());
			createNewQualification();
			loadQualification();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public void loadQualification() {
		qualificationList = qualificationService.findAllQualification();
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
}
