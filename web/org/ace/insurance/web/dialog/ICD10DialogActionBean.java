package org.ace.insurance.web.dialog;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.filter.cirteria.CRIAICD10;
import org.ace.insurance.filter.icd10.interfaces.IICD10_Filter;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.icd10.service.interfaces.IICD10Service;
import org.ace.java.web.common.BaseBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "ICD10DialogActionBean")
@ViewScoped
public class ICD10DialogActionBean extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{ICD10Service}")
	private IICD10Service icdIicd10Service;

	public void setIcdIicd10Service(IICD10Service icdIicd10Service) {
		this.icdIicd10Service = icdIicd10Service;
	}
	
	@ManagedProperty(value = "#{ICD10_Filter}")
	protected IICD10_Filter filter;
	
	private CRIAICD10 icd10Criteria;
	private String criteriaValue;
	private List<ICD10> icd001List;
	private ICD10 icd10;
	
	@PostConstruct
	public void init() {
		icd001List = filter.find(30);
		
	}
	
	public void search() {
		icd001List = filter.find(icd10Criteria, criteriaValue);
	} 
	
	public void selectICD10(ICD10 icd10) {
		//ICD10 icd= icdIicd10Service.findbyId(icd10.getId());
		RequestContext.getCurrentInstance().closeDialog(icd10);
	}

	public CRIAICD10 getIcd10Criteria() {
		return icd10Criteria;
	}

	public void setIcd10Criteria(CRIAICD10 icd10Criteria) {
		this.icd10Criteria = icd10Criteria;
	}


	public List<ICD10> getIcd001List() {
		return icd001List;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public void setFilter(IICD10_Filter filter) {
		this.filter = filter;
	}
	
	public CRIAICD10[] getCriteriaItems() {
		return CRIAICD10.values();
	}

	public ICD10 getIcd10() {
		return icd10;
	}

	public void setIcd10(ICD10 icd10) {
		this.icd10 = icd10;
	}
	
}
