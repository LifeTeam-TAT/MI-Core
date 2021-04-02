package org.ace.insurance.web.manage.system;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.icd10.service.interfaces.IICD10Service;
import org.ace.insurance.web.common.LazyDataModelUtil;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;
import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean(name = "ManageICD10ActionBean")
public class ManageICD10ActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ICD10Service}")
	private IICD10Service ICD10Service;

	public void setICD10Service(IICD10Service ICD10Service) {
		this.ICD10Service = ICD10Service;
	}

	private ICD10 icd10;
	private boolean createNew;
	private List<ICD10> icd10List;
	private String criteria;
	private LazyDataModelUtil<ICD10> lazyModel;

	@PostConstruct
	public void init() {
		createNewMedical();
		loadICD10List();
	}

	public void createNewMedical() {
		createNew = true;
		criteria = "";
		icd10 = new ICD10();
	}

	public void loadICD10List() {
		icd10List = ICD10Service.findAllICD10();
		lazyModel = new LazyDataModelUtil<ICD10>(icd10List);
	}

	public void addNewICD10() {
		try {
			ICD10Service.addNewICD10(icd10);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, icd10.getCode());
			createNewMedical();
			loadICD10List();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void updateICD10() {
		try {
			ICD10Service.updateICD10(icd10);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, icd10.getCode());
			createNewMedical();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		loadICD10List();
	}

	public String deleteICD10() {
		try {
			ICD10Service.deleteICD10(icd10);
			loadICD10List();
			addInfoMessage(null, MessageId.DELETE_SUCCESS, icd10.getCode());
			createNewMedical();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
		return null;
	}

	public void prepareUpdateICD10(ICD10 icd10) {
		createNew = false;
		this.icd10 = icd10;
	}

	public ICD10 getIcd10() {
		return icd10;
	}

	public boolean isCreateNew() {
		return createNew;
	}

	public List<ICD10> getIcd10List() {
		return icd10List;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setIcd10(ICD10 icd10) {
		this.icd10 = icd10;
	}

	public void setCreateNew(boolean createNew) {
		this.createNew = createNew;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public void selectICD10(ICD10 icd10) {
		RequestContext.getCurrentInstance().closeDialog(icd10);
	}

	public LazyDataModelUtil<ICD10> getLazyModel() {
		return lazyModel;
	}

	public void setIcd10List(List<ICD10> icd10List) {
		this.icd10List = icd10List;
	}

}
